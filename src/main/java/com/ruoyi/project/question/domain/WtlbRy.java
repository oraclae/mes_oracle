package com.ruoyi.project.question.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 问题类别人员对照对象 wtgl_wtlb_ry
 * 
 * @author ruoyi
 * @date 2023-08-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WtlbRy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private String XH;

    /** 问题类别 */
    @Excel(name = "问题类别")
    private String WTLB;

    /** 问题细类 */
    @Excel(name = "问题细类")
    private String WTXL;

    /** 部门id */
    @Excel(name = "部门id")
    private String DEPTID;

    /** 被叫人姓名 */
    @Excel(name = "被叫人姓名")
    private String BJRXM;

    /** 被叫人id */
    @Excel(name = "被叫人id")
    private String BJRID;

    /** 被叫人id */
    @Excel(name = "接收人姓名")
    private String JSRXM;

    /** 被叫人id */
    @Excel(name = "接收人ID")
    private String JSRID;

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
