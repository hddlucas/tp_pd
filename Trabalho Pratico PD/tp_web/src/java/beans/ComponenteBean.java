/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controllers.CategoriaFacadeLocal;
import controllers.ComponenteFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import models.Categoria;
import models.Componente;
import models.Utilizador;

/**
 *
 * @author marcosequeira
 */
@Named(value = "componenteBean")
@SessionScoped
public class ComponenteBean implements Serializable {

    @EJB
    private CategoriaFacadeLocal categoriaFacade;

    @EJB
    private ComponenteFacadeLocal componenteFacade;

    private Componente componente = new Componente();
    private Integer idComponente;
    private Integer idCategoria;
    private String nome;
    private String observacoes;
    private Integer avaliacao;
    private BigInteger valor;
    private List<Categoria> categorias;
    
    /**
     * Creates a new instance of ComponenteBean
     */
    public ComponenteBean() {   
    }  
    
    public List<Componente> getList() {
        return componenteFacade.getComponentsList();
    }
    
    public String edit(Componente c) throws Exception {
        this.componente = c;
        return "edit.xhtml";
    }
    
    
    public String show(Componente c) {
        this.componente = c;
        return "componente.xhtml";
    }

    
    public String create() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            JsonObjectBuilder componenteFields = Json.createObjectBuilder();
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                        
            componenteFields.add("nome", request.getParameter("form:nome"));
            componenteFields.add("observacoes", request.getParameter("form:observacoes"));
            componenteFields.add("idCategoria", request.getParameter("form:categoria_input"));

            JsonObject fieldsObject = componenteFields.build();
            this.componente = componenteFacade.create(fieldsObject.toString());
            
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Componente criada com sucesso"));
        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", ex.toString()));

            return "create.xhtml?faces-redirect=true?";
        } finally {
            context.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
            return "index.xhtml?faces-redirect=true?";
        }
    }
    
    
    public List<Categoria> getCategorias() {
        this.categorias = categoriaFacade.getCategoriesList();

        return categorias;
    }
    
    
    
    public String update(Componente c) throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            this.componente = c;
           
            JsonObjectBuilder componenteFields = Json.createObjectBuilder();
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            
            componenteFields.add("nome", request.getParameter("form:nome"));
            componenteFields.add("observacoes", request.getParameter("form:observacoes"));
            componenteFields.add("idCategoria", request.getParameter("form:categoria_input"));

            JsonObject fieldsObject = componenteFields.build();
            componenteFacade.update(fieldsObject.toString(), c.getIdComponente());
                       
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Informação do componente atualizada com sucesso"));

        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", ex.toString()));
        } finally {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
            return "index.xhtml?faces-redirect=true?";
        }
    }
    
    
    public String destroy(int id) throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            componenteFacade.destroy(id);
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Categoria Removida com sucesso"));

        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um problema ao eliminar a Categoria"));

        } finally {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
            return "index.xhtml?faces-redirect=true";
        }
    }
    
    
    //PROPERTIES
    public Componente getComponente() {
        return componente;
    }

    public Integer getIdComponente() {
        return idComponente;
    }

    public String getNome() {
        return nome;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public Integer getAvaliacao() {
        return avaliacao;
    }

    public BigInteger getValor() {
        return valor;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }
    
    
    
}
