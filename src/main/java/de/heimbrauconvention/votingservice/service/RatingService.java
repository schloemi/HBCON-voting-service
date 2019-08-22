package de.heimbrauconvention.votingservice.service;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import de.heimbrauconvention.votingservice.domain.Rating;
import de.heimbrauconvention.votingservice.dto.CompetitionDTO;
import de.heimbrauconvention.votingservice.dto.RatingCodeDTO;
import de.heimbrauconvention.votingservice.dto.RatingDTO;
import de.heimbrauconvention.votingservice.dto.RatingItemDTO;
import de.heimbrauconvention.votingservice.dto.ResponseStatus;
import de.heimbrauconvention.votingservice.repository.RatingRepository;

@Transactional
@Service
public class RatingService extends AbstractEntityService<Rating, RatingDTO, CrudRepository<Rating,Long>>{

	@Autowired
	RatingRepository ratingRepository;
	
	@Autowired
	RatingItemService ratingItemService;
	
	@Autowired
	RatingCodeService ratingCodeService;
	
	@Autowired
	CompetitionService competitionService;
	

	@Override
	public RatingDTO convertToDto(Rating pItem) {
		return (pItem == null)? null : modelMapper.map(pItem, RatingDTO.class);
	}

	public RatingDTO rate(String codeId, String itemId, Float value) {
		RatingDTO dto = new RatingDTO();
		
		RatingItemDTO ratingItemDTO = ratingItemService.getDTO(itemId);
		dto.setRatingItemDTO(ratingItemDTO);
		if (!ResponseStatus.OK.equals(ratingItemDTO.getResponseStatus())) {
			dto.setResponseStatus(ratingItemDTO.getResponseStatus());
			return dto;
		}
		
		RatingCodeDTO ratingCodeDTO = ratingCodeService.getDTO(codeId);;
		dto.setRatingCodeDTO(ratingCodeDTO);
		if (!ResponseStatus.OK.equals(ratingCodeDTO.getResponseStatus())) {
			dto.setResponseStatus(ratingCodeDTO.getResponseStatus());
			return dto;
		}
		
		CompetitionDTO competitionDTO = competitionService.getDTO(ratingItemDTO.getCompetitionDTO().getId());
		dto.setCompetitionDTO(competitionDTO);
		if (!ResponseStatus.OK.equals(competitionDTO.getResponseStatus())) {
			dto.setResponseStatus(competitionDTO.getResponseStatus());
			return dto;
		}
		
		List<Object[]> competitionRatings = ratingRepository.getCompetitionRatingsForRatingCode(ratingCodeDTO.getEntity().getId());
		if (!CollectionUtils.isEmpty(competitionRatings)) {
			for (Object[] competitionRating : competitionRatings) {
				if (	((BigInteger)competitionRating[0]).longValue() == competitionDTO.getEntity().getId() && 
						((BigInteger)competitionRating[2]).longValue() < 1L) {
					dto.setResponseStatus(ResponseStatus.ERROR_NO_RATINGS_LEFT);
					return dto;
				}
			}
		}
		
		if (Boolean.TRUE.equals(competitionDTO.getEntity().getDistinctRating())) {
			List<Rating> ratingsWithCode = ratingRepository.findByRatingCodeAndRatingItem(ratingCodeDTO.getEntity(), ratingItemDTO.getEntity());
			if (!CollectionUtils.isEmpty(ratingsWithCode)) {
				dto.setResponseStatus(ResponseStatus.ERROR_DISTINCT_RATING);
				return dto;
			} 
		}	
		
		Rating newRating = new Rating(ratingItemDTO.getEntity(), ratingCodeDTO.getEntity(), value);
		ratingRepository.save(newRating);
		
		RatingDTO newDTO = convertToDto(newRating);
		newDTO.setRatingCodeDTO(dto.getRatingCodeDTO());
		newDTO.setRatingItemDTO(dto.getRatingItemDTO());
		newDTO.setCompetitionDTO(dto.getCompetitionDTO());
		
		ratingCodeService.addRatingCodeContingent(newDTO.getRatingCodeDTO());
		dto.setResponseStatus(ResponseStatus.OK);
		
		return dto;
	}


}
