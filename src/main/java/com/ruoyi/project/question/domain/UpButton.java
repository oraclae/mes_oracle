package com.ruoyi.project.question.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * Author: LiHai
 * Date: 2023/7/21
 * Description:
 */
@Data
public class UpButton {
    //序号
    private String XH;
    //排序
    private Integer PX;
    //按钮名
    private String BUTNAME;
    //图片名
    private String IMGNAME;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date CJSJ;
}
