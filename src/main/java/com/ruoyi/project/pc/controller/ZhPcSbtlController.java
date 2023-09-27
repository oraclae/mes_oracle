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
import com.ruoyi.project.pc.domain.ZhPcSbtl;
import com.ruoyi.project.pc.service.IZhPcSbtlService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 排产设备特例Controller
 *
 * @author qxd
 * @date 2023-09-25
 */
@RestController
@RequestMapping("/pc/sbtl")
public class ZhPcSbtlController extends BaseController {
    @Autowired
    private IZhPcSbtlService zhPcSbtlService;

    /**
     * 查询排产设备特例列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ZhPcSbtl zhPcSbtl) {
        startPage();
        List<ZhPcSbtl> list = zhPcSbtlService.selectZhPcSbtlList(zhPcSbtl);
        return getDataTable(list);
    }

    /**
     * 导出排产设备特例列表
     */
    @Log(title = "排产设备特例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ZhPcSbtl zhPcSbtl) {
        List<ZhPcSbtl> list = zhPcSbtlService.selectZhPcSbtlList(zhPcSbtl);
        ExcelUtil<ZhPcSbtl> util = new ExcelUtil<ZhPcSbtl>(ZhPcSbtl.class);
        util.exportExcel(response, list, "排产设备特例数据");
    }

    /**
     * 获取排产设备特例详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(zhPcSbtlService.selectZhPcSbtlById(id));
    }

    /**
     * 新增排产设备特例
     */
    @Log(title = "排产设备特例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ZhPcSbtl zhPcSbtl) {
        return toAjax(zhPcSbtlService.insertZhPcSbtl(zhPcSbtl));
    }

    /**
     * 修改排产设备特例
     */
    @Log(title = "排产设备特例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ZhPcSbtl zhPcSbtl) {
        return toAjax(zhPcSbtlService.updateZhPcSbtl(zhPcSbtl));
    }

    /**
     * 删除排产设备特例
     */
    @Log(title = "排产设备特例", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(zhPcSbtlService.deleteZhPcSbtlByIds(ids));
    }
}
