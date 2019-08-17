package de.heimbrauconvention.votingservice.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import de.heimbrauconvention.votingservice.domain.RatingCode;


public interface RatingCodeRepository extends CrudRepository<RatingCode, Long> {
	
	Optional<RatingCode> findByPublicId(String publicId);
	

}	