<template>
  <div style="padding: 10px;">
    <h2 style="text-align: center">Gantt echart</h2>
    <div class="app-container">
      <div ref="gantt" class="left-container"/>
    </div>
  </div>
</template>

<script>
import gantt1 from 'dhtmlx-gantt'  // 引入模块
import 'dhtmlx-gantt/codebase/dhtmlxgantt.css'
// import 'dhtmlx-gantt/codebase/locale/locale_cn'  // 本地化
export default {
  name: "ganttEchart",
  data() {
    return {
      tasks1: {
        data: [
          { id: 1, text: 'aaa', start_date: '2020-12-15', personName: '张总', duration: 3, progress: 0.6 ,color:"#6BC172"},
          { id: 2, text: 'bbb', start_date: '2020-12-18', personName: '李总', duration: 6, progress: 0.4 ,color:"#6BC172"},
          { id: 3, text: 'ab', start_date: '2020-12-18', personName: '赵总', duration: 3, progress: 0.2, parent: 2 },
          { id: 4, text: 'bb', start_date: '2020-12-21', personName: '赵总', duration: 3, progress: 0, parent: 2 }
        ],
        links1: [
          { id: 1, source: 1, target: 2, type: '0' }
        ]
      }
    }
  },
  mounted() {
    gantt1.config.xml_date = "%Y-%m-%d";
    // 在时间线上增加一行年份显示
    gantt1.config.subscales = [
      {
        unit: 'year',
        step: 1,
        date: '%Y'
      }
    ]
    // 初始化
    gantt1.init(this.$refs.gantt);
    // 数据解析
    gantt1.parse(this.tasks1);


  }
}
</script>

<style scoped>
.left-container {
  height: 600px;
}
</style>

