package com.ruoyi.project.question.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.question.domain.*;
import com.ruoyi.project.question.domain.vo.DaccVO;
import com.ruoyi.project.question.domain.vo.NumberVO;
import com.ruoyi.project.question.domain.vo.WtxxVo;
import com.ruoyi.project.question.mapper.QuestionMapper;
import com.ruoyi.project.question.mapper.WtglCjlsMapper;
import com.ruoyi.project.question.service.IWtglCjlsService;
import com.ruoyi.project.question.service.QuestionService;
import com.ruoyi.project.system.domain.SysDept;
import com.ruoyi.project.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.SimpleFormatter;

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
        SysDept sysDept = sysDeptMapper.selectDeptById(SecurityUtils.getDeptId());
        wtglCjls.setCJBMKS(sysDept.getDeptName());
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
    public int insertWtglCjls(WtglCjls wtglCjls) {
        wtglCjls.setLSID(UUID.randomUUID().toString());
        wtglCjls.setWTZT("提交");
        wtglCjls.setBJZRR("被叫责任人");//被叫责任人赋值
        // 提报人赋值
        String nickName = SecurityUtils.getUsername();
        wtglCjls.setTBR(nickName);
        SysDept sysDept = sysDeptMapper.selectDeptById(SecurityUtils.getDeptId());
        wtglCjls.setBJZRKS(sysDept.getDeptName());
        /*查询部门科室，并给创建部门科室赋值*/
        wtglCjls.setCJBMKS(sysDept.getDeptName());
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
     * @param wtglCjlsList  需要修改的对象
     * @param wtzt 问题状态
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
        Date date = new Date();
        for (WtglCjls selectedRow : selectedRows) {
            Date cjsj = selectedRow.getCJSJ();
            LocalDateTime date1 = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            LocalDateTime date2 = LocalDateTime.ofInstant(cjsj.toInstant(), ZoneId.systemDefault());
            long minutesDifference = ChronoUnit.MINUTES.between(date2, date1);
            selectedRow.setTBJSSJ(String.valueOf(minutesDifference) + "分");
        }
        if ("是".equals(whether)) {
            i = wtglCjlsMapper.wtjsYesIt(selectedRows);
        } else {
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
        return wtglCjlsMapper.selectWtglCjlsSpeechList();
    }

    /**
     * 获取接收人列表
     *
     * @param wtlb 问题类别
     * @return 接收人列表
     */
    @Override
    public List<String> getjsrBywtlb(String wtlb) {
//        List<String> jsrList = wtglCjlsMapper.getjsrBywtlb();
        List<String> jsrList = new ArrayList<>();
        jsrList.add("接收人1");
        jsrList.add("接收人2");
        jsrList.add("接收人3");
        jsrList.add("接收人4");
        return jsrList;
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
}
