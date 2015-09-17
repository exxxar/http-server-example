/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

/**
 *
 * @author Aleks
 */
import java.util.ArrayList;

public class HttpBuffer {
  private class StructHTTP{
     public String param;
     public String value;
   }

   private ArrayList<String> params = new ArrayList<String>();
   private ArrayList<String> values = new ArrayList<String>();
   
   public int size(){
       return Math.max(params.size(), values.size());
   }
   
    public HttpBuffer() {
        Init();
    }

    public void Init()
    {
        params.clear();
        values.clear();
    }

    public boolean isSet(String elem)//as an isset in php
    {
       if (params.size()==0)
            return false;
       boolean found = false;
       for (int i=0;i<params.size();i++)
       {
           if (params.get(i).toLowerCase().trim().equals(elem.toLowerCase()))           {

               found = true;
               break;
           }
       }
       return found;
    }

    public String getElement(String elem)
    {
       boolean found = false;
       int index = 0;

       if (params.size()==0)
            return "<nothing>";

       for (int i=0;i<params.size();i++)
       {          
           if (params.get(i).toLowerCase().trim().equals(elem.toLowerCase()))
           {
               index = i;
               found = true;
               break;
           }
       }
      if (found==true)
       return values.get(index);
      else
          return "<nothing>";
    }

  public String getElement(int elem)
    {
       return values.get(elem);
    }

    public int Parse(String post)
    {
        if (post.indexOf("?")==-1&&post.indexOf("=")==-1)
            return -1;

        StructHTTP h = new StructHTTP();
        boolean end = false;
        int Down = 0, Up=0,Old_up=0;
        int index = 0;
        if (post.indexOf("?")!=-1)//for GET
        {
            if (post.indexOf("=")==-1)
                return -1;

            post = post.substring(post.indexOf("?")+1);
        }

        while (end==false){
       
        Down = post.indexOf("=", Old_up);
        if (post.indexOf("&", Down)!=-1)
             Up = post.indexOf("&", Down);
        else
        {
            Up = post.length();
            end = true;
        }
        if (Old_up<=0)
            h.param = post.substring(Old_up,Down);
        else
             h.param = post.substring(Old_up+1,Down);

        h.value = post.substring(Down+1,Up);
        Old_up = Up;
        params.add(index, h.param);
        values.add(index, h.value);        
        index++;
        
        }
        return 0;        
    }
    
    @Override
    public String toString(){
        StringBuffer buf = new StringBuffer();
        for (String p:params)
            buf.append(p);
        return buf.toString();
    }
}
