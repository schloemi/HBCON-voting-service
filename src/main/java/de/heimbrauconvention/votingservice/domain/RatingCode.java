package de.heimbrauconvention.votingservice.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rating_code")
public class RatingCode extends AbstractEntity {

	private Boolean expired;;

	private Boolean printed;

	@NotNull
	@ManyToOne
	private Competition competition;

	@OneToMany(mappedBy = "ratingCode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Rating> ratings;

	public RatingCode() {
		super();
	}

	public RatingCode(@NotNull Competition competition) {
		this();
		this.competition = competition;
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

	public Boolean getExpired() {
		if (expired == null) {
			expired = Boolean.FALSE;
		}
		return expired;
	}

	public void setExpired(Boolean expired) {
		this.expired = expired;
	}

	public Boolean getPrinted() {
		if (printed == null) {
			printed = Boolean.FALSE;
		}
		return printed;
	}

	public void setPrinted(Boolean printed) {
		this.printed = printed;
	}

}
