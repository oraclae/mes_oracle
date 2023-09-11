package com.ruoyi.project.question.controller;

import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.question.domain.DaccDTO;
import com.ruoyi.project.question.domain.SjjhDTO;
import com.ruoyi.project.question.domain.WtxxDTO;
import com.ruoyi.project.question.domain.vo.Sjjh;
import com.ruoyi.project.question.domain.vo.WtxxVo;
import com.ruoyi.project.question.domain.vo.ZrrVO;
import com.ruoyi.project.question.service.QuestionService;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: QXD
 * Date: 2023/4/7 11:57
 * Description:
 * Version:
 */

@RestController
public class QuestionController extends BaseController {
    @Autowired
    private QuestionService questionService;

    /**
     * 页面加载后执行的方法
     */
    @GetMapping("/myquestion/selectWtxxData")
    public TableDataInfo selectWtxxData(WtxxDTO wtxxDTO) {
        startPage();
        List<WtxxVo> list = questionService.selectWtxxData(wtxxDTO);
        return getDataTable(list);
    }

    /**
     * 获得我的待办页面的问题
     *
     * @param wtxxDTO wtxx dto
     * @return {@link TableDataInfo}
     */
    @GetMapping("/myquestion/getQuestionList")
    public TableDataInfo getQuestionList(WtxxDTO wtxxDTO) {
        startPage();
        List<WtxxVo> list = questionService.getQuestionList(wtxxDTO);
        return getDataTable(list);
    }

    /**
     * 获得领导需要批示的问题
     */
    @GetMapping("/myquestion/getLeaderQuestion")
    public TableDataInfo getLeaderQuestion(WtxxDTO wtxxDTO) {
        startPage();
        List<WtxxVo> list = questionService.getLeaderQuestion(wtxxDTO);
        return getDataTable(list);
    }

    /**
     * 获得问题交互的数据
     */
    @GetMapping("/myquestion/getQuestionInteraction")
    public TableDataInfo getQuestionInteraction(WtxxDTO wtxxDTO) {
        startPage();
        List<WtxxVo> list = questionService.getQuestionInteraction(wtxxDTO);
        return getDataTable(list);
    }

    /**
     * 获得问题交互记录的数据
     */
    @GetMapping("/myquestion/getProblemInteractionList")
    public TableDataInfo getProblemInteractionList(Sjjh sjjh) {
        startPage();
        List<Sjjh> list = questionService.getProblemInteractionList(sjjh);
        return getDataTable(list);
    }

    /**
     * 批量删除问题数据
     */
    @PostMapping("/myquestion/deleteQuestions")
    public AjaxResult deleteQuestions(@RequestBody List<String> ids) {
        questionService.deleteQuestions(ids);
        return AjaxResult.success("删除成功");
    }

    /**
     * 批量删除问题交互数据
     */
    @PostMapping("/myquestion/deleteProblemInteraction")
    public AjaxResult deleteProblemInteraction(@RequestBody List<String> ids) {
        questionService.deleteProblemInteraction(ids);
        return AjaxResult.success("删除成功");
    }

    /**
     * 批量修改问题状态数据
     */
    @PostMapping("/myquestion/updateQuestionsStatus")
    public AjaxResult updateQuestionsStatus(@RequestBody List<WtxxDTO> wtxxDTO) {
        questionService.updateQuestionsStatus(wtxxDTO);
        return AjaxResult.success("修改成功");
    }


    /**
     * 批量修改我的待办问题状态数据
     */
    @PostMapping("/myquestion/updateMyDoListStatus")
    public AjaxResult updateMyDoListStatus(@RequestBody List<WtxxDTO> wtxxDTO) {
        questionService.updateMyDoListStatus(wtxxDTO);
        return AjaxResult.success("修改成功");
    }

    /**
     * 批量修改问题数据
     *
     * @param wtxxVos wtxx vos
     * @return {@link AjaxResult}
     */
    @PostMapping("/myquestion/updateQuestions")
    public AjaxResult updateQuestions(@RequestBody List<WtxxVo> wtxxVos) {
        questionService.updateQuestions(wtxxVos);
        return AjaxResult.success("修改成功");
    }


    /**
     * 批量修改问题交互数据
     */
    @PostMapping("/myquestion/updateProblemInteraction")
    public AjaxResult updateProblemInteraction(@RequestBody List<SjjhDTO> sjjhs) {
        questionService.updateProblemInteraction(sjjhs);
        return AjaxResult.success("修改成功");
    }


    /**
     * 获取问题来源数据的方法
     */
    @GetMapping("/myquestion/getwtlyMethod")
    public TableDataInfo getwtlyMethod() {
        List<String> list = questionService.getwtlyMethod();
        return getDataTable(list);
    }

    /**
     * 获取问题类别数据的方法
     */
    @GetMapping("/myquestion/getwtlbMethod")
    public TableDataInfo getwtlbMethod() {
        List<String> list = questionService.getwtlbMethod();
        return getDataTable(list);
    }

    /**
     * 获取问题细类数据的方法
     */
    @GetMapping("/myquestion/getwtxlMethod")
    public TableDataInfo getwtxlMethod(String wtlb) {
        List<String> list = questionService.getwtxlMethod(wtlb);
        return getDataTable(list);
    }

    /**
     * 获取紧急程度的数据的方法
     */
    @GetMapping("/myquestion/getjjcdMethod")
    public TableDataInfo getjjcdMethod() {
        List<String> list = questionService.getjjcdMethod();
        return getDataTable(list);
    }

    /**
     * 获取问题编号的数据的方法
     */
    @GetMapping("/myquestion/getwtbhMethod")
    public AjaxResult getwtbhMethod() {
        String wtbh = questionService.getwtbhMethod();
        return AjaxResult.success(wtbh);
    }

    /**
     * 创建问题的方法
     */
    @PostMapping("/myquestion/createQuestion")
    public AjaxResult createQuestion(@RequestBody WtxxDTO wtxxDTO) {
        questionService.createQuestion(wtxxDTO);
        return AjaxResult.success("修改成功");
    }


    /**
     * 获得回复预览对应创建人和问题的责任人数据
     */
    @GetMapping("/myquestion/getzerData")
    public TableDataInfo getzerData(String wtid) {
        List<ZrrVO> list = questionService.getzerData(wtid);
        return getDataTable(list);
    }

    /**
     * 回复预览页面的回复信息保存
     */
    @PostMapping("/myquestion/saveJhjlList")
    public AjaxResult saveJhjlList(@RequestBody Sjjh sjjh) {
        questionService.saveJhjlList(sjjh);
        return AjaxResult.success("保存成功");
    }

    /**
     * 问题关注的方法
     */
    @PostMapping("/myquestion/savegzwtList")
    public AjaxResult savegzwtList(@RequestBody List<String> ids) {
        try {
            questionService.savegzwtList(ids);
        } catch (Exception e) {
            return AjaxResult.error(302, "数据已经关注，请勿重复关注");
        }
        return AjaxResult.success("关注成功");
    }

    /**
     * 获得我的关注问题列表
     */
    @GetMapping("/myquestion/getMyProblemList")
    public TableDataInfo getMyProblemList(WtxxDTO wtxxDTO) {
        startPage();
        List<WtxxVo> list = questionService.getMyProblemList(wtxxDTO);
        return getDataTable(list);
    }


    /**
     * 取消关注的方法
     */
    @PostMapping("/myquestion/deletegzwtList")
    public AjaxResult deletegzwtList(@RequestBody List<String> ids) {
        questionService.deletegzwtList(ids);
        return AjaxResult.success("取消关注成功");
    }

    /**
     * 得到交互数据
     */
    @GetMapping("/myquestion/getJhjl")
    public TableDataInfo getJhjl(Sjjh sjjh) {
        List<Sjjh> list = questionService.getJhjl(sjjh.getWTID(),sjjh.getJHZT());
        return getDataTable(list);
    }

    /**
     * 根据问题id查询所有交互数据
     */
    @GetMapping("/myquestion/getJhjlByWtid")
    public TableDataInfo getJhjlByWtid(Sjjh sjjh) {
        List<Sjjh> list = questionService.getJhjlByWtid(sjjh.getWTID());
        return getDataTable(list);
    }

    /**
     * 得到交互数据
     */
    @GetMapping("/myquestion/getLDPS")
    public TableDataInfo getLDPS(Sjjh sjjh) {
        List<Sjjh> list = questionService.getLDPS(sjjh.getWTID(),sjjh.getJHZT());
        return getDataTable(list);
    }

    /**
     * 责任人闭环功能
     */
    @PostMapping("/myquestion/closedLoop")
    public AjaxResult closedLoop(@RequestBody ZrrVO zrrVO) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        List<WtxxVo> wtxxDTOS = new ArrayList<>();
        WtxxVo wtxxVo = new WtxxVo();
        wtxxVo.setID(zrrVO.getWTID());
        wtxxVo.setBHSJ(format);
        wtxxDTOS.add(wtxxVo);
        questionService.updateQuestions(wtxxDTOS);
        questionService.closedLoop(zrrVO.getXH());
        return AjaxResult.success("责任人闭环成功");
    }

    /**
     * 责任人激活功能
     */
    @PostMapping("/myquestion/activate")
    public AjaxResult activate(@RequestBody ZrrVO zrrVO) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        List<WtxxVo> wtxxDTOS = new ArrayList<>();
        WtxxVo wtxxVo = new WtxxVo();
        wtxxVo.setID(zrrVO.getWTID());
        wtxxVo.setJHSJ(format);
        wtxxDTOS.add(wtxxVo);
        questionService.updateQuestions(wtxxDTOS);
        questionService.activate(zrrVO.getXH());
        return AjaxResult.success("责任人激活成功");
    }


    /**
     * 更新问题状态
     */
    @PostMapping("/myquestion/updateWtztEnd")
    public AjaxResult updateWtztEnd(@RequestBody WtxxDTO wtxxDTO) {
        questionService.updateWtztEnd(wtxxDTO);
        return AjaxResult.success("问题改成接收状态");
    }
    /**
     * 删除回复数据
     */
    @PostMapping("/myquestion/deleteJhjlByXh")
    public AjaxResult deleteJhjlByXh(@RequestBody String id) {
        questionService.deleteJhjlByXh(id);
        return AjaxResult.success("删除成功");
    }


    /**
     * 获得阅知人的信息
     */
    @GetMapping("/myquestion/getQuestionYzrList")
    public TableDataInfo getQuestionYzrList(WtxxDTO wtxxDTO) {
        startPage();
        List<WtxxVo> list = questionService.getQuestionYzrList(wtxxDTO);
        return getDataTable(list);
    }


    /**
     * 修改问题表的我的待办数量
     */
    @PostMapping("/myquestion/updateQuestionWddb")
    public AjaxResult updateQuestionWddb(@RequestBody Sjjh sjjh) {
        questionService.updateQuestionWdwt(sjjh);
        return AjaxResult.success("修改我的待办待办数量");
    }

    /**
     * 修改问题表的我的问题待办数量
     */
    @PostMapping("/myquestion/updateQuestionWdwt")
    public AjaxResult updateQuestionWdwt(@RequestBody Sjjh sjjh) {
        questionService.updateQuestionWddb(sjjh);
        return AjaxResult.success("修改我的问题待办数量");
    }


    /**
     * 将我的问题待办数量变成0
     */
    @PostMapping("/myquestion/updateQuestionWTDBToZero")
    public AjaxResult updateQuestionWTDBToZero(@RequestBody WtxxDTO wtxxDTO) {
        questionService.updateQuestionWTDBToZero(wtxxDTO);
        return AjaxResult.success("修改我的问题待办数量变成0");
    }
    /**
     * 将我的问题待办数量变成0
     */
    @PostMapping("/myquestion/updateQuestionWDWTToZero")
    public AjaxResult updateQuestionWDWTToZero(@RequestBody WtxxDTO wtxxDTO) {
        questionService.updateQuestionWDWTToZero(wtxxDTO);
        return AjaxResult.success("修改我的问题待办数量变成0");
    }


    /**
     * 根据问题id查询交互数据
     */
    @GetMapping("/myquestion/selectJhjlList")
    public TableDataInfo selectJhjlList(WtxxDTO wtxxDTO) {
        List<SjjhDTO> list = questionService.selectJhjlList(wtxxDTO);
        return getDataTable(list);
    }


    /**
     * 回答选择了
     */
    @PostMapping("/myquestion/answerSelectOff")
    public AjaxResult answerSelectOff(@RequestBody DaccDTO daccDTOS) {
        questionService.answerSelectOff(daccDTOS);
        return AjaxResult.success("选择成功");
    }

    /**
     * 激活后问题变成反馈状态
     */
    @PostMapping("/myquestion/updateQuestionStatus")
    public AjaxResult updateQuestionStatus(@RequestBody WtxxDTO wtxxDTO) {
        questionService.updateQuestionStatus(wtxxDTO);
        return AjaxResult.success("修改成功");
    }

    /**
     * 根据id获得我的问题数据
     */
    @GetMapping("/myquestion/getYwcj")
    public AjaxResult getYwcj(String id) {
        WtxxVo wtxxVo = questionService.getYwcj(id);
        return AjaxResult.success(wtxxVo);
    }

    /**
     * 修改问题
     */
    @PostMapping("/myquestion/updateQuestion")
    public AjaxResult updateQuestion(@RequestBody WtxxDTO wtxxDTO) {
        questionService.updateQuestion(wtxxDTO);
        return AjaxResult.success("修改成功");
    }

    /**
     * 根据id查询交互数据
     */
    @GetMapping("/myquestion/getJHJL")
    public AjaxResult getJHJL(String xh) {
        Sjjh sjjh = questionService.getJHJL(xh);
        return AjaxResult.success(sjjh);
    }

    /**
     * 根据id更新交互数据
     */
    @PostMapping("/myquestion/updateJHJL")
    public AjaxResult updateJHJL(@RequestBody SjjhDTO sjjhDTO) {
        questionService.updateJHJL(sjjhDTO);
        return AjaxResult.success("修改成功");
    }

    @PostMapping("/myquestion/upload")
    public AjaxResult upload(@RequestParam("image") MultipartFile multipartFile) {
        try {

            File file = File.createTempFile("temp", ".png");

            // 将 MultipartFile 的内容复制到临时文件
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            FileCopyUtils.copy(multipartFile.getInputStream(), fileOutputStream);
            fileOutputStream.close();
            //String path = "C:\\Users\\Administrator\\Desktop\\微信图片_20230711164942.png";
            //File file = new File(path);
            String lagnguagePath = "D:\\tessdata";
            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath(lagnguagePath);
            tesseract.setLanguage("chi_sim");
            String rus = null;
            rus = tesseract.doOCR(file);
            System.out.println(rus);
        } catch (TesseractException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.success("");

    }

}
