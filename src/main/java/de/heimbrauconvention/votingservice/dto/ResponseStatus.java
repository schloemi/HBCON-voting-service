package de.heimbrauconvention.votingservice.dto;

public enum ResponseStatus {
	OK,
 	ERROR_READING_RATING_CODE,
 	ERROR_READING_RATING_ITEM,
 	ERROR_READING_COMPETITION,
 	ERROR_MATCH_COMPETION_RATING_CODE,
 	ERROR_MATCH_COMPETION_RATING_ITEM,
 	ERROR_MATCH_RATING_CODE_ITEM,
 	ERROR_NOT_FOUND_RATING_CODE,
 	ERROR_NOT_FOUND_RATING_ITEM,
 	ERROR_NOT_FOUND_COMPETITION,
 	ERROR_RATING_CODE_NOT_ACTIVE,
 	ERROR_NOT_ACTIVE_COMPETITION,
 	ERROR_NOT_ACTIVE_RATING_ITEM,
 	ERROR_NOT_ACTIVE_RATING_CODE, 
 	ERROR_COMPETITION_HAS_ENDED,
 	ERROR_COMPETITION_HAS_NOT_BEGUN,
 	ERROR_DISTINCT_RATING, 
 	ERROR_NO_RATINGS_LEFT,
 	

}
