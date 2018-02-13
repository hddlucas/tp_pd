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
@Table(name = "proposta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proposta.findAll", query = "SELECT p FROM Proposta p")
    , @NamedQuery(name = "Proposta.findByIdProposta", query = "SELECT p FROM Proposta p WHERE p.idProposta = :idProposta")
    , @NamedQuery(name = "Proposta.findByValorTotal", query = "SELECT p FROM Proposta p WHERE p.valorTotal = :valorTotal")
    , @NamedQuery(name = "Proposta.findByGanhou", query = "SELECT p FROM Proposta p WHERE p.ganhou = :ganhou")
    , @NamedQuery(name = "Proposta.findByCreatedAt", query = "SELECT p FROM Proposta p WHERE p.createdAt = :createdAt")
    , @NamedQuery(name = "Proposta.findPropostasSolucaoByPropostaAquisicao", query = "SELECT p FROM ProdutoProposta pp, Proposta p JOIN pp.proposta x, AquisicaoProposta a JOIN pp.aquisicaoProposta pa  WHERE p.deleted=false AND pa.idAquisicao=:idAquisicao GROUP BY p ")

        
        
    , @NamedQuery(name = "Proposta.findByDeleted", query = "SELECT p FROM Proposta p WHERE p.deleted = :deleted")})
public class Proposta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_proposta")
    private Integer idProposta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_total")
    private Double valorTotal;
    @Column(name = "ganhou")
    private Boolean ganhou;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted;
    @JoinColumn(name = "id_utilizador", referencedColumnName = "id_utilizador")
    @ManyToOne(optional = false)
    private Utilizador idUtilizador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proposta")
    private Collection<ProdutoProposta> produtoPropostaCollection;

    public Proposta() {
    }

    public Proposta(Integer idProposta) {
        this.idProposta = idProposta;
    }

    public Proposta(Integer idProposta, boolean deleted) {
        this.idProposta = idProposta;
        this.deleted = deleted;
    }

    public Integer getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(Integer idProposta) {
        this.idProposta = idProposta;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
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

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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
        hash += (idProposta != null ? idProposta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proposta)) {
            return false;
        }
        Proposta other = (Proposta) object;
        if ((this.idProposta == null && other.idProposta != null) || (this.idProposta != null && !this.idProposta.equals(other.idProposta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Proposta[ idProposta=" + idProposta +  idUtilizador.getNome()+" ]";
    }
    
}
