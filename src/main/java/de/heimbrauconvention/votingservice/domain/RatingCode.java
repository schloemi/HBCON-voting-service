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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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

}
