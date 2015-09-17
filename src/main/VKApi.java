/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static org.apache.http.HttpHeaders.USER_AGENT;
import support.Params;
/**
 *
 * @author Aleks
 */
public class VKApi {    
       private String client_id = "4952101";
       private String client_key = "DrmFt30Q96RMkNnYah2Q";
       private String redirect_uri = "https://localhost/vk_callback";
       private String access_token = "";
     
   public String step(String code) throws IOException, ParseException{       
        HttpClient client = new DefaultHttpClient();
        HttpGet method = new HttpGet("https://oauth.vk.com/access_token?" +
            "client_id="+client_id+"&" +
            "client_secret="+client_key+"&" +
            "code="+code+"&" +
            "redirect_uri="+redirect_uri);
        HttpResponse resp = client.execute(method);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
        JSONParser parser   = new JSONParser();
        JSONObject j = (JSONObject)parser.parse(reader.readLine());
        access_token = j.get("access_token").toString();
        reader.close();    
        return j.toJSONString();
   }
    
    public JSONObject sendMessage(String id,String message) throws IOException, ParseException{

      return  getMethod("messages.send", 
                new Params("user_id", id),
                new Params("message",message)
                );
       
    }
    
    public JSONObject getFriends(String id,String order) throws MalformedURLException, IOException, ParseException{
       
       return getMethod("friends.get",
               new Params("user_id",id),
               new Params("fields", "nickname,online,domain"),
               new Params("order",order)
               );
    }
    
    public JSONObject getFriendsOnline(String id,String order) throws IOException, MalformedURLException, ParseException{
        return getMethod("friends.getOnline",
               new Params("user_id",id),
               new Params("order",order)
               );        
    }
    
    public JSONObject getFriendsLists() throws IOException, MalformedURLException, ParseException{
        return getMethod("friends.getLists");        
    }
    
   public JSONObject addFriendsList(String name,String list) throws IOException, MalformedURLException, ParseException{
        return getMethod("friends.addList",
               new Params("name",name),
               new Params("user_ids",list)
               );        
    }
   
   public JSONObject editFriendsList(String name,String listId,String user_ids,
   String add_user_ids,String delete_user_ids) throws IOException, MalformedURLException, ParseException{
      
       return getMethod("friends.editList",
               new Params("name",name),
               new Params("list_id",listId),
               new Params("user_ids",user_ids),
               new Params("add_user_ids",add_user_ids),
               new Params("delete_user_ids",delete_user_ids)
               );        
    }
    
    private JSONObject getMethod(String method,Params... s) throws MalformedURLException, IOException, ParseException{
        String buf = new String("https://api.vk.com/method/");
        buf = buf.concat(method).concat("?access_token=").concat(access_token);
        for (Params st:s)
            buf = buf.concat("&").concat(st.getName()).concat("=").concat(st.getValue());
        
        String line = "";        
        URL url = new URL(buf);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        line = reader.readLine();
        reader.close();
        System.out.println(line);
        
        JSONParser parser   = new JSONParser();
        return (JSONObject) parser.parse(line);
    }
       
}
