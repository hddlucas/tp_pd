/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author hddlucas
 */
@Embeddable
public class ComponenteProdutoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_componente")
    private int idComponente;
    @Basic(optional = false)
    @Column(name = "id_aquisicao")
    private int idAquisicao;

    public ComponenteProdutoPK() {
    }

    public ComponenteProdutoPK(int idComponente, int idAquisicao) {
        this.idComponente = idComponente;
        this.idAquisicao = idAquisicao;
    }

    public int getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(int idComponente) {
        this.idComponente = idComponente;
    }

    public int getIdAquisicao() {
        return idAquisicao;
    }

    public void setIdAquisicao(int idAquisicao) {
        this.idAquisicao = idAquisicao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idComponente;
        hash += (int) idAquisicao;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComponenteProdutoPK)) {
            return false;
        }
        ComponenteProdutoPK other = (ComponenteProdutoPK) object;
        if (this.idComponente != other.idComponente) {
            return false;
        }
        if (this.idAquisicao != other.idAquisicao) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.ComponenteProdutoPK[ idComponente=" + idComponente + ", idAquisicao=" + idAquisicao + " ]";
    }
    
}
