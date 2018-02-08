/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hddlucas
 */
@Entity
@Table(name = "produto_proposta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProdutoProposta.findAll", query = "SELECT p FROM ProdutoProposta p")
    , @NamedQuery(name = "ProdutoProposta.findByIdProposta", query = "SELECT p FROM ProdutoProposta p WHERE p.produtoPropostaPK.idProposta = :idProposta")
    , @NamedQuery(name = "ProdutoProposta.findByIdAquisicao", query = "SELECT p FROM ProdutoProposta p WHERE p.produtoPropostaPK.idAquisicao = :idAquisicao")
    , @NamedQuery(name = "ProdutoProposta.findByObservacoes", query = "SELECT p FROM ProdutoProposta p WHERE p.observacoes = :observacoes")
    , @NamedQuery(name = "ProdutoProposta.findByAvaliacao", query = "SELECT p FROM ProdutoProposta p WHERE p.avaliacao = :avaliacao")})
public class ProdutoProposta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProdutoPropostaPK produtoPropostaPK;
    @Column(name = "observacoes")
    private String observacoes;
    @Column(name = "avaliacao")
    private String avaliacao;
    @JoinColumn(name = "id_aquisicao", referencedColumnName = "id_aquisicao", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AquisicaoProposta aquisicaoProposta;
    @JoinColumn(name = "id_proposta", referencedColumnName = "id_proposta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proposta proposta;

    public ProdutoProposta() {
    }

    public ProdutoProposta(ProdutoPropostaPK produtoPropostaPK) {
        this.produtoPropostaPK = produtoPropostaPK;
    }

    public ProdutoProposta(int idProposta, int idAquisicao) {
        this.produtoPropostaPK = new ProdutoPropostaPK(idProposta, idAquisicao);
    }

    public ProdutoPropostaPK getProdutoPropostaPK() {
        return produtoPropostaPK;
    }

    public void setProdutoPropostaPK(ProdutoPropostaPK produtoPropostaPK) {
        this.produtoPropostaPK = produtoPropostaPK;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public AquisicaoProposta getAquisicaoProposta() {
        return aquisicaoProposta;
    }

    public void setAquisicaoProposta(AquisicaoProposta aquisicaoProposta) {
        this.aquisicaoProposta = aquisicaoProposta;
    }

    public Proposta getProposta() {
        return proposta;
    }

    public void setProposta(Proposta proposta) {
        this.proposta = proposta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (produtoPropostaPK != null ? produtoPropostaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProdutoProposta)) {
            return false;
        }
        ProdutoProposta other = (ProdutoProposta) object;
        if ((this.produtoPropostaPK == null && other.produtoPropostaPK != null) || (this.produtoPropostaPK != null && !this.produtoPropostaPK.equals(other.produtoPropostaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.ProdutoProposta[ produtoPropostaPK=" + produtoPropostaPK + " ]";
    }
    
}
