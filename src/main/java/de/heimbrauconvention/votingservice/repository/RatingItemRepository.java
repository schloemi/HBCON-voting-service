package de.heimbrauconvention.votingservice.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import de.heimbrauconvention.votingservice.domain.RatingItem;


public interface RatingItemRepository extends CrudRepository<RatingItem, Long> {
	
	Optional<RatingItem> findByPublicId(String publicId);
	
}	