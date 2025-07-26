package com.reedmanit.runaustralia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/routes")
public class RouteController {

    // Display the route map page
    @GetMapping
    public String showRoutePage(Model model) {
        // Add any default values or necessary attributes
        model.addAttribute("defaultStartLat", "-27.4705"); // Brisbane coordinates
        model.addAttribute("defaultStartLng", "153.0260");
        model.addAttribute("defaultEndLat", "-27.4698");
        model.addAttribute("defaultEndLng", "153.0251");

        // Add any saved routes
       // List<Route> savedRoutes = routeService.getAllRoutes();
       // model.addAttribute("savedRoutes", savedRoutes);

        return "route/runqldroute";
    }


}
