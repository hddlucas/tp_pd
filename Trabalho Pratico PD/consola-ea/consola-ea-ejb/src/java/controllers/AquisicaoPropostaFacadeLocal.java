/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Classes.Item;
import controllers.exceptions.RollbackFailureException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import models.AquisicaoProposta;
import models.ComponenteProduto;
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
    List<AquisicaoProposta> getClosedList();
    String create(String fields,List<Item> i)throws RollbackFailureException, Exception;
    AquisicaoProposta findAquisicaoProposta(Integer id);
    int totalPropostasCurrentDate(Date date);
    int totalPropostas();
    int totalPropostasEmAberto();
    int getTotalPropostasRecebidas(AquisicaoProposta a);
    public boolean propostaAdjudicada(AquisicaoProposta a);
    void destroy(Integer id)throws Exception;
    String getUltimaPropostaId();
    String update(String fields, List<Item> i, int propostaId) throws Exception;
    public List <ComponenteProduto> getComponenteProduto(AquisicaoProposta a);
    List <ComponenteProduto> getComponenteProdutoId(int id);
}
