package com.ruoyi.project.pc.scheduledTask;

import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.project.pc.service.IDaDdgylxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

/**
 * Author: QXD
 * Date: 2023/6/20 10:28
 * Description:
 * Version:
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class ScheduledTask extends BaseController {

    @Autowired
    private IDaDdgylxService daDdgylxService;

    //@PostConstruct
    public void pc() {
        System.out.println("开始");
        String lzjh = "D000000046";
        daDdgylxService.pc(lzjh);
        System.out.println("结束");
    }

    //@PostConstruct
    public void sbpc() {
        System.out.println("开始设备排产");
        String lzjh = "D000000046";
        daDdgylxService.sbpc(lzjh, "2023-09-26 10:00");
        System.out.println("结束设备排产");
    }
}
