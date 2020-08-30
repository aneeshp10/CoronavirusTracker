package com.example.coronavirustracker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.coronavirustracker.models.LocationStats;
import com.example.coronavirustracker.services.CoronaVirusDataService;

@Controller
public class HomeController {

	@Autowired
	CoronaVirusDataService corSer;

	@GetMapping("/")
	public String home(Model model) {
		List<LocationStats> allStats = corSer.getAllStats();
		int totalReportedCases = allStats.stream().mapToInt(sumOfStats -> sumOfStats.getLatestTotalCases()).sum();
		int newCases = allStats.stream().mapToInt(newStats -> newStats.getDifferenceFromPreviousDay())
				.sum();
		model.addAttribute("locStatsVar", allStats);
		model.addAttribute("totalReportedCases", totalReportedCases);
		model.addAttribute("newCases", newCases);
		return "home";

	}
}
