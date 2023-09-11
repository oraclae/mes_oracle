package com.ruoyi.project.question.domain;

import lombok.Data;
import java.util.List;

/**
 * Author: QXD
 * Date: 2023/6/29 8:37
 * Description:
 * Version:
 */
@Data
public class ZhiNengWenDaDTO {
    private String[] ids;
    private String WTID;            //问题ID
    private String WTMC;            //问题名称
    private String ZRRID;           //责任人ID
    private String ZRR;             //责任人
    private String GBSJ;            //关闭时间
    private String DAXX;            //答案信息
    private String CJR;             //问题创建人
    private String CJRID;           //问题创建人ID
    private String GJC;             //关键词
    private String WTLB;            //问题类别
    private String WTXL;            //问题细类
    private String DAXXID;          //答案信息ID
    private Integer RD;             //热度
    private String WTMS;             //问题描述
    private String GXH;             //工序号
    private String GXMC;             //工序名称
    private String JH;             //件号
    private String CPXH;             //产品型号
    private List<String> GJZLIST;   //关键字
}
