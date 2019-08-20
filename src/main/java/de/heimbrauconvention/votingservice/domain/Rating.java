package de.heimbrauconvention.votingservice.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rating")
public class Rating extends AbstractEntity {

	public static final Float DEFAULT_VALUE = 1.0F;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "rating_item_id")
	RatingItem ratingItem;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "rating_code_id")
	RatingCode ratingCode;
	
	Float value =  DEFAULT_VALUE;
	
	public Rating() {
		super();
	}
	
	public Rating(@NotNull RatingItem ratingItem, @NotNull RatingCode ratingCode) {
		this(ratingItem, ratingCode, DEFAULT_VALUE);
	}
	
	public Rating(@NotNull RatingItem ratingItem, @NotNull RatingCode ratingCode, Float value) {
		this.ratingItem = ratingItem;
		this.ratingCode = ratingCode;
		this.value = (value != null && value > 0.0F )? value : DEFAULT_VALUE;
	}

	public RatingItem getRatingItem() {
		return ratingItem;
	}

	public RatingCode getRatingCode() {
		return ratingCode;
	}
	

}
