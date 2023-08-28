package com.ruoyi.project.question.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.project.question.domain.Gjzzd;
import com.ruoyi.project.question.service.IGjzzdService;
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
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 关键字字典Controller
 * 
 * @author LiHai
 * @date 2023-08-25
 */
@RestController
@RequestMapping("/system/gjzzd")
public class GjzzdController extends BaseController
{
    @Autowired
    private IGjzzdService gjzzdService;

    /**
     * 查询关键字字典列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Gjzzd gjzzd)
    {
        startPage();
        List<Gjzzd> list = gjzzdService.selectGjzzdList(gjzzd);
        return getDataTable(list);
    }

    /**
     * 导出关键字字典列表
     */
    @Log(title = "关键字字典", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Gjzzd gjzzd)
    {
        List<Gjzzd> list = gjzzdService.selectGjzzdList(gjzzd);
        ExcelUtil<Gjzzd> util = new ExcelUtil<Gjzzd>(Gjzzd.class);
        util.exportExcel(response, list, "关键字字典数据");
    }

    /**
     * 获取关键字字典详细信息
     */
    @GetMapping(value = "/{xh}")
    public AjaxResult getInfo(@PathVariable("xh") String xh)
    {
        return success(gjzzdService.selectGjzzdByXh(xh));
    }

    /**
     * 新增关键字字典
     */
    @Log(title = "关键字字典", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Gjzzd gjzzd)
    {
        int i = gjzzdService.insertGjzzd(gjzzd);
        if (i>0){
            return toAjax(i);
        }else {
            return AjaxResult.error("关键字“"+gjzzd.getGjz()+"”已存在");
        }
    }

    /**
     * 修改关键字字典
     */
    @Log(title = "关键字字典", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Gjzzd gjzzd)
    {
        return toAjax(gjzzdService.updateGjzzd(gjzzd));
    }

    /**
     * 删除关键字字典
     */
    @Log(title = "关键字字典", businessType = BusinessType.DELETE)
	@DeleteMapping("/{xhs}")
    public AjaxResult remove(@PathVariable String[] xhs)
    {
        return toAjax(gjzzdService.deleteGjzzdByXhs(xhs));
    }
}
