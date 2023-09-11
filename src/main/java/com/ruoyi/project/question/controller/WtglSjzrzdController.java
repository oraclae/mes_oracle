package com.ruoyi.project.question.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.ruoyi.project.question.domain.WtglSjzrzd;
import com.ruoyi.project.question.domain.WtxxDTO;
import com.ruoyi.project.question.service.IWtglSjzrzdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 升级责任人Controller
 *
 * @author qxd
 * @date 2023-08-18
 */
@RestController
@RequestMapping("/system/sjzrzd")
public class WtglSjzrzdController extends BaseController {
    @Autowired
    private IWtglSjzrzdService wtglSjzrzdService;

    /**
     * 查询升级责任人列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WtglSjzrzd wtglSjzrzd) {
        startPage();
        List<WtglSjzrzd> list = wtglSjzrzdService.selectWtglSjzrzdList(wtglSjzrzd);
        return getDataTable(list);
    }

    /**
     * 导出升级责任人列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, WtglSjzrzd wtglSjzrzd) {
        List<WtglSjzrzd> list = wtglSjzrzdService.selectWtglSjzrzdList(wtglSjzrzd);
        ExcelUtil<WtglSjzrzd> util = new ExcelUtil<WtglSjzrzd>(WtglSjzrzd.class);
        util.exportExcel(response, list, "升级责任人数据");
    }

    /**
     * 获取升级责任人详细信息
     */
    @GetMapping(value = "/{xh}")
    public AjaxResult getInfo(@PathVariable("xh") String xh) {
        return success(wtglSjzrzdService.selectWtglSjzrzdByXh(xh));
    }

    /**
     * 新增升级责任人
     */
    @PostMapping
    public AjaxResult add(@RequestBody WtglSjzrzd wtglSjzrzd) {
        try {
            return toAjax(wtglSjzrzdService.insertWtglSjzrzd(wtglSjzrzd));
        } catch (Exception e) {
            System.out.println("当前责任人重复了");
            return AjaxResult.error("当前责任人重复了");
        }
    }

    /**
     * 修改升级责任人
     */
    @PutMapping
    public AjaxResult edit(@RequestBody WtglSjzrzd wtglSjzrzd) {
        return toAjax(wtglSjzrzdService.updateWtglSjzrzd(wtglSjzrzd));
    }

    /**
     * 删除升级责任人
     */
    @DeleteMapping("/{xhs}")
    public AjaxResult remove(@PathVariable String[] xhs) {
        return toAjax(wtglSjzrzdService.deleteWtglSjzrzdByXhs(xhs));
    }


    /**
     * 通过责任人id查询整个升级人数据
     */
    @GetMapping(value = "/getSjzrzdByZrrid/{zrrid}")
    public AjaxResult getSjzrzdByZrrid(@PathVariable("zrrid") String zrrid) {
        return success(wtglSjzrzdService.selectWtglSjzrzdByZrrid(zrrid));
    }

    /**
     * 问题升级的方法
     */
    @PostMapping("/wtsjOneZrr")
    public AjaxResult wtsjOneZrr(@RequestBody WtxxDTO wtxxDTO) {
        int i = wtglSjzrzdService.wtsjOneZrr(wtxxDTO);
        if (i == 0) {
            return AjaxResult.error("请确定升级责任人表的一级责任人的id是否正确");
        } else if (i == 500) {
            return AjaxResult.error("升级责任人已存在，问题不可升级");
        } else {
            return AjaxResult.success("升级成功");
        }
    }
}
