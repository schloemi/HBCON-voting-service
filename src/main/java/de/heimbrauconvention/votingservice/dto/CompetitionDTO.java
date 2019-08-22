package de.heimbrauconvention.votingservice.dto;

import java.util.Date;

import de.heimbrauconvention.votingservice.domain.Competition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class CompetitionDTO extends AbstractEntityDTO<Competition> {

	private Long id;
	private String title;
	private String description;
	private String url;
	private String email;
	private Date startDate;
	private Date endDate;

	public CompetitionDTO() {
		super();
	}

}
