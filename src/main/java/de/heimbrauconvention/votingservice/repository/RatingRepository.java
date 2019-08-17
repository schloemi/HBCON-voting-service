package de.heimbrauconvention.votingservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.heimbrauconvention.votingservice.domain.Rating;
import de.heimbrauconvention.votingservice.domain.RatingCode;
import de.heimbrauconvention.votingservice.domain.RatingItem;


public interface RatingRepository extends CrudRepository<Rating, Long> {

	List<Rating> findByRatingCode(RatingCode ratingCode);
	
	List<Rating> findByRatingItem(RatingItem ratingItem);
	
	
	@Query(value = "SELECT ri.public_id, score.amount from rating_item ri " + 
			"	LEFT JOIN (" + 
			"		SELECT rating_item_id, SUM(value) as amount" + 
			"		FROM rating            " + 
			"		GROUP BY rating_item_id) score On score.rating_item_id = ri.id" + 
			"	WHERE competition_id = ?1 and ri.is_active = 1;"
			, nativeQuery = true)
	List<Object[]> statistics(long competitionId);
	
	@Query(value = "SELECT ri.public_id, score.amount from rating_item ri " + 
			"	LEFT JOIN (" + 
			"		SELECT rating_item_id, SUM(value) as amount" + 
			"		FROM rating            " + 
			"		GROUP BY rating_item_id) score On score.rating_item_id = ri.id" + 
			"	WHERE competition_id = ?1 AND " + 
			"         rating_item_id = ?2 AND " +
			"         ri.is_active = 1;"
			, nativeQuery = true)
	List<Object[]> statistics(long competitionId, long ratingItemId);
	
	
}	