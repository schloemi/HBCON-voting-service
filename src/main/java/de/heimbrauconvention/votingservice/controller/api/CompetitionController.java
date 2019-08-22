package de.heimbrauconvention.votingservice.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.heimbrauconvention.votingservice.dto.CompetitionDTO;
import de.heimbrauconvention.votingservice.dto.StatisticDTO;
import de.heimbrauconvention.votingservice.service.CompetitionService;

@RestController
@RequestMapping("/api")
public class CompetitionController {

	@Autowired
	CompetitionService service;

	
	@GetMapping(path = "/competitions", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CompetitionDTO> listCompetition() {
		
		return service.getAllActiveCompetitions();
	}

	@GetMapping(path = "/competitions/{competitionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CompetitionDTO getCompetition (
			@PathVariable Long competitionId
		) {

		return service.convertToDto(service.getById(competitionId));
	}

	
	@GetMapping(path = "/competitions/{competitionId}/statistic", produces = MediaType.APPLICATION_JSON_VALUE)
	public StatisticDTO statistic(
			@PathVariable Long competitionId
		) {

		return service.getStatistic(competitionId);
	}

	@GetMapping(path = "/competitions/{competitionId}/validate", produces = MediaType.APPLICATION_JSON_VALUE)
	public CompetitionDTO validateCode(
			@PathVariable Long competitionId
		) {

		return service.getDTO(competitionId);
	}

}