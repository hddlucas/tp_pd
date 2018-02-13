/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "avaliacao_vendedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AvaliacaoVendedor.findAll", query = "SELECT a FROM AvaliacaoVendedor a")
    , @NamedQuery(name = "AvaliacaoVendedor.findByIdAvaliacaoVendedor", query = "SELECT a FROM AvaliacaoVendedor a WHERE a.idAvaliacaoVendedor = :idAvaliacaoVendedor")
    , @NamedQuery(name = "AvaliacaoVendedor.findByIdAvaliador", query = "SELECT a FROM AvaliacaoVendedor a WHERE a.idAvaliador = :idAvaliador")
    , @NamedQuery(name = "AvaliacaoVendedor.findByAvaliacao", query = "SELECT a FROM AvaliacaoVendedor a WHERE a.avaliacao = :avaliacao")})
public class AvaliacaoVendedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_avaliacao_vendedor")
    private Integer idAvaliacaoVendedor;
    @Column(name = "id_avaliador")
    private Integer idAvaliador;
    @Basic(optional = false)
    @Column(name = "avaliacao")
    private int avaliacao;
    @JoinColumn(name = "id_utilizador", referencedColumnName = "id_utilizador")
    @ManyToOne
    private Utilizador idUtilizador;

    public AvaliacaoVendedor() {
    }

    public AvaliacaoVendedor(Integer idAvaliacaoVendedor) {
        this.idAvaliacaoVendedor = idAvaliacaoVendedor;
    }

    public AvaliacaoVendedor(Integer idAvaliacaoVendedor, int avaliacao) {
        this.idAvaliacaoVendedor = idAvaliacaoVendedor;
        this.avaliacao = avaliacao;
    }

    public Integer getIdAvaliacaoVendedor() {
        return idAvaliacaoVendedor;
    }

    public void setIdAvaliacaoVendedor(Integer idAvaliacaoVendedor) {
        this.idAvaliacaoVendedor = idAvaliacaoVendedor;
    }

    public Integer getIdAvaliador() {
        return idAvaliador;
    }

    public void setIdAvaliador(Integer idAvaliador) {
        this.idAvaliador = idAvaliador;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Utilizador getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(Utilizador idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAvaliacaoVendedor != null ? idAvaliacaoVendedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AvaliacaoVendedor)) {
            return false;
        }
        AvaliacaoVendedor other = (AvaliacaoVendedor) object;
        if ((this.idAvaliacaoVendedor == null && other.idAvaliacaoVendedor != null) || (this.idAvaliacaoVendedor != null && !this.idAvaliacaoVendedor.equals(other.idAvaliacaoVendedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.AvaliacaoVendedor[ idAvaliacaoVendedor=" + idAvaliacaoVendedor + " ]";
    }
    
}
