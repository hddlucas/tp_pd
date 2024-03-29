/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     22/02/2018 23:37:23                          */
/*==============================================================*/


drop index RELATIONSHIP_7_FK;

drop index ACQUISITION_PROPOSALS_PK;

drop table AQUISICAO_PROPOSTA;

drop table AVALIACAO_VENDEDOR;

drop index CATEGORIES_PK;

drop table CATEGORIA;

drop index RELATIONSHIP_10_FK;

drop index COMPONENTS_PK;

drop table COMPONENTE;

drop index RELATIONSHIP_9_FK;

drop index RELATIONSHIP_5_PK;

drop table COMPONENTE_PRODUTO;

drop table MENSAGEM;

drop index PROFILES_PK;

drop table PERFIL;

drop index RELATIONSHIP_11_FK;

drop index PROPOSALS_PK;

drop table PROPOSTA;

drop index USER_PK;

drop table UTILIZADOR;

drop index PROFILES_USER2_FK;

drop index PROFILES_USER_FK;

drop index PROFILES_USER_PK;

drop table UTILIZADOR_PERFIL;

/*==============================================================*/
/* Table: AQUISICAO_PROPOSTA                                    */
/*==============================================================*/
create table AQUISICAO_PROPOSTA (
   ID_AQUISICAO         SERIAL               not null,
   ID_UTILIZADOR        INT4                 not null,
   VALOR_MAX            FLOAT8               null,
   OBSERVACOES          VARCHAR(1024)        null,
   CREATED_AT           TIMESTAMP            null,
   DELETED              BOOL                 not null,
   constraint PK_AQUISICAO_PROPOSTA primary key (ID_AQUISICAO)
);

/*==============================================================*/
/* Index: ACQUISITION_PROPOSALS_PK                              */
/*==============================================================*/
create unique index ACQUISITION_PROPOSALS_PK on AQUISICAO_PROPOSTA (
ID_AQUISICAO
);

/*==============================================================*/
/* Index: RELATIONSHIP_7_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_7_FK on AQUISICAO_PROPOSTA (
ID_UTILIZADOR
);

/*==============================================================*/
/* Table: AVALIACAO_VENDEDOR                                    */
/*==============================================================*/
create table AVALIACAO_VENDEDOR (
   ID_AVALIACAO_VENDEDOR SERIAL               not null,
   ID_UTILIZADOR        INT4                 not null,
   ID_PROPOSTA          INT4                 not null,
   ID_AVALIADOR         INT4                 not null,
   AVALIACAO            INT4                 not null,
   constraint PK_AVALIACAO_VENDEDOR primary key (ID_AVALIACAO_VENDEDOR)
);

/*==============================================================*/
/* Table: CATEGORIA                                             */
/*==============================================================*/
create table CATEGORIA (
   ID_CATEGORIA         SERIAL               not null,
   NOME                 VARCHAR(1024)        null,
   DELETED              BOOL                 not null,
   constraint PK_CATEGORIA primary key (ID_CATEGORIA)
);

/*==============================================================*/
/* Index: CATEGORIES_PK                                         */
/*==============================================================*/
create unique index CATEGORIES_PK on CATEGORIA (
ID_CATEGORIA
);

/*==============================================================*/
/* Table: COMPONENTE                                            */
/*==============================================================*/
create table COMPONENTE (
   ID_COMPONENTE        SERIAL               not null,
   ID_CATEGORIA         INT4                 not null,
   NOME                 VARCHAR(1024)        null,
   OBSERVACOES          VARCHAR(1024)        null,
   DELETED              BOOL                 not null,
   constraint PK_COMPONENTE primary key (ID_COMPONENTE)
);

/*==============================================================*/
/* Index: COMPONENTS_PK                                         */
/*==============================================================*/
create unique index COMPONENTS_PK on COMPONENTE (
ID_COMPONENTE
);

/*==============================================================*/
/* Index: RELATIONSHIP_10_FK                                    */
/*==============================================================*/
create  index RELATIONSHIP_10_FK on COMPONENTE (
ID_CATEGORIA
);

/*==============================================================*/
/* Table: COMPONENTE_PRODUTO                                    */
/*==============================================================*/
create table COMPONENTE_PRODUTO (
   ID_COMPONENTE        INT4                 not null,
   ID_AQUISICAO         INT4                 not null,
   AVALIACAO            INT4                 null,
   VALOR                VARCHAR(255)         null,
   OPERADOR             VARCHAR(2)           null,
   constraint PK_COMPONENTE_PRODUTO primary key (ID_COMPONENTE, ID_AQUISICAO)
);

/*==============================================================*/
/* Index: RELATIONSHIP_5_PK                                     */
/*==============================================================*/
create unique index RELATIONSHIP_5_PK on COMPONENTE_PRODUTO (
ID_COMPONENTE,
ID_AQUISICAO
);

/*==============================================================*/
/* Index: RELATIONSHIP_9_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_9_FK on COMPONENTE_PRODUTO (
ID_COMPONENTE
);

/*==============================================================*/
/* Table: MENSAGEM                                              */
/*==============================================================*/
create table MENSAGEM (
   ID_MENSAGEM          SERIAL               not null,
   ID_DESTINATARIO      INT4                 not null,
   ID_REMETENTE         INT4                 not null,
   ASSUNTO              VARCHAR(255)         not null,
   MENSAGEM             TEXT                 not null,
   LIDA                 BOOL                 null,
   ELIMINADA_REMETENTE  BOOL                 null,
   ELIMINADA_DESTINATARIO BOOL                 null,
   CREATED_AT           TIMESTAMP            null,
   DELETED              BOOL                 not null,
   constraint PK_MENSAGEM primary key (ID_MENSAGEM)
);

/*==============================================================*/
/* Table: PERFIL                                                */
/*==============================================================*/
create table PERFIL (
   ID_PERFIL            SERIAL               not null,
   PERFIL               VARCHAR(1024)        null,
   DESCRICAO            VARCHAR(1024)        null,
   DELETED              BOOL                 not null,
   constraint PK_PERFIL primary key (ID_PERFIL)
);

/*==============================================================*/
/* Index: PROFILES_PK                                           */
/*==============================================================*/
create unique index PROFILES_PK on PERFIL (
ID_PERFIL
);

/*==============================================================*/
/* Table: PROPOSTA                                              */
/*==============================================================*/
create table PROPOSTA (
   ID_PROPOSTA          SERIAL               not null,
   ID_UTILIZADOR        INT4                 not null,
   ID_AQUISICAO         INT4                 null,
   DESCRICAO            TEXT                 null,
   VALOR_TOTAL          FLOAT8               null,
   GANHOU               BOOL                 null,
   CREATED_AT           TIMESTAMP            null,
   AVALIACAO            INT4                 null,
   OBSERVACOES          TEXT                 null,
   DELETED              BOOL                 not null,
   constraint PK_PROPOSTA primary key (ID_PROPOSTA)
);

/*==============================================================*/
/* Index: PROPOSALS_PK                                          */
/*==============================================================*/
create unique index PROPOSALS_PK on PROPOSTA (
ID_PROPOSTA
);

/*==============================================================*/
/* Index: RELATIONSHIP_11_FK                                    */
/*==============================================================*/
create  index RELATIONSHIP_11_FK on PROPOSTA (
ID_UTILIZADOR
);

/*==============================================================*/
/* Table: UTILIZADOR                                            */
/*==============================================================*/
create table UTILIZADOR (
   ID_UTILIZADOR        SERIAL               not null,
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
   ULTIMO_LOGIN         TIMESTAMP            null,
   ATIVO                BOOL                 not null,
   DELETED              BOOL                 not null,
   constraint PK_UTILIZADOR primary key (ID_UTILIZADOR)
);

/*==============================================================*/
/* Index: USER_PK                                               */
/*==============================================================*/
create unique index USER_PK on UTILIZADOR (
ID_UTILIZADOR
);

/*==============================================================*/
/* Table: UTILIZADOR_PERFIL                                     */
/*==============================================================*/
create table UTILIZADOR_PERFIL (
   ID_PERFIL            INT4                 not null,
   ID_UTILIZADOR        INT4                 not null,
   constraint PK_UTILIZADOR_PERFIL primary key (ID_PERFIL, ID_UTILIZADOR)
);

/*==============================================================*/
/* Index: PROFILES_USER_PK                                      */
/*==============================================================*/
create unique index PROFILES_USER_PK on UTILIZADOR_PERFIL (
ID_PERFIL,
ID_UTILIZADOR
);

/*==============================================================*/
/* Index: PROFILES_USER_FK                                      */
/*==============================================================*/
create  index PROFILES_USER_FK on UTILIZADOR_PERFIL (
ID_PERFIL
);

/*==============================================================*/
/* Index: PROFILES_USER2_FK                                     */
/*==============================================================*/
create  index PROFILES_USER2_FK on UTILIZADOR_PERFIL (
ID_UTILIZADOR
);

alter table AQUISICAO_PROPOSTA
   add constraint FK_AQUISICA_AQUISICAO_UTILIZAD foreign key (ID_UTILIZADOR)
      references UTILIZADOR (ID_UTILIZADOR)
      on delete restrict on update restrict;

alter table AVALIACAO_VENDEDOR
   add constraint FK_AVALIACA_REFERENCE_UTILIZAD foreign key (ID_UTILIZADOR)
      references UTILIZADOR (ID_UTILIZADOR)
      on delete restrict on update restrict;

alter table AVALIACAO_VENDEDOR
   add constraint FK_AVALIACA_REFERENCE_PROPOSTA foreign key (ID_PROPOSTA)
      references PROPOSTA (ID_PROPOSTA)
      on delete restrict on update restrict;

alter table COMPONENTE
   add constraint FK_COMPONEN_RELATIONS_CATEGORI foreign key (ID_CATEGORIA)
      references CATEGORIA (ID_CATEGORIA)
      on delete restrict on update restrict;

alter table COMPONENTE_PRODUTO
   add constraint FK_COMPONEN_COMPONENT_COMPONEN foreign key (ID_COMPONENTE)
      references COMPONENTE (ID_COMPONENTE)
      on delete restrict on update restrict;

alter table COMPONENTE_PRODUTO
   add constraint FK_COMPONEN_REFERENCE_AQUISICA foreign key (ID_AQUISICAO)
      references AQUISICAO_PROPOSTA (ID_AQUISICAO)
      on delete restrict on update restrict;

alter table MENSAGEM
   add constraint FK_MENSAGEM_REFERENCE_UTILIZAD foreign key (ID_DESTINATARIO)
      references UTILIZADOR (ID_UTILIZADOR)
      on delete restrict on update restrict;

alter table PROPOSTA
   add constraint FK_PROPOSTA_PROPOSTA__UTILIZAD foreign key (ID_UTILIZADOR)
      references UTILIZADOR (ID_UTILIZADOR)
      on delete restrict on update restrict;

alter table PROPOSTA
   add constraint FK_PROPOSTA_REFERENCE_AQUISICA foreign key (ID_AQUISICAO)
      references AQUISICAO_PROPOSTA (ID_AQUISICAO)
      on delete restrict on update restrict;

alter table UTILIZADOR_PERFIL
   add constraint FK_UTILIZAD_PROFILES__UTILIZAD foreign key (ID_UTILIZADOR)
      references UTILIZADOR (ID_UTILIZADOR)
      on delete restrict on update restrict;

alter table UTILIZADOR_PERFIL
   add constraint FK_UTILIZAD_UTILIZADO_PERFIL foreign key (ID_PERFIL)
      references PERFIL (ID_PERFIL)
      on delete restrict on update restrict;



/*==============================================================*/
/* DEFAULT ADMIN                              			*/
/*==============================================================*/

INSERT INTO  utilizador (username,password,nome,nif,bi,ativo,deleted)
VALUES 
('admin','12345','Administrador','23323322112','111111111',true,false),
('vendedor','12345','Vendedor','311112122','222222222',true,false),
('avaliador','12345','Avaliador','12345122236789','333333333',true,false),
('agente','12345','Agente','123333332332','444444444',true,false),
('hddlucas','12345','Hugo David Duarte Lucas','12312223221','555555555',true,false),
('marcosequeira','12345','Marco Sequeira','12323231221','666666666',true,false);


/*==============================================================*/
/* DEFAULT ROLES                              			*/
/*==============================================================*/

INSERT INTO  perfil (perfil,descricao,deleted)
VALUES ('admin','Administrador',false),('avaliador','Avaliador',false),('agente','Agente',false),('vendedor','Vendedor',false);



/*==============================================================*/
/* ASSOCIATE ADMIN ROLE		                                */
/*==============================================================*/

INSERT INTO  utilizador_perfil (id_perfil,id_utilizador)
VALUES (1,1),(4,2),(2,3),(3,4),(1,5),(1,6);


/*==============================================================*/
/* CATEGORIAS	                                */
/*==============================================================*/

INSERT INTO  categoria (nome,deleted)
VALUES 
('Medidas',false),
('Dados fabrico',false),
('Medidas Eléctricas',false),
('Montagem',false),
('Instalação',false),
('Estética',false);
    
INSERT INTO  componente (id_categoria,nome,observacoes,deleted)
VALUES 

(1,'Medida da rosca','',false),
(1,'Comprimento (mm)','',false),   
(1,'Largura [mm]','',false), 
(1,'Altura [mm]','',false), 
(1,'Medida da rosca','',false),
(1,'Comprimento [polegadas]','',false),   
(1,'Unidade de quantidade','',false), 
(1,'Diâmetro [mm]','',false),
(1,'Peso líquido [kg]','',false),
(1,'Espessura [mm]','',false),

(2,'Aprovação do fabricante','',false), 
(2,'Ano de fabrico a partir de','',false), 

(3,'Intensidade da corrente [A]','',false),
(3,'Corrente de ensaio a frio EN [A]','',false),
(3,'Potência nominal [W]','',false),
(3,'Tensão [V]','',false),

(4,'Lado de montagem','',false), 
(4,'Informação sobre montagem','',false), 


(5,'Tipo de instalação eléctrica de bordo','',false), 

(6,'Cor','',false);








  

