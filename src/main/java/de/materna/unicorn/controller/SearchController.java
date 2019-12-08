package de.materna.unicorn.controller;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
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
 * Represents the controller for the search requests.
 */
@Data
@ApplicationScoped
@Named
public class SearchController {
	private double minAge;
	private double maxAge;
	private double minLenght;
	private double maxLenght;

	@Inject
	private Logger log;

	@Inject
	private UnicornService unicornService;

	@Inject
	private Utils utils;
	
	@Inject
	private ProfilController profilController;
	
	private List< Unicorn > unicorns;

	/**
	 * Creates the requested filtered unicorn list.
	 * 
	 * @return filtered unicorn list.
	 */
	@Produces
	@Named
	public List< Unicorn > getFilteredUnicorns( ProfilController controller ) {
		filterUnicorns( );
		unicorns.remove( controller.getUnicorn( ).get( ) );
		return unicorns;
	}

	/**
	 * Creates the requested matched unicorn list.
	 * 
	 * @return matched unicorn list.
	 */
	@Produces
	@Named
	public List< Unicorn > getMatchedUnicorns( ProfilController controller ) {
		matchUnicorns( controller.getUnicorn( ).get( ) );
		unicorns.remove( controller.getUnicorn( ).get( ) );
		return unicorns;
	}

	/**
	 * Notify if the list has changed.
	 * 
	 * @param unicorn unicorn.
	 */
	public void onUnicornListChanged( @Observes ( notifyObserver = Reception.IF_EXISTS) final Unicorn unicorn ) {
		filterUnicorns( );
	}

	/**
	 * Filters the unicorn list with the given values from the form.
	 */
	public void filterUnicorns( ) {

		boolean filterByLength = minAge == 0 && maxAge == 0;
		boolean filterByAge = minLenght == 0 && maxLenght == 0;

		if ( filterByLength ) {
			unicorns = unicornService.findAll( ).stream( ).filter( unicorn -> unicorn.getHornlenght( ) > minLenght )
					.filter( unicorn -> unicorn.getHornlenght( ) < maxLenght ).collect( Collectors.toList( ) );
		}

		else if ( filterByAge ) {
			unicorns = unicornService.findAll( ).stream( )
					.filter( unicorn -> Integer.valueOf( utils.getYears( unicorn ) ) > minAge )
					.filter( unicorn -> Integer.valueOf( utils.getYears( unicorn ) ) < maxAge )
					.collect( Collectors.toList( ) );
		}

		else {
			unicorns = unicornService.findAll( ).stream( ).filter( unicorn -> unicorn.getHornlenght( ) > minLenght )
					.filter( unicorn -> unicorn.getHornlenght( ) < maxLenght )
					.filter( unicorn -> Integer.valueOf( utils.getYears( unicorn ) ) > minAge )
					.filter( unicorn -> Integer.valueOf( utils.getYears( unicorn ) ) < maxAge )
					.collect( Collectors.toList( ) );
		}
	}
	
	/**
	 * Filters the unicorn list to matching dates.
	 */
	public void matchUnicorns( Unicorn unicorn ) {
		unicorns = unicornService.findAll( ).stream( )
				.filter( dateUnicorn -> dateUnicorn.getBirthdate( ).getMonth( )
						.equals( unicorn.getBirthdate( ).getMonth( ) ) )
				.filter( dateUnicorn -> dateUnicorn.getName( ).substring( 0 , 1 )
						.equals( unicorn.getName( ).substring( 0 , 1 ) ) )
				.filter( dateUnicorn -> Integer.valueOf( utils.getYears( dateUnicorn ) ) > Integer
						.valueOf( utils.getYears( unicorn ) ) )
				.collect( Collectors.toList( ) );
		
		if ( unicorns.size( ) != 0)
			log.info( "Alle Match-Kriterien konnten erfüllt werden." );
		
		if ( unicorns.size( ) == 0 ) {
			log.info( "Mit Einschränkungen: Mehrauswahl-Nach-Allen Kriterien" );
			unicorns = unicornService.findAll( ).stream( )
					.filter( dateUnicorn -> dateUnicorn.getBirthdate( ).getMonth( )
							.equals( unicorn.getBirthdate( ).getMonth( ) ) )
					.filter( dateUnicorn -> dateUnicorn.getName( ).substring( 0 , 1 )
							.equals( unicorn.getName( ).substring( 0 , 1 ) ) )
					.collect( Collectors.toList( ) );

			if ( unicorns.size( ) == 0 ) {
				log.info( "Mit Einschränkungen: Mehrauswahl-Nach-Allen Kriterien, Anfangsbuchstaben" );
				unicorns = unicornService.findAll( ).stream( ).filter( dateUnicorn -> dateUnicorn.getBirthdate( )
						.getMonth( ).equals( unicorn.getBirthdate( ).getMonth( ) ) )
						.collect( Collectors.toList( ) );

				if ( unicorns.size( ) == 0 ) {
					log.info( "Eingeschränkte-Suche nach Alter" );
					unicorns = unicornService.findAll( ).stream( )
							.filter( dateUnicorn -> Integer.valueOf( utils.getYears( dateUnicorn ) ) > Integer
									.valueOf( utils.getYears( unicorn ) ) )
							.collect( Collectors.toList( ) );

					if ( unicorns.size( ) == 0 ) {
						log.info( "Leider keine Treffer gefunden." );
					}
				}
			}
		}

	}

	/**
	 * @return the restpoint to the filtered search.
	 */
	public String filteredSearch( ) {
		return "filtered-search";
	}

	/**
	 * @return the restpoint to the matched search.
	 */
	public String matchedSearch( ) {
		return "matched-search";
	}

	/**
	 * @return the restpoint to the matched search.
	 */
	public String allUnicorns( ) {
		return "search";
	}

}
