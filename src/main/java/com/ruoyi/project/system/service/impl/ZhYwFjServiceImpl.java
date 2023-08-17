package com.ruoyi.project.system.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.domain.ZhYwFj;
import com.ruoyi.project.system.mapper.SysUserMapper;
import com.ruoyi.project.system.mapper.ZhYwFjMapper;
import com.ruoyi.project.system.service.IZhYwFjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 附件Service业务层处理
 *
 * @author qxd
 * @date 2023-07-19
 */
@Service
public class ZhYwFjServiceImpl implements IZhYwFjService {
    @Autowired
    private ZhYwFjMapper zhYwFjMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询附件
     *
     * @param xh 附件主键
     * @return 附件
     */
    @Override
    public ZhYwFj selectZhYwFjByXh(String xh) {
        return zhYwFjMapper.selectZhYwFjByXh(xh);
    }

    /**
     * 查询附件列表
     *
     * @param zhYwFj 附件
     * @return 附件
     */
    @Override
    public List<ZhYwFj> selectZhYwFjList(ZhYwFj zhYwFj) {
        return zhYwFjMapper.selectZhYwFjList(zhYwFj);
    }

    /**
     * 新增附件
     *
     * @param zhYwFj 附件
     * @return 结果
     */
    @Override
    public int insertZhYwFj(ZhYwFj zhYwFj) {
        SysUser sysUser = sysUserMapper.selectUserById(SecurityUtils.getUserId());
        zhYwFj.setXh(UUID.randomUUID().toString());
        zhYwFj.setScsj(new Date());
        zhYwFj.setCjr(sysUser.getNickName());
        return zhYwFjMapper.insertZhYwFj(zhYwFj);
    }

    /**
     * 修改附件
     *
     * @param zhYwFj 附件
     * @return 结果
     */
    @Override
    public int updateZhYwFj(ZhYwFj zhYwFj) {
        return zhYwFjMapper.updateZhYwFj(zhYwFj);
    }

    /**
     * 批量删除附件
     *
     * @param xhs 需要删除的附件主键
     * @return 结果
     */
    @Override
    public int deleteZhYwFjByXhs(String[] xhs) {
        String uploadPath = RuoYiConfig.getUploadPath();
        if (xhs.length < 2) {
            ZhYwFj zhYwFj = zhYwFjMapper.selectZhYwFjByXh(xhs[0]);
            String lj = zhYwFj.getLj();
            String[] split = lj.split("upload");
            File file = new File(uploadPath + split[1]);
            if (file.isFile()) {
                boolean delete = file.delete();
                if (delete) {
                    zhYwFjMapper.deleteZhYwFjByXhs(xhs);
                    return 1;
                } else {
                    return 500;
                }
            } else {
                return 404;
            }
        } else {
            for (String xh : xhs) {
                ZhYwFj zhYwFj = zhYwFjMapper.selectZhYwFjByXh(xh);
                String lj = zhYwFj.getLj();
                String[] split = lj.split("upload");
                File file = new File(uploadPath + split[1]);
                file.delete();
            }
            return zhYwFjMapper.deleteZhYwFjByXhs(xhs);
        }
    }

    /**
     * 删除附件信息
     *
     * @param xh 附件主键
     * @return 结果
     */
    @Override
    public int deleteZhYwFjByXh(String xh) {
        return zhYwFjMapper.deleteZhYwFjByXh(xh);
    }

    /**
     * 根据id列表获取附件列表
     */
    @Override
    public List<ZhYwFj> selectZhYwFjListByIds(List<ZhYwFj> zhYwFjs) {
        return zhYwFjMapper.selectZhYwFjListByIds(zhYwFjs);
    }
}
