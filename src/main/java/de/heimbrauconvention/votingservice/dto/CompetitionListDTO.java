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
public class CompetitionListDTO extends AbstractDTO {

	
	List<CompetitionDTO> competitions = new ArrayList<>();
	
	public CompetitionListDTO() {
		super();
		setCreationTime(new Date());
	}

}
