package com.ruoyi.project.question.service.impl;

import com.ruoyi.project.question.domain.ZhiNengWenDaDTO;
import com.ruoyi.project.question.domain.vo.ZhiNengWenDaSelectVO;
import com.ruoyi.project.question.mapper.ZhiNengWenDaMapper;
import com.ruoyi.project.question.service.IZhiNengWenDaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: LiHai
 * Date: 2023/7/25
 * Description:
 */
@Service
public class ZhiNengWenDaServiceImpl implements IZhiNengWenDaService {
    @Autowired
    ZhiNengWenDaMapper zhiNengWenDaMapper;

    /**
     * 查询
     */
    @Override
    public ZhiNengWenDaSelectVO selectDataByNotId(ZhiNengWenDaDTO zhiNengWenDaDTO) {
        System.out.println(zhiNengWenDaDTO.getIds().length);
        return zhiNengWenDaMapper.selectDataByNotId(zhiNengWenDaDTO);
    }
}
