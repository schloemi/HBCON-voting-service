package de.heimbrauconvention.votingservice.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatisticDTO extends AbstractDTO{

	private List<RatingScoreDTO> scores;
	
	public StatisticDTO() {
		setCreationTime(new Date());
	}
	
	public List<RatingScoreDTO> getScores() {
		if (scores == null) {
			scores = new ArrayList<RatingScoreDTO>();
		}
		return scores;
	}

	public void setScores(List<RatingScoreDTO> scores) {
		this.scores = scores;
	}
	
}
