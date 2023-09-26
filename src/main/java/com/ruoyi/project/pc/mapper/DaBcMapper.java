package com.ruoyi.project.pc.mapper;

import java.util.List;

import com.ruoyi.project.pc.domain.DaBc;

/**
 * 班次Mapper接口
 *
 * @author qxd
 * @date 2023-09-25
 */
public interface DaBcMapper {
    /**
     * 查询班次
     *
     * @param id 班次主键
     * @return 班次
     */
    public DaBc selectDaBcById(String id);

    /**
     * 查询班次列表
     *
     * @param daBc 班次
     * @return 班次集合
     */
    public List<DaBc> selectDaBcList(DaBc daBc);

    /**
     * 新增班次
     *
     * @param daBc 班次
     * @return 结果
     */
    public int insertDaBc(DaBc daBc);

    /**
     * 修改班次
     *
     * @param daBc 班次
     * @return 结果
     */
    public int updateDaBc(DaBc daBc);

    /**
     * 删除班次
     *
     * @param id 班次主键
     * @return 结果
     */
    public int deleteDaBcById(String id);

    /**
     * 批量删除班次
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDaBcByIds(String[] ids);
}
