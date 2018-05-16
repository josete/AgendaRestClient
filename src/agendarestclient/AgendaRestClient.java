/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendarestclient;

import Objetos.AgendaObjeto;
import Objetos.ImportarExportar;
import Objetos.ListaAgendas;
import Objetos.PersonaObjeto;
import Objetos.Usuario;
import Servicio.ServicioAgenda;
import Servicio.ServicioLogin;
import Servicio.ServicioPersona;
import Servicio.ServicioRegistro;
import Servicio.ServicioValidarAgenda;
import Servicio.ServicioValidarPersona;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
    static ServicioLogin loginService = new ServicioLogin();
    static ServicioRegistro registroService = new ServicioRegistro();
    static File tokenFile;
    static String token = null;
    static int numeroAgenda = 0;

    public static void main(String[] args) {
        crearMenu();
    }

    public static void crearMenu() {
        tokenFile = new File("token.tk");
        if (tokenFile.exists() && !tokenFile.isDirectory()) {
            if (token == null) {
                token = leerToken();
            }
            crearMenuConToken();
        } else {
            crearMenuSinToken();
        }
    }

    public static void crearMenuConToken() {
        System.out.println("1. Ver agendas");
        System.out.println("2. Ver agenda");
        System.out.println("3. Ver persona");
        System.out.println("4. Modificar persona");
        System.out.println("5. Borrar persona");
        System.out.println("6. Crear persona");
        System.out.println("7. Salir");
        int opcion = scanner.nextInt();
        comprobarConToken(opcion);
    }

    public static void crearMenuSinToken() {
        System.out.println("1.  Entrar");
        System.out.println("2.  Registrarse");
        System.out.println("3.  Validar agenda");
        System.out.println("4.  Validar persona");
        System.out.println("5.  Salir");
        int opcion = scanner.nextInt();
        comprobarSinToken(opcion);
    }

    public static void comprobarSinToken(int opcion) {
        switch (opcion) {
            case 1:
                entrar();
                break;
            case 2:
                registro();
                break;
            case 3:
                validarAgenda();
                break;
            case 4:
                validarPersona();
                break;
            case 5:
                System.exit(0);
        }
    }

    public static void comprobarConToken(int opcion) {
        switch (opcion) {
            case 1:
                verAgendas();
                break;
            case 2:
                verAgenda();
                break;
            case 3:
                verPersona();
                break;
            case 4:
                modificarPersona();
                break;
            case 5:
                borrarPersona();
                break;
            case 6:
                crearContacto();
                break;
            case 7:
                tokenFile.delete();
                System.exit(0);
        }
    }

    public static void entrar() {
        System.out.println("Introduce tu email: ");
        String email = scanner.next();
        System.out.println("Introduce tu contraseña: ");
        String password = scanner.next();
        String token = loginService.putXml(new Usuario(email, password));
        if (token.length() > 0) {
            escribirToken(token);
            crearMenu();
        } else {
            System.out.println("El email o la contraseña no son correctos");
            crearMenu();
        }
    }

    public static void registro() {
        System.out.println("Introduce tu email: ");
        String email = scanner.next();
        System.out.println("Introduce tu contraseña: ");
        String password = scanner.next();
        registroService.putXml(new Usuario(email, password));
        System.out.println("Usuario creado,ya puede iniciar sesión");
        crearMenuSinToken();
    }

    public static void modificarPersona(){
        System.out.println("Introduce el id de la persona:");
        int id = scanner.nextInt();
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
        PersonaObjeto p = new PersonaObjeto(nombre, telefono, email);
        personaService.actualizar(p, String.valueOf(id),token);
    }
    
    public static void borrarPersona(){
        System.out.println("Introduce el id de la persona:");
        int id = scanner.nextInt();
        personaService.borrar(String.valueOf(id), token);
    }
    public static void verAgendas() {
        ListaAgendas a = agendaSesrvice.getXml(ListaAgendas.class, token);
        int i = 1;
        for (String ag : a.getAgendas()) {
            System.out.println(i + ". " + ag);
            i++;
        }
        System.out.println("Selecciona una agenda: ");
        int opcion = scanner.nextInt();
        numeroAgenda = opcion;
        System.out.println("Seleccionada la agenda " + numeroAgenda);
        crearMenu();
    }

    public static void verAgenda() {
        if (numeroAgenda != 0) {
            AgendaObjeto a = agendaSesrvice.getAgenda(AgendaObjeto.class, String.valueOf(numeroAgenda), token);
            for (PersonaObjeto p : a.getPersonas()) {
                System.out.println("Nombre: " + p.getNombre());
                System.out.println("Telefono: " + p.getTelefono());
                System.out.println("Email: " + p.getEmail());
                System.out.println("---------------------------");
            }
            crearMenu();
        } else {
            System.out.println("No has seleccionado una agenda");
            crearMenu();
        }
    }

    public static void verPersona() {
        System.out.println("Introduce el nombre: ");
        String nombre = scanner.next();
        AgendaObjeto a = personaService.getXml(AgendaObjeto.class, String.valueOf(numeroAgenda), nombre, token);
        for (PersonaObjeto p : a.getPersonas()) {
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
        PersonaObjeto p = new PersonaObjeto(nombre, telefono, email);
        personaService.putXml(p, String.valueOf(numeroAgenda),token);
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

    private static void escribirToken(String token) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("token.tk");
            pw = new PrintWriter(fichero);
            pw.println(token);
        } catch (IOException e) {
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (IOException e2) {
            }
        }

    }

    private static String leerToken() {
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            fr = new FileReader(tokenFile);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                return linea;
            }
        } catch (IOException e) {
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (IOException e2) {
            }
        }
        return null;

    }
}
