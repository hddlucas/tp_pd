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
import models.Mensagem;
import models.Perfil;
import models.Utilizador;
import org.json.JSONObject;

/**
 *
 * @author hddlucas
 */
@Stateless
public class MensagemFacade implements MensagemFacadeLocal {

    @EJB
    private UtilizadorFacadeLocal utilizadorFacade;

    @EJB
    private DAOLocal dAO;
    
    @Override
    public List<Mensagem> getUsersMessagesList(int userId) {
        
        Utilizador user = utilizadorFacade.findUtilizador(userId);
        return (List<Mensagem>) user.getMensagemCollection();
    }
    
    
    @Override
    public void create(String fields) throws RollbackFailureException, Exception {
        try {

            JSONObject messageFields = new JSONObject(fields);
            Utilizador u = utilizadorFacade.findUtilizadorByUsername(messageFields.getString("destinatario")).get(0);
                       
            Mensagem m = new Mensagem();
            m.setIdRemetente(Integer.parseInt(messageFields.getString("id_remetente")));
            m.setAssunto(messageFields.getString("assunto"));
            m.setMensagem(messageFields.getString("mensagem")); 
            m.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            m.setLida(false);
            m.setIdDestinatario(u);
            u.getMensagemCollection().add(m);

            dAO.getEntityManager().merge(u);
            
     
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void markAsRead(int id) {
        Mensagem m = (Mensagem) dAO.getEntityManager().find(Mensagem.class, id);
        m.setLida(true);
        dAO.getEntityManager().merge(m);

    }

    @Override
    public void sendNotification(String fields) throws RollbackFailureException, Exception{
    try {

            JSONObject messageFields = new JSONObject(fields);
            Utilizador u = utilizadorFacade.findUtilizador(Integer.parseInt(messageFields.getString("destinatario")));
                       
            Mensagem m = new Mensagem();
            m.setIdRemetente(Integer.parseInt(messageFields.getString("id_remetente")));
            m.setAssunto(messageFields.getString("assunto"));
            m.setMensagem(messageFields.getString("mensagem")); 
            m.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            m.setLida(false);
            m.setIdDestinatario(u);
            u.getMensagemCollection().add(m);

            dAO.getEntityManager().merge(u);
            
        } catch (Exception ex) {
            throw ex;
        }    
    }

    @Override
    public int getTotalMessagesPerRead(Utilizador u) {
        try {
            Utilizador user = utilizadorFacade.findUtilizador(u.getIdUtilizador());

            Query q = dAO.getEntityManager().createNativeQuery("SELECT COUNT(m.mensagem) FROM mensagem m  where m.lida=false and m.id_destinatario=#idDestinatario");

            Long count = (Long) q.setParameter("idDestinatario", user.getIdUtilizador()).getSingleResult();

            return toIntExact(count);

        } catch (Exception ex) {
            return 0;
        }
    }
  
    
}
