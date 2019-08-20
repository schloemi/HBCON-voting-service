package de.heimbrauconvention.votingservice.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import de.heimbrauconvention.votingservice.domain.Competition;
import de.heimbrauconvention.votingservice.domain.RatingCode;
import de.heimbrauconvention.votingservice.dto.RatingCodeDTO;
import de.heimbrauconvention.votingservice.dto.ResponseStatus;
import de.heimbrauconvention.votingservice.dto.ValidationDTO;
import de.heimbrauconvention.votingservice.repository.RatingCodeRepository;

@Transactional
@Service
public class RatingCodeService extends AbstractEntityService<RatingCode, RatingCodeDTO, CrudRepository<RatingCode,Long>>{

	@Autowired
	RatingCodeRepository repository;
	
	@Autowired
	CompetitionService competitionService;
	
	@Autowired
	RatingService ratingService;
	
	@Override
	public RatingCodeDTO convertToDto(RatingCode pItem) {
		return (pItem == null)? null : modelMapper.map(pItem, RatingCodeDTO.class);
	}


	public ValidationDTO validate(String codeId) {
		ValidationDTO dto = new ValidationDTO();
		
		RatingCode ratingCode = repository.findByPublicId(codeId).orElse(null) ;
		if (ratingCode == null) {
			dto.setResponseStatus(ResponseStatus.ERROR_NOT_FOUND_RATING_CODE);
			return dto;
		}

		if (!Boolean.TRUE.equals(ratingCode.getIsActive())) {
			dto.setResponseStatus(ResponseStatus.ERROR_RATING_CODE_NOT_ACTIVE);
			return dto;
		}
		
		
		/**
		 * 
		 * TODO: refactoring
		 */
		
		/*
		Competition competition = ratingCode.getCompetition();
		
		ResponseStatus competitionStatus = competitionService.validateCompetition(competition);
		if (!ResponseStatus.OK.equals(competitionStatus)) {
			dto.setResponseStatus(competitionStatus);
			return dto;
		}*/
		
		dto.setResponseStatus(ResponseStatus.OK);
		return dto;
	}


	public RatingCodeDTO get(String publicId) {
		RatingCodeDTO dto = new RatingCodeDTO();
		ValidationDTO validationDTO = validate(publicId);

		if(!ResponseStatus.OK.equals(validationDTO.getResponseStatus())) {
			dto.setResponseStatus(validationDTO.getResponseStatus());
			return dto;
		}
		RatingCode ratingCode = repository.findByPublicId(publicId).orElse(null) ;
		if (ratingCode != null) {
			dto =  modelMapper.map(ratingCode, RatingCodeDTO.class);
			dto.setResponseStatus(ResponseStatus.OK);
			
			/**
			 * 
			 * TODO Hier müssen noch die Competitions rein.
			 */
		}
		return dto;
	}

	
}
