package com.ruoyi.project.question.mapper;


import com.ruoyi.project.question.domain.ZhiNengWenDaDTO;
import com.ruoyi.project.question.domain.vo.ZhiNengWenDaSelectVO;

import java.util.List;

/**
 * sjzrzdMapper接口
 * 
 * @author LiHai
 * @date 2023-07-21
 */
public interface ZhiNengWenDaMapper
{

    /**
     * 查询
     */
    ZhiNengWenDaSelectVO selectDataByNotId(ZhiNengWenDaDTO zhiNengWenDaDTO);

    /**
     * 获取关键字
     */
    List<String> selectGjz(String wtms);
}
