package com.ruoyi.project.question.service.impl;

import java.util.List;
import java.util.UUID;

import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.question.mapper.WtlbRyMapper;
import com.ruoyi.project.question.domain.WtlbRy;
import com.ruoyi.project.question.service.IWtlbRyService;

/**
 * 问题类别人员对照Service业务层处理
 *
 * @author ruoyi
 * @date 2023-08-25
 */
@Service
public class WtlbRyServiceImpl implements IWtlbRyService {
    @Autowired
    private WtlbRyMapper wtlbRyMapper;

    /**
     * 查询问题类别人员对照
     *
     * @param xh 问题类别人员对照主键
     * @return 问题类别人员对照
     */
    @Override
    public WtlbRy selectWtlbRyByXh(String xh) {
        return wtlbRyMapper.selectWtlbRyByXh(xh);
    }

    /**
     * 查询问题类别人员对照列表
     *
     * @param wtlbRy 问题类别人员对照
     * @return 问题类别人员对照
     */
    @Override
    public List<WtlbRy> selectWtlbRyList(WtlbRy wtlbRy) {
        wtlbRy.setDEPTID(SecurityUtils.getDeptId().toString());
        return wtlbRyMapper.selectWtlbRyList(wtlbRy);
    }

    /**
     * 新增问题类别人员对照
     *
     * @param wtlbRy 问题类别人员对照
     * @return 结果
     */
    @Override
    public int insertWtlbRy(WtlbRy wtlbRy) {
        wtlbRy.setXH(UUID.randomUUID().toString());
        wtlbRy.setDEPTID(SecurityUtils.getDeptId().toString());
        if (wtlbRyMapper.countByDeptIdAndWtlbAndWtxl(wtlbRy) > 0) {
            return 0;
        }
        return wtlbRyMapper.insertWtlbRy(wtlbRy);
    }

    /**
     * 修改问题类别人员对照
     *
     * @param wtlbRy 问题类别人员对照
     * @return 结果
     */
    @Override
    public int updateWtlbRy(WtlbRy wtlbRy) {
        return wtlbRyMapper.updateWtlbRy(wtlbRy);
    }

    /**
     * 批量删除问题类别人员对照
     *
     * @param xhs 需要删除的问题类别人员对照主键
     * @return 结果
     */
    @Override
    public int deleteWtlbRyByXhs(String[] xhs) {
        return wtlbRyMapper.deleteWtlbRyByXhs(xhs);
    }

    /**
     * 删除问题类别人员对照信息
     *
     * @param xh 问题类别人员对照主键
     * @return 结果
     */
    @Override
    public int deleteWtlbRyByXh(String xh) {
        return wtlbRyMapper.deleteWtlbRyByXh(xh);
    }
}
