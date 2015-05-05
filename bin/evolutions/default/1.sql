# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table blog (
  id                        integer not null,
  title                     varchar(255),
  content                   TEXT,
  blog_image_path           varchar(255),
  date                      varchar(255),
  user_id                   integer,
  posted_by                 varchar(255),
  constraint pk_blog primary key (id))
;

create table cart (
  id                        integer not null,
  user_id                   integer,
  user_mail                 varchar(255),
  checkout                  double,
  size                      integer,
  shipping_address          varchar(255),
  constraint pk_cart primary key (id))
;

create table category (
  id                        integer not null,
  name                      varchar(255),
  constraint pk_category primary key (id))
;

create table comment (
  id                        integer not null,
  comment                   varchar(255),
  owner_id                  integer,
  product_id                integer,
  constraint pk_comment primary key (id))
;

create table faq (
  id                        integer not null,
  question                  varchar(255),
  answer                    TEXT,
  constraint pk_faq primary key (id))
;

create table image (
  id                        integer not null,
  image                     varchar(255),
  product_id                integer,
  constraint pk_image primary key (id))
;

create table message (
  id                        integer not null,
  content                   varchar(255),
  sender_id                 integer,
  receiver_id               integer,
  product_id                integer,
  subject                   varchar(255),
  constraint pk_message primary key (id))
;

create table notification (
  id                        integer not null,
  seller_id                 integer,
  order_id                  integer,
  is_unchecked              boolean,
  constraint pk_notification primary key (id))
;

create table orders (
  id                        integer not null,
  buyer_id                  integer,
  price                     double,
  token                     varchar(255),
  shipping_address          varchar(255),
  order_date                varchar(255),
  constraint pk_orders primary key (id))
;

create table product (
  id                        integer not null,
  name                      varchar(255),
  category_id               integer,
  cart_id                   integer,
  owner_id                  integer,
  quantity                  integer,
  price                     double,
  description               varchar(255),
  deleted                   boolean,
  sold                      boolean,
  ordered_quantity          integer,
  amount                    double,
  constraint pk_product primary key (id))
;

create table product_quantity (
  id                        integer not null,
  product_id                integer,
  quantity                  integer,
  order_id                  integer,
  constraint pk_product_quantity primary key (id))
;

create table report (
  id                        integer not null,
  reported_product_id       integer,
  reporter_id               integer,
  message                   varchar(255),
  constraint pk_report primary key (id))
;

create table tag (
  id                        integer not null,
  product_id                integer,
  tag                       varchar(255),
  constraint pk_tag primary key (id))
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
  blogger                   boolean,
  verification              boolean,
  confirmation              varchar(255),
  has_additional_info       boolean,
  rating                    double,
  number_of_ratings         integer,
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id))
;


create table OrderDetails (
  orderId                        integer not null,
  productId                      integer not null,
  constraint pk_OrderDetails primary key (orderId, productId))
;
create sequence blog_seq;

create sequence cart_seq;

create sequence category_seq;

create sequence comment_seq;

create sequence faq_seq;

create sequence image_seq;

create sequence message_seq;

create sequence notification_seq;

create sequence orders_seq;

create sequence product_seq;

create sequence product_quantity_seq;

create sequence report_seq;

create sequence tag_seq;

create sequence user_seq;

alter table comment add constraint fk_comment_owner_1 foreign key (owner_id) references user (id) on delete restrict on update restrict;
create index ix_comment_owner_1 on comment (owner_id);
alter table comment add constraint fk_comment_product_2 foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_comment_product_2 on comment (product_id);
alter table image add constraint fk_image_product_3 foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_image_product_3 on image (product_id);
alter table message add constraint fk_message_sender_4 foreign key (sender_id) references user (id) on delete restrict on update restrict;
create index ix_message_sender_4 on message (sender_id);
alter table message add constraint fk_message_receiver_5 foreign key (receiver_id) references user (id) on delete restrict on update restrict;
create index ix_message_receiver_5 on message (receiver_id);
alter table message add constraint fk_message_product_6 foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_message_product_6 on message (product_id);
alter table notification add constraint fk_notification_seller_7 foreign key (seller_id) references user (id) on delete restrict on update restrict;
create index ix_notification_seller_7 on notification (seller_id);
alter table orders add constraint fk_orders_buyer_8 foreign key (buyer_id) references user (id) on delete restrict on update restrict;
create index ix_orders_buyer_8 on orders (buyer_id);
alter table product add constraint fk_product_cart_9 foreign key (cart_id) references cart (id) on delete restrict on update restrict;
create index ix_product_cart_9 on product (cart_id);
alter table product add constraint fk_product_owner_10 foreign key (owner_id) references user (id) on delete restrict on update restrict;
create index ix_product_owner_10 on product (owner_id);
alter table product_quantity add constraint fk_product_quantity_order_11 foreign key (order_id) references orders (id) on delete restrict on update restrict;
create index ix_product_quantity_order_11 on product_quantity (order_id);
alter table report add constraint fk_report_reportedProduct_12 foreign key (reported_product_id) references product (id) on delete restrict on update restrict;
create index ix_report_reportedProduct_12 on report (reported_product_id);
alter table report add constraint fk_report_reporter_13 foreign key (reporter_id) references user (id) on delete restrict on update restrict;
create index ix_report_reporter_13 on report (reporter_id);
alter table tag add constraint fk_tag_product_14 foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_tag_product_14 on tag (product_id);



alter table OrderDetails add constraint fk_OrderDetails_orders_01 foreign key (orderId) references orders (id) on delete restrict on update restrict;

alter table OrderDetails add constraint fk_OrderDetails_product_02 foreign key (productId) references product (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists blog;

drop table if exists cart;

drop table if exists category;

drop table if exists comment;

drop table if exists faq;

drop table if exists image;

drop table if exists message;

drop table if exists notification;

drop table if exists orders;

drop table if exists OrderDetails;

drop table if exists product;

drop table if exists product_quantity;

drop table if exists report;

drop table if exists tag;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists blog_seq;

drop sequence if exists cart_seq;

drop sequence if exists category_seq;

drop sequence if exists comment_seq;

drop sequence if exists faq_seq;

drop sequence if exists image_seq;

drop sequence if exists message_seq;

drop sequence if exists notification_seq;

drop sequence if exists orders_seq;

drop sequence if exists product_seq;

drop sequence if exists product_quantity_seq;

drop sequence if exists report_seq;

drop sequence if exists tag_seq;

drop sequence if exists user_seq;

