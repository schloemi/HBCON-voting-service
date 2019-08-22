package de.heimbrauconvention.votingservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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

}