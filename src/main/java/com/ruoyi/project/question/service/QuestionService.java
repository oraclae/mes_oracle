package com.ruoyi.project.question.service;

import com.ruoyi.project.question.domain.DaccDTO;
import com.ruoyi.project.question.domain.SjjhDTO;
import com.ruoyi.project.question.domain.WtxxDTO;
import com.ruoyi.project.question.domain.vo.Sjjh;
import com.ruoyi.project.question.domain.vo.*;
import com.ruoyi.project.question.domain.vo.ZrrVO;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Author: QXD
 * Date: 2023/4/27 13:59
 * Description:
 * Version:
 */
@Transactional
public interface QuestionService {

    /**
     * 我的问题列表数据
     */
    List<WtxxVo> selectWtxxData(WtxxDTO wtxxDTO);
    //批量删除问题的方法
    List<String> deleteQuestions(List<String> ids);
    //修改问题状态的方法
    void updateQuestionsStatus(List<WtxxDTO> wtxxDTO);

    /**
     * 获得我的待办的问题数据
     *
     * @param wtxxDTO wtxx dto
     * @return {@link List}<{@link WtxxVo}>
     */
    List<WtxxVo> getQuestionList(WtxxDTO wtxxDTO);

    /**
     * 更新我的待办页面责任人的状态
     *
     * @param wtxxDTO wtxx dto
     */
    void updateMyDoListStatus(List<WtxxDTO> wtxxDTO);


    /**
     * 获得领导批示的问题
     */
    List<WtxxVo> getLeaderQuestion(WtxxDTO wtxxDTO);

    /**
     * 修改问题
     */
    void updateQuestions(List<WtxxVo> wtxxVos);

    /**
     * 获得问题交互记录数据
     */
    List<Sjjh> getProblemInteractionList(Sjjh sjjh);

    /**
     * 更新问题交互数据
     */
    void updateProblemInteraction(List<SjjhDTO> sjjhs);

    /**
     * 删除问题交互数据
     */
    void deleteProblemInteraction(List<String> ids);

    //获取问题来源
    List<String> getwtlyMethod();

    //获得问题类别数据
    List<String> getwtlbMethod();

    //获取问题编号的数据
    String getwtbhMethod();

    //获取问题细类问题
    List<String> getwtxlMethod(String wtlb);

    //获取紧急程度的数据
    List<String> getjjcdMethod();
    //创建问题数据
    void createQuestion(WtxxDTO wtxxDTO);

    /**
     * 获得问题交互
     */
    List<WtxxVo> getQuestionInteraction(WtxxDTO wtxxDTO);

    /**
     * 获得回复预览对应创建人和问题的责任人数据
     */
    List<ZrrVO> getzerData(String wtid);

    /**
     * 回复预览页面的回复信息保存
     */
    @Transactional
    void saveJhjlList(Sjjh sjjh);

    /**
     * 问题关注的方法
     */
    void savegzwtList(List<String> ids);

    /**
     * 获得我的关注问题列表
     */
    List<WtxxVo> getMyProblemList(WtxxDTO wtxxDTO);

    /**
     * 取消关注的方法
     */
    void deletegzwtList(List<String> ids);

    /**
     * 得到问题的回复数据
     */
    List<Sjjh> getJhjl(String wtid,String jhzt);

    /**
     * 得到问题的回复数据
     */
    List<Sjjh> getLDPS(String wtid,String jhzt);

    /**
     * 责任人闭环功能
     */
    void closedLoop(ZrrVO zrrVO);

    /**
     * 责任人激活功能
     */
    void activate(String xh);

    /**
     * 更新问题状态
     */
    void updateWtztEnd(WtxxDTO wtxxDTO);

    /**
     * 计算问题天数
     */
    void problemDays();


    /**
     * 删除回复数据
     */
    void deleteJhjlByXh(String id);

    List<WtxxVo> getQuestionYzrList(WtxxDTO wtxxDTO);

    /**
     * 修改问题表的我的问题待办数量
     */
    void updateQuestionWddb(Sjjh sjjh);
    /**
     * 修改问题表的我的待办待办数量
     */
    void updateQuestionWdwt(Sjjh sjjh);

    /**
     * 将我的问题待办数量变成0
     */
    void updateQuestionWTDBToZero(WtxxDTO wtxxDTO);

    /**
     * 将我的问题待办数量变成0
     */
    void updateQuestionWDWTToZero(WtxxDTO wtxxDTO);

    /**
     * 根据问题id查询交互数据
     */
    List<SjjhDTO> selectJhjlList(WtxxDTO wtxxDTO);

    /**
     * 选择回答
     */
    void answerSelectOff(DaccDTO daccDTO);

    /**
     * 清空反馈记录
     */
    void clearFeedbackRecord();

    void updateQuestionStatus(WtxxDTO wtxxDTO);

    /**
     * 根据id获得我的问题数据
     */
    WtxxVo getYwcj(String id);

    /**
     * 更新问题
     */
    void updateQuestion(WtxxDTO wtxxDTO);

    /**
     * 根据id查询交互数据
     */
    Sjjh getJHJL(String xh);

    /**
     * 根据id更新交互数据
     */
    void updateJHJL(SjjhDTO sjjhDTO);

    /**
     * 根据问题id查询所有交互数据
     */
    List<Sjjh> getJhjlByWtid(String wtid);

    /**
     * 升级责任人的定时任务
     */
    void shengjizrr();

    /**
     * 修改是否当周反馈
     */
    int updateSfdzfk(String id);
}
