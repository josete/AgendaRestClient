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
@XmlRootElement(name="Persona")
public class PersonaObjetoConId implements Serializable{
    
    @XmlElement(name = "id")
    int id;
    @XmlElement(name = "name")
    String nombre;
    @XmlElement(name = "email")
    String email;
    @XmlElement(name = "telephone")
    String telefono;    
    
    public PersonaObjetoConId(){
        
    }

    public PersonaObjetoConId(String nombre, String telefono, String email,int id) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
}
