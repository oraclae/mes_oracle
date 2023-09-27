package com.ruoyi.project.pc.service;

import java.util.List;

import com.ruoyi.project.pc.domain.DaLzjscdd;

/**
 * 零组件生产订单Service接口
 *
 * @author qxd
 * @date 2023-09-22
 */
public interface IDaLzjscddService {
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
     * 批量删除零组件生产订单
     *
     * @param ids 需要删除的零组件生产订单主键集合
     * @return 结果
     */
    public int deleteDaLzjscddByIds(String[] ids);

    /**
     * 删除零组件生产订单信息
     *
     * @param id 零组件生产订单主键
     * @return 结果
     */
    public int deleteDaLzjscddById(String id);
}
