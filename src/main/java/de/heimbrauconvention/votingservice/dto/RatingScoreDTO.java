package de.heimbrauconvention.votingservice.dto;

public class RatingScoreDTO {

	private String publicId;
	private Integer score;
	private String title;

	
	public RatingScoreDTO(String publicId, Integer score, String title) {
		super();
		this.publicId = publicId;
		this.score = score;
		this.title = title;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	

}