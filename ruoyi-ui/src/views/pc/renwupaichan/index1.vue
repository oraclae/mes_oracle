<template>
  <div>
    <div ref="ganttContainer" style="height: 100vh"></div>
  </div>
</template>

<script>
import gantt from 'dhtmlx-gantt';
import 'dhtmlx-gantt/codebase/dhtmlxgantt.css';

export default {
  data() {
    return {
      /* render:"split" 子集显示在同一行中*/
      tasks: [
        {id: 1, render:"split", start_date: new Date(2022, 0, 1, 1, 0, 0), text: "任务一", end_date: new Date(2022, 0, 1, 4, 0, 0)},
        {id: 2, parent: 1, start_date: new Date(2022, 0, 1, 1, 0, 0), text: "阶段一", end_date: new Date(2022, 0, 1, 2, 0, 0)},
        {id: 3, parent: 1, start_date: new Date(2022, 0, 1, 3, 0, 0), text: "阶段二", end_date: new Date(2022, 0, 1, 4, 0, 0)}
      ]
    }
  },
  methods: {},
  mounted() {
    gantt.config.columns = [
      /*{name: 'text', label: '阶段名字', tree: true, width: '120'},
      {
        name: 'duration',
        label: '时长',
        align: 'center',
        template: function (obj) {
          return obj.duration + '分'
        },
      },*/
      {name: "start_date", label: "开始时间", align: "center"},

      {name: "end_date", label: "结束时间", align: "center"},

      {name: "text", label: "设备", align: "center"},
    ]

    /*启用任务拖拽改变持续时间*/
    gantt.config.drag_resize = true;

    /* 设置日期和时间的显示格式 */
    gantt.config.date_scale = '%H:%i';

    /*设置日期时间格式*/
    gantt.config.xml_date = '%Y-%m-%d %H:%i';

    /*将scale_unit设置为'hour'*/
    gantt.config.scale_unit = 'minute';

    /*将scale_size设置为1*/
    gantt.config.scale_step = 30;

    /*持续时间以小时为单位*/
    gantt.config.duration_unit = 'minute';


    /*
    初始化
     */
    gantt.init(this.$refs.ganttContainer);

    /*
     解析并加载甘特图数据。data参数是一个JavaScript对象数组，每个对象代表一个甘特图任务。
     */
    gantt.parse({
      data: this.tasks
    });

  }
}
</script>

<style scoped>

</style>
