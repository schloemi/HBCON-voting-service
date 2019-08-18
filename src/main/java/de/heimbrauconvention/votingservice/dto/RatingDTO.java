package de.heimbrauconvention.votingservice.dto;

public class RatingDTO extends AbstractEntityDTO {

	private RatingCodeDTO ratingCodeDTO;
	private RatingItemDTO ratingItemDTO;
	private Integer ratingsLeft;

	public RatingDTO() {

	}

	public RatingCodeDTO getRatingCodeDTO() {
		return ratingCodeDTO;
	}

	public void setRatingCodeDTO(RatingCodeDTO ratingCodeDTO) {
		this.ratingCodeDTO = ratingCodeDTO;
	}

	public RatingItemDTO getRatingItemDTO() {
		return ratingItemDTO;
	}

	public void setRatingItemDTO(RatingItemDTO ratingItemDTO) {
		this.ratingItemDTO = ratingItemDTO;
	}

	public Integer getRatingsLeft() {
		return ratingsLeft;
	}

	public void setRatingsLeft(Integer ratingsLeft) {
		this.ratingsLeft = ratingsLeft;
	}

}
