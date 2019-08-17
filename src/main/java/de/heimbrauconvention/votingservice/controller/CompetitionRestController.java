package de.heimbrauconvention.votingservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.heimbrauconvention.votingservice.dto.CompetitionDTO;
import de.heimbrauconvention.votingservice.dto.RatingDTO;
import de.heimbrauconvention.votingservice.dto.RatingItemDTO;
import de.heimbrauconvention.votingservice.dto.StatisticDTO;
import de.heimbrauconvention.votingservice.dto.ValidationDTO;
import de.heimbrauconvention.votingservice.service.CompetitionService;
import de.heimbrauconvention.votingservice.service.RatingService;


@RestController
@RequestMapping("/api")
public class CompetitionRestController {
	
  
   @Autowired
   CompetitionService competitionService;
   
   @Autowired
   RatingService ratingService;
   
   
	@RequestMapping(value = "/longboard")
	public String longboardOverview(HttpServletRequest request, HttpServletResponse response) {
		
		return "index";
	}
   
   
   
   @GetMapping(path = "/competitions" , produces=MediaType.APPLICATION_JSON_VALUE)
   public List<CompetitionDTO> listCompetition(
					HttpServletRequest request,
					HttpServletResponse response) {
	   
	    return competitionService.convertToDto(competitionService.getAllAsList());
   }
   
   
   
   @GetMapping(path = "/competitions/{competitionId}" , produces=MediaType.APPLICATION_JSON_VALUE)
   public CompetitionDTO getCompetition(
					HttpServletRequest request,
					HttpServletResponse response,
					@PathVariable Long competitionId) {
	   
	    return competitionService.convertToDto(competitionService.getById(competitionId));
   }
   
   
   @GetMapping(path = "/competitions/{competitionId}/item/{itemId}" , produces=MediaType.APPLICATION_JSON_VALUE)
   public RatingItemDTO getCompetition(
					HttpServletRequest request,
					HttpServletResponse response,
					@PathVariable Long competitionId,
					@PathVariable String itemId
						
				) {
	   
	    return competitionService.getRatingItemWithScore(competitionId, itemId);
   }
   

   /*********************************************************
    * 
    * STATISTIC ENDPOINTS
    */
   
   
   @GetMapping(path = "/competitions/{competitionId}/statistic" , produces=MediaType.APPLICATION_JSON_VALUE)
   public StatisticDTO statistic(
					HttpServletRequest request,
					HttpServletResponse response,
					@PathVariable Long competitionId
			) {
	   
	    return competitionService.getStatistic(competitionId);
   }
   
   
   
   /*********************************************************
    * 
    * VALIDATION ENDPOINTS
    */
   
   @GetMapping(path = "/competitions/{competitionId}/validate" , produces=MediaType.APPLICATION_JSON_VALUE)
   public ValidationDTO validateCode(
					HttpServletRequest request,
					HttpServletResponse response,
					@PathVariable Long competitionId
					
			) {
	   
	    return competitionService.validateCompetition(competitionId);
   }
   
   @GetMapping(path = "/competitions/{competitionId}/validate/code/{codeId}" , produces=MediaType.APPLICATION_JSON_VALUE)
   public ValidationDTO validateCode(
					HttpServletRequest request,
					HttpServletResponse response,
					@PathVariable Long competitionId,
					@PathVariable String codeId
			) {
	   
	    return competitionService.validateCode(competitionId, codeId);
   }
   
   @GetMapping(path = "/competitions/{competitionId}/validate/item/{itemId}" , produces=MediaType.APPLICATION_JSON_VALUE)
   public ValidationDTO validateItem(
					HttpServletRequest request,
					HttpServletResponse response,
					@PathVariable Long competitionId,
					@PathVariable String itemId
			) {
	   
	    return competitionService.validateItem(competitionId, itemId);
   }
   
   
   
   
   /*********************************************************
    * 
    * RATING ENDPOINTS
    */
  
   // TODO: Make this a POST-Mapping
   @GetMapping(path = "/competitions/{competitionId}/rate/code/{codeId}/item/{itemId}" , produces=MediaType.APPLICATION_JSON_VALUE)
   public RatingDTO rate(
					HttpServletRequest request,
					HttpServletResponse response,
					@PathVariable Long competitionId,
					@PathVariable String codeId,
					@PathVariable String itemId
			) {
	   
	    return ratingService.rate(competitionId, codeId, itemId, 1.0F);
   }
	
   // TODO: Make this a POST-Mapping
   @GetMapping(path = "/competitions/{competitionId}/rate/code/{codeId}/item/{itemId}/value/{value}" , produces=MediaType.APPLICATION_JSON_VALUE)
   public RatingDTO rateWithValue(
					HttpServletRequest request,
					HttpServletResponse response,
					@PathVariable Long competitionId,
					@PathVariable String codeId,
					@PathVariable String itemId,
					@PathVariable Float value
			) {
	   
	    return ratingService.rate(competitionId, codeId, itemId, value);
   }
	
	
   
   
}