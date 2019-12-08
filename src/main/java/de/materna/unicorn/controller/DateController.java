package de.materna.unicorn.controller;

import java.util.Optional;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.materna.unicorn.model.Unicorn;
import lombok.Data;

/**
 * Represents the controller for the dating requests.
 */
@ApplicationScoped
@Data
@Named
public class DateController {
	private Optional<Unicorn> unicorn;
	private Unicorn selectedUnicorn;
	private long idOfSelectedDate;
	
	@Inject
	private Logger log;
	
	@Inject
	private FacesContext context;
	
	/**
	 * Initialized a new unicorn.
	 */
	@PostConstruct
	public void init( ) {
		unicorn = Optional.of( new Unicorn( ) );
	}
	
	/**
	 * @param unicorn the unicorn to be displayed by the client.
	 * @return restpoint to the requested unicorn profile.
	 */
	public String profil( Unicorn unicorn ) {
		cacheUnicorn( unicorn );
		return "/date-profil.xhtml?faces-redirect=true&id=" + unicorn.getId( );
	}
	
	/**
	 * Caches the requested unicorn from the view.
	 * @param unicorn the selected unicorn from the view.
	 */
	private void cacheUnicorn( Unicorn unicorn ) {
		this.unicorn = Optional.of( unicorn );
	}
	
}
