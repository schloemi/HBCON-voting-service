package de.heimbrauconvention.votingservice.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import de.heimbrauconvention.votingservice.domain.Competition;
import de.heimbrauconvention.votingservice.domain.RatingItem;
import de.heimbrauconvention.votingservice.dto.RatingItemDTO;
import de.heimbrauconvention.votingservice.dto.ResponseStatus;
import de.heimbrauconvention.votingservice.dto.ValidationDTO;
import de.heimbrauconvention.votingservice.repository.RatingItemRepository;
import de.heimbrauconvention.votingservice.repository.RatingRepository;

@Transactional
@Service
public class RatingItemService extends AbstractEntityService<RatingItem, RatingItemDTO, CrudRepository<RatingItem,Long>>{

	@Autowired
	RatingItemRepository ratingItemRepository;
	
	@Autowired
	RatingRepository ratingRepository;
	
	@Autowired
	CompetitionService competitionService;
	
	
	@Override
	public RatingItemDTO convertToDto(RatingItem pItem) {
		return (pItem == null)? null : modelMapper.map(pItem, RatingItemDTO.class);
	}


	public ValidationDTO validate(String publicId) {
		ValidationDTO dto = new ValidationDTO();
		
		
		RatingItem ratingItem = ratingItemRepository.findByPublicId(publicId).orElse(null) ;
		if (ratingItem == null) {
			dto.setResponseStatus(ResponseStatus.ERROR_NOT_FOUND_RATING_ITEM);
			return dto;
		}

		if (!Boolean.TRUE.equals(ratingItem.getIsActive())) {
			dto.setResponseStatus(ResponseStatus.ERROR_NOT_ACTIVE_RATING_ITEM);
			return dto;
		}
		
		Competition competition = ratingItem.getCompetition();
		
		ResponseStatus competitionStatus = competitionService.validateCompetition(competition);
		if (!ResponseStatus.OK.equals(competitionStatus)) {
			dto.setResponseStatus(competitionStatus);
			return dto;
		}
		
		dto.setResponseStatus(ResponseStatus.OK);
		return dto;
		
	}
	
	public RatingItemDTO getRatingItem(String publicId) {
		return getRatingItem(publicId, false);
	}
	
	public RatingItemDTO getRatingItem(String publicId, boolean withwScore) {
		
		RatingItemDTO dto = new RatingItemDTO();
		ValidationDTO validationDTO = validate(publicId);

		if(!ResponseStatus.OK.equals(validationDTO.getResponseStatus())) {
			dto.setResponseStatus(validationDTO.getResponseStatus());
			return dto;
		}
		
		RatingItem ratingItem = ratingItemRepository.findByPublicId(publicId).orElse(null) ;
		if (ratingItem != null) {
			dto =  modelMapper.map(ratingItem, RatingItemDTO.class);
			dto.setResponseStatus(ResponseStatus.OK);
			if(withwScore) {
				List<Object[]> statistics = ratingRepository.statistics(ratingItem.getCompetition().getId(), ratingItem.getId());
				if (!CollectionUtils.isEmpty(statistics)) {
					Object[] obj = statistics.get(0);
					
					if (obj[1] != null) {
						dto.setScore(((Double) obj[1]).intValue());
					}
				}
			}
			
		} else {
			dto.setResponseStatus(ResponseStatus.ERROR_NOT_FOUND_RATING_ITEM);
		}
		return dto;
	}

	
}
