package com.ruoyi.project.question.controller;

import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.question.domain.ZhiNengWenDaDTO;
import com.ruoyi.project.question.service.IZhiNengWenDaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: LiHai
 * Date: 2023/7/25
 * Description:
 */
@RestController
@RequestMapping
public class ZhiNengWenDaController extends BaseController {
    @Autowired
    private IZhiNengWenDaService zhiNengWenDaService;

    /**
     * 查询
     */
    @PostMapping(value = "/zhineng/selectDataByNotId")
    public AjaxResult selectDataByNotId(@RequestBody ZhiNengWenDaDTO zhiNengWenDaDTO)
    {
        return success(zhiNengWenDaService.selectDataByNotId(zhiNengWenDaDTO));
    }


}
