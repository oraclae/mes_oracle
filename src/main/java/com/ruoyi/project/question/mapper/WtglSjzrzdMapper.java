package com.ruoyi.project.question.mapper;

import java.util.List;

import com.ruoyi.project.question.domain.WtglSjzrzd;

/**
 * 升级责任人Mapper接口
 *
 * @author qxd
 * @date 2023-08-18
 */
public interface WtglSjzrzdMapper {
    /**
     * 查询升级责任人
     *
     * @param xh 升级责任人主键
     * @return 升级责任人
     */
    public WtglSjzrzd selectWtglSjzrzdByXh(String xh);

    /**
     * 查询升级责任人列表
     *
     * @param wtglSjzrzd 升级责任人
     * @return 升级责任人集合
     */
    public List<WtglSjzrzd> selectWtglSjzrzdList(WtglSjzrzd wtglSjzrzd);

    /**
     * 新增升级责任人
     *
     * @param wtglSjzrzd 升级责任人
     * @return 结果
     */
    public int insertWtglSjzrzd(WtglSjzrzd wtglSjzrzd);

    /**
     * 修改升级责任人
     *
     * @param wtglSjzrzd 升级责任人
     * @return 结果
     */
    public int updateWtglSjzrzd(WtglSjzrzd wtglSjzrzd);

    /**
     * 删除升级责任人
     *
     * @param xh 升级责任人主键
     * @return 结果
     */
    public int deleteWtglSjzrzdByXh(String xh);

    /**
     * 批量删除升级责任人
     *
     * @param xhs 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWtglSjzrzdByXhs(String[] xhs);

    /**
     * 通过责任人id查询整个升级人数据
     */
    WtglSjzrzd selectWtglSjzrzdByZrrid(String zrrid);
}
