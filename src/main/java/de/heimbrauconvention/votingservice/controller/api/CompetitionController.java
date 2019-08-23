package de.heimbrauconvention.votingservice.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.heimbrauconvention.votingservice.dto.CompetitionDTO;
import de.heimbrauconvention.votingservice.dto.CompetitionListDTO;
import de.heimbrauconvention.votingservice.dto.KeyFiguresDTO;
import de.heimbrauconvention.votingservice.dto.StatisticDTO;
import de.heimbrauconvention.votingservice.service.CompetitionService;

@RestController
@RequestMapping("/api")
public class CompetitionController {

	@Autowired
	CompetitionService service;

	
	@GetMapping(path = "/competitions", produces = MediaType.APPLICATION_JSON_VALUE)
	public CompetitionListDTO listCompetition() {
		
		StopWatch watch = new StopWatch();
		watch.start();
		CompetitionListDTO dto = service.getAllActiveCompetitions();
		watch.stop();
		dto.setProcessingTime(watch.getTotalTimeMillis());
		
		return dto;
	}

	@GetMapping(path = "/competitions/{publicId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CompetitionDTO getCompetition (
			@PathVariable String publicId
		) {

		StopWatch watch = new StopWatch();
		watch.start();
		CompetitionDTO dto = service.getDTO(publicId);
		watch.stop();
		dto.setProcessingTime(watch.getTotalTimeMillis());
		
		return dto;
	}

	
	@GetMapping(path = "/competitions/{publicId}/statistic", produces = MediaType.APPLICATION_JSON_VALUE)
	public StatisticDTO getStatistic(
			@PathVariable String publicId
		) {

		StopWatch watch = new StopWatch();
		watch.start();
		StatisticDTO dto = service.getStatistic(publicId);
		watch.stop();
		dto.setProcessingTime(watch.getTotalTimeMillis());
		
		return dto;
	}
	
	@GetMapping(path = "/competitions/{publicId}/key-figures", produces = MediaType.APPLICATION_JSON_VALUE)
	public KeyFiguresDTO getKeyFigures(
			@PathVariable String publicId
		) {

		StopWatch watch = new StopWatch();
		watch.start();
		KeyFiguresDTO dto = service.getKeyFigures(publicId);
		watch.stop();
		dto.setProcessingTime(watch.getTotalTimeMillis());
		
		return dto;
	}
	

}