/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import controllers.PerfilJpaController;
import controllers.UtilizadorJpaController;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import models.Perfil;
import models.Utilizador;
import org.json.*;

/**
 *
 * @author hddlucas
 */
@Stateful
public class Administrador implements AdministradorRemote {

    @EJB
    private DAOLocal dao;

    //USERS
    @Override
    public ArrayList<String> getUsersList() {
        EntityManager em = dao.getEntityManager();
        UtilizadorJpaController userController = new UtilizadorJpaController(em);

        List<Utilizador> utilizadores = userController.getUsersList();
        ArrayList<String> res = new ArrayList<>();
        for (Utilizador u : utilizadores) {
            res.add(u.toString());
        }

        return res;
    }

    @Override
    public ArrayList<String> getListOfNonActiveUsers() {

        EntityManager em = dao.getEntityManager();
        UtilizadorJpaController userController = new UtilizadorJpaController(em);

        List<Utilizador> utilizadores = userController.getListOfNonActiveUsers();
        ArrayList<String> res = new ArrayList<>();
        for (Utilizador u : utilizadores) {
            res.add(u.toString());
        }

        return res;
    }

    @Override
    public boolean addNewUser(String fields) {
        EntityManager em = dao.getEntityManager();
        UtilizadorJpaController userController = new UtilizadorJpaController(em);
        try {
            userController.create(fields);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteUser(int id) {
        EntityManager em = dao.getEntityManager();
        UtilizadorJpaController userController = new UtilizadorJpaController(em);

        try {
            userController.destroy(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUserIdInDB(int id) {
        EntityManager em = dao.getEntityManager();
        UtilizadorJpaController userController = new UtilizadorJpaController(em);
        boolean isPresent = false;
        if (userController.findUtilizador(id) != null) {
            isPresent = true;
        }
        return isPresent;
    }

    @Override
    public boolean isUserUsernameInDB(String username) {
        EntityManager em = dao.getEntityManager();
        UtilizadorJpaController userController = new UtilizadorJpaController(em);
        List<Utilizador> utilizadores = userController.findUtilizadorByUsername(username);

        if (utilizadores.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isUserBiInDB(String bi) {
        EntityManager em = dao.getEntityManager();
        UtilizadorJpaController userController = new UtilizadorJpaController(em);
        List<Utilizador> utilizadores = userController.findUtilizadorByBi(bi);

        if (utilizadores.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isUserNifInDB(String nif) {
        EntityManager em = dao.getEntityManager();
        UtilizadorJpaController userController = new UtilizadorJpaController(em);
        List<Utilizador> utilizadores = userController.findUtilizadorByNif(nif);

        if (utilizadores.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean login(String username, String password) {
        EntityManager em = dao.getEntityManager();
        UtilizadorJpaController userController = new UtilizadorJpaController(em);

        return userController.login(username, password);

    }

    @Override
    public boolean hasRole(int userId, String role) {
        EntityManager em = dao.getEntityManager();
        UtilizadorJpaController userController = new UtilizadorJpaController(em);

        return userController.hasRole(userId, role);

    }

    @Override
    public ArrayList<String> getUserRolesList(int userId) {

        EntityManager em = dao.getEntityManager();
        UtilizadorJpaController userController = new UtilizadorJpaController(em);
        List<Perfil> perfis = userController.getUserRolesByUserId(userId);

        ArrayList<String> res = new ArrayList<>();
        for (Perfil p : perfis) {
            res.add(p.toString());
        }

        return res;
    }

    @Override
    public boolean updateUserInformation(int userId, String fields) {
        EntityManager em = dao.getEntityManager();
        UtilizadorJpaController userController = new UtilizadorJpaController(em);

        try {
            userController.update(fields, userId);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isActive(int userId) {
        EntityManager em = dao.getEntityManager();
        UtilizadorJpaController userController = new UtilizadorJpaController(em);
        return userController.isActive(userId);
    }

    @Override
    public boolean activeUser(int userId) {

        EntityManager em = dao.getEntityManager();
        UtilizadorJpaController userController = new UtilizadorJpaController(em);
        try {
            userController.activeUser(userId);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int getUserIdByUsername(String username) {
        EntityManager em = dao.getEntityManager();
        UtilizadorJpaController userController = new UtilizadorJpaController(em);
        List<Utilizador> utilizadores = userController.findUtilizadorByUsername(username);

        return utilizadores.get(0).getIdUtilizador();
    }

    @Override
    public boolean addUserRole(int userId, int roleId) {
        EntityManager em = dao.getEntityManager();
        UtilizadorJpaController userController = new UtilizadorJpaController(em);

        try {
            userController.addUserRole(userId,roleId);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean removeUserRole(int userId,int roleId) {
    EntityManager em = dao.getEntityManager();
        UtilizadorJpaController userController = new UtilizadorJpaController(em);
         try {
            userController.removeUserRole(userId,roleId);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
    
    

    //ROLES
    @Override
    public ArrayList<String> getRolesList() {
        EntityManager em = dao.getEntityManager();
        PerfilJpaController perfilController = new PerfilJpaController(em);

        List<Perfil> perfis = perfilController.getRolesList();
        ArrayList<String> res = new ArrayList<>();
        for (Perfil u : perfis) {
            res.add(u.toString());
        }

        return res;
    }

    @Override
    public String getRoleNameById(int roleId) {
        EntityManager em = dao.getEntityManager();
        PerfilJpaController perfilController = new PerfilJpaController(em);

        return perfilController.getRoleNameById(roleId);

    }
    

    
    

}
