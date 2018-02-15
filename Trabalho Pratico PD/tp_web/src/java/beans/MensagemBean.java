/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controllers.MensagemFacadeLocal;
import controllers.UtilizadorFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.http.HttpServletRequest;
import models.Mensagem;
import models.Utilizador;

/**
 *
 * @author hddlucas
 */
@Named(value = "mensagemBean")
@SessionScoped
public class MensagemBean implements Serializable {

    @EJB
    private UtilizadorFacadeLocal utilizadorFacade;

    @EJB
    private MensagemFacadeLocal mensagemFacade;

    private Mensagem message = new Mensagem();
    private Integer idMensagem;
    private int idRemetente;
    private String assunto;
    private String mensagem;
    private Boolean lida;
    private Boolean eliminadaRemetente;
    private Boolean eliminadaDestinatario;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    private Utilizador idDestinatario;
    private String[] selectedUsers;
    private List<String> users;

    /**
     * Creates a new instance of MensagemBean
     */
    public MensagemBean() {
    }

    public Mensagem getMessage() {
        return this.message;
    }

    public String showUserMessages(Utilizador u) {
        return "/messages/index.xhtml?faces-redirect=true?";
    }

    public List<Mensagem> getList(Utilizador u) {
        return mensagemFacade.getUsersMessagesList(u.getIdUtilizador());
    }

    public List<String> getUsers() {
        users = new ArrayList();
        List<Utilizador> utilizadores = utilizadorFacade.getUsersList();
        utilizadores.forEach((k) -> {
            users.add(k.getUsername());
        });

        return users;
    }

    public String[] getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(String[] selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    public String show(Mensagem m) {
        this.message = m;
        if (!this.message.getLida()) {
            mensagemFacade.markAsRead(this.message.getIdMensagem());
            this.message.setLida(true);
        }
        return "message.xhtml";
    }

    public String create() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String[] destinatarios = request.getParameterValues("form:users");

            for (String destinatario : destinatarios) {
                JsonObjectBuilder messageFields = Json.createObjectBuilder();
                messageFields.add("id_remetente", request.getParameter("form:id_remetente"));
                messageFields.add("assunto", request.getParameter("form:assunto"));
                messageFields.add("mensagem", request.getParameter("form:mensagem"));
                messageFields.add("destinatario", destinatario);
                JsonObject fieldsObject = messageFields.build();

                mensagemFacade.create(fieldsObject.toString());
            }

            if (destinatarios.length > 1) {
                context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Mensagens enviadas com sucesso"));
            } else {
                context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Mensagem enviada com sucesso"));
            }

        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", ex.getMessage()));

            return "index.xhtml?faces-redirect=true?";
        } finally {
            context.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
            return "index.xhtml?faces-redirect=true?";
        }
    }

    public void sendNotification() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            JsonObjectBuilder messageFields = Json.createObjectBuilder();
            messageFields.add("id_remetente", request.getParameter("message-form:id_remetente"));
            messageFields.add("assunto", request.getParameter("message-form:assunto"));
            messageFields.add("mensagem", request.getParameter("message-form:mensagem"));
            messageFields.add("destinatario", request.getParameter("message-form:id_destinatario"));
            JsonObject fieldsObject = messageFields.build();

            mensagemFacade.sendNotification(fieldsObject.toString());

            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Mensagem enviada com sucesso"));

        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", ex.getMessage()));

        } finally {
            context.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
        }
    }

    //PROPRIEDADES
    public Integer getIdMensagem() {
        return idMensagem;
    }

    public void setIdMensagem(Integer idMensagem) {
        this.idMensagem = idMensagem;
    }

    public int getIdRemetente() {
        return idRemetente;
    }

    public void setIdRemetente(int idRemetente) {
        this.idRemetente = idRemetente;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Boolean getLida() {
        return lida;
    }

    public void setLida(Boolean lida) {
        this.lida = lida;
    }

    public Boolean getEliminadaRemetente() {
        return eliminadaRemetente;
    }

    public void setEliminadaRemetente(Boolean eliminadaRemetente) {
        this.eliminadaRemetente = eliminadaRemetente;
    }

    public Boolean getEliminadaDestinatario() {
        return eliminadaDestinatario;
    }

    public void setEliminadaDestinatario(Boolean eliminadaDestinatario) {
        this.eliminadaDestinatario = eliminadaDestinatario;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Utilizador getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(Utilizador idDestinatario) {
        this.idDestinatario = idDestinatario;
    }
}
