package com.ruoyi.project.pc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.pc.mapper.DaSbMapper;
import com.ruoyi.project.pc.domain.DaSb;
import com.ruoyi.project.pc.service.IDaSbService;

/**
 * 设备Service业务层处理
 *
 * @author qxd
 * @date 2023-09-25
 */
@Service
public class DaSbServiceImpl implements IDaSbService {
    @Autowired
    private DaSbMapper daSbMapper;

    /**
     * 查询设备
     *
     * @param id 设备主键
     * @return 设备
     */
    @Override
    public DaSb selectDaSbById(String id) {
        return daSbMapper.selectDaSbById(id);
    }

    /**
     * 查询设备列表
     *
     * @param daSb 设备
     * @return 设备
     */
    @Override
    public List<DaSb> selectDaSbList(DaSb daSb) {
        return daSbMapper.selectDaSbList(daSb);
    }

    /**
     * 新增设备
     *
     * @param daSb 设备
     * @return 结果
     */
    @Override
    public int insertDaSb(DaSb daSb) {
        return daSbMapper.insertDaSb(daSb);
    }

    /**
     * 修改设备
     *
     * @param daSb 设备
     * @return 结果
     */
    @Override
    public int updateDaSb(DaSb daSb) {
        return daSbMapper.updateDaSb(daSb);
    }

    /**
     * 批量删除设备
     *
     * @param ids 需要删除的设备主键
     * @return 结果
     */
    @Override
    public int deleteDaSbByIds(String[] ids) {
        return daSbMapper.deleteDaSbByIds(ids);
    }

    /**
     * 删除设备信息
     *
     * @param id 设备主键
     * @return 结果
     */
    @Override
    public int deleteDaSbById(String id) {
        return daSbMapper.deleteDaSbById(id);
    }
}
