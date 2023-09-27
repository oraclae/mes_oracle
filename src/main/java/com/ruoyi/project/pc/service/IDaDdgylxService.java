package com.ruoyi.project.pc.service;

import java.util.List;

import com.ruoyi.project.pc.domain.DaDdgylx;

/**
 * 订单工艺路线Service接口
 *
 * @author qxd
 * @date 2023-09-22
 */
public interface IDaDdgylxService {

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
     * 批量删除订单工艺路线
     *
     * @param ids 需要删除的订单工艺路线主键集合
     * @return 结果
     */
    public int deleteDaDdgylxByIds(String[] ids);

    /**
     * 删除订单工艺路线信息
     *
     * @param id 订单工艺路线主键
     * @return 结果
     */
    public int deleteDaDdgylxById(String id);


    /**
     * 排产
     */
    public void pc(String lzjh);

    /**
     * 设备排产
     */
    public void sbpc(String lzjh,String dateTime);

}
