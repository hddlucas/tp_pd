/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import controllers.AquisicaoPropostaFacadeLocal;
import controllers.PerfilFacade;
import controllers.PerfilFacadeLocal;
import controllers.UtilizadorFacade;
import controllers.UtilizadorFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import models.AquisicaoProposta;
import models.Perfil;
import models.Utilizador;

/**
 *
 * @author hddlucas
 */
@Stateful
public class Administrador implements AdministradorRemote {
    
    @EJB
    private UtilizadorFacadeLocal userFacade;
    
    @EJB
    private PerfilFacadeLocal perfilFacade;

    @EJB
    private AquisicaoPropostaFacadeLocal acquisitionFacade ;
    
    //USERS
    @Override
    public ArrayList<String> getUsersList() {

        List<Utilizador> utilizadores = userFacade.getUsersList();
        ArrayList<String> res = new ArrayList<>();
        for (Utilizador u : utilizadores) {
            res.add(u.toString());
        }

        return res;
    }

    @Override
    public ArrayList<String> getListOfNonActiveUsers() {

        List<Utilizador> utilizadores = userFacade.getListOfNonActiveUsers();
        ArrayList<String> res = new ArrayList<>();
        for (Utilizador u : utilizadores) {
            res.add(u.toString());
        }

        return res;
    }

    @Override
    public boolean addNewUser(String fields) {
        try {
            userFacade.create(fields);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteUser(int id) {

        try {
            userFacade.destroy(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUserIdInDB(int id) {
        boolean isPresent = false;
        if (userFacade.findUtilizador(id) != null) {
            isPresent = true;
        }
        return isPresent;
    }

    @Override
    public boolean isUserUsernameInDB(String username) {
        List<Utilizador> utilizadores = userFacade.findUtilizadorByUsername(username);

        if (utilizadores.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isUserBiInDB(String bi) {
        List<Utilizador> utilizadores = userFacade.findUtilizadorByBi(bi);

        if (utilizadores.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isUserNifInDB(String nif) {
        List<Utilizador> utilizadores = userFacade.findUtilizadorByNif(nif);

        if (utilizadores.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean login(String username, String password) {

        return userFacade.login(username, password);

    }

    @Override
    public boolean hasRole(int userId, String role) {

        return userFacade.hasRole(userId, role);

    }

    @Override
    public ArrayList<String> getUserRolesList(int userId) {

        List<Perfil> perfis = userFacade.getUserRolesByUserId(userId);

        ArrayList<String> res = new ArrayList<>();
        for (Perfil p : perfis) {
            res.add(p.toString());
        }

        return res;
    }

    @Override
    public boolean updateUserInformation(int userId, String fields) {

        try {
            userFacade.update(fields, userId);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isActive(int userId) {
        return userFacade.isActive(userId);
    }

    @Override
    public boolean activeUser(int userId) {

        try {
            userFacade.activeUser(userId);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int getUserIdByUsername(String username) {
        List<Utilizador> utilizadores = userFacade.findUtilizadorByUsername(username);

        return utilizadores.get(0).getIdUtilizador();
    }

    @Override
    public boolean addUserRole(int userId, int roleId) {

        try {
            
            Utilizador u = userFacade.findUtilizador(userId);
            userFacade.addUserRole(u,roleId);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean removeUserRole(int userId,int roleId) {
         try {
            userFacade.removeUserRole(userId,roleId);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
    
    

    //ROLES
    @Override
    public ArrayList<String> getRolesList() {
        List<Perfil> perfis = perfilFacade.getRolesList();
        ArrayList<String> res = new ArrayList<>();
        for (Perfil u : perfis) {
            res.add(u.toString());
        }

        return res;
    }

    @Override
    public String getRoleNameById(int roleId) {
        return perfilFacade.getRoleNameById(roleId);

    }

    @Override
    public ArrayList<String> getAcquisitionProposals() {

        List<AquisicaoProposta> proposals = acquisitionFacade.getAcquisitionProposals();
        ArrayList<String> res = new ArrayList<>();
        for (AquisicaoProposta u : proposals) {
            res.add(u.toString());
        }

        return res;
    }    
    

}
