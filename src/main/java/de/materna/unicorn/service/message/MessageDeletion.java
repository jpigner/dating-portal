package de.materna.unicorn.service.message;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.materna.unicorn.model.Message;

/**
 * Services to delete messages from the database.
 */
@Stateless
public class MessageDeletion {
	@Inject
    private EntityManager em;
    
	@Inject
	private Logger log;
	
    /**
     * Removes a message from the database.
     * @param message the message to be deleted.
     * @throws Exception throws exception if problem occurs while removing the entity.
     */
    public void remove(Message message) throws Exception {
    	if ( em.contains( message )) {
    		log.info( "EntityManager: Entity Found!" );
    		em.remove( message );
    	} else {
    		log.info( "EntityManager: Not Found! Using Query" );
    		em.createQuery("DELETE FROM Message message WHERE id=" + message.getId( )).executeUpdate();
    	}
  }
}
