package com.ruoyi.project.system.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.question.domain.WtxxDTO;
import com.ruoyi.project.question.domain.vo.Sjjh;
import com.ruoyi.project.question.mapper.QuestionMapper;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.domain.ZhYwFj;
import com.ruoyi.project.system.mapper.SysUserMapper;
import com.ruoyi.project.system.mapper.ZhYwFjMapper;
import com.ruoyi.project.system.service.IZhYwFjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
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

    @Autowired
    private QuestionMapper questionMapper;

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
        String wtid = zhYwFj.getId();
        if ("回复数据".equals(zhYwFj.getType())) {
            questionMapper.updatejhjlToHFFJ(zhYwFj.getId(),"是");
            Sjjh sjjh = questionMapper.selectJhjlByXh(zhYwFj.getId());
            wtid = sjjh.getWTID();
        }
        //修改问题的上传附件状态
        WtxxDTO wtxxDTO = new WtxxDTO();
        wtxxDTO.setID(wtid);
        wtxxDTO.setSCFJ("true");
        questionMapper.updateQuestion(wtxxDTO);
        zhYwFj.setScsj(new Date());
        zhYwFj.setCjr(sysUser.getNickName());
        zhYwFj.setCjrid(sysUser.getUserId().toString());
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
                    //删除数据库附件
                    zhYwFjMapper.deleteZhYwFjByXhs(xhs);
                    //判断删除的是问题附件还是交互记录附件
                    int questionNum = questionMapper.countQuestionById(zhYwFj.getId());
                    Sjjh sjjh = questionMapper.selectJhjlByXh(zhYwFj.getId());
                    //如果是问题管理的附件删除
                    if (questionNum != 0 || sjjh != null) {
                        /*获取问题id*/
                        String wtid = zhYwFj.getId();
                        //通过其他表序号查询附件数量
                        int num = zhYwFjMapper.countByXhs(zhYwFj.getId());
                        //如果删除的是交互记录的附件
                        if (sjjh != null) {
                            //如果没有其他附件了则将交互记录的hffj改为否
                            if (num < 1) {
                                questionMapper.updatejhjlToHFFJ(zhYwFj.getId(), "否");
                            }
                            //获取问题id
                            wtid = sjjh.getWTID();
                        }

                        //获取当前问题下交互记录有附件的序号判断数量
                        List<String> ids = new ArrayList<>();
                        ids.add(wtid);
                        List<String> jhjlXhs = questionMapper.selectJhjlByWtidsAndHffj(ids);
                        //如果没有附件则改变问题附件为false
                        if (jhjlXhs.size() == 0 && num == 0) {
                            WtxxDTO wtxxDTO = new WtxxDTO();
                            wtxxDTO.setID(wtid);
                            wtxxDTO.setSCFJ("false");
                            questionMapper.updateQuestion(wtxxDTO);
                        }
                    }
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

    /**
     * 根据其他表id查询附件列表
     */
    @Override
    public List<ZhYwFj> selectFjListByIds(String[] qtbxhs) {
        return zhYwFjMapper.selectFjListByIds(qtbxhs);
    }
}
