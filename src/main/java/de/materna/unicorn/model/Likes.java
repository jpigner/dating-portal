package de.materna.unicorn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

/**
 * Represents a like consisting of an id of the like, id of the liking unicorn, id from the liking unicorn
 * and a boolean that indicates if a like was given.
 * 
 * Likes are a kind of currency for popularity in this dating application. 
 */
@Data
@Entity
public class Likes {
	
	@Id
	@GeneratedValue
	private long id;
	
	private long idOfLikedUnicorn;
	private long idFromLikedUnicorn;
	private boolean liked;
}
