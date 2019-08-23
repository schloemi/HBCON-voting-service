package de.heimbrauconvention.votingservice.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class KeyFiguresDTO extends AbstractDTO {

	private Long totalRatings = 0L;
	
	private Long uniqueUser = 0L;
	
	public KeyFiguresDTO() {
		super();
		setCreationTime(new Date());
	}
	
}
