/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import controllers.exceptions.RollbackFailureException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import models.Perfil;
import models.Utilizador;
import org.json.JSONObject;

/**
 *
 * @author hddlucas
 */
@Stateless
public class UtilizadorFacade implements UtilizadorFacadeLocal {

    @EJB
    private DAOLocal dAO;

    @Override
    public List<Utilizador> getUsersList() {
        Query q = dAO.getEntityManager().createNamedQuery("Utilizador.findAll");
        List<Utilizador> utilizadores = q.getResultList();

        return utilizadores;
    }

    @Override
    public List<Utilizador> getListOfNonActiveUsers() {
        List<Utilizador> utilizadores = null;
        Query q = dAO.getEntityManager().createNamedQuery("Utilizador.findUsersByStatus");
        utilizadores = q
                .setParameter("ativo", false)
                .getResultList();
        return utilizadores;
    }

    @Override
    public void create(String fields) throws RollbackFailureException, Exception {
        try {

            JSONObject userFields = new JSONObject(fields);
            Utilizador u = new Utilizador();
            u.setNome(userFields.getString("nome"));
            u.setUsername(userFields.getString("username"));
            u.setPassword(userFields.getString("password"));
            u.setBi(userFields.getString("bi"));
            u.setNif(userFields.getString("nif"));
            u.setAtivo(false);

            dAO.getEntityManager().persist(u);

        } catch (Exception ex) {
            throw ex;
        }

    }

    @Override
    public void update(String fields, int userId) throws Exception {
        try {

            Utilizador u = (Utilizador) dAO.getEntityManager().find(Utilizador.class, userId);

            JSONObject userFields = new JSONObject(fields);
            u.setPais(userFields.getString("pais"));
            u.setCidade(userFields.getString("cidade"));
            u.setCodigoPostal(userFields.getString("codigo_postal"));
            u.setContacto(userFields.getString("contacto"));

            dAO.getEntityManager().merge(u);

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            Utilizador utilizador = (Utilizador) dAO.getEntityManager().find(Utilizador.class, id);
            dAO.getEntityManager().remove(utilizador);

        } catch (Exception ex) {
            try {
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    @Override
    public Utilizador findUtilizador(Integer id) {
        return dAO.getEntityManager().find(Utilizador.class, id);
    }

    @Override
    public List<Utilizador> findUtilizadorByUsername(String username) {
        List<Utilizador> utilizadores = null;
        Query q = dAO.getEntityManager().createNamedQuery("Utilizador.findByUsername");
        utilizadores = q
                .setParameter("username", username)
                .getResultList();
        return utilizadores;

    }

    @Override
    public List<Utilizador> findUtilizadorByBi(String bi) {
        List<Utilizador> utilizadores = null;
        Query q = dAO.getEntityManager().createNamedQuery("Utilizador.findByBi");
        utilizadores = q
                .setParameter("bi", bi)
                .getResultList();
        return utilizadores;
    }

    @Override
    public List<Utilizador> findUtilizadorByNif(String nif) {
        List<Utilizador> utilizadores = null;
        Query q = dAO.getEntityManager().createNamedQuery("Utilizador.findByNif");
        utilizadores = q
                .setParameter("nif", nif)
                .getResultList();
        return utilizadores;
    }

    @Override
    public boolean login(String username, String password) {
        boolean logged = false;
        List<Utilizador> utilizadores = null;
        Query q = dAO.getEntityManager().createNamedQuery("Utilizador.findByUsername");
        utilizadores = q
                .setParameter("username", username)
                .getResultList();

        if (utilizadores.size() > 0) {
            for (int i = 0; i < utilizadores.size(); i++) {
                Utilizador u = utilizadores.get(i);
                if (u.getPassword().equals(password)) {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    u.setUltimoLogin(timestamp);
                    dAO.getEntityManager().merge(u);
                    return true;
                }
            }
        }
        return logged;
    }

    @Override
    public boolean hasRole(int userId, String role) {
        boolean isAdmin = false;
        Utilizador u = (Utilizador) dAO.getEntityManager().find(Utilizador.class, userId);
        Collection<Perfil> perfis = u.getPerfilCollection();
        Iterator<Perfil> it = perfis.iterator();
        while (it.hasNext()) {
            Perfil p = it.next();
            if (p.getPerfil().equals(role)) {
                return true;
            }
        }

        return isAdmin;
    }

    @Override
    public boolean isActive(int userId) {
        boolean isAdmin = false;
        Utilizador u = (Utilizador) dAO.getEntityManager().find(Utilizador.class, userId);
        return u.getAtivo();
    }

    @Override
    public List<Perfil> getUserRolesByUserId(int userId) {
        Utilizador u = (Utilizador) dAO.getEntityManager().find(Utilizador.class, userId);
        Collection<Perfil> perfis = u.getPerfilCollection();
        List<Perfil> res = new ArrayList<Perfil>(perfis);

        return res;
    }

    @Override
    public void activeUser(int userId) {
        try {
            Utilizador u = (Utilizador) dAO.getEntityManager().find(Utilizador.class, userId);
            u.setAtivo(true);
            dAO.getEntityManager().merge(u);

        } catch (Exception ex) {
            throw ex;
        }

    }

    /**
     *
     * @param userId
     * @param roleId
     */
    @Override
    public void addUserRole(int userId, int roleId) {
        try {

            Utilizador u = (Utilizador) dAO.getEntityManager().find(Utilizador.class, userId);
            Perfil p = (Perfil) dAO.getEntityManager().find(Perfil.class, roleId);

            u.getPerfilCollection().add(p);
            p.getUtilizadorCollection().add(u);

            dAO.getEntityManager().merge(u);
            dAO.getEntityManager().merge(p);

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void removeUserRole(int userId, int roleId) {
        try {

            Utilizador u = (Utilizador) dAO.getEntityManager().find(Utilizador.class, userId);
            Perfil p = (Perfil) dAO.getEntityManager().find(Perfil.class, roleId);

            u.getPerfilCollection().remove(p);
            p.getUtilizadorCollection().remove(u);

            dAO.getEntityManager().merge(u);
            dAO.getEntityManager().merge(p);

        } catch (Exception ex) {
            throw ex;
        }
    }

}