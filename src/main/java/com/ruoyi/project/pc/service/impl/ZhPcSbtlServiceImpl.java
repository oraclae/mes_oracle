package com.ruoyi.project.pc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.pc.mapper.ZhPcSbtlMapper;
import com.ruoyi.project.pc.domain.ZhPcSbtl;
import com.ruoyi.project.pc.service.IZhPcSbtlService;

/**
 * 排产设备特例Service业务层处理
 *
 * @author qxd
 * @date 2023-09-25
 */
@Service
public class ZhPcSbtlServiceImpl implements IZhPcSbtlService {
    @Autowired
    private ZhPcSbtlMapper zhPcSbtlMapper;

    /**
     * 查询排产设备特例
     *
     * @param id 排产设备特例主键
     * @return 排产设备特例
     */
    @Override
    public ZhPcSbtl selectZhPcSbtlById(String id) {
        return zhPcSbtlMapper.selectZhPcSbtlById(id);
    }

    /**
     * 查询排产设备特例列表
     *
     * @param zhPcSbtl 排产设备特例
     * @return 排产设备特例
     */
    @Override
    public List<ZhPcSbtl> selectZhPcSbtlList(ZhPcSbtl zhPcSbtl) {
        return zhPcSbtlMapper.selectZhPcSbtlList(zhPcSbtl);
    }

    /**
     * 新增排产设备特例
     *
     * @param zhPcSbtl 排产设备特例
     * @return 结果
     */
    @Override
    public int insertZhPcSbtl(ZhPcSbtl zhPcSbtl) {
        return zhPcSbtlMapper.insertZhPcSbtl(zhPcSbtl);
    }

    /**
     * 修改排产设备特例
     *
     * @param zhPcSbtl 排产设备特例
     * @return 结果
     */
    @Override
    public int updateZhPcSbtl(ZhPcSbtl zhPcSbtl) {
        return zhPcSbtlMapper.updateZhPcSbtl(zhPcSbtl);
    }

    /**
     * 批量删除排产设备特例
     *
     * @param ids 需要删除的排产设备特例主键
     * @return 结果
     */
    @Override
    public int deleteZhPcSbtlByIds(String[] ids) {
        return zhPcSbtlMapper.deleteZhPcSbtlByIds(ids);
    }

    /**
     * 删除排产设备特例信息
     *
     * @param id 排产设备特例主键
     * @return 结果
     */
    @Override
    public int deleteZhPcSbtlById(String id) {
        return zhPcSbtlMapper.deleteZhPcSbtlById(id);
    }
}
