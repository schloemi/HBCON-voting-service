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
	Integer ratingsLeft;
	
	public RatingCodeContingentDTO() {
		super();
	}

	public RatingCodeContingentDTO(CompetitionDTO competitionDTO, Integer ratings, Integer ratingsLeft) {
		super();
		this.competitionDTO = competitionDTO;
		this.ratings = ratings;
		this.ratingsLeft = ratingsLeft;
	}
	
}
