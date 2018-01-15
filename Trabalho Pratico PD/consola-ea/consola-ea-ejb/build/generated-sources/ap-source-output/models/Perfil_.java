package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Utilizador;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-13T12:42:10")
@StaticMetamodel(Perfil.class)
public class Perfil_ { 

    public static volatile SingularAttribute<Perfil, Integer> idPerfil;
    public static volatile CollectionAttribute<Perfil, Utilizador> utilizadorCollection;
    public static volatile SingularAttribute<Perfil, String> perfil;
    public static volatile SingularAttribute<Perfil, String> descricao;

}