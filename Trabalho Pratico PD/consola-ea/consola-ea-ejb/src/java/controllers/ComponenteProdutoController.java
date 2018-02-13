/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import models.Componente;
import models.ComponenteProduto;

/**
 *
 * @author hddlucas
 */
@Stateless
public class ComponenteProdutoController implements ComponenteProdutoControllerLocal {
    @EJB
    private DAOLocal dAO;

    @Override
    public ComponenteProduto findComponenteProduto(Integer id) {
        return dAO.getEntityManager().find(ComponenteProduto.class, id);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
