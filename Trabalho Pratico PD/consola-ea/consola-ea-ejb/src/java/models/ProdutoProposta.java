/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.math.BigDecimal;
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
    , @NamedQuery(name = "ProdutoProposta.findByIdProduto", query = "SELECT p FROM ProdutoProposta p WHERE p.produtoPropostaPK.idProduto = :idProduto")
    , @NamedQuery(name = "ProdutoProposta.findByValorMax", query = "SELECT p FROM ProdutoProposta p WHERE p.valorMax = :valorMax")
    , @NamedQuery(name = "ProdutoProposta.findByObservacoes", query = "SELECT p FROM ProdutoProposta p WHERE p.observacoes = :observacoes")
    , @NamedQuery(name = "ProdutoProposta.findByAvaliacao", query = "SELECT p FROM ProdutoProposta p WHERE p.avaliacao = :avaliacao")})
public class ProdutoProposta implements Serializable {

    @Column(name = "observacoes")
    private String observacoes;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProdutoPropostaPK produtoPropostaPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_max")
    private BigDecimal valorMax;
    @Column(name = "avaliacao")
    private String avaliacao;
    @JoinColumn(name = "id_produto", referencedColumnName = "id_produto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Produto produto;
    @JoinColumn(name = "id_proposta", referencedColumnName = "id_proposta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proposta proposta;

    public ProdutoProposta() {
    }

    public ProdutoProposta(ProdutoPropostaPK produtoPropostaPK) {
        this.produtoPropostaPK = produtoPropostaPK;
    }

    public ProdutoProposta(int idProposta, int idProduto) {
        this.produtoPropostaPK = new ProdutoPropostaPK(idProposta, idProduto);
    }

    public ProdutoPropostaPK getProdutoPropostaPK() {
        return produtoPropostaPK;
    }

    public void setProdutoPropostaPK(ProdutoPropostaPK produtoPropostaPK) {
        this.produtoPropostaPK = produtoPropostaPK;
    }

    public BigDecimal getValorMax() {
        return valorMax;
    }

    public void setValorMax(BigDecimal valorMax) {
        this.valorMax = valorMax;
    }


    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
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

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
}
