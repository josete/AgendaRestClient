/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Portatil
 */
public class ImportarExportar {
    
    File agenda;

    public ImportarExportar(String nombre) {
        agenda = new File(nombre);
    }
    
    public File guardar(AgendaObjeto agenda){
        try {
            JAXBContext context = JAXBContext.newInstance(AgendaObjeto.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(agenda, this.agenda);
            return this.agenda;
        } catch (JAXBException ex) {
            Logger.getLogger(ImportarExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void exportarPersona(PersonaObjeto persona,String nombreArchivo){
        try {
            JAXBContext context = JAXBContext.newInstance(PersonaObjeto.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            File XMLfile = new File(nombreArchivo+".xml");
            marshaller.marshal(persona, XMLfile);
        } catch (JAXBException ex) {
            Logger.getLogger(ImportarExportar.class.getName()).log(Level.SEVERE, null, ex);
        }catch(NullPointerException ex){}
    }
    
    public File guardarPersona(PersonaObjeto persona){
        try {
            JAXBContext context = JAXBContext.newInstance(PersonaObjeto.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            File f = new File(persona.getNombre()+".xml");
            marshaller.marshal(persona, f);
            return f;
        } catch (JAXBException ex) {
            Logger.getLogger(ImportarExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public PersonaObjeto importarPersona(File persona){
        try {
            System.out.println("Cargando persona ...");
            JAXBContext jaxbC = JAXBContext.newInstance(PersonaObjeto.class);
            Unmarshaller jaxbU = jaxbC.createUnmarshaller();
            PersonaObjeto p = (PersonaObjeto) jaxbU.unmarshal(persona);
            return p;
        } catch (JAXBException ex) {
            Logger.getLogger(ImportarExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public AgendaObjeto cargar(){
        try {
            System.out.println("Cargando agenda ...");
            JAXBContext jaxbC = JAXBContext.newInstance(AgendaObjeto.class);
            Unmarshaller jaxbU = jaxbC.createUnmarshaller();
            AgendaObjeto a = (AgendaObjeto) jaxbU.unmarshal(agenda);
            return a;
        } catch (JAXBException ex) {
            //Logger.getLogger(ImportarExportar.class.getName()).log(Level.SEVERE, null, ex);
            return new AgendaObjeto();
        }
        //return null;
        //return null;
    }
    
    public AgendaObjeto comprobarSiExisteAgenda(){
        if(agenda.exists() && !agenda.isDirectory()){
            return cargar();
        }
        return null;
    }
    
}
