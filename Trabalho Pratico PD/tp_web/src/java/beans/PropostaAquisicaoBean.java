/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import Classes.Item;
import Classes.Operador;
import controllers.AquisicaoPropostaFacadeLocal;
import controllers.ComponenteFacadeLocal;
import controllers.ComponenteProdutoControllerLocal;
import controllers.PropostaFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import models.AquisicaoProposta;
import models.Componente;
import models.ComponenteProduto;
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
    private ComponenteProdutoControllerLocal componenteProdutoController;

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
    private String filtroProcessos;

    private Operador op;
    private List<Item> items;
    private List<Operador> operadores;
 
    @PostConstruct
    public void init() {
        items = new ArrayList<>();
        
        operadores = new ArrayList<>();   
        
        operadores.add(new Operador(1, "Igual a", "="));
        operadores.add(new Operador(2, "Menor que", "<"));
        operadores.add(new Operador(3, "Maior que", ">"));
        operadores.add(new Operador(4, "Inclui", "⊃"));
        operadores.add(new Operador(5, "Exclui", "⊅"));
    }

    public void setItems(List<Item> items) {
        this.items = items;
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
    
    public List<Operador> getOperadores() {
       
        return operadores;
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

            if(items.isEmpty()){
                context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não tem componentes associados"));
                return "error";
            }
            else{
                String x  = aquisicaoPropostaFacade.create(fieldsObject.toString(),items);
                context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO,"Informação", x));
                return "index.xhtml?faces-redirect=true?"; 
            }
            
        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", ex.getMessage()));

            return "index.xhtml?faces-redirect=true?";
        }
    }
    
    
        public String update(AquisicaoProposta a) throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            JsonObjectBuilder messageFields = Json.createObjectBuilder();
            messageFields.add("id_utilizador", request.getParameter("form:id_utilizador"));
            messageFields.add("valor_max", request.getParameter("form:max_value_input"));
            messageFields.add("observacoes", request.getParameter("form:observacoes"));

            JsonObject fieldsObject = messageFields.build();
            
            if(items.isEmpty()){
                context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não tem componentes associados"));
                return "error";
            }
            else {
                String x = aquisicaoPropostaFacade.update(fieldsObject.toString(), items, a.getIdAquisicao());
                context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Informação alterada com sucesso"));
                return "index.xhtml?faces-redirect=true?";
            }
        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", ex.toString()));
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
        if("Adjudicada".equals(this.filtroProcessos)){
            return aquisicaoPropostaFacade.getClosedList();
        }

        else if("Pendente".equals(this.filtroProcessos))
            return aquisicaoPropostaFacade.getOpenList();

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

     
    public List<Proposta>getProposalSolutionsList(AquisicaoProposta a){
        return propostaFacade.findPropostasSolucaoByPropostaAquisicao(a);
    }
    

    public String showProposal(int idAquisicao) {
        this.proposedAcquisition = aquisicaoPropostaFacade.findAquisicaoProposta(idAquisicao);

        items.clear();
        
        List <ComponenteProduto> cp = componenteProdutoController.getComponentePropostaByIdAquisicao(idAquisicao);
       
        cp.forEach((k) -> {
            Item item = new Item(); 
            
            int operador = Integer.parseInt(k.getOperador());
            item.setLabel("" + items.size());
            item.setComponenteString(k.getComponente().getNome());
            item.setComponente(k.getComponente().getIdComponente());
            
            this.getOperadores().forEach((y) -> {
                if(y.getId() == operador){
                    item.setOperadorString(y.getDescricao());
                    item.setOperador(y.getId());
                }
            });
            
            item.setValor(k.getValor());          
            items.add(item);
        }); 
       
        return "aquisitionProposal.xhtml";
    }
    
     public String showProccess(AquisicaoProposta p) {
        this.proposedAcquisition = p;
        return "/processes/proccess.xhtml";
    }
     
    public List<AquisicaoProposta> getOpenProposals() {
        return aquisicaoPropostaFacade.getOpenList();
    }
     
    public String editProposal(int idAquisicao) {
        this.proposedAcquisition = aquisicaoPropostaFacade.findAquisicaoProposta(idAquisicao);

        items.clear();
        
        List <ComponenteProduto> cp = componenteProdutoController.getComponentePropostaByIdAquisicao(idAquisicao);
       
        cp.forEach((k) -> {
            Item item = new Item(); 
            
            int operador = Integer.parseInt(k.getOperador());
            item.setLabel("" + items.size());
            item.setComponenteString(k.getComponente().getNome());
            item.setComponente(k.getComponente().getIdComponente());
            
            this.getOperadores().forEach((y) -> {
                if(y.getId() == operador){
                    item.setOperadorString(y.getDescricao());
                    item.setOperador(y.getId());
                }
            });
            
            item.setValor(k.getValor());          
            items.add(item);
        });

        return "edit.xhtml";
    }
    
    
    public String createPage() {       
        items.clear();

        return "create.xhtml";
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
    
    public String getFiltroProcessos() {
        return filtroProcessos;
    }

    public void setFiltroProcessos(String filtroProcessos) {
        this.filtroProcessos = filtroProcessos;
    }
}
