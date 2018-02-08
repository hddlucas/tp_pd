/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.RollbackFailureException;
import static java.lang.Integer.parseInt;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import models.AquisicaoProposta;
import models.Produto;
import models.Proposta;
import models.Utilizador;
import org.json.JSONObject;

/**
 *
 * @author marcosequeira
 */
@Stateless
public class PropostaFacade implements PropostaFacadeLocal {

    @EJB
    private UtilizadorFacadeLocal utilizadorFacade;

    private int totalGanhas=0;

    @EJB
    private DAOLocal dAO;
    
    @Override
    public void create(String fields) throws RollbackFailureException, Exception {
        try {

            JSONObject proposalFields = new JSONObject(fields);
            Utilizador u = utilizadorFacade.findUtilizador(Integer.parseInt(proposalFields.getString("idUtilizador")));            
            Proposta p = new Proposta();
            p.setValorTotal(Integer.parseInt(proposalFields.getString("valor_total")));
            p.setGanhou(Boolean.FALSE);

            p.setIdUtilizador(u);
            
            u.getPropostaCollection().add(p);
            
            dAO.getEntityManager().merge(u);

            
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public int getTotalWin() {

         try {
             Query q = dAO.getEntityManager().createNamedQuery("Proposta.findAll");

            totalGanhas=0;
            List<Proposta> proposals = q.getResultList();

            proposals.forEach((k) -> {
                if (k.getGanhou() == true) {
                    totalGanhas++;
                }
            });
            
            return totalGanhas;
            
        } catch (Exception ex) {
            return 0;
        }
        
    }
    
    
    
    
    
    
}
