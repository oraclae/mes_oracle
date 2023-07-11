package com.ruoyi.project.question.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Author: QXD
 * Date: 2023/4/7 13:54
 * Description:
 * Version:
 */
@Data
public class WtxxDTO {
   private String type; //快查数据的类型
   private String userName; //快查数据
   private String isUpdate;
   private String value; //修改问题数据状态的数据
   private String pageNum;
   private String pageSize;
   private String tName;
   private String tCode;
   private String tPid;
   private String tLevel;
   private String radios;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date CJSJ;      //问题创建时间
   private String WTMC;    //问题名称
   private String SCDDH;   //生产订单号
   private String CPXH;    //产品型号
   private String WTLY;    //问题来源
   private String ZRBM;    //责任部门 责任人表中的数据
   private String ZRKS;    //责任科室 责任人表中的数据
   private String ZRR;     //主责任人 责任人表中的数据
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private String XWJJSJ;    //希望解决时间
   private String WTXL;    //问题细类
   private String ZYCD;    //紧急程度
   private String YZRBM;   //阅知人部门
   private String YZRBMID;   //阅知人部门id
   private String WTLB;    //问题类别
   private String WTMS;    //问题描述
   private String WTZT;    //问题状态
   private String WTZT1;    //问题状态
   private String WTZT2;    //问题状态
   private String WTZT3;    //问题状态
   private String ID;      //问题ID
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date GBSJ;      //关闭时间
   private String WCTS;    //完成天数
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date TJSJ;      //提交时间
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date JSSJ;      //接收时间
   private String GZXT;    //是否跟踪协同
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date JJSJ;      //解决时间
   private Integer WTDB;    //我的问题待办数量
   private Integer WDWT;    //我的待办待办数量
   private String CJBMID;  //创建部门ID
   private String CJKSID;  //创建科室ID
   private String CJRID;   //创建人ID
   private String CJR;     //创建人
   private String CJKS;     //创建科室
   private String CJBM;     //创建部门
   private String GXH;     //工序号
   private String YZR;     //阅知人
   private String YZRID;     //阅知人id
   private String ZRRID;   //责任人ID
   private String ZRRBMID; //责任人部门ID
   private String ZRRKSID; //责任人科室ID
   private String WTSJ;    //问题升级
   private String XCPHWT;  //是否现场配合问题
   private String LDPS;    //是否领导批示
   private String SCFJ;    //是否上传附件
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date YJSJ;      //预警时间
   private String WTBH;    //问题编号
   private String YXCD;    //影响程度
   private String SJRJS;    //升级人级数
   private String CQTS;    //超期天数
   private String WWCTS;    //未完成天数
   private String LXFK;    //是否例行反馈
   private String PC;      //批次
   private String SB;      //设备
   private String DQZRR;  //当前责任人
   private String BHSJ;  //闭环时间
   private String JHSJ;  //激活时间
   private Integer SFDZFK;  //是否当周反馈
}
