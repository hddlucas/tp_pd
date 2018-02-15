/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.RollbackFailureException;
import static java.lang.Math.toIntExact;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import models.AquisicaoProposta;
import models.AvaliacaoVendedor;
import models.Mensagem;
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
            
            p.setIdAquisicao(a);
            a.getPropostaList().add(p);

            dAO.getEntityManager().merge(a);
            
            return "1";

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Proposta findProposta(Integer id) {
        return dAO.getEntityManager().find(Proposta.class, id);
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

            if (q.getSingleResult() != null) {
                return (Double) q.getSingleResult();
            }
            return 0.0;

        } catch (Exception ex) {
            return 0.0;
        }
    }

    @Override
    public List<Proposta> findPropostasSolucaoByPropostaAquisicao(AquisicaoProposta a) {        
        AquisicaoProposta aq = aquisicaoPropostaFacade.findAquisicaoProposta(a.getIdAquisicao());
        return aq.getPropostaList();
    }

    @Override
    public void acceptProposal(String aceptFields) throws RollbackFailureException, Exception {

        try {
            
            JSONObject acceptJsonFields = new JSONObject(aceptFields);
            Proposta p = this.findProposta(Integer.parseInt(acceptJsonFields.getString("idSolucao")));
            p.setGanhou(true);

            p.setAvaliacao(acceptJsonFields.getString("produtoRating"));
            p.setObservacoes(acceptJsonFields.getString("observacoes"));
            
            dAO.getEntityManager().merge(p);


            Utilizador u = utilizadorFacade.findUtilizador(p.getIdUtilizador().getIdUtilizador());
             
            AvaliacaoVendedor av = new AvaliacaoVendedor();
            av.setAvaliacao(Integer.parseInt(acceptJsonFields.getString("vendedorRating")));
            av.setIdAvaliador(p.getIdAquisicao().getIdUtilizador().getIdUtilizador());
            
            av.setIdUtilizador(u);
            u.getAvaliacaoVendedorCollection().add(av);
            
            dAO.getEntityManager().merge(u);

        } catch (Exception ex) {
            throw ex;
        }

    }

}
