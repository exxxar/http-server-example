package entities;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="users")
public class Users {

    private Long id;
    private String domain;//id | domain
    private String name;
    private String password;
    private String email;
    private String phone;
    private Date regDate;

    
    public Users() {
        this.domain = "test domain";
        this.name = "test domain";
        this.password = "test domain";
        this.email = "test domain";
        this.phone = "test domain";     
        this.regDate = new Date();
       
    }

    public Users(Long id, String domain, String name, String password, String email, String phone, Date regDate) {
        this.id = id;
        this.domain = domain;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.regDate = regDate;
    }

 
    
    
    
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "user_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Column
     public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="domain")
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Column(name="password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name="email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Column(name="phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
   
    @Column(name="registration_date")
    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

       
    
}
