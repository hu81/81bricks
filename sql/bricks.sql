-- ----------------------------
-- Table structure for ldr
-- ----------------------------
drop table if exists brk_resource;
create table brk_resource (
  resource_id           bigint(20)      not null auto_increment    comment '资源ID',
  type              varchar(50)     not null                   comment '资源类型',
  origin_id         varchar(500)    not null                    comment '资源id',
  origin_url        varchar(500)    not null                   comment '资源链接',
  data              mediumtext                                 comment '图片数据',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (resource_id)
) engine=innodb auto_increment=100 comment = '资源图片表';


-- ----------------------------
-- Table structure for brk_face
-- ----------------------------
drop table if exists brk_face;
create table brk_face (
  face_id           bigint(20)      not null auto_increment    comment '表情ID',
  face_name         varchar(500)    not null                   comment '表情名称',
  comments          varchar(1024)                              comment '备注',
  origin_id         varchar(500)                               comment '表情id',
  origin_url        varchar(500)                               comment '表情链接',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (face_id)
) engine=innodb auto_increment=1 comment = '表情表';


drop table if exists brk_face_layer;
create table brk_face_layer (
    layer_id           bigint(20)      not null auto_increment    comment '图层ID',
    face_id            bigint(20)      not null                   comment '表情id',
    layer_type         varchar(64)                                comment '图层类型',
    data               mediumtext                                 comment '图片数据',
    x                  int                             comment 'x坐标',
    y                  int                             comment 'y坐标',
    width              int                             comment '图层宽度',
    height             int                             comment '图层高度',
  primary key (layer_id)
) engine=innodb auto_increment=1 comment = '表情图层表';