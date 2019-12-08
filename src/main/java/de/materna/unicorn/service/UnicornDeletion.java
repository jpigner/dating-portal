package de.materna.unicorn.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.materna.unicorn.model.Unicorn;

/**
 * Services to delete unicorns from the database.
 */
@Stateless
public class UnicornDeletion {
    @Inject
    private EntityManager em;
    
    @Inject
    private Logger log;
    
    /**
     * Removes a unicorn from the database.
     * @param unicorn the unicorn to be deleted.
     * @throws Exception throws exception if problem occurs while removing the entity.
     */
    public void remove(Unicorn unicorn) throws Exception {
    	if ( em.contains( unicorn )) {
    		log.info( "EntityManager: Entity Found!" );
    		em.remove( unicorn );
    	} else {
    		log.info( "EntityManager: Not Found! Using Query" );
    		em.createQuery("DELETE FROM Unicorn unicorn WHERE id=" + unicorn.getId( )).executeUpdate();
    	}
  }
}
