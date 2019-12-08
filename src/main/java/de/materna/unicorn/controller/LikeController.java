package de.materna.unicorn.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import de.materna.unicorn.model.Likes;
import de.materna.unicorn.model.Unicorn;
import de.materna.unicorn.service.likes.LikesDeletion;
import de.materna.unicorn.service.likes.LikesRegistration;
import de.materna.unicorn.utils.DatabaseCreater;
import de.materna.unicorn.utils.Utils;
import lombok.Data;

/**
 * Represents the controller for the like requests.
 */
@Data
@Model
public class LikeController {
	@Inject
	private Logger log;

	@Inject
	private LikesRegistration likesRegistration;

	@Inject
	private LikesDeletion likesDeletion;

	@Inject
	private DatabaseCreater creater;

	@Inject
	private Utils utils;

	@Produces
	@Named
	private Likes like;

	/**
	 * Initialized a new unicorn.
	 */
	@PostConstruct
	public void initNewEntity( ) {
		this.like = new Likes( );
	}

	/**
	 * Register all likes from a given database.
	 * 
	 * @throws Exception throws exception if problem occurs while persisting like.
	 */
	public void registerAllLikes( ) throws Exception {
		List< Likes > likes = creater.createLikes( );
		for ( Likes l : likes ) {
			likesRegistration.register( l );
			initNewEntity( );
		}
	}

	/**
	 * 
	 * @param visitor             the liking unicorn.
	 * @param idOfSelectedUnicorn the id of the unicorn to be liked.
	 * @return restpoint to the requested unicorn profile.
	 * @throws Exception throws exception if problem occurs while persisting like.
	 */
	public String registerLike( Unicorn visitor, long idOfSelectedUnicorn ) throws Exception {
		Likes like = new Likes( );
		like.setIdFromLikedUnicorn( visitor.getId( ) );
		like.setIdOfLikedUnicorn( idOfSelectedUnicorn );
		like.setLiked( true );

		log.info( "Like registered! " + like );
		likesRegistration.register( like );
		initNewEntity( );
		return "/date-profil.xhtml?faces-redirect=true&id=" + idOfSelectedUnicorn;
	}
	
	/**
	 * @param date the unicorn to be disliked.
	 * @param likeToBeDeleted the like to be deleted.
	 * @return restpoint to the requested unicorn profile.
	 * @throws Exception throws exception if problem occurs while persisting like.
	 */
	public String dislikeUnicorn( Unicorn date, Likes likeToBeDeleted ) throws Exception {
		Likes like = likeToBeDeleted;
		log.info( "Disliked! " + like );
		likesDeletion.remove( like );
		initNewEntity( );
		return "/date-profil.xhtml?faces-redirect=true&id=" + date.getId( );
	}
}
