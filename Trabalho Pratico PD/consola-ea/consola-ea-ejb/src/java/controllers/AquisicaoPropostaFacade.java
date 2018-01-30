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
import models.AquisicaoProposta;
import models.Utilizador;

/**
 *
 * @author hddlucas
 */
@Stateless
public class AquisicaoPropostaFacade implements AquisicaoPropostaFacadeLocal {

  
    @EJB
    private DAOLocal dAO;
    
    @Override
    public List<AquisicaoProposta> getAcquisitionProposals() {
        Query q = dAO.getEntityManager().createNamedQuery("AquisicaoProposta.findAll");
        List<AquisicaoProposta> proposals = q.getResultList();

        return proposals;
    }
    
}
