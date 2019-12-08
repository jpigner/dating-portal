package de.materna.unicorn.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.github.javafaker.Faker;

import de.materna.unicorn.model.Likes;
import de.materna.unicorn.model.Message;
import de.materna.unicorn.model.Unicorn;

/**
 * Represents a utility class to create a random database.
 */
public class DatabaseCreater {
	private Faker faker = new Faker(new Locale("de"));
	
	@Inject
	private Logger log;
	
	@Inject
	private DatabaseHasher hasher;
	
	/**
	 * @return a list of generated fake likes that can be persisted as entity's.
	 */
	public List<Likes> createLikes() {
		List<Likes> likes = new ArrayList<>();

		for (int i = 0; i < 999; i++) {
			Likes like = new Likes();
			like.setIdFromLikedUnicorn(faker.number().numberBetween(1, 120));

			long idOfLikedUnicorn = 0;
			do {
				idOfLikedUnicorn = faker.number().numberBetween(1, 120);
			} while (idOfLikedUnicorn == like.getIdFromLikedUnicorn());
			like.setIdOfLikedUnicorn(idOfLikedUnicorn);

			like.setLiked(true);

			if (likes.stream().anyMatch(liked -> liked.getIdFromLikedUnicorn() == like.getIdFromLikedUnicorn()
					&& liked.getIdOfLikedUnicorn() == like.getIdOfLikedUnicorn())) {
				log.info( "Bereits geliked" );
			} else {
				log.info( "Noch nicht geliked" );
				likes.add(like);
			}
				
		}

		return likes;
	}

	/**
	 * @return a list of generated fake messages that can be persisted as entity's.
	 */
	public List<Message> createMessages() {
		List<Message> messages = new ArrayList<>();

		for (int i = 0; i < 9999; i++) {
			Message message = new Message();
			message.setDate(LocalDateTime.now());
			message.setReceiverId(faker.number().numberBetween(1, 120));

			long senderId = 0;
			do {
				senderId = faker.number().numberBetween(1, 120);
			} while (senderId == message.getReceiverId());
			message.setSenderId(senderId);

			message.setMessage("Hallo. " + faker.backToTheFuture().quote() + " " + faker.gameOfThrones().quote() + " "
					+ faker.chuckNorris().fact() + " " + faker.lordOfTheRings().character() + ". "
					+ faker.hitchhikersGuideToTheGalaxy().quote() + faker.backToTheFuture().quote() + faker.backToTheFuture().quote()
					+ faker.backToTheFuture().quote() + faker.backToTheFuture().quote() + faker.backToTheFuture().quote());
			
			message.setMessage( message.getMessage().substring(0, 250));
			messages.add(message);
		}

		return messages;
	}

	/**
	 * @return a list of generated fake unicorns that can be persisted as entity's.
	 */
	public List<Unicorn> createUnicorns() {
		List<Unicorn> unicorns = new ArrayList<>();

		for (int i = 0; i < 120; i++) {
			Unicorn unicorn = new Unicorn();
			unicorn.setBirthdate(
					LocalDateTime.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault()).toLocalDate());
			unicorn.setHornlenght(Integer.valueOf(faker.address().streetAddressNumber()));
			unicorn.setName(faker.name().fullName());
			unicorn.setPassword("pass" + i);
			unicorn.setPassword(hasher.getHashedPassword(unicorn.getPassword()));

			unicorn.setEmail("email" + i + "@email.de");

			if (i < 10)
				unicorn.setFotoid("unicorn00" + i + ".jpg");
			else if (i >= 10 && i < 100)
				unicorn.setFotoid("unicorn0" + i + ".jpg");
			else
				unicorn.setFotoid("unicorn" + i + ".jpg");

			unicorn.setDescription("Hallo mein Name ist " + unicorn.getName() + ". Und mein " + "Lieblingszitat ist "
					+ faker.backToTheFuture().quote() + " Meine Lieblingsfarbe ist und war schon immer "
					+ faker.color().name() + ".");

			unicorn.setNickname(unicorn.getName().split(" ")[0]);

			unicorns.add(unicorn);
		}
		return unicorns;
	}

}
