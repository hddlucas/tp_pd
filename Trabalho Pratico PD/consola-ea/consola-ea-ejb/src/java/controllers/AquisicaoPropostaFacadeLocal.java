/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.RollbackFailureException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import models.AquisicaoProposta;

/**
 *
 * @author hddlucas
 */
@Local
public interface AquisicaoPropostaFacadeLocal {
    
    List<AquisicaoProposta> getAcquisitionProposals();
    AquisicaoProposta findAquisicaoProposta(Integer id);
    int totalPropostasCurrentDate(Date date);
    int totalPropostas();
    int totalPropostasEmAberto();

}
