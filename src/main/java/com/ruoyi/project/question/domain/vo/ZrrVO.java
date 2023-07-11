package com.ruoyi.project.question.domain.vo;

import lombok.Data;

/**
 * Author: QXD
 * Date: 2023/6/16 10:46
 * Description:
 * Version:
 */
@Data
public class ZrrVO {
   private String ZRRID;     //问题责任人ID
   private String WTID;      //问题ID
   private String ZRRZT;     //问题责任人状态
   private String ZRBM;      //问题责任部门
   private String ZRKS;      //问题责任科室
   private String ZRR;       //问题责任人
   private String XH;        //序号
   private String ZRBMID;    //问题责任部门ID
   private String ZRKSID;    //问题责任科室ID
   private String SFZZRR;    //是否主责任人

}
