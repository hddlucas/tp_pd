/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.RollbackFailureException;
import javax.ejb.Local;

/**
 *
 * @author marcosequeira
 */
@Local
public interface PropostaFacadeLocal {
    void create(String fields) throws RollbackFailureException, Exception;
    int getTotalWin();
    Double getTotalTransactedMoney();


}
