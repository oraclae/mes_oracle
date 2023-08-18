package com.ruoyi.project.question.controller;

import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.question.domain.*;
import com.ruoyi.project.question.domain.vo.NumberVO;
import com.ruoyi.project.question.domain.vo.WtxxVo;
import com.ruoyi.project.question.service.IWtglCjlsService;
import com.ruoyi.project.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 提出问题Controller
 * 
 * @author LiHai
 * @date 2023-06-19
 */
@RestController
@RequestMapping("/myjob/cjls")
public class WtglCjlsController extends BaseController
{
    @Autowired
    private IWtglCjlsService wtglCjlsService;

    /**
     * 查询提出问题列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WtglCjlsDTO wtglCjls)
    {
        startPage();
        List<WtglCjls> list = wtglCjlsService.selectWtglCjlsList(wtglCjls);
        return getDataTable(list);
    }

    /**
     * 查询提出问题列表
     */
    @GetMapping("/downlistCjls")
    public TableDataInfo downlistCjls(WtglCjls wtglCjls)
    {
        startPage();
        List<WtglCjls> list = wtglCjlsService.selectWtglDownCjlsList(wtglCjls);
        return getDataTable(list);
    }

    /**
     * 查询问题喊话的列表
     */
    @GetMapping("/speechList")
    public TableDataInfo speechList()
    {
        List<WtglCjls> list = wtglCjlsService.selectWtglCjlsSpeechList();
        return getDataTable(list);
    }

    /**
     * 查询提出问题列表
     */
    @GetMapping("/ywcjlist")
    public TableDataInfo ywcjlist(WtglCjls wtglCjls)
    {
        startPage();
        List<WtxxVo> list = wtglCjlsService.selectywcjList(wtglCjls);
        return getDataTable(list);
    }

    /**
     * 导出提出问题列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, WtglCjls wtglCjls)
    {
    }

    /**
     * 获取提出问题详细信息
     */
    @GetMapping(value = "/{LSID}")
    public AjaxResult getInfo(@PathVariable("LSID") String LSID)
    {
        return success(wtglCjlsService.selectWtglCjlsByLSID(LSID));
    }

    /**
     * 新增提出问题
     */
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WtglCjls wtglCjls)
    {
        return toAjax(wtglCjlsService.insertWtglCjls(wtglCjls));
    }

    /**
     * 修改提出问题
     */
    @PutMapping
    public AjaxResult edit(@RequestBody WtglCjls wtglCjls)
    {
        return toAjax(wtglCjlsService.updateWtglCjls(wtglCjls));
    }

    /**
     * 删除提出问题
     */
    @DeleteMapping("/{LSIDs}")
    public AjaxResult remove(@PathVariable String[] LSIDs)
    {
        return toAjax(wtglCjlsService.deleteWtglCjlsByLSIDs(LSIDs));
    }

    /**
     * 修改问题状态
     */
    @PostMapping("/{wtzt}/changewtzt")
    public AjaxResult changewtzt(@PathVariable String wtzt,@RequestBody List<WtglCjls> cjlsList)
    {
        return toAjax(wtglCjlsService.changewtzt(cjlsList,wtzt));
    }

    /**
     * 问题接收
     */
    @PostMapping("/{whether}/wtjs")
    public AjaxResult wtjsIt(@PathVariable String whether, @RequestBody List<WtglCjls> selectedRows)
    {
        return toAjax(wtglCjlsService.wtjsIt(selectedRows,whether));
    }

    /**
     * 修改解决方案
     */
    @PostMapping("/{id}/updatejjfa")
    public AjaxResult updatejjfa(@PathVariable String id, @RequestBody String str)
    {
        return toAjax(wtglCjlsService.updatejjfa(id,str));
    }

    /**
     * 获取完成、未完成数量
     */
    @GetMapping("/getwcsl")
    public TableDataInfo getwcsl()
    {
        List<NumberVO> list = wtglCjlsService.getwcsl();
        return getDataTable(list);
    }

    /**
     * 获取部门科室数量
     */
    @GetMapping("/getcjksbmNumber")
    public TableDataInfo getcjksbmNumber()
    {
        List<NumberVO> list = wtglCjlsService.getcjksbmNumber();
        return getDataTable(list);
    }

    /**
     * 获取接收人列表
     */
    @GetMapping("/{wtlb}/getjsrBywtlb")
    public TableDataInfo getjsrBywtlb(@PathVariable String wtlb)
    {
        List<SysUser> list = wtglCjlsService.getjsrBywtlb(wtlb);
        return getDataTable(list);
    }
    /**
     * 查询答案信息列表
     */
    @GetMapping("/listDacc")
    public TableDataInfo listDacc(WtglDacc wtglDacc)
    {
        startPage();
        List<WtglDacc> list = wtglCjlsService.listDacc(wtglDacc);
        return getDataTable(list);
    }

    /**
     * 智能查询
     */
    @GetMapping("/listDaccToZN")
    public TableDataInfo listDaccToZN(WtglDacc wtglDacc)
    {
        startPage();
        List<WtglDacc> list = wtglCjlsService.listDaccToZN(wtglDacc);
        System.out.println(list);
        return getDataTable(list);
    }

    /**
     * 获取答案信息详细信息
     */
    @GetMapping("/getDacc/{DaxxID}")
    public AjaxResult getDacc(@PathVariable("DaxxID") String DaxxID)
    {
        return success(wtglCjlsService.getDacc(DaxxID));
    }

    /**
     * 新增答案信息
     */
    @PostMapping("/addDacc")
    public AjaxResult addDacc(@RequestBody WtglDacc wtglDacc)
    {
//        return toAjax(wtglCjlsService.addDacc(wtglDacc));
        return success();
    }

    /**
     * 修改答案信息
     */
    @PutMapping("/updateDacc")
    public AjaxResult updateDacc(@RequestBody WtglDacc wtglDacc)
    {
        return toAjax(wtglCjlsService.updateDacc(wtglDacc));
    }

    /**
     * 根据答案表的id增加热度
     */
    @PutMapping("/updateDaccToRd")
    public AjaxResult updateDaccToRd(@RequestBody WtglDacc wtglDacc)
    {
        return toAjax(wtglCjlsService.updateDaccToRd(wtglDacc.getDAXXID()));
    }

    /**
     * 删除答案信息
     */
    @DeleteMapping("/delDacc/{daccIds}")
    public AjaxResult delDacc(@PathVariable String[] daccIds)
    {
        return toAjax(wtglCjlsService.delDacc(daccIds));
    }

    /**
     * 查询业务字典列表
     */
    @GetMapping("/listYwzd")
    public TableDataInfo listYwzd(Ywzd ywzd)
    {
        startPage();
        List<Ywzd> list = wtglCjlsService.listYwzd(ywzd);
        return getDataTable(list);
    }

    /**
     * 获取业务字典详细信息
     */
    @GetMapping(value = "/getYwzd/{XH}")
    public AjaxResult getYwzd(@PathVariable("XH") String XH)
    {
        return success(wtglCjlsService.getYwzd(XH));
    }

    /**
     * 新增业务字典
     */
    @PostMapping("/addYwzd")
    public AjaxResult addYwzd(@RequestBody Ywzd ywzd)
    {
        return toAjax(wtglCjlsService.addYwzd(ywzd));
    }

    /**
     * 修改业务字典
     */
    @PutMapping("updateYwzd")
    public AjaxResult updateYwzd(@RequestBody Ywzd ywzd)
    {
        return toAjax(wtglCjlsService.updateYwzd(ywzd));
    }

    /**
     * 删除业务字典
     */
    @DeleteMapping("/delYwzd/{xhs}")
    public AjaxResult delYwzd(@PathVariable String[] xhs)
    {
        return toAjax(wtglCjlsService.delYwzd(xhs));
    }

    /**
     * 查询提出问题按钮列表
     */
    @GetMapping("/getUpButtons")
    public TableDataInfo getUpButtons()
    {
        List<UpButton> list = wtglCjlsService.getUpButtons();
        return getDataTable(list);
    }

    /**
     * 查询提出问题按钮
     */
    @GetMapping(value = "/getUpButton/{xh}")
    public AjaxResult getUpButton(@PathVariable("xh") String xh)
    {
        return success(wtglCjlsService.getUpButton(xh));
    }


    /**
     * 新增提出问题按钮
     */
    @PostMapping("/addUpButton")
    public AjaxResult addUpButton(@RequestBody UpButton upButton)
    {
        return toAjax(wtglCjlsService.addUpButton(upButton));
    }

    /**
     * 修改提出问题按钮
     */
    @PutMapping("/updateUpButton")
    public AjaxResult updateUpButton(@RequestBody UpButton upButton)
    {
        return toAjax(wtglCjlsService.updateUpButton(upButton));
    }


    /**
     * 删除提出问题按钮
     */
    @DeleteMapping("/delangl/{xhs}")
    public AjaxResult delangl(@PathVariable String[] xhs)
    {
        return toAjax(wtglCjlsService.delangl(xhs));
    }


}

