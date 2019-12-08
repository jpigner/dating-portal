package de.materna.unicorn.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

/**
 * Represents a message consisting of an id of the like, a local date time that represents
 * the sending date of the message, a receiver id of the message, a sender id of the message,
 * and the message it self.
 * 
 * Messages offers the possibility to communicate with each other in this dating application.
 */
@Data
@Entity
public class Message {
	
	@Id
	@GeneratedValue
	private long id;
	
	private LocalDateTime date;
	private long senderId;
	private long receiverId;
	
	@Column ( length = 10_000)
	private String message;

}
