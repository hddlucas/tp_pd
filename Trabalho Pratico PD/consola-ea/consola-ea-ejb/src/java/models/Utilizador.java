
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hddlucas
 */
@Entity
@Table(name = "utilizador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Utilizador.findAll", query = "SELECT u FROM Utilizador u")
    ,@NamedQuery(name = "Utilizador.findUsersByStatus", query = "SELECT u FROM Utilizador u WHERE u.ativo = :ativo")
    , @NamedQuery(name = "Utilizador.findByIdUtilizador", query = "SELECT u FROM Utilizador u WHERE u.idUtilizador = :idUtilizador")
    , @NamedQuery(name = "Utilizador.findByNome", query = "SELECT u FROM Utilizador u WHERE u.nome = :nome")
    , @NamedQuery(name = "Utilizador.findByUsername", query = "SELECT u FROM Utilizador u WHERE u.username = :username")
    , @NamedQuery(name = "Utilizador.findByPassword", query = "SELECT u FROM Utilizador u WHERE u.password = :password")
    , @NamedQuery(name = "Utilizador.findByBi", query = "SELECT u FROM Utilizador u WHERE u.bi = :bi")
    , @NamedQuery(name = "Utilizador.findByNif", query = "SELECT u FROM Utilizador u WHERE u.nif = :nif")
    , @NamedQuery(name = "Utilizador.findByCidade", query = "SELECT u FROM Utilizador u WHERE u.cidade = :cidade")
    , @NamedQuery(name = "Utilizador.findByPais", query = "SELECT u FROM Utilizador u WHERE u.pais = :pais")
    , @NamedQuery(name = "Utilizador.findByMorada", query = "SELECT u FROM Utilizador u WHERE u.morada = :morada")
    , @NamedQuery(name = "Utilizador.findByContacto", query = "SELECT u FROM Utilizador u WHERE u.contacto = :contacto")
    , @NamedQuery(name = "Utilizador.findByCodigoPostal", query = "SELECT u FROM Utilizador u WHERE u.codigoPostal = :codigoPostal")
    , @NamedQuery(name = "Utilizador.findByUltimoLogin", query = "SELECT u FROM Utilizador u WHERE u.ultimoLogin = :ultimoLogin")
    , @NamedQuery(name = "Utilizador.findByAtivo", query = "SELECT u FROM Utilizador u WHERE u.ativo = :ativo")})
public class Utilizador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id_utilizador", insertable = false , columnDefinition = "serial") 
    private Integer idUtilizador;
    
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "bi")
    private String bi;
    @Basic(optional = false)
    @Column(name = "nif")
    private String nif;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "pais")
    private String pais;
    @Column(name = "morada")
    private String morada;
    @Column(name = "contacto")
    private String contacto;
    @Column(name = "codigo_postal")
    private String codigoPostal;
    @Column(name = "ultimo_login")
    @Temporal(TemporalType.DATE)
    private Date ultimoLogin;
    @Basic(optional = false)
    @Column(name = "ativo")
    private boolean ativo;
    @ManyToMany(mappedBy = "utilizadorCollection")
    private Collection<Perfil> perfilCollection;

    public Utilizador() {
    }

    public Utilizador(Integer idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public Utilizador(Integer idUtilizador, String nome, String username, String password, String bi, String nif, boolean ativo) {
        this.idUtilizador = idUtilizador;
        this.nome = nome;
        this.username = username;
        this.password = password;
        this.bi = bi;
        this.nif = nif;
        this.ativo = ativo;
    }

    public Integer getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(Integer idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Date getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(Date ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @XmlTransient
    public Collection<Perfil> getPerfilCollection() {
        return perfilCollection;
    }

    public void setPerfilCollection(Collection<Perfil> perfilCollection) {
        this.perfilCollection = perfilCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUtilizador != null ? idUtilizador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utilizador)) {
            return false;
        }
        Utilizador other = (Utilizador) object;
        if ((this.idUtilizador == null && other.idUtilizador != null) || (this.idUtilizador != null && !this.idUtilizador.equals(other.idUtilizador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Id do Utilizador: " + idUtilizador + " Username: " + username + " Nome: " + nome + " BI: " + bi + " NIF: " + nif  +   " Pais: " + pais  +  " Cidade: " + cidade  +   " Codigo Postal: " + codigoPostal    +   " Contacto: " + contacto  + " Ativo: " + ativo +"" ;
}
    
}

