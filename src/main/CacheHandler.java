/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.util.ArrayList;
import java.util.List;
import support.BasicFunctional;
import support.cacheParam;

/**
 *
 * @author Aleks
 */
public class CacheHandler extends BasicFunctional{
    private List<cacheParam> list =  new ArrayList<>();

    public CacheHandler() {
    }
    
    public void add(cacheParam c){
        list.add(c);
    }
    
    public void add(String name,String value,String md5, long expire){
        cacheParam c = new cacheParam();
        c.setName(name);
        c.setValue(value);
        c.setMd5(md5);
        c.setDate(expire);
        
        list.add(c);
    }
    
    public cacheParam get(String name,String md5){
        
        md5 = md5.length()==32?md5:md5Custom(md5);

        for (cacheParam c:list)
            if (c.getName().equals(name.trim())&&c.getMd5().equals(md5.trim()))
                return c;
        return new cacheParam();
    }
    
    public List<cacheParam> getByMD5(String md5){
         md5 = md5.length()==32?md5:md5Custom(md5);
         List<cacheParam> l = new ArrayList<>();
         for (cacheParam c:list)
            if (c.getMd5().equals(md5.trim()))
                l.add(c);
         if (l.isEmpty())
             return null;
         return l;
    }
    
     public List<cacheParam> getByName(String name){
         List<cacheParam> l = new ArrayList<>();
         for (cacheParam c:list)
            if (c.getName().equals(name.trim()))
                l.add(c);
         if (l.isEmpty())
             return null;
         return l;
    }
     
    public void show(){
        for(cacheParam c:list)
            System.err.println(c.toString());
    }
    
}
