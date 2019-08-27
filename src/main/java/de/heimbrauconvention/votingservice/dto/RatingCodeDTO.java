package de.heimbrauconvention.votingservice.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OrderBy;

import de.heimbrauconvention.votingservice.domain.RatingCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RatingCodeDTO extends AbstractEntityDTO<RatingCode> {

	@OrderBy("competitionDTO.startDate")
	List<RatingCodeContingentDTO> ratingCodeContingent = new ArrayList<>();
	
	public RatingCodeDTO() {
		super();
	}
	
}
