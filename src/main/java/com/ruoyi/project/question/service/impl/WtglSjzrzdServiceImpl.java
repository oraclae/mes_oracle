package com.ruoyi.project.question.service.impl;

import java.util.List;
import java.util.UUID;

import com.ruoyi.project.question.domain.WtglSjzrzd;
import com.ruoyi.project.question.mapper.WtglSjzrzdMapper;
import com.ruoyi.project.question.service.IWtglSjzrzdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 升级责任人Service业务层处理
 *
 * @author qxd
 * @date 2023-08-18
 */
@Service
public class WtglSjzrzdServiceImpl implements IWtglSjzrzdService {
    @Autowired
    private WtglSjzrzdMapper wtglSjzrzdMapper;

    /**
     * 查询升级责任人
     *
     * @param xh 升级责任人主键
     * @return 升级责任人
     */
    @Override
    public WtglSjzrzd selectWtglSjzrzdByXh(String xh) {
        return wtglSjzrzdMapper.selectWtglSjzrzdByXh(xh);
    }

    /**
     * 查询升级责任人列表
     *
     * @param wtglSjzrzd 升级责任人
     * @return 升级责任人
     */
    @Override
    public List<WtglSjzrzd> selectWtglSjzrzdList(WtglSjzrzd wtglSjzrzd) {
        return wtglSjzrzdMapper.selectWtglSjzrzdList(wtglSjzrzd);
    }

    /**
     * 新增升级责任人
     *
     * @param wtglSjzrzd 升级责任人
     * @return 结果
     */
    @Override
    public int insertWtglSjzrzd(WtglSjzrzd wtglSjzrzd) {
        wtglSjzrzd.setXh(UUID.randomUUID().toString());
        return wtglSjzrzdMapper.insertWtglSjzrzd(wtglSjzrzd);
    }

    /**
     * 修改升级责任人
     *
     * @param wtglSjzrzd 升级责任人
     * @return 结果
     */
    @Override
    public int updateWtglSjzrzd(WtglSjzrzd wtglSjzrzd) {
        return wtglSjzrzdMapper.updateWtglSjzrzd(wtglSjzrzd);
    }

    /**
     * 批量删除升级责任人
     *
     * @param xhs 需要删除的升级责任人主键
     * @return 结果
     */
    @Override
    public int deleteWtglSjzrzdByXhs(String[] xhs) {
        return wtglSjzrzdMapper.deleteWtglSjzrzdByXhs(xhs);
    }

    /**
     * 删除升级责任人信息
     *
     * @param xh 升级责任人主键
     * @return 结果
     */
    @Override
    public int deleteWtglSjzrzdByXh(String xh) {
        return wtglSjzrzdMapper.deleteWtglSjzrzdByXh(xh);
    }
}
