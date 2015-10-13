package org.primefaces.showcase.view.ajax;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
 
@ManagedBean
@ViewScoped
public class Categorie implements Serializable {
    
    
    @PostConstruct
    public void init() {
        List<String> cat= new ArrayList<String>();
        cat.add("Fromage");
        cat.add("Saucisson");
        cat.add("Pâté");        
    }
 
    
}