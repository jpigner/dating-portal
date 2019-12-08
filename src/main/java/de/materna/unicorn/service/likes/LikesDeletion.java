package de.materna.unicorn.service.likes;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.materna.unicorn.model.Likes;

/**
 * Services to delete likes from the database.
 */
@Stateless
public class LikesDeletion {
	@Inject
	private EntityManager em;

	@Inject
	private Logger log;

	/**
	 * Removes a message from the database.
	 * 
	 * @param message the message to be deleted.
	 * @throws Exception throws exception if problem occurs while removing the
	 *                   entity.
	 */
	public void remove( Likes like ) throws Exception {
		if ( em.contains( like ) ) {
			log.info( "EntityManager: Entity Found!" );
			em.remove( like );
		} else {
			log.info( "EntityManager: Not Found! Using Query" );
			em.createQuery( "DELETE FROM Likes likes WHERE id=" + like.getId( ) ).executeUpdate( );
		}
	}
}