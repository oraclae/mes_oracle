package com.ruoyi.project.pc.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 班次对象 da_bc
 *
 * @author qxd
 * @date 2023-09-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DaBc extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 版次名称
     */
    @Excel(name = "版次名称")
    private String bcmc;

    /**
     * 版次代号
     */
    @Excel(name = "版次代号")
    private String bcdh;

    /**
     * 开始时间
     */
    @Excel(name = "开始时间")
    private String kssj;

    /**
     * 结束时间
     */
    @Excel(name = "结束时间")
    private String jssj;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String bz;
}
