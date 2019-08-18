package de.heimbrauconvention.votingservice.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import de.heimbrauconvention.votingservice.domain.Competition;


public interface CompetitionRepository extends CrudRepository<Competition, Long> {
	
	Optional<Competition> findByPublicId(String publicId);


}	