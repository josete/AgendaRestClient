/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Portatil
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Usuario")
public class Usuario implements Serializable{

    public Usuario() {
    }
    
    @XmlElement(name = "email")
    public String email;
    @XmlElement(name = "password")
    public String password;

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    
}
