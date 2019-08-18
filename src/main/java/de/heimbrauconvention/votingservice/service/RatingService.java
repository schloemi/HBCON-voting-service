package de.heimbrauconvention.votingservice.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import de.heimbrauconvention.votingservice.domain.Competition;
import de.heimbrauconvention.votingservice.domain.Rating;
import de.heimbrauconvention.votingservice.domain.RatingCode;
import de.heimbrauconvention.votingservice.domain.RatingItem;
import de.heimbrauconvention.votingservice.dto.RatingDTO;
import de.heimbrauconvention.votingservice.dto.ResponseStatus;
import de.heimbrauconvention.votingservice.repository.RatingCodeRepository;
import de.heimbrauconvention.votingservice.repository.RatingItemRepository;
import de.heimbrauconvention.votingservice.repository.RatingRepository;

@Transactional
@Service
public class RatingService extends AbstractEntityService<Rating, RatingDTO, CrudRepository<Rating,Long>>{

	@Autowired
	RatingRepository ratingRepository;
	
	@Autowired
	RatingItemRepository ratingItemRepository;
	
	@Autowired
	RatingCodeRepository ratingCodeRepository;
	
	@Autowired
	CompetitionService competitionService;
	
	
	
	@Override
	public RatingDTO convertToDto(Rating pItem) {
		return (pItem == null)? null : modelMapper.map(pItem, RatingDTO.class);
	}

	public RatingDTO rate(String codeId, String itemId, Float value) {
		RatingDTO dto = new RatingDTO();
		
		
		if (StringUtils.isEmpty(codeId)) {
			dto.setResponseStatus(ResponseStatus.ERROR_READING_RATING_CODE);
			return dto;
		}
		
		if (StringUtils.isEmpty(itemId)) {
			dto.setResponseStatus(ResponseStatus.ERROR_READING_RATING_ITEM);
			return dto;
		}
		
		RatingCode ratingCode = ratingCodeRepository.findByPublicId(codeId).orElse(null);
		if (ratingCode == null) {
			dto.setResponseStatus(ResponseStatus.ERROR_NOT_FOUND_RATING_CODE);
			return dto;
		}
		
		if (Boolean.TRUE.equals(ratingCode.getExpired())) {
			dto.setResponseStatus(ResponseStatus.ERROR_RATING_CODE_EXPIRED);
			return dto;
		}
		
		Competition competition = ratingCode.getCompetition();
		
		ResponseStatus competitionStatus = competitionService.validateCompetition(competition);
		if (!ResponseStatus.OK.equals(competitionStatus)) {
			dto.setResponseStatus(competitionStatus);
			return dto;
		}
		
		RatingItem ratingItem = ratingItemRepository.findByPublicId(itemId).orElse(null);
		if (ratingItem == null) {
			dto.setResponseStatus(ResponseStatus.ERROR_NOT_FOUND_RATING_ITEM);
			return dto;
		}
		
		if (!Boolean.TRUE.equals(ratingItem.getIsActive())) {
			dto.setResponseStatus(ResponseStatus.ERROR_NOT_ACTIVE_RATING_ITEM);
			return dto;
		}
		
		if (!competition.equals(ratingItem.getCompetition())) {
			dto.setResponseStatus(ResponseStatus.ERROR_MATCH_RATING_CODE_ITEM);
			return dto;
		}
		
		
		List<Rating> ratingsWithCode = ratingRepository.findByRatingCode(ratingCode);
		int ratingsLeft = competition.getMaxRatingPerCode() - (!CollectionUtils.isEmpty(ratingsWithCode)? ratingsWithCode.size() : 0);
		if (ratingsLeft < 1) {
			ratingCode.setExpired(Boolean.TRUE);
			ratingCodeRepository.save(ratingCode);
			dto.setResponseStatus(ResponseStatus.ERROR_RATING_CODE_EXPIRED);
			dto.setRatingsLeft(0);
			return dto;
		}
	
		if (!CollectionUtils.isEmpty(ratingsWithCode) && Boolean.TRUE.equals(competition.getDistinctRating())) {
			for (Rating rating : ratingsWithCode) {
				if (ratingItem.equals(rating.getRatingItem())) {
					dto.setResponseStatus(ResponseStatus.ERROR_DISTINCT_RATING);
					return dto;
				}
			}
		}
		
		Rating newRating = new Rating(ratingItem, ratingCode, value);
		ratingRepository.save(newRating);
		dto = convertToDto(newRating);
		dto.setRatingsLeft(--ratingsLeft);
		dto.setResponseStatus(ResponseStatus.OK);
		return dto;
	}

	public int getRatingsLeft(RatingCode ratingCode) {
		if (ratingCode == null || ratingCode.getCompetition() == null) {
			return 0;
		}
		List<Rating> ratingsWithCode = ratingRepository.findByRatingCode(ratingCode);
		return ratingCode.getCompetition().getMaxRatingPerCode() - (!CollectionUtils.isEmpty(ratingsWithCode)? ratingsWithCode.size() : 0);
		
	}

}
