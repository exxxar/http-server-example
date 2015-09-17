/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import annotatons.Command;
import annotatons.Page;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.ByteMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import entities.Users;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import javassist.CtClass;
import javassist.compiler.MemberResolver;
import javax.imageio.ImageIO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import support.BasicFunctional;
import support.Params;
import support.cacheParam;

/**
 *
 * @author Aleks
 */

public class Pages extends BasicFunctional{
    private final HttpBuffer hBufGet;
    private final HttpBuffer hBufPost;
    private final CacheHandler localCache;
    private final SQL sql;
    private final Socket socket;
    private final List<Params> header;
    private final VKApi vk;
    private final FBApi fb;
  
    public Pages(Socket socket, SQL sql, HttpBuffer hBufGet, HttpBuffer hBufPost, CacheHandler localCache, List<Params> list) {
        this.hBufGet = hBufGet;
        this.hBufPost = hBufPost;
        this.localCache = localCache;
        this.sql = sql;
        this.socket = socket;
        this.header = list;
        this.vk = new VKApi();
        this.fb = new FBApi();
    }
    
    @Page(name = "")
    public String index() throws IOException{        
        return LoadPage("index.html");
    }
    
    @Page(name = "error")
    public String error() throws IOException{
        return LoadPage("404.html");
    }
    
    @Page(name = "about")
    public String about() throws IOException{
        return LoadPage("about.html");
    }
    
    @Page(name = "upload")
    public String uploadFile() throws UnsupportedEncodingException, NoSuchAlgorithmException{
       
        return redirectHTTPS("http://vk.com");
        // выполнить действие
//        if (hBufGet.isSet("md5")){   
//            String md5 = hBufGet.getElement("md5");           
//            cacheParam c = localCache.get("uploadFile",md5); 
//           return "<html> 2 ["+c.getValue()+"] "+c.getMd5()+"</html>";           
//        }
//        String md5 = "";//md5Custom(socket.getInetAddress()+getParam("User-Agent").getValue());        
//        cacheParam c = localCache.get("uploadFile",md5); 
//        if (c.getName().equals(""))
//            return redirectHTTPS("/");
//        return "<html>["+c.getValue()+"]["+c.getMd5()+"] </html>";
    }
    
    
    @Page(name="qrgen")
    public String qrgen(){
        
        System.out.println(hBufGet.getElement("text"));
        
        String filePath = hBufGet.getElement("qr")+".png";
        try {
            String qrCodeText = hBufGet.getElement("qr");
       
        int size = 125;
        String fileType = "png";
        File qrFile = new File(filePath);
        
      
        createQRImage(qrFile, qrCodeText, size, fileType);
        System.out.println("DONE");
        } catch (Exception e) {
        }
        return "<img src='"+filePath+"'>";
    } 
       
    @Page(name="vk_callback")
    public String vkCallback() throws IOException, ParseException{ 
        vk.step(hBufGet.getElement("code"));      
       // vk.sendMessage("14054379", "1111");
        return vk.getFriends("14054379","hints").toJSONString();
    }
   
    @Page(name="vkapi")
    public String vkapi(){        
        return redirectHTTPS("https://oauth.vk.com/authorize?client_id=4952101&scope=4096&redirect_uri=https://localhost/vk_callback&response_type=code&v=5.34&state=1234&display=popup");
    }
    
    
    @Page(name="fb_callback")
    public String fbCallback() throws IOException{
        fb.step2ClientAuth(hBufGet.getElement("code"));
        return "test";
    }
    
    @Page(name="fbapi")
    public String fbapi() throws IOException, ParseException{
       
       // fb.doAppAuth();
         return redirectHTTPS(fb.step1ClientAuth());
    }
    
    @Page(name="message")
    public String message(){
        System.out.println(hBufPost.getElement("name")+" "+hBufPost.getElement("email")+" "+hBufPost.getElement("text"));
        return "1";
    }
    
    @Page(name = "registration")
    public String registration() throws IOException{
    
        if (!hBufPost.isSet("name")||!hBufPost.isSet("pass")||!hBufPost.isSet("email")){
            //TODO: add to cache error name and error id
            return redirectHTTPS("/error");
        }
        
        
        Users u = new Users();
        u.setName(hBufPost.getElement("name"));
        u.setPassword(hBufPost.getElement("pass"));
        u.setEmail(hBufPost.getElement("email"));
        u.setRegDate(new Date());
        sql.addUser(u);
        return LoadPage("about.html");
    }
    
    @Page(name = "login")
    public String login() throws IOException{
        if (!hBufPost.isSet("name")||!hBufPost.isSet("pass"))
            return LoadPage("404.html");
        
        
        return LoadPage("/");
    }
    
    
    @Page(name = "logout")
    public String logout() throws IOException{
        return LoadPage("404.html");
    }
    
    
    @Page(name = "redirect")
    public String redirect() throws IOException{      
        System.err.println("redirect="+hBufGet.getElement("url"));
        return redirectHTTPS(hBufGet.getElement("url"));
    }
    
    
    public String commandHandler(String buf) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        // обработчик команд
        //{$content} {$error} {$menu} {$captcha} {$avatar}
        String dCommands[] = getCommands();//list of commands        
        for (String dCommand : dCommands) {
                while (buf.contains(dCommand)){                   
                    int startInd = buf.indexOf(dCommand);
                    int len = dCommand.length();
                    int endInd = startInd + len;
                    StringBuffer newBuf = new StringBuffer(buf.substring(0,startInd));
                    newBuf.append((String) getCMethod(dCommand));
                    newBuf.append(buf.substring(endInd));
                    buf = newBuf.toString();                   
                   }
        }
             
        return buf;
    }    
        
    @Command(name="{$top-question}")
    public String getTopQuestion(){ 
        
        return "Спроси себя: что сделал я для мира?";
    }
    
    
    @Command(name="{$content}")
    public String getContent(){         
        return "sdfsdf";
    }

    @Command(name="{$error}")
    public String getError(){
        
        return "fdfsdfs";
    }
    
    public String redirectHTTPS(String url){
        return "<html>"+ 
                "<script type=\"text/javascript\">\n" +
                "window.location.href = \""+url+"\"\n" +
                "</script></html>";
    }
        
    
    public Object getMethod(String name, Object... o) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        for (Method m : this.getClass().getMethods()) {              
            if (m.isAnnotationPresent(Page.class)&&((Page)m.getAnnotation(Page.class)).name().equals(name))  {
                    return this.getClass().getMethod(m.getName()).invoke(this,o);
            }
        }
        
        return "<html>Handler not found</html>";
    }
    
    
    public Object getCMethod(String name, Object... o) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        for (Method m : this.getClass().getMethods()) {              
            if (m.isAnnotationPresent(Command.class)&&((Command)m.getAnnotation(Command.class)).name().equals(name))  {
                    return this.getClass().getMethod(m.getName()).invoke(this,o);
            }
        }
        
        return "Handler not found";
    }
    
    public String[] getSelf(){     
        
        List<String> list = new ArrayList<>();        
        for (Method m : this.getClass().getMethods()) {              
            if (m.isAnnotationPresent(Page.class))  {
                    list.add(((Page)m.getAnnotation(Page.class)).name());             
                
            }
        }
               
        
        String s[] = new String[list.size()];
        for (int i=0;i<list.size();i++)
            s[i] = list.get(i);
        return s;
        
    }
    
    public String[] getCommands(){     
        
        List<String> list = new ArrayList<>();        
        for (Method m : this.getClass().getMethods()) {              
            if (m.isAnnotationPresent(Command.class))  {
                    list.add(((Command)m.getAnnotation(Command.class)).name());             
                
            }
        }
               
        
        String s[] = new String[list.size()];
        for (int i=0;i<list.size();i++)
            s[i] = list.get(i);
        return s;
        
    }
}
