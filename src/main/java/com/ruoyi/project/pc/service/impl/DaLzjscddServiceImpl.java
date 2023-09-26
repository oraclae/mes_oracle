package com.ruoyi.project.pc.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.pc.mapper.DaLzjscddMapper;
import com.ruoyi.project.pc.domain.DaLzjscdd;
import com.ruoyi.project.pc.service.IDaLzjscddService;

/**
 * 零组件生产订单Service业务层处理
 * 
 * @author qxd
 * @date 2023-09-22
 */
@Service
public class DaLzjscddServiceImpl implements IDaLzjscddService 
{
    @Autowired
    private DaLzjscddMapper daLzjscddMapper;

    /**
     * 查询零组件生产订单
     * 
     * @param id 零组件生产订单主键
     * @return 零组件生产订单
     */
    @Override
    public DaLzjscdd selectDaLzjscddById(String id)
    {
        return daLzjscddMapper.selectDaLzjscddById(id);
    }

    /**
     * 查询零组件生产订单列表
     * 
     * @param daLzjscdd 零组件生产订单
     * @return 零组件生产订单
     */
    @Override
    public List<DaLzjscdd> selectDaLzjscddList(DaLzjscdd daLzjscdd)
    {
        return daLzjscddMapper.selectDaLzjscddList(daLzjscdd);
    }

    /**
     * 新增零组件生产订单
     * 
     * @param daLzjscdd 零组件生产订单
     * @return 结果
     */
    @Override
    public int insertDaLzjscdd(DaLzjscdd daLzjscdd)
    {
        return daLzjscddMapper.insertDaLzjscdd(daLzjscdd);
    }

    /**
     * 修改零组件生产订单
     * 
     * @param daLzjscdd 零组件生产订单
     * @return 结果
     */
    @Override
    public int updateDaLzjscdd(DaLzjscdd daLzjscdd)
    {
        return daLzjscddMapper.updateDaLzjscdd(daLzjscdd);
    }

    /**
     * 批量删除零组件生产订单
     * 
     * @param ids 需要删除的零组件生产订单主键
     * @return 结果
     */
    @Override
    public int deleteDaLzjscddByIds(String[] ids)
    {
        return daLzjscddMapper.deleteDaLzjscddByIds(ids);
    }

    /**
     * 删除零组件生产订单信息
     * 
     * @param id 零组件生产订单主键
     * @return 结果
     */
    @Override
    public int deleteDaLzjscddById(String id)
    {
        return daLzjscddMapper.deleteDaLzjscddById(id);
    }
}
