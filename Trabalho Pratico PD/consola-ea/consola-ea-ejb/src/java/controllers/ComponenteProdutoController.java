/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import models.AquisicaoProposta;
import models.Componente;
import models.ComponenteProduto;

/**
 *
 * @author hddlucas
 */
@Stateless
public class ComponenteProdutoController implements ComponenteProdutoControllerLocal {
    @EJB
    private DAOLocal dAO;

    @Override
    public ComponenteProduto findComponenteProduto(Integer id) {
        return dAO.getEntityManager().find(ComponenteProduto.class, id);
    }

    @Override
    public List<ComponenteProduto> getComponentePropostaByIdAquisicao(int idAquisicao) {
        List<ComponenteProduto> cp;

        Query q = dAO.getEntityManager().createNamedQuery("ComponenteProduto.findByIdAquisicao");
        cp = q
                .setParameter("idAquisicao", idAquisicao)
                .getResultList();
        
        return cp;
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
