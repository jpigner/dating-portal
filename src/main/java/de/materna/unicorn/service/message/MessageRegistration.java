package de.materna.unicorn.service.message;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.materna.unicorn.model.Message;

/**
 * Services to register messages to the database.
 */
@Stateless
public class MessageRegistration {
	@Inject
	private EntityManager em;

	/**
	 * Saves a message to the database.
	 * 
	 * @param message the message to be persisted.
	 * @throws Exception throws exception if problem occurs while persisting the
	 *                   entity.
	 */
	public void register( Message message ) throws Exception {
		em.persist( message );
	}
}
