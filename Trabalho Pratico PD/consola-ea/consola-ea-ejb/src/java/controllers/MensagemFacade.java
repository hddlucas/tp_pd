/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.RollbackFailureException;
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
    public void create(String fields) throws Exception {
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

  
    
}
