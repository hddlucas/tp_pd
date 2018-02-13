/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import models.ProdutoProposta;

/**
 *
 * @author hddlucas
 */
@Stateless
public class ProdutoPropostaController implements ProdutoPropostaControllerLocal {

    @EJB
    private DAOLocal dAO;

    @Override
    public ProdutoProposta findComponenteProduto(Integer id) {
        return dAO.getEntityManager().find(ProdutoProposta.class, id);
    }
}
