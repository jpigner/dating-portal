package de.materna.unicorn.repository;

import java.util.List;
import java.util.Optional;

import de.materna.unicorn.model.Unicorn;

/**
 * Represents a repository for the unicorn entity.
 */
public interface UnicornRepository {
	/**
	 * @param id the id of the unicorn.
	 * @return unicorn with the given id or optional empty if null.
	 */
	Optional< Unicorn > findById( long id );
	/**
	 * 
	 * @param name the name of the unicorn.
	 * @return unicorn with the given name or optional empty if null.
	 */
	Optional< Unicorn > findByEmail( String name );
	
	/**
	 * @return a collection of all unicorns in the repository.
	 */
	List< Unicorn > findAll( );

}
