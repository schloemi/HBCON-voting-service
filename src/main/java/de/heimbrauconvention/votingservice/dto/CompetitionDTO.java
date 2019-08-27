package de.heimbrauconvention.votingservice.dto;

import java.util.Date;

import de.heimbrauconvention.votingservice.domain.Competition;
import de.heimbrauconvention.votingservice.domain.RatingStrategie;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class CompetitionDTO extends AbstractEntityDTO<Competition> {

	private String title;
	private String description;
	private String url;
	private String email;
	private Date startDate;
	private Date endDate;
	private String icon;
	private String location;
	private RatingStrategie ratingStrategie;
	

	public CompetitionDTO() {
		super();
	}

}
