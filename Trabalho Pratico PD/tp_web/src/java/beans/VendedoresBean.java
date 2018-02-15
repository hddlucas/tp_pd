/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controllers.UtilizadorFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import models.AvaliacaoVendedor;
import models.Categoria;
import models.Proposta;
import models.Utilizador;

/**
 *
 * @author hddlucas
 */
@Named(value = "vendedoresBean")
@SessionScoped
public class VendedoresBean implements Serializable {

    @EJB
    private UtilizadorFacadeLocal utilizadorFacade;
    private Utilizador user = new Utilizador();
    private Integer idUtilizador;
    private String nome;

    /**
     * Creates a new instance of CategoriasBean
     */
    public VendedoresBean() {
    }

    public List<Utilizador> getList() {
        List<Utilizador> allUsers = utilizadorFacade.getUsersList();
       
        
        utilizadorFacade.getUsersList().forEach((k) -> {
            if (hasRole(k.getIdUtilizador(), "vendedor") == false) {
                allUsers.remove(k);
            }
        });

        return allUsers;
    }
    
    public int getTotalVendasVendedor(Utilizador vendedor){
        return utilizadorFacade.getTotalVendasVendedor(vendedor);
    }

    public List<Proposta> getVendas(Utilizador vendedor){
        return utilizadorFacade.getVendas(vendedor);
    }
    
    public List<AvaliacaoVendedor> getAvaliacoes(Utilizador vendedor){
        return utilizadorFacade.getAvaliacaoList(vendedor);
    }
    
    
    
    public String show(Utilizador u) {
        this.user = u;
        return "salesman.xhtml";
    }

    public boolean hasRole(int userId, String role) {
        return utilizadorFacade.hasRole(userId, role);
    }

    public Utilizador getUser() {
        return this.user;
    }

    //PROPRIEDADES
    public Integer getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(Integer idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
