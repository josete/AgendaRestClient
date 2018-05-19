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
public class AgendaObjetoConId implements Serializable{
    
    @XmlElement(name = "Persona")
    ArrayList<PersonaObjetoConId> personas;

    public AgendaObjetoConId() {
        personas=new ArrayList<>();
    }

    public AgendaObjetoConId(ArrayList<PersonaObjetoConId> personas) {
        this.personas = personas;
    }
    
    public void anadirPersona(PersonaObjetoConId persona){
        personas.add(persona);
    }

    public ArrayList<PersonaObjetoConId> getPersonas() {
        return personas;
    }
    
    
}
