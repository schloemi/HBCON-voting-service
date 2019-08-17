package de.heimbrauconvention.votingservice.dto;

import java.util.Date;

public abstract class AbstractDTO {

	private Date creationTime;

	private ResponseStatus responseStatus;

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}
	
}
