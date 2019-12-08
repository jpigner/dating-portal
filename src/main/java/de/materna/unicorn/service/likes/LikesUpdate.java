package de.materna.unicorn.service.likes;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.materna.unicorn.model.Likes;

/**
 * Services to update likes to the database.
 */
@Stateless
public class LikesUpdate {
	@Inject
	private EntityManager em;
	
	/**
	 * @param like the like to be updated in the database.
	 * @throws Exception throws exception if problem occurs while merging the like.
	 */
	public void update( Likes like ) throws Exception {
		em.merge( like );
	}
}
