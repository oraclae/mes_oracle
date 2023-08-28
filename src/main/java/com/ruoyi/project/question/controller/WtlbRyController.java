package com.ruoyi.project.question.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.question.domain.WtlbRy;
import com.ruoyi.project.question.service.IWtlbRyService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 问题类别人员对照Controller
 *
 * @author ruoyi
 * @date 2023-08-25
 */
@RestController
@RequestMapping("/question/wtlbry")
public class WtlbRyController extends BaseController {
    @Autowired
    private IWtlbRyService wtlbRyService;

    /**
     * 获取登陆人信息
     */
    @GetMapping("/getUserInfo")
    public AjaxResult getUserInfo() {
        return success(SecurityUtils.getLoginUser());
    }

    /**
     * 查询问题类别人员对照列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WtlbRy wtlbRy) {
        startPage();
        List<WtlbRy> list = wtlbRyService.selectWtlbRyList(wtlbRy);
        return getDataTable(list);
    }

    /**
     * 导出问题类别人员对照列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, WtlbRy wtlbRy) {
        List<WtlbRy> list = wtlbRyService.selectWtlbRyList(wtlbRy);
        ExcelUtil<WtlbRy> util = new ExcelUtil<WtlbRy>(WtlbRy.class);
        util.exportExcel(response, list, "问题类别人员对照数据");
    }

    /**
     * 获取问题类别人员对照详细信息
     */
    @GetMapping(value = "/{xh}")
    public AjaxResult getInfo(@PathVariable("xh") String xh) {
        return success(wtlbRyService.selectWtlbRyByXh(xh));
    }

    /**
     * 新增问题类别人员对照
     */
    @PostMapping
    public AjaxResult add(@RequestBody WtlbRy wtlbRy) {
        int i = wtlbRyService.insertWtlbRy(wtlbRy);
        if (i == 0) {
            return AjaxResult.error("问题细类已存在");
        }
        return toAjax(i);
    }

    /**
     * 修改问题类别人员对照
     */
    @PutMapping
    public AjaxResult edit(@RequestBody WtlbRy wtlbRy) {
        return toAjax(wtlbRyService.updateWtlbRy(wtlbRy));
    }

    /**
     * 删除问题类别人员对照
     */
    @DeleteMapping("/{xhs}")
    public AjaxResult remove(@PathVariable String[] xhs) {
        return toAjax(wtlbRyService.deleteWtlbRyByXhs(xhs));
    }
}
