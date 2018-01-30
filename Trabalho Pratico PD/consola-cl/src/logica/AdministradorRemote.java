/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;
import javax.ejb.Remote;


/**
 *
 * @author hddlucas
 */
@Remote
public interface AdministradorRemote {
 
    
    //USERS
    ArrayList<String> getUsersList();
    ArrayList<String> getUserRolesList(int userId);
    ArrayList<String> getListOfNonActiveUsers();

    boolean addNewUser(String fields);
    boolean deleteUser(int id) ;
    boolean updateUserInformation(int userId,String fields);
    boolean login(String username,String password);
    boolean activeUser(int userId);
    boolean addUserRole(int userId,int roleId);
    boolean removeUserRole(int userId,int roleId);
    int getUserIdByUsername(String username);
    
    boolean isUserIdInDB(int id);
    boolean isUserUsernameInDB(String username);
    boolean isUserBiInDB(String bi);
    boolean isUserNifInDB(String nif);
    boolean hasRole(int userId,String role);
    boolean isActive(int userId);
     
    //ROLES
    ArrayList<String> getRolesList();
    String getRoleNameById(int roleId);
   
    //PROCESSES
    ArrayList<String> getAcquisitionProposals();
    
}
