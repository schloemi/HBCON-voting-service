package de.heimbrauconvention.votingservice.dto;

public class RatingCodeDTO extends AbstractEntityDTO {

	private Boolean expired;
	
	private Integer ratingsLeft = 0;
	
	public RatingCodeDTO() {

	}

	public Boolean getExpired() {
		return expired;
	}

	public void setExpired(Boolean expired) {
		this.expired = expired;
	}

	public Integer getRatingsLeft() {
		return ratingsLeft;
	}

	public void setRatingsLeft(Integer ratingsLeft) {
		this.ratingsLeft = ratingsLeft;
	}

	
}
