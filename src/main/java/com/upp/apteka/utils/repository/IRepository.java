package com.upp.apteka.utils.repository;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * basic interface for all the repositories
 * 
 * @author Solomka
 *
 * @param <Type>
 *            object to persist
 * @param <Key>
 *            object key
 * 
 */

public interface IRepository<Type, Key> {

	@NotNull
	public List<Type> getAll();

	@NotNull
	Key create(@NotNull Type obj);

	@NotNull
	Type read(@NotNull Key key);

	@NotNull
	void update(@NotNull Type obj);

	@NotNull
	boolean delete(@NotNull Key key);

}
