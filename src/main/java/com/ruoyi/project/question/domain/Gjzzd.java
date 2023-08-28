package com.ruoyi.project.question.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 关键字字典对象 wtgl_gjzzd
 * 
 * @author LiHai
 * @date 2023-08-25
 */
public class Gjzzd extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private String xh;

    /** 关键字 */
    @Excel(name = "关键字")
    private String gjz;

    public void setXh(String xh) 
    {
        this.xh = xh;
    }

    public String getXh() 
    {
        return xh;
    }
    public void setGjz(String gjz) 
    {
        this.gjz = gjz;
    }

    public String getGjz() 
    {
        return gjz;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("xh", getXh())
            .append("gjz", getGjz())
            .toString();
    }
}
