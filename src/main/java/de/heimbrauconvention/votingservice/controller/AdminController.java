package de.heimbrauconvention.votingservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.heimbrauconvention.votingservice.domain.Competition;
import de.heimbrauconvention.votingservice.domain.RatingCode;
import de.heimbrauconvention.votingservice.domain.RatingItem;
import de.heimbrauconvention.votingservice.dto.RatingDTO;
import de.heimbrauconvention.votingservice.service.CompetitionService;
import de.heimbrauconvention.votingservice.service.RatingCodeService;
import de.heimbrauconvention.votingservice.service.RatingItemService;
import de.heimbrauconvention.votingservice.service.RatingService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private RatingItemService ratingItemService;
	
	@Autowired
	private RatingCodeService ratingCodeService;
	
	@Autowired
	private CompetitionService competitionService;
	
	@Autowired
	private RatingService ratingService;
	
	
	@RequestMapping(value="/competitions/{competitionId}/ratingItems", method = RequestMethod.GET)
	public String index(
			ModelMap modelMap,
			@PathVariable("competitionId") Long competitionId
		) {
		
		Competition competition = competitionService.getById(competitionId);
		if (competition == null) {
			return "error";
		}
		modelMap.put("competition", competition);
		return "admin/rating-items";
	}
	
	
	@RequestMapping(value = "/competitions/{competitionId}/addRatings/{number}", method = RequestMethod.GET)
	public String addRatings(
			ModelMap modelMap,
			@PathVariable("competitionId") Long competitionId,
			@PathVariable("number") Integer number 
		) {
		
		Competition competition = competitionService.getById(competitionId);
		if (competition == null) {
			return "error";
		}

		List<RatingDTO> ratings = new ArrayList<>();
		List<RatingItem> ratingItems = new ArrayList<>(competition.getRatingItems());
		List<RatingCode> ratingCodes = new ArrayList<>(competition.getRatingCodes());
		if (!CollectionUtils.isEmpty(ratingItems) && !CollectionUtils.isEmpty(ratingCodes)) {
			for (int i=0; i <number; i++) {
				int ratingItemIndex = (int) (Math.random() * ratingItems.size());
				int ratingCodeIndex = (int) (Math.random() * ratingCodes.size());
				RatingDTO rating = (RatingDTO) ratingService.rate(ratingCodes.get(ratingCodeIndex).getPublicId(), ratingItems.get(ratingItemIndex).getPublicId() , 1.0F);
				rating.setRatingCodeDTO(ratingCodeService.convertToDto(ratingCodes.get(ratingCodeIndex)));
				rating.setRatingItemDTO(ratingItemService.convertToDto(ratingItems.get(ratingItemIndex)));
				ratings.add(rating);
			}
		}
		modelMap.put("competition", competition);
		modelMap.put("ratings", ratings);
		return "admin/rating-add";
	}


}
