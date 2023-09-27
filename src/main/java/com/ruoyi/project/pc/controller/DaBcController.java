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
import com.ruoyi.project.pc.domain.DaBc;
import com.ruoyi.project.pc.service.IDaBcService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 班次Controller
 *
 * @author qxd
 * @date 2023-09-25
 */
@RestController
@RequestMapping("/pc/bc")
public class DaBcController extends BaseController {
    @Autowired
    private IDaBcService daBcService;

    /**
     * 查询班次列表
     */
    @GetMapping("/list")
    public TableDataInfo list(DaBc daBc) {
        startPage();
        List<DaBc> list = daBcService.selectDaBcList(daBc);
        return getDataTable(list);
    }

    /**
     * 导出班次列表
     */
    @Log(title = "班次", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DaBc daBc) {
        List<DaBc> list = daBcService.selectDaBcList(daBc);
        ExcelUtil<DaBc> util = new ExcelUtil<DaBc>(DaBc.class);
        util.exportExcel(response, list, "班次数据");
    }

    /**
     * 获取班次详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(daBcService.selectDaBcById(id));
    }

    /**
     * 新增班次
     */
    @Log(title = "班次", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DaBc daBc) {
        return toAjax(daBcService.insertDaBc(daBc));
    }

    /**
     * 修改班次
     */
    @Log(title = "班次", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DaBc daBc) {
        return toAjax(daBcService.updateDaBc(daBc));
    }

    /**
     * 删除班次
     */
    @Log(title = "班次", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(daBcService.deleteDaBcByIds(ids));
    }
}
