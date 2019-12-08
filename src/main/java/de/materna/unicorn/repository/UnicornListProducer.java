package de.materna.unicorn.repository;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import de.materna.unicorn.model.Unicorn;
import de.materna.unicorn.service.UnicornService;
import de.materna.unicorn.utils.Utils;
import lombok.Data;

/**
 *  Represents a producer of a list for all given unicorns in the database.
 */
@Data
@RequestScoped
@Named
public class UnicornListProducer {
	@Inject
	private UnicornService unicornService;
	
	@Inject
	private Utils utils;
	
	@Inject
	private Logger log;
	
	private List< Unicorn > unicorns;

	/**
	 * Initialized the request in the database.
	 */
	@PostConstruct
	public void initList() {
		retrieveAllUnicorns( );
	}
	
	/**
	 * @return unicorn list with all given unicorns of the database.
	 */
	@Produces
	@Named
	public List< Unicorn > getUnicorns( ) {
		return unicorns;
	}
	
	/**
	 * Notify if an entity in the database has changed.
	 * @param unicorn unicorn.
	 */
	public void onUnicornListChanged( @Observes ( notifyObserver = Reception.IF_EXISTS) final Unicorn unicorn ) {
		retrieveAllUnicorns();
	}
	
	/**
	 * Retrieves all unicorns.
	 */
	public void retrieveAllUnicorns( ) {
		unicorns = unicornService.findAll( );
	}
}
