<template>
  <div style="padding: 10px;">
    <h2 style="text-align: center">Gantt echart</h2>
    <div class="app-container">
      <div ref="gantt" class="left-container"/>
    </div>
  </div>
</template>

<script>
import gantt from 'dhtmlx-gantt'  // 引入模块
import 'dhtmlx-gantt/codebase/dhtmlxgantt.css'
// import 'dhtmlx-gantt/codebase/locale/locale_cn'  // 本地化
export default {
  name: "ganttEchart",
  data() {
    return {
      tasks: {
        data: [
          { id: 1, text: 'Task #1', start_date: '2020-12-15', personName: '张总', duration: 3, progress: 0.6 ,color:"#6BC172"},
          { id: 2, text: 'Task #2', start_date: '2020-12-18', personName: '李总', duration: 6, progress: 0.4 ,color:"#6BC172"},
          { id: 3, text: 'Task #2-1', start_date: '2020-12-18', personName: '赵总', duration: 3, progress: 0.2, parent: 2 },
          { id: 4, text: 'Task #2-2', start_date: '2020-12-21', personName: '赵总', duration: 3, progress: 0, parent: 2 }
        ],
        links: [
          { id: 1, source: 1, target: 2, type: '0' }
        ]
      }
    }
  },
  mounted() {
    gantt.config.xml_date = "%Y-%m-%d";
    // 在时间线上增加一行年份显示
    gantt.config.subscales = [
      {
        unit: 'year',
        step: 1,
        date: '%Y'
      }
    ]
    // 初始化
    gantt.init(this.$refs.gantt);
    // 数据解析
    gantt.parse(this.tasks);


  }
}
</script>

<style scoped>
.left-container {
  height: 600px;
}
</style>

