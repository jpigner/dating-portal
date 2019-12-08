package de.materna.unicorn.utils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.binary.Hex;

import de.materna.unicorn.model.Unicorn;

/**
 * Represents a password hasher for the database.
 */
public class DatabaseHasher {
	/**
	 * @param plainTextPassword the password in plain text to be hashed.
	 * @param iterations        the amount of the iterations for the algorithm.
	 * @param keyLength         the output length of the hash.
	 * @return the hashed password.
	 */
	public String getHashedPassword( String plainTextPassword, int iterations, int keyLength ) {
		String salt = String.valueOf( 123456 );
		char[ ] passwordChars = plainTextPassword.toCharArray( );
		byte[ ] saltBytes = salt.getBytes( );
		byte[ ] hashedBytes = hashPassword( passwordChars , saltBytes , iterations , keyLength );
		return Hex.encodeHexString( hashedBytes );
	}

	/**
	 * @param plainTextPassword the password in plain text to be hashed.
	 * @param iterations        the amount of the iterations for the algorithm.
	 * @return the hashed password, with a default key length of 512 and the given
	 *         iterations.
	 */
	public String getHashedPassword( String plainTextPassword, int iterations ) {
		return getHashedPassword( plainTextPassword , iterations , 512 );
	}

	/**
	 * @param plainTextPassword the password in plain text to be hashed.
	 * @return the hashed password, with a default key length of 512 and 1000
	 *         iterations.
	 */
	public String getHashedPassword( String plainTextPassword ) {
		return getHashedPassword( plainTextPassword , 1000 , 512 );
	}

	/**
	 * @param unicorns   the list of unicorns with plain text password to be hashed.
	 * @param iterations the amount of the iterations for the algorithm.
	 * @param keyLength  the output length of the hash.
	 * @return the database with hashed passwords.
	 */
	public List< Unicorn > initializeHashedDatabase( List< Unicorn > unicorns, int iterations, int keyLength ) {
		return saveHashedPasswords( unicorns , iterations , keyLength );
	}

	private List< Unicorn > saveHashedPasswords( List< Unicorn > unicorns, int iterations, int keyLength ) {
		for ( Unicorn unicorn : unicorns ) {
			String salt = String.valueOf( 123456 );
			char[ ] passwordChars = unicorn.getPassword( ).toCharArray( );
			byte[ ] saltBytes = salt.getBytes( );
			byte[ ] hashedBytes = hashPassword( passwordChars , saltBytes , iterations , keyLength );
			unicorn.setPassword( Hex.encodeHexString( hashedBytes ) );
		}
		return unicorns;
	}

	private byte[ ] hashPassword( final char[ ] password, final byte[ ] salt, final int iterations,
			final int keyLength ) {
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
			PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
			SecretKey key = skf.generateSecret( spec );
			byte[ ] res = key.getEncoded( );
			return res;
		} catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
			throw new RuntimeException( e );
		}
	}

}
