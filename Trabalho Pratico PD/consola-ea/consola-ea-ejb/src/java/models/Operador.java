/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hddlucas
 */
@Entity
@Table(name = "operador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operador.findAll", query = "SELECT o FROM Operador o")
    , @NamedQuery(name = "Operador.findByIdOperador", query = "SELECT o FROM Operador o WHERE o.idOperador = :idOperador")
    , @NamedQuery(name = "Operador.findByNomeoperador", query = "SELECT o FROM Operador o WHERE o.nomeoperador = :nomeoperador")
    , @NamedQuery(name = "Operador.findByDescricaooperador", query = "SELECT o FROM Operador o WHERE o.descricaooperador = :descricaooperador")})
public class Operador implements Serializable {

    @Column(name = "nome")
    private String nome;
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(mappedBy = "idOperador")
    private Collection<Componente> componenteCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_operador")
    private Integer idOperador;
    @Column(name = "nomeoperador")
    private String nomeoperador;
    @Column(name = "descricaooperador")
    private String descricaooperador;
    @OneToMany(mappedBy = "idOperador")
    private Collection<AquisicaoProposta> aquisicaoPropostaCollection;

    public Operador() {
    }

    public Operador(Integer idOperador) {
        this.idOperador = idOperador;
    }

    public Integer getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(Integer idOperador) {
        this.idOperador = idOperador;
    }

    public String getNomeoperador() {
        return nomeoperador;
    }

    public void setNomeoperador(String nomeoperador) {
        this.nomeoperador = nomeoperador;
    }

    public String getDescricaooperador() {
        return descricaooperador;
    }

    public void setDescricaooperador(String descricaooperador) {
        this.descricaooperador = descricaooperador;
    }

    @XmlTransient
    public Collection<AquisicaoProposta> getAquisicaoPropostaCollection() {
        return aquisicaoPropostaCollection;
    }

    public void setAquisicaoPropostaCollection(Collection<AquisicaoProposta> aquisicaoPropostaCollection) {
        this.aquisicaoPropostaCollection = aquisicaoPropostaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOperador != null ? idOperador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Operador)) {
            return false;
        }
        Operador other = (Operador) object;
        if ((this.idOperador == null && other.idOperador != null) || (this.idOperador != null && !this.idOperador.equals(other.idOperador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Operador[ idOperador=" + idOperador + " ]";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public Collection<Componente> getComponenteCollection() {
        return componenteCollection;
    }

    public void setComponenteCollection(Collection<Componente> componenteCollection) {
        this.componenteCollection = componenteCollection;
    }
    
}
