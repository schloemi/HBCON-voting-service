package de.heimbrauconvention.votingservice.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import de.heimbrauconvention.votingservice.domain.Competition;
import de.heimbrauconvention.votingservice.dto.CompetitionDTO;
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
	
	public List<CompetitionDTO> getAllActiveCompetitions() {
		return this.getAllAsList()
			.stream()
			.filter(c -> c.getIsActive())
			.map(c -> convertToDto(c))
			.collect(Collectors.toList());
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
				String title = (objects[2] == null)? "NO_TITLE" :(String) objects[2];
				RatingScoreDTO score = new RatingScoreDTO(ratingItemId, amount, title);
				dto.getScores().add(score);
			}
		}
		dto.setResponseStatus(ResponseStatus.OK);
		return dto;
	}
	
	
	public ValidationDTO<Competition> validate(Long competitionId, ValidationDTO<Competition> dto, boolean checkTimeFrame) {
		
		dto.setResponseStatus(ResponseStatus.OK);
		
		if (StringUtils.isEmpty(competitionId)) {
			dto.setResponseStatus(ResponseStatus.ERROR_READING_COMPETITION);
			return dto;
		}
		
		Competition competition = this.getById(competitionId);
		dto.setEntity(competition);
		if (competition == null) {
			dto.setResponseStatus(ResponseStatus.ERROR_NOT_FOUND_COMPETITION);
			return dto;
		}
		
		if (!Boolean.TRUE.equals(competition.getIsActive())) {
			dto.setResponseStatus(ResponseStatus.ERROR_NOT_ACTIVE_COMPETITION);
		}
		
		if (Boolean.TRUE.equals(checkTimeFrame)) {
			Date now = new Date();
			if (competition.getEndDate() != null && now.after(competition.getEndDate())) {
				dto.setResponseStatus(ResponseStatus.ERROR_COMPETITION_HAS_ENDED);
			}
			
			if (competition.getStartDate() != null && now.before(competition.getStartDate())) {
				dto.setResponseStatus(ResponseStatus.ERROR_COMPETITION_HAS_NOT_BEGUN);
			}
		}
		return dto;
	}
	
	
	public CompetitionDTO getDTO(Long competitionId) {
		CompetitionDTO dto = new CompetitionDTO();
		ValidationDTO<Competition> validationDTO = new ValidationDTO<>();
		validate(competitionId, validationDTO, false);
		
		if(!ResponseStatus.OK.equals(validationDTO.getResponseStatus())) {
			dto.setResponseStatus(validationDTO.getResponseStatus());
			return dto;
		}
		
		dto = modelMapper.map(validationDTO.getEntity(), CompetitionDTO.class);
		dto.setEntity(validationDTO.getEntity());
		dto.setResponseStatus(ResponseStatus.OK);
		return dto;
	}
	

}
