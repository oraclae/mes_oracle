package com.ruoyi.project.question.controller;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.question.service.ISyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.ruoyi.framework.web.domain.AjaxResult.success;

/**
 * Author: LiHai
 * Date: 2023/9/11
 * Description:
 */
@RestController
@RequestMapping("/question/sy")
public class SyController {
    @Autowired
    private ISyService syService;

    /**
     * 获取问题类别细类
     */
    @GetMapping("/getsl")
    public AjaxResult getWtlbXlList()
    {
        return success(syService.getSl());
    }
}
