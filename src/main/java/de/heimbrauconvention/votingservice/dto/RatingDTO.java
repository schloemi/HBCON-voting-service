package de.heimbrauconvention.votingservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.heimbrauconvention.votingservice.domain.Rating;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RatingDTO extends AbstractEntityDTO<Rating> {

	private RatingCodeDTO ratingCodeDTO;
	private RatingItemDTO ratingItemDTO;

	@JsonIgnore
	private CompetitionDTO competitionDTO;
	
	public RatingDTO() {
		super();
	}

}
