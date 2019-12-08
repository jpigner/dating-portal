package de.materna.unicorn.repository.likes;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import de.materna.unicorn.model.Likes;
import de.materna.unicorn.service.likes.LikesService;
import lombok.Data;

/**
 *  Represents a producer of a list for all given likes in the database.
 */
@Data
@RequestScoped
@Named
public class LikesListProducer {

	@Inject
	private LikesService likesService;
	
	private List<Likes> likes;
	
	/**
	 * Initialized the request in the database.
	 */
	@PostConstruct
	public void initList() {
		retrieveAllLikes( );
	}
	
	/**
	 * @return likes list with all given likes of the database.
	 */
	@Produces
	@Named
	public List< Likes > getLikes( ) {
		return likes;
	}
	
	/**
	 * Notify if an entity in the database has changed.
	 * @param like like.
	 */
	public void onLikesListChanged( @Observes ( notifyObserver = Reception.IF_EXISTS) final Likes like ) {
		retrieveAllLikes();
	}
	
	/**
	 * Retrieves all likes.
	 */
	public void retrieveAllLikes( ) {
		likes = likesService.findAll( );
	}
}
