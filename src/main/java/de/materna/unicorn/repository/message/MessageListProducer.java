package de.materna.unicorn.repository.message;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import de.materna.unicorn.model.Message;
import de.materna.unicorn.service.message.MessageService;
import lombok.Data;

/**
 *  Represents a producer of a list for all given messages in the database.
 */
@Data
@RequestScoped
@Named
public class MessageListProducer {
	
	@Inject
	private MessageService messageService;
	
	private List<Message> messages;
	
	/**
	 * Initialized the request in the database.
	 */
	@PostConstruct
	public void initList() {
		retrieveAllLikes( );
	}
	
	/**
	 * @return messages list with all given messages of the database.
	 */
	@Produces
	@Named
	public List< Message > getMessages( ) {
		return messages;
	}
	
	/**
	 * Notify if an entity in the database has changed.
	 * @param message message.
	 */
	public void onLikesListChanged( @Observes ( notifyObserver = Reception.IF_EXISTS) final Message message ) {
		retrieveAllLikes();
	}
	
	/**
	 * Retrieves all messages.
	 */
	public void retrieveAllLikes( ) {
		messages = messageService.findAll( );
	}
}
