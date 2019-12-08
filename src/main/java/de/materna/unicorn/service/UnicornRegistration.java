package de.materna.unicorn.service;

import java.time.LocalDate;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.materna.unicorn.model.Unicorn;

/**
 * Services to register unicorns to the database.
 */
@Stateless
public class UnicornRegistration {
	@Inject
	private EntityManager em;

	/**
	 * Saves a unicorn to the database.
	 * 
	 * @param unicorn the unicorn to be persisted.
	 * @throws Exception throws exception if problem occurs while persisting the
	 *                   entity.
	 */
	public void register( Unicorn unicorn ) throws Exception {
		em.persist( unicorn );
	}

	/**
	 * Creates a new unicorn.
	 * 
	 * @param name        the name of the unicorn.
	 * @param hornlenght  the horn length of the unicorn.
	 * @param birthdate   the birthday of the unicorn.
	 * @param description the description for the unicorn.
	 * @param fotoID      the photo id for the unicorn.
	 * @param email       the email for the unicorn.
	 * @param password    the password for the unicorn.
	 * @return the unicorn with the given values.
	 */
	public Unicorn create( String name, int hornlenght, LocalDate birthdate, String description, String fotoID,
			String email, String password ) {
		Unicorn unicorn = new Unicorn( );
		unicorn.setBirthdate( birthdate );
		unicorn.setDescription( description );
		unicorn.setEmail( email );
		unicorn.setFotoid( fotoID );
		unicorn.setHornlenght( hornlenght );
		unicorn.setPassword( password );
		return unicorn;
	}
}
