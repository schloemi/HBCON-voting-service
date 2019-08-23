package de.heimbrauconvention.votingservice.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.heimbrauconvention.votingservice.dto.RatingCodeDTO;
import de.heimbrauconvention.votingservice.service.RatingCodeService;


@RestController
@RequestMapping("/api")
public class RatingCodeController{

	@Autowired
	RatingCodeService service;
	
	@GetMapping(path = "/ratingcodes/{publicId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public RatingCodeDTO getRatingCode(
			@PathVariable String publicId
		) {

		StopWatch watch = new StopWatch();
		watch.start();
		RatingCodeDTO dto = service.getDTO(publicId);
		watch.stop();
		dto.setProcessingTime(watch.getTotalTimeMillis());
		
		return dto;
	}
	
	
	@GetMapping(path = "/ratingcodes/{publicId}/checkin", produces = MediaType.APPLICATION_JSON_VALUE)
	public RatingCodeDTO checkin(
			@PathVariable String publicId
		) {

		StopWatch watch = new StopWatch();
		watch.start();
		RatingCodeDTO dto = service.getDTO(publicId);
		watch.stop();
		dto.setProcessingTime(watch.getTotalTimeMillis());
		
		return dto;
	}

}
