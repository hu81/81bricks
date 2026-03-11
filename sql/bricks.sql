-- ----------------------------
-- Table structure for ldr
-- ----------------------------
drop table if exists brk_resource;
create table brk_resource (
  resource_id           bigint(20)      not null auto_increment    comment '资源ID',
  type              varchar(50)     not null                   comment '资源类型',
  origin_id         varchar(500)    not null                    comment '资源id',
  origin_url        varchar(500)    not null                   comment '资源链接',
  data              text                                       comment '图片数据',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (resource_id)
) engine=innodb auto_increment=100 comment = '资源图片表';


-- ----------------------------
-- Table structure for ldr
-- ----------------------------
drop table if exists brk_face;
create table brk_face (
  face_id           bigint(20)      not null auto_increment    comment '表情ID',
  origin_id         varchar(500)    not null                    comment '表情id',
  origin_url        varchar(500)    not null                   comment '表情链接',
  eye_origin_id     varchar(500)                               comment '眼睛id',
  month_origin_id     varchar(500)                             comment '嘴巴id',
  eyebrow_origin_id     varchar(500)                           comment '眉毛id',
  glass_origin_id     varchar(500)                             comment '眼镜id',
  sticker_origin_id     varchar(500)                           comment '贴纸id',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (face_id)
) engine=innodb auto_increment=100 comment = '表情表';