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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import models.AquisicaoProposta;
import models.Categoria;
import models.Componente;
import models.ComponenteProduto;
import models.Operador;
import models.Utilizador;
import org.json.JSONObject;

/**
 *
 * @author hddlucas
 */
@Stateless
public class AquisicaoPropostaFacade implements AquisicaoPropostaFacadeLocal {

    @EJB
    private OperadorFacadeLocal operadorFacade;

    @EJB
    private ComponenteFacadeLocal componenteFacade;

    @EJB
    private UtilizadorFacadeLocal utilizadorFacade;

    @EJB
    private DAOLocal dAO;

    @Override
    public List<AquisicaoProposta> getAcquisitionProposals() {
        Query q = dAO.getEntityManager().createNamedQuery("AquisicaoProposta.findAllNotDeleted");
        List<AquisicaoProposta> proposals = q.getResultList();

        return proposals;
    }

    @Override
    public List<AquisicaoProposta> getUserAcquisitionProposals(int userId) {
            
        Utilizador u =utilizadorFacade.findUtilizador(userId);
        
        List<AquisicaoProposta> proposals = (List<AquisicaoProposta>) u.getAquisicaoPropostaCollection();
        proposals.removeIf(p -> p.getDeleted() != false);

        return proposals;
    }

    @Override
    public AquisicaoProposta findAquisicaoProposta(Integer id) {
        return dAO.getEntityManager().find(AquisicaoProposta.class, id);
    }

    @Override
    public String create(String fields, List<Item> i) throws RollbackFailureException, Exception {
        try {

            JSONObject proposalFields = new JSONObject(fields);
            Utilizador u = utilizadorFacade.findUtilizador(Integer.parseInt(proposalFields.getString("id_utilizador")));

            AquisicaoProposta a = new AquisicaoProposta();
            a.setValorMax(Double.parseDouble(proposalFields.getString("valor_max")));
            a.setObservacoes(proposalFields.getString("observacoes"));
            a.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            a.setIdUtilizador(u);
            u.getAquisicaoPropostaCollection().add(a);

            dAO.getEntityManager().merge(u);

            
            i.forEach((x) -> {
                ComponenteProduto p = new ComponenteProduto();
                Componente c = componenteFacade.findComponente(x.getComponente());
                Operador op = operadorFacade.findOperador(x.getOperador());

                p.setAquisicaoProposta(a);
                p.setComponente(c);

                p.setValor(x.getValor());
                
                p.setAquisicaoProposta(a);
                p.setComponente(c);

                //a.getComponenteProdutoCollection().add(p);
                c.getComponenteProdutoCollection().add(p);
                
                dAO.getEntityManager().persist(p);


                dAO.getEntityManager().merge(c);  
                dAO.getEntityManager().merge(a);  
            });

            return a.toString();

            //return p.getValor();
        } catch (Exception ex) {
            return ex.getMessage();
        }

    }

    @Override
    public int totalPropostasCurrentDate(Date date) {

        List<AquisicaoProposta> proposals = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String data = formatter.format(date);
        Query q = dAO.getEntityManager().createNativeQuery("SELECT * FROM aquisicao_proposta p where p.deleted=false and date(p.created_at) = ' " + data + "';");
        proposals = q
                .getResultList();
        return proposals.size();
    }

    @Override
    public int totalPropostas() {
        Query q = dAO.getEntityManager().createNativeQuery("SELECT COUNT(a.id_aquisicao) FROM aquisicao_proposta a where a.deleted=false");

        Long count = (Long) q.getSingleResult();

        return toIntExact(count);
    }

    @Override
    public int totalPropostasEmAberto() {

        //encontras as que foram ganhas
        Query q = dAO.getEntityManager().createNativeQuery("SELECT count(a.id_aquisicao) FROM aquisicao_proposta a, proposta p where a.id_aquisicao = p.id_aquisicao AND p.ganhou=true");
        Long count = (Long) q.getSingleResult();

        List<AquisicaoProposta> proposals = this.getAcquisitionProposals();
        int total = proposals != null ? proposals.size() : 0;

        return total - toIntExact(count);
    }

    @Override
    public int getTotalPropostasRecebidas(AquisicaoProposta a) {
        Query q = dAO.getEntityManager().createNativeQuery("SELECT count(p.id_proposta) FROM proposta p where p.deleted=false AND p.id_aquisicao= #idAquisicao");
        Long count = (Long) q.setParameter("idAquisicao", a.getIdAquisicao())
                .getSingleResult();

        return toIntExact(count);

    }

    @Override
    public boolean propostaAdjudicada(AquisicaoProposta a) {
        Query q = dAO.getEntityManager().createNativeQuery("SELECT count(p.id_proposta) FROM  proposta p where p.deleted=false AND p.ganhou=true AND p.id_aquisicao= #idAquisicao ");
        Long count = (Long) q.setParameter("idAquisicao", a.getIdAquisicao())
                .getSingleResult();

        return count > 0;
    }

    
    @Override
    public void destroy(Integer id) throws Exception  {
        try {
            AquisicaoProposta a = dAO.getEntityManager().find(AquisicaoProposta.class, id);
            a.setDeleted(true);
            dAO.getEntityManager().merge(a);

        } catch (Exception ex) { 
            throw ex;
        }
    }
    
    
  
     
    @Override
    public List<AquisicaoProposta> getOpenList() {
        Query q = dAO.getEntityManager().createNamedQuery("AquisicaoProposta.findAll");
        List<AquisicaoProposta> proposals = q.getResultList();
        
        List<AquisicaoProposta> toReturn = q.getResultList();
        
        proposals.forEach((k) -> {
            if (propostaAdjudicada(k) == true) {
                toReturn.remove(k);
            }
        });
               
        return toReturn;
    }
    
    
    @Override
    public List<AquisicaoProposta> getClosedList() {
        Query q = dAO.getEntityManager().createNamedQuery("AquisicaoProposta.findAll");
        List<AquisicaoProposta> proposals = q.getResultList();
        
        List<AquisicaoProposta> toReturn = q.getResultList();
        
        proposals.forEach((k) -> {
            if (propostaAdjudicada(k) == false) {
                toReturn.remove(k);
            }
        });
               
        return toReturn;
    }
    
    
}
