package de.materna.unicorn.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * Represents a unicorn consisting of an id, name, horn length, birthday,
 * description, photo id, email and password.
 * 
 * Unicorns are the visitors and customers of the dating application.
 */
@Data
@Entity
public class Unicorn {
	@Id
	@GeneratedValue
	private long id;

	@NotNull ( message = "Please set your name")
	@Column ( length = 255)
	@Pattern ( regexp = "[^0-9]*" , message = "Must not contain numbers")
	private String name;
	
	private String nickname;
	
	@NotNull
	private int hornlenght;
	
	private LocalDate birthdate;

	@Column ( length = 10_000)
	private String description;
	private String fotoid;
	
	@NotNull ( message = "Please set your email")
	private String email;
	private String password;
}
