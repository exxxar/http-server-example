/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;



import entities.Users;
import java.io.IOException;
import javax.swing.plaf.basic.BasicFileChooserUI;
import org.hibernate.Session;
import org.postgresql.util.MD5Digest;
import support.BasicFunctional;
/**
 *
 * @author Aleks
 */
public class Main extends BasicFunctional{

    /**
     * @param args the command line arguments
     */

    
    public static void main(String[] args) throws IOException, Throwable {
     CacheHandler cache = new CacheHandler();
     HTTPs https = new HTTPs(cache);
   //  SQL s = new SQL();
     HTTP http = new HTTP(cache);  
//        try {
//         s = new SQL();   
//         Users u = new Users();
//        Session se = HibernateUtil.getSessionFactory().openSession();
//        se.beginTransaction();
//        se.saveOrUpdate(u);
//        se.getTransaction().commit();
//        se.close();
//        s.addOnline(u.getId(),"test");
//        } catch (Exception e) {
//        }
    
    
    
  //  s.remOnline(u);
     new Thread(https).start();  
     new Thread(http).start();

    }
    
}
