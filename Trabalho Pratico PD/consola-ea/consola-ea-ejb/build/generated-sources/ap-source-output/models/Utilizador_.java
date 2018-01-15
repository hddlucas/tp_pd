package models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Perfil;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-13T12:42:10")
@StaticMetamodel(Utilizador.class)
public class Utilizador_ { 

    public static volatile SingularAttribute<Utilizador, String> cidade;
    public static volatile SingularAttribute<Utilizador, String> contacto;
    public static volatile SingularAttribute<Utilizador, Boolean> ativo;
    public static volatile SingularAttribute<Utilizador, String> codigoPostal;
    public static volatile SingularAttribute<Utilizador, String> bi;
    public static volatile SingularAttribute<Utilizador, Date> ultimoLogin;
    public static volatile SingularAttribute<Utilizador, String> nome;
    public static volatile SingularAttribute<Utilizador, String> nif;
    public static volatile SingularAttribute<Utilizador, String> pais;
    public static volatile CollectionAttribute<Utilizador, Perfil> perfilCollection;
    public static volatile SingularAttribute<Utilizador, Integer> idUtilizador;
    public static volatile SingularAttribute<Utilizador, String> password;
    public static volatile SingularAttribute<Utilizador, String> username;
    public static volatile SingularAttribute<Utilizador, String> morada;

}