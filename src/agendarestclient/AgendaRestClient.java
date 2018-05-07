/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendarestclient;

import Objetos.AgendaObjeto;
import Objetos.ImportarExportar;
import Objetos.PersonaObjeto;
import Servicio.ServicioAgenda;
import Servicio.ServicioPersona;
import Servicio.ServicioValidarAgenda;
import Servicio.ServicioValidarPersona;
import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Portatil
 */
public class AgendaRestClient {

    /**
     * @param args the command line arguments
     */
    static Scanner scanner = new Scanner(System.in);
    static ServicioAgenda agendaSesrvice = new ServicioAgenda();
    static ServicioPersona personaService = new ServicioPersona();
    static ServicioValidarAgenda validarAgendaService = new ServicioValidarAgenda();
    static ServicioValidarPersona validarPersonaService = new ServicioValidarPersona();

    public static void main(String[] args) {
        crearMenu();
    }

    public static void crearMenu() {
        System.out.println("1.  Añadir contacto");
        System.out.println("2.  Ver agenda");
        System.out.println("3.  Ver persona");
        System.out.println("4.  Validar agenda");
        System.out.println("5.  Validar persona");
        System.out.println("6.  Salir");
        int opcion = scanner.nextInt();
        comprobar(opcion);
    }

    public static void comprobar(int opcion) {
        switch (opcion) {
            case 1:
                crearContacto();
                break;
            case 2:
                verAgenda();
                break;
            case 3:
                verPersona();
                break;
            case 4:
                validarAgenda();
                break;
            case 5:
                validarPersona();
                break;
            case 6:
                System.exit(0);
        }
    }

    public static void verAgenda() {
        AgendaObjeto a = agendaSesrvice.getXml(AgendaObjeto.class);
        for (PersonaObjeto p : a.getPersonas()) {
            System.out.println("Nombre: " + p.getNombre());
            System.out.println("Telefono: " + p.getTelefono());
            System.out.println("Email: " + p.getEmail());
            System.out.println("--------------");
        }
        crearMenu();
    }

    public static void verPersona() {
        System.out.println("Introduce el nombre: ");
        String nombre = scanner.next();
        PersonaObjeto p = personaService.getXml(PersonaObjeto.class, nombre);
        if (p != null) {
            System.out.println("Nombre: " + p.getNombre());
            System.out.println("Telefono: " + p.getTelefono());
            System.out.println("Email: " + p.getEmail());
        }
        crearMenu();
    }

    public static void crearContacto() {
        System.out.println("Nombre: ");
        String nombre = scanner.next();
        System.out.println("Telefono: ");
        String telefono = scanner.next();
        while (!comprobarNumero(telefono)) {
            System.out.println("El numero de telefono no es valido, vuelve a introducirlo: ");
            telefono = scanner.next();
        }
        System.out.println("email: ");
        String email = scanner.next();
        if (!comprobarEmail(email)) {
            System.out.println("El email introducido no es valido, vuelve a introducirlo: ");
            email = scanner.next();
        }
        Form formulario = new Form();
        formulario.param("nombre", nombre);
        formulario.param("telefono", telefono);
        formulario.param("email", email);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/AgendaRest/webresources/persona");
        PersonaObjeto bean
                = target.request(MediaType.APPLICATION_JSON_TYPE)
                        .post(Entity.entity(formulario, MediaType.APPLICATION_FORM_URLENCODED_TYPE),
                                PersonaObjeto.class);
        System.out.println("Persona insertada");
        crearMenu();
    }

    public static void validarAgenda() {
        System.out.println("Nombre del archivo de la agenda: ");
        String nombre = scanner.next();
        ImportarExportar i = new ImportarExportar(nombre);
        AgendaObjeto a = i.cargar();
        String b = validarAgendaService.putXml(a);
        if (b.equals("true")) {
            System.out.println("Agenda valida");
        } else {
            System.out.println("Agenda no valida");
        }
        crearMenu();
    }

    public static void validarPersona() {
        System.out.println("Nombre del archivo de la persona: ");
        String nombre = scanner.next();
        ImportarExportar i = new ImportarExportar(nombre);
        PersonaObjeto p = i.importarPersona(new File(nombre));
        String b = validarPersonaService.putXml(p);
        if (b.equals("true")) {
            System.out.println("Persona valida");
        } else {
            System.out.println("Persona no valida");
        }
        crearMenu();
    }

    private static boolean comprobarNumero(String numero) {
        Pattern patron = Pattern.compile("\\d{9}");
        Matcher valido = patron.matcher(numero);
        return valido.matches();
    }

    private static boolean comprobarEmail(String email) {
        Pattern patron = Pattern.compile("[\\-a-zA-Z0-9\\.\\+]+@[a-zAZ0-9](\\.?[\\-a-zA-Z0-9]*[a-zA-Z0-9])*");
        Matcher valido = patron.matcher(email);
        return valido.matches();
    }
}
