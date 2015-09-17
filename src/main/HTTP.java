/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Vector;
import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author Aleks
 */
public class HTTP extends Thread {
    private int HTTP_PORT = 8280;
    private CacheHandler cacheHandler;
    public HTTP(CacheHandler c) throws Throwable {
            this.cacheHandler = c;
    }
    
    public HTTP(int port,CacheHandler c) throws Throwable {
        this.HTTP_PORT = port;
        this.cacheHandler = c;
    }
   
    public ServerSocket getServerSocket() throws Exception {      
        return new ServerSocket(HTTP_PORT);
    }
      
    @Override
    public void run() {
        super.run();
        handleConnection();
    }

    public void handleConnection() {
        System.out.println("HTTP Server Started");
        ServerSocket listen;        
        try {
         listen = getServerSocket();
         ConnectionHandler ch = null;
         while (true) {
            Socket socket = listen.accept();   
            ch = new ConnectionHandler(socket,cacheHandler);
            ch.run();
         }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    
}
