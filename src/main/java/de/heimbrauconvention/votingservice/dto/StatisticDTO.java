package de.heimbrauconvention.votingservice.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class StatisticDTO extends AbstractDTO {

	private List<RatingScoreDTO> scores = new ArrayList<RatingScoreDTO>();
	
	public StatisticDTO() {
		super();
		setCreationTime(new Date());
	}
	
}
