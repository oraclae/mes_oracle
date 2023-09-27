package com.ruoyi.project.pc.mapper;

import java.util.List;

import com.ruoyi.project.pc.domain.DaSb;

/**
 * 设备Mapper接口
 *
 * @author qxd
 * @date 2023-09-25
 */
public interface DaSbMapper {
    /**
     * 查询设备
     *
     * @param id 设备主键
     * @return 设备
     */
    public DaSb selectDaSbById(String id);

    /**
     * 查询设备列表
     *
     * @param daSb 设备
     * @return 设备集合
     */
    public List<DaSb> selectDaSbList(DaSb daSb);

    /**
     * 新增设备
     *
     * @param daSb 设备
     * @return 结果
     */
    public int insertDaSb(DaSb daSb);

    /**
     * 修改设备
     *
     * @param daSb 设备
     * @return 结果
     */
    public int updateDaSb(DaSb daSb);

    /**
     * 删除设备
     *
     * @param id 设备主键
     * @return 结果
     */
    public int deleteDaSbById(String id);

    /**
     * 批量删除设备
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDaSbByIds(String[] ids);
}
