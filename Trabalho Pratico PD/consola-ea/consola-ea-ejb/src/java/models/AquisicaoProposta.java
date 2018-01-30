/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hddlucas
 */
@Entity
@Table(name = "aquisicao_proposta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AquisicaoProposta.findAll", query = "SELECT a FROM AquisicaoProposta a")
    , @NamedQuery(name = "AquisicaoProposta.findByIdAquisicao", query = "SELECT a FROM AquisicaoProposta a WHERE a.idAquisicao = :idAquisicao")
    , @NamedQuery(name = "AquisicaoProposta.findByValorMax", query = "SELECT a FROM AquisicaoProposta a WHERE a.valorMax = :valorMax")
    , @NamedQuery(name = "AquisicaoProposta.findByCreatedAt", query = "SELECT a FROM AquisicaoProposta a WHERE a.createdAt = :createdAt")
    , @NamedQuery(name = "AquisicaoProposta.findByObservacoes", query = "SELECT a FROM AquisicaoProposta a WHERE a.observacoes = :observacoes")
    , @NamedQuery(name = "AquisicaoProposta.findByGanhou", query = "SELECT a FROM AquisicaoProposta a WHERE a.ganhou = :ganhou")})
public class AquisicaoProposta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id_aquisicao", insertable = false , columnDefinition = "serial") 
    private Integer idAquisicao;
    @Column(name = "valor_max")
    private Integer valorMax;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "observacoes")
    private String observacoes;
    @Column(name = "ganhou")
    private Boolean ganhou;
    @JoinColumn(name = "id_operador", referencedColumnName = "id_operador")
    @ManyToOne
    private Operador idOperador;
    @JoinColumn(name = "id_produto", referencedColumnName = "id_produto")
    @ManyToOne
    private Produto idProduto;
    @JoinColumn(name = "id_utilizador", referencedColumnName = "id_utilizador")
    @ManyToOne(optional = false)
    private Utilizador idUtilizador;

    public AquisicaoProposta() {
    }

    public AquisicaoProposta(Integer idAquisicao) {
        this.idAquisicao = idAquisicao;
    }

    public Integer getIdAquisicao() {
        return idAquisicao;
    }

    public void setIdAquisicao(Integer idAquisicao) {
        this.idAquisicao = idAquisicao;
    }

    public Integer getValorMax() {
        return valorMax;
    }

    public void setValorMax(Integer valorMax) {
        this.valorMax = valorMax;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Boolean getGanhou() {
        return ganhou;
    }

    public void setGanhou(Boolean ganhou) {
        this.ganhou = ganhou;
    }

    public Operador getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(Operador idOperador) {
        this.idOperador = idOperador;
    }

    public Produto getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Produto idProduto) {
        this.idProduto = idProduto;
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
        return "Id da Proposta de Aquisicao: " + idAquisicao + " Proposta por : " + idUtilizador + " Id do Produto: " + idProduto + " Valor Maximo de Aquisicao: " + valorMax + " Criada em : " + createdAt + "";
    }
    
}
