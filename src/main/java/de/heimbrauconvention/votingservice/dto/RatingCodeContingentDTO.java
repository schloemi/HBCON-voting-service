package de.heimbrauconvention.votingservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RatingCodeContingentDTO {

	CompetitionDTO competitionDTO;
	Integer ratings;
	Integer ratinngsLeft;
	
	public RatingCodeContingentDTO() {
		super();
	}

	public RatingCodeContingentDTO(CompetitionDTO competitionDTO, Integer ratings, Integer ratinngsLeft) {
		super();
		this.competitionDTO = competitionDTO;
		this.ratings = ratings;
		this.ratinngsLeft = ratinngsLeft;
	}
	
}
