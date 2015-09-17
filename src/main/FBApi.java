/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import support.Params;

/**
 *
 * @author Aleks
 */
public class FBApi {
    private String APP_ID = "389834051210786";
    private String APP_SECRET = "32d57613ea773f22091ec033c798d474";
    private String CALLBACK_URL="https://localhost/fb_callback";
    private String grant_type = "client_credentials";
    private String access_token;
    private String app_access_token;
    
    public FBApi() {
    }

    public FBApi(String APP_ID, String APP_SECRET, String access_token) {
        this.APP_ID = APP_ID;
        this.APP_SECRET = APP_SECRET;
        this.access_token = access_token;
    }
    
    public void doAppAuth() throws IOException, ParseException{
        HttpClient client = new DefaultHttpClient();
        HttpGet method = new HttpGet("https://graph.facebook.com/oauth/access_token?client_id="+
                APP_ID+"&client_secret="+
                APP_SECRET+"&grant_type="+
                grant_type);
        
        HttpResponse resp = client.execute(method);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
        app_access_token = reader.readLine();
        reader.close();   
               
    }
    
    public String step1ClientAuth(){
        return "https://www.facebook.com/dialog/oauth?client_id="+APP_ID+"&redirect_uri="+CALLBACK_URL+"&response_type=code"
                + "scope=user_about_me%2Cuser_actions.books%2Cuser_actions.fitness%2Cuser_actions.music%2Cuser_actions.news%2Cuser_actions.video%2Cuser_birthday%2Cuser_education_history%2Cuser_events%2Cuser_friends%2Cuser_games_activity%2Cuser_groups%2Cuser_hometown%2Cuser_likes%2Cuser_location%2Cuser_managed_groups%2Cuser_photos%2Cuser_posts%2Cuser_relationship_details%2Cuser_relationships%2Cuser_religion_politics%2Cuser_status%2Cuser_tagged_places%2Cuser_videos%2Cuser_website%2Cuser_work_history";
    }
    
    public void step2ClientAuth(String code) throws IOException{
     HttpClient client = new DefaultHttpClient();
        HttpGet method = new HttpGet("https://graph.facebook.com/oauth/access_token?client_id="+
                APP_ID+"&redirect_uri="+CALLBACK_URL+"&client_secret="+
                APP_SECRET+"&code="+
                code);
        
        HttpResponse resp = client.execute(method);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
        HttpBuffer localGetBuff = new HttpBuffer();
        localGetBuff.Init();
        localGetBuff.Parse(reader.readLine());
        access_token = localGetBuff.getElement("access_token");       
        reader.close();       
    }
    
    public JSONObject me() throws IOException, MalformedURLException, ParseException{
        return getMethod("me");        
    }

     private JSONObject getMethod(String method,Params... s) throws MalformedURLException, IOException, ParseException{
        String buf = new String("https://graph.facebook.com/");
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
