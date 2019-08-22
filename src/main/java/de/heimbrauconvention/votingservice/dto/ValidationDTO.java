package de.heimbrauconvention.votingservice.dto;

import de.heimbrauconvention.votingservice.domain.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ValidationDTO<T extends AbstractEntity> extends AbstractDTO {

	private T entity;
	
	public ValidationDTO() {
	
	}

}
