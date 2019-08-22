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
	
	List<Rating> findByRatingCodeAndRatingItem(RatingCode ratingCode, RatingItem ratingItem);
	
	@Query(value = "SELECT ri.public_id, score.amount, ri.title from rating_item ri " + 
			"		LEFT JOIN (" + 
			"			SELECT rating_item_id, SUM(value) as amount" + 
			"			FROM rating            " + 
			"			GROUP BY rating_item_id) score On score.rating_item_id = ri.id" + 
			"		WHERE competition_id = ?1 and ri.is_active = 1" + 
			"   	ORDER BY score.amount DESC;"
			, nativeQuery = true)
	List<Object[]> statistics(long competitionId);
	
	@Query(value = "SELECT ri.public_id, score.amount from rating_item ri " + 
			"		LEFT JOIN (" + 
			"			SELECT rating_item_id, SUM(value) as amount" + 
			"			FROM rating            " + 
			"			GROUP BY rating_item_id) score On score.rating_item_id = ri.id" + 
			"		WHERE competition_id = ?1 AND " + 
			"       rating_item_id = ?2 AND " +
			"       ri.is_active = 1;"
			, nativeQuery = true)
	List<Object[]> statistics(long competitionId, long ratingItemId);
	

	@Query(value = "SELECT  " + 
			"   		c.id,  " + 
			"			CASE WHEN innerJoin.ratings IS NULL THEN 0 ELSE innerJoin.ratings END AS ratings, " + 
			"			c.max_rating_per_code - CASE WHEN innerJoin.ratings IS NULL THEN 0 ELSE innerJoin.ratings END AS ratings_left " + 
			"		FROM competition c " + 
			"		LEFT JOIN (" + 
			"			SELECT comp.id AS competition_id, count(*) AS ratings " + 
			"			FROM rating_code rc " + 
			"			JOIN rating r ON r.rating_code_id = rc.id  " + 
			"			JOIN rating_item item ON item.id = r.rating_item_id " + 
			"			JOIN competition comp ON item.competition_id = comp.id " + 
			"			WHERE r.rating_code_id = ?1 " + 
			"			GROUP BY rc.id, comp.id) innerJoin ON c.id = innerJoin.competition_id " + 
			"		WHERE c.is_active <> 0 " + 
			"       AND   c.id IN (SELECT competition_id FROM rating_code_2_competition WHERE rating_code_id = ?1)"
			, nativeQuery = true)
	List<Object[]> getCompetitionRatingsForRatingCode(long ratingCodeId);

	
}	