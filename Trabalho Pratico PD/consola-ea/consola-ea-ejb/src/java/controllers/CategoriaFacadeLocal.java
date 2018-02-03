/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.List;
import javax.ejb.Local;
import models.Categoria;
import models.Perfil;
import models.Utilizador;

/**
 *
 * @author marcosequeira
 */
@Local
public interface CategoriaFacadeLocal {
    List<Categoria> getCategoriesList();
    Categoria create(String fields)throws Exception;
    void update(String fields, int categoryId)throws Exception;
    void destroy(Integer id)throws Exception;
    Categoria findCategoria(Integer id);
    List<Categoria> findCategoriaByNome(String nome);
}
