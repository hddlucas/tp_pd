/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import controllers.exceptions.PreexistingEntityException;
import controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.Utilizador;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import models.Perfil;

/**
 *
 * @author hddlucas
 */
public class PerfilJpaController implements Serializable {

    public PerfilJpaController( EntityManager em) {
        this.em = em;
    }

    private EntityManager em = null;

  
    public List<Perfil> getRolesList() {
     Query q = em.createNamedQuery("Perfil.findAll");
     List<Perfil> perfis = q.getResultList();

     return perfis;
    }
    
    
    public String getRoleNameById(int roleId){
        
       Perfil p = em.find(Perfil.class, roleId); 
       return p.getPerfil();
       
    }
    
    
}
