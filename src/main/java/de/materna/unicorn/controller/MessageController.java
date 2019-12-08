package de.materna.unicorn.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.materna.unicorn.model.Message;
import de.materna.unicorn.model.Unicorn;
import de.materna.unicorn.repository.message.MessageListProducer;
import de.materna.unicorn.service.message.MessageDeletion;
import de.materna.unicorn.service.message.MessageRegistration;
import de.materna.unicorn.utils.DatabaseCreater;
import de.materna.unicorn.utils.Utils;
import lombok.Data;

/**
 * Represents the controller for the message requests.
 */
@Data
@ApplicationScoped
@Named
public class MessageController {
	private boolean allMessages;
	private String messageText;
	private long id;

	@Inject
	private MessageRegistration messageRegistration;

	@Inject
	private MessageDeletion messageDeletion;

	@Inject
	private DatabaseCreater creater;

	@Inject
	private FacesContext context;

	@Inject
	private Logger log;

	@Inject
	private Utils utils;

	@Inject
	private MessageListProducer messages;

	@Produces
	@Named
	private Message message;

	/**
	 * Initialized a new message.
	 */
	@PostConstruct
	public void initNewEntity( ) {
		this.message = new Message( );
	}

	/**
	 * Register all messages from a given database.
	 * 
	 * @throws Exception throws exception if problem occurs while persisting
	 *                   message.
	 */
	public void registerAllMessages( ) throws Exception {
		List< Message > messages = creater.createMessages( );
		for ( Message m : messages ) {
			messageRegistration.register( m );
			initNewEntity( );
		}
	}

	/**
	 * @param visitor             the visitor that sends the message.
	 * @param idOfSelectedUnicorn the id of the unicorn to receive that message.
	 * @param date                the date and time of sending the message.
	 * @param text                the message.
	 * @return restpoint to the requested unicorn date profile.
	 * @throws Exception throws exception if problem occurs while persisting the
	 *                   message.
	 */
	public String registerMessage( Unicorn visitor, long idOfSelectedUnicorn, Unicorn date, String text )
			throws Exception {
		Message message = new Message( );
		message.setDate( LocalDateTime.now( ) );
		message.setMessage( text );
		message.setReceiverId( idOfSelectedUnicorn );
		message.setSenderId( visitor.getId( ) );

		boolean writeMessage = true;
		Message lastMessage = null;
		int sizeOfMessageList = utils.getMyMessages( messages.getMessages( ) , visitor ).size( );

		if ( sizeOfMessageList != 0 ) {
			lastMessage = utils.getMyMessages( messages.getMessages( ) , visitor ).get( sizeOfMessageList - 1 );
			if ( lastMessage.getMessage( ).equals( text ) || text.trim( ).equals( "" ) ) {
				writeMessage = false;
				log.info( "Error: Nachricht ist leer oder bereits verschickt!" );

				String errorMessage = "Error: Nachricht ist leer oder bereits verschickt!";
				FacesMessage faceMessage = new FacesMessage( FacesMessage.SEVERITY_ERROR, errorMessage,
						"Nachricht konnte nicht verschickt weden!" );
				context.addMessage( null , faceMessage );
			}
		}

		if ( writeMessage ) {
			messageRegistration.register( message );
			initNewEntity( );
			log.info( " Message persisted : " + text );
			messageText = "";

			String successMessage = "Deine Nachricht wurde abgeschickt";
			FacesMessage faceMessage = new FacesMessage( FacesMessage.SEVERITY_INFO, successMessage,
					"Nachricht erfolgreich abgeschickt!" );
			context.addMessage( null , faceMessage );
		}

		return "/date-profil.xhtml?faces-redirect=true&id=" + date.getId( );
	}
	
	/**
	 * @param messageToDelete the message to be deleted.
	 * @return restpoint to the requested messages.
	 * @throws Exception throws exception if problem occurs while persisting the
	 *                   message.
	 */
	public String deleteMessage( Message messageToDelete ) throws Exception {
		log.info( ""+ messageToDelete );
		messageDeletion.remove( messageToDelete );
		log.info( "Nachricht wurde gelöscht" );
		initNewEntity( );
		return "/message.xhtml?faces-redirect=true&id=" + id;
	}

	/**
	 * Shows all messages from the unicorn.
	 * @return restpoint to the requested messages.
	 */
	public String seeAllMessages( ) {
		this.allMessages = true;
		return "/message.xhtml?faces-redirect=true&id=" + id;
	}
	
	/**
	 * Shows the last 10 messages from the unicorn.
	 * @return restpoint to the requested messages.
	 */
	public String seeLastMessages( ) {
		this.allMessages = false;
		return "/message.xhtml?faces-redirect=true&id=" + id;
	}
}
