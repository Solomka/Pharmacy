package com.upp.apteka.utils.repository;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.upp.apteka.bo.Pharmacy;

/**
 * basic interface for all the repositories
 * 
 * @author Solomka
 *
 * @param <Type>
 * 							object to persist
 * @param <Key>
 * 							object key
 * @param <HSpecification>
 * 							specification to use for searching requests
 */

public interface Repository<Type, Key, HSpecification extends HqlSpecification> {
	
	@NotNull
	public List<Type> getAll();
	
	@NotNull
	List<Type> searchByCriteria(HSpecification specification);
	
	@NotNull
	Key create(@NotNull Type obj);
	
	@NotNull
	Type read(@NotNull Key key);
	
	@NotNull
	void update(@NotNull Type obj);
	
	@NotNull
	boolean delete(@NotNull Key key); 

}
