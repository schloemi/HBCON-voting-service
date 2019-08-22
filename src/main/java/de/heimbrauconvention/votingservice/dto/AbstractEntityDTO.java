package de.heimbrauconvention.votingservice.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.heimbrauconvention.votingservice.domain.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public abstract class AbstractEntityDTO<T extends AbstractEntity> {

	private String publicId;
	private Date creationTime;
	private Date modificationTime;
	private Boolean isActive = Boolean.TRUE;
	private ResponseStatus responseStatus;
	
	@JsonIgnore
	private T entity;
	
	public AbstractEntityDTO() {
		super();
	}
	
}
