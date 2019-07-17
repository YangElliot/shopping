/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/6/11 9:25:32                            */
/*==============================================================*/


drop table if exists t_product;

drop table if exists t_product_desc;

drop table if exists t_product_type;

/*==============================================================*/
/* Table: t_product                                             */
/*==============================================================*/
create table t_product
(
   id                   bigint not null auto_increment,
   name                 varchar(12) not null,
   price                bigint not null,
   sale_price           bigint not null,
   images               varchar(255) not null,
   sale_point           varchar(24) not null,
   type_id              bigint not null,
   type_name            varchar(12) not null,
   flag                 tinyint(1) not null comment '1:有效
            0:失效',
   primary key (id)
);

/*==============================================================*/
/* Table: t_product_desc                                        */
/*==============================================================*/
create table t_product_desc
(
   id                   bigint not null,
   product_id           bigint not null,
   product_desc         text not null,
   primary key (id)
);

/*==============================================================*/
/* Table: t_product_type                                        */
/*==============================================================*/
create table t_product_type
(
   id                   bigint not null auto_increment,
   pid                  bigint not null,
   name                 varchar(12) not null,
   flag                 tinyint(1) not null comment '1：有效
            0：失效',
   primary key (id)
);

