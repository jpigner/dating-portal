package de.materna.unicorn.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import de.materna.unicorn.model.Unicorn;
import de.materna.unicorn.service.UnicornRegistration;
import de.materna.unicorn.utils.DatabaseCreater;
import de.materna.unicorn.utils.DatabaseHasher;
import lombok.Data;

/**
 * Represents the controller for the register requests.
 */
@Data
@Model
public class RegisterController {
	@Inject
	private UnicornRegistration unicornRegistration;
	
	@Inject
	private DatabaseCreater creater;

	@Inject
	private DatabaseHasher hasher;

	@Inject
	private Logger log;
	
	@Produces
	@Named
	private Unicorn unicorn;
	private Date birthday;

	/**
	 * Initialized a new unicorn.
	 */
	@PostConstruct
	public void initNewEntity( ) {
		this.unicorn = new Unicorn( );
	}

	/**
	 * Register a new unicorn.
	 * 
	 * @throws Exception throws exception if problem occurs while persisting
	 *                   unicorn.
	 * @return restpoint to the login after saving the entity
	 */
	public String register( String name ) throws Exception {
		unicorn.setFotoid( name );
		unicorn.setNickname( unicorn.getName().split(" ")[0]);
		unicorn.setBirthdate(
				LocalDateTime.ofInstant( birthday.toInstant( ) , ZoneId.systemDefault( ) ).toLocalDate( ) );
		unicorn.setPassword( hasher.getHashedPassword( unicorn.getPassword( ) ) );
		unicornRegistration.register( unicorn );
		initNewEntity( );
		return "login";
	}

	/**
	 * Register all unicorns from a given database.
	 * 
	 * @throws Exception throws exception if problem occurs while persisting
	 *                   unicorn.
	 */
	public void registerAll( ) throws Exception {
		List< Unicorn > unicorns = creater.createUnicorns( );
		for ( Unicorn u : unicorns ) {
			unicornRegistration.register( u );
			initNewEntity( );
		}
	}
}
