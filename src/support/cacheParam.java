/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package support;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 *
 * @author Aleks
 */
 public class cacheParam {
        private String name;
        private String value;
        private String md5;
        private long date;
        private Object obj;
        
        public cacheParam()  {
            this.name = "";
            this.value = "";
            this.date = 0l;
            this.md5 = "";
            
        }

        
         public cacheParam(String name, String value, String md5, long expiredate, Object... o) {
            this.name = name;
            this.value = value;
            this.md5 = md5;
            this.date = expiredate;
            this.obj = o;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }
        
        
        @Override
       public String toString(){
           return this.name+" "+this.value+" "+this.md5+" "+date;
       }
        
        
    }