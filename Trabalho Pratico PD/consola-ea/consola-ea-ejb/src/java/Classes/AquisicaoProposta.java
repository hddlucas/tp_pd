package Classes;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import models.Perfil;
import models.Proposta;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hddlucas
 */
public class AquisicaoProposta {
    
    private Integer idAquisicao;
    private Float valorMax;
    private String observacoes;
    private Date createdAt;
    private Integer idProduto;
    private Integer idUtilizador;
    
    public AquisicaoProposta() {
    }

    public AquisicaoProposta(Integer idAquisicao) {
        this.idAquisicao = idAquisicao;
    }

    public AquisicaoProposta(Integer idAquisicao, Float valorMax, String observacoes, Date createdAt, Integer idProduto, Integer idUtilizador) {
        this.idAquisicao = idAquisicao;
        this.valorMax = valorMax;
        this.observacoes = observacoes;
        this.createdAt = createdAt;
        this.idProduto = idProduto;
        this.idUtilizador = idUtilizador;
    }
    
    
    public Integer getIdAquisicao() {
        return idAquisicao;
    }

    public void setIdAquisicao(Integer idAquisicao) {
        this.idAquisicao = idAquisicao;
    }

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

    public Float getValorMax() {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
        if (!(object instanceof models.AquisicaoProposta)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Id da Aquisicao:" + idAquisicao + " valorMax:" + valorMax + " observacoes:" + observacoes + " createdAt:" + createdAt + " idProduto:" + idProduto + " idUtilizador:" + idUtilizador + "" ;
    }


}
