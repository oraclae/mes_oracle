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
          text: '任务1',
          start_date: '2023-10-09 08:00', // 开始时间
          end_date: '2023-10-09 09:00', // 结束时间
        },
        {
          id: 2,
          text: '任务2',
          start_date: '2023-10-09 09:30', // 开始时间
          end_date: '2023-10-09 11:00', // 结束时间
        },
      ],
    };
  },
  mounted() {
    gantt.config.xml_date = '%Y-%m-%d %H:%i'; // 设置日期时间格式
    gantt.init(this.$refs.ganttContainer);
    gantt.parse({
      data: this.tasks,
    });

    // 配置时间刻度
    gantt.config.scale_unit = 'minute';
    gantt.config.date_scale = '%H:%i';
    gantt.config.step = 30; // 步长为30分钟
  },
};
</script>

<style>
.gantt_container {
  width: 100%;
  height: 400px;
}
</style>
