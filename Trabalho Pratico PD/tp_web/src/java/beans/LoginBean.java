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
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
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
    String username;
    String password;
    private Utilizador logedUser = new Utilizador();

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

    //PROPRIEDADES
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
