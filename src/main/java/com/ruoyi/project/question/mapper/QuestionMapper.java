package com.ruoyi.project.question.mapper;

import com.ruoyi.project.question.domain.SjjhDTO;
import com.ruoyi.project.question.domain.WtxxDTO;
import com.ruoyi.project.question.domain.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: QXD
 * Date: 2023/4/27 13:59
 * Description:
 * Version:
 */
@Repository
public interface QuestionMapper {

    /**
     * 查询待办的数据
     * @return 数据集合
     */
    List<WtxxVo> selectWtxxData1(@Param("wtxxDTO") WtxxDTO wtxxDTO);
    /**
     * 查询我的待办的数据
     * @return 数据集合
     */
    List<WtxxVo> selectWtxxData2(@Param("wtxxDTO") WtxxDTO wtxxDTO);

    /**
     * 创建问题的方法
     */
    void createQuestion(@Param("wtxxDTO") WtxxDTO wtxxDTO);


    //批量删除我的问题数据
    void deleteQuestions(@Param("ids") List<String> ids);

    //修改我的问题状态的方法
    void updateQuestionsStatus(@Param("wtxxDTO") List<WtxxDTO> wtxxDTO);

    //批量修改问题
    void updateQuestions(@Param("wtxxVos") List<WtxxVo> wtxxVos);

    //修改我的待办页面责任人的状态
    void updateMyDoListStatus(@Param("wtxxDTO") List<WtxxDTO> wtxxDTO);

    //获得问题交互记录数据
    List<Sjjh> getProblemInteractionList(@Param("sjjh") Sjjh sjjh);

    //更新问题交互数据
    void updateProblemInteraction(@Param("sjjhs") List<SjjhDTO> sjjhs);

    //批量删除问题交互数据
    void deleteProblemInteraction(@Param("ids") List<String> ids);

    //获取问题来源的方法
    List<String> getwtlyMethod();

    //获取问题类别的数据方法
    List<String> getwtlbMethod();

    //获取问题细类的方法
    List<String> getwtxlMethod(@Param("wtlb") String wtlb);

    //获取紧急程度数据
    List<String> getjjcdMethod();

    //获取数据库中最大的问题编号
    String selectMaxWtbh(@Param("wtbh")String wtbh);

    /**
     * 获得回复预览对应创建人和问题的责任人数据
     */
    List<ZrrVO> getzerData(@Param("wtid") String wtid);

    /**
     * 回复预览页面的回复信息保存
     */
    void saveJhjlList(@Param("sjjh") Sjjh sjjh);

    /**
     * 问题关注的方法
     */
    void savegzwtList(@Param("wtgzVo") List<WtgzVo> wtgzVo);

    /**
     * 获得我的关注问题列表
     */
    List<WtxxVo> getMyProblemList(@Param("wtxxDTO") WtxxDTO wtxxDTO);

    /**
     * 取消关注的方法
     */
    void deletegzwtList(@Param("wtgzVos") List<WtgzVo> wtgzVos);

    /**
     * 得到问题的回复数据
     */
    List<Sjjh> selectJhjlByWtid(@Param("wtid") String wtid,@Param("jhzt") String jhzt);

    /**
     * 得到问题领导批示的回复数据
     */
    List<Sjjh> selectldpsByWtid(@Param("wtid") String wtid,@Param("jhzt") String jhzt);

    /**
     * 根据xh和交互状态来查询数据
     */
    Sjjh selectJhjlByid(@Param("id") String id,@Param("jhzt") String jhzt);

    /**
     * 修改父级包含子集的集合数据
     */
    int updatejhjl(@Param("xh") String xh,@Param("ejhfppyj") String ejhfppyj);

    /**
     * 责任人闭环功能
     */
    void closedLoop(@Param("xh") String xh);

    /**
     * 责任人激活功能
     */
    void activate(@Param("xh") String xh);

    /**
     * 查询需要升级责任人的问题
     */
    List<WtxxVo> selectywcj();

    /**
     * 查询该问题下的主责任人
     */
    ZrrVO selectzrr(@Param("id") String id);

    /**
     * 修改责任人是否为主要责任人
     */
    void updatezrr(@Param("xh") String xh,@Param("wtid") String wtid);

    /**
     * 查询一级的升级责任人的数据返回二级的用户id
     */
    String selectBysjzrzdone(@Param("zrbm") String zrbm,@Param("zrrid") String zrrid);

    /**
     * 查询二级的升级责任人id的数据返回三级的用户id
     */
    String selectBysjzrzdtwo(@Param("zrbm") String zrbm,@Param("zrrid") String zrrid);

    /**
     * 更新问题升级人级数
     */
    void updateQuestionSJRJS(@Param("id") String id,@Param("i") int i);

    List<WtxxVo> selectWtxxData();

    /**
     * 删除回复数据
     */
    void deleteJhjlByXh(@Param("id") String id);

    /**
     * 模糊查询交互数据通过二级模糊查寻id
     */
    List<Sjjh> selectJhjlByEjhfppyj(String id);

    /**
     * 替换要删除的回复问题交互数据的二级内容
     */
    void updatejhjlToAll(@Param("xh") String xh,@Param("replaceAll") String replaceAll);

    /**
     * 修改问题表的我的问题待办数量
     */
    void updateQuestionWddb(@Param("wtxxDTO") WtxxDTO wtxxDTO);

    /**
     * 修改问题表的我的待办待办数量
     */
    void updateQuestionWdwt(@Param("wtxxDTO")WtxxDTO wtxxDTO);

    /**
     * 将我的问题待办数量变成0
     *
     * @param id id
     */
    void updateQuestionWTDBToZero(@Param("id") String id);

    /**
     * 将我的问题待办数量变成0
     *
     * @param id id
     */
    void updateQuestionWDWTToZero(@Param("id") String id);

    /**
     * 根据问题id查询交互数据
     */
    List<SjjhDTO> selectJhjlList(@Param("wtxxDTO") WtxxDTO wtxxDTO);

    /**
     * 保存答案列表
     */
    void saveDaccList(@Param("dacc") DaccVO daccVO);

    /**
     * 更新问题状态
     */
    void updateQuestionStatus(@Param("wtid") String wtid, @Param("status") String status);
    void updateQuestionStatusSJ(@Param("wtid") String wtid, @Param("status") String status, @Param("format") String format);

    /**
     * 更新问题的判断当周反馈没有
     */
    void updateQuestionSFDZFK();

    /**
     * 根据id获得我的问题数据
     */
    WtxxVo getYwcj(@Param("id") String id);

    /**
     * 根据id更新问题
     */
    void updateQuestion(WtxxDTO wtxxDTO);

    /**
     * 根据id查询交互数据
     */
    Sjjh selectJhjlByXh(@Param("xh") String xh);

    /**
     * 根据id更新交互数据
     */
    void updateJHJL(SjjhDTO sjjhDTO);
    /**
     * 查询责任人表主责任人根据问题id
     */
    ZrrVO selectZrrByWtidAndZhu(@Param("wtid") String wtid);

    /**
     * 根据问题id查询所有交互数据
     */
    List<Sjjh> getJhjlByWtid(@Param("wtid") String wtid);

    /**
     * 创建责任代办
     */
    void createZRDB(List<ZrrVO> zrrVOList);

    //获得问题升级等于true的数据
    List<WtxxVo> selectWtxxByWtsj();

    /**
     * 根据wtid修改问题的是否主责任人
     */
    void updateZrdb(@Param("wtxxVos") List<WtxxVo> wtxxVos);

    /**
     * 修改责任人表的数据
     */
    void updateZRDBData(@Param("zrdb") ZrrVO zrrVO);
}
