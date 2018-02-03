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
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import models.Utilizador;

/**
 *
 * @author hddlucas
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    /**
     * Creates a new instance of LoginBean
     */
    @EJB
    private UtilizadorFacadeLocal utilizadorFacade;
    private Utilizador logedUser = new Utilizador();

    private Integer idUtilizador;
    String username;
    String password;
    private String nome;
    private String bi;
    private String nif;
    private String cidade;
    private String pais;
    private String morada;
    private String contacto;
    private String codigoPostal;
    private Date ultimoLogin;
    private boolean ativo;

    public LoginBean() {
    }

    public Utilizador getLoggedUser() {
        return this.logedUser;
    }

    public String login() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext context = FacesContext.getCurrentInstance();

        boolean logged = utilizadorFacade.login(request.getParameter("form:username"), request.getParameter("form:password"));
        if (!logged) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Login inválido!"));
            return null;
        } else {
            List<Utilizador> utilizadores = utilizadorFacade.findUtilizadorByUsername(request.getParameter("form:username"));
            if (!utilizadores.get(0).getAtivo()) {
                context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Por favor, Aguarde aprovação de um Administrador!"));
                return null;
            }

            this.logedUser = utilizadores.get(0);
            return "/index.xhtml?faces-redirect=true?";
        }

    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        this.logedUser = null;
        context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Terminou a sua sessão. Obrigado pela sua visita"));
        context.getCurrentInstance()
                .getExternalContext()
                .getFlash().setKeepMessages(true);

        return "/authentication/login.xhtml?faces-redirect=true?";
    }

    public String register() {

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
            userFields.add("pais", request.getParameter("form:pais_input"));
            if (request.getParameterMap().containsKey("form:tipo_de_conta")) {
                userFields.add("tipo_de_conta", request.getParameter("form:tipo_de_conta"));
            }

            JsonObject fieldsObject = userFields.build();
            utilizadorFacade.create(fieldsObject.toString());

            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Utilizador criado com sucesso"));

        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro ao criar o utilizador"));
            return null;
        } finally {
            context.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
            return "/authentication/login.xhtml?faces-redirect=true?";
        }
    }

    public String showUserInfo(Utilizador u) {
        this.logedUser = u;
        return "/authentication/userProfile.xhtml?faces-redirect=true?";
    }
    
     public String edit(Utilizador u) {
        this.logedUser = u;
        return "/authentication/editProfile.xhtml?faces-redirect=true?";
    }
     
       public String updateUserProfile(Utilizador u) throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            this.logedUser = u;
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
            userFields.add("ativo", this.logedUser.getAtivo());

      
            JsonObject fieldsObject = userFields.build();
            utilizadorFacade.update(fieldsObject.toString(), this.logedUser.getIdUtilizador());
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "A sua informação foi atualizada com sucesso"));

        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro ao atualizar a sua Informacao"));
        } finally {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
            return this.showUserInfo(this.logedUser);
        }
    }
    
    
    //PROPRIEDADES
    public Integer getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(Integer idUtilizador) {
        this.idUtilizador = idUtilizador;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    //VALIDATORS
    public void validateUsername(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        List<Utilizador> utilizadores = utilizadorFacade.findUtilizadorByUsername(value.toString());
        if (utilizadores.size() < 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O username introduzido não existe");
            throw new ValidatorException(msg);

        }
    }

}
