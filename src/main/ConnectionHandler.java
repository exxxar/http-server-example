/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import annotatons.Page;
import entities.Users;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;
import support.BasicFunctional;
import support.Params;
import support.cacheParam;

/**
 *
 * @author Aleks
 */
public class ConnectionHandler extends BasicFunctional{
  
    private final Socket socket;
    private final BufferedWriter writer;
    private final BufferedReader reader;
    private final List<Params> header; //headers for non-page items
    private final HttpBuffer hBufGet;
    private final HttpBuffer hBufPost;
    private final CacheHandler localCache;
    private final SQL sql;
    private final Pages pages;

    public ConnectionHandler(Socket socket, CacheHandler cache) throws IOException, UnsupportedEncodingException, NoSuchAlgorithmException {
        this.socket = socket;
        this.writer = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
        this.reader = new BufferedReader( new InputStreamReader(socket.getInputStream(),"cp1251"));
        header = new ArrayList<>();
        hBufGet = new HttpBuffer();
        hBufGet.Init();
        hBufPost = new HttpBuffer();
        hBufPost.Init();
        localCache = cache;
        sql = new SQL();
        pages = new Pages(socket,sql,hBufGet,hBufPost,localCache,header);
    }

    public void run() {            
        try {
           
           String badExt[] = {"html","php","htm"};
           String ext = null;
           String path = null;
           readRequest();
                    
           path =getParam("path").getValue();  
             if (path.contains(".")) {
                 ext = path.substring(path.lastIndexOf(".")+1);
                 for (String s:badExt)
                     if (ext.toLowerCase().equals(s)){
                         setParam("path", "error");
                         ext = null;
                         break;
                     }

             }     
             
        //   sql.updateOnline(md5Custom(socket.getInetAddress()+getParam("User-Agent").getValue()));
           
           if (ext!=null)
                SendFile(socket, path);
            else
                 PageProcessor();
           
           
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable ex) {
            ex.printStackTrace();
                   
        }
         
    }
    
    
    public void readRequest() throws IOException, UnsupportedEncodingException, NoSuchAlgorithmException{
        int index = 0;
        boolean hOver = false;
        String r = null;
        String fileName = null;
        boolean first = false;
        List<int[]> f =  new ArrayList<int[]>();
        while(true) { 
            try {
                r = reader.readLine();
            } catch (Exception e) {
            }
            

            if (r==null)
               return;
            if (index==0){
                
                String[] parts = r.split(" ");
                String method = parts[0];                 
                String path = parts[1].substring(1);
                String protocol = parts[2];  
               
                header.add(new Params("method", method));
                if (path.contains("?")){
                    header.add(new Params("path", path.substring(0,path.indexOf("?"))));
                    hBufGet.Parse(path);
                }
                    else
                        header.add(new Params("path", path));
                header.add(new Params("protocol", protocol)); 
                
            }
            
            if (index>0&&!hOver) { 
                if (r.contains(":")){
                    String[] parts = r.split(":");                    
                    header.add(new Params(parts[0],parts[1]));
                   // System.err.println(parts[0]+":"+parts[1]);
                }
                else                    
                    if (r.trim().equals(""))
                        hOver=true;                 
                    
            }
            
            
            if (hOver&&!getParam("Content-Type").getValue().contains("multipart/form-data")){
                    String s = new String("");
                    while(reader.ready())
                           s +=""+(char)reader.read();
                    hBufPost.Parse(s);
                    break;
                    
            }

            if (hOver&&getParam("Content-Type").getValue().contains("multipart/form-data")){
                String boundary = getParam("Content-Type").getValue();
                boundary = boundary.split("=")[1];
                String fileHeader[] = new String[3];
                for (int i=0;i<3;i++){
                    fileHeader[i]=reader.readLine();
                    System.err.println("h["+i+"]="+fileHeader[i]);
                }
                reader.readLine();
                fileName = fileHeader[1].split("filename=\"")[1];
                fileName = fileName.replaceAll("\"","").trim();
                localCache.add("uploadFile", fileName, md5Custom(socket.getInetAddress()+getParam("User-Agent").getValue()), (new Date()).getTime()+10000l);
                while(reader.ready()){
                                  
                    IntStream n = reader.readLine().codePoints();               
                    
                    f.add( n.toArray());
                    int sub[] = {10};//??? modify it! 
                    f.add(sub);

                }
                for (int i=0;i<3;i++)
                    f.remove(f.size()-1);              
            }
             
            index++;
            if (!reader.ready())
                break;
        }
           
        if (fileName!=null&&!fileName.trim().equals("")) {
           
            BufferedWriter bos =  bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),"windows-1251"));
            for (int[] n:f)
                 for (int kd:n)
                        bos.write(kd);
              bos.close();
        }
        localCache.show();
        System.out.println(getParam("path").getName()+":"+getParam("path").getValue());
    }
        
    public Params getParam(String name){
      
        for (Params h:header)
            if (h.getName().equals(name.trim()))
                return h;       
        
        return new Params("","");
    }
    
    public void setParam(String name,String value){
   
            for (int i=0;i<header.size();i++)
                if (header.get(i).getName().equals(name.trim()))
                    header.get(i).setValue(value);
       
        
    }
     

    private void PageProcessor() throws Throwable
        {     
           String page = getParam("path").getValue();   
           String buf = null;
           boolean pageExist = false;       
           String dPages[] = pages.getSelf();        
           if (!pageExist){
               for (String dPage : dPages) {
                   if (dPage.equals(page.trim().toLowerCase())) {
                       buf = (String) pages.getMethod(dPage);
                       pageExist = true;
                       break;
                   }
               }
            }         
           
            if (!pageExist){
                //TODO: add domain and user_id test\filter
            }              
            
            if (!pageExist)
                buf = pages.redirectHTTPS("/error");
            
            buf = pages.commandHandler(buf);
            writeResponse(buf);
        }
       

    private void writeResponse(String s) throws Throwable {
            if (socket.isClosed()||!socket.isConnected()||socket.isOutputShutdown())
                return;
            
            StringBuffer buf = new StringBuffer();
            String response = "HTTP/1.0 200 OK\r\n"+
                    "Server: YarServer/2009-09-09\r\n" +
                    "Content-Type: text/html\r\n" +
                   // "Content-Length: " + s.length()+ "\r\n" +
                    "Connection: keep-alive\r\n\r\n";
            
            buf.append(response);
            buf.append(s);
            try {
                writer.write(buf.toString());
                writer.flush();
                writer.close();
             } catch (Exception e) {
                socket.close();
             }  
        }
    
    
    
    
  }