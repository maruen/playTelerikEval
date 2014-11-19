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
  company_id                bigint,
  constraint pk_computer primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  login                     varchar(255),
  email                     varchar(255),
  role                      varchar(255),
  enabled                   tinyint(1) default 0,
  constraint pk_user primary key (id))
;

alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_computer_company_1 on computer (company_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table company;

drop table computer;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

