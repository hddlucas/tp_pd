/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.List;
import javax.ejb.Local;
import models.AvaliacaoVendedor;
import models.Perfil;
import models.Proposta;
import models.Utilizador;

/**
 *
 * @author hddlucas
 */
@Local
public interface UtilizadorFacadeLocal {
    
    List<Utilizador> getUsersList();
    List<Utilizador> getListOfNonActiveUsers();
    Utilizador create(String fields)throws Exception;
    void update(String fields, int userId)throws Exception;
    void destroy(Integer id)throws Exception;
    Utilizador findUtilizador(Integer id);
    List<Utilizador> findUtilizadorByUsername(String username);
    List<Utilizador> findUtilizadorByBi(String bi);
    List<Utilizador> findUtilizadorByNif(String nif);
    boolean login(String username, String password);
    boolean hasRole(int userId, String role);
    boolean isActive(int userId);
    List<Perfil> getUserRolesByUserId(int userId);
    void activeUser(int userId);
    void addUserRole(Utilizador u, int roleId);
    void removeUserRole(Utilizador u, int roleId);
    public String getUserNameById(int userId);
    public int getTotalUsersPerRole(String  roleName);
    int getTotalVendasVendedor(Utilizador u);
    List <Proposta> getVendas(Utilizador u);
    List <AvaliacaoVendedor> getAvaliacaoList(Utilizador u);
    int  getPontuacaoMedia(Utilizador u);
    int getPontuacaoMediaProdutosVendidos(Utilizador u);
}
