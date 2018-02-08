/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hddlucas
 */
@Entity
@Table(name = "aquisicao_proposta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AquisicaoProposta.findAll", query = "SELECT a FROM AquisicaoProposta a")
    ,@NamedQuery(name = "AquisicaoProposta.findAllNotDeleted", query = "SELECT a FROM AquisicaoProposta a WHERE a.deleted=false")
    , @NamedQuery(name = "AquisicaoProposta.findByIdAquisicao", query = "SELECT a FROM AquisicaoProposta a WHERE a.idAquisicao = :idAquisicao")
    , @NamedQuery(name = "AquisicaoProposta.findByValorMax", query = "SELECT a FROM AquisicaoProposta a WHERE a.valorMax = :valorMax")
    , @NamedQuery(name = "AquisicaoProposta.findByObservacoes", query = "SELECT a FROM AquisicaoProposta a WHERE a.observacoes = :observacoes")
    , @NamedQuery(name = "AquisicaoProposta.findByCreatedAt", query = "SELECT a FROM AquisicaoProposta a WHERE a.createdAt = :createdAt")
    , @NamedQuery(name = "AquisicaoProposta.findByDeleted", query = "SELECT a FROM AquisicaoProposta a WHERE a.deleted = :deleted")})

public class AquisicaoProposta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_aquisicao")
    private Integer idAquisicao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_max")
    private Double valorMax;
    @Column(name = "observacoes")
    private String observacoes;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aquisicaoProposta")
    private Collection<ComponenteProduto> componenteProdutoCollection;
    @JoinColumn(name = "id_utilizador", referencedColumnName = "id_utilizador")
    @ManyToOne(optional = false)
    private Utilizador idUtilizador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aquisicaoProposta")
    private Collection<ProdutoProposta> produtoPropostaCollection;

    public AquisicaoProposta() {
    }

    public AquisicaoProposta(Integer idAquisicao) {
        this.idAquisicao = idAquisicao;
    }

    public AquisicaoProposta(Integer idAquisicao, boolean deleted) {
        this.idAquisicao = idAquisicao;
        this.deleted = deleted;
    }

    public Integer getIdAquisicao() {
        return idAquisicao;
    }

    public void setIdAquisicao(Integer idAquisicao) {
        this.idAquisicao = idAquisicao;
    }

    public Double getValorMax() {
        return valorMax;
    }

    public void setValorMax(Double valorMax) {
        this.valorMax = valorMax;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @XmlTransient
    public Collection<ComponenteProduto> getComponenteProdutoCollection() {
        return componenteProdutoCollection;
    }

    public void setComponenteProdutoCollection(Collection<ComponenteProduto> componenteProdutoCollection) {
        this.componenteProdutoCollection = componenteProdutoCollection;
    }

    public Utilizador getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(Utilizador idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    @XmlTransient
    public Collection<ProdutoProposta> getProdutoPropostaCollection() {
        return produtoPropostaCollection;
    }

    public void setProdutoPropostaCollection(Collection<ProdutoProposta> produtoPropostaCollection) {
        this.produtoPropostaCollection = produtoPropostaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAquisicao != null ? idAquisicao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AquisicaoProposta)) {
            return false;
        }
        AquisicaoProposta other = (AquisicaoProposta) object;
        if ((this.idAquisicao == null && other.idAquisicao != null) || (this.idAquisicao != null && !this.idAquisicao.equals(other.idAquisicao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.AquisicaoProposta[ idAquisicao=" + idAquisicao + " ]";
    }
    
}
