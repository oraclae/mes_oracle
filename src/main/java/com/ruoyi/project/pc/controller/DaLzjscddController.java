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
import com.ruoyi.project.pc.domain.DaLzjscdd;
import com.ruoyi.project.pc.service.IDaLzjscddService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 零组件生产订单Controller
 *
 * @author qxd
 * @date 2023-09-22
 */
@RestController
@RequestMapping("/pc/lzjscdd")
public class DaLzjscddController extends BaseController
{
    @Autowired
    private IDaLzjscddService daLzjscddService;

    /**
     * 查询零组件生产订单列表
     */
    @GetMapping("/list")
    public TableDataInfo list(DaLzjscdd daLzjscdd)
    {
        startPage();
        List<DaLzjscdd> list = daLzjscddService.selectDaLzjscddList(daLzjscdd);
        return getDataTable(list);
    }

    /**
     * 导出零组件生产订单列表
     */
    @Log(title = "零组件生产订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DaLzjscdd daLzjscdd)
    {
        List<DaLzjscdd> list = daLzjscddService.selectDaLzjscddList(daLzjscdd);
        ExcelUtil<DaLzjscdd> util = new ExcelUtil<DaLzjscdd>(DaLzjscdd.class);
        util.exportExcel(response, list, "零组件生产订单数据");
    }

    /**
     * 获取零组件生产订单详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(daLzjscddService.selectDaLzjscddById(id));
    }

    /**
     * 新增零组件生产订单
     */
    @Log(title = "零组件生产订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DaLzjscdd daLzjscdd)
    {
        return toAjax(daLzjscddService.insertDaLzjscdd(daLzjscdd));
    }

    /**
     * 修改零组件生产订单
     */
    @Log(title = "零组件生产订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DaLzjscdd daLzjscdd)
    {
        return toAjax(daLzjscddService.updateDaLzjscdd(daLzjscdd));
    }

    /**
     * 删除零组件生产订单
     */
    @Log(title = "零组件生产订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(daLzjscddService.deleteDaLzjscddByIds(ids));
    }
}
