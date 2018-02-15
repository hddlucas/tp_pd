/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.RollbackFailureException;
import java.util.List;
import javax.ejb.Local;
import models.Mensagem;
import models.Utilizador;


/**
 *
 * @author hddlucas
 */
@Local
public interface MensagemFacadeLocal {
     public void create(String fields)throws RollbackFailureException, Exception;
     List<Mensagem> getUsersMessagesList(int userId);
     public void markAsRead(int id);
     public void sendNotification(String fields)throws RollbackFailureException, Exception;
}
