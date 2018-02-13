/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.ejb.Local;
import models.ComponenteProduto;

/**
 *
 * @author hddlucas
 */
@Local
public interface ComponenteProdutoControllerLocal {
    ComponenteProduto findComponenteProduto(Integer id);

}
