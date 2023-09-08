package com.ruoyi.project.question.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 升级责任人对象 wtgl_sjzrzd
 *
 * @author qxd
 * @date 2023-08-18
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class WtglSjzrzd extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private String xh;

    /** 责任部门 */
    @Excel(name = "责任部门")
    private String zrbm;

    /** 责任科室 */
    @Excel(name = "一级级别")
    private String yjjb;
    @Excel(name = "当前级别")
    private String dqjb;

    /** 一级责任人 */
    @Excel(name = "一级责任人")
    private String yjzrr;

    /** 二级责任人 */
    @Excel(name = "二级责任人")
    private String ejzrr;

    /** 三级责任人 */
    @Excel(name = "三级责任人")
    private String sjzrr;

    /** 一级责任人id */
    @Excel(name = "一级责任人id")
    private String yjzrrid;

    /** 二级责任人id */
    @Excel(name = "二级责任人id")
    private String ejzrrid;

    /** 三级责任人id */
    @Excel(name = "三级责任人id")
    private String sjzrrid;

    /** 当前责任人 */
    @Excel(name = "当前责任人")
    private String dqzer;

    /** 当前责任人ID */
    @Excel(name = "当前责任人ID")
    private String dqzerid;
}
