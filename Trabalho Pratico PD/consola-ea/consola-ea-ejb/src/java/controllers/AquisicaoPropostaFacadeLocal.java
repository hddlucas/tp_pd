/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Classes.Item;
import controllers.exceptions.RollbackFailureException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import models.AquisicaoProposta;
import models.Proposta;
import models.Utilizador;

/**
 *
 * @author hddlucas
 */
@Local
public interface AquisicaoPropostaFacadeLocal {
    
    List<AquisicaoProposta> getAcquisitionProposals();
    List<AquisicaoProposta> getUserAcquisitionProposals(int userId);
    List<AquisicaoProposta> getOpenList();
    String create(String fields,List<Item> i)throws RollbackFailureException, Exception;
    AquisicaoProposta findAquisicaoProposta(Integer id);
    int totalPropostasCurrentDate(Date date);
    int totalPropostas();
    int totalPropostasEmAberto();
    int getTotalPropostasRecebidas(AquisicaoProposta a);
    public boolean propostaAdjudicada(AquisicaoProposta a);
    void destroy(Integer id)throws Exception;
}
