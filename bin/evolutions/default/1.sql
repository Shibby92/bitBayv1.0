# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                        integer not null,
  name                      varchar(255),
  constraint pk_category primary key (id))
;

create table product (
  id                        integer not null,
  name                      varchar(255),
  category_id               integer,
  owner_id                  integer,
  created                   timestamp,
  quantity                  integer,
  price                     double,
  description               varchar(255),
  image_url                 varchar(255),
  constraint pk_product primary key (id))
;

create table user (
  id                        integer not null,
  email                     varchar(255),
  password                  varchar(255),
  username                  varchar(255),
  birth_date                timestamp,
  user_address              varchar(255),
  shipping_address          varchar(255),
  city                      varchar(255),
  gender                    varchar(255),
  admin                     boolean,
  verification              boolean,
  confirmation              varchar(255),
  has_additional_info       boolean,
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id))
;

create sequence category_seq;

create sequence product_seq;

create sequence user_seq;

alter table product add constraint fk_product_owner_1 foreign key (owner_id) references user (id) on delete restrict on update restrict;
create index ix_product_owner_1 on product (owner_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists category;

drop table if exists product;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists category_seq;

drop sequence if exists product_seq;

drop sequence if exists user_seq;

