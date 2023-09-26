package com.ruoyi.project.pc.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 日期对象 da_rl
 *
 * @author qxd
 * @date 2023-09-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DaRl extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 日期
     */
    @Excel(name = "日期")
    private String rq;

    /**
     * 星期
     */
    @Excel(name = "星期")
    private String xq;

    /**
     * 是否上班
     */
    @Excel(name = "是否上班")
    private String sfsb;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String bz;

    /**
     * 开始日期
     */
    private String ksrq;

    /**
     * 结束日期
     */
    private String jsrq;
}
