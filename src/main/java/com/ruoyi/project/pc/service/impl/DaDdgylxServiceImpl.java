package com.ruoyi.project.pc.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.ruoyi.project.pc.domain.DaBc;
import com.ruoyi.project.pc.domain.DaLzjscdd;
import com.ruoyi.project.pc.mapper.DaBcMapper;
import com.ruoyi.project.pc.mapper.DaLzjscddMapper;
import com.ruoyi.project.pc.mapper.DaRlMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.pc.mapper.DaDdgylxMapper;
import com.ruoyi.project.pc.domain.DaDdgylx;
import com.ruoyi.project.pc.service.IDaDdgylxService;

/**
 * 订单工艺路线Service业务层处理
 *
 * @author qxd
 * @date 2023-09-22
 */
@Service
@Slf4j
public class DaDdgylxServiceImpl implements IDaDdgylxService {
    @Autowired
    private DaDdgylxMapper daDdgylxMapper;
    @Autowired
    private DaLzjscddMapper daLzjscddMapper;
    @Autowired
    private DaRlMapper daRlMapper;
    @Autowired
    private DaBcMapper daBcMapper;

    /**
     * 查询订单工艺路线
     * @param id 订单工艺路线主键
     * @return 订单工艺路线
     */
    @Override
    public DaDdgylx selectDaDdgylxById(String id) {
        return daDdgylxMapper.selectDaDdgylxById(id);
    }

    /**
     * 查询订单工艺路线列表
     * @param daDdgylx 订单工艺路线
     * @return 订单工艺路线
     */
    @Override
    public List<DaDdgylx> selectDaDdgylxList(DaDdgylx daDdgylx) {
        return daDdgylxMapper.selectDaDdgylxList(daDdgylx);
    }

    /**
     * 新增订单工艺路线
     * @param daDdgylx 订单工艺路线
     * @return 结果
     */
    @Override
    public int insertDaDdgylx(DaDdgylx daDdgylx) {
        return daDdgylxMapper.insertDaDdgylx(daDdgylx);
    }

    /**
     * 修改订单工艺路线
     * @param daDdgylx 订单工艺路线
     * @return 结果
     */
    @Override
    public int updateDaDdgylx(DaDdgylx daDdgylx) {
        return daDdgylxMapper.updateDaDdgylx(daDdgylx);
    }

    /**
     * 批量删除订单工艺路线
     * @param ids 需要删除的订单工艺路线主键
     * @return 结果
     */
    @Override
    public int deleteDaDdgylxByIds(String[] ids) {
        return daDdgylxMapper.deleteDaDdgylxByIds(ids);
    }

    /**
     * 删除订单工艺路线信息
     * @param id 订单工艺路线主键
     * @return 结果
     */
    @Override
    public int deleteDaDdgylxById(String id) {
        return daDdgylxMapper.deleteDaDdgylxById(id);
    }

    /**
     * 排产方法
     */
    @Override
    public void pc(String lzjh) {
        String[] split = lzjh.split("/");
        //获取班次的每天总分钟
        List<DaBc> daBcs = daBcMapper.selectDaBcList(new DaBc());
        //根据订单号获得订单工艺路线的集合
        for (String s : split) {
            List<DaDdgylx> daDdgylxes = daDdgylxMapper.selectByLzjhDesc(s);
            String jhkssk = "";
            for (DaDdgylx daDdgylx : daDdgylxes) {
                //获得在职数量
                DaLzjscdd daLzjscdd = new DaLzjscdd();
                daLzjscdd.setScddh(daDdgylx.getDdh());
                List<DaLzjscdd> daLzjscdds = daLzjscddMapper.selectDaLzjscddList(daLzjscdd);
                long zzsl = 1;
                if (daLzjscdds.size() != 1) {
                    log.info("数据出现错误"+"生产订单号：{}",daLzjscdd.getScddh());
                    break;
                } else {
                    zzsl = daLzjscdds.get(0).getZzsl();
                }
                //计划结束时间
                String jhwcsj = daDdgylx.getJhwcsj();
                //如果计划开始时间有值，那么就是这条订单路线的开始，如果没有值那么就是这个条工艺路线的第二条数据
                if (jhwcsj == null) {
                    //给第二条的计划结束时间赋值
                    //在这里将处理时间的范围原因
                    daDdgylx.setJhwcsj(jhkssk);
                }
                Long jggs = daDdgylx.getJggs();
                Long zbgs = daDdgylx.getZbgs();
                //获得需要多少分钟
                long min = zzsl * jggs + zbgs;
                //判断是否上班
                //判断是否时间不对
                //jhkssk = isSatisfyCondition(daDdgylx.getJhwcsj(), min);
                jhkssk = twoIsSatisfyCondition(daDdgylx.getJhwcsj(), min,daBcs);
                if ("".equals(jhkssk)) {
                    log.info("班次出现错误，请重新确定,生产订单号：{}",daLzjscdd.getScddh());
                    break;
                }
                if ("计划结束时间填写错误".equals(jhkssk)) {
                    log.info("计划结束时间填写错误:生产订单号：{}",daLzjscdd.getScddh());
                    break;
                }
                //这里也处理时间的范围原因
                daDdgylx.setJhkssj(jhkssk);
            }
            for (DaDdgylx daDdgylx : daDdgylxes) {
                //int i = daDdgylxMapper.updateDaDdgylxList(daDdgylx);
            }
            for (DaDdgylx daDdgylx : daDdgylxes) {
                System.out.println(daDdgylx);
            }
        }

    }

    //判断时间是否在规则范围内
    private String isSatisfyCondition(String jhwcsj, long minutes) {
        String jhkssj = null;
        //获得计划完成时间和计划开始时间的差距为多少天和多少分钟
        long day = minutes / (11 * 60);//多少天
        int min = Math.toIntExact(minutes % (11 * 60));//多少分钟
        //判断是否这个日期放假
        String[] two = jhwcsj.split(" ");
        String dateString = two[0];//如2023-02-23
        //计算计划完成时间的时间差和上面的min比较
        String[] split = two[1].split(":");
        int hours_min = 0;
        int hours = Integer.parseInt(split[0]);
        if (hours > 17) {
            hours_min = (hours - 8) * 60 - 120;//差距多少分钟
            hours_min += Integer.parseInt(split[1]);//加上分钟的时间算出计划完成时间和八点的差距
        } else if (hours > 12 && hours < 17) {
            hours_min = (hours - 8) * 60 - 60;//差距多少分钟
            hours_min += Integer.parseInt(split[1]);//加上分钟的时间算出计划完成时间和八点的差距
        } else {
            hours_min = (hours - 8) * 60;//差距多少分钟
            hours_min += Integer.parseInt(split[1]);//加上分钟的时间算出计划完成时间和八点的差距
        }
        //判断。相差时间的分钟和计划解决时间的分钟谁大
        if (hours_min > min) {
            //如果计划解决时间的分钟大那么正常计算得出离八点多远
            int distance = hours_min - min;
            //如果在8点到12点的时候
            if (distance <= 240) {
                int hours_dq = distance / 60 + 8;//当前的时间的小时距离八点的位置
                int minutes_dq = distance % 60;//当前时间的分钟位置
                jhkssj = hours_dq + ":" + minutes_dq;
            } else if (distance <= 480) {
                int hours_dq = distance / 60 + 8 + 1;//当前的时间的小时距离八点的位置
                int minutes_dq = distance % 60;//当前时间的分钟位置
                jhkssj = hours_dq + ":" + minutes_dq;
            } else {
                int hours_dq = distance / 60 + 8 + 2;//当前的时间的小时距离八点的位置
                int minutes_dq = distance % 60;//当前时间的分钟位置
                jhkssj = hours_dq + ":" + minutes_dq;
            }

        } else {
            //如果计划解决时间的分钟小于正常计算的时间
            int distance = min - hours_min;
            //那么天数加1天
            day += 1;
            int hours_dq = distance / 60;//当前的时间的小时位置
            int minutes_dq = distance % 60;//当前时间的分钟位置
            //判断小时是否大于3的话那么就在一点到五点的位置
            if (hours_dq < 3) {
                if (minutes_dq == 0) {
                    hours_dq = 21 - hours_dq;  //在六点到九点的范围内
                } else {
                    hours_dq = 21 - hours_dq - 1;
                    minutes_dq = 60 - minutes_dq;
                }
            } else if (hours_dq > 3 && hours_dq <= 7) {
                if (minutes_dq == 0) {
                    hours_dq = 17 - (hours_dq - 3); //在一点和五点的范围内
                } else {
                    hours_dq = 17 - (hours_dq - 3) - 1; //在一点和五点的范围内
                    minutes_dq = 60 - minutes_dq;
                }
            } else {
                if (minutes_dq == 0) {
                    hours_dq = 12 - (hours_dq - 3 - 4);//在八点和十二点的范围内
                } else {
                    hours_dq = 12 - (hours_dq - 3 - 4) - 1;//在八点和十二点的范围内
                    minutes_dq = 60 - minutes_dq;
                }
            }
            //计算出时间出来
            jhkssj = hours_dq + ":" + minutes_dq;
        }
        //计算天数
        //查询数据库来判断是否放假
        //如果是的话那就拿上一条的不放假日期
        for (long i = 0; i < day; i++) {
            String timeReductionOf = getTimeReductionOf(dateString);
            //先在数据库中找到这个字符串是否上班，如果不上班那么在次执行减一天的操作
            String isSb = daRlMapper.selectByRq(timeReductionOf);
            while ("否".equals(isSb)) {
                timeReductionOf = getTimeReductionOf(timeReductionOf);
                isSb = daRlMapper.selectByRq(timeReductionOf);
            }
            dateString = timeReductionOf;
        }
        return dateString + " " + jhkssj;
    }
    //判断时间是否在规则范围内
    private String twoIsSatisfyCondition(String jhwcsj, long minutes,List<DaBc> daBcs) {
        String[] jhkssjStr = jhwcsj.split(" ");
        DaBc daBcZao = null;
        DaBc daBcWu = null;
        DaBc daBcWan = null;
        int minutesBc = 0;
        for (DaBc daBc : daBcs) {
            if ("早班".equals(daBc.getBcmc())) {
                daBcZao = daBc;
            } else if ("午班".equals(daBc.getBcmc())) {
                daBcWu = daBc;
            } else {
                daBcWan = daBc;
            }
            Integer minutes1 = getMinutes(daBc.getKssj(), daBc.getJssj());
            if (minutes1 == null) {
                minutesBc = 0;
                break;
            }
            minutesBc += minutes1;
        }
        //先判断班次差距的分钟为0的时候，那么出现错误
        if (minutesBc == 0) {
            return null;
        }

        //minutesBc 是班次一天的所有分钟
        //获取这个一道序需要多少天
        long day = minutes / minutesBc;
        //去掉多少天后剩余多少分钟；
        long min = minutes % minutesBc;
        //判断计划结束时间的时间判断在早中晚那个位置，并且和早办的开始时间 相差多少分钟，
        // 如果相差的时间小于min的话那么就往上加一天，如果小于那么就直接减
        Integer minutes1 = getMinutesToUnrest(daBcZao.getKssj(), jhkssjStr[1], daBcZao.getJssj(), daBcWu.getKssj(), daBcWu.getJssj(), daBcWan.getKssj());
        //计算计划结束时间和班检日期开始的相减
        if (minutes1 == null) {
            return null;
        }
        //最后的时间如8：00
        String dataTime = null;
        //判断计划完成时间和早班开始时间的差距
        //如果为正数那么不需要往前加一天了，如果为负数那么说明今天的
        if (minutes1 >= 0) {
            if (min - minutes1 < 0) {
                int num = Math.toIntExact(minutes1 - min);
                dataTime = getCountPlusinutes(daBcZao.getKssj(), num, daBcZao.getJssj(), daBcWu.getKssj(), daBcWu.getJssj(), daBcWan.getKssj());
            }else{
                day += 1;
                int num = Math.toIntExact(minutesBc - (min - minutes1));
                dataTime = getCountPlusinutes(daBcZao.getKssj(), num, daBcZao.getJssj(), daBcWu.getKssj(), daBcWu.getJssj(), daBcWan.getKssj());
            }
        } else {
            log.info("计划结束时间填写错误");
            return "计划结束时间填写错误";
        }
        //计划结束时间的日期
        String dateString = jhkssjStr[0];
        //计算天数
        //查询数据库来判断是否放假
        //如果是的话那就拿上一条的不放假日期
        for (long i = 0; i < day; i++) {
            String timeReductionOf = getTimeReductionOf(dateString);
            //先在数据库中找到这个字符串是否上班，如果不上班那么在次执行减一天的操作
            String isSb = daRlMapper.selectByRq(timeReductionOf);
            while ("否".equals(isSb)) {
                timeReductionOf = getTimeReductionOf(timeReductionOf);
                isSb = daRlMapper.selectByRq(timeReductionOf);
            }
            dateString = timeReductionOf;
        }
        return dateString+" "+dataTime;
    }

    //开始时间和结束时间计算相差多少分钟
    private Integer getMinutes(String start, String end) {
        if ((start == null || "".equals(start)) && (end == null || "".equals(end))) {
            return null;
        }
        // 创建两个Calendar对象，表示两个时间点
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        String[] starts = start.split(":");
        String[] ends = end.split(":");
        // 设置第一个时间为8:00
        calendar1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(starts[0]));
        calendar1.set(Calendar.MINUTE, Integer.parseInt(starts[1]));
        // 设置第二个时间为12:00
        calendar2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(ends[0]));
        calendar2.set(Calendar.MINUTE, Integer.parseInt(ends[1]));

        // 获取两个时间的毫秒值
        long timeInMillis1 = calendar1.getTimeInMillis();
        long timeInMillis2 = calendar2.getTimeInMillis();

        // 计算时间差（毫秒）
        long timeDifferenceMillis = timeInMillis2 - timeInMillis1;

        // 计算时间差（分钟）并强制转换为int
        return  (int) (timeDifferenceMillis / (60 * 1000));
    }

    //计算两个时间的差值，如果在休息范围内减去休息
    private static Integer getMinutesToUnrest(String start, String end, String restTime, String restTimeToEnd, String restTimeTwo, String restTimeToEndTwo) {
        try {
            if (start == null || "".equals(start)&&
                end==null||"".equals(end)&&
                restTime==null||"".equals(restTime)&&
                restTimeToEnd==null||"".equals(restTimeToEnd)&&
                restTimeTwo==null||"".equals(restTimeTwo)&&
                restTimeToEndTwo==null||"".equals(restTimeToEndTwo)
            ) {
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            // 创建 Calendar 对象，表示起始时间和结束时间
            Calendar startTime = Calendar.getInstance();
            Calendar endTime = Calendar.getInstance();

            // 设置起始时间为
            startTime.setTime(sdf.parse(start));

            // 设置结束时间为
            endTime.setTime(sdf.parse(end));

            // 计算总分钟数
            long totalMinutes = (endTime.getTimeInMillis() - startTime.getTimeInMillis()) / (60 * 1000);

            // 休息时间段1（假设从12:00到13:00为休息时间）
            Calendar breakStartTime1 = Calendar.getInstance();
            Calendar breakEndTime1 = Calendar.getInstance();
            breakStartTime1.setTime(sdf.parse(restTime));
            breakEndTime1.setTime(sdf.parse(restTimeToEnd));

            // 休息时间段2（假设从15:30到16:00为休息时间）
            Calendar breakStartTime2 = Calendar.getInstance();
            Calendar breakEndTime2 = Calendar.getInstance();
            breakStartTime2.setTime(sdf.parse(restTimeTwo));
            breakEndTime2.setTime(sdf.parse(restTimeToEndTwo));

            // 计算休息时间段1的分钟数
            long breakMinutes1 = (breakEndTime1.getTimeInMillis() - breakStartTime1.getTimeInMillis()) / (60 * 1000);

            // 计算休息时间段2的分钟数
            long breakMinutes2 = (breakEndTime2.getTimeInMillis() - breakStartTime2.getTimeInMillis()) / (60 * 1000);

            // 扣除休息时间的分钟数
            //判断是否在休息时间内
            if (Integer.parseInt(end.split(":")[0]) > Integer.parseInt(restTimeTwo.split(":")[0])) {
                return Math.toIntExact(totalMinutes - breakMinutes1 - breakMinutes2);
            } else if (Integer.parseInt(end.split(":")[0]) > Integer.parseInt(restTime.split(":")[0])&&
                    Integer.parseInt(end.split(":")[0]) < Integer.parseInt(restTimeTwo.split(":")[0])) {
                return Math.toIntExact(totalMinutes - breakMinutes1);
            } else {
                return Math.toIntExact(totalMinutes);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    //计算两个时间的差值，如果在休息范围内减去休息正序
    private static Integer getMinutesToUnrestzheng(String start, String end, String restTime, String restTimeToEnd, String restTimeTwo, String restTimeToEndTwo) {
        try {
            if (start == null || "".equals(start)&&
                    end==null||"".equals(end)&&
                    restTime==null||"".equals(restTime)&&
                    restTimeToEnd==null||"".equals(restTimeToEnd)&&
                    restTimeTwo==null||"".equals(restTimeTwo)&&
                    restTimeToEndTwo==null||"".equals(restTimeToEndTwo)
            ) {
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            // 创建 Calendar 对象，表示起始时间和结束时间
            Calendar startTime = Calendar.getInstance();
            Calendar endTime = Calendar.getInstance();

            // 设置起始时间为
            startTime.setTime(sdf.parse(start));

            // 设置结束时间为
            endTime.setTime(sdf.parse(end));

            // 计算总分钟数
            long totalMinutes = (endTime.getTimeInMillis() - startTime.getTimeInMillis()) / (60 * 1000);

            // 休息时间段1（假设从12:00到13:00为休息时间）
            Calendar breakStartTime1 = Calendar.getInstance();
            Calendar breakEndTime1 = Calendar.getInstance();
            breakStartTime1.setTime(sdf.parse(restTime));
            breakEndTime1.setTime(sdf.parse(restTimeToEnd));

            // 休息时间段2（假设从15:30到16:00为休息时间）
            Calendar breakStartTime2 = Calendar.getInstance();
            Calendar breakEndTime2 = Calendar.getInstance();
            breakStartTime2.setTime(sdf.parse(restTimeTwo));
            breakEndTime2.setTime(sdf.parse(restTimeToEndTwo));

            // 计算休息时间段1的分钟数
            long breakMinutes1 = (breakEndTime1.getTimeInMillis() - breakStartTime1.getTimeInMillis()) / (60 * 1000);

            // 计算休息时间段2的分钟数
            long breakMinutes2 = (breakEndTime2.getTimeInMillis() - breakStartTime2.getTimeInMillis()) / (60 * 1000);

            // 扣除休息时间的分钟数
            //判断是否在休息时间内
            if (Integer.parseInt(start.split(":")[0]) < Integer.parseInt(restTime.split(":")[0])) {
                return Math.toIntExact(totalMinutes - breakMinutes1 - breakMinutes2);
            } else if (Integer.parseInt(start.split(":")[0]) > Integer.parseInt(restTimeToEnd.split(":")[0])&&
                    Integer.parseInt(start.split(":")[0]) < Integer.parseInt(restTimeTwo.split(":")[0])) {
                return Math.toIntExact(totalMinutes - breakMinutes2);
            } else {
                return Math.toIntExact(totalMinutes);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //给一个起始的时间如8：00 在给一个分钟，计算出时间 给正数是加负数是减
    private static String getCountPlusinutes(String start,int minutes, String restTime, String restTimeToEnd, String restTimeTwo, String restTimeToEndTwo) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            // 创建 Calendar 对象，表示启示时间
            Calendar startTime = Calendar.getInstance();
            startTime.setTime(sdf.parse(start)); // 启示时间为8:00

            // 添加指定分钟数
            int minutesToAdd = minutes;

            // 创建 Calendar 对象，表示第一个休息时间段（12:00到13:00）
            Calendar breakStartTime1 = Calendar.getInstance();
            Calendar breakEndTime1 = Calendar.getInstance();
            breakStartTime1.setTime(sdf.parse(restTime));
            breakEndTime1.setTime(sdf.parse(restTimeToEnd));

            // 创建 Calendar 对象，表示第二个休息时间段（17:00到18:00）
            Calendar breakStartTime2 = Calendar.getInstance();
            Calendar breakEndTime2 = Calendar.getInstance();
            breakStartTime2.setTime(sdf.parse(restTimeTwo));
            breakEndTime2.setTime(sdf.parse(restTimeToEndTwo));

            // 考虑休息时间段，分别将分钟数减去休息时间段的时长
            while (minutesToAdd > 0) {
                if (startTime.before(breakStartTime1)) {
                    // 如果当前时间在第一个休息时间段之前
                    int minutesUntilBreak1 = (int) ((breakStartTime1.getTimeInMillis() - startTime.getTimeInMillis()) / (60 * 1000));
                    if (minutesUntilBreak1 >= minutesToAdd) {
                        // 如果剩余分钟数小于等于第一个休息时间段的时长
                        startTime.add(Calendar.MINUTE, minutesToAdd);
                        break;
                    } else {
                        // 否则，扣除第一个休息时间段的时长并继续
                        minutesToAdd -= minutesUntilBreak1;
                        startTime.setTime(breakEndTime1.getTime());
                    }
                } else if (startTime.before(breakStartTime2)) {
                    // 如果当前时间在第一个休息时间段之后，在第二个休息时间段之前，同上
                    int minutesUntilBreak2 = (int) ((breakStartTime2.getTimeInMillis() - startTime.getTimeInMillis()) / (60 * 1000));
                    if (minutesUntilBreak2 >= minutesToAdd) {
                        startTime.add(Calendar.MINUTE, minutesToAdd);
                        break;
                    } else {
                        minutesToAdd -= minutesUntilBreak2;
                        startTime.setTime(breakEndTime2.getTime());
                    }
                } else {
                    // 如果当前时间在第二个休息时间段之后
                    startTime.add(Calendar.MINUTE, minutesToAdd);
                    break;
                }
            }

            // 获取计算后的时间
            return sdf.format(startTime.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //执行减一天的方法
    public String getTimeReductionOf(String dateString) {

        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 将初始日期字符串解析为 LocalDate 对象
        LocalDate date = LocalDate.parse(dateString, formatter);

        // 每次减去一天
        date = date.minusDays(1);

        // 将结果日期对象格式化为字符串
        return date.format(formatter);
    }
    //执行加一天的方法
    public String getTimeAddDayOf(String dateString) {

        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 将初始日期字符串解析为 LocalDate 对象
        LocalDate date = LocalDate.parse(dateString, formatter);

        // 每次减去一天
        date = date.plusDays(1);

        // 将结果日期对象格式化为字符串
        return date.format(formatter);
    }

    /**
     * 设备排产
     */
    @Override
    public void sbpc(String lzjh,String dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //将lzjh切割
        String[] split = lzjh.split("/");
        if (dateTime == null || "".equals(dateTime)) {
            dateTime = simpleDateFormat.format(new Date());
        }
        List<DaBc> daBcs = daBcMapper.selectDaBcList(new DaBc());
        for (String s : split) {
            List<DaDdgylx> daDdgylxes = daDdgylxMapper.selectByLzjh(s);
            String jhkssj = null;
            for (DaDdgylx daDdgylx : daDdgylxes) {
                long zzsl = 1;
                {
                    //获得在职数量
                    DaLzjscdd daLzjscdd = new DaLzjscdd();
                    daLzjscdd.setScddh(daDdgylx.getDdh());
                    List<DaLzjscdd> daLzjscdds = daLzjscddMapper.selectDaLzjscddList(daLzjscdd);
                    if (daLzjscdds.size() != 1) {
                        log.info("数据出现错误" + "生产订单号：{}", daLzjscdd.getScddh());
                        break;
                    } else {
                        zzsl = daLzjscdds.get(0).getZzsl();
                    }
                }
                Long jggs = daDdgylx.getJggs();
                Long zbgs = daDdgylx.getZbgs();
                //获得需要多少分钟
                long min = zzsl * jggs + zbgs;
                //获取计划在制数量
                if (jhkssj == null) {
                    jhkssj = dateTime;
                }
                daDdgylx.setJhkssj(jhkssj);
                //通过开始时间计算结束时间
                jhkssj = getJiSuanTime(jhkssj, min, daBcs);
                daDdgylx.setJhwcsj(jhkssj);
            }
            for (DaDdgylx daDdgylx : daDdgylxes) {
                log.info("数据：{}", daDdgylx);
            }
        }

    }

    private void selectList(String ddh) {
        List<DaDdgylx> daDdgylxes = daDdgylxMapper.selectByLzjh(ddh);

    }

    private String getJiSuanTime(String jhkssj, long minutes, List<DaBc> daBcs) {
        String[] jhkssjStr = jhkssj.split(" ");
        DaBc daBcZao = null;
        DaBc daBcWu = null;
        DaBc daBcWan = null;
        int minutesBc = 0;
        for (DaBc daBc : daBcs) {
            if ("早班".equals(daBc.getBcmc())) {
                daBcZao = daBc;
            } else if ("午班".equals(daBc.getBcmc())) {
                daBcWu = daBc;
            } else {
                daBcWan = daBc;
            }
            Integer minutes1 = getMinutes(daBc.getKssj(), daBc.getJssj());
            if (minutes1 == null) {
                minutesBc = 0;
                break;
            }
            minutesBc += minutes1;
        }
        //先判断班次差距的分钟为0的时候，那么出现错误
        if (minutesBc == 0) {
            return null;
        }
        //判断当前工序的生产时间和一天的分钟做对比，如果一天大那么再这一天之内，如果大于一天分钟那么大于一天
        //minutesBc 是班次一天的所有分钟
        //获取这个一道序需要多少天
        long day = minutes / minutesBc;
        //去掉多少天后剩余多少分钟；
        long min = minutes % minutesBc;
        //判断计划结束时间的时间判断在早中晚那个位置，并且和早办的开始时间 相差多少分钟，
        // 如果相差的时间小于min的话那么就往上加一天，如果小于那么就直接减
        Integer minutes1 = getMinutesToUnrestzheng(jhkssjStr[1], daBcWan.getJssj(), daBcZao.getJssj(), daBcWu.getKssj(), daBcWu.getJssj(), daBcWan.getKssj());
        String dataTime = null;
        if (minutes1 >= 0) {
            if (min - minutes1 < 0) {
                int num = Math.toIntExact(min);
                dataTime = getCountPlusinutes(jhkssjStr[1], num, daBcZao.getJssj(), daBcWu.getKssj(), daBcWu.getJssj(), daBcWan.getKssj());
            }else{
                day += 1;
                int num = Math.toIntExact(min - minutes1);
                dataTime = getCountPlusinutes(daBcZao.getKssj(), num, daBcZao.getJssj(), daBcWu.getKssj(), daBcWu.getJssj(), daBcWan.getKssj());
            }
        } else {
            log.info("计算时间出现错误");
            return null;
        }
        //计划结束时间的日期
        String dateString = jhkssjStr[0];
        //计算天数
        //查询数据库来判断是否放假
        //如果是的话那就拿上一条的不放假日期
        for (long i = 0; i < day; i++) {
            String timeReductionOf = getTimeAddDayOf(dateString);
            //先在数据库中找到这个字符串是否上班，如果不上班那么在次执行减一天的操作
            String isSb = daRlMapper.selectByRq(timeReductionOf);
            while ("否".equals(isSb)) {
                timeReductionOf = getTimeAddDayOf(timeReductionOf);
                isSb = daRlMapper.selectByRq(timeReductionOf);
            }
            dateString = timeReductionOf;
        }
        return dateString+" "+dataTime;
    }

    public static void main(String[] args) {
        String countPlusinutes = getCountPlusinutes("18:25", 55, "12:00", "13:00", "17:00", "18:00");
        System.out.println(countPlusinutes);
        Integer minutesToUnrest = getMinutesToUnrest("18:25", "21:00", "12:00", "13:00", "17:00", "18:00");
        System.out.println(minutesToUnrest);
    }
}
