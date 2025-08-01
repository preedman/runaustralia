package com.reedmanit.runaustralia;

import com.reedmanit.runaustralia.data.Place;
import com.reedmanit.runaustralia.data.Route;
import com.reedmanit.runaustralia.service.RouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RouteRepositoryTest {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private TestEntityManager entityManager; // Add this to manage test entities

    private Route testRoute1;
    private Route testRoute2;
    private Place startPlace;
    private Place endPlace;

    @BeforeEach
    void setUp() {
        // Create test places
        startPlace = new Place();
        startPlace.setName("Queen Street Mall");
        startPlace.setLatitude(-27.4695f);
        startPlace.setLongitude(153.02528f);
        startPlace = entityManager.persist(startPlace); // Persist place first


        endPlace = new Place();
        endPlace.setName("Caloundra Hotel");
        endPlace.setLatitude(-26.80512f);
        endPlace.setLongitude(153.1352f);
        endPlace = entityManager.persist(endPlace); // Persist place first


        // Create first test route
        testRoute1 = new Route();
        testRoute1.setName("Sydney to Melbourne");
        testRoute1.setDistance(878.0f);
        testRoute1.setPlaceStart(startPlace);
        testRoute1.setPlaceEnd(endPlace);
        testRoute1 = entityManager.persist(testRoute1);


        // Create second test route
        testRoute2 = new Route();
        testRoute2.setName("Melbourne to Adelaide");
        testRoute2.setDistance(725.5f);
        testRoute2.setPlaceStart(endPlace);
        testRoute2.setPlaceEnd(startPlace);
        testRoute2 = entityManager.persist(testRoute2);

        // Save test data
        routeRepository.save(testRoute1);
        routeRepository.save(testRoute2);
        entityManager.flush();
    }

    @Test
    void findAll_ShouldReturnAllRoutes() {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));

        // When
        Page<Route> routePage = routeRepository.findAll(pageRequest);

        // Then
        assertThat(routePage).isNotNull();
        assertThat(routePage.getContent()).hasSize(2);
        assertThat(routePage.getContent()).contains(testRoute1, testRoute2);
    }

    @Test
    void findByNameContaining_ShouldReturnMatchingRoutes() {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10);
        String searchTerm = "Melbourne";

        // When
        Page<Route> routePage = routeRepository.findByNameContaining(searchTerm, pageRequest);

        // Then
        assertThat(routePage).isNotNull();
        assertThat(routePage.getContent()).hasSize(2);
        assertThat(routePage.getContent().get(0).getName()).contains("Melbourne");
    }

    @Test
    void findByDistanceGreaterThanEqual_ShouldReturnMatchingRoutes() {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10);
        Float minDistance = 800.0f;

        // When
        Page<Route> routePage = routeRepository.findByDistanceGreaterThanEqual(minDistance, pageRequest);

        // Then
        assertThat(routePage).isNotNull();
        assertThat(routePage.getContent()).hasSize(1);
        assertThat(routePage.getContent().get(0).getDistance()).isGreaterThanOrEqualTo(minDistance);
    }

    @Test
    void findAllWithPlaces_ShouldReturnRoutesWithPlaces() {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10);

        // When
        Page<Route> routePage = routeRepository.findAllWithPlaces(pageRequest);

        // Then
        assertThat(routePage).isNotNull();
        assertThat(routePage.getContent()).hasSize(2);

        Route firstRoute = routePage.getContent().get(0);
        assertNotNull(firstRoute.getPlaceStart());
        assertNotNull(firstRoute.getPlaceEnd());
        assertEquals("Queen Street Mall", firstRoute.getPlaceStart().getName());


    }

    @Test
    void saveRoute_ShouldPersistRoute() {
        // Given
        Route newRoute = new Route();
        newRoute.setName("Brisbane to Sydney");
        newRoute.setDistance(927.0f);
        newRoute.setPlaceStart(startPlace);
        newRoute.setPlaceEnd(endPlace);

        // When
        Route savedRoute = routeRepository.save(newRoute);

        // Then
        assertThat(savedRoute).isNotNull();
        assertThat(savedRoute.getId()).isNotNull();
        assertThat(savedRoute.getName()).isEqualTo("Brisbane to Sydney");
        assertThat(savedRoute.getDistance()).isEqualTo(927.0f);
    }


}
