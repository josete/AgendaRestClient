/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicio;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:ValidarPersona
 * [persona/validar]<br>
 * USAGE:
 * <pre>
 *        ServicioValidarPersona client = new ServicioValidarPersona();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Portatil
 */
public class ServicioValidarPersona {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/AgendaRest/webresources";

    public ServicioValidarPersona() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("persona/validar");
    }

    public String putXml(Object requestEntity) throws ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), String.class);
    }

    public void close() {
        client.close();
    }
    
}
