package com.ruoyi.project.pc.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 零组件生产订单对象 da_lzjscdd
 *
 * @author qxd
 * @date 2023-09-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DaLzjscdd extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 零组件号
     */
    @Excel(name = "零组件号")
    private String lzjh;

    /**
     * 零组件名称
     */
    @Excel(name = "零组件名称")
    private String lzjmc;

    /**
     * 生产订单号
     */
    @Excel(name = "生产订单号")
    private String scddh;

    /**
     * 是否外协
     */
    @Excel(name = "是否外协")
    private String sfwx;

    /**
     * 批次
     */
    @Excel(name = "批次")
    private String pc;

    /**
     * 要求交付日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "要求交付日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date yqjfrq;

    /**
     * 订单数量
     */
    @Excel(name = "订单数量")
    private Long ddsl;

    /**
     * 在制数量
     */
    @Excel(name = "在制数量")
    private Long zzsl;

    /**
     * 在制品状态
     */
    @Excel(name = "在制品状态")
    private String zzpzt;

    /**
     * 预计完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预计完成日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date yjwcsj;

    /**
     * 完工数量
     */
    @Excel(name = "完工数量")
    private Long wgsl;

    /**
     * 实际完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sjwcsj;

    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cjrq;
}
