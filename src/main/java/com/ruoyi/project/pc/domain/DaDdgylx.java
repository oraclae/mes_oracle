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
 * 订单工艺路线对象 da_ddgylx
 *
 * @author qxd
 * @date 2023-09-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DaDdgylx extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String ID;

    /**
     * 产品型号
     */
    @Excel(name = "产品型号")
    private String cpxh;

    /**
     * 产品名称
     */
    @Excel(name = "产品名称")
    private String cpmc;

    /**
     * 零组件型号
     */
    @Excel(name = "零组件型号")
    private String lzjxh;

    /**
     * 零组件名称
     */
    @Excel(name = "零组件名称")
    private String lzjmc;

    /**
     * 阶段
     */
    @Excel(name = "阶段")
    private String jd;

    /**
     * 版次
     */
    @Excel(name = "版次")
    private String bc;

    /**
     * 工序号
     */
    @Excel(name = "工序号")
    private Long gxh;

    /**
     * 工序名称
     */
    @Excel(name = "工序名称")
    private String gxmc;

    /**
     * 承制单位
     */
    @Excel(name = "承制单位")
    private String czdw;

    /**
     * 设备型号
     */
    @Excel(name = "设备型号")
    private String sbxh;

    /**
     * 控制码
     */
    @Excel(name = "控制码")
    private String kzm;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String bz;

    /**
     * 关键序
     */
    @Excel(name = "关键序")
    private String gjx;

    /**
     * 工种
     */
    @Excel(name = "工种")
    private String gc;

    /**
     * 人员
     */
    @Excel(name = "人员")
    private String ry;

    /**
     * 业务等级
     */
    @Excel(name = "业务等级")
    private String ywdj;

    /**
     * 质量要求
     */
    @Excel(name = "质量要求")
    private String zlyq;

    /**
     * 准备工时
     */
    @Excel(name = "准备工时")
    private Long zbgs;

    /**
     * 加工工时
     */
    @Excel(name = "加工工时")
    private Long jggs;

    /**
     * 生产加工(天)
     */
    @Excel(name = "生产加工(天)")
    private Long scjg;

    /**
     * 生产加工时间
     */
    @Excel(name = "生产加工时间")
    private Long scjgsj;

    /**
     * 核算工时
     */
    @Excel(name = "核算工时")
    private Long hsgs;

    /**
     * 核算工时比率
     */
    @Excel(name = "核算工时比率")
    private Long hsgsbl;

    /**
     * 存货编码
     */
    @Excel(name = "存货编码")
    private String chbm;

    /**
     * 批次
     */
    @Excel(name = "批次")
    private String pc;

    /**
     * 厂家
     */
    @Excel(name = "厂家")
    private String cj;

    /**
     * 临艺说明
     */
    @Excel(name = "临艺说明")
    private String lysm;

    /**
     * 派工时间
     */
    @Excel(name = "派工时间")
    private String pgsj;

    /**
     * 计划开始时间
     */
    @Excel(name = "计划开始时间")
    private String jhkssj;

    /**
     * 计划完成时间
     */
    @Excel(name = "计划完成时间")
    private String jhwcsj;

    /**
     * 实际完成时间
     */
    @Excel(name = "实际完成时间")
    private String sjwcsj;

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
     * 确认数量
     */
    @Excel(name = "确认数量")
    private Long qrsl;

    /**
     * 报废数量
     */
    @Excel(name = "报废数量")
    private Long bfsl;

    /**
     * 领料数量
     */
    @Excel(name = "领料数量")
    private Long llsl;

    /**
     * 缴库数量
     */
    @Excel(name = "缴库数量")
    private Long jksl;

    /**
     * 拆批数量
     */
    @Excel(name = "拆批数量")
    private Long cpsl;

    /**
     * 返工返修数量
     */
    @Excel(name = "返工返修数量")
    private Long fgfxsl;

    /**
     * 拆批说明
     */
    @Excel(name = "拆批说明")
    private String cpsm;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date cjsj;

    /**
     * 检验数量
     */
    @Excel(name = "检验数量")
    private Long jysl;

    /**
     * 不合格品数量
     */
    @Excel(name = "不合格品数量")
    private Long bhgpsl;

    /**
     * 确认
     */
    @Excel(name = "确认")
    private String qr;

    /**
     * 确认记录
     */
    @Excel(name = "确认记录")
    private String qrjl;

    /**
     * 级数
     */
    @Excel(name = "级数")
    private String js;

    /**
     * 系统控制
     */
    @Excel(name = "系统控制")
    private String xtkz;

    /**
     * 订单状态
     */
    @Excel(name = "订单状态")
    private String ddzt;

    /**
     * 是否业务
     */
    @Excel(name = "是否业务")
    private String sfyw;

    /**
     * 目录系统编码
     */
    @Excel(name = "目录系统编码")
    private String mlxtbm;

    /**
     * 人员编号
     */
    @Excel(name = "人员编号")
    private String rybh;

    /**
     * 库位
     */
    @Excel(name = "库位")
    private String kw;

    /**
     * 是否紧急
     */
    @Excel(name = "是否紧急")
    private String sfjj;

    /**
     * 拖欠数量
     */
    @Excel(name = "拖欠数量")
    private String tqsl;

    /**
     * 是否转工
     */
    @Excel(name = "是否转工")
    private String sfzg;

    /**
     * 是否外协
     */
    @Excel(name = "是否外协")
    private String sfwx;

    /**
     * 分厂
     */
    @Excel(name = "分厂")
    private String fc;

    /**
     * 超期状态
     */
    @Excel(name = "超期状态")
    private String cqzt;

    /**
     * 计划类型
     */
    @Excel(name = "计划类型")
    private String jhlx;

    /**
     * 工序需使用时间
     */
    @Excel(name = "工序需使用时间")
    private String gxxsysj;

    /**
     * 加工单元
     */
    @Excel(name = "加工单元")
    private String jgdy;

    /**
     * 是否需要工装
     */
    @Excel(name = "是否需要工装")
    private String sfxygz;

    /**
     * 设备编码
     */
    @Excel(name = "设备编码")
    private String sbbm;

    /**
     * 工段
     */
    @Excel(name = "工段")
    private String gd;

    /**
     * 附件
     */
    @Excel(name = "附件")
    private String fj;
    /**
     * 订单号
     */
    @Excel(name = "订单号")
    private String ddh;
}
