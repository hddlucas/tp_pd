/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.List;
import javax.ejb.Local;
import models.Operador;

/**
 *
 * @author hddlucas
 */
@Local
public interface OperadorFacadeLocal {
   List<Operador> getOperadorList();
}
