package de.materna.unicorn.data;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Represents the resources for the application.
 */
public class Resources {
    @Produces
    @PersistenceContext
    private static EntityManager em;
    
    /**
     * @param injectionPoint the injection point.
     * @return an instance of the requested logger.
     */
    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
    
    /**
     * @return current instance of the faces context.
     */
    @Produces
    @RequestScoped
    public FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}
