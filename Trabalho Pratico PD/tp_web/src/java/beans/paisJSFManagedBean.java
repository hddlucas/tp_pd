package beans;
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
 
@ManagedBean
@ViewScoped
public class paisJSFManagedBean implements Serializable {
     
    private Map<String,Map<String,String>> data = new HashMap<String, Map<String,String>>();
    private String country; 
    private String city;  
    private Map<String,String> countries;
    private Map<String,String> cities;
     
    @PostConstruct
    public void init() {
        countries  = new HashMap<String, String>();
        countries.put("Portugal","Portugal");    
        countries.put("Espanha","Espanha");    
        countries.put("Franca","Franca");    


    }
 
 
    public String getCountry() {
        return country;
    }
 
    public void setCountry(String country) {
        this.country = country;
    }
 
    public Map<String, String> getCountries() {
        return countries;
    }
 
    
    


}