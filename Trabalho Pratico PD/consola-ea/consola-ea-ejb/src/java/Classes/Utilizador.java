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
import models.AquisicaoProposta;
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
public class Utilizador {
    
    private Integer idUtilizador;
    private String nome;
    private String username;
    private String password;
    private String bi;
    private String nif;
    private String cidade;
    private String pais;
    private String morada;
    private String contacto;
    private String codigoPostal;
    private Date ultimoLogin;
    private boolean ativo;

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
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUtilizador != null ? idUtilizador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof models.Utilizador)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Id do Utilizador: " + idUtilizador + " Username: " + username + " Nome: " + nome + " BI: " + bi + " NIF: " + nif  +   " Pais: " + pais  +  " Cidade: " + cidade  +   " Codigo Postal: " + codigoPostal    +   " Contacto: " + contacto  + " Ativo: " + ativo +"" ;
}

    
    
}
