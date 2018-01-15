/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     12/01/2018 21:14:27                          */
/*==============================================================*/



drop index PERFIL_PK;

drop table PERFIL;

drop index UTILIZADOR_PK;

drop table UTILIZADOR;

drop index UTILIZADOR_PERFIL2_FK;

drop index UTILIZADOR_PERFIL_FK;

drop index UTILIZADOR_PERFIL_PK;

drop table UTILIZADOR_PERFIL;





/*==============================================================*/
/* ASSOCIATE USER TO ADMIN ROLE                             */
/*==============================================================*/


/*==============================================================*/
/* Table: PERFIL                                                */
/*==============================================================*/
create table PERFIL (
   ID_PERFIL            INT4                 not null,
   PERFIL               VARCHAR(1024)        not null,
   DESCRICAO            TEXT                 null,
   constraint PK_PERFIL primary key (ID_PERFIL)
);

/*==============================================================*/
/* Index: PERFIL_PK                                             */
/*==============================================================*/
create unique index PERFIL_PK on PERFIL (
ID_PERFIL
);

/*==============================================================*/
/* Table: UTILIZADOR                                            */
/*==============================================================*/
create table UTILIZADOR (
   ID_UTILIZADOR        INT4                 not null,
   NOME                 VARCHAR(1024)        not null,
   USERNAME             VARCHAR(1024)        not null,
   PASSWORD             VARCHAR(1024)        not null,
   BI                   VARCHAR(1024)        not null,
   NIF                  VARCHAR(1024)        not null,
   CIDADE               VARCHAR(1024)        null,
   PAIS                 VARCHAR(1024)        null,
   MORADA               TEXT                 null,
   CONTACTO             VARCHAR(1024)        null,
   CODIGO_POSTAL        VARCHAR(1024)        null,
   ULTIMO_LOGIN         DATE                 null,
   ATIVO                BOOL                 not null,
   constraint PK_UTILIZADOR primary key (ID_UTILIZADOR)
);

/*==============================================================*/
/* Index: UTILIZADOR_PK                                         */
/*==============================================================*/
create unique index UTILIZADOR_PK on UTILIZADOR (
ID_UTILIZADOR
);

/*==============================================================*/
/* Table: UTILIZADOR_PERFIL                                     */
/*==============================================================*/
create table UTILIZADOR_PERFIL (
   ID_UTILIZADOR        INT4                 not null,
   ID_PERFIL            INT4                 not null,
   constraint PK_UTILIZADOR_PERFIL primary key (ID_UTILIZADOR, ID_PERFIL)
);

/*==============================================================*/
/* Index: UTILIZADOR_PERFIL_PK                                  */
/*==============================================================*/
create unique index UTILIZADOR_PERFIL_PK on UTILIZADOR_PERFIL (
ID_UTILIZADOR,
ID_PERFIL
);

/*==============================================================*/
/* Index: UTILIZADOR_PERFIL_FK                                  */
/*==============================================================*/
create  index UTILIZADOR_PERFIL_FK on UTILIZADOR_PERFIL (
ID_UTILIZADOR
);

/*==============================================================*/
/* Index: UTILIZADOR_PERFIL2_FK                                 */
/*==============================================================*/
create  index UTILIZADOR_PERFIL2_FK on UTILIZADOR_PERFIL (
ID_PERFIL
);

alter table UTILIZADOR_PERFIL
   add constraint FK_UTILIZAD_UTILIZADO_UTILIZAD foreign key (ID_UTILIZADOR)
      references UTILIZADOR (ID_UTILIZADOR)
      on delete restrict on update restrict;

alter table UTILIZADOR_PERFIL
   add constraint FK_UTILIZAD_UTILIZADO_PERFIL foreign key (ID_PERFIL)
      references PERFIL (ID_PERFIL)
      on delete restrict on update restrict;


/*==============================================================*/
/* DEFAULT ADMIN                              			*/
/*==============================================================*/

INSERT INTO  utilizador (id_utilizador,username,password,nome,nif,bi,ativo)
VALUES (1,'admin','12345','Administrador','123456789','123456789',true);


/*==============================================================*/
/* DEFAULT ROLES                              			*/
/*==============================================================*/

INSERT INTO  perfil (id_perfil,perfil)
VALUES (1,'admin'),(2,'avaliador'),(3,'agente'),(4,'vendedor');


/*==============================================================*/
/* ASSOCIATE ADMIN ROLE		                                */
/*==============================================================*/

INSERT INTO  utilizador_perfil (id_perfil,id_utilizador)
VALUES (1,1);

