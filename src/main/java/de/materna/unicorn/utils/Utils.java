package de.materna.unicorn.utils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.enterprise.inject.Model;

import de.materna.unicorn.model.Likes;
import de.materna.unicorn.model.Message;
import de.materna.unicorn.model.Unicorn;

/**
 * Represents a utility class.
 */
@Model
public class Utils {
	/**
	 * @param unicorns list of all unicorns.
	 * @param id the id of the unicorn.
	 * @return the specified unicorn to the given id.
	 */
	public Unicorn getDateUnicorn( List<Unicorn> unicorns, long id ) {
		return unicorns.stream().filter( unicorn -> unicorn.getId() == id ).findFirst().get();
	}
	
	/**
	 * @param messages list of all messages.
	 * @param unicorn the holder of the messages.
	 * @param specifiedMessageId the id of the message.
	 * @return the specified message to the given message id.
	 */
	public Message getSpecifiedMessage( List<Message> messages, Unicorn unicorn, long specifiedMessageId ) {
		return getMyMessages( messages , unicorn ).stream( )
				.filter( message -> message.getId( ) == specifiedMessageId )
				.findAny( ).get( );
	}
	
	/**
	 * @param messages list of all messages.
	 * @param unicorn the holder of the messages.
	 * @return all messages from the given unicorn.
	 */
	public List<Message> getMyMessages( List<Message> messages, Unicorn unicorn ) {
		return messages.stream().filter( message -> message.getReceiverId() == unicorn.getId() ).collect( Collectors.toList() );
	}
	
	/**
	 * @param likes list of all likes.
	 * @param visitor the unicorn that want to like.
	 * @param idOfSelectedDate id of the unicorn to be liked.
	 * @return true if a like exits to the given liker or false if not.
	 */
	public boolean isDateLiked( List<Likes> likes, Unicorn visitor, long idOfSelectedDate ) {
		List<Likes> fromLoggedUnicornLikedList = likes.stream()
				.filter( id -> id.getIdFromLikedUnicorn() == visitor.getId() )
				.collect( Collectors.toList() );
		return fromLoggedUnicornLikedList.stream().filter( like -> like.isLiked( ) ).anyMatch( id -> id.getIdOfLikedUnicorn() == idOfSelectedDate );
	}
	
	/**
	 * @param likes list of all likes.
	 * @param id id of the unicorn where the likes should be counted.
	 * @return number of likes of the requested unicorn.
	 */
	public int getDateLikes( List<Likes> likes, long id ) {
		return likes.stream().filter( like -> like.getIdOfLikedUnicorn() == id )
				.filter( like -> like.isLiked( ) )
				.collect( Collectors.toList() ).size();
	}
	
	/**
	 * @param likes list of all likes.
	 * @param unicorn the liker.
	 * @param idOfSelectedUnicorn the id of the unicorn that was liked.
	 * @return the specified like to the given liker and receiver.
	 */
	public Likes getSpecifiedLike( List<Likes> likes, Unicorn unicorn, long idOfSelectedUnicorn ) {
		return likes.stream( ).filter( like -> like.getIdFromLikedUnicorn() == unicorn.getId() )
				.filter( like -> like.getIdOfLikedUnicorn( ) == idOfSelectedUnicorn ).findAny( ).get( );
	}
	
	/**
	 * @param likes list of all likes.
	 * @param unicorn the unicorn that holds the likes.
	 * @return  list of all likes that holds the unicorn.
	 */
	public int getMyLikes(List<Likes> likes, Unicorn unicorn) {
		return likes.stream().filter( like -> like.getIdOfLikedUnicorn() == unicorn.getId() )
				.filter( like -> like.isLiked( ) )
				.collect( Collectors.toList() ).size();
	}
	
	/**
	 * @param unicorn the unicorn to calculate the years since birthday.
	 * @return the years of the given unicorn since birthday.
	 */
	public String getYears(Unicorn unicorn) {
		return String.valueOf(unicorn.getBirthdate().until(LocalDate.now()).toTotalMonths() / 12);
	}
	
	/**
	 * @param unicorn the unicorn which will generate a nickname.
	 * @return a nickname for the requested unicorn.
	 */
	public String getNickname(Unicorn unicorn) {
		return unicorn.getName().split(" ")[0];
	}
	
	/**
	 * @return a range of numbers for the days of month.
	 */
	public int[] getDays() {
		return IntStream.rangeClosed(1, 31).toArray();
	}
	
	/**
	 * @return a range of numbers for the months.
	 */
	public int[] getMonths() {
		return IntStream.rangeClosed(1, 12).toArray();
	}
	
	/**
	 * @return a range of numbers for the years.
	 */
	public int[] getYears() {
		return IntStream.range(1900, LocalDate.now().getYear()).toArray();
	}

}
