package com.ruoyi.project.pc.mapper;

import java.util.List;

import com.ruoyi.project.pc.domain.ZhPcSbtl;

/**
 * 排产设备特例Mapper接口
 *
 * @author qxd
 * @date 2023-09-25
 */
public interface ZhPcSbtlMapper {
    /**
     * 查询排产设备特例
     *
     * @param id 排产设备特例主键
     * @return 排产设备特例
     */
    public ZhPcSbtl selectZhPcSbtlById(String id);

    /**
     * 查询排产设备特例列表
     *
     * @param zhPcSbtl 排产设备特例
     * @return 排产设备特例集合
     */
    public List<ZhPcSbtl> selectZhPcSbtlList(ZhPcSbtl zhPcSbtl);

    /**
     * 新增排产设备特例
     *
     * @param zhPcSbtl 排产设备特例
     * @return 结果
     */
    public int insertZhPcSbtl(ZhPcSbtl zhPcSbtl);

    /**
     * 修改排产设备特例
     *
     * @param zhPcSbtl 排产设备特例
     * @return 结果
     */
    public int updateZhPcSbtl(ZhPcSbtl zhPcSbtl);

    /**
     * 删除排产设备特例
     *
     * @param id 排产设备特例主键
     * @return 结果
     */
    public int deleteZhPcSbtlById(String id);

    /**
     * 批量删除排产设备特例
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZhPcSbtlByIds(String[] ids);
}
