package com.ruoyi.project.question.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.question.domain.*;
import com.ruoyi.project.question.domain.vo.DaccVO;
import com.ruoyi.project.question.domain.vo.NumberVO;
import com.ruoyi.project.question.domain.vo.WtlbXlVO;
import com.ruoyi.project.question.domain.vo.WtxxVo;
import com.ruoyi.project.question.mapper.QuestionMapper;
import com.ruoyi.project.question.mapper.WtglCjlsMapper;
import com.ruoyi.project.question.mapper.WtlbRyMapper;
import com.ruoyi.project.question.service.IWtglCjlsService;
import com.ruoyi.project.system.domain.SysDept;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.mapper.SysDeptMapper;
import com.ruoyi.project.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 提出问题Service业务层处理
 *
 * @author LiHai
 * @date 2023-06-19
 */
@Service
public class WtglCjlsServiceImpl implements IWtglCjlsService {
    @Autowired
    private WtglCjlsMapper wtglCjlsMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private WtlbRyMapper wtlbRyMapper;

    /**
     * 查询提出问题
     *
     * @param LSID 提出问题主键
     * @return 提出问题
     */
    @Override
    public WtglCjls selectWtglCjlsByLSID(String LSID) {
        return wtglCjlsMapper.selectWtglCjlsByLSID(LSID);
    }

    /**
     * 查询提出问题列表
     *
     * @param wtglCjls 提出问题
     * @return 提出问题
     */
    @Override
    public List<WtglCjls> selectWtglCjlsList(WtglCjlsDTO wtglCjls) {
        if (SecurityUtils.getUserId() != 1) {
            SysDept sysDept = sysDeptMapper.selectDeptById(SecurityUtils.getDeptId());
            wtglCjls.setCJBMKS(sysDept.getDeptName());
        }
        return wtglCjlsMapper.selectWtglCjlsList(wtglCjls);
    }

    /**
     * 查询接收问题列表
     *
     * @param wtglCjls 提出问题
     * @return 提出问题
     */
    @Override
    public List<WtglCjls> selectWtglDownCjlsList(WtglCjls wtglCjls) {
        SysDept sysDept = sysDeptMapper.selectDeptById(SecurityUtils.getDeptId());
        wtglCjls.setCJBMKS(sysDept.getDeptName());
        return wtglCjlsMapper.selectWtglDownCjlsList(wtglCjls);
    }

    /**
     * 新增提出问题
     *
     * @param wtglCjls 提出问题
     * @return 结果
     */
    @Override
    public synchronized int insertWtglCjls(WtglCjls wtglCjls) {
        wtglCjls.setLSID(UUID.randomUUID().toString());
        wtglCjls.setWTZT("提交");
        WtlbRy bjr = wtglCjlsMapper.selectBjrByWtlbAndDeptId(wtglCjls, SecurityUtils.getDeptId().toString());
        //如果没有被叫人
        if (bjr == null) {
            return 0;
        }
        wtglCjls.setBJZRR(bjr.getBJRXM());
        wtglCjls.setBJRID(bjr.getBJRID());
        //获取登录用户
        SysUser sysUser = sysUserMapper.selectUserById(SecurityUtils.getUserId());
        wtglCjls.setTBR(sysUser.getNickName());//提报人赋值
        wtglCjls.setTBRID(sysUser.getUserId().toString());//提报人ID赋值

        //获取登录用户的部门
        SysDept sysDept = sysDeptMapper.selectDeptById(SecurityUtils.getDeptId());
        wtglCjls.setBJZRKS(sysDept.getDeptName());//被叫责任科室设为本部门
        wtglCjls.setCJBMKS(sysDept.getDeptName());//创建部门科室赋值

        //先查询今天的年月日
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = simpleDateFormat.format(new Date());
        //获取数据库中问题编号最新的问题编号
        String wtbh = wtglCjlsMapper.selectWtbh(format);
        if (wtbh == null) {
            wtglCjls.setWTBH("X" + format + "001");
        } else {
            String suffix = wtbh.substring(9);
            DecimalFormat d = new DecimalFormat("000");
            wtglCjls.setWTBH("X" + format + d.format(Integer.parseInt(suffix) + 1));
        }
        return wtglCjlsMapper.insertWtglCjls(wtglCjls);
    }

    /**
     * 修改提出问题
     *
     * @param wtglCjls 提出问题
     * @return 结果
     */
    @Override
    public int updateWtglCjls(WtglCjls wtglCjls) {
        return wtglCjlsMapper.updateWtglCjls(wtglCjls);
    }

    /**
     * 批量删除提出问题
     *
     * @param LSIDs 需要删除的提出问题主键
     * @return 结果
     */
    @Override
    public int deleteWtglCjlsByLSIDs(String[] LSIDs) {
        return wtglCjlsMapper.deleteWtglCjlsByLSIDs(LSIDs);
    }

    /**
     * 删除提出问题信息
     *
     * @param LSID 提出问题主键
     * @return 结果
     */
    @Override
    public int deleteWtglCjlsByLSID(String LSID) {
        return wtglCjlsMapper.deleteWtglCjlsByLSID(LSID);
    }

    /**
     * 查询问题列表
     *
     * @param wtglCjls 参数
     * @return 列表
     */
    @Override
    public List<WtxxVo> selectywcjList(WtglCjls wtglCjls) {
        SysDept sysDept = sysDeptMapper.selectDeptById(SecurityUtils.getDeptId());
        wtglCjls.setCJBMKS(sysDept.getDeptName());
        List<WtxxVo> list = wtglCjlsMapper.selectywcjList(wtglCjls);
        List<String> ids = new ArrayList<>();
        for (WtxxVo wtxxVo : list) {
            ids.add(wtxxVo.getID());
        }
        List<String> sfgzIds = wtglCjlsMapper.getsfgzIdsByIds(ids);
        for (WtxxVo wtxxVo : list) {
            wtxxVo.setSfgz(false);
            for (String sfgzId : sfgzIds) {
                if (wtxxVo.getID().equals(sfgzId)) {
                    wtxxVo.setSfgz(true);
                }
            }
        }
        return list;
    }

    /**
     * 修改问题状态
     *
     * @param wtglCjlsList 需要修改的对象
     * @param wtzt         问题状态
     * @return 结果
     */
    @Override
    public int changewtzt(List<WtglCjls> wtglCjlsList, String wtzt) {
        List<DaccVO> daccVOS = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        for (WtglCjls wtglCjls : wtglCjlsList) {
            ids.add(wtglCjls.getLSID());
            if ("确认已完成".equals(wtzt)) {
                DaccVO daccVO = new DaccVO();
                daccVO.setWTID(wtglCjls.getLSID());
                daccVO.setCJRID(wtglCjls.getTBRID());
                daccVO.setCJR(wtglCjls.getTBR());
                daccVO.setWTMC(wtglCjls.getWTMC());
                daccVO.setGBSJ(format);
                daccVO.setRD(0);
                daccVO.setZRR(wtglCjls.getBJZRR());
                daccVO.setCPXH(wtglCjls.getCPXH());
                daccVO.setJH(wtglCjls.getJH());
                daccVO.setWTLB(wtglCjls.getWTLB());
                daccVO.setWTXL(wtglCjls.getWTXL());
                daccVO.setGXH(wtglCjls.getGXH());
                daccVO.setGXMC(wtglCjls.getGXMC());
                daccVO.setWTMS(wtglCjls.getWTMS());
                daccVO.setDAXX(wtglCjls.getJJFA());
                daccVO.setDAXXID(UUID.randomUUID().toString());
                daccVO.setDEPTID(SecurityUtils.getDeptId().toString());
                daccVOS.add(daccVO);
                questionMapper.saveDaccList(daccVO);
            }
            /*if (daccVOS.size()>0) {
                questionMapper.saveDaccList(daccVOS);
            }*/
        }
        return wtglCjlsMapper.changewtzt(ids, wtzt);
    }

    /**
     * 问题接收
     *
     * @param selectedRows 需要修改的数据
     * @param whether      是否为责任人
     * @return 结果
     */
    @Override
    public int wtjsIt(List<WtglCjls> selectedRows, String whether) {
        int i = 0;
        //设置时间
        Date date = new Date();
        for (WtglCjls selectedRow : selectedRows) {
            Date cjsj = selectedRow.getCJSJ();
            LocalDateTime date1 = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            LocalDateTime date2 = LocalDateTime.ofInstant(cjsj.toInstant(), ZoneId.systemDefault());
            long minutesDifference = ChronoUnit.MINUTES.between(date2, date1);
            selectedRow.setTBJSSJ(String.valueOf(minutesDifference) + "分");
        }
        //是责任人接收
        if ("是".equals(whether)) {
            i = wtglCjlsMapper.wtjsYesIt(selectedRows);
        } else {
            //不是责任人接收
            i = wtglCjlsMapper.wtjsNoIt(selectedRows);
        }
        return i;
    }

    /**
     * 修改解决方案
     *
     * @param id  id
     * @param str 方案
     * @return 结果
     */
    @Override
    public int updatejjfa(String id, String str) {
        return wtglCjlsMapper.updatejjfa(id, str);
    }

    /**
     * 获取完成、未完成数量
     *
     * @return 数量
     */
    @Override
    public List<NumberVO> getwcsl() {
        SysDept sysDept = sysDeptMapper.selectDeptById(SecurityUtils.getDeptId());
        return wtglCjlsMapper.getwtztNumber(sysDept.getDeptName());
    }

    /**
     * 获取部门科室数量
     *
     * @return 数量
     */
    @Override
    public List<NumberVO> getcjksbmNumber() {
        return wtglCjlsMapper.getcjksbmNumber();
    }

    /**
     * 查询问题喊话的列表
     *
     * @return 列表
     */
    @Override
    public List<WtglCjls> selectWtglCjlsSpeechList() {
        SysDept sysDept = sysDeptMapper.selectDeptById(SecurityUtils.getDeptId());
        return wtglCjlsMapper.selectWtglCjlsSpeechList(sysDept.getDeptName());
    }

    /**
     * 获取接收人列表
     *
     * @param wtlbRy 问题类别
     * @return 接收人列表
     */
    @Override
    public List<SysUser> getjsrBywtlb(WtlbRy wtlbRy) {
        wtlbRy.setDEPTID(SecurityUtils.getDeptId().toString());
        List<WtlbRy> wtlbRyList = wtlbRyMapper.selectWtlbRyList(wtlbRy);//获取问题类别人员对照数据
        String[] jsrIds = wtlbRyList.get(0).getJSRID().split(",");//获取接收人id列表
        List<SysUser> sysUser = wtglCjlsMapper.selectUserByDeptId(SecurityUtils.getDeptId());//获取当前部门下所有人员
        //返回接收人列表
        List<SysUser> list = new ArrayList<>();
        for (SysUser user : sysUser) {
            for (String jsrId : jsrIds) {
                if (jsrId.equals(user.getUserId().toString())) {
                    list.add(user);
                }
            }
        }
        return list;
    }

    /**
     * 查询答案信息列表
     */
    @Override
    public List<WtglDacc> listDacc(WtglDacc wtglDacc) {
        return wtglCjlsMapper.listDacc(wtglDacc);
    }

    /**
     * 获取答案信息详细信息
     */
    @Override
    public WtglDacc getDacc(String daxxID) {
        return wtglCjlsMapper.getDacc(daxxID);
    }

    /**
     * 修改答案信息
     */
    @Override
    public int updateDacc(WtglDacc wtglDacc) {
        System.out.println(wtglDacc);
        return wtglCjlsMapper.updateDacc(wtglDacc);
    }

    /**
     * 删除答案信息
     */
    @Override
    public int delDacc(String[] daccIds) {
        return wtglCjlsMapper.delDacc(daccIds);
    }

    /**
     * 查询业务字典列表
     */
    @Override
    public List<Ywzd> listYwzd(Ywzd ywzd) {
        return wtglCjlsMapper.listYwzd(ywzd);
    }

    /**
     * 新增业务字典
     */
    @Override
    public int addYwzd(Ywzd ywzd) {
        ywzd.setXH(UUID.randomUUID().toString());
        return wtglCjlsMapper.addYwzd(ywzd);
    }

    /**
     * 获取业务字典详细信息
     */
    @Override
    public Ywzd getYwzd(String xh) {
        return wtglCjlsMapper.getYwzdByXH(xh);
    }

    /**
     * 修改业务字典
     */
    @Override
    public int updateYwzd(Ywzd ywzd) {
        return wtglCjlsMapper.updateYwzd(ywzd);
    }

    /**
     * 删除业务字典
     */
    @Override
    public int delYwzd(String[] xhs) {
        return wtglCjlsMapper.delYwzd(xhs);
    }

    /**
     * 智能查询
     */
    @Override
    public List<WtglDacc> listDaccToZN(WtglDacc wtglDacc) {
        wtglDacc.setDEPTID(SecurityUtils.getDeptId().toString());
        return wtglCjlsMapper.listDaccToZN(wtglDacc);
    }

    /**
     * 根据答案表的id增加热度
     */
    @Override
    public int updateDaccToRd(String daxxid) {
        return wtglCjlsMapper.updateDaccToRd(daxxid);
    }

    /**
     * 查询提出问题按钮列表
     */
    @Override
    public List<UpButton> getUpButtons() {
        return wtglCjlsMapper.getUpButtons();
    }

    /**
     * 查询提出问题按钮
     */
    @Override
    public UpButton getUpButton(String xh) {
        return wtglCjlsMapper.getUpButton(xh);
    }

    /**
     * 新增提出问题按钮
     */
    @Override
    public int addUpButton(UpButton upButton) {
        upButton.setXH(UUID.randomUUID().toString());
        return wtglCjlsMapper.addUpButton(upButton);
    }

    /**
     * 修改提出问题按钮
     */
    @Override
    public int updateUpButton(UpButton upButton) {
        return wtglCjlsMapper.updateUpButton(upButton);
    }

    /**
     * 删除提出问题按钮
     */
    @Override
    public int delangl(String[] xhs) {
        return wtglCjlsMapper.delangl(xhs);
    }

    /**
     * 获取问题类别细类
     */
    @Override
    public List<WtlbXlVO> getWtlbXlList() {
        List<WtlbXlVO> list = wtglCjlsMapper.getWtlbXlList();
        return list;
    }

    /**
     * 新增提出问题
     *
     * @param wtglCjls 提出问题
     * @return 结果
     */
    public int test(WtglCjls wtglCjls) {
//        questionMapper.selectWTByisWtsj();


        wtglCjls.setLSID(UUID.randomUUID().toString());
        wtglCjls.setWTZT("提交");
        //获取登录用户
        SysUser sysUser = sysUserMapper.selectUserById(SecurityUtils.getUserId());
        wtglCjls.setTBR(sysUser.getNickName());//提报人赋值
        wtglCjls.setTBRID(sysUser.getUserId().toString());//提报人ID赋值
        //获取本部门的部门领导
        List<SysUser> ldUsers = wtglCjlsMapper.selectUserByPostAndDept("部门领导", sysUser.getDeptId());
        if (ldUsers.size() == 0) {
            ldUsers.add(SecurityUtils.getLoginUser().getUser());
        }
        wtglCjls.setBJZRR(ldUsers.get(0).getNickName());//被叫责任人赋值为部门领导
        wtglCjls.setBJRID(ldUsers.get(0).getUserId().toString());//被叫人ID赋值为部门领导ID
        //获取登录用户的部门
        SysDept sysDept = sysDeptMapper.selectDeptById(SecurityUtils.getDeptId());
        wtglCjls.setBJZRKS(sysDept.getDeptName());//被叫责任科室设为本部门
        wtglCjls.setCJBMKS(sysDept.getDeptName());//创建部门科室赋值
        return wtglCjlsMapper.insertWtglCjls(wtglCjls);
    }
}
