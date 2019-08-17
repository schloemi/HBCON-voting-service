package de.heimbrauconvention.votingservice.repository;

import org.springframework.data.repository.CrudRepository;

import de.heimbrauconvention.votingservice.domain.Competition;


public interface CompetitionRepository extends CrudRepository<Competition, Long> {
	
	

}	