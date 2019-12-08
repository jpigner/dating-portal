package de.materna.unicorn.service.likes;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.materna.unicorn.model.Likes;

/**
 * Services to register likes to the database.
 */
@Stateless
public class LikesRegistration {
	@Inject
	private EntityManager em;

	/**
	 * Saves a like to the database.
	 * 
	 * @param like the like to be persisted.
	 * @throws Exception throws exception if problem occurs while persisting the
	 *                   entity.
	 */
	public void register( Likes like ) throws Exception {
		em.persist( like );
	}
}
