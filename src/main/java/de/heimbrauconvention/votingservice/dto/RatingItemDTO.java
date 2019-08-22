package de.heimbrauconvention.votingservice.dto;

import de.heimbrauconvention.votingservice.domain.RatingItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RatingItemDTO extends AbstractEntityDTO<RatingItem> {

	private String title;
	private String description;
	private String url;
	private String email;
	private Boolean isActive;
	private Integer score;
	private CompetitionDTO competitionDTO;
	
	public RatingItemDTO() {
		super();
	}

}
