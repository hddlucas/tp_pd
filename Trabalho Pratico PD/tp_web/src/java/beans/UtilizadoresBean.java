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
import java.util.Collection;
import java.util.Date;
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
import javax.xml.bind.annotation.XmlTransient;
import models.Perfil;
import models.Utilizador;

/**
 *
 * @author hddlucas
 */
@Named(value = "utilizadoresBean")
@SessionScoped
public class UtilizadoresBean implements Serializable {

    @EJB
    private UtilizadorFacadeLocal utilizadorFacade;
    private Utilizador user = new Utilizador();
    private Integer idUtilizador;
    private String nome;
    private String username;
    private String password;
    private String bi;
    private String nif;
    private String cidade;
    private String pais;
    private String morada;
    private String contacto;
    private String codigoPostal;
    private Date ultimoLogin;
    private boolean ativo;
    private boolean userRole;

    private Collection<Perfil> perfilCollection;

    /**
     * Creates a new instance of UtilizadoresBean
     */
    public UtilizadoresBean() {
    }

    
       public Utilizador getUser() {
        return this.user;
    }

    public List<Utilizador> getList() {
        return utilizadorFacade.getUsersList();
    }
   
    public String create() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            JsonObjectBuilder userFields = Json.createObjectBuilder();
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            userFields.add("nome", request.getParameter("form:nome"));
            userFields.add("username", request.getParameter("form:username"));
            userFields.add("password", request.getParameter("form:password"));
            userFields.add("bi", request.getParameter("form:bi"));
            userFields.add("nif", request.getParameter("form:nif"));
            userFields.add("morada", request.getParameter("form:morada"));
            userFields.add("contacto", request.getParameter("form:contacto"));
            userFields.add("codigo_postal", request.getParameter("form:codigo_postal"));
            userFields.add("cidade", request.getParameter("form:cidade"));
            userFields.add("pais", request.getParameter("form:pais"));
            if (request.getParameterMap().containsKey("form:ativo_input")) {
                userFields.add("ativo", "1");
            }

            JsonObject fieldsObject = userFields.build();
            this.user = utilizadorFacade.create(fieldsObject.toString());
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Utilizador criado com sucesso"));

        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro ao criar o utilizador"));

            return "create.xhtml?faces-redirect=true?";
        } finally {
            context.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
            return "user.xhtml?faces-redirect=true?";
        }
    }

 
    public String show(Utilizador u) {
        this.user = u;
        return "user.xhtml";
    }

    public String edit(Utilizador u) throws Exception {
        this.user = u;
        return "edit.xhtml";
    }

    public String update(Utilizador u) throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            this.user = u;
            JsonObjectBuilder userFields = Json.createObjectBuilder();
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            System.out.println(request.getParameter("form:ativo"));
            userFields.add("nome", request.getParameter("form:nome"));
            userFields.add("password", request.getParameter("form:password"));
            userFields.add("bi", request.getParameter("form:bi"));
            userFields.add("nif", request.getParameter("form:nif"));
            userFields.add("morada", request.getParameter("form:morada"));
            userFields.add("contacto", request.getParameter("form:contacto"));
            userFields.add("codigo_postal", request.getParameter("form:codigo_postal"));
            userFields.add("cidade", request.getParameter("form:cidade"));
            userFields.add("pais", request.getParameter("form:pais"));
            if (request.getParameterMap().containsKey("form:ativo_input")) {
                userFields.add("ativo", "1");
            }

            JsonObject fieldsObject = userFields.build();
            utilizadorFacade.update(fieldsObject.toString(), this.user.getIdUtilizador());
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Informacao do utilizador atualizada com sucesso"));

        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro ao atualizar a Informacao do utilizador"));
        } finally {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
            return "user.xhtml?faces-redirect=true?";
        }
    }

    public String destroy(int id) throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            utilizadorFacade.destroy(id);
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Utilizador Removido com sucesso"));

        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um problema ao eliminar o utilizador"));

        } finally {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
            return "index.xhtml?faces-redirect=true";
        }
    }

    public boolean hasRole(int userId, String role) {
        return utilizadorFacade.hasRole(userId, role);
    }

    public void addUserRole(int idUtilizador, int roleId) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            utilizadorFacade.addUserRole(idUtilizador, roleId);
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Perfil adicionado ao utilizador com sucesso"));

        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um problema ao adicionar o perfil ao utilizador"));

        } finally {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
            
        }
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Date getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(Date ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    

    public Collection<Perfil> getPerfilCollection() {
        return perfilCollection;
    }

    public void setPerfilCollection(Collection<Perfil> perfilCollection) {
        this.perfilCollection = perfilCollection;
    }
    
   public boolean isUserRole(Perfil p) {
        userRole = this.user.getPerfilCollection().contains(p);
        return userRole;
    }

    public void setUserRole(boolean userRole) {
        this.userRole = userRole;
    }

    //VALIDATORS
    public void validateUsername(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Object oldValue = ((UIInput) component).getValue();
        if (!(value != null ? value.equals(oldValue) : oldValue == null)) {
            List<Utilizador> utilizadores = utilizadorFacade.findUtilizadorByUsername(value.toString());
            if (utilizadores.size() > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O username introduzido já existe");
                throw new ValidatorException(msg);
            }
        }
    }

    public void validateBi(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        Object oldValue = ((UIInput) component).getValue();
        if (!(value != null ? value.equals(oldValue) : oldValue == null)) {
            List<Utilizador> utilizadores = utilizadorFacade.findUtilizadorByBi(value.toString());
            if (utilizadores.size() > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O BI introduzido já existe");
                throw new ValidatorException(msg);
            }
        }
    }

    public void validateNif(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Object oldValue = ((UIInput) component).getValue();
        if (!(value != null ? value.equals(oldValue) : oldValue == null)) {
            List<Utilizador> utilizadores = utilizadorFacade.findUtilizadorByNif(value.toString());
            if (utilizadores.size() > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O NIF introduzido já existe");
                throw new ValidatorException(msg);
            }
        }
    }

}
