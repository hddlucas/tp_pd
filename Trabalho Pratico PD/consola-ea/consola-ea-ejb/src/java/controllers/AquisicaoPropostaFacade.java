/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.RollbackFailureException;
import static java.lang.Integer.parseInt;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import models.AquisicaoProposta;
import models.Categoria;
import models.Produto;
import models.Utilizador;
import org.json.JSONObject;

/**
 *
 * @author hddlucas
 */
@Stateless
public class AquisicaoPropostaFacade implements AquisicaoPropostaFacadeLocal {

    @EJB
    private DAOLocal dAO;

    @Override
    public List<AquisicaoProposta> getAcquisitionProposals() {
        Query q = dAO.getEntityManager().createNamedQuery("AquisicaoProposta.findAll");
        List<AquisicaoProposta> proposals = q.getResultList();

        return proposals;
    }

    @Override
    public AquisicaoProposta findAquisicaoProposta(Integer id) {
        return dAO.getEntityManager().find(AquisicaoProposta.class, id);
    }

    @Override
    public int totalPropostas(Date date) {
        List<AquisicaoProposta> proposals = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Query q = dAO.getEntityManager().createNativeQuery("SELECT * FROM aquisicao_proposta p where date(p.created_at) = {d ':createdAt'}");
        proposals = q
                .setParameter("createdAt", 2018 - 02 - 02)
                .getResultList();

        return proposals.size();
    }

    @Override
    public int totalPropostas() {
        List<AquisicaoProposta> proposals = null;
        proposals = this.getAcquisitionProposals();

        return proposals.size();
    }

}
