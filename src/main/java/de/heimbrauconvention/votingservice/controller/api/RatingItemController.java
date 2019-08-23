package de.heimbrauconvention.votingservice.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.heimbrauconvention.votingservice.dto.RatingItemDTO;
import de.heimbrauconvention.votingservice.service.RatingItemService;


@RestController
@RequestMapping("/api")
public class RatingItemController{

	@Autowired
	RatingItemService service;
	
	@GetMapping(path = "/ratingitems/{publicId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public RatingItemDTO getRatingItem(
			@PathVariable String publicId
		) {

		StopWatch watch = new StopWatch();
		watch.start();
		RatingItemDTO dto = service.getDTO(publicId, Boolean.TRUE);
		watch.stop();
		dto.setProcessingTime(watch.getTotalTimeMillis());
		
		return dto;
	}
	
	@GetMapping(path = "/ratingitems/{publicId}/validate", produces = MediaType.APPLICATION_JSON_VALUE)
	public RatingItemDTO validate(
			@PathVariable String publicId
			) {
		
		StopWatch watch = new StopWatch();
		watch.start();
		RatingItemDTO dto =  service.getDTO(publicId);
		watch.stop();
		dto.setProcessingTime(watch.getTotalTimeMillis());
		
		return dto;
	}
}
