package entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Users.class)
public abstract class Users_ {

	public static volatile SingularAttribute<Users, String> password;
	public static volatile SingularAttribute<Users, String> phone;
	public static volatile SingularAttribute<Users, String> domain;
	public static volatile SingularAttribute<Users, String> name;
	public static volatile SingularAttribute<Users, Date> regDate;
	public static volatile SingularAttribute<Users, Long> id;
	public static volatile SingularAttribute<Users, String> email;

}

