/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author hddlucas
 */
public class UtilizadorPerfil {
    
    boolean hasPerfil;

    public UtilizadorPerfil() {
    }

    public UtilizadorPerfil(boolean hasPerfil, String perfil, String codigo) {
        this.hasPerfil = hasPerfil;
        this.perfil = perfil;
        this.codigo = codigo;
    }
    String perfil;
    String codigo;

    
    
    public boolean isHasPerfil() {
        return hasPerfil;
    }

    public void setHasPerfil(boolean hasPerfil) {
        this.hasPerfil = hasPerfil;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
    
}
