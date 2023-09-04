package com.ruoyi.project.question.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 import com.ruoyi.common.annotation.Excel;
 import com.ruoyi.common.core.domain.BaseEntity;

 /**
 * 【请填写功能名称】对象 wtgl_dacc
 *
 * @author ruoyi
 * @date 2023-07-07
 */
@Data
public class WtglDacc{
    private static final long serialVersionUID = 1L;

    private String selectData; //快查数据的数据

    /**
     * 问题ID
     */
    private String WTID;

    /**
     * 问题名称
     */
    private String WTMC;

    /**
     * 责任人ID
     */
    private String ZRRID;

    /**
     * 责任人
     */
    private String ZRR;

    /**
     * 关闭时间
     */
    private String GBSJ;

    /**
     * 答案信息
     */
    private String DAXX;

    /**
     * 问题创建人
     */
    private String CJR;

    /**
     * 问题创建人ID
     */
    private String CJRID;

    /**
     * 关键词
     */
    private String GJC;

    /**
     * 问题类别
     */
    private String WTLB;

    /**
     * 问题细类
     */
    private String WTXL;

    /**
     * 答案信息ID
     */
    private String DAXXID;

    /**
     * 热度
     */
    private Long RD;

    /**
     * 问题描述
     */
    private String WTMS;

    /**
     * 工序号
     */
    private String GXH;

    /**
     * 工序名称
     */
    private String GXMC;

    /**
     * 问题查询(问题名称+问题描述)
     */
    private String WTCX;

    /**
     * 产品型号
     */
    private String CPXH;

    /**
     * 序号
     */
    private String JH;

    /**
     * 关键字
     */
    private String KEYWORDS;

    /**
     * 部门ID
     */
    private String DEPTID;

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
