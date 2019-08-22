package de.heimbrauconvention.votingservice.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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

	public ValidationDTO<RatingItem> validate(String publicId, ValidationDTO<RatingItem> dto) {

		dto.setResponseStatus(ResponseStatus.OK);

		if (StringUtils.isEmpty(publicId)) {
			dto.setResponseStatus(ResponseStatus.ERROR_READING_RATING_ITEM);
			return dto;
		}
		
		dto.setEntity(ratingItemRepository.findByPublicId(publicId).orElse(null)) ;
		if (dto.getEntity() == null) {
			dto.setResponseStatus(ResponseStatus.ERROR_NOT_FOUND_RATING_ITEM);
			return dto;
		}

		if (!Boolean.TRUE.equals(dto.getEntity().getIsActive())) {
			dto.setResponseStatus(ResponseStatus.ERROR_NOT_ACTIVE_RATING_ITEM);
			return dto;
		}
		
		return dto;
	}

	public RatingItemDTO getDTO(String publicId, boolean withwScore) {
		RatingItemDTO dto = new RatingItemDTO();
		ValidationDTO<RatingItem> validationDTO = new ValidationDTO<>();
		validate(publicId, validationDTO);
		
		if(!ResponseStatus.OK.equals(validationDTO.getResponseStatus())) {
			dto.setResponseStatus(validationDTO.getResponseStatus());
			return dto;
		}
		
		dto = modelMapper.map(validationDTO.getEntity(), RatingItemDTO.class);
		dto.setEntity(validationDTO.getEntity());
		dto.setCompetitionDTO(competitionService.convertToDto(dto.getEntity().getCompetition()));
		if (withwScore) {
			List<Object[]> statistics = ratingRepository.statistics(dto.getEntity().getCompetition().getId(), dto.getEntity().getId());
			if (!CollectionUtils.isEmpty(statistics)) {
				Object[] obj = statistics.get(0);
				if (obj[1] != null) {
					dto.setScore(((Double) obj[1]).intValue());
				}
			}
		}
		
		dto.setResponseStatus(ResponseStatus.OK);
		return dto;
	}
	
	public RatingItemDTO getDTO(String publicId) {
		return getDTO(publicId, Boolean.TRUE);
	}

	
}
