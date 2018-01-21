/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Query;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import models.Perfil;
import models.Utilizador;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author hddlucas
 */
public class UtilizadorJpaController implements Serializable {

    public UtilizadorJpaController(EntityManager em) {
        this.em = em;
    }
    private EntityManager em = null;

    public List<Utilizador> getUsersList() {
        Query q = em.createNamedQuery("Utilizador.findAll");
        List<Utilizador> utilizadores = q.getResultList();

        return utilizadores;
    }

    public List<Utilizador> getListOfNonActiveUsers() {
        List<Utilizador> utilizadores = null;
        Query q = em.createNamedQuery("Utilizador.findUsersByStatus");
        utilizadores = q
                .setParameter("ativo", false)
                .getResultList();
        return utilizadores;
    }

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

            em.persist(u);

        } catch (Exception ex) {
            throw ex;
        }

    }

    public void update(String fields, int userId) throws Exception {
        try {

            Utilizador u = (Utilizador) em.find(Utilizador.class, userId);

            JSONObject userFields = new JSONObject(fields);
            u.setPais(userFields.getString("pais"));
            u.setCidade(userFields.getString("cidade"));
            u.setCodigoPostal(userFields.getString("codigo_postal"));
            u.setContacto(userFields.getString("contacto"));

            em.merge(u);

        } catch (Exception ex) {
            throw ex;
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            Utilizador utilizador = (Utilizador) em.find(Utilizador.class, id);
            em.remove(utilizador);

        } catch (Exception ex) {
            try {
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    public Utilizador findUtilizador(Integer id) {
        return em.find(Utilizador.class, id);
    }

    public List<Utilizador> findUtilizadorByUsername(String username) {
        List<Utilizador> utilizadores = null;
        Query q = em.createNamedQuery("Utilizador.findByUsername");
        utilizadores = q
                .setParameter("username", username)
                .getResultList();
        return utilizadores;

    }

    public List<Utilizador> findUtilizadorByBi(String bi) {
        List<Utilizador> utilizadores = null;
        Query q = em.createNamedQuery("Utilizador.findByBi");
        utilizadores = q
                .setParameter("bi", bi)
                .getResultList();
        return utilizadores;
    }

    public List<Utilizador> findUtilizadorByNif(String nif) {
        List<Utilizador> utilizadores = null;
        Query q = em.createNamedQuery("Utilizador.findByNif");
        utilizadores = q
                .setParameter("nif", nif)
                .getResultList();
        return utilizadores;
    }

    public boolean login(String username, String password) {
        boolean logged = false;
        List<Utilizador> utilizadores = null;
        Query q = em.createNamedQuery("Utilizador.findByUsername");
        utilizadores = q
                .setParameter("username", username)
                .getResultList();

        if (utilizadores.size() > 0) {
            for (int i = 0; i < utilizadores.size(); i++) {
                Utilizador u = utilizadores.get(i);
                if (u.getPassword().equals(password)) {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    u.setUltimoLogin(timestamp);
                    em.merge(u);
                    return true;
                }
            }
        }
        return logged;
    }

    public boolean hasRole(int userId, String role) {
        boolean isAdmin = false;
        Utilizador u = (Utilizador) em.find(Utilizador.class, userId);
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

    public boolean isActive(int userId) {
        boolean isAdmin = false;
        Utilizador u = (Utilizador) em.find(Utilizador.class, userId);
        return u.getAtivo();
    }

    public List<Perfil> getUserRolesByUserId(int userId) {
        Utilizador u = (Utilizador) em.find(Utilizador.class, userId);
        Collection<Perfil> perfis = u.getPerfilCollection();
        List<Perfil> res = new ArrayList<Perfil>(perfis);

        return res;
    }

    public void activeUser(int userId) {
        try {
            Utilizador u = (Utilizador) em.find(Utilizador.class, userId);
            u.setAtivo(true);
            em.merge(u);

        } catch (Exception ex) {
            throw ex;
        }

    }

    public void addUserRole(int userId, int roleId) {
        try {
            
            Utilizador u = (Utilizador) em.find(Utilizador.class, userId);
            Perfil p = (Perfil) em.find(Perfil.class, roleId);
            
            u.getPerfilCollection().add(p);
            p.getUtilizadorCollection().add(u);
            
            em.merge(u);
            em.merge(p);
            
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    
    
    
    public void removeUserRole(int userId, int roleId) {
        try {
            
            Utilizador u = (Utilizador) em.find(Utilizador.class, userId);
            Perfil p = (Perfil) em.find(Perfil.class, roleId);
            
            u.getPerfilCollection().remove(p);
            p.getUtilizadorCollection().remove(u);
            
            em.merge(u);
            em.merge(p);
            
        } catch (Exception ex) {
            throw ex;
        }
    }
    

}
