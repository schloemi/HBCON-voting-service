package de.heimbrauconvention.votingservice.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "competition")
public class Competition extends AbstractEntity {

	public static final Integer MAX_RATING_SCORE = 1;
	public static final Integer MAX_RATING_PER_CODE = 5;

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
	private Integer maxRatingScore;

	@Column(name = "max_rating_score_per_code")
	private Integer maxRatingPerCode;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "competition_id")
	private Set<RatingItem> ratingItems;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "competition_id")
	private Set<RatingCode> ratingCodes;

	public Competition() {
		super();
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getDistinctRating() {
		return distinctRating;
	}

	public void setDistinctRating(Boolean distinctRating) {
		this.distinctRating = distinctRating;
	}

	public Integer getMaxRatingScore() {
		if (maxRatingScore == null) {
			maxRatingScore = MAX_RATING_SCORE;
		}
		return maxRatingScore;
	}

	public void setMaxRatingScore(Integer maxRatingScore) {
		this.maxRatingScore = maxRatingScore;
	}

	public Integer getMaxRatingPerCode() {
		if (maxRatingPerCode == null) {
			maxRatingPerCode = MAX_RATING_PER_CODE;
		}
		return maxRatingPerCode;
	}

	public void setMaxRatingPerCode(Integer maxRatingPerCode) {
		this.maxRatingPerCode = maxRatingPerCode;
	}

	public Set<RatingCode> getRatingCodes() {
		if (ratingCodes == null) {
			ratingCodes = new HashSet<RatingCode>();
		}
		return ratingCodes;
	}

	public void setRatingCodes(Set<RatingCode> ratingCodes) {
		this.ratingCodes = ratingCodes;
	}

	public Set<RatingItem> getRatingItems() {
		if (ratingItems == null) {
			ratingItems = new HashSet<RatingItem>();
		}
		return ratingItems;
	}

	public void setRatingItems(Set<RatingItem> ratingItems) {
		this.ratingItems = ratingItems;
	}

}
