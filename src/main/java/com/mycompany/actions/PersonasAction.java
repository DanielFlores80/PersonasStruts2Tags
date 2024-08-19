/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.actions;

import com.mycompany.model.Persona;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.logging.log4j.*;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author grenn
 */
@Results({
@Result(name = "success", location = "/WEB-INF/content/personas.jsp"),
@Result(name = "input", location = "/WEB-INF/content/personas.jsp")
})
//Se implementa la clase SessionAware para almacenar la lista y no perder información
public class PersonasAction extends ActionSupport implements SessionAware {

    Logger log = LogManager.getLogger(PersonasAction.class);
    Map<String, Object> session;//Mapa del objeto ArrayList para almacenar en la sesión. 
    private Persona persona;
    private List<Persona> personas;//Lista para almacenar a las personas

    //Este metodo permite validar si existe alguna lista en la sesión activa para poder almacenar a cada persona que entre a la clase.
    //Cada persona se valida si no es nula para poder ser almacenada en la lista
    @Action("agregarPersona")
    public String agregarPersona() {
        //Se recupera la lista de la sesión actual
        personas = (List<Persona>) session.get("personas");
        if (personas == null) {
            personas = new ArrayList<>();
        }
        if (persona != null) {//Si es diferente de nulo
            log.info("\n ");
            personas.add(persona);
            log.info("persona: " + persona);
        } else {//Si es nulo 
            log.info("Persona con valor nulo");
        }
        session.put("personas", personas);
        return SUCCESS;
    }

    /*Metodo para validar la entrada de información que hay al momento de guardar la información en la vista, 
    dentro del metodo se verifica que los campos de texto sean texto solamente y el campo numerico no contenga letras,
    En caso de existir un error se agrega un addFieldError a los campos identificados.
    */
    @Override
    public void validate() {
        //Expresiones regulares para texto y numeros
        String nombreRegex = "^[a-zA-Z\\s]+$";
        String calleRegex = "^[a-zA-Z\\s]+$";
        String noCalleRegex = "^[0-9]+$";
        String paisRegex = "^[a-zA-Z\\s]+$";
        if (persona != null) {//Si es diferente de nulo
            //Validaciones con expresiones regulares o nulos para signar error en los campos de la vista
            if (persona.getNombre() == null ) {
                addFieldError("persona.nombre", getText("persona.error.vacio.nombre"));
            }
            if (!Pattern.matches(nombreRegex, persona.getNombre())) {
                addFieldError("persona.nombre", getText("persona.error.nombre"));
            }
            if (persona.getDomicilio().getCalle() == null) {
                addFieldError("persona.domicilio.calle", getText("persona.error.vacio.calle"));
            }
            if (!Pattern.matches(calleRegex, persona.getDomicilio().getCalle())) {
                addFieldError("persona.domicilio.calle", getText("persona.error.calle"));
            }
            if (!Pattern.matches(noCalleRegex, String.valueOf(persona.getDomicilio().getNumeroCalle())) || persona.getDomicilio().getNumeroCalle() <= 0) {
                addFieldError("persona.domicilio.numeroCalle", getText("persona.error.noCalle"));
            }
            if (persona.getDomicilio().getPais() == null) {
                addFieldError("persona.domicilio.pais", getText("persona.error.vacio.pais"));
            }
            if (!Pattern.matches(paisRegex, persona.getDomicilio().getPais())) {
                addFieldError("persona.domicilio.pais", getText("persona.error.pais"));
            }
        }

    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public List<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(ArrayList<Persona> personas) {
        this.personas = personas;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

}
