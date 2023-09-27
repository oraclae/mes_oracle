package com.ruoyi.project.pc.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.pc.mapper.DaRlMapper;
import com.ruoyi.project.pc.domain.DaRl;
import com.ruoyi.project.pc.service.IDaRlService;

/**
 * 日期Service业务层处理
 *
 * @author qxd
 * @date 2023-09-23
 */
@Service
@Slf4j
public class DaRlServiceImpl implements IDaRlService {
    @Autowired
    private DaRlMapper daRlMapper;

    /**
     * 查询日期
     *
     * @param id 日期主键
     * @return 日期
     */
    @Override
    public DaRl selectDaRlById(String id) {
        return daRlMapper.selectDaRlById(id);
    }

    /**
     * 查询日期列表
     *
     * @param daRl 日期
     * @return 日期
     */
    @Override
    public List<DaRl> selectDaRlList(DaRl daRl) {
        return daRlMapper.selectDaRlList(daRl);
    }

    /**
     * 新增日期
     *
     * @param daRl 日期
     * @return 结果
     */
    @Override
    public int insertDaRl(DaRl daRl) {
        return daRlMapper.insertDaRl(daRl);
    }

    /**
     * 修改日期
     *
     * @param daRl 日期
     * @return 结果
     */
    @Override
    public int updateDaRl(DaRl daRl) {
        return daRlMapper.updateDaRl(daRl);
    }

    /**
     * 批量删除日期
     *
     * @param ids 需要删除的日期主键
     * @return 结果
     */
    @Override
    public int deleteDaRlByIds(String[] ids) {
        return daRlMapper.deleteDaRlByIds(ids);
    }

    /**
     * 删除日期信息
     *
     * @param id 日期主键
     * @return 结果
     */
    @Override
    public int deleteDaRlById(String id) {
        return daRlMapper.deleteDaRlById(id);
    }

    /**
     * 生成日期的方法
     */
    @Override
    public void addGenerationDate(DaRl daRl) {
        String ksrq = daRl.getKsrq();
        String jsrq = daRl.getJsrq();
        List<String> list = generateDateArray(ksrq, jsrq);
        List<DaRl> daRls = new ArrayList<>();
        for (String s : list) {
            DaRl da = new DaRl();
            da.setId(UUID.randomUUID().toString());
            String week = getWeek(s);
            if (week == null) {
                log.info("选择的日期出现错误");
                return;
            }
            da.setXq(week);
            if ("星期日".equals(week) || "星期六".equals(week)) {
                da.setSfsb("否");
            } else {
                da.setSfsb("是");
            }
            da.setRq(s);
            daRls.add(da);
        }
        for (DaRl rl : daRls) {
            daRlMapper.insertDaRl(rl);
        }
    }

    /**
     * 根据开始时间和结束时间生成中间时间的集合
     * @param startDateStr 开始时间
     * @param endDateStr 结束时间
     * @return 生成日历的集合
     */
    public static List<String> generateDateArray(String startDateStr, String endDateStr) {
        List<String> dateArray = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);

            while (!calendar.getTime().after(endDate)) {
                dateArray.add(dateFormat.format(calendar.getTime()));
                calendar.add(Calendar.DATE, 1);
            }
        } catch (ParseException e) {
            log.info("生成日历的时间格式错误");
            e.printStackTrace();
        }
        return dateArray;
    }

    /**
     * 根据日期返回星期几
     */
    private static String getWeek(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(dateStr);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            String week = null;
            switch (dayOfWeek) {
                case Calendar.SUNDAY:
                    week = "星期日";
                break;
                case Calendar.MONDAY:
                    week = "星期一";
                    break;
                case Calendar.TUESDAY:
                    week = "星期二";
                    break;
                case Calendar.WEDNESDAY:
                    week = "星期三";
                    break;
                case Calendar.THURSDAY:
                    week = "星期四";
                    break;
                case Calendar.FRIDAY:
                    week = "星期五";
                    break;
                case Calendar.SATURDAY:
                    week = "星期六";
                    break;
                default:
                    System.out.println("无效日期");
            }
            return week;
        } catch (Exception e) {
            log.info("获取星期的方法的日期格式不正确");
            e.printStackTrace();
            return null;
        }
    }
}
