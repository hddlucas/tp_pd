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


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hddlucas
 */
public class Proposta {
    
    private Integer idProposta;
    private Integer idUtilizador;
    private Float valorTotal;
    private Date createdAt;
    private Boolean ganhou;
    
    public Proposta() {
    }

    public Proposta(Integer idProposta) {
        this.idProposta = idProposta;
    }

    public Proposta(Integer idProposta, Integer idUtilizador, Float valorTotal, Date createdAt, Boolean ganhou) {
        this.idProposta = idProposta;
        this.idUtilizador = idUtilizador;
        this.valorTotal = valorTotal;
        this.createdAt = createdAt;
        this.ganhou = ganhou;
    }

    public Boolean getGanhou() {
        return ganhou;
    }

    public void setGanhou(Boolean ganhou) {
        this.ganhou = ganhou;
    }

    

    public Integer getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(Integer idProposta) {
        this.idProposta = idProposta;
    }

    public Integer getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(Integer idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public Float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof models.Proposta)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Proposta{" + "idProposta=" + idProposta + ", idUtilizador=" + idUtilizador + ", valorTotal=" + valorTotal + ", createdAt=" + createdAt + '}';
    }



}
