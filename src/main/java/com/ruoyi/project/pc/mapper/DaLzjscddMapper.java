package com.ruoyi.project.pc.mapper;

import java.util.List;
import com.ruoyi.project.pc.domain.DaLzjscdd;

/**
 * 零组件生产订单Mapper接口
 * 
 * @author qxd
 * @date 2023-09-22
 */
public interface DaLzjscddMapper 
{
    /**
     * 查询零组件生产订单
     * 
     * @param id 零组件生产订单主键
     * @return 零组件生产订单
     */
    public DaLzjscdd selectDaLzjscddById(String id);

    /**
     * 查询零组件生产订单列表
     * 
     * @param daLzjscdd 零组件生产订单
     * @return 零组件生产订单集合
     */
    public List<DaLzjscdd> selectDaLzjscddList(DaLzjscdd daLzjscdd);

    /**
     * 新增零组件生产订单
     * 
     * @param daLzjscdd 零组件生产订单
     * @return 结果
     */
    public int insertDaLzjscdd(DaLzjscdd daLzjscdd);

    /**
     * 修改零组件生产订单
     * 
     * @param daLzjscdd 零组件生产订单
     * @return 结果
     */
    public int updateDaLzjscdd(DaLzjscdd daLzjscdd);

    /**
     * 删除零组件生产订单
     * 
     * @param id 零组件生产订单主键
     * @return 结果
     */
    public int deleteDaLzjscddById(String id);

    /**
     * 批量删除零组件生产订单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDaLzjscddByIds(String[] ids);
}
