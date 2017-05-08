create table CRMR_BRANCH
(
  CODE VARCHAR2(50) not null,
  NAME VARCHAR2(50) not null
);
create table CRMR_PERMISSION
(
  ID        NUMBER not null,
  TYPE      VARCHAR2(10) not null,
  NAME      VARCHAR2(10) not null,
  TITLE     VARCHAR2(100) not null,
  SEQNO     VARCHAR2(100) not null,
  PARENT_ID NUMBER not null
);
create table CRMR_POSITION
(
  ID          NUMBER not null,
  NAME        VARCHAR2(50) not null,
  BRANCH_CODE VARCHAR2(50)
);
create table CRMR_POSITION_ROLE
(
  POSITION_ID NUMBER not null,
  ROLE_ID     NUMBER not null,
  ALLOW_GRANT NUMBER not null
);
create table CRMR_RESOURCE
(
  ID            NUMBER not null,
  SOURCE        VARCHAR2(100) not null,
  PERMISSION_ID NUMBER not null
);
create table CRMR_ROLE
(
  ID    NUMBER not null,
  NAME  VARCHAR2(10) not null,
  TITLE VARCHAR2(100) not null
);
create table CRMR_ROLE_PERMISSION
(
  ROLE_ID       NUMBER not null,
  PERMISSION_ID NUMBER not null
);
create table CRMR_USER
(
  ID          NUMBER not null,
  USERNAME    VARCHAR2(50) not null,
  PASSWORD    VARCHAR2(50) not null,
  SALT        VARCHAR2(8) not null,
  ENABLED     NUMBER(1),
  REAL_NAME   VARCHAR2(50),
  POSITION_ID NUMBER
);
