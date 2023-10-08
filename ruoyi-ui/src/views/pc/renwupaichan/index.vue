<!--
 * @Author: your name
 * @Date: 2021-07-22 11:01:08
 * @LastEditTime: 2022-03-16 14:42:54
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \internetPlatform\src\views\iot\systemManage\test2.vue
-->
<template>
  <div>
    <div ref="gantt" style="height:80vh" />
  </div>
</template>

<script>
import gantt from 'dhtmlx-gantt'
import 'dhtmlx-gantt/codebase/dhtmlxgantt.css'
export default {
  data() {
    return {
      // 甘特图配置
      tasks: {
        data: [
          {
            id: 11,
            text: 'Project #1',
            type: gantt.config.types.project,
            progress: 0.6,
            open: true,
          },

          {
            id: 12,
            text: 'Task #1',
            start_date: '03-04-2022',
            duration: '5',
            parent: '11',
            progress: 1,
            open: true,
          },
          {
            id: 13,
            text: 'Task #2',
            start_date: '03-04-2022',
            type: gantt.config.types.project,
            parent: '11',
            progress: 0.5,
            open: true,
          },
          {
            id: 14,
            text: 'Task #3',
            start_date: '02-04-2022',
            duration: '6',
            parent: '11',
            progress: 0.8,
            open: true,
          },
          {
            id: 15,
            text: 'Task #4',
            type: gantt.config.types.project,
            parent: '11',
            progress: 0.2,
            open: true,
          },
          {
            id: 16,
            text: 'Final milestone',
            start_date: '15-04-2022',
            type: gantt.config.types.milestone,
            parent: '11',
            progress: 0,
            open: true,
          },

          {
            id: 17,
            text: 'Task #2.1',
            start_date: '03-04-2022',
            duration: '2',
            parent: '13',
            progress: 1,
            open: true,
          },
          {
            id: 18,
            text: 'Task #2.2',
            start_date: '06-04-2022',
            duration: '3',
            parent: '13',
            progress: 0.8,
            open: true,
          },
          {
            id: 19,
            text: 'Task #2.3',
            start_date: '10-04-2022',
            duration: '4',
            parent: '13',
            progress: 0.2,
            open: true,
          },
          {
            id: 20,
            text: 'Task #2.4',
            start_date: '10-04-2022',
            duration: '4',
            parent: '13',
            progress: 0,
            open: true,
          },
          {
            id: 21,
            text: 'Task #4.1',
            start_date: '03-04-2022',
            duration: '4',
            parent: '15',
            progress: 0.5,
            open: true,
          },
          {
            id: 22,
            text: 'Task #4.2',
            start_date: '03-04-2022',
            duration: '4',
            parent: '15',
            progress: 0.1,
            open: true,
          },
          {
            id: 23,
            text: 'Mediate milestone',
            start_date: '14-04-2022',
            type: gantt.config.types.milestone,
            parent: '15',
            progress: 0,
            open: true,
          },
        ],
        links: [
          { id: '10', source: '11', target: '12', type: '1' },
          { id: '11', source: '11', target: '13', type: '1' },
          { id: '12', source: '11', target: '14', type: '1' },
          { id: '13', source: '11', target: '15', type: '1' },
          { id: '14', source: '23', target: '16', type: '0' },
          { id: '15', source: '13', target: '17', type: '1' },
          { id: '16', source: '17', target: '18', type: '0' },
          { id: '17', source: '18', target: '19', type: '0' },
          { id: '18', source: '19', target: '20', type: '0' },
          { id: '19', source: '15', target: '21', type: '2' },
          { id: '20', source: '15', target: '22', type: '2' },
          { id: '21', source: '15', target: '23', type: '0' },
        ],
      },
    }
  },

  mounted() {
    this.init()
  },
  methods: {
    // 初始化
    init() {
      // 自动延长时间刻度
      gantt.config.fit_tasks = true
      // 允许拖放
      gantt.config.drag_project = true
      // 定义时间格式
      gantt.config.scales = [
        { unit: 'month', step: 1, date: '%F, %Y' },
        { unit: 'day', step: 1, date: '%j, %D' },
      ]
      // 添加弹窗属性
      gantt.config.lightbox.sections = [
        {
          name: 'description',
          height: 70,
          map_to: 'text',
          type: 'textarea',
          focus: true,
        },
        { name: 'type', type: 'typeselect', map_to: 'type' },
        { name: 'time', type: 'duration', map_to: 'auto' },
      ]
      // 初始化
      gantt.init(this.$refs.gantt)
      // 数据解析
      gantt.parse(this.tasks)
    },
  },
}
</script>

<style lang="scss" scoped>
.firstLevelTask {
  border: none;
  .gantt_task_content {
    // font-weight: bold;
    font-size: 13px;
  }
}

.secondLevelTask {
  border: none;
}

.thirdLevelTask {
  border: 2px solid #da645d;
  color: #da645d;
  background: #da645d;
}

.milestone-default {
  border: none;
  background: rgba(0, 0, 0, 0.45);
}

.milestone-unfinished {
  border: none;
  background: #5692f0;
}

.milestone-finished {
  border: none;
  background: #84bd54;
}

.milestone-canceled {
  border: none;
  background: #da645d;
}

html,
body {
  margin: 0px;
  padding: 0px;
  height: 100%;
  overflow: hidden;
}

.container {
  height: 900px;
  .left-container {
    height: 100%;
  }
}

.left-container {
  height: 600px;
}

.gantt_scale_line {
  border-top: 0;
}

.weekend {
  //background:#f4f7f4!important;
  color: #000000 !important;
}

.gantt_selected .weekend {
  background: #f7eb91 !important;
}

.gantt_task_content {
  text-align: left;
  padding-left: 10px;
}

//上面任务条样式
.gantt_task_scale {
  height: 45px !important;
}

.gantt_task .gantt_task_scale .gantt_scale_cell {
  line-height: 30px !important;
  height: 28px !important;
}
</style>

