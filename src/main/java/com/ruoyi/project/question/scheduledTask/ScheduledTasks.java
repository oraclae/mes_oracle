package com.ruoyi.project.question.scheduledTask;

import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.project.question.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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
public class ScheduledTasks extends BaseController {

    @Autowired
    private QuestionService questionService;

    //@Scheduled(cron = "0 0 1 * * ?") //每天凌晨一点开始执行
    @PostConstruct
    private void shengjizrr() throws Exception {
        log.debug("升级责任人开始");
        questionService.shengjizrr();
        log.debug("升级责任人结束");
    }

    //@Scheduled(cron = "0 0 23 * * ?") //每天凌晨12点开始执行
    //@PostConstruct
    private void problemDays() throws Exception {
        log.debug("计算问题天数开始");
        questionService.problemDays();
        log.debug("计算问题天数结束");
    }

    @Scheduled(cron = "0 0 23 ? * 0") //每天凌晨12点开始执行
    //@PostConstruct
    private void clearFeedbackRecord() throws Exception {
        log.debug("清空反馈记录开始");
        questionService.clearFeedbackRecord();
        log.debug("清空反馈记录结束");
    }
}
