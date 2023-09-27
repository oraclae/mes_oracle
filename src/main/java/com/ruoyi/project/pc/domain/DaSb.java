package com.ruoyi.project.pc.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 设备对象 da_sb
 *
 * @author qxd
 * @date 2023-09-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DaSb extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 设备编号
     */
    @Excel(name = "设备编号")
    private String sbbh;

    /**
     * 设备名称
     */
    @Excel(name = "设备名称")
    private String sbmc;

    /**
     * 设备类别
     */
    @Excel(name = "设备类别")
    private String sblb;

    /**
     * 所属部门
     */
    @Excel(name = "所属部门")
    private String ssbm;

    /**
     * 设备班次
     */
    @Excel(name = "设备班次")
    private String sbbc;

    /**
     * 设备颜色
     */
    @Excel(name = "设备颜色")
    private String sbys;

    /**
     * 甘特图显示次序
     */
    @Excel(name = "甘特图显示次序")
    private String gttxscx;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String zt;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String bz;

    /**
     * 设备别名
     */
    @Excel(name = "设备别名")
    private String sbbm;
}
