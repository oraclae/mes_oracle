package com.ruoyi.project.pc.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
import com.ruoyi.project.pc.domain.DaRl;
import com.ruoyi.project.pc.service.IDaRlService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 日期Controller
 *
 * @author qxd
 * @date 2023-09-23
 */
@RestController
@RequestMapping("/pc/rl")
public class DaRlController extends BaseController {
    @Autowired
    private IDaRlService daRlService;

    /**
     * 查询日期列表
     */
    @GetMapping("/list")
    public TableDataInfo list(DaRl daRl) {
        startPage();
        List<DaRl> list = daRlService.selectDaRlList(daRl);
        return getDataTable(list);
    }

    /**
     * 导出日期列表
     */
    @Log(title = "日期", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DaRl daRl) {
        List<DaRl> list = daRlService.selectDaRlList(daRl);
        ExcelUtil<DaRl> util = new ExcelUtil<DaRl>(DaRl.class);
        util.exportExcel(response, list, "日期数据");
    }

    /**
     * 获取日期详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(daRlService.selectDaRlById(id));
    }

    /**
     * 新增日期
     */
    @Log(title = "日期", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DaRl daRl) {
        return toAjax(daRlService.insertDaRl(daRl));
    }

    /**
     * 修改日期
     */
    @Log(title = "日期", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DaRl daRl) {
        return toAjax(daRlService.updateDaRl(daRl));
    }

    /**
     * 删除日期
     */
    @Log(title = "日期", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(daRlService.deleteDaRlByIds(ids));
    }

    /**
     * 生成日期的方法
     */
    @GetMapping("/addGenerationDate")
    public AjaxResult addGenerationDate(DaRl daRl) {
        daRlService.addGenerationDate(daRl);
        return AjaxResult.success();
    }
}
