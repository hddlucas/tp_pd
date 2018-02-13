/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import Classes.Item;
import controllers.AquisicaoPropostaFacadeLocal;
import controllers.ComponenteFacadeLocal;
import controllers.OperadorFacadeLocal;
import controllers.PropostaFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.faces.validator.ValidatorException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import models.AquisicaoProposta;
import models.Categoria;
import models.Componente;
import models.Operador;
import models.Proposta;
import models.Utilizador;

/**
 *
 * @author hddlucas
 */
@Named(value = "propostaAquisicaoBean")
@SessionScoped
public class PropostaAquisicaoBean implements Serializable {

    @EJB
    private OperadorFacadeLocal operadorFacade;

    @EJB
    private ComponenteFacadeLocal componenteFacade;

    
    @EJB
    private AquisicaoPropostaFacadeLocal aquisicaoPropostaFacade;  
    
    @EJB
    private PropostaFacadeLocal propostaFacade; 
    
    private AquisicaoProposta proposedAcquisition = new AquisicaoProposta();

    private Integer idAquisicao;
    private Integer idUtilizador;
    private float valorMax;
    private String observacoes;
    private Date createdAt;
    private List<Componente> componentes;
    private List<Operador> operadores;

    
    private List<Item> items;

    @PostConstruct
    public void init() {
        items = new ArrayList<>();
    }

    public void add() {
        Item item = new Item();
        item.setLabel("" + items.size());
        items.add(item);
    }

    public void remove(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }
    
        
    /**
     * Creates a new instance of PropostaAquisicaoBean
     */
    public PropostaAquisicaoBean() {
    }
    
    public AquisicaoProposta getProposedAcquisition() {
        return this.proposedAcquisition;
    }

    public String create() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            
            JsonObjectBuilder messageFields = Json.createObjectBuilder();
            messageFields.add("id_utilizador", request.getParameter("form:id_utilizador"));
            messageFields.add("valor_max", request.getParameter("form:max_value_input"));
            messageFields.add("observacoes", request.getParameter("form:observacoes"));

            JsonObject fieldsObject = messageFields.build();

            String x  = aquisicaoPropostaFacade.create(fieldsObject.toString(),items);
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO,"Informação", x));


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
    
   
    public String destroy(int id) throws Exception {
      FacesContext context = FacesContext.getCurrentInstance();
      try {
          aquisicaoPropostaFacade.destroy(id);
          context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Proposta de Aquisição Removida com sucesso"));

      } catch (Exception ex) {
          context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um problema ao eliminar a Proposta de Aquisição"));

      } finally {
          FacesContext.getCurrentInstance()
                  .getExternalContext()
                  .getFlash().setKeepMessages(true);
          return "index.xhtml?faces-redirect=true";
      }
    }

    public List<AquisicaoProposta> getList() {
        return aquisicaoPropostaFacade.getAcquisitionProposals();
    }

    public List<AquisicaoProposta> getUserProposalsList(Utilizador u) {
        return aquisicaoPropostaFacade.getUserAcquisitionProposals(u.getIdUtilizador());
    }

    public int getTotalOfReceivedProposals(AquisicaoProposta a) {
        return aquisicaoPropostaFacade.getTotalPropostasRecebidas(a);
    }

    public boolean proposalAdjudicated(AquisicaoProposta a) {
        return aquisicaoPropostaFacade.propostaAdjudicada(a);
    }

    

    public List<Componente> getComponentes() {
        this.componentes = componenteFacade.getComponentsList();
        return componentes;
    }
    
     public List<Operador> getOperadores() {
        this.operadores = operadorFacade.getOperadorList();
        return operadores;
    }
     
    public List<Proposta>getProposalSolutionsList(AquisicaoProposta a){
        return propostaFacade.findPropostasSolucaoByPropostaAquisicao(a);
    }
     
    public String showProposal(AquisicaoProposta p) {
        this.proposedAcquisition = p;
        return "aquisitionProposal.xhtml";
    }
     
   
     
    public String editProposal(AquisicaoProposta p) {
        this.proposedAcquisition = p;
        return "edit.xhtml";
    }
     
    public String show(AquisicaoProposta p) {
        this.proposedAcquisition = p;
        return "solutionProposal.xhtml";
    }

    public Integer getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(Integer idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public float getValorMax() {
        return valorMax;
    }

    public void setValorMax(float valorMax) {
        this.valorMax = valorMax;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Integer getIdAquisicao() {
        return idAquisicao;
    }

    public void setIdAquisicao(Integer idAquisicao) {
        this.idAquisicao = idAquisicao;
    }   
    
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
  
}
