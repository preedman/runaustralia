package com.reedmanit.runaustralia.controller;

import com.reedmanit.runaustralia.data.Activity;
import com.reedmanit.runaustralia.service.ActivityRepository;
import com.reedmanit.runaustralia.service.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityRepository activityRepository;
    private final MemberRepository memberRepository;

    public ActivityController(ActivityRepository activityRepository,
                              MemberRepository memberRepository) {
        this.activityRepository = activityRepository;
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public String listActivities(@RequestParam(required = false) Integer memberId,
                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 Model model) {

        // Add all members for the filter dropdown
        model.addAttribute("members", memberRepository.findAll());

        // Create the pageable request
        Pageable pageable = PageRequest.of(page, size, Sort.by("datedone").descending());

        // Get filtered activities
        Page<Activity> activities;
        if (memberId != null || startDate != null || endDate != null) {
            activities = activityRepository.findByFilters(memberId, startDate, endDate, pageable);
        } else {
            activities = activityRepository.findAll(pageable);
        }

        model.addAttribute("activities", activities);
        return "activities/list";
    }
}

