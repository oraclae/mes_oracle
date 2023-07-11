package com.ruoyi.project.question.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.question.domain.DaccDTO;
import com.ruoyi.project.question.domain.SjjhDTO;
import com.ruoyi.project.question.domain.WtxxDTO;
import com.ruoyi.project.question.domain.vo.*;
import com.ruoyi.project.question.mapper.QuestionMapper;
import com.ruoyi.project.question.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    /**
     * 我的问题查询数据（通过单选框的判断）
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
        wtxxDTO.setZRRID(userId+"");
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
     * @param ids 删除数据的集合
     */
    @Override
    public void deleteQuestions(List<String> ids) {
        questionMapper.deleteQuestions(ids);
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
        questionMapper.updateQuestionsStatus(wtxxDTO);
        for (WtxxDTO dto : wtxxDTO) {
            dto.setValue("申请已完成");
            dto.setZRRID(SecurityUtils.getUserId() + "");
        }
        questionMapper.updateMyDoListStatus(wtxxDTO);
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
        return questionMapper.getwtxlMethod(wtlb);
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
        wtxxDTO.setCJR(SecurityUtils.getUsername());//创建人

        //创建人科室
        //创建人部门
        wtxxDTO.setID(UUID.randomUUID().toString());
        wtxxDTO.setWTZT("提交");
        questionMapper.createQuestion(wtxxDTO);
    }

    /**
     * 得到问题交互
     */
    @Override
    public List<WtxxVo> getQuestionInteraction(WtxxDTO wtxxDTO) {
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
        sjjh.setWTZRRID(SecurityUtils.getUserId() + "");
        sjjh.setWTZRR(SecurityUtils.getUsername());
        sjjh.setHFR(SecurityUtils.getUsername());
        List<WtxxDTO> wtxxDTOS = new ArrayList<>();
        if (sjjh.getXH() != null&&!sjjh.getXH().isEmpty()&&!"领导批示".equals(sjjh.getJHZT())) {
            if ("接收".equals(sjjh.getValue())&&sjjh.getUserName()!=null) {
                WtxxDTO wtxxDTO = new WtxxDTO();
                wtxxDTO.setID(sjjh.getWTID());
                wtxxDTO.setValue("反馈");
                wtxxDTOS.add(wtxxDTO);
                questionMapper.updateQuestionsStatus(wtxxDTOS);
            }
            String ejhfppyj = UUID.randomUUID() + "";
            Sjjh sjjh1 = questionMapper.selectJhjlByid(sjjh.getXH(), sjjh.getJHZT());
            int updatejhjl = 0;
            if (sjjh1.getEJHFPPYJ() == null||sjjh1.getEJHFPPYJ().isEmpty()) {
                updatejhjl = questionMapper.updatejhjl(sjjh.getXH(),ejhfppyj);
            }else {
                updatejhjl = questionMapper.updatejhjl(sjjh.getXH(), sjjh1.getEJHFPPYJ() + "," + ejhfppyj);
            }
            if (updatejhjl > 0) {
                sjjh.setXH(ejhfppyj);
                questionMapper.saveJhjlList(sjjh);
            }
        } else if (!"领导批示".equals(sjjh.getJHZT())) {
            if ("接收".equals(sjjh.getValue())&&sjjh.getUserName()!=null) {
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
        if ("待办".equals(wtxxDTO.getRadios())) {
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
    public List<Sjjh> getJhjl(String wtid,String jhzt) {
        if ("领导批示".equals(jhzt)) {
            List<Sjjh> sjjhList = questionMapper.selectJhjlByWtid(wtid,jhzt);
            return sjjhList;
        } else {
            List<Sjjh> sjjhList = questionMapper.selectJhjlByWtid(wtid,jhzt);
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
    public List<Sjjh> getLDPS(String wtid,String jhzt) {
            List<Sjjh> sjjhList = questionMapper.selectldpsByWtid(wtid,jhzt);
            return sjjhList;
    }

    /**
     * 交互数据的递归
     */
    private void zhengli(Sjjh sjjh,String jhzt,List<Sjjh> sjjhs) {
        String[] split = sjjh.getEJHFPPYJ().split(",");
        for (String s : split) {
            Sjjh sj = null;
            for (Sjjh sjjh1 : sjjhs) {
                if (sjjh1.getXH().equals(s)&&jhzt.equals(sjjh1.getJHZT())) {
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
    public void closedLoop(String xh) {
        questionMapper.closedLoop(xh);
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
        String wtbh = questionMapper.selectMaxWtbh(format+"001");
        if (wtbh == null) {
            return format + "001";
        } else {
            String suffix = wtbh.substring(8);
            DecimalFormat d = new DecimalFormat("000");
            return format + d.format(Integer.parseInt(suffix) + 1);
        }
    }


    /**
     * 升级责任人的定时任务的执行方法
     */
    @Override
    public void shengjizrr() {
        List<WtxxVo> wtxxVos = questionMapper.selectywcj();
        for (WtxxVo wtxxVo : wtxxVos) {
            String userId = null;
            ZrrVO zrrVO = questionMapper.selectzrr(wtxxVo.getID());
            if (wtxxVo.getSJRJS() == null || wtxxVo.getSJRJS().isEmpty()) {
                userId = questionMapper.selectBysjzrzdone(zrrVO.getZRBM(), zrrVO.getZRRID());
            } else if ("1".equals(wtxxVo.getSJRJS())) {
                userId = questionMapper.selectBysjzrzdtwo(zrrVO.getZRBM(), zrrVO.getZRRID());
            }
            if (userId == null || userId.isEmpty()) {
                System.out.println("用户数据错误或者，该责任人没有下一级责任人");
            } else {
                //插入一条责任人数据

                questionMapper.updatezrr(zrrVO.getXH(),wtxxVo.getID());
                //修改我的问题表中的责任人的级数
                if (wtxxVo.getSJRJS() == null || wtxxVo.getSJRJS().isEmpty()) {
                    questionMapper.updateQuestionSJRJS(wtxxVo.getID(), 1);
                } else {
                    questionMapper.updateQuestionSJRJS(wtxxVo.getID(), 2);
                }
            }
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
                wtxxVo.setWWCTS(i+"");
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
                for (String s : split) {
                    if (id.equals(s)) {
                        if (split.length > 1) {
                            questionMapper.updatejhjlToAll(sjjh.getXH(), sjjh.getEJHFPPYJ().replaceAll("," + id, ""));
                        } else {
                            questionMapper.updatejhjlToAll(sjjh.getXH(), null);
                        }
                        break;
                    }
                }
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
        wtxxDTO.setSFDZFK(1);
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
        if (sjjh.size() == 0) {
            return;
        }
        questionMapper.updateQuestionStatus(sjjh.get(0).getWTID(), "已关闭");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        List<DaccVO> daccVOS = new ArrayList<>();
        Set<String> wtZrrIds = new HashSet<>();
        for (SjjhDTO sjjhDTO : sjjh) {
            String wtzrrid = sjjhDTO.getWTZRRID();
            wtZrrIds.add(wtzrrid);
        }
        List<List<SjjhDTO>> list = new ArrayList<>();
        for (String wtZrrId : wtZrrIds) {
            List<SjjhDTO> sjjhDTOStream = sjjh.stream().filter(new Predicate<SjjhDTO>() {
                @Override
                public boolean test(SjjhDTO sjjhDTO) {
                    return sjjhDTO.getWTZRRID().equals(wtZrrId);
                }
            }).collect(Collectors.toList());
            list.add(sjjhDTOStream);
        }
        for (List<SjjhDTO> sjjhDTOS : list) {
            DaccVO daccVO = new DaccVO();
            for (SjjhDTO sjjhDTO : sjjhDTOS) {
                daccVO.setWTID(sjjhDTO.getWTID());
                daccVO.setCJRID(sjjhDTO.getWTCJID());
                daccVO.setCJR(sjjhDTO.getWTCJR());
                daccVO.setZRR(sjjhDTO.getWTZRR());
                daccVO.setZRRID(sjjhDTO.getWTZRRID());
                daccVO.setGBSJ(format);
                daccVO.setRD(0);
                daccVO.setWTLB(daccDTO.getWTLB());
                daccVO.setWTXL(daccDTO.getWTXL());
                daccVO.setWTMC(sjjhDTO.getWTBT());
                daccVO.setGXH(daccDTO.getGXH());
                daccVO.setGXMC(daccDTO.getGXH());
                daccVO.setWTMS(daccDTO.getWTMS());
                daccVO.setDAXXID(UUID.randomUUID() + "");
                if (daccVO.getDAXX() != null) {
                    daccVO.setDAXX(daccVO.getDAXX() + "," + sjjhDTO.getHFXX());
                } else {
                    daccVO.setDAXX(sjjhDTO.getHFXX());
                }
            }
            daccVOS.add(daccVO);
        }
        questionMapper.saveDaccList(daccVOS);
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
     * 获得阅知人的问题信息
     */
    @Override
    public List<WtxxVo> getQuestionYzrList(WtxxDTO wtxxDTO) {
        wtxxDTO.setYZR(SecurityUtils.getUserId() + "");
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



    //用来计算两个日期之间的天数date1是小数值的，data2是大数值的
    public static int differentDays(Date date1,Date date2){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2) //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0) //闰年
                {
                    timeDistance += 366;
                }
                else //不是闰年
                {
                    timeDistance += 365;
                }
            }
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return timeDistance + (day2-day1) ;
        }
        else //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }
}
