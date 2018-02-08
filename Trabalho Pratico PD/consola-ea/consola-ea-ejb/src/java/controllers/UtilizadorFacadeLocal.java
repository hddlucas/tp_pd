/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.List;
import javax.ejb.Local;
import models.Perfil;
import models.Utilizador;

/**
 *
 * @author hddlucas
 */
@Local
public interface UtilizadorFacadeLocal {
    
    List<Utilizador> getUsersList();
    List<Utilizador> getListOfNonActiveUsers();
    Utilizador create(String fields)throws Exception;
    void update(String fields, int userId)throws Exception;
    void destroy(Integer id)throws Exception;
    Utilizador findUtilizador(Integer id);
    List<Utilizador> findUtilizadorByUsername(String username);
    List<Utilizador> findUtilizadorByBi(String bi);
    List<Utilizador> findUtilizadorByNif(String nif);
    boolean login(String username, String password);
    boolean hasRole(int userId, String role);
    boolean isActive(int userId);
    List<Perfil> getUserRolesByUserId(int userId);
    void activeUser(int userId);
    void addUserRole(int userId, int roleId);
    void removeUserRole(int userId, int roleId);
    public String getUserNameById(int userId);
    public int getTotalUsersPerRole(String  roleName);
}
