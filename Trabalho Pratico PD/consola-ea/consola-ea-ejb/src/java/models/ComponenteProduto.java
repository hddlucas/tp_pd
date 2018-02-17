/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "componente_produto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComponenteProduto.findAll", query = "SELECT c FROM ComponenteProduto c")
    , @NamedQuery(name = "ComponenteProduto.findByIdComponente", query = "SELECT c FROM ComponenteProduto c WHERE c.componenteProdutoPK.idComponente = :idComponente")
    , @NamedQuery(name = "ComponenteProduto.findByIdAquisicao", query = "SELECT c FROM ComponenteProduto c WHERE c.componenteProdutoPK.idAquisicao = :idAquisicao")
    , @NamedQuery(name = "ComponenteProduto.findByAvaliacao", query = "SELECT c FROM ComponenteProduto c WHERE c.avaliacao = :avaliacao")
    , @NamedQuery(name = "ComponenteProduto.findByValor", query = "SELECT c FROM ComponenteProduto c WHERE c.valor = :valor")
    , @NamedQuery(name = "ComponenteProduto.findByOperador", query = "SELECT c FROM ComponenteProduto c WHERE c.operador = :operador")})
public class ComponenteProduto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComponenteProdutoPK componenteProdutoPK;
    @Column(name = "avaliacao")
    private Integer avaliacao;
    @Column(name = "valor")
    private String valor;
    @Column(name = "operador")
    private String operador;
    @JoinColumn(name = "id_aquisicao", referencedColumnName = "id_aquisicao", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AquisicaoProposta aquisicaoProposta;
    @JoinColumn(name = "id_componente", referencedColumnName = "id_componente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Componente componente;

    public ComponenteProduto() {
    }

    public ComponenteProduto(ComponenteProdutoPK componenteProdutoPK) {
        this.componenteProdutoPK = componenteProdutoPK;
    }

    public ComponenteProduto(int idComponente, int idAquisicao) {
        this.componenteProdutoPK = new ComponenteProdutoPK(idComponente, idAquisicao);
    }

    public ComponenteProdutoPK getComponenteProdutoPK() {
        return componenteProdutoPK;
    }

    public void setComponenteProdutoPK(ComponenteProdutoPK componenteProdutoPK) {
        this.componenteProdutoPK = componenteProdutoPK;
    }

    public Integer getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Integer avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public AquisicaoProposta getAquisicaoProposta() {
        return aquisicaoProposta;
    }

    public void setAquisicaoProposta(AquisicaoProposta aquisicaoProposta) {
        this.aquisicaoProposta = aquisicaoProposta;
    }

    public Componente getComponente() {
        return componente;
    }

    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (componenteProdutoPK != null ? componenteProdutoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComponenteProduto)) {
            return false;
        }
        ComponenteProduto other = (ComponenteProduto) object;
        if ((this.componenteProdutoPK == null && other.componenteProdutoPK != null) || (this.componenteProdutoPK != null && !this.componenteProdutoPK.equals(other.componenteProdutoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.ComponenteProduto[ componenteProdutoPK=" + componenteProdutoPK + " ]";
    }
    
}
