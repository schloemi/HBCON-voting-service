package de.heimbrauconvention.votingservice.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import de.heimbrauconvention.votingservice.domain.Competition;
import de.heimbrauconvention.votingservice.domain.RatingCode;
import de.heimbrauconvention.votingservice.domain.RatingItem;
import de.heimbrauconvention.votingservice.dto.CompetitionDTO;
import de.heimbrauconvention.votingservice.dto.RatingItemDTO;
import de.heimbrauconvention.votingservice.dto.RatingScoreDTO;
import de.heimbrauconvention.votingservice.dto.ResponseStatus;
import de.heimbrauconvention.votingservice.dto.StatisticDTO;
import de.heimbrauconvention.votingservice.dto.ValidationDTO;
import de.heimbrauconvention.votingservice.repository.RatingCodeRepository;
import de.heimbrauconvention.votingservice.repository.RatingItemRepository;
import de.heimbrauconvention.votingservice.repository.RatingRepository;

@Transactional
@Service
public class CompetitionService extends AbstractEntityService<Competition, CompetitionDTO, CrudRepository<Competition,Long>>{

	@Autowired
	RatingCodeRepository ratingCodeRepository;
	
	@Autowired
	RatingItemRepository ratingItemRepository;
	
	
	@Autowired
	RatingRepository ratingRepository;
	
	
	
	public CompetitionService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public CompetitionDTO convertToDto(Competition competition) {
		CompetitionDTO dto = new CompetitionDTO();
		if (competition == null) {
			dto.setResponseStatus(ResponseStatus.ERROR_NOT_FOUND_COMPETITION);
		} else {
			dto =  modelMapper.map(competition, CompetitionDTO.class);
			dto.setResponseStatus(ResponseStatus.OK);
		}
		return dto;
	}

	public StatisticDTO getStatistic(Long competitionId) {
		StatisticDTO dto = new StatisticDTO();
		
		Competition competition = this.getById(competitionId);
		if (competition == null) {
			dto.setResponseStatus(ResponseStatus.ERROR_NOT_FOUND_COMPETITION);
			return dto;
		}
		
		List<Object[]> statistics = ratingRepository.statistics(competitionId);
		if (!CollectionUtils.isEmpty(statistics)) {
			for (Object[] objects : statistics) {
				if (objects[0] == null) {
					continue;
				}
				String ratingItemId = (String) objects[0];
				Integer amount = (objects[1] == null)? 0 : ((Double) objects[1]).intValue();
				RatingScoreDTO score = new RatingScoreDTO(ratingItemId, amount);
				dto.getScores().add(score);
			}
		}
		dto.setResponseStatus(ResponseStatus.OK);
		return dto;
	}
	
	public ResponseStatus validateCompetition(Competition competition) {
		if (competition == null) {
			return ResponseStatus.ERROR_NOT_FOUND_COMPETITION;
		}
		
		if (!Boolean.TRUE.equals(competition.getIsActive())) {
			return ResponseStatus.ERROR_NOT_ACTIVE_COMPETITION;
		}
		
		Date now = new Date();
		if (competition.getEndDate() != null && now.after(competition.getEndDate())) {
			return ResponseStatus.ERROR_COMPETITION_HAS_ENDED;
		}
		
		if (competition.getStartDate() != null && now.before(competition.getEndDate())) {
			return ResponseStatus.ERROR_COMPETITION_HAS_NOT_BEGUN;
		}
		
		
		return ResponseStatus.OK;
	}
	
	
	public ValidationDTO validateCompetition(Long competitionId) {
		ValidationDTO dto = new ValidationDTO();
		
		Competition competition = this.getById(competitionId);
		
		ResponseStatus competitionStatus = validateCompetition(competition);
		if (!ResponseStatus.OK.equals(competitionStatus)) {
			dto.setResponseStatus(competitionStatus);
			return dto;
		} else {
			dto.setResponseStatus(ResponseStatus.OK);
		}
		return dto;
	}

	public ValidationDTO validateCode(Long competitionId, String codeId) {
		ValidationDTO dto = new ValidationDTO();
		
		Competition competition = this.getById(competitionId);
		
		ResponseStatus competitionStatus = validateCompetition(competition);
		if (!ResponseStatus.OK.equals(competitionStatus)) {
			dto.setResponseStatus(competitionStatus);
			return dto;
		}
		
		RatingCode ratingCode = ratingCodeRepository.findByPublicId(codeId).orElse(null) ;
		if (ratingCode == null) {
			dto.setResponseStatus(ResponseStatus.ERROR_NOT_FOUND_RATING_CODE);
			return dto;
		}

		if (Boolean.TRUE.equals(ratingCode.getExpired())) {
			dto.setResponseStatus(ResponseStatus.ERROR_RATING_CODE_EXPIRED);
			return dto;
		}
		
		if (!competition.equals(ratingCode.getCompetition())) {
			dto.setResponseStatus(ResponseStatus.ERROR_MATCH_COMPETION_RATING_CODE);
			return dto;
		}
		
		dto.setResponseStatus(ResponseStatus.OK);
		return dto;
	}

	public ValidationDTO validateItem(Long competitionId, String itemId) {
		
		ValidationDTO dto = new ValidationDTO();
		
		Competition competition = this.getById(competitionId);
		
		ResponseStatus competitionStatus = validateCompetition(competition);
		if (!ResponseStatus.OK.equals(competitionStatus)) {
			dto.setResponseStatus(competitionStatus);
			return dto;
		}
		
		RatingItem ratingItem = ratingItemRepository.findByPublicId(itemId).orElse(null) ;
		if (ratingItem == null) {
			dto.setResponseStatus(ResponseStatus.ERROR_NOT_FOUND_RATING_ITEM);
			return dto;
		}

		if (!Boolean.TRUE.equals(ratingItem.getIsActive())) {
			dto.setResponseStatus(ResponseStatus.ERROR_NOT_ACTIVE_RATING_ITEM);
			return dto;
		}
		
		if (!competition.equals(ratingItem.getCompetition())) {
			dto.setResponseStatus(ResponseStatus.ERROR_MATCH_COMPETION_RATING_ITEM);
			return dto;
		}
		
		dto.setResponseStatus(ResponseStatus.OK);
		return dto;
		
	}

	public RatingItemDTO getRatingItemWithScore(Long competitionId, String itemId) {
		
		RatingItemDTO dto = new RatingItemDTO();
		ValidationDTO validationDTO = validateItem(competitionId, itemId);

		if(!ResponseStatus.OK.equals(validationDTO.getResponseStatus())) {
			dto.setResponseStatus(validationDTO.getResponseStatus());
			return dto;
		}
		
		RatingItem ratingItem = ratingItemRepository.findByPublicId(itemId).orElse(null) ;
		if (ratingItem != null) {
			dto =  modelMapper.map(ratingItem, RatingItemDTO.class);
		
			List<Object[]> statistics = ratingRepository.statistics(competitionId, ratingItem.getId());
			if (!CollectionUtils.isEmpty(statistics)) {
				Object[] obj = statistics.get(0);
				
				if (obj[1] != null) {
					dto.setScore(((Double) obj[1]).intValue());
				}
			}
			dto.setResponseStatus(ResponseStatus.OK);
		} else {
			dto.setResponseStatus(ResponseStatus.ERROR_NOT_FOUND_RATING_ITEM);
			
		}

		return dto;
	}

}
