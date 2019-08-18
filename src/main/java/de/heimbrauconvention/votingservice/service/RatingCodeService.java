package de.heimbrauconvention.votingservice.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import de.heimbrauconvention.votingservice.domain.RatingCode;
import de.heimbrauconvention.votingservice.dto.RatingCodeDTO;
import de.heimbrauconvention.votingservice.repository.RatingCodeRepository;

@Transactional
@Service
public class RatingCodeService extends AbstractEntityService<RatingCode, RatingCodeDTO, CrudRepository<RatingCode,Long>>{

	@Autowired
	RatingCodeRepository ratingCodeRepository;
	
	
	@Override
	public RatingCodeDTO convertToDto(RatingCode pItem) {
		return (pItem == null)? null : modelMapper.map(pItem, RatingCodeDTO.class);
	}

	
}
