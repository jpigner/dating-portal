package de.materna.unicorn.repository.message;

import java.util.List;

import de.materna.unicorn.model.Message;

/**
 * Represents a repository for the Message entity.
 */
public interface MessageRepository {
	
	/**
	 * @param id the id of the message to search.
	 * @return the message of the given id.
	 */
	Message findById( long id );
	
	/**
	 * @return a collection of all messages in the repository.
	 */
	List< Message > findAll( );
	
}
