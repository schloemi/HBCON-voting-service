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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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

}
