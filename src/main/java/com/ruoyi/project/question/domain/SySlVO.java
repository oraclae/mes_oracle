package com.ruoyi.project.question.domain;

import lombok.Data;

/**
 * Author: LiHai
 * Date: 2023/9/11
 * Description:
 */
@Data
public class SySlVO {
    private Integer dbsl;//待办数量
    private Integer hjsl;//呼叫数量   被叫人为本人
    private Integer jssl;//接收数量   接收人为本人
}
