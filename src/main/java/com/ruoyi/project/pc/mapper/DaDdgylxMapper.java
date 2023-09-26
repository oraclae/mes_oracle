package com.ruoyi.project.pc.mapper;

import java.util.List;

import com.ruoyi.project.pc.domain.DaDdgylx;
import org.apache.ibatis.annotations.Param;

/**
 * 订单工艺路线Mapper接口
 *
 * @author qxd
 * @date 2023-09-22
 */
public interface DaDdgylxMapper {
    /**
     * 查询订单工艺路线
     *
     * @param id 订单工艺路线主键
     * @return 订单工艺路线
     */
    public DaDdgylx selectDaDdgylxById(String id);

    /**
     * 查询订单工艺路线列表
     *
     * @param daDdgylx 订单工艺路线
     * @return 订单工艺路线集合
     */
    public List<DaDdgylx> selectDaDdgylxList(DaDdgylx daDdgylx);

    /**
     * 新增订单工艺路线
     *
     * @param daDdgylx 订单工艺路线
     * @return 结果
     */
    public int insertDaDdgylx(DaDdgylx daDdgylx);

    /**
     * 修改订单工艺路线
     *
     * @param daDdgylx 订单工艺路线
     * @return 结果
     */
    public int updateDaDdgylx(DaDdgylx daDdgylx);

    /**
     * 删除订单工艺路线
     *
     * @param id 订单工艺路线主键
     * @return 结果
     */
    public int deleteDaDdgylxById(String id);

    /**
     * 批量删除订单工艺路线
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDaDdgylxByIds(String[] ids);

    /**
     * 搜索订单工艺路线数据（根据零组件号集合查询）
     */
    List<DaDdgylx> selectByLzjh(String ddh);

    /**
     * 批量修改数据
     */
    int updateDaDdgylxList(DaDdgylx daDdgylx);
}
