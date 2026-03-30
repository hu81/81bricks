-- ----------------------------
-- 零件分类表
-- ----------------------------
drop table if exists ld_part_categories;
create table ld_part_categories (
  category_id           int(4)      not null    comment '分类ID',
  name              varchar(200)     not null                   comment '零件名称',
  primary key (category_id)
) engine=innodb comment = '零件分类表';

-- ----------------------------
-- 零件表
-- ----------------------------
drop table if exists ld_parts;
create table ld_parts (
  part_num          varchar(20)      not null    comment '零件编号',
  name              varchar(250)     not null                   comment '零件名称',
  part_cate_id      int(4)      not null   comment '分类ID',
  image              mediumtext                                 comment '图片数据'
) engine=innodb comment = '零件分类表';

-- ----------------------------
-- 颜色表
-- ----------------------------
drop table if exists ld_colors;
create table ld_colors (
  id      int(4)      not null    comment '颜色id',
  name              varchar(250)     not null                   comment '颜色名称',
  rgb              varchar(10)     not null                   comment '颜色RGB',
  is_trans      bool      not null   comment '是否透明'
) engine=innodb comment = '颜色表';

-- ----------------------------
-- elements
-- ----------------------------
drop table if exists ld_elements;
create table ld_elements (
  element_id      varchar(20)      not null    comment 'id',
  part_num        varchar(20)      not null                   comment '零件编号',
  color_id      int(4)      not null    comment '颜色id'
) engine=innodb comment = 'elements';