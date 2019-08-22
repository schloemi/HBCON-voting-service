package de.heimbrauconvention.votingservice.dto;

import java.util.HashSet;
import java.util.Set;

import de.heimbrauconvention.votingservice.domain.RatingCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RatingCodeDTO extends AbstractEntityDTO<RatingCode> {

	Set<RatingCodeContingentDTO> ratingCodeContingent = new HashSet<>();
	
	public RatingCodeDTO() {
		super();
	}
	
}
