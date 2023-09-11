package com.ruoyi.project.question.service;

import com.ruoyi.project.question.controller.SyController;
import com.ruoyi.project.question.domain.Gjzzd;
import com.ruoyi.project.question.domain.SySlVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 首页Service接口
 * 
 * @author LiHai
 * @date 2023-08-25
 */
@Transactional
public interface ISyService
{
    /**
     * 获取数量
     */
    SySlVO getSl();
}
