/*
 Navicat Premium Data Transfer

 Source Server         : oracle
 Source Server Type    : Oracle
 Source Server Version : 190000
 Source Host           : localhost:1521
 Source Schema         : SYSTEM

 Target Server Type    : Oracle
 Target Server Version : 190000
 File Encoding         : 65001

 Date: 10/07/2023 17:17:41
*/


-- ----------------------------
-- Table structure for WTGL_YWZD
-- ----------------------------
CREATE TABLE "WTGL_YWZD" (
  "XH" VARCHAR2(255) NOT NULL,
  "ZJD" VARCHAR2(255),
  "CJD" VARCHAR2(255),
  "BZ" VARCHAR2(255),
  "FJ" VARCHAR2(255)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  FREELISTS 1
  FREELIST GROUPS 1
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "WTGL_YWZD"."XH" IS '序号';
COMMENT ON COLUMN "WTGL_YWZD"."ZJD" IS '主节点（父）';
COMMENT ON COLUMN "WTGL_YWZD"."CJD" IS '次节点（子）';
COMMENT ON COLUMN "WTGL_YWZD"."BZ" IS '备注';
COMMENT ON COLUMN "WTGL_YWZD"."FJ" IS '附件';

-- ----------------------------
-- Records of WTGL_YWZD
-- ----------------------------
INSERT INTO "WTGL_YWZD" VALUES ('001', '问题类别', 'A-1', NULL, NULL);
INSERT INTO "WTGL_YWZD" VALUES ('002', '问题类别', 'A-2', NULL, NULL);
INSERT INTO "WTGL_YWZD" VALUES ('003', '问题类别', 'A-3', NULL, NULL);
INSERT INTO "WTGL_YWZD" VALUES ('004', '紧急程度', '紧急', NULL, NULL);
INSERT INTO "WTGL_YWZD" VALUES ('005', '紧急程度', '普通', NULL, NULL);

-- ----------------------------
-- Primary Key structure for table WTGL_YWZD
-- ----------------------------
ALTER TABLE "WTGL_YWZD" ADD CONSTRAINT "WTGL_YWZD_PK" PRIMARY KEY ("XH");

-- ----------------------------
-- Checks structure for table WTGL_YWZD
-- ----------------------------
ALTER TABLE "WTGL_YWZD" ADD CONSTRAINT "SYS_C007476" CHECK ("XH" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "WTGL_YWZD" ADD CONSTRAINT "SYS_C007771" CHECK ("XH" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
