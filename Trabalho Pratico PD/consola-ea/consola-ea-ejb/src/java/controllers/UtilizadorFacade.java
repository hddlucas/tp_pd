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

    private int total = 0;

    @Override
    public List<Utilizador> getUsersList() {
        Query q = dAO.getEntityManager().createNamedQuery("Utilizador.findAllNotDeleted");
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

    /**
     *
     * @param u
     * @param roleId
     */
    @Override
    public void addUserRole(Utilizador u, int roleId) {
        try {

            Perfil p = (Perfil) dAO.getEntityManager().find(Perfil.class, roleId);
            if (u.getPerfilCollection().size() > 0 && roleId == 4 || this.hasRole(u.getIdUtilizador(), "vendedor"))  {
                return;
            }
            
       

            u.getPerfilCollection().add(p);
            p.getUtilizadorCollection().add(u);

            dAO.getEntityManager().merge(u);
            dAO.getEntityManager().merge(p);

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Utilizador create(String fields) throws RollbackFailureException, Exception {
        try {

            JSONObject userFields = new JSONObject(fields);
            Utilizador u = new Utilizador();
            u.setNome(userFields.getString("nome"));
            u.setUsername(userFields.getString("username"));
            u.setPassword(userFields.getString("password"));
            u.setBi(userFields.getString("bi"));
            u.setNif(userFields.getString("nif"));

            if (userFields.has("morada")) {
                u.setMorada(userFields.getString("morada"));
            }
            if (userFields.has("contacto")) {
                u.setContacto(userFields.getString("contacto"));
            }
            if (userFields.has("codigo_postal")) {
                u.setCodigoPostal(userFields.getString("codigo_postal"));
            }
            if (userFields.has("pais")) {
                u.setPais(userFields.getString("pais"));
            }
            if (userFields.has("cidade")) {
                u.setCidade(userFields.getString("cidade"));
            }
            if (userFields.has("ativo")) {
                u.setAtivo(true);
            } else if (userFields.has("tipo_de_conta")) {
                if (userFields.getString("tipo_de_conta").equals("vendedor")) {
                    u.setAtivo(false);
                } else {
                    u.setAtivo(true);
                }
            } else {
                u.setAtivo(true);
            }

            dAO.getEntityManager().persist(u);

            if (userFields.has("tipo_de_conta")) {
                if (userFields.getString("tipo_de_conta").equals("vendedor")) {
                    this.addUserRole(this.findUtilizadorByUsername(userFields.getString("username")).get(0), 4);
                }
            }

            return u;

        } catch (Exception ex) {
            throw ex;
        }

    }

    @Override
    public void update(String fields, int userId) throws Exception {
        try {
            Utilizador u = (Utilizador) dAO.getEntityManager().find(Utilizador.class, userId);
            JSONObject userFields = new JSONObject(fields);

            if (userFields.has("nome")) {
                u.setNome(userFields.getString("nome"));
            }
            if (userFields.has("password")) {
                u.setPassword(userFields.getString("password"));
            }
            if (userFields.has("nif")) {
                u.setNif(userFields.getString("nif"));
            }
            if (userFields.has("bi")) {
                u.setBi(userFields.getString("bi"));
            }

            if (userFields.has("codigo_postal")) {
                u.setCodigoPostal(userFields.getString("codigo_postal"));
            }

            if (userFields.has("morada")) {
                u.setMorada(userFields.getString("morada"));
            }

            if (userFields.has("contacto")) {
                u.setContacto(userFields.getString("contacto"));
            }
            if (userFields.has("pais")) {
                u.setPais(userFields.getString("pais"));
            }
            if (userFields.has("cidade")) {
                u.setCidade(userFields.getString("cidade"));
            }
            if (userFields.has("ativo")) {
                u.setAtivo(true);
            } else {
                u.setAtivo(false);
            }
            dAO.getEntityManager().merge(u);

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void destroy(Integer id) throws Exception {
        try {
            Utilizador u = dAO.getEntityManager().find(Utilizador.class, id);
            u.setDeleted(true);

            dAO.getEntityManager().merge(u);

        } catch (Exception ex) {
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
                if (u.getPassword().equals(password) && !u.getDeleted()) {
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

    @Override
    public void removeUserRole(Utilizador u, int roleId) {
        try {

            Perfil p = (Perfil) dAO.getEntityManager().find(Perfil.class, roleId);

            u.getPerfilCollection().remove(p);
            p.getUtilizadorCollection().remove(u);

            dAO.getEntityManager().merge(u);
            dAO.getEntityManager().merge(p);

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public String getUserNameById(int userId) {
        try {

            Utilizador u = (Utilizador) dAO.getEntityManager().find(Utilizador.class, userId);
            if (u != null) {
                return u.getUsername();
            }
            return "Utilizador não encontrado";

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public int getTotalUsersPerRole(String roleName) {
        try {

            total = 0;
            List<Utilizador> allUsers = this.getUsersList();

            allUsers.forEach((k) -> {
                if (hasRole(k.getIdUtilizador(), roleName) == true) {
                    total++;
                }
            });

            return total;
        } catch (Exception ex) {
            return 0;
        }
    }

}
