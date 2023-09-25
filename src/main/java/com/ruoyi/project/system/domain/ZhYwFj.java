package com.ruoyi.project.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 附件对象 zh_yw_fj
 *
 * @author qxd
 * @date 2023-07-19
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class ZhYwFj extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    private String xh;

    /**
     * id
     */
    private String id;

    /**
     * 文件名称
     */
    @Excel(name = "文件名称")
    private String wjmc;

    /**
     * 上传时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date scsj;

    /**
     * 下载路径
     */
    @Excel(name = "下载路径")
    private String lj;

    /**
     * 创建人
     */
    private String cjr;

    /**
     * 创建人id
     */
    private String cjrid;

    /**
     * 判断上传的数据是什么类型如回复数据问题的和问题的
     */
    private String type;
}
