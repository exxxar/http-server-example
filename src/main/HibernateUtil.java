package main;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.ejb.Ejb3Configuration;
import support.BasicFunctional;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aleks
 */
public class HibernateUtil extends BasicFunctional{


  private static final SessionFactory sessionFactory;

  private static final Ejb3Configuration ejb3Configuration;

  static {
    try {
      // Create the SessionFactory from hibernate.cfg.xml
      sessionFactory = new AnnotationConfiguration().configure()
          .buildSessionFactory();
      ejb3Configuration = new Ejb3Configuration()
          .configure("/hibernate.cfg.xml");
    } catch (Throwable ex) {      
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public static Ejb3Configuration getEjb3Configuration() {
    return ejb3Configuration;
  }
}