package de.heimbrauconvention.votingservice.dto;

public class RatingScoreDTO {

	private String publicId;
	Integer score;

	public RatingScoreDTO(String publicId, Integer score) {
		super();
		this.publicId = publicId;
		this.score = score;
	}

	public String getPublicId() {
		return publicId;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}