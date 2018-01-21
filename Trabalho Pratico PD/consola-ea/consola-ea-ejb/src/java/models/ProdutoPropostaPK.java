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
public class ProdutoPropostaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_proposta")
    private int idProposta;
    @Basic(optional = false)
    @Column(name = "id_produto")
    private int idProduto;

    public ProdutoPropostaPK() {
    }

    public ProdutoPropostaPK(int idProposta, int idProduto) {
        this.idProposta = idProposta;
        this.idProduto = idProduto;
    }

    public int getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(int idProposta) {
        this.idProposta = idProposta;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idProposta;
        hash += (int) idProduto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProdutoPropostaPK)) {
            return false;
        }
        ProdutoPropostaPK other = (ProdutoPropostaPK) object;
        if (this.idProposta != other.idProposta) {
            return false;
        }
        if (this.idProduto != other.idProduto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.ProdutoPropostaPK[ idProposta=" + idProposta + ", idProduto=" + idProduto + " ]";
    }
    
}
