package de.heimbrauconvention.votingservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.heimbrauconvention.votingservice.domain.Competition;
import de.heimbrauconvention.votingservice.service.CompetitionService;

@Controller
@RequestMapping("/web")
public class WebController {

	@Autowired
	CompetitionService competitionService;
	
	
	@RequestMapping("/competitions/{competitionId}")
    public String homePage(
    		HttpServletRequest request,
			HttpServletResponse response,
			Model model,
    		@PathVariable Long competitionId) {
		
		Competition competition = competitionService.getById(competitionId);
		if (competition == null) {
			return "error";
		}
		model.addAttribute("competition", competitionService.convertToDto(competition));
		return "competition";
    }

}
