package de.heimbrauconvention.votingservice.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import de.heimbrauconvention.votingservice.domain.RatingItem;
import de.heimbrauconvention.votingservice.dto.RatingItemDTO;
import de.heimbrauconvention.votingservice.repository.RatingItemRepository;

@Transactional
@Service
public class RatingItemService extends AbstractEntityService<RatingItem, RatingItemDTO, CrudRepository<RatingItem,Long>>{

	@Autowired
	RatingItemRepository ratingItemRepository;
	
	
	@Override
	public RatingItemDTO convertToDto(RatingItem pItem) {
		return (pItem == null)? null : modelMapper.map(pItem, RatingItemDTO.class);
	}

	
}
