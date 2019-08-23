package de.heimbrauconvention.votingservice.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Entity
@Table(name = "competition")
public class Competition extends AbstractEntity {

	public static final Float		MAX_RATING_SCORE = 1.0F;
	public static final Integer 	MAX_RATING_PER_CODE = 5;
	
	@Column(length = 255)
	private String icon;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "start_date")
	private Date startDate;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "end_dare")
	private Date endDate;

	@Column(length = 255)
	private String title;

	@Column(length = 4000)
	private String description;

	@Column(length = 255)
	private String url;

	@Column(length = 255)
	private String email;

	@Column(name = "is_active")
	private Boolean isActive = Boolean.FALSE;

	@Column(name = "distinct_rating")
	private Boolean distinctRating = Boolean.FALSE;

	@Column(name = "max_rating_score")
	private Float maxRatingScore = MAX_RATING_SCORE;

	@Column(name = "max_rating_per_code")
	private Integer maxRatingPerCode = MAX_RATING_PER_CODE;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "competition_id")
	@OrderBy(value = "publicId")
	private Set<RatingItem> ratingItems = new HashSet<RatingItem>();

	@ManyToMany(mappedBy = "competitions", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<RatingCode> ratingCodes = new HashSet<RatingCode>();

	public Competition() {
		super();
	}

}
