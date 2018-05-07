/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Portatil
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Agenda")
public class AgendaObjeto implements Serializable{
    
    @XmlElement(name = "Persona")
    ArrayList<PersonaObjeto> personas;

    public AgendaObjeto() {
        personas=new ArrayList<>();
    }

    public AgendaObjeto(ArrayList<PersonaObjeto> personas) {
        this.personas = personas;
    }
    
    public void anadirPersona(PersonaObjeto persona){
        personas.add(persona);
    }

    public ArrayList<PersonaObjeto> getPersonas() {
        return personas;
    }
    
    
}
