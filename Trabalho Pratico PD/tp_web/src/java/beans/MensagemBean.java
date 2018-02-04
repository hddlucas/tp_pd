/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controllers.MensagemFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
     
    
    public List<Mensagem> getUserMessages(Utilizador u) {
        
        return null;
    } 
    
    
    public String create() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            JsonObjectBuilder userFields = Json.createObjectBuilder();
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            userFields.add("nome", request.getParameter("form:nome"));
           
            JsonObject fieldsObject = userFields.build();
            //this.user = utilizadorFacade.create(fieldsObject.toString());
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Mensagem enviada com sucesso"));

        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro ao enviar a mensagem"));

            return "index.xhtml?faces-redirect=true?";
        } finally {
            context.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
            return "index.xhtml?faces-redirect=true?";
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
