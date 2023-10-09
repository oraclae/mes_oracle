<template>
  <div>
    <div ref="ganttContainer"></div>
  </div>
</template>

<script>
import gantt from 'dhtmlx-gantt';
import 'dhtmlx-gantt/codebase/dhtmlxgantt.css';

export default {
  data() {
    return {
      tasks: [
        {
          id: 1,
          text: '冒烟阶段',
          start_date: '2020-04-08 01:10:10',
          duration: 2, // 3小时，以分钟为单位
        },
        {
          id: 2,
          text: '单元测试',
          start_date: '2020-04-08 04:30:10',
          duration: 1, // 2小时，以分钟为单位
        },
        {
          id: 3,
          text: '回归测试',
          start_date: '2020-04-08 06:50:10',
          duration: 3, // 4小时，以分钟为单位
        },
      ],
    };
  },
  mounted() {
    gantt.config.scale_unit = 'minute'; // 设置时间单位为分钟
    gantt.config.date_scale = '%H:%i'; // 设置日期和时间的显示格式
    gantt.config.step = 1; // 步长为1
    gantt.config.xml_date = '%Y-%m-%d %H:%i:%s';
    gantt.init(this.$refs.ganttContainer);
    gantt.parse({
      data: this.tasks,
    });

    gantt.templates.date_scale = function (date) {
      return formatDate(date);
    };

    function formatDate(date) {
      const months = [
        '一月', '二月', '三月', '四月', '五月', '六月',
        '七月', '八月', '九月', '十月', '十一月', '十二月'
      ];
      const month = months[date.getMonth()];
      const day = date.getDate();

      return `${month}${day}日`;
    }
  },
};
</script>

<style>
.gantt_container {
  width: 100%;
  height: 400px;
}
</style>
