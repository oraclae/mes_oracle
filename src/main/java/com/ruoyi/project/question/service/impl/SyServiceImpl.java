package com.ruoyi.project.question.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.question.domain.SySlVO;
import com.ruoyi.project.question.mapper.SyMapper;
import com.ruoyi.project.question.service.ISyService;
import com.ruoyi.project.system.domain.SysDept;
import com.ruoyi.project.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 首页Service业务层处理
 * 
 * @author LiHai
 * @date 2023-08-25
 */
@Service
public class SyServiceImpl implements ISyService
{
    @Autowired
    private SyMapper syMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public SySlVO getSl() {
        SysDept sysDept = sysDeptMapper.selectDeptById(SecurityUtils.getDeptId());
        String bm = sysDept.getDeptName();
        String userId = SecurityUtils.getUserId().toString();
        SySlVO sySlVO = new SySlVO();
        //代办数量
        int dbsl = syMapper.getDbsl(userId);
        sySlVO.setDbsl(dbsl);
        //接收数量
        int hjsl = syMapper.getHjsl(bm,userId);
        sySlVO.setHjsl(hjsl);
        //接收数量
        int jssl = syMapper.getJssl(bm,userId);
        sySlVO.setJssl(jssl);
        return sySlVO;
    }
}
