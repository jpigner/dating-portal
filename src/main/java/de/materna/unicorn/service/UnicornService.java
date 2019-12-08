package de.materna.unicorn.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.materna.unicorn.model.Unicorn;
import de.materna.unicorn.repository.UnicornRepository;
import lombok.Data;

/**
 * Represents a service to read entity's from the repository.
 */
@Model
@Data
public class UnicornService implements UnicornRepository {
	@Inject
	private EntityManager em;

	@Inject
	private Logger log;

	@Override
	public Optional< Unicorn > findById( long id ) {
		return Optional.of( em.find( Unicorn.class , id ) );
	}

	@Override
	public Optional< Unicorn > findByEmail( String email ) {
		String jpql = "SELECT unicorn FROM Unicorn unicorn WHERE unicorn.email = :email";
		TypedQuery< Unicorn > query = em.createQuery( jpql , Unicorn.class );
		query.setParameter( "email" , email );
		Optional< Unicorn > unicorn;
		try {
			unicorn = Optional.of( query.getResultList( ).get( 0 ) );
		} catch ( Exception e ) {
			return Optional.of( new Unicorn( ) );
		}
		return unicorn;
	}

	@Override
	public List< Unicorn > findAll( ) {
		String jpql = "SELECT unicorn FROM Unicorn unicorn";
		TypedQuery< Unicorn > query = em.createQuery( jpql , Unicorn.class );
		return query.getResultList( );
	}

}
