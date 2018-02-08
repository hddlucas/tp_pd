/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controllers.AquisicaoPropostaFacadeLocal;
import controllers.CategoriaFacadeLocal;
import controllers.PropostaFacade;
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

/**
 *
 * @author hddlucas
 */
@Named(value = "propostaAquisicaoBean")
@SessionScoped
public class PropostaAquisicaoBean implements Serializable {

    @EJB
    private AquisicaoPropostaFacadeLocal aquisicaoPropostaFacade;  
    
    private AquisicaoProposta proposedAcquisition = new AquisicaoProposta();
    private Integer idAquisicao;
    private Integer idProduto;
    private Integer idUtilizador;
    private float valorMax;
    private String observacoes;
    private Date createdAt;

        
    /**
     * Creates a new instance of PropostaAquisicaoBean
     */
    public PropostaAquisicaoBean() {
    }


    public List<models.AquisicaoProposta> getList() {
        return aquisicaoPropostaFacade.getAcquisitionProposals();
    }

    
    public AquisicaoProposta getProposedAcquisition() {
        return this.proposedAcquisition;
    }

    public String show(AquisicaoProposta p) {
        this.proposedAcquisition = p;
        return "solutionProposal.xhtml";
    }


    //PROPRIEDADES
    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
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
