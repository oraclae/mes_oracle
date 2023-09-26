package com.ruoyi.project.pc.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.pc.mapper.DaBcMapper;
import com.ruoyi.project.pc.domain.DaBc;
import com.ruoyi.project.pc.service.IDaBcService;

/**
 * 班次Service业务层处理
 * 
 * @author qxd
 * @date 2023-09-25
 */
@Service
public class DaBcServiceImpl implements IDaBcService 
{
    @Autowired
    private DaBcMapper daBcMapper;

    /**
     * 查询班次
     * 
     * @param id 班次主键
     * @return 班次
     */
    @Override
    public DaBc selectDaBcById(String id)
    {
        return daBcMapper.selectDaBcById(id);
    }

    /**
     * 查询班次列表
     * 
     * @param daBc 班次
     * @return 班次
     */
    @Override
    public List<DaBc> selectDaBcList(DaBc daBc)
    {
        return daBcMapper.selectDaBcList(daBc);
    }

    /**
     * 新增班次
     * 
     * @param daBc 班次
     * @return 结果
     */
    @Override
    public int insertDaBc(DaBc daBc)
    {
        return daBcMapper.insertDaBc(daBc);
    }

    /**
     * 修改班次
     * 
     * @param daBc 班次
     * @return 结果
     */
    @Override
    public int updateDaBc(DaBc daBc)
    {
        return daBcMapper.updateDaBc(daBc);
    }

    /**
     * 批量删除班次
     * 
     * @param ids 需要删除的班次主键
     * @return 结果
     */
    @Override
    public int deleteDaBcByIds(String[] ids)
    {
        return daBcMapper.deleteDaBcByIds(ids);
    }

    /**
     * 删除班次信息
     * 
     * @param id 班次主键
     * @return 结果
     */
    @Override
    public int deleteDaBcById(String id)
    {
        return daBcMapper.deleteDaBcById(id);
    }
}
