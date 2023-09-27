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
import com.ruoyi.project.pc.domain.DaDdgylx;
import com.ruoyi.project.pc.service.IDaDdgylxService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 订单工艺路线Controller
 *
 * @author qxd
 * @date 2023-09-22
 */
@RestController
@RequestMapping("/pc/ddgylx")
public class DaDdgylxController extends BaseController {
    @Autowired
    private IDaDdgylxService daDdgylxService;

    /**
     * 查询订单工艺路线列表
     */
    @GetMapping("/list")
    public TableDataInfo list(DaDdgylx daDdgylx) {
        startPage();
        List<DaDdgylx> list = daDdgylxService.selectDaDdgylxList(daDdgylx);
        return getDataTable(list);
    }

    /**
     * 导出订单工艺路线列表
     */
    @Log(title = "订单工艺路线", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DaDdgylx daDdgylx) {
        List<DaDdgylx> list = daDdgylxService.selectDaDdgylxList(daDdgylx);
        ExcelUtil<DaDdgylx> util = new ExcelUtil<DaDdgylx>(DaDdgylx.class);
        util.exportExcel(response, list, "订单工艺路线数据");
    }

    /**
     * 获取订单工艺路线详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(daDdgylxService.selectDaDdgylxById(id));
    }

    /**
     * 新增订单工艺路线
     */
    @Log(title = "订单工艺路线", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DaDdgylx daDdgylx) {
        return toAjax(daDdgylxService.insertDaDdgylx(daDdgylx));
    }

    /**
     * 修改订单工艺路线
     */
    @Log(title = "订单工艺路线", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DaDdgylx daDdgylx) {
        return toAjax(daDdgylxService.updateDaDdgylx(daDdgylx));
    }

    /**
     * 删除订单工艺路线
     */
    @Log(title = "订单工艺路线", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(daDdgylxService.deleteDaDdgylxByIds(ids));
    }
}
