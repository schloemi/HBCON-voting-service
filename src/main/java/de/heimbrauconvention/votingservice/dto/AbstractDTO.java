package de.heimbrauconvention.votingservice.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class AbstractDTO {

	private Date creationTime;
	private ResponseStatus responseStatus;

	public AbstractDTO() {
		super();
	}
	
}
