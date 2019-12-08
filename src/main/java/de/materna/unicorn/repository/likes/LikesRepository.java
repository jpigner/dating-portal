package de.materna.unicorn.repository.likes;

import java.util.List;

import de.materna.unicorn.model.Likes;

/**
 * Represents a repository for the Likes entity.
 */
public interface LikesRepository {
	
	/**
	 * @param id the id of the like to search.
	 * @return the like of the given id.
	 */
	Likes findById( long id );
	
	/**
	 * @return a collection of all likes in the repository.
	 */
	List< Likes > findAll( );
}
