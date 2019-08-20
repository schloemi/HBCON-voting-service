package de.heimbrauconvention.votingservice.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "rating_code")
public class RatingCode extends AbstractEntity {

	@Column(name = "is_active")
	private Boolean isActive = Boolean.FALSE;
	
	private Boolean printed = Boolean.FALSE;

	@ManyToMany(cascade = CascadeType.ALL, fetch =  FetchType.LAZY)
	@JoinTable(name = "rating_code_2_competition",
	    joinColumns = @JoinColumn(name = "rating_code_id"),
	    inverseJoinColumns = @JoinColumn(name = "competition_id")
	)
	private Set<Competition> competitions = new HashSet<Competition>();

	@OneToMany(mappedBy = "ratingCode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Rating> ratings = new HashSet<Rating>();

	public RatingCode() {
		super();
	}

	public RatingCode(Set<Competition> competitions) {
		this();
		this.competitions = competitions;
	}

	public Set<Competition> getCompetitions() {
		return competitions;
	}

	public void setCompetitions(Set<Competition> competitions) {
		this.competitions = competitions;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getPrinted() {
		return printed;
	}
	
	public void setPrinted(Boolean printed) {
		this.printed = printed;
	}

}
