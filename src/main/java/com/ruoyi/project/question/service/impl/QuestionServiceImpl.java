package com.ruoyi.project.question.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.question.domain.DaccDTO;
import com.ruoyi.project.question.domain.SjjhDTO;
import com.ruoyi.project.question.domain.WtglSjzrzd;
import com.ruoyi.project.question.domain.WtxxDTO;
import com.ruoyi.project.question.domain.vo.*;
import com.ruoyi.project.question.mapper.QuestionMapper;
import com.ruoyi.project.question.service.QuestionService;
import com.ruoyi.project.system.domain.SysDept;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.mapper.SysDeptMapper;
import com.ruoyi.project.system.mapper.SysUserMapper;
import com.ruoyi.project.system.mapper.ZhYwFjMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: QXD
 * Date: 2023/3/29 11:43
 * Description:
 * Version:
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ZhYwFjMapper fjMapper;

    /**
     * 我的问题查询数据（通过单选框的判断）
     *
     * @param wtxxDTO 包含单选框的值
     * @return 表格的数据集合
     */
    @Override
    public List<WtxxVo> selectWtxxData(WtxxDTO wtxxDTO) {
        Long userId = SecurityUtils.getUserId();
        wtxxDTO.setCJRID(userId + "");
        if ("待办".equals(wtxxDTO.getRadios())) {
            wtxxDTO.setWTZT("提交");
            return questionMapper.selectWtxxData1(wtxxDTO);
        } else if ("待关闭".equals(wtxxDTO.getRadios())) {
            wtxxDTO.setWTZT("待关闭");
            return questionMapper.selectWtxxData1(wtxxDTO);
        } else if ("处理中".equals(wtxxDTO.getRadios())) {
            wtxxDTO.setWTZT("接收");
            wtxxDTO.setWTZT1("反馈");
            return questionMapper.selectWtxxData1(wtxxDTO);
        } else {
            wtxxDTO.setWTZT("已关闭");
            return questionMapper.selectWtxxData1(wtxxDTO);
        }
    }

    /**
     * 得到我的待办的问题列表
     */
    @Override
    public List<WtxxVo> getQuestionList(WtxxDTO wtxxDTO) {
        Long userId = SecurityUtils.getUserId();
        wtxxDTO.setZRRID(userId + "");
        if ("待办".equals(wtxxDTO.getRadios())) {
            wtxxDTO.setWTZT("提交");
            return questionMapper.selectWtxxData2(wtxxDTO);
        } else if ("处理中".equals(wtxxDTO.getRadios())) {
            wtxxDTO.setWTZT("接收");
            wtxxDTO.setWTZT1("反馈");
            wtxxDTO.setWTZT2("待关闭");
            return questionMapper.selectWtxxData2(wtxxDTO);
        } else {
            wtxxDTO.setWTZT("已关闭");
            return questionMapper.selectWtxxData2(wtxxDTO);
        }
    }

    /**
     * 批量删除数据
     *
     * @param ids 删除数据的集合
     */
    @Override
    public List<String> deleteQuestions(List<String> ids) {
        //查询有附件的交互记录
        List<String> xhs = questionMapper.selectJhjlByWtidsAndHffj(ids);
        //查询附件地址
        xhs.addAll(ids);
        List<String> fwjdz = fjMapper.selectFwjdzsByQtbxhs(xhs);
        if (fwjdz.size() != 0) {
            return fwjdz;
        }
        questionMapper.deleteQuestions(ids);//删除问题
        questionMapper.deleteWtgzByWtid(ids);//删除关注数据
        questionMapper.deleteZrdbByIds(ids);//删除责任人
        questionMapper.deleteJhjlByWtids(ids);//删除交互记录
        return fwjdz;
    }


    /**
     * 修改我的问题状态的方法
     */
    @Override
    public void updateQuestionsStatus(List<WtxxDTO> wtxxDTO) {
        questionMapper.updateQuestionsStatus(wtxxDTO);
    }

    /**
     * 修改我的待办页面责任人的状态
     *
     * @param wtxxDTO wtxx dto
     */
    @Override
    public void updateMyDoListStatus(List<WtxxDTO> wtxxDTO) {
        for (WtxxDTO dto : wtxxDTO) {
            dto.setValue("申请已完成");
            dto.setZRRID(SecurityUtils.getUserId() + "");
        }
        questionMapper.updateMyDoListStatus(wtxxDTO);
        List<WtxxDTO> wtxxDTOS = new ArrayList<>();
        for (WtxxDTO dto : wtxxDTO) {
            int num = 0;
            List<ZrrVO> list = questionMapper.getzerData(dto.getID());
            for (ZrrVO zrrVO : list) {
                String zrrzt = zrrVO.getZRRZT();
                if ("申请已完成".equals(zrrzt) || "确认已完成".equals(zrrzt)) {
                    num++;
                }
            }
            if (num == list.size()) {
                dto.setValue("待关闭");
            } else {
                dto.setValue("反馈");
            }
            wtxxDTOS.add(dto);
        }
        if (wtxxDTOS.size() > 0) {
            questionMapper.updateQuestionsStatus(wtxxDTOS);
        }
    }

    /**
     * 获得领导批示的问题
     */
    @Override
    public List<WtxxVo> getLeaderQuestion(WtxxDTO wtxxDTO) {
        wtxxDTO.setLDPS("true");
        if ("待办".equals(wtxxDTO.getRadios())) {
            wtxxDTO.setWTZT("提交");
            wtxxDTO.setWTZT1("接收");
            wtxxDTO.setWTZT2("反馈");
            wtxxDTO.setWTZT3("待关闭");
            return questionMapper.selectWtxxData1(wtxxDTO);
        } else {
            wtxxDTO.setWTZT("已关闭");
            return questionMapper.selectWtxxData1(wtxxDTO);

        }
    }

    /**
     * 批量修改问题
     */
    @Override
    public void updateQuestions(List<WtxxVo> wtxxVos) {
        questionMapper.updateQuestions(wtxxVos);
    }

    /**
     * 获得问题交互记录数据
     */
    @Override
    public List<Sjjh> getProblemInteractionList(Sjjh sjjh) {
        return questionMapper.getProblemInteractionList(sjjh);
    }

    /**
     * 批量更新问题交互数据
     */
    @Override
    public void updateProblemInteraction(List<SjjhDTO> sjjhs) {
        questionMapper.updateProblemInteraction(sjjhs);
    }

    /**
     * 删除问题交互数据
     */
    @Override
    public void deleteProblemInteraction(List<String> ids) {
        questionMapper.deleteProblemInteraction(ids);
    }

    //获取问题来源的方法
    @Override
    public List<String> getwtlyMethod() {
        return questionMapper.getwtlyMethod();
    }

    //获取问题类别数据方法
    @Override
    public List<String> getwtlbMethod() {
        return questionMapper.getwtlbMethod();
    }

    //获取问题细类数据的方法
    @Override
    public List<String> getwtxlMethod(String wtlb) {
        if (wtlb != null) {
            return questionMapper.getwtxlMethod(wtlb);
        } else {
            return new ArrayList<>();
        }
    }

    //获取紧急程度
    @Override
    public List<String> getjjcdMethod() {
        return questionMapper.getjjcdMethod();
    }

    @Override
    public void createQuestion(WtxxDTO wtxxDTO) {
        Long userId = SecurityUtils.getUserId();
        wtxxDTO.setCJRID(userId + "");//创建人id
        SysUser sysUser = sysUserMapper.selectUserById(SecurityUtils.getUserId());
        wtxxDTO.setCJR(sysUser.getNickName());//创建人
        //创建人科室
        //创建人部门
        String id = UUID.randomUUID().toString();
        wtxxDTO.setID(id);
        wtxxDTO.setWTZT("提交");
        wtxxDTO.setCJBMID(SecurityUtils.getDeptId().toString());
        SysDept sysDept = sysDeptMapper.selectDeptById(SecurityUtils.getDeptId());
        wtxxDTO.setCJBM(sysDept.getDeptName());
        List<ZrrVO> zrrVOList = wtxxDTO.getZRRVOLIST();
        if (zrrVOList.size() != 0) {
            for (ZrrVO zrrVO : zrrVOList) {
                zrrVO.setWTID(id);
                zrrVO.setXH(UUID.randomUUID().toString());
            }
            questionMapper.createZRDB(zrrVOList);
        }
        questionMapper.createQuestion(wtxxDTO);
    }

    /**
     * 得到问题交互
     */
    @Override
    public List<WtxxVo> getQuestionInteraction(WtxxDTO wtxxDTO) {
        if ("待办".equals(wtxxDTO.getRadios())) {
            wtxxDTO.setWTZT("提交");
            return questionMapper.selectWtxxData1(wtxxDTO);
        } else if ("处理中".equals(wtxxDTO.getRadios())) {
            wtxxDTO.setWTZT("接收");
            wtxxDTO.setWTZT1("反馈");
            wtxxDTO.setWTZT2("待关闭");
            return questionMapper.selectWtxxData1(wtxxDTO);
        } else {
            wtxxDTO.setWTZT("已关闭");
            return questionMapper.selectWtxxData1(wtxxDTO);
        }
    }

    /**
     * 获得回复预览对应创建人和问题的责任人数据
     */
    @Override
    public List<ZrrVO> getzerData(String wtid) {
        return questionMapper.getzerData(wtid);
    }

    /**
     * 回复预览页面的回复信息保存
     */
    @Override
    public void saveJhjlList(Sjjh sjjh) {
        Long userId = SecurityUtils.getUserId();
        sjjh.setWTZRRID(userId.toString());
        SysUser sysUser = sysUserMapper.selectUserById(userId);
        sjjh.setWTZRR(sysUser.getNickName());
        sjjh.setHFR(sysUser.getNickName());
        sjjh.setHFRID(sysUser.getUserId().toString());
        List<WtxxDTO> wtxxDTOS = new ArrayList<>();
        if (sjjh.getXH() != null && !sjjh.getXH().isEmpty() && !"领导批示".equals(sjjh.getJHZT())) {
            if ("接收".equals(sjjh.getValue()) && sjjh.getUserName() != null) {
                WtxxDTO wtxxDTO = new WtxxDTO();
                wtxxDTO.setID(sjjh.getWTID());
                wtxxDTO.setValue("反馈");
                wtxxDTOS.add(wtxxDTO);
                questionMapper.updateQuestionsStatus(wtxxDTOS);
            }
            String ejhfppyj = UUID.randomUUID() + "";
            Sjjh sjjh1 = questionMapper.selectJhjlByid(sjjh.getXH(), sjjh.getJHZT());
            int updatejhjl = 0;
            if (sjjh1.getEJHFPPYJ() == null || sjjh1.getEJHFPPYJ().isEmpty()) {
                updatejhjl = questionMapper.updatejhjl(sjjh.getXH(), ejhfppyj);
            } else {
                updatejhjl = questionMapper.updatejhjl(sjjh.getXH(), sjjh1.getEJHFPPYJ() + "," + ejhfppyj);
            }
            if (updatejhjl > 0) {
                sjjh.setXH(ejhfppyj);
                questionMapper.saveJhjlList(sjjh);
            }
        } else if (!"领导批示".equals(sjjh.getJHZT())) {
            if ("接收".equals(sjjh.getValue()) && sjjh.getUserName() != null) {
                WtxxDTO wtxxDTO = new WtxxDTO();
                wtxxDTO.setID(sjjh.getWTID());
                wtxxDTO.setValue("反馈");
                wtxxDTOS.add(wtxxDTO);
                questionMapper.updateQuestionsStatus(wtxxDTOS);
            }
            sjjh.setXH(UUID.randomUUID() + "");
            questionMapper.saveJhjlList(sjjh);
        } else {
            sjjh.setXH(UUID.randomUUID() + "");
            questionMapper.saveJhjlList(sjjh);
            updateQuestionWddb(sjjh);
            updateQuestionWdwt(sjjh);
        }
    }

    /**
     * 问题关注的方法
     */
    @Override
    public void savegzwtList(List<String> ids) {
        List<WtgzVo> wtgzVo = new ArrayList<>();
        for (String id : ids) {
            WtgzVo wt = new WtgzVo();
            wt.setWTID(id);
            wt.setCJRID(SecurityUtils.getUserId() + "");
            wtgzVo.add(wt);
        }
        questionMapper.savegzwtList(wtgzVo);
    }

    /**
     * 获得我的关注问题列表
     */
    @Override
    public List<WtxxVo> getMyProblemList(WtxxDTO wtxxDTO) {
        wtxxDTO.setCJRID(SecurityUtils.getUserId() + "");
        if ("处理中".equals(wtxxDTO.getRadios())) {
            wtxxDTO.setWTZT("提交");
            wtxxDTO.setWTZT1("接收");
            wtxxDTO.setWTZT2("反馈");
            wtxxDTO.setWTZT3("待关闭");
            return questionMapper.getMyProblemList(wtxxDTO);
        } else {
            wtxxDTO.setWTZT("已关闭");
            return questionMapper.getMyProblemList(wtxxDTO);
        }
    }

    /**
     * 取消关注的方法
     */
    @Override
    public void deletegzwtList(List<String> ids) {
        List<WtgzVo> wtgzVos = new ArrayList<>();
        for (String id : ids) {
            WtgzVo wt = new WtgzVo();
            wt.setCJRID(SecurityUtils.getUserId() + "");
            wt.setWTID(id);
            wtgzVos.add(wt);
        }
        questionMapper.deletegzwtList(wtgzVos);
    }

    /**
     * 得到问题的回复数据
     */
    @Override
    public List<Sjjh> getJhjl(String wtid, String jhzt) {
        if ("领导批示".equals(jhzt)) {
            List<Sjjh> sjjhList = questionMapper.selectJhjlByWtid(wtid, jhzt);
            return sjjhList;
        } else {
            List<Sjjh> sjjhList = questionMapper.selectJhjlByWtid(wtid, jhzt);
            List<Sjjh> sjjhResult = new ArrayList<>();
            for (Sjjh sjjh : sjjhList) {
                if ("1".equals(sjjh.getJS())) {
                    sjjhResult.add(sjjh);
                }
            }
            for (Sjjh sjjh : sjjhResult) {
                if (sjjh.getEJHFPPYJ() != null && !"".equals(sjjh.getEJHFPPYJ())) {
                    zhengli(sjjh, jhzt, sjjhList);
                }
            }
            return sjjhResult;
        }

    }

    /**
     * 得到领导批示的数据
     */
    @Override
    public List<Sjjh> getLDPS(String wtid, String jhzt) {
        List<Sjjh> sjjhList = questionMapper.selectldpsByWtid(wtid, jhzt);
        return sjjhList;
    }

    /**
     * 交互数据的递归
     */
    private void zhengli(Sjjh sjjh, String jhzt, List<Sjjh> sjjhs) {
        String[] split = sjjh.getEJHFPPYJ().split(",");
        for (String s : split) {
            Sjjh sj = null;
            for (Sjjh sjjh1 : sjjhs) {
                if (sjjh1.getXH().equals(s) && jhzt.equals(sjjh1.getJHZT())) {
                    sj = sjjh1;
                }
            }
            if (sj != null && sj.getEJHFPPYJ() != null && !"".equals(sj.getEJHFPPYJ())) {
                zhengli(sj, jhzt, sjjhs);
                sjjh.setSjjhs(sj);
            } else {
                sjjh.setSjjhs(sj);
            }
        }
    }

    /**
     * 责任人闭环功能
     */
    @Override
    public void closedLoop(ZrrVO zrrVO) {
        questionMapper.closedLoop(zrrVO.getXH());
        List<ZrrVO> zrrVOS = questionMapper.getzerData(zrrVO.getWTID());
        int num = 0;
        WtxxDTO dto = new WtxxDTO();
        dto.setID(zrrVO.getWTID());
        for (ZrrVO zrr : zrrVOS) {
            String zrrzt = zrr.getZRRZT();
            if ("申请已完成".equals(zrrzt) || "确认已完成".equals(zrrzt)) {
                num++;
            }
        }
        if (num == zrrVOS.size()) {
            dto.setValue("待关闭");
        } else {
            dto.setValue("反馈");
        }

        List<WtxxDTO> wtxxDTOS = new ArrayList<>();
        wtxxDTOS.add(dto);
        questionMapper.updateQuestionsStatus(wtxxDTOS);
    }

    /**
     * 责任人激活功能
     */
    @Override
    public void activate(String xh) {
        questionMapper.activate(xh);
    }

    /**
     * 更新问题状态
     */
    @Override
    public void updateWtztEnd(WtxxDTO wtxxDTO) {
        if ("提交".equals(wtxxDTO.getWTZT())) {
            List<WtxxDTO> wtxxDTOS = new ArrayList<>();
            WtxxDTO wtxx = new WtxxDTO();
            wtxx.setID(wtxxDTO.getID());
            wtxx.setValue("接收");
            wtxx.setJSSJ(new Date());
            wtxxDTOS.add(wtxx);
            questionMapper.updateQuestionsStatus(wtxxDTOS);
        }
    }

    //获取问题编号的方法
    @Override
    public String getwtbhMethod() {
        //先查询今天的年月日
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = simpleDateFormat.format(new Date());
        //获取数据库中问题编号最新的问题编号
        String wtbh = questionMapper.selectMaxWtbh(format + "001");
        if (wtbh == null) {
            return format + "001";
        } else {
            String suffix = wtbh.substring(8);
            DecimalFormat d = new DecimalFormat("000");
            return format + d.format(Integer.parseInt(suffix) + 1);
        }
    }

    /**
     * 计算问题天数
     */
    @Override
    public void problemDays() {
        List<WtxxVo> wtxxVos = questionMapper.selectWtxxData();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = simpleDateFormat.format(new Date());
        for (WtxxVo wtxxVo : wtxxVos) {
            if ("已关闭".equals(wtxxVo.getWTZT())) {
                Date gbsj = wtxxVo.getGBSJ();
                String format = simpleDateFormat.format(gbsj);
                try {
                    Date parse = simpleDateFormat.parse(format);
                    Date parse1 = simpleDateFormat.parse(currentTime);
                    int i = differentDays(parse, parse1);
                    wtxxVo.setWCTS(i + "");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            Date cjsj = wtxxVo.getCJSJ();
            String format = simpleDateFormat.format(cjsj);
            try {
                Date parse = simpleDateFormat.parse(format);
                Date parse1 = simpleDateFormat.parse(currentTime);
                int i = differentDays(parse, parse1);
                wtxxVo.setWWCTS(i + "");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date xwjjsj = wtxxVo.getXWJJSJ();
            String xwjjsjTime = simpleDateFormat.format(xwjjsj);
            try {
                Date parse = simpleDateFormat.parse(xwjjsjTime);
                Date parse1 = simpleDateFormat.parse(currentTime);
                int i = differentDays(parse1, parse);
                wtxxVo.setCQTS(i + "");
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        questionMapper.updateQuestions(wtxxVos);
    }

    /**
     * 删除回复数据
     */
    @Override
    public void deleteJhjlByXh(String id) {
        //删除这个数据的所有父节点的指向子节点的数据
        List<Sjjh> sjjhs = questionMapper.selectJhjlByEjhfppyj(id);
        if (sjjhs.size() == 0) {
            questionMapper.deleteJhjlByXh(id);
            return;
        } else {
            for (Sjjh sjjh : sjjhs) {
                String[] split = sjjh.getEJHFPPYJ().split(",");
                if (split.length > 1) {
                    StringJoiner newEjhfppyj = new StringJoiner(",");
                    for (String s1 : split) {
                        if (!s1.equals(id)) {
                            newEjhfppyj.add(s1);
                        }
                    }
                    questionMapper.updatejhjlToAll(sjjh.getXH(), newEjhfppyj.toString());
                } else {
                    questionMapper.updatejhjlToAll(sjjh.getXH(), null);
                }
                break;
            }
        }
        questionMapper.deleteJhjlByXh(id);
    }

    /**
     * 修改问题表的我的问题待办数量
     */
    @Override
    public void updateQuestionWddb(Sjjh sjjh) {
        WtxxDTO wtxxDTO = new WtxxDTO();
        wtxxDTO.setID(sjjh.getWTID());
        wtxxDTO.setWTDB(1);
        questionMapper.updateQuestionWddb(wtxxDTO);
    }

    /**
     * 修改问题表的我的待办待办数量
     */
    @Override
    public void updateQuestionWdwt(Sjjh sjjh) {
        WtxxDTO wtxxDTO = new WtxxDTO();
        wtxxDTO.setID(sjjh.getWTID());
        wtxxDTO.setWDWT(1);
        questionMapper.updateQuestionWdwt(wtxxDTO);
    }

    /**
     * 将我的问题待办数量变成0
     */
    @Override
    public void updateQuestionWTDBToZero(WtxxDTO wtxxDTO) {
        questionMapper.updateQuestionWTDBToZero(wtxxDTO.getID());
    }

    /**
     * 将我的问题待办数量变成0
     */
    @Override
    public void updateQuestionWDWTToZero(WtxxDTO wtxxDTO) {
        questionMapper.updateQuestionWDWTToZero(wtxxDTO.getID());
    }

    /**
     * 根据问题id查询交互数据
     */
    @Override
    public List<SjjhDTO> selectJhjlList(WtxxDTO wtxxDTO) {
        return questionMapper.selectJhjlList(wtxxDTO);
    }

    /**
     * 选择回答
     */
    @Override
    public void answerSelectOff(DaccDTO daccDTO) {
        List<SjjhDTO> sjjh = daccDTO.getSjjhDTOS();
        //关闭问题
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        //给当前问题修改为已关闭状态
        questionMapper.updateQuestionStatusSJ(daccDTO.getWTID(), "已关闭", format);
        DaccVO daccVO = new DaccVO();
        for (SjjhDTO sjjhDTO : sjjh) {
            daccVO.setWTID(sjjhDTO.getWTID());
            daccVO.setCJRID(sjjhDTO.getWTCJID());
            daccVO.setCJR(sjjhDTO.getWTCJR());
            daccVO.setWTMC(sjjhDTO.getWTBT());
            daccVO.setGBSJ(format);
            daccVO.setRD(0);
            daccVO.setCPXH(daccDTO.getCPXH());
            daccVO.setJH(daccDTO.getJH());
            daccVO.setWTLB(daccDTO.getWTLB());
            daccVO.setWTXL(daccDTO.getWTXL());
            daccVO.setGXH(daccDTO.getGXH());
            daccVO.setGXMC(daccDTO.getGXH());
            daccVO.setWTMS(daccDTO.getWTMS());
            daccVO.setDAXXID(UUID.randomUUID() + "");
            if (daccVO.getDAXX() != null) {
                daccVO.setDAXX(daccVO.getDAXX() + "$$" + sjjhDTO.getHFXX());
            } else {
                daccVO.setDAXX(sjjhDTO.getHFXX());
            }
        }
        if (daccVO.getWTID() != null) {
            List<ZrrVO> zrrVOS = questionMapper.getzerData(daccVO.getWTID());
            String zrr = "";
            String zrrId = "";
            for (ZrrVO zrrVO : zrrVOS) {
                if ("".equals(zrr)) {
                    zrr = zrrVO.getZRR();
                    zrrId = zrrVO.getZRRID();
                } else {
                    zrr = zrr + "," + zrrVO.getZRR();
                    zrrId = zrrId + "," + zrrVO.getZRRID();
                }
            }
            daccVO.setZRR(zrr);
            daccVO.setZRRID(zrrId);
            daccVO.setDEPTID(SecurityUtils.getDeptId().toString());
            questionMapper.saveDaccList(daccVO);
        }
        //修改所有的责任人为确定已完成
        //先找出wtid的数据
        String wtid = daccDTO.getWTID();
        List<ZrrVO> zrrVOS = questionMapper.getzerData(wtid);
        for (ZrrVO zrrVO : zrrVOS) {
            if ("申请已完成".equals(zrrVO.getZRRZT())) {
                zrrVO.setZRRZT("确定已完成");
                questionMapper.updateZRDBData(zrrVO);
            }
        }
    }

    /**
     * 明确反馈记录
     */
    @Override
    public void clearFeedbackRecord() {
        questionMapper.updateQuestionSFDZFK();
    }

    /**
     * 激活后问题变成反馈状态
     */
    @Override
    public void updateQuestionStatus(WtxxDTO wtxxDTO) {
        questionMapper.updateQuestionStatus(wtxxDTO.getID(), wtxxDTO.getValue());
    }

    /**
     * 根据id获得我的问题数据
     */
    @Override
    public WtxxVo getYwcj(String id) {
        return questionMapper.getYwcj(id);
    }

    /**
     * 更新问题
     */
    @Override
    public void updateQuestion(WtxxDTO wtxxDTO) {
        questionMapper.updateQuestion(wtxxDTO);
    }

    /**
     * 根据id查询交互数据
     */
    @Override
    public Sjjh getJHJL(String xh) {
        return questionMapper.selectJhjlByXh(xh);
    }

    /**
     * 根据id更新交互数据
     */
    @Override
    public void updateJHJL(SjjhDTO sjjhDTO) {
        questionMapper.updateJHJL(sjjhDTO);
    }

    /**
     * 根据问题id查询所有交互数据
     */
    @Override
    public List<Sjjh> getJhjlByWtid(String wtid) {
        List<Sjjh> jhjlByWtid = questionMapper.getJhjlByWtid(wtid);
        List<Sjjh> sjjhResult = new ArrayList<>();
        for (Sjjh sjjh : jhjlByWtid) {
            if ("1".equals(sjjh.getJS())) {
                sjjhResult.add(sjjh);
            }
        }
        for (Sjjh sjjh : sjjhResult) {
            if (sjjh.getEJHFPPYJ() != null && !"".equals(sjjh.getEJHFPPYJ())) {
                zhengli(sjjh, sjjh.getJHZT(), jhjlByWtid);
            }
        }
        return sjjhResult;
    }

    /**
     * 获得阅知人的问题信息
     */
    @Override
    public List<WtxxVo> getQuestionYzrList(WtxxDTO wtxxDTO) {
        wtxxDTO.setYZRID(SecurityUtils.getUserId() + "");
        wtxxDTO.setYZRBMID(SecurityUtils.getDeptId() + "");
        if ("待办".equals(wtxxDTO.getRadios())) {
            wtxxDTO.setWTZT("提交");
            return questionMapper.selectWtxxData1(wtxxDTO);
        } else if ("处理中".equals(wtxxDTO.getRadios())) {
            wtxxDTO.setWTZT3("提交");
            wtxxDTO.setWTZT("接收");
            wtxxDTO.setWTZT1("反馈");
            wtxxDTO.setWTZT2("待关闭");
            return questionMapper.selectWtxxData1(wtxxDTO);

        } else {
            wtxxDTO.setWTZT("已关闭");
            return questionMapper.selectWtxxData1(wtxxDTO);
        }
    }


    //用来计算两个日期之间的天数date1是小数值的，data2是大数值的
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) //闰年
                {
                    timeDistance += 366;
                } else //不是闰年
                {
                    timeDistance += 365;
                }
            }
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return timeDistance + (day2 - day1);
        } else //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }

    @Autowired
    private WtglSjzrzdServiceImpl wtglSjzrzdService;

    /**
     * 升级责任人的定时任务的执行方法
     */
    @Override
    public void shengjizrr() {
        //先获得我的问题表数据（数据中问题升级为true的数据选中出来）
        List<WtxxVo> wtxxVos = questionMapper.selectWtxxByWtsj();
        /*if (wtxxVos.size() > 0) {
            //获得该wtid的责任人变成没有主责任人
            questionMapper.updateZrdb(wtxxVos);
        }*/
        for (WtxxVo wtxxVo : wtxxVos) {
            List<ZrrVO> list1 = questionMapper.getzerData(wtxxVo.getID());
            Set<String> ids = new HashSet<>();
            for (ZrrVO zrrVO : list1) {
                String zrrid = zrrVO.getZRRID();
                //查询升级人字典表数据
                WtglSjzrzd wtglSjzrzd = new WtglSjzrzd();
                wtglSjzrzd.setDqzerid(zrrid);
                List<WtglSjzrzd> wtglSjzrzds = wtglSjzrzdService.selectWtglSjzrzdList(wtglSjzrzd);
                if (wtglSjzrzds.size() != 1) {
                    if (wtglSjzrzds.size() == 0) {
                        System.out.println("这个数据没有下一级了，升级取消");
                    } else {
                        System.out.println("请联系管理员，查看字典数据，是否一个升级人的二级升级人为多个升级人");
                    }
                    continue;
                }
                String yjzrrid = wtglSjzrzds.get(0).getYjzrrid();
                ids.add(yjzrrid);
            }
            for (ZrrVO zrrVO : list1) {
                Iterator<String> iterator = ids.iterator();
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    if (next.equals(zrrVO.getZRRID())) {
                        iterator.remove();
                    }
                }
            }
            if (ids.size() == 0) {
                WtxxDTO wtxxDTO = new WtxxDTO();
                wtxxDTO.setID(wtxxVo.getID());
                wtxxDTO.setWTSJ("false");
                questionMapper.updateQuestion(wtxxDTO);
            }
            for (String id : ids) {
                //获取升级责任人的用户数据
                SysUser sysUser = sysUserMapper.selectUserById(Long.parseLong(id));
                SysDept sysDept = sysDeptMapper.selectDeptById(sysUser.getDeptId());
                //插入新的责任人数据
                List<ZrrVO> list = new ArrayList<>();
                ZrrVO zrrVO = new ZrrVO();
                zrrVO.setWTID(wtxxVo.getID());
                zrrVO.setSFZZRR("true");
                zrrVO.setXH(UUID.randomUUID().toString());
                zrrVO.setZRR(sysUser.getNickName());
                zrrVO.setZRRID(sysUser.getUserId() + "");
                zrrVO.setZRBM(sysDept.getDeptName());
                zrrVO.setZRBMID(sysUser.getDeptId() + "");
                zrrVO.setZRRZT("未处理");
                list.add(zrrVO);
                questionMapper.createZRDB(list);
            }

        }
    }

    /**
     * 修改是否当周反馈
     */
    @Override
    public int updateSfdzfk(String id) {
        return questionMapper.updateSfdzfk(id);
    }
}
