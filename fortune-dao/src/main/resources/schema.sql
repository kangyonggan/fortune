DROP DATABASE IF EXISTS fortune_dev;

CREATE DATABASE fortune_dev
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_general_ci;

USE fortune_dev;

-- ----------------------------
--  Table structure for user
-- ----------------------------
DROP TABLE
IF EXISTS user;

CREATE TABLE user
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  username     VARCHAR(20)                           NOT NULL
  COMMENT '用户名',
  email        VARCHAR(64)                           NOT NULL
  COMMENT '邮箱',
  password     VARCHAR(64)                           NOT NULL
  COMMENT '密码',
  salt         VARCHAR(64)                           NOT NULL
  COMMENT '密码盐',
  fullname     VARCHAR(32)                           NOT NULL
  COMMENT '姓名',
  is_deleted   TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '用户表';
CREATE UNIQUE INDEX id_UNIQUE
  ON user (id);
CREATE INDEX created_time_ix
  ON user (created_time);
CREATE UNIQUE INDEX username_UNIQUE
  ON user (username);
CREATE UNIQUE INDEX email_UNIQUE
  ON user (email);

-- ----------------------------
--  Table structure for role
-- ----------------------------
DROP TABLE
IF EXISTS role;

CREATE TABLE role
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  code         VARCHAR(32)                           NOT NULL
  COMMENT '角色代码',
  name         VARCHAR(32)                           NOT NULL
  COMMENT '角色名称',
  is_deleted   TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '角色表';
CREATE UNIQUE INDEX id_UNIQUE
  ON role (id);
CREATE INDEX created_time_ix
  ON role (created_time);
CREATE UNIQUE INDEX code_UNIQUE
  ON role (code);

-- ----------------------------
--  Table structure for menu
-- ----------------------------
DROP TABLE
IF EXISTS menu;

CREATE TABLE menu
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  code         VARCHAR(32)                           NOT NULL
  COMMENT '菜单代码',
  name         VARCHAR(32)                           NOT NULL
  COMMENT '菜单名称',
  pcode        VARCHAR(32)                           NOT NULL                DEFAULT ''
  COMMENT '父菜单代码',
  url          VARCHAR(128)                          NOT NULL                DEFAULT ''
  COMMENT '菜单地址',
  sort         INT(11)                               NOT NULL                DEFAULT 0
  COMMENT '菜单排序(从0开始)',
  icon         VARCHAR(128)                          NOT NULL                DEFAULT ''
  COMMENT '菜单图标的样式',
  is_deleted   TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '菜单表';
CREATE UNIQUE INDEX id_UNIQUE
  ON menu (id);
CREATE INDEX created_time_ix
  ON menu (created_time);
CREATE INDEX sort_ix
  ON menu (sort);
CREATE UNIQUE INDEX code_UNIQUE
  ON menu (code);

-- ----------------------------
--  Table structure for user_role
-- ----------------------------
DROP TABLE
IF EXISTS user_role;

CREATE TABLE user_role
(
  username  VARCHAR(20) NOT NULL
  COMMENT '用户名',
  role_code VARCHAR(32) NOT NULL
  COMMENT '角色代码',
  PRIMARY KEY (username, role_code)
)
  COMMENT '用户角色表';

-- ----------------------------
--  Table structure for role_menu
-- ----------------------------
DROP TABLE
IF EXISTS role_menu;

CREATE TABLE role_menu
(
  role_code VARCHAR(32) NOT NULL
  COMMENT '角色代码',
  menu_code VARCHAR(32) NOT NULL
  COMMENT '菜单代码',
  PRIMARY KEY (role_code, menu_code)
)
  COMMENT '角色菜单表';

-- ----------------------------
--  Table structure for dictionary
-- ----------------------------
DROP TABLE
IF EXISTS dictionary;

CREATE TABLE dictionary
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  code         VARCHAR(32)                           NOT NULL
  COMMENT '代码',
  value        VARCHAR(128)                          NOT NULL
  COMMENT '值',
  type         VARCHAR(16)                           NOT NULL
  COMMENT '类型',
  sort         INT(11)                               NOT NULL                DEFAULT 0
  COMMENT '排序(从0开始)',
  is_deleted   TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '字典表';
CREATE UNIQUE INDEX id_UNIQUE
  ON dictionary (id);
CREATE UNIQUE INDEX code_UNIQUE
  ON dictionary (code);
CREATE INDEX created_time_ix
  ON dictionary (created_time);
CREATE INDEX type_ix
  ON dictionary (type);
CREATE INDEX sort_ix
  ON dictionary (sort);

# fpay相关表

-- ----------------------------
--  Table structure for merchant
-- ----------------------------
DROP TABLE
IF EXISTS merchant;

CREATE TABLE merchant
(
  id            BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  merch_co      VARCHAR(15)                           NOT NULL
  COMMENT '商户号',
  merch_nm      VARCHAR(15)                           NOT NULL
  COMMENT '商户名称',
  merch_acct_no VARCHAR(20)                           NOT NULL
  COMMENT '商户银行卡号',
  merch_acct_nm VARCHAR(20)                           NOT NULL
  COMMENT '商户银行卡户名',
  merch_mobile  VARCHAR(11)                           NOT NULL
  COMMENT '商户手机号',
  merch_id_no   VARCHAR(18)                           NOT NULL
  COMMENT '商户证件号',
  merch_id_tp   VARCHAR(2)                            NOT NULL
  COMMENT '商户证件类型',
  charset       VARCHAR(8)                            NOT NULL                    DEFAULT 'UTF-8'
  COMMENT '编码',
  balance       DECIMAL(16, 2)                        NOT NULL                    DEFAULT '0'
  COMMENT '余额',
  ftp_host      VARCHAR(20)                           NOT NULL                    DEFAULT ''
  COMMENT 'ftp主机名',
  ftp_user      VARCHAR(64)                           NOT NULL                    DEFAULT ''
  COMMENT 'ftp用户名',
  ftp_pwd       VARCHAR(128)                          NOT NULL                    DEFAULT ''
  COMMENT 'ftp密码',
  ftp_dir       VARCHAR(128)                          NOT NULL                    DEFAULT ''
  COMMENT 'ftp目录',
  is_debug      TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '是否是测试环境:{0:生产环境, 1:测试环境}',
  is_deleted    TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time  TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time  TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '商户信息表';
CREATE UNIQUE INDEX id_UNIQUE
  ON merchant (id);
CREATE INDEX created_time_ix
  ON merchant (created_time);
CREATE UNIQUE INDEX merch_co_UNIQUE
  ON merchant (merch_co);

-- ----------------------------
--  Table structure for trans
-- ----------------------------
DROP TABLE
IF EXISTS trans;

CREATE TABLE trans
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  merch_co     VARCHAR(15)                           NOT NULL
  COMMENT '商户号',
  tran_co      VARCHAR(4)                            NOT NULL
  COMMENT '交易码',
  tran_nm      VARCHAR(4)                            NOT NULL
  COMMENT '交易名称',
  is_paused    TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '交易暂停:{0:正常, 1:暂停}',
  resume_time  TIMESTAMP                             NULL
  COMMENT '交易暂停的恢复时间',
  is_deleted   TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '交易代码对应关系表';
CREATE UNIQUE INDEX id_UNIQUE
  ON trans (id);
CREATE INDEX created_time_ix
  ON trans (created_time);
CREATE UNIQUE INDEX merch_co_tran_co_UNIQUE
  ON trans (merch_co, tran_co);

-- ----------------------------
--  Table structure for protocol
-- ----------------------------
DROP TABLE
IF EXISTS protocol;

CREATE TABLE protocol
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  merch_co     VARCHAR(15)                           NOT NULL
  COMMENT '商户号',
  acct_no      VARCHAR(20)                           NOT NULL
  COMMENT '卡号',
  protocol_no  VARCHAR(64)                           NOT NULL
  COMMENT '协议号',
  acct_nm      VARCHAR(20)                           NOT NULL
  COMMENT '户名',
  mobile       VARCHAR(11)                           NOT NULL
  COMMENT '手机号',
  id_tp        VARCHAR(1)                            NOT NULL                    DEFAULT '0'
  COMMENT '证件类型, 默认身份证：0',
  id_no        VARCHAR(40)                           NOT NULL
  COMMENT '证件号码',
  expired_time TIMESTAMP                             NOT NULL
  COMMENT '协议有效期',
  is_unsign    TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '是否解约:{0:正常, 1:已解约}',
  is_deleted   TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '协议签约表';
CREATE UNIQUE INDEX id_UNIQUE
  ON protocol (id);
CREATE INDEX created_time_ix
  ON protocol (created_time);
CREATE UNIQUE INDEX protocol_no_UNIQUE
  ON protocol (protocol_no);
CREATE UNIQUE INDEX merch_co_acct_no_UNIQUE
  ON protocol (merch_co, acct_no);
CREATE INDEX merch_co_ix
  ON protocol (merch_co);
CREATE INDEX acct_no_ix
  ON protocol (acct_no);

-- ----------------------------
--  Table structure for command
-- ----------------------------
DROP TABLE
IF EXISTS command;

CREATE TABLE command
(
  id              BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  merch_co        VARCHAR(15)                           NOT NULL
  COMMENT '商户号',
  protocol        VARCHAR(64)                           NOT NULL
  COMMENT '协议号',
  merch_serial_no VARCHAR(20)                           NOT NULL
  COMMENT '商户流水号',
  fpay_serial_no  VARCHAR(20)                           NOT NULL
  COMMENT '发财付流水号',
  fpay_date       VARCHAR(8)                            NOT NULL
  COMMENT '发财付交易日期',
  acct_no         VARCHAR(20)                           NOT NULL
  COMMENT '卡号',
  acct_nm         VARCHAR(20)                           NOT NULL
  COMMENT '户名',
  mobile          VARCHAR(11)                           NOT NULL
  COMMENT '手机号',
  id_tp           VARCHAR(1)                            NOT NULL                    DEFAULT '0'
  COMMENT '证件类型, 默认身份证：0',
  id_no           VARCHAR(40)                           NOT NULL
  COMMENT '证件号码',
  currCo          VARCHAR(2)                            NOT NULL                    DEFAULT '00'
  COMMENT '币种, 默认人民币：00',
  amount          DECIMAL(16, 2)                        NOT NULL
  COMMENT '交易金额',
  sndr_acct_tp    VARCHAR(2)                            NOT NULL                    DEFAULT '00'
  COMMENT '付款方式账户类型',
  rcvr_acct_tp    VARCHAR(2)                            NOT NULL                    DEFAULT '00'
  COMMENT '收款方式账户类型',
  settle_date     VARCHAR(8)                            NOT NULL
  COMMENT '清算日期',
  remark          VARCHAR(30)                           NOT NULL                    DEFAULT ''
  COMMENT '备注',
  resv1           VARCHAR(30)                           NOT NULL                    DEFAULT ''
  COMMENT '预留字段1',
  resv2           VARCHAR(30)                           NOT NULL                    DEFAULT ''
  COMMENT '预留字段2',
  tran_st         VARCHAR(1)                            NOT NULL                    DEFAULT 'I'
  COMMENT '交易状态',
  is_deleted      TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time    TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time    TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '交易流水表';
CREATE UNIQUE INDEX id_UNIQUE
  ON command (id);
CREATE INDEX created_time_ix
  ON command (created_time);
CREATE UNIQUE INDEX protocol_UNIQUE
  ON command (protocol);
CREATE UNIQUE INDEX merch_co_acct_no_UNIQUE
  ON command (merch_co, acct_no);
CREATE INDEX merch_co_ix
  ON command (merch_co);
CREATE INDEX acct_no_ix
  ON command (acct_no);

-- ----------------------------
--  Table structure for resp
-- ----------------------------
DROP TABLE
IF EXISTS resp;

CREATE TABLE resp
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  resp_co      VARCHAR(4)                            NOT NULL
  COMMENT '响应码',
  resp_msg     VARCHAR(40)                           NOT NULL
  COMMENT '响应码描述',
  trans_st     VARCHAR(1)                            NOT NULL
  COMMENT '交易状态',
  is_deleted   TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '错误码表';
CREATE UNIQUE INDEX id_UNIQUE
  ON resp (id);
CREATE INDEX created_time_ix
  ON resp (created_time);
CREATE UNIQUE INDEX resp_co_UNIQUE
  ON resp (resp_co);

#====================初始数据====================#

-- ----------------------------
--  data for user
-- ----------------------------
INSERT INTO user
(username, email, password, salt, fullname)
VALUES
  ('admin', 'java@kangyonggan.com', '9606b0029ba4a8c9369f288cced0dc465eb5eabd', '3685072edcf8aad8', '管理员');

-- ----------------------------
--  data for role
-- ----------------------------
INSERT INTO role
(code, name)
VALUES
  ('ROLE_ADMIN', '管理员');

-- ----------------------------
--  data for menu
-- ----------------------------
INSERT INTO menu
(code, name, pcode, url, sort, icon)
VALUES
  ('DASHBOARD', '工作台', '', 'index', 0, 'menu-icon fa fa-dashboard'),

  ('SYSTEM', '系统', 'DASHBOARD', 'system', 1, 'menu-icon fa fa-cogs'),
  ('SYSTEM_USER', '用户管理', 'SYSTEM', 'system/user', 0, ''),
  ('SYSTEM_ROLE', '角色管理', 'SYSTEM', 'system/role', 1, ''),
  ('SYSTEM_MENU', '菜单管理', 'SYSTEM', 'system/menu', 2, ''),

  ('DATA', '数据', 'DASHBOARD', 'data', 2, 'menu-icon fa fa-gavel'),
  ('DATA_CACHE', '缓存管理', 'CONTENT', 'data/cache', 0, ''),
  ('DATA_DICTIONARY', '数据字典', 'CONTENT', 'data/dictionary', 1, ''),
  ('DATA_MERCHANT', '商户信息', 'DATA', 'data/merchant', 2, '');

-- ----------------------------
--  data for user_role
-- ----------------------------
INSERT INTO user_role
VALUES
  ('admin', 'ROLE_ADMIN');

-- ----------------------------
--  data for role_menu
-- ----------------------------
INSERT INTO role_menu SELECT
                        'ROLE_ADMIN',
                        code
                      FROM menu;

INSERT INTO dictionary
(code, value, type, sort)
VALUES
  # 六种交易
  ('K001', '签约', 'TRANS_CO', 0),
  ('K002', '解约', 'TRANS_CO', 1),
  ('K003', '单笔代扣', 'TRANS_CO', 2),
  ('K004', '单笔代付', 'TRANS_CO', 3),
  ('K005', '交易查询', 'TRANS_CO', 4),
  ('K006', '账户余额查询', 'TRANS_CO', 5),

  # 证件类型
  ('0', '身份证', 'ID_TP', 0),
  ('1', '港澳居民往来内地通行证', 'ID_TP', 1),
  ('2', '港澳居民往来内地通行证', 'ID_TP', 2),
  ('3', '外国护照', 'ID_TP', 3),
  ('4', '其他', 'ID_TP', 4),

  # 币种
  ('00', '人民币', 'CURR_CO', 0);
