# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table dbconfig (
  id                        bigint auto_increment not null,
  dbname                    varchar(255),
  dbdriver                  varchar(255),
  dburl                     varchar(255),
  dbuser                    varchar(255),
  dbpassword                varchar(255),
  constraint pk_dbconfig primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  login                     varchar(255),
  password                  varchar(255),
  email                     varchar(255),
  role                      varchar(255),
  enabled                   tinyint(1) default 0,
  constraint pk_user primary key (id))
;


create table user_dbconfig (
  user_id                        bigint not null,
  dbconfig_id                    bigint not null,
  constraint pk_user_dbconfig primary key (user_id, dbconfig_id))
;



alter table user_dbconfig add constraint fk_user_dbconfig_user_01 foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table user_dbconfig add constraint fk_user_dbconfig_dbconfig_02 foreign key (dbconfig_id) references dbconfig (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table dbconfig;

drop table user_dbconfig;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

