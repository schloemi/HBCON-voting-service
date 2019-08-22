package de.heimbrauconvention.votingservice.service;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import de.heimbrauconvention.votingservice.domain.RatingCode;
import de.heimbrauconvention.votingservice.dto.CompetitionDTO;
import de.heimbrauconvention.votingservice.dto.RatingCodeContingentDTO;
import de.heimbrauconvention.votingservice.dto.RatingCodeDTO;
import de.heimbrauconvention.votingservice.dto.ResponseStatus;
import de.heimbrauconvention.votingservice.dto.ValidationDTO;
import de.heimbrauconvention.votingservice.repository.RatingCodeRepository;
import de.heimbrauconvention.votingservice.repository.RatingRepository;

@Transactional
@Service
public class RatingCodeService extends AbstractEntityService<RatingCode, RatingCodeDTO, CrudRepository<RatingCode,Long>>{

	@Autowired
	RatingCodeRepository repository;
	
	@Autowired
	RatingRepository ratingRepository;
	
	@Autowired
	CompetitionService competitionService;
	
	
	@Override
	public RatingCodeDTO convertToDto(RatingCode pItem) {
		return (pItem == null)? null : modelMapper.map(pItem, RatingCodeDTO.class);
	}


	public ValidationDTO<RatingCode> validate(String codeId, ValidationDTO<RatingCode> dto) {

		dto.setResponseStatus(ResponseStatus.OK);

		if (StringUtils.isEmpty(codeId)) {
			dto.setResponseStatus(ResponseStatus.ERROR_READING_RATING_CODE);
			return dto;
		}
		
		dto.setEntity(repository.findByPublicId(codeId).orElse(null));
		if (dto.getEntity() == null) {
			dto.setResponseStatus(ResponseStatus.ERROR_NOT_FOUND_RATING_CODE);
		} 
		else if (!Boolean.TRUE.equals(dto.getEntity().getIsActive())) {
			dto.setResponseStatus(ResponseStatus.ERROR_RATING_CODE_NOT_ACTIVE);
		} 
		return dto;
	}


	public RatingCodeDTO getDTO(String publicId) {
		RatingCodeDTO dto = new RatingCodeDTO();
		ValidationDTO<RatingCode> validationDTO = new ValidationDTO<>();
		validate(publicId, validationDTO);

		if(!ResponseStatus.OK.equals(validationDTO.getResponseStatus())) {
			dto.setResponseStatus(validationDTO.getResponseStatus());
			return dto;
		}
		
		dto = modelMapper.map(validationDTO.getEntity(), RatingCodeDTO.class);
		dto.setEntity(validationDTO.getEntity());
		dto.setResponseStatus(ResponseStatus.OK);
		
		addRatingCodeContingent(dto);
		
		return dto;
	}


	public void addRatingCodeContingent(RatingCodeDTO dto) {
		List<Object[]> competitionRatings = ratingRepository.getCompetitionRatingsForRatingCode(dto.getEntity().getId());
		dto.getRatingCodeContingent().clear();
		if (!CollectionUtils.isEmpty(competitionRatings)) {
			for (Object[] competitionRating : competitionRatings) {
				CompetitionDTO competitionDTO = competitionService.convertToDto(competitionService.getById(((BigInteger)competitionRating[0]).longValue()));
				
				RatingCodeContingentDTO contingentDTO = new RatingCodeContingentDTO(
						competitionDTO,
						((BigInteger)competitionRating[1]).intValue(), 
						((BigInteger)competitionRating[2]).intValue());
				
				dto.getRatingCodeContingent().add(contingentDTO);
			}
		}
	}

	
}
