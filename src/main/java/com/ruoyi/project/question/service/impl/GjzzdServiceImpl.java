package com.ruoyi.project.question.service.impl;

import java.util.List;
import java.util.UUID;

import com.ruoyi.project.question.domain.Gjzzd;
import com.ruoyi.project.question.mapper.GjzzdMapper;
import com.ruoyi.project.question.service.IGjzzdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 关键字字典Service业务层处理
 * 
 * @author LiHai
 * @date 2023-08-25
 */
@Service
public class GjzzdServiceImpl implements IGjzzdService
{
    @Autowired
    private GjzzdMapper gjzzdMapper;

    /**
     * 查询关键字字典
     * 
     * @param xh 关键字字典主键
     * @return 关键字字典
     */
    @Override
    public Gjzzd selectGjzzdByXh(String xh)
    {
        return gjzzdMapper.selectGjzzdByXh(xh);
    }

    /**
     * 查询关键字字典列表
     * 
     * @param gjzzd 关键字字典
     * @return 关键字字典
     */
    @Override
    public List<Gjzzd> selectGjzzdList(Gjzzd gjzzd)
    {
        return gjzzdMapper.selectGjzzdList(gjzzd);
    }

    /**
     * 新增关键字字典
     * 
     * @param gjzzd 关键字字典
     * @return 结果
     */
    @Override
    public int insertGjzzd(Gjzzd gjzzd)
    {
        gjzzd.setXh(UUID.randomUUID().toString());
        if (gjzzdMapper.countByGjz(gjzzd.getGjz())>0) {
            return 0;
        }
        return gjzzdMapper.insertGjzzd(gjzzd);
    }

    /**
     * 修改关键字字典
     * 
     * @param gjzzd 关键字字典
     * @return 结果
     */
    @Override
    public int updateGjzzd(Gjzzd gjzzd)
    {
        return gjzzdMapper.updateGjzzd(gjzzd);
    }

    /**
     * 批量删除关键字字典
     * 
     * @param xhs 需要删除的关键字字典主键
     * @return 结果
     */
    @Override
    public int deleteGjzzdByXhs(String[] xhs)
    {
        return gjzzdMapper.deleteGjzzdByXhs(xhs);
    }

    /**
     * 删除关键字字典信息
     * 
     * @param xh 关键字字典主键
     * @return 结果
     */
    @Override
    public int deleteGjzzdByXh(String xh)
    {
        return gjzzdMapper.deleteGjzzdByXh(xh);
    }
}
