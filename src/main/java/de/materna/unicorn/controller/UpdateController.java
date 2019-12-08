package de.materna.unicorn.controller;

import java.time.LocalDate;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import de.materna.unicorn.model.Unicorn;
import de.materna.unicorn.service.UnicornUpdate;
import de.materna.unicorn.utils.DatabaseHasher;
import lombok.Data;

/**
 * Represents the controller for updating the unicorns.
 */
@Data
@Model
public class UpdateController {
	@ApplicationScoped
	private String password;
	
	private int day;
	private int month;
	private int year;
	
	@Inject
	private Logger log;
	
	@Inject
	private UnicornUpdate update;
	
	@Inject
	private DatabaseHasher hasher;

	/**
	 * Updates the year of the birthday.
	 * 
	 * @throws Exception if error occurs while updating unicorn.
	 */
	public void updateYearOfBirthday( Unicorn unicorn ) throws Exception {
		log.info(" Value Changed ");
		unicorn.setBirthdate(LocalDate.of(year, unicorn.getBirthdate().getMonth(),
				unicorn.getBirthdate().getDayOfMonth()));
		log.info("" + unicorn.getBirthdate());
		update.update(unicorn);
	}

	/**
	 * Updates the month of the birthday.
	 * 
	 * @throws Exception if error occurs while updating unicorn.
	 */
	public void updateMonthOfBirthday( Unicorn unicorn ) throws Exception {
		log.info(" Value Changed ");
		unicorn.setBirthdate(LocalDate.of(unicorn.getBirthdate().getYear(), month,
				unicorn.getBirthdate().getDayOfMonth()));
		log.info("" + unicorn.getBirthdate());
		update.update(unicorn);
	}

	/**
	 * Updates the day of the birthday.
	 * 
	 * @throws Exception if error occurs while updating unicorn.
	 */
	public void updateDayOfBirthday( Unicorn unicorn ) throws Exception {
		log.info(" Value Changed ");
		unicorn.setBirthdate(LocalDate.of(unicorn.getBirthdate().getYear(),
				unicorn.getBirthdate().getMonthValue(), day));
		log.info("" + unicorn.getBirthdate());
		update.update(unicorn);
	}

	/**
	 * Updates the horn length.
	 * 
	 * @throws Exception if error occurs while updating unicorn.
	 */
	public void updateHornLength( Unicorn unicorn ) throws Exception {
		log.info(" Value Changed ");
		log.info("" + unicorn.getHornlenght());
		update.update(unicorn);
	}

	/**
	 * Updates the description.
	 * 
	 * @throws Exception if error occurs while updating unicorn.
	 */
	public void updateDescription( Unicorn unicorn ) throws Exception {
		log.info(" Value Changed ");
		log.info(unicorn.getDescription());
		update.update(unicorn);
	}

	/**
	 * Updates the nickname.
	 * 
	 * @throws Exception if error occurs while updating unicorn.
	 */
	public void updateNickname( Unicorn unicorn ) throws Exception {
		log.info(" Value Changed ");
		log.info(unicorn.getNickname());
		update.update(unicorn);
	}

	/**
	 * Updates the email.
	 * 
	 * @throws Exception if error occurs while updating unicorn.
	 */
	public void updateEmail( Unicorn unicorn ) throws Exception {
		log.info(" Value Changed ");
		log.info(unicorn.getEmail());
		update.update(unicorn);
	}

	/**
	 * Updates the password.
	 * 
	 * @throws Exception if error occurs while updating unicorn.
	 */
	public void updatePassword( String password, Unicorn unicorn ) throws Exception {
		log.info(" Value Changed ");
		
		unicorn.setPassword( hasher.getHashedPassword( password ) );

		log.info(unicorn.getPassword());
		update.update(unicorn);
		password = "";
	}

	/**
	 * Updates the name.
	 * 
	 * @throws Exception if error occurs while updating unicorn.
	 */
	public void updateName( Unicorn unicorn ) throws Exception {
		log.info(" Value Changed ");
		log.info(unicorn.getName());
		update.update(unicorn);
	}
	
	/**
	 * @deprecated replaced by {@link #updateName(Unicorn)}}
	 */
	@Deprecated
	public void refreshName( Unicorn unicorn ) throws Exception {
		log.info(" Value Changed ");
		log.info(unicorn.getName());
		update.update(unicorn);
	}
}
