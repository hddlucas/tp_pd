/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import models.Perfil;

/**
 *
 * @author hddlucas
 */
@Stateless
public class PerfilFacade implements PerfilFacadeLocal {

    @EJB
    private DAOLocal dAO;

  
    @Override
    public List<Perfil> getRolesList() {
     Query q = dAO.getEntityManager().createNamedQuery("Perfil.findAll");
     List<Perfil> perfis = q.getResultList();

     return perfis;
    }
    
    
    @Override
    public String getRoleNameById(int roleId){
        
       Perfil p = dAO.getEntityManager().find(Perfil.class, roleId); 
       return p.getPerfil();
       
    }
}
