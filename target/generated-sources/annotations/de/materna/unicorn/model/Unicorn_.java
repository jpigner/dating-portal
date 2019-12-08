package de.materna.unicorn.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Unicorn.class)
public abstract class Unicorn_ {

	public static volatile SingularAttribute<Unicorn, Integer> hornlenght;
	public static volatile SingularAttribute<Unicorn, String> password;
	public static volatile SingularAttribute<Unicorn, LocalDate> birthdate;
	public static volatile SingularAttribute<Unicorn, String> name;
	public static volatile SingularAttribute<Unicorn, String> description;
	public static volatile SingularAttribute<Unicorn, Long> id;
	public static volatile SingularAttribute<Unicorn, String> email;
	public static volatile SingularAttribute<Unicorn, String> fotoid;

}

