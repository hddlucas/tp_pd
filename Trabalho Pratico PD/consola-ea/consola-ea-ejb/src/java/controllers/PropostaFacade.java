/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.sun.org.glassfish.external.probe.provider.annotations.Probe;
import controllers.exceptions.RollbackFailureException;
import static java.lang.Integer.parseInt;
import static java.lang.Math.toIntExact;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import models.AquisicaoProposta;
import models.ProdutoProposta;
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
    private AquisicaoPropostaFacadeLocal aquisicaoPropostaFacade;

    @EJB
    private UtilizadorFacadeLocal utilizadorFacade;

    
    
    private int totalGanhas = 0;
    private double totalDinheiro = 0;

    @EJB
    private DAOLocal dAO;

    @Override
    public String create(String fields) throws RollbackFailureException, Exception {
        try {

            JSONObject proposalFields = new JSONObject(fields);
            Utilizador u = utilizadorFacade.findUtilizador(Integer.parseInt(proposalFields.getString("idUtilizador")));
            AquisicaoProposta a = aquisicaoPropostaFacade.findAquisicaoProposta(Integer.parseInt(proposalFields.getString("idAquisicao")));
            
            
            Proposta p = new Proposta();
            p.setValorTotal(Double.parseDouble(proposalFields.getString("valor")));
            p.setDescricao(proposalFields.getString("observacoes"));
            p.setGanhou(Boolean.FALSE);
            p.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            
            p.setIdUtilizador(u);
            u.getPropostaCollection().add(p);
            
            dAO.getEntityManager().merge(u);
            
//            

            return "1";
            
            
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public int getTotalWin() {
        try {

            Query q = dAO.getEntityManager().createNativeQuery("SELECT COUNT(p.id_proposta) FROM proposta p where p.ganhou=true");

            Long count = (Long) q.getSingleResult();
            
            return toIntExact(count);
            
        } catch (Exception ex) {
            return 1;
        }
    }

    @Override
    public Double getTotalTransactedMoney() {

        try {
            Query q = dAO.getEntityManager().createNativeQuery("SELECT SUM(p.valor_total) FROM proposta p where p.ganhou=true");

            if(q.getSingleResult()!=null)
                return (Double) q.getSingleResult();
            return 0.0;
            
        } catch (Exception ex) {
            return 0.0;
        }
    }
    
    @Override
    public List<Proposta> findPropostasSolucaoByPropostaAquisicao(AquisicaoProposta a){
        
        Query q = dAO.getEntityManager().createNamedQuery("Proposta.findPropostasSolucaoByPropostaAquisicao");
        List<Proposta> propostasSolucao = q.setParameter("idAquisicao", a.getIdAquisicao()).getResultList();

        return propostasSolucao;
    }

}
