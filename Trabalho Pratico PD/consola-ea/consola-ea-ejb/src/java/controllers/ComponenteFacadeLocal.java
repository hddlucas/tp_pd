/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.RollbackFailureException;
import java.util.List;
import javax.ejb.Local;
import models.Componente;

/**
 *
 * @author marcosequeira
 */
@Local
public interface ComponenteFacadeLocal {
    List<Componente> getComponentsList();
    Componente create(String fields) throws RollbackFailureException, Exception;
    void update(String fields, int componenteId) throws Exception;
    void destroy(Integer id) throws Exception;
}
