package com.reedmanit.runaustralia.controller;

import com.reedmanit.runaustralia.data.Activity;
import com.reedmanit.runaustralia.data.Member;
import com.reedmanit.runaustralia.service.ActivityRepository;
import com.reedmanit.runaustralia.service.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @GetMapping
    public String showDashboard(Model model) {
        // Get statistics
        Map<String, Object> stats = getStatistics();
        model.addAttribute("stats", stats);

        // Get recent activities
        Page<Activity> recentActivities = activityRepository.findAll(
                PageRequest.of(0, 5, Sort.by("datedone").descending())
        );
        model.addAttribute("recentActivities", recentActivities.getContent());

        // Get active members
        Page<Member> activeMembers = memberRepository.findActiveMembers(
                PageRequest.of(0, 5)
        );
        model.addAttribute("activeMembers", activeMembers.getContent());

        // Get recent members with activities
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        Page<Member> recentActiveMembers = memberRepository.findMembersWithRecentActivities(
                oneMonthAgo,
                PageRequest.of(0, 5)
        );
        model.addAttribute("recentActiveMembers", recentActiveMembers.getContent());

        return "dashboard/dashboard";
    }

    private Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();

        // Member statistics
        stats.put("totalMembers", memberRepository.count());
        stats.put("activeMembers", memberRepository.countByStatus("ACTIVE"));

        // Get activities for the last 30 days
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        Page<Activity> recentActivities = activityRepository.findByFilters(
                null,
                thirtyDaysAgo,
                LocalDate.now(),
                PageRequest.of(0, Integer.MAX_VALUE)
        );

        // Calculate activity statistics
        double totalDistance = 0;
        double totalTime = 0;
        Map<String, Integer> activityTypes = new HashMap<>();

        for (Activity activity : recentActivities.getContent()) {
            totalDistance += activity.getDistance() != null ? activity.getDistance() : 0;
            totalTime += activity.getActivitytime() != null ? activity.getActivitytime() : 0;

            String type = activity.getType();
            activityTypes.put(type, activityTypes.getOrDefault(type, 0) + 1);
        }

        stats.put("totalActivities", recentActivities.getTotalElements());
        stats.put("totalDistance", Math.round(totalDistance * 100.0) / 100.0);
        stats.put("totalTime", Math.round(totalTime * 100.0) / 100.0);
        stats.put("activityTypes", activityTypes);
        stats.put("period", "Last 30 days");

        return stats;
    }
}


