/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.List;
import javax.ejb.Local;
import models.Perfil;

/**
 *
 * @author hddlucas
 */
@Local
public interface PerfilFacadeLocal {
    
     List<Perfil> getRolesList();
     String getRoleNameById(int roleId);
}
