package Classes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hddlucas
 */
public class Item {
    private String label;
    private String value;
    private int componente;
    private String componenteString;
    private int operador;
    private String operadorString;
    private int avaliacao;

    public int getOperador() {
        return operador;
    }

    public void setOperador(int operador) {
        this.operador = operador;
    }
    private String valor;

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getComponente() {
        return componente;
    }

    public void setComponente(int componente) {
        this.componente = componente;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOperadorString() {
        return operadorString;
    }

    public void setOperadorString(String operadorString) {
        this.operadorString = operadorString;
    }

    public String getComponenteString() {
        return componenteString;
    }

    public void setComponenteString(String componenteString) {
        this.componenteString = componenteString;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }
}
