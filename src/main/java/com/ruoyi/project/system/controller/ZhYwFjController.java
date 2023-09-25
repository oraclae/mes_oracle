package com.ruoyi.project.system.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.ZhYwFj;
import com.ruoyi.project.system.service.IZhYwFjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 附件Controller
 *
 * @author qxd
 * @date 2023-07-19
 */
@RestController
@RequestMapping("/system/fj")
public class ZhYwFjController extends BaseController {
    @Autowired
    private IZhYwFjService zhYwFjService;

    /**
     * 根据其他表id查询附件列表
     */
    @PostMapping("/selectFjListByIds")
    public TableDataInfo selectFjListByIds(@RequestBody String[] qtbxhs) {
        List<ZhYwFj> list = zhYwFjService.selectFjListByIds(qtbxhs);
        return getDataTable(list);
    }

    /**
     * 查询附件列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ZhYwFj zhYwFj) {
        startPage();
        List<ZhYwFj> list = zhYwFjService.selectZhYwFjList(zhYwFj);
        return getDataTable(list);
    }


    /**
     * 根据id查询附件列表的数据集合
     */
    @GetMapping("/listById")
    public TableDataInfo listById(ZhYwFj zhYwFj) {
        List<ZhYwFj> list = zhYwFjService.selectZhYwFjList(zhYwFj);
        return getDataTable(list);
    }

    /**
     * 根据id查询附件列表的数据集合
     */
    @PostMapping("/listByIds")
    public TableDataInfo listByIds(@RequestBody List<ZhYwFj> zhYwFjs) {
        System.out.println(zhYwFjs.size());
        List<ZhYwFj> list = zhYwFjService.selectZhYwFjListByIds(zhYwFjs);
        return getDataTable(list);
    }

    /**
     * 导出附件列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, ZhYwFj zhYwFj) {
        List<ZhYwFj> list = zhYwFjService.selectZhYwFjList(zhYwFj);
        ExcelUtil<ZhYwFj> util = new ExcelUtil<ZhYwFj>(ZhYwFj.class);
        util.exportExcel(response, list, "附件数据");
    }

    /**
     * 获取附件详细信息
     */
    @GetMapping(value = "/{xh}")
    public AjaxResult getInfo(@PathVariable("xh") String xh) {
        return success(zhYwFjService.selectZhYwFjByXh(xh));
    }

    /**
     * 新增附件
     */
    @PostMapping
    public AjaxResult add(@RequestBody ZhYwFj zhYwFj) {
        return toAjax(zhYwFjService.insertZhYwFj(zhYwFj));
    }

    /**
     * 修改附件
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ZhYwFj zhYwFj) {
        return toAjax(zhYwFjService.updateZhYwFj(zhYwFj));
    }

    /**
     * 删除附件
     */
    @DeleteMapping("/{xhs}")
    public AjaxResult remove(@PathVariable String[] xhs) {
        int i = zhYwFjService.deleteZhYwFjByXhs(xhs);
        if (i == 1) {
            return AjaxResult.success("删除成功", i);
        } else if (i==404) {
            return AjaxResult.error(404,"文件不存在");
        } else if (i == 500) {
            return AjaxResult.error(500,"删除错误");
        } else {
            return AjaxResult.success("批量删除成功");
        }
    }
}


