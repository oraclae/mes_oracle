package com.ruoyi.project.question.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * Author: QXD
 * Date: 2023/6/13 12:43
 * Description: 问题的数据交互实体类
 * Version:
 */
@Data
public class SjjhDTO {
   private String userName;
   private String isUpdate;
   private String value; //修改问题数据状态的数据
   private String pageNum;
   private String pageSize;
   private String tName;
   private String tCode;
   private String tPid;
   private String tLevel;
   private String radios;
   private String XH;         //序号
   private String HFXX;       //回复信息
   private String WTBT;       //问题标题
   private String WTID;       //问题ID
   private String HFR;        //回复人
   private String HFRID;        //回复人id
   private String WTCJID;     //问题创建人ID
   private String WTCJR;      //问题创建人
   private String WTCJBM;     //问题创建部门
   private String WTCJKS;     //问题创建科室
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date   WTCJSJ;     //问题创建时间
   private String WTZRRID;    //问题责任人ID
   private String WTZRRBM;    //问题责任人部门
   private String WTZRR;      //问题责任人
   private String JHZT;       //交互记录状态
   private String EJHFPPYJ;   //二级回复信息与一级回复信息匹配的
   private String JS;   //级数
   private String YYD;   //已阅读
   private String WTXL;   //问题细类
   private String WTLB;   //问题类别
}
