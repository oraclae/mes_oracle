package com.ruoyi.project.question.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: LiHai
 * Date: 2023/7/10
 * Description:
 */
@Data
public class Ywzd {
    private static final long serialVersionUID = 1L;

    private String selectData;

    /** 主节点(父) */
    private String ZJD;

    /** 次节点（子） */
    private String CJD;

    /** 备注 */
    private String BZ;

    /** 附件 */
    private String FJ;

    /** 序号 */
    private String XH;

    /** 排序 */
    private Long PX;

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
