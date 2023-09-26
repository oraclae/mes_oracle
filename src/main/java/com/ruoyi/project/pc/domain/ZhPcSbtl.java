package com.ruoyi.project.pc.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 排产设备特例对象 zh_pc_sbtl
 *
 * @author qxd
 * @date 2023-09-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ZhPcSbtl extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 日期 */
    @Excel(name = "日期")
    private String rq;

    /** 设备班次 */
    @Excel(name = "设备班次")
    private String sbbc;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String sbbh;

    /** 开/关 */
    @Excel(name = "开/关")
    private String kg;

    /** 备注 */
    @Excel(name = "备注")
    private String bz;

    /** 设备型号 */
    @Excel(name = "设备型号")
    private String sbxh;
}
