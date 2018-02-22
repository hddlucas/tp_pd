/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Classes.Item;
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
    void create(String fields) throws RollbackFailureException, Exception;
    int getTotalWin();
    Double getTotalTransactedMoney();
    List<Proposta> findPropostasSolucaoByPropostaAquisicao(AquisicaoProposta a);
    String acceptProposal(String aceptFields, List <Item> items) throws RollbackFailureException, Exception;
    Proposta findProposta(Integer id);
    List<Proposta> getAcquisitionProposals(int userId);
    void update(String fields, int propostaId) throws Exception;

}
