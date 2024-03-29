/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import Classes.Item;
import controllers.AquisicaoPropostaFacadeLocal;
import controllers.MensagemFacadeLocal;
import controllers.PropostaFacadeLocal;
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
    private MensagemFacadeLocal mensagemFacade;

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
    private Integer produtoRating=0;
    private Integer vendedorRating=0;
    private int avaliacao;

   
   

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

      /**
     * Creates a new instance of PropostaSolucaoBean
     */
    public PropostaSolucaoBean() {
    }
    
    
    
    public Proposta getPropostaSolucao() {
        return this.proposta;
    }

    public String create() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            JsonObjectBuilder propostaFields = Json.createObjectBuilder();
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            propostaFields.add("idUtilizador", request.getParameter("form:id_utilizador"));
            propostaFields.add("idAquisicao", request.getParameter("form:idP"));

            propostaFields.add("valor", request.getParameter("form:valor_input"));
            propostaFields.add("observacoes", request.getParameter("form:observacoes"));

            JsonObject fieldsObject = propostaFields.build();

           propostaFacade.create(fieldsObject.toString());
           
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Proposta de Solução inserida com sucesso"));

        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Ocorreu um erro ao criar a Proposta de Solução"));

            return "index.xhtml?faces-redirect=true?";
        } finally {
            context.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
            return "index.xhtml?faces-redirect=true?";
        }
    }
    
    public String showSolution(Proposta ps) {
        this.proposta = ps;
        return "/aquisitionalProposal/solutionProposal.xhtml?faces-redirect=true";
    }
    
    public String acceptProposal(int idPropostaSolucao, List <Item> items){
        FacesContext context = FacesContext.getCurrentInstance();
        try{
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        JsonObjectBuilder aceptFields = Json.createObjectBuilder();
        
        Proposta p = propostaFacade.findProposta(idPropostaSolucao);
        AquisicaoProposta aquisicaoProposta = aquisicaoPropostaFacade.findAquisicaoProposta(p.getIdAquisicao().getIdAquisicao());
 
        
        aceptFields.add("produtoRating", Integer.toString(this.produtoRating));
        aceptFields.add("vendedorRating", Integer.toString(this.vendedorRating));
        aceptFields.add("idSolucao", Integer.toString(idPropostaSolucao));
        aceptFields.add("observacoes", request.getParameter("form:observacoes"));
        aceptFields.add("id_aquisicao", Integer.toString(aquisicaoProposta.getIdAquisicao()));
        
        JsonObject fieldsObject = aceptFields.build();

        propostaFacade.acceptProposal(fieldsObject.toString(), items);
        
        context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO,"Informação", "Proposta de Solução aceite com sucesso"));

        
         } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", ex.getMessage()));
            return "/aquisitionalProposal/aquisitionProposal.xhtml?faces-redirect=true";

        } finally {
            context.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
            return "/aquisitionalProposal/aquisitionProposal.xhtml?faces-redirect=true";
        }
    }
    
    
    
    //VALIDATORS
    public void validateSolucao(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Object oldValue = ((UIInput) component).getValue();

        int id = Integer.parseInt(request.getParameter("form:idP"));
        
        AquisicaoProposta proposta = aquisicaoPropostaFacade.findAquisicaoProposta(id);

        double valorSolucao = Double.parseDouble(request.getParameter("form:valor_input"));

        if(valorSolucao > proposta.getValorMax()) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O valor introduzido não pode ser superior ao valor máximo");
            throw new ValidatorException(msg);
        }
    }
    
    
    //PROPRIEDADES
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
    
    public Integer getProdutoRating() {
        return produtoRating;
    }

    public void setProdutoRating(Integer produtoRating) {
        this.produtoRating = produtoRating;
    }
    
     public Integer getVendedorRating() {
        return vendedorRating;
    }

    public void setVendedorRating(Integer vendedorRating) {
        this.vendedorRating = vendedorRating;
    }
    
     public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }

    public List<Proposta> getListToUser(int userId) {

        return propostaFacade.getAcquisitionProposals(userId);
    }
    
    public String showSolutionProposalSaller(Proposta ps) {
        this.proposta = ps;
        return "/myProposals/solutionProposal.xhtml?faces-redirect=true";
    } 
    
    public String editSaller(Proposta ps) {
        this.proposta = ps;
        return "/myProposals/edit.xhtml?faces-redirect=true";
    }    
    
    
    public String update(Proposta ps) throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
//            this.proposta = ps;
            JsonObjectBuilder proposalFields = Json.createObjectBuilder();
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            proposalFields.add("observations", request.getParameter("form:observations"));
            proposalFields.add("max_value", request.getParameter("form:valor_input"));

            JsonObject fieldsObject = proposalFields.build();
            propostaFacade.update(fieldsObject.toString(), ps.getIdProposta());
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Proposta alterada com sucesso"));

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
            propostaFacade.destroy(id);
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Proposta Removida com sucesso"));

        } catch (Exception ex) {
            context.addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um problema ao eliminar a Proposta"));

        } finally {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
            return "index.xhtml?faces-redirect=true";
        }
    }
}
