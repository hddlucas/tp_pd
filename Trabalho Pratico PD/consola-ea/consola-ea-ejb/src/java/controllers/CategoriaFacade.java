/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.RollbackFailureException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import models.Categoria;
import models.Utilizador;
import org.json.JSONObject;

/**
 *
 * @author marcosequeira
 */
@Stateless
public class CategoriaFacade implements CategoriaFacadeLocal {
@EJB
    private DAOLocal dAO;

    @Override
    public List<Categoria> getCategoriesList() {
        Query q = dAO.getEntityManager().createNamedQuery("Categoria.findAll");
        List<Categoria> categorias = q.getResultList();

        return categorias;
    }

    @Override
    public Categoria create(String fields) throws RollbackFailureException, Exception {
        try {

            JSONObject categoryFields = new JSONObject(fields);
            Categoria c = new Categoria();
            c.setNome(categoryFields.getString("nome"));

            dAO.getEntityManager().persist(c);

            return c;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void update(String fields, int categoryId) throws Exception {
        try {
            Categoria c = (Categoria) dAO.getEntityManager().find(Categoria.class, categoryId);
            JSONObject categoryFields = new JSONObject(fields);

            if(categoryFields.has("nome")){
                c.setNome(categoryFields.getString("nome"));
            }
            dAO.getEntityManager().merge(c);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void destroy(Integer id) throws Exception  {
        try {
            Categoria c = dAO.getEntityManager().find(Categoria.class, id);
            dAO.getEntityManager().createQuery("DELETE FROM Categoria c WHERE c.idCategoria = :id").setParameter("id", id).executeUpdate();

        } catch (Exception ex) { 
            throw ex;
        }
    }

    @Override
    public Categoria findCategoria(Integer id) {
        return dAO.getEntityManager().find(Categoria.class, id);
    }
    
    @Override
    public List<Categoria> findCategoriaByNome(String nome) {
        List<Categoria> categorias = null;
        Query q = dAO.getEntityManager().createNamedQuery("Categoria.findByNome");
        categorias = q
                .setParameter("nome", nome)
                .getResultList();
        return categorias;
    }
}