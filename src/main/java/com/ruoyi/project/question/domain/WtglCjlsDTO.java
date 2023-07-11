package com.ruoyi.project.question.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 提出问题对象 wtgl_cjls
 * 
 * @author LiHai
 * @date 2023-06-19
 */
@Data
public class WtglCjlsDTO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private String selectType; //快查数据的类型
    private String selectData; //快查数据的数据

    /** ID */
    private String LSID;

    /** 展示 */
    private String ZS;

    /** 车间问题ID */
    private String CJWTID;

    /** 接收按钮 */
    private String WTJS;

    /** 设备型号 */
    private String SBXH;

    /** 件号 */
    private String JH;

    /** 产品型号 */
    private String CPXH;

    /** 工序号 */
    private String GXH;

    /** 工序名称 */
    private String GXMC;

    /** 班产日期 */
    private String BCRQ;

    /** 提报人 */
    private String TBR;

    /** 被叫科室 */
    private String BJZRKS;

    /** 被叫人 */
    private String BJZRR;

    /** 问题描述 */
    private String WTMS;

    /** 提报-接收时间 */
    private String TBJSSJ;

    /** 提报-当前时间 */
    private String TBDQST;

    /** 接收人 */
    private String JSR;

    /** 解决方案 */
    private String JJFA;

    /** 问题状态 */
    private String WTZT;

    /** 生产订单号 */
    private String SCDDH;

    /** 批次 */
    private String PC;

    /** 是否满意 */
    private String SFMY;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date CJSJ;

    /** 提报人id */
    private String TBRID;

    /** 被叫人id */
    private String BJRID;

    /** 接收人id */
    private String JSRID;

    /** 创建部门科室 */
    private String CJBMKS;

    /** 问题名称 */
    private String WTMC;

    /** 问题类别 */
    private String WTLB;

    /** 问题细类 */
    private String WTXL;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;

    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params)
    {
        this.params = params;
    }

}
