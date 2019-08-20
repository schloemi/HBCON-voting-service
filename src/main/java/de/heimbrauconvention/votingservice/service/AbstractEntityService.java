package de.heimbrauconvention.votingservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import de.heimbrauconvention.votingservice.domain.AbstractEntity;
import de.heimbrauconvention.votingservice.dto.AbstractEntityDTO;

@Transactional
@Service
public abstract class AbstractEntityService<T extends AbstractEntity, S extends AbstractEntityDTO, R extends CrudRepository<T, Long>> {

	public abstract S convertToDto(final T pItem);
	
	@Autowired
	protected R repository;
	
	@Autowired
	protected ModelMapper modelMapper;
	

	public void save(final T pItem) {
		if (pItem == null){
			return;
		}
		this.repository.save(pItem);
	}

	public T getById(final Long id) {
		return this.repository.findById(id).orElse(null);
	}

	public Iterable<T> getAll() {
		return this.repository.findAll();
	}
	
	public List<T> getAllAsList() {
		List<T> list = new ArrayList<>();
		Iterable<T> iterable = getAll();
		for (T t : iterable) {
			list.add(t);
		}
		return list;
	}
	
	public List<S> convertToDto(final List<T> pItemsList){
		List<S> dtos = new ArrayList<>();
		if (!CollectionUtils.isEmpty(pItemsList)){
			dtos = pItemsList.stream().map(item -> convertToDto(item)).collect(Collectors.toList());
		}
	    return dtos;
	}
	
	
}
