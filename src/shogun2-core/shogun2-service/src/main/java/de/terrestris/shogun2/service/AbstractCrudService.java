package de.terrestris.shogun2.service;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import de.terrestris.shogun2.dao.GenericHibernateDao;
import de.terrestris.shogun2.model.PersistentObject;

/**
 * This abstract service class provides basic CRUD functionality.
 *
 * @author Nils Bühner
 * @see AbstractDaoService
 *
 */
public abstract class AbstractCrudService<E extends PersistentObject, D extends GenericHibernateDao<E, Integer>>
		extends AbstractDaoService<E, D> {

	/**
	 *
	 * @param e
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	public E saveOrUpdate(E e) {
		dao.saveOrUpdate(e);
		return e;
	}

	/**
	 * Return the real object from the database. Returns null if the object does
	 * not exist.
	 * 
	 * @param id
	 * @return
	 */
	@PostAuthorize("hasRole(@configHolder.getSuperAdminRoleName()) or hasPermission(returnObject, 'READ')")
	public E findById(Integer id) {
		return dao.findById(id);
	}

	/**
	 * Return a proxy of the object (without hitting the database). This should
	 * only be used if it is assumed that the object really exists and where
	 * non-existence would be an actual error.
	 * 
	 * @param id
	 * @return
	 */
	@PostAuthorize("hasRole(@configHolder.getSuperAdminRoleName()) or hasPermission(returnObject, 'READ')")
	public E loadById(int id) {
		return dao.loadById(id);
	}

	/**
	 *
	 * @return
	 */
	@PostFilter("hasRole(@configHolder.getSuperAdminRoleName()) or hasPermission(filterObject, 'READ')")
	public List<E> findAll() {
		return dao.findAll();
	}

	/**
	 *
	 * @param e
	 */
	@PreAuthorize("hasRole(@configHolder.getSuperAdminRoleName()) or hasPermission(#e, 'DELETE')")
	public void delete(E e) {
		dao.delete(e);
	}

}
