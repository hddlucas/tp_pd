/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.RollbackFailureException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import models.Categoria;
import models.Componente;
import models.Proposta;
import models.Utilizador;
import org.json.JSONObject;

/**
 *
 * @author marcosequeira
 */
@Stateless
public class ComponenteFacade implements ComponenteFacadeLocal {

    @EJB
    private CategoriaFacadeLocal categoriaFacade;

    @EJB
    private DAOLocal dAO;

    @Override
    public List<Componente> getComponentsList() {
        Query q = dAO.getEntityManager().createNamedQuery("Componente.findAllNotDeleted");
        List<Componente> componentes = q.getResultList();

        return componentes;
    }
    
    @Override
    public Componente create(String fields) throws RollbackFailureException, Exception {
        try {
            JSONObject componenteFields = new JSONObject(fields);
            Categoria categoria = categoriaFacade.findCategoria(Integer.parseInt(componenteFields.getString("idCategoria")));
            
            Componente c = new Componente();
            c.setNome(componenteFields.getString("nome"));
            c.setObservacoes(componenteFields.getString("observacoes"));

            c.setIdCategoria(categoria);
            
            categoria.getComponenteCollection().add(c);
            
            dAO.getEntityManager().merge(categoria);
                        
            return c;
        } catch (Exception ex) {
            throw ex;
        }
    }
 
    
    @Override
    public void update(String fields, int componenteId) throws Exception {
        try {
            Componente c = (Componente) dAO.getEntityManager().find(Componente.class, componenteId);
            JSONObject componentFields = new JSONObject(fields);

            if(componentFields.has("nome")){
                c.setNome(componentFields.getString("nome"));
            }
            
            if(componentFields.has("observacoes")){
                c.setObservacoes(componentFields.getString("observacoes"));
            }
            
            if(componentFields.has("idCategoria")){
                Categoria categoria = categoriaFacade.findCategoria(Integer.parseInt(componentFields.getString("idCategoria")));

                c.setIdCategoria(categoria);
            
                categoria.getComponenteCollection().add(c);

                dAO.getEntityManager().merge(categoria);
            }
            dAO.getEntityManager().merge(c);
            
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    @Override
    public void destroy(Integer id) throws Exception  {
        try {
            Componente c = dAO.getEntityManager().find(Componente.class, id);
            c.setDeleted(true);
            dAO.getEntityManager().merge(c);
        } catch (Exception ex) { 
            throw ex;
        }
    }
}
