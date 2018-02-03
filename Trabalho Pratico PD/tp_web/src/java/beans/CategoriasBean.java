/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controllers.CategoriaFacadeLocal;
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
import models.Categoria;
import models.Utilizador;

/**
 *
 * @author hddlucas
 */
@Named(value = "categoriasBean")
@SessionScoped
public class CategoriasBean implements Serializable {

    @EJB
    private CategoriaFacadeLocal categoriaFacade;
    private Categoria category = new Categoria();
    private Integer idCategoria;
    private String nome;

    /**
     * Creates a new instance of CategoriasBean
     */
    public CategoriasBean() {
    }

    public List<Categoria> getList() {
        return categoriaFacade.getCategoriesList();
    }

    public String create() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            JsonObjectBuilder categoryFields = Json.createObjectBuilder();
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            categoryFields.add("nome", request.getParameter("form:nome"));

            JsonObject fieldsObject = categoryFields.build();
            this.category = categoriaFacade.create(fieldsObject.toString());
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Categoria criada com sucesso"));

        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro ao criar a categoria"));

            return "create.xhtml?faces-redirect=true?";
        } finally {
            context.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
            return "category.xhtml?faces-redirect=true?";
        }
    }

    public Categoria getCategory() {
        return this.category;
    }

    public String show(Categoria c) {
        this.category = c;
        return "category.xhtml";
    }

    public String edit(Categoria c) throws Exception {
        this.category = c;
        return "edit.xhtml";
    }

    public String update(Categoria c) throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            this.category = c;
            JsonObjectBuilder categoryFields = Json.createObjectBuilder();
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            categoryFields.add("nome", request.getParameter("form:nome"));

            JsonObject fieldsObject = categoryFields.build();
            categoriaFacade.update(fieldsObject.toString(), this.category.getIdCategoria());
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Informacao da categoria atualizada com sucesso"));

        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro ao atualizar a Informacao da categoria"));
        } finally {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
            return "category.xhtml?faces-redirect=true?";
        }
    }

    public String destroy(int id) throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            categoriaFacade.destroy(id);
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

    //PROPRIEDADES
    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    //VALIDATORS
    public void validateNome(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Object oldValue = ((UIInput) component).getValue();
        if (!(value != null ? value.equals(oldValue) : oldValue == null)) {
            List<Categoria> categorias = categoriaFacade.findCategoriaByNome(value.toString());
            if (categorias.size() > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "A categoria introduzida já existe");
                throw new ValidatorException(msg);
            }
        }
        if(value.toString().length() < 4) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "A categoria introduzida deve possuir mais de 4 caracteres");
            throw new ValidatorException(msg);
        }
    }
}
