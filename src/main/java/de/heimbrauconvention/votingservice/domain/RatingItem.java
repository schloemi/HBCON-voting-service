package de.heimbrauconvention.votingservice.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rating_item")
public class RatingItem extends AbstractEntity {

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

	@NotNull
	@ManyToOne
	private Competition competition;

	@OneToMany(mappedBy = "ratingItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Rating> ratings = new HashSet<Rating>();

	public RatingItem() {
		super();
	}

	public RatingItem(@NotNull Competition competition) {
		this.competition = competition;
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

	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

}
