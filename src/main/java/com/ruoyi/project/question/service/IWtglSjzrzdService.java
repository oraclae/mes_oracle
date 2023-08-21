package com.ruoyi.project.question.service;

import com.ruoyi.project.question.domain.WtglSjzrzd;

import java.util.List;

/**
 * 升级责任人Service接口
 *
 * @author qxd
 * @date 2023-08-18
 */
public interface IWtglSjzrzdService {
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
     * 批量删除升级责任人
     *
     * @param xhs 需要删除的升级责任人主键集合
     * @return 结果
     */
    public int deleteWtglSjzrzdByXhs(String[] xhs);

    /**
     * 删除升级责任人信息
     *
     * @param xh 升级责任人主键
     * @return 结果
     */
    public int deleteWtglSjzrzdByXh(String xh);
}
