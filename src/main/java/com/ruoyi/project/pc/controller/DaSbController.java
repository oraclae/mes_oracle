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
import com.ruoyi.project.pc.domain.DaSb;
import com.ruoyi.project.pc.service.IDaSbService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 设备Controller
 *
 * @author qxd
 * @date 2023-09-25
 */
@RestController
@RequestMapping("/pc/sb")
public class DaSbController extends BaseController {
    @Autowired
    private IDaSbService daSbService;

    /**
     * 查询设备列表
     */
    @GetMapping("/list")
    public TableDataInfo list(DaSb daSb) {
        startPage();
        List<DaSb> list = daSbService.selectDaSbList(daSb);
        return getDataTable(list);
    }

    /**
     * 导出设备列表
     */
    @Log(title = "设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DaSb daSb) {
        List<DaSb> list = daSbService.selectDaSbList(daSb);
        ExcelUtil<DaSb> util = new ExcelUtil<DaSb>(DaSb.class);
        util.exportExcel(response, list, "设备数据");
    }

    /**
     * 获取设备详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(daSbService.selectDaSbById(id));
    }

    /**
     * 新增设备
     */
    @Log(title = "设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DaSb daSb) {
        return toAjax(daSbService.insertDaSb(daSb));
    }

    /**
     * 修改设备
     */
    @Log(title = "设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DaSb daSb) {
        return toAjax(daSbService.updateDaSb(daSb));
    }

    /**
     * 删除设备
     */
    @Log(title = "设备", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(daSbService.deleteDaSbByIds(ids));
    }
}
