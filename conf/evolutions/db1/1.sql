# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table company (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_company primary key (id))
;

create table computer (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  introduced                varchar(255),
  discontinued              varchar(255),
  constraint pk_computer primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table company;

drop table computer;

SET FOREIGN_KEY_CHECKS=1;

