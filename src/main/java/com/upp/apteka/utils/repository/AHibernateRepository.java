package com.upp.apteka.utils.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * Class that provides JPA/Hibernate Repository implementation
 * 
 * @author Solomka
 *
 * @param <Type>
 *            object to persist
 * @param <Key>
 *            object key
 * @param <HSpecification>
 *            specification to use for searching requests
 */
public abstract class AHibernateRepository<Type, Key extends Serializable> {

	// ***PO.java
	private final Class<Type> entityClass;

	@SuppressWarnings("unchecked")
	public AHibernateRepository() {
		this.entityClass = (Class<Type>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	@Autowired
	SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public Key add(Type obj) {
		// Key key = (Key) getSession().save(obj);
		// getSession().flush();
		 return (Key) getSession().save(obj);
		 
	}

	/**
	 * return object by id from db
	 * 
	 * @param key
	 * @return
	 */
	public Type get(Key key) {
		return (Type) getSession().get(entityClass, key);
	}

	/**
	 * return a proxy object with the given identity value
	 * 
	 * @param key
	 * @return
	 */
	public Type loadEntity(Key key) {
		return (Type) getSession().load(entityClass, key);
	}

	public void updateEntity(Type obj) {
		getSession().update(obj);
	}

	public boolean deleteEntity(Key key) {

		Type obj = loadEntity(key);
		if (obj != null) {
			getSession().delete(obj);
			return true;
		}
		return false;
	}
	
	/**
	 * find entities by the criteria
	 * 
	 * @param criterion
	 * @return
	 */
	/*
	@SuppressWarnings("unchecked")
	public List<Type> findByCriteria(String hql){		
				
		return (List<Type>) createQuery(hql).list();
		
	}
*/
	/*
	@SuppressWarnings("unchecked")
	public List<Type> findByCriteria(Criterion criterion){
		
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(criterion);
		
		return (List<Type>) criteria.list();
		
	}
*/
	
	
	protected Criteria createEntityCriteria() {
		return getSession().createCriteria(entityClass);
	}

	protected Query createQuery(String query) {
		return getSession().createQuery(query);
	}

}
