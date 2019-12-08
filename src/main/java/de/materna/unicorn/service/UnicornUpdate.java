package de.materna.unicorn.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.materna.unicorn.model.Unicorn;

/**
 * Services to update unicorns to the database.
 */
@Stateless
public class UnicornUpdate {
	@Inject
	private EntityManager em;
	
	/**
	 * @param unicorn the unicorn to be updated in the database.
	 * @throws Exception throws exception if problem occurs while merging the unicorn.
	 */
	public void update( Unicorn unicorn ) throws Exception {
		em.merge( unicorn );
	}

}
