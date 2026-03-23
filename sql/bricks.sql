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


-- ----------------------------
-- Table structure for brk_set (套装表)
-- ----------------------------
drop table if exists brk_set;
create table brk_set (
  set_id             bigint(20)      not null auto_increment    comment '套装ID',
  set_name            varchar(500)    not null                   comment '套装名称',
  uuid               varchar(256)    default null               comment '套装UUID',
  asset_type         varchar(50)     not null                   comment '资产类型(套装)',
  origin_id          varchar(500)                               comment '原始ID',
  origin_url         varchar(500)                               comment '原始链接',
  comments           varchar(1024)                              comment '备注',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  update_by          varchar(64)     default ''                 comment '更新者',
  update_time        datetime                                   comment '更新时间',
  primary key (set_id)
) engine=innodb auto_increment=1 comment = '套装表';


-- ----------------------------
-- Table structure for brk_set_category (套装分类表)
-- ----------------------------
drop table if exists brk_set_category;
create table brk_set_category (
  category_id        bigint(20)      not null auto_increment    comment '分类ID',
  set_id              bigint(20)      not null                   comment '套装ID',
  category_name       varchar(50)     not null                   comment '分类名称(tops/bottoms/shoes等)',
  uuid               varchar(256)    not null                   comment '分类UUID',
  version            varchar(20)     default 'v0'               comment '版本',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  update_by          varchar(64)     default ''                 comment '更新者',
  update_time        datetime                                   comment '更新时间',
  primary key (category_id),
  key idx_set_id (set_id),
  unique key uk_set_category (set_id, category_name)
) engine=innodb auto_increment=1 comment = '套装分类表';


-- ----------------------------
-- Table structure for brk_set_mesh (套装网格/纹理表)
-- ----------------------------
drop table if exists brk_set_mesh;
create table brk_set_mesh (
  mesh_id            bigint(20)      not null auto_increment    comment '网格ID',
  category_id        bigint(20)      not null                   comment '分类ID',
  mesh_index          int             not null                   comment '网格索引',
  ref_id             varchar(500)                               comment '纹理引用ID',
  part_number        varchar(50)                                comment '零件号',
  base               varchar(50)                                comment '基础零件号',
  mesh_type          varchar(50)     default 'texface'           comment '网格类型',
  texture_left       mediumtext                                  comment '左侧纹理',
  texture_right      mediumtext                                  comment '右侧纹理',
  texture_top        mediumtext                                  comment '顶部纹理',
  texture_back       mediumtext                                  comment '背面纹理',
  texture_bottom     mediumtext                                  comment '底部纹理',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  primary key (mesh_id),
  key idx_category_id (category_id),
  key idx_ref_id (ref_id)
) engine=innodb auto_increment=1 comment = '套装网格表';


-- ----------------------------
-- Table structure for brk_bricks (积木模型表)
-- ----------------------------
drop table if exists brk_bricks;
create table brk_bricks (
  bricks_id          bigint(20)      not null auto_increment    comment '积木模型ID',
  bricks_name        varchar(500)    not null                   comment '积木模型名称',
  uuid               varchar(256)     not null                   comment '模型UUID',
  asset_type         varchar(50)     not null                   comment '资产类型',
  category           varchar(50)     not null                   comment '分类(hair/body/shoes等)',
  diy_group          varchar(50)     default null               comment 'DIY分组',
  root_group         int             default null               comment '根分组ID',
  default_color      int             default null               comment '默认颜色ID',
  origin_id          varchar(500)                               comment '原始ID',
  origin_url         varchar(500)                               comment '原始链接',
  comments           varchar(1024)                              comment '备注',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  update_by          varchar(64)     default ''                 comment '更新者',
  update_time        datetime                                   comment '更新时间',
  primary key (bricks_id),
  unique key uk_uuid (uuid),
  key idx_origin_id (origin_id),
  key idx_category (category)
) engine=innodb auto_increment=1 comment = '积木模型表';


-- ----------------------------
-- Table structure for brk_bricks_brick (积木模型积木表)
-- ----------------------------
drop table if exists brk_bricks_brick;
create table brk_bricks_brick (
  brick_id           bigint(20)      not null auto_increment    comment '积木ID',
  bricks_id          bigint(20)      not null                   comment '积木模型ID',
  brick_index        int             not null                   comment '积木索引',
  part_number        varchar(50)     not null                   comment '积木件号',
  color_id           varchar(50)     not null                   comment '颜色ID',
  x                  decimal(10,4)   not null                   comment 'X坐标',
  y                  decimal(10,4)   not null                   comment 'Y坐标',
  z                  decimal(10,4)   not null                   comment 'Z坐标',
  m11                decimal(10,6)  default 1                  comment '变换矩阵M11',
  m12                decimal(10,6)  default 0                  comment '变换矩阵M12',
  m13                decimal(10,6)  default 0                  comment '变换矩阵M13',
  m14                decimal(10,6)  default 0                  comment '变换矩阵M14',
  m21                decimal(10,6)  default 0                  comment '变换矩阵M21',
  m22                decimal(10,6)  default 1                  comment '变换矩阵M22',
  m23                decimal(10,6)  default 0                  comment '变换矩阵M23',
  m24                decimal(10,6)  default 0                  comment '变换矩阵M24',
  m31                decimal(10,6)  default 0                  comment '变换矩阵M31',
  m32                decimal(10,6)  default 0                  comment '变换矩阵M32',
  m33                decimal(10,6)  default 1                  comment '变换矩阵M33',
  m34                decimal(10,6)  default 0                  comment '变换矩阵M34',
  primary key (brick_id),
  key idx_bricks_id (bricks_id)
) engine=innodb auto_increment=1 comment = '积木模型积木表';


-- ----------------------------
-- Table structure for brk_bricks_connpoint (积木模型连接点表)
-- ----------------------------
drop table if exists brk_bricks_connpoint;
create table brk_bricks_connpoint (
  conn_id            bigint(20)      not null auto_increment    comment '连接点ID',
  bricks_id          bigint(20)      not null                   comment '积木模型ID',
  conn_index         int             not null                   comment '连接点索引',
  conn_type          varchar(50)     not null                   comment '连接类型',
  stud_type          varchar(50)     not null                   comment 'Stud类型',
  x                  decimal(10,4)   not null                   comment 'X坐标',
  y                  decimal(10,4)   not null                   comment 'Y坐标',
  z                  decimal(10,4)   not null                   comment 'Z坐标',
  nx                 decimal(10,6)  default 0                  comment '法向量X',
  ny                 decimal(10,6)  default 1                  comment '法向量Y',
  nz                 decimal(10,6)  default 0                  comment '法向量Z',
  primary key (conn_id),
  key idx_bricks_id (bricks_id)
) engine=innodb auto_increment=1 comment = '积木模型连接点表';


-- ----------------------------
-- Table structure for brk_bricks_mesh (积木模型网格表)
-- ----------------------------
drop table if exists brk_bricks_mesh;
create table brk_bricks_mesh (
  mesh_id             bigint(20)      not null auto_increment    comment '网格ID',
  bricks_id           bigint(20)      not null                   comment '积木模型ID',
  mesh_index          int             not null                   comment '网格索引',
  ref_id              varchar(500)                               comment '纹理引用ID',
  part_number         varchar(50)                                comment '零件号',
  base                varchar(50)                                comment '基础零件号',
  mesh_type           varchar(50)     default 'texface'          comment '网格类型',
  texture_left        mediumtext                                  comment '左侧纹理',
  texture_right       mediumtext                                  comment '右侧纹理',
  texture_top         mediumtext                                  comment '顶部纹理',
  texture_back        mediumtext                                  comment '背面纹理',
  texture_bottom      mediumtext                                  comment '底部纹理',
  primary key (mesh_id),
  key idx_bricks_id (bricks_id)
) engine=innodb auto_increment=1 comment = '积木模型网格表';


-- ----------------------------
-- Table structure for brk_bricks_group (积木模型分组表)
-- ----------------------------
drop table if exists brk_bricks_group;
create table brk_bricks_group (
  group_id            bigint(20)      not null auto_increment    comment '分组ID',
  bricks_id           bigint(20)      not null                   comment '积木模型ID',
  group_index         int             not null                   comment '分组索引',
  group_name          varchar(50)     not null                   comment '分组名称',
  ref_id              varchar(500)                               comment '引用ID',
  x                   decimal(10,4)   not null                   comment 'X坐标',
  y                   decimal(10,4)   not null                   comment 'Y坐标',
  z                   decimal(10,4)   not null                   comment 'Z坐标',
  m11                 decimal(10,6)  default 1                  comment '变换矩阵M11',
  m12                 decimal(10,6)  default 0                  comment '变换矩阵M12',
  m13                 decimal(10,6)  default 0                  comment '变换矩阵M13',
  m14                 decimal(10,6)  default 0                  comment '变换矩阵M14',
  m21                 decimal(10,6)  default 0                  comment '变换矩阵M21',
  m22                 decimal(10,6)  default 1                  comment '变换矩阵M22',
  m23                 decimal(10,6)  default 0                  comment '变换矩阵M23',
  m24                 decimal(10,6)  default 0                  comment '变换矩阵M24',
  m31                 decimal(10,6)  default 0                  comment '变换矩阵M31',
  m32                 decimal(10,6)  default 0                  comment '变换矩阵M32',
  m33                 decimal(10,6)  default 1                  comment '变换矩阵M33',
  m34                 decimal(10,6)  default 0                  comment '变换矩阵M34',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  primary key (group_id),
  key idx_bricks_id (bricks_id)
) engine=innodb auto_increment=1 comment = '积木模型分组表';