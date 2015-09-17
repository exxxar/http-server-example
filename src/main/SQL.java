/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import entities.Online;
import entities.Users;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Aleks
 */
public class SQL extends HibernateUtil{
    
    private final Session session;
    private final long EXPIRE_TIME = 600000;

    public SQL() {
        session = getSessionFactory().openSession();
    }
    
   
    
    public boolean isUserExist(long id){
        Users u = (Users)session.createCriteria(Users.class).add(Restrictions.eq("id", id)).uniqueResult();
        return u!=null;
    }
   
    public boolean isUserOnline(long id){
        Online o = (Online)session.createCriteria(Online.class).add(Restrictions.eq("userId", id)).uniqueResult();
        return o!=null;
    }
    
    public void addOnline(long id,String md5){
        if (!isUserExist(id))
            return;
        
        Online o = new Online();
        o.setUserId(id);
        o.setMd5(md5);
        o.setExpireTime((new Date()).getTime()+EXPIRE_TIME);
        System.out.println(o.getExpireTime()+" "+(new Date()).getTime());
        Users u = (Users)session.createCriteria(Users.class).add(Restrictions.eq("id", id)).uniqueResult();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
    }
      
    public void updateOnline(String md5){       
        
        Online o = getOnline(md5);
        if (!o.getMd5().equals(md5))
            return;
        
        if (!isUserExist(o.getUserId()))
            return;
        
        o.setExpireTime((new Date()).getTime()+EXPIRE_TIME);
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();    
        
    }
    
    public void remOnline(long id){
          if (!isUserExist(id)||!isUserOnline(id))
            return;
                       
        Online o  =   (Online) session.createCriteria(Online.class).add(Restrictions.eq("userId", id)).uniqueResult();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();        
    }
    
    public Users getUser(long id){
        return (Users)session.createCriteria(Users.class).add(Restrictions.eq("id", id)).uniqueResult();
    }
 
     public Online getOnline(String md5){
        return (Online)session.createCriteria(Online.class).add(Restrictions.eq("md5", md5)).uniqueResult();
    }
     
    public void addUser(Users user){     
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();        
    }
}
