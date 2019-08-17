package de.heimbrauconvention.votingservice.dto;

public class RatingDTO extends AbstractEntityDTO {

	private Integer ratingsLeft;

	public RatingDTO() {

	}

	public Integer getRatingsLeft() {
		return ratingsLeft;
	}

	public void setRatingsLeft(Integer ratingsLeft) {
		this.ratingsLeft = ratingsLeft;
	}

}
