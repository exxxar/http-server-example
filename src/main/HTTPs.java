/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.security.KeyStore;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author Aleks
 */
public class HTTPs extends Thread{
    
    private int HTTPS_PORT = 443;
     private CacheHandler cacheHandler;
    private String keystore = "key1_test.jks";
    private char keystorepass[] = "123456".toCharArray();
    private char keypassword[] = "123456".toCharArray();
    public HTTPs(CacheHandler c) throws Throwable {
      this.cacheHandler = c;
            
    }
    
    public void setKeystorePath(String path) {
     this.keystore = path;
    }
    
    public void setKeyStorePass(String... pass) {
        
        for (int i=0;i<pass.length;i++)
            switch(i){
                case 0: this.keypassword =  pass[0].toCharArray(); break;
                case 1: this.keystorepass = pass[1].toCharArray(); break;        
            }
     
    }
     
    public ServerSocket getServerSocket(){
        try {
        KeyStore ks = getKeyStore();
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, keypassword);
        SSLContext sslcontext = SSLContext.getInstance("SSLv3");
        sslcontext.init(kmf.getKeyManagers(), null, null);
        ServerSocketFactory ssf = sslcontext.getServerSocketFactory(); 
        SSLServerSocket serversocket = (SSLServerSocket)ssf.createServerSocket(HTTPS_PORT);
        return serversocket;
        }
        catch (Exception e){
        
        }
        return null;
    }
   
    private KeyStore getKeyStore() throws Exception {
         KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
         ks.load(new FileInputStream(keystore), keystorepass);
         return ks;       
    }

    @Override
    public void run() {
        super.run();
        
        handleConnection();
    }

    public void handleConnection()  {
        System.out.println("HTTPs Server Started");
        ServerSocket listen;        
        try {
         listen = getServerSocket();
         ConnectionHandler ch = null;
         while (true) {
            SSLSocket socket = (SSLSocket)listen.accept();   
            ch = new ConnectionHandler(socket,cacheHandler);
            ch.run();
            
         }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
    
}
