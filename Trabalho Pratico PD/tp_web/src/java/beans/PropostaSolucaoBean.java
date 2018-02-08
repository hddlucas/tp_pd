/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controllers.AquisicaoPropostaFacadeLocal;
import controllers.PropostaFacadeLocal;
import controllers.UtilizadorFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
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
import models.AquisicaoProposta;
import models.Categoria;
import models.Proposta;
import models.Utilizador;

/**
 *
 * @author marcosequeira
 */
@Named(value = "propostaSolucaoBean")
@SessionScoped
public class PropostaSolucaoBean implements Serializable {

    @EJB
    private UtilizadorFacadeLocal utilizadorFacade;

    @EJB
    private AquisicaoPropostaFacadeLocal aquisicaoPropostaFacade;

    @EJB
    private PropostaFacadeLocal propostaFacade;   
    
    private Proposta proposta = new Proposta();
    private Integer idProposta;
    private Integer valorTotal;
    private Boolean ganhou;
    private Date createdAt;
    private Utilizador idUtilizador;

    public PropostaSolucaoBean(PropostaFacadeLocal propostaFacade, Integer idProposta, Integer valorTotal, Boolean ganhou, Date createdAt, Utilizador idUtilizador) {
        this.propostaFacade = propostaFacade;
        this.idProposta = idProposta;
        this.valorTotal = valorTotal;
        this.ganhou = ganhou;
        this.createdAt = createdAt;
        this.idUtilizador = idUtilizador;
    }

    public PropostaSolucaoBean(Integer idProposta) {
        this.idProposta = idProposta;
    }

    public Proposta getPropostaSolucao() {
        return this.proposta;
    }

    
    public Integer getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(Integer idProposta) {
        this.idProposta = idProposta;
    }

    public Integer getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Integer valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Boolean getGanhou() {
        return ganhou;
    }

    public void setGanhou(Boolean ganhou) {
        this.ganhou = ganhou;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Utilizador getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(Utilizador idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    /**
     * Creates a new instance of PropostaSolucaoBean
     */
    public PropostaSolucaoBean() {
    }
    
    
    public String create() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            
            JsonObjectBuilder propostaFields = Json.createObjectBuilder();
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            propostaFields.add("idUtilizador", request.getParameter("form:id_utilizador"));
            propostaFields.add("valor_total", request.getParameter("form:solucao"));
            
            JsonObject fieldsObject = propostaFields.build();
            propostaFacade.create(fieldsObject.toString());
           
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Proposta Solução criada com sucesso"));

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
    
    
        //VALIDATORS
    public void validateSolucao(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Object oldValue = ((UIInput) component).getValue();

        int id = Integer.parseInt(request.getParameter("form:idP"));
        
        AquisicaoProposta proposta = aquisicaoPropostaFacade.findAquisicaoProposta(id);

        float valorSolucao = Float.parseFloat(request.getParameter("form:solucao"));

        if(valorSolucao > proposta.getValorMax()) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O valor introduzido não pode ser superior ao valor máximo");
            throw new ValidatorException(msg);
        }
    }
}