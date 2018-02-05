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
@Table(name = "mensagem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mensagem.findAll", query = "SELECT m FROM Mensagem m")
    , @NamedQuery(name = "Mensagem.findByIdMensagem", query = "SELECT m FROM Mensagem m WHERE m.idMensagem = :idMensagem")
    , @NamedQuery(name = "Mensagem.findByIdRemetente", query = "SELECT m FROM Mensagem m WHERE m.idRemetente = :idRemetente")
    , @NamedQuery(name = "Mensagem.findByAssunto", query = "SELECT m FROM Mensagem m WHERE m.assunto = :assunto")
    , @NamedQuery(name = "Mensagem.findByMensagem", query = "SELECT m FROM Mensagem m WHERE m.mensagem = :mensagem")
    , @NamedQuery(name = "Mensagem.findByLida", query = "SELECT m FROM Mensagem m WHERE m.lida = :lida")
    , @NamedQuery(name = "Mensagem.findByEliminadaRemetente", query = "SELECT m FROM Mensagem m WHERE m.eliminadaRemetente = :eliminadaRemetente")
    , @NamedQuery(name = "Mensagem.findByEliminadaDestinatario", query = "SELECT m FROM Mensagem m WHERE m.eliminadaDestinatario = :eliminadaDestinatario")
    , @NamedQuery(name = "Mensagem.findByCreatedAt", query = "SELECT m FROM Mensagem m WHERE m.createdAt = :createdAt")})
public class Mensagem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id_mensagem", insertable = true , columnDefinition = "serial") 
    private Integer idMensagem;
    @Basic(optional = false)
    @Column(name = "id_remetente")
    private int idRemetente;
    @Basic(optional = false)
    @Column(name = "assunto")
    private String assunto;
    @Basic(optional = false)
    @Column(name = "mensagem")
    private String mensagem;
    @Column(name = "lida")
    private Boolean lida;
    @Column(name = "eliminada_remetente")
    private Boolean eliminadaRemetente;
    @Column(name = "eliminada_destinatario")
    private Boolean eliminadaDestinatario;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "id_destinatario", referencedColumnName = "id_utilizador")
    @ManyToOne(optional = false)
    private Utilizador idDestinatario;

    public Mensagem() {
    }

    public Mensagem(Integer idMensagem) {
        this.idMensagem = idMensagem;
    }

    public Mensagem(Integer idMensagem, int idRemetente, String assunto, String mensagem) {
        this.idMensagem = idMensagem;
        this.idRemetente = idRemetente;
        this.assunto = assunto;
        this.mensagem = mensagem;
    }

    public Integer getIdMensagem() {
        return idMensagem;
    }

    public void setIdMensagem(Integer idMensagem) {
        this.idMensagem = idMensagem;
    }

    public int getIdRemetente() {
        return idRemetente;
    }

    public void setIdRemetente(int idRemetente) {
        this.idRemetente = idRemetente;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Boolean getLida() {
        return lida;
    }

    public void setLida(Boolean lida) {
        this.lida = lida;
    }

    public Boolean getEliminadaRemetente() {
        return eliminadaRemetente;
    }

    public void setEliminadaRemetente(Boolean eliminadaRemetente) {
        this.eliminadaRemetente = eliminadaRemetente;
    }

    public Boolean getEliminadaDestinatario() {
        return eliminadaDestinatario;
    }

    public void setEliminadaDestinatario(Boolean eliminadaDestinatario) {
        this.eliminadaDestinatario = eliminadaDestinatario;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Utilizador getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(Utilizador idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMensagem != null ? idMensagem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mensagem)) {
            return false;
        }
        Mensagem other = (Mensagem) object;
        if ((this.idMensagem == null && other.idMensagem != null) || (this.idMensagem != null && !this.idMensagem.equals(other.idMensagem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Mensagem[ idMensagem=" + idMensagem + " ]";
    }
    
}
