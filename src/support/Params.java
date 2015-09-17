/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package support;

/**
 *
 * @author Aleks
 */
  public class Params extends BasicFunctional{
        private String name;
        private String value;

        public Params() {
        }

        public Params(String name, String value) {
            this.name = name;
            this.value = value;
        }
        
        public String getName(){
            return name;
        }
        
        public String getValue(){
            return value;
        }
        
        public void setName(String name){
            this.name = name;
        }
        
        public void setValue(String value){
            this.value = value;
        }
        
        @Override
        public String toString(){
            return name+":"+value;
        }
    }
