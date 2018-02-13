/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controllers.PerfilFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import models.Perfil;

/**
 *
 * @author hddlucas
 */
@Named(value = "perfisBean")
@ViewScoped
public class PerfisBean implements Serializable {

    @EJB
    private PerfilFacadeLocal perfilFacade;
    private Integer idPerfil;
    private String perfil;
    private String descricao;
    
    /**
     * Creates a new instance of PerfisBean
     */
    public PerfisBean() {
    }
    
    public List<Perfil> getList() {
        return perfilFacade.getRolesList();
    }

    
    //PROPRIEDADES
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
    
    
}
