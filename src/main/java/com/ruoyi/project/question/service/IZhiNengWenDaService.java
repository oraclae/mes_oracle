package com.ruoyi.project.question.service;

import com.ruoyi.project.question.domain.ZhiNengWenDaDTO;
import com.ruoyi.project.question.domain.vo.ZhiNengWenDaSelectVO;

/**
 * Author: LiHai
 * Date: 2023/7/25
 * Description:
 */
public interface IZhiNengWenDaService {
    /**
     * 查询
     */
    ZhiNengWenDaSelectVO selectDataByNotId(ZhiNengWenDaDTO zhiNengWenDaDTO);
}
