package de.heimbrauconvention.votingservice.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.heimbrauconvention.votingservice.dto.RatingItemDTO;
import de.heimbrauconvention.votingservice.dto.ValidationDTO;
import de.heimbrauconvention.votingservice.service.RatingItemService;


@RestController
@RequestMapping("/api")
public class RatingItemController{

	@Autowired
	RatingItemService service;
	
	@GetMapping(path = "/ratingitems/{publicId}/validate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ValidationDTO validate(
			@PathVariable String publicId
		) {

		return service.validate(publicId);
	}

	
	@GetMapping(path = "/ratingitems/{publicId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public RatingItemDTO getCompetition(
			@PathVariable Long competitionId,
			@PathVariable String itemId
		) {

		return service.getRatingItem(itemId, true);
	}
	
}