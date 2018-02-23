/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Classes.Item;
import controllers.exceptions.RollbackFailureException;
import static java.lang.Math.toIntExact;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
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
    private MensagemFacadeLocal mensagemFacade;

    @EJB
    private AquisicaoPropostaFacadeLocal aquisicaoPropostaFacade;

    @EJB
    private UtilizadorFacadeLocal utilizadorFacade;

    
    
    private int totalGanhas = 0;
    private double totalDinheiro = 0;

    @EJB
    private DAOLocal dAO;

    @Override
    public void create(String fields) throws RollbackFailureException, Exception {
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

            dAO.getEntityManager().merge(a);
            
           
            //send notification informing that someone sent a solution proposal for this aquisition            
             JsonObjectBuilder messageFields = Json.createObjectBuilder();
             messageFields.add("id_remetente", Integer.toString(u.getIdUtilizador()));
             messageFields.add("assunto", "Proposta de Solução Recebida");
             messageFields.add("mensagem", "Recebeu uma nova proposta de Solução de " + u.getUsername() + " em " + new Timestamp(System.currentTimeMillis())+ " para a proposta " +  a.getIdAquisicao());
             messageFields.add("destinatario", Integer.toString(a.getIdUtilizador().getIdUtilizador()));
             JsonObject fieldsMessageObject= messageFields.build();
            
             mensagemFacade.sendNotification(fieldsMessageObject.toString());

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
    public String acceptProposal(String aceptFields, List <Item> items) throws RollbackFailureException, Exception {

        try {
            JSONObject acceptJsonFields = new JSONObject(aceptFields);
            Proposta p = this.findProposta(Integer.parseInt(acceptJsonFields.getString("idSolucao")));
            p.setGanhou(true);

            p.setAvaliacao(Integer.parseInt(acceptJsonFields.getString("produtoRating")));
            p.setObservacoes(acceptJsonFields.getString("observacoes"));
            
            dAO.getEntityManager().merge(p);

            Utilizador u = utilizadorFacade.findUtilizador(p.getIdUtilizador().getIdUtilizador());
            
            AvaliacaoVendedor av = new AvaliacaoVendedor();
            av.setAvaliacao(Integer.parseInt(acceptJsonFields.getString("vendedorRating")));
            av.setIdAvaliador(p.getIdAquisicao().getIdUtilizador().getIdUtilizador());
            
            av.setIdUtilizador(u);
            av.setIdProposta(p);
            p.getAvaliacaoVendedorCollection().add(av);
            u.getAvaliacaoVendedorCollection().add(av);
            
            dAO.getEntityManager().merge(u);
            dAO.getEntityManager().merge(p);
            
            //update avaliation of componente_produto 
            items.forEach((k) -> {
               String query = "UPDATE componente_produto set avaliacao=? WHERE id_componente=? AND id_aquisicao=?";               
               Query qu = dAO.getEntityManager().createNativeQuery(query);
               qu.setParameter(1, k.getAvaliacao());
               qu.setParameter(2, k.getComponente());
               qu.setParameter(3, p.getIdAquisicao().getIdAquisicao());
               qu.executeUpdate();
            });

             //send notification informing salesman that proposal was acepted
             JsonObjectBuilder messageFields = Json.createObjectBuilder();
             messageFields.add("id_remetente", Integer.toString(p.getIdAquisicao().getIdUtilizador().getIdUtilizador()));
             messageFields.add("assunto", "Proposta de Solução Aceite");
             messageFields.add("mensagem", "A sua proposta de solução foi aceite por " + p.getIdAquisicao().getIdUtilizador().getUsername() + " em " + new Timestamp(System.currentTimeMillis()) );
             messageFields.add("destinatario", Integer.toString(u.getIdUtilizador()));
             JsonObject fieldsMessageObject= messageFields.build();

             mensagemFacade.sendNotification(fieldsMessageObject.toString());
            
             return "Sucesso";
        } catch (Exception ex) {
            return ex.getMessage();
        }

    }

    @Override
    public List<Proposta> getAcquisitionProposals(int userId) {
        Query q = dAO.getEntityManager().createNamedQuery("Proposta.findByIdUtilizador");
        List<Proposta> proposals = q.setParameter("idUtilizador", userId).getResultList();

        return proposals;
    }
    
    @Override
    public void update(String fields, int propostaId) throws Exception {
        try {
            Proposta p = (Proposta) dAO.getEntityManager().find(Proposta.class, propostaId);
            JSONObject proposalFields = new JSONObject(fields);

            if(proposalFields.has("observations")){
                p.setObservacoes(proposalFields.getString("observations"));
            }
            
            if(proposalFields.has("max_value")){
                p.setValorTotal(Double.parseDouble(proposalFields.getString("max_value")));
            }
            
            dAO.getEntityManager().merge(p);
        } catch (Exception ex) {
            throw ex;
        }
    }
    
}
