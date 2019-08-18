package de.heimbrauconvention.votingservice.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.heimbrauconvention.votingservice.dto.RatingDTO;
import de.heimbrauconvention.votingservice.service.RatingService;

@RestController
@RequestMapping("/api")
public class RatingController {

	
	@Autowired
	RatingService ratingService;
	
	/*********************************************************
	 * 
	 * RATING ENDPOINTS
	 */

	// TODO: Make this a POST-Mapping
	@GetMapping(path = "/rating/code/{codeId}/item/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public RatingDTO rate(
			@PathVariable String codeId,
			@PathVariable String itemId
		) {

		return ratingService.rate(codeId, itemId, 1.0F);
	}

	// TODO: Make this a POST-Mapping
	@GetMapping(path = "/rating/code/{codeId}/item/{itemId}/value/{value}", produces = MediaType.APPLICATION_JSON_VALUE)
	public RatingDTO rateWithValue(
			@PathVariable String codeId, 
			@PathVariable String itemId,
			@PathVariable Float value) {

		return ratingService.rate(codeId, itemId, value);
	}
	

}
