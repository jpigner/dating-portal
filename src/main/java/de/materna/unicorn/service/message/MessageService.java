package de.materna.unicorn.service.message;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.materna.unicorn.model.Message;
import de.materna.unicorn.repository.message.MessageRepository;

/**
 * Represents a service to read messages from the repository.
 */
public class MessageService implements MessageRepository{
	@Inject
	private EntityManager em;
	
	@Override
	public Message findById(long id) {
		return em.find(Message.class, id);
	}

	@Override
	public List<Message> findAll() {
		String jpql = "SELECT message FROM Message message";
		TypedQuery< Message > query = em.createQuery( jpql , Message.class );
		return query.getResultList( );
	}

}
