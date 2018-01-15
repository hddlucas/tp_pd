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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hddlucas
 */
@Entity
@Table(name = "perfil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Perfil.findAll", query = "SELECT p FROM Perfil p")
    , @NamedQuery(name = "Perfil.findByIdPerfil", query = "SELECT p FROM Perfil p WHERE p.idPerfil = :idPerfil")
    , @NamedQuery(name = "Perfil.findByPerfil", query = "SELECT p FROM Perfil p WHERE p.perfil = :perfil")
    , @NamedQuery(name = "Perfil.findByDescricao", query = "SELECT p FROM Perfil p WHERE p.descricao = :descricao")})
public class Perfil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_perfil")
    private Integer idPerfil;
    @Basic(optional = false)
    @Column(name = "perfil")
    private String perfil;
    @Column(name = "descricao")
    private String descricao;
    @JoinTable(name = "utilizador_perfil", joinColumns = {
        @JoinColumn(name = "id_perfil", referencedColumnName = "id_perfil")}, inverseJoinColumns = {
        @JoinColumn(name = "id_utilizador", referencedColumnName = "id_utilizador")})
    @ManyToMany
    private Collection<Utilizador> utilizadorCollection;

    public Perfil() {
    }

    public Perfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Perfil(Integer idPerfil, String perfil) {
        this.idPerfil = idPerfil;
        this.perfil = perfil;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public Collection<Utilizador> getUtilizadorCollection() {
        return utilizadorCollection;
    }

    public void setUtilizadorCollection(Collection<Utilizador> utilizadorCollection) {
        this.utilizadorCollection = utilizadorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerfil != null ? idPerfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Perfil)) {
            return false;
        }
        Perfil other = (Perfil) object;
        if ((this.idPerfil == null && other.idPerfil != null) || (this.idPerfil != null && !this.idPerfil.equals(other.idPerfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Id do Perfil: " + idPerfil + " Perfil: " + descricao;
    }
    
}
