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
import models.Categoria;
import models.Operador;

/**
 *
 * @author hddlucas
 */
@Stateless
public class OperadorFacade implements OperadorFacadeLocal {

    @EJB
    private DAOLocal dAO;
    
    @Override
    public List<Operador> getOperadorList() {
        Query q = dAO.getEntityManager().createNamedQuery("Operador.findAll");
        List<Operador> operadores = q.getResultList();

        return operadores;
    }
    
}
