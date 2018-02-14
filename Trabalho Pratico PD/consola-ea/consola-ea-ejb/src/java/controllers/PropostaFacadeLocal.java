/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.RollbackFailureException;
import java.util.List;
import javax.ejb.Local;
import models.AquisicaoProposta;
import models.Proposta;

/**
 *
 * @author marcosequeira
 */
@Local
public interface PropostaFacadeLocal {
    String create(String fields) throws RollbackFailureException, Exception;
    int getTotalWin();
    Double getTotalTransactedMoney();
    List<Proposta> findPropostasSolucaoByPropostaAquisicao(AquisicaoProposta a);
    void acceptProposal(String aceptFields) throws RollbackFailureException, Exception;
    Proposta findProposta(Integer id);

}
