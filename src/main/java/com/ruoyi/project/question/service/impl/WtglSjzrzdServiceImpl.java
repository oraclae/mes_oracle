package com.ruoyi.project.question.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ruoyi.project.question.domain.WtglSjzrzd;
import com.ruoyi.project.question.domain.WtxxDTO;
import com.ruoyi.project.question.domain.vo.ZrrVO;
import com.ruoyi.project.question.mapper.QuestionMapper;
import com.ruoyi.project.question.mapper.WtglSjzrzdMapper;
import com.ruoyi.project.question.service.IWtglSjzrzdService;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.mapper.SysDeptMapper;
import com.ruoyi.project.system.mapper.SysUserMapper;
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

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

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
    public int insertWtglSjzrzd(WtglSjzrzd wtglSjzrzd) throws Exception{
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

    /**
     * 通过责任人id查询整个升级人数据
     */
    @Override
    public WtglSjzrzd selectWtglSjzrzdByZrrid(String zrrid) {
        WtglSjzrzd wtglSjzrzd = wtglSjzrzdMapper.selectWtglSjzrzdByZrrid(zrrid);
        return wtglSjzrzd;
    }

    /**
     * 问题升级的方法
     */
    @Override
    public int wtsjOneZrr(WtxxDTO wtxxDTO) {
        //通过wtid和zrrid判断是否存在这个责任人
        List<ZrrVO> list = questionMapper.getzerData(wtxxDTO.getID());
        for (ZrrVO zrrVO : list) {
            if (zrrVO.getZRRID().equals(wtxxDTO.getYjzrrid())) {
                return 500;
            }
        }
        //先获得需要添加责任人的id
        String yjzrrid = wtxxDTO.getYjzrrid();
        SysUser sysUser = sysUserMapper.selectUserById(Long.parseLong(yjzrrid));
        if (sysUser == null) {
            System.out.println("没有找到该用户");
            return 0;
        }
        List<ZrrVO> zrrVOS = new ArrayList<>();
        ZrrVO zrrVO = new ZrrVO();
        zrrVO.setWTID(wtxxDTO.getID());
        zrrVO.setXH(UUID.randomUUID().toString());
        zrrVO.setZRR(sysUser.getNickName());
        zrrVO.setZRRID(sysUser.getUserId() + "");
        zrrVO.setZRBM(sysUser.getDept().getDeptName());
        zrrVO.setZRBMID(sysUser.getDeptId()+"");
        zrrVO.setZRRZT("未处理");
        zrrVOS.add(zrrVO);
        questionMapper.createZRDB(zrrVOS);
        return 1;
    }

}
