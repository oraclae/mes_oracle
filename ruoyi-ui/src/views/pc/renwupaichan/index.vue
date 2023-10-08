<template>
  <div class="app-container">
    <div class="select-wrap">
      <el-select v-model="unit" placeholder="请选择" @change="selectChange">
        <el-option label="日" value="day"></el-option>
        <el-option label="月" value="month"></el-option>
        <el-option label="年" value="year"></el-option>
      </el-select>
    </div>
    <div ref="gantt" class="gantt-container"></div>
  </div>
</template>
<script>
import {gantt} from 'dhtmlx-gantt';
import "dhtmlx-gantt/codebase/dhtmlxgantt.css"
import { listGannt } from "@/api/report/gannt";
export default {
  name: "Renwupaichan",
  data() {
    return {
      tasks: {
        data : [],
        links : [],
      },
      unit: "day"
    };
  },
  mounted() {
    //获取任务列表
    this.getList();
  },
  methods: {
    //标记标签
    todayMaker(){
      var dateToStr = gantt.date.date_to_str(gantt.config.task_date);
      gantt.addMarker({
        start_date: myDate,
        css: "today",
        text: "NowWeek",
        title: dateToStr(myDate)
      });
    },
    /** 查询任务列表 */
    getList() {
      this.loading = true;
      listGannt().then(response => {
        this.tasks.data = response.data.data.map(function(current, ind, arry) {
          var newObj = {}
          if (current.type) { //存在type字段 说明非一级菜单，判断阶段的具体类型 设置不同颜色
            if (current.type === 'project') { //项目
              newObj = Object.assign({}, current, {
                'color': '#fcca02',
                bar_height: "20"
              })
            } else if (current.type === 'milestone1') { //阶段
              newObj = Object.assign({}, current, {
                'color': '#fec0dc',
                bar_height: "20"//设置进度条的高度
              })
            } else if (current.type === 'task') { //任务
              if(current.level == null){
                newObj = Object.assign({}, current, {
                  'color': '#12E27A',
                  bar_height: "20"
                })
              }else{
                newObj = Object.assign({}, current, {
                  'color': '#62ddd4'
                })
              }
            } else if (current.type === 4) {
              newObj = Object.assign({}, current, {
                'color': '#d1a6ff'
              })
            }
          } else { //一级菜单是蓝色的
            newObj = Object.assign({}, current, {
              'color': '#5692f0'
            })
          }
          return newObj
        });
        this.tasks.links = response.data.links
        this.initGannt()
      });
    },
    initGannt() {
      //自适应甘特图的尺寸大小, 使得在不出现滚动条的情况下, 显示全部任务
      gantt.config.autosize = true
      //只读模式
      gantt.config.readonly = true
      //是否显示左侧树表格
      gantt.config.show_grid = true
      //表格列设置
      gantt.config.columns = [
        { name: 'text', label: '名称', tree: true, align: 'left', width: '200' },
        { name: 'start_date', label: '计划开始时间', align: 'center', width: '120' },
        { name: 'end_date', label: '计划结束时间', align: "center", width: '120' },
        { name: 'planWorkday', label: '时长(天)', align: 'center'},
      ];
      var monthScaleTemplate = function(date) {
        var dateToStr = gantt.date.date_to_str("第 %m 月");
        return dateToStr(date);
      };
      gantt.config.subscales = [
        {
          unit: "month",
          step: 1,
          template: monthScaleTemplate
        },
        {
          unit: "day",
          step: 1,
          format: "%d"
        },
      ];
      gantt.plugins({
        tooltip: true, //鼠标划过任务是否显示明细
        marker: true //添加垂直标记
      });
      // 鼠标滑过任务显示内容
      gantt.attachEvent("onGanttReady", function() {
        var tooltips = gantt.ext.tooltips;
        gantt.templates.tooltip_text = function(start, end, task) {
          return task.text + "<br/>" +gantt.templates.tooltip_date_format(start)
        };
      });
      //设置任务条进度内容
      gantt.templates.progress_text = function(start, end, task) {
        return "<div style='text-align:left;color:#fff;padding-left:20px'>" + Math.round(task.progress * 100) +
          "% </div>";
      };

      //任务条显示内容定义在甘特图初始化之前
      gantt.templates.task_text = function(start, end, task) {
        // return task.text + '(' + task.duration + '天)';Math.floor(*100/24)/100
        return "<div style='text-align:center;color:#fff'>" + task.text + '(' + task.planWorkday + '天)' +
          "</div>";
      }
      //任务条上的文字大小 以及取消border自带样式
      gantt.templates.task_class = function(start, end, item) {
        // console.log(item);
        var id = item.id;
        var arr = id.split("_");
        return (arr[0] === 'subtask' || arr[0] === 'weekly')? 'thirdLevelTask' : 'secondLevelTask'
      }

      var that = this;
      //双击事件
      gantt.attachEvent("onTaskDblClick", function (id) {
        //注意这里对data里面的属性操作要在外层定义一个变量指向vue的this。这里面的this是gantt的this
      })

      gantt.templates.grid_folder = function(item) {
        return "<div class='gantt_tree_icon gantt_folder_" +
          (item.$open ? "open" : "closed") + "'></div>";
      };
      gantt.config.layout = {
        css: "gantt_container",
        cols: [{
          width: 500,
          min_width: 280,
          rows: [{
            view: "grid",
            scrollX: "gridScroll",
            scrollable: true,
            scrollY: "scrollVer"
          },
            {
              view: "scrollbar",
              id: "gridScroll",
              group: "horizontal"
            }
          ]
        },
          {
            resizer: true,
            width: 1
          },
          {
            rows: [{
              view: "timeline",
              scrollX: "scrollHor",
              scrollY: "scrollVer"
            },
              {
                view: "scrollbar",
                id: "scrollHor",
                group: "horizontal"
              }
            ]
          },
          {
            view: "scrollbar",
            id: "scrollVer"
          }
        ]
      };

      //时间轴图表中，任务条形图的高度
      // gantt.config.task_height = 28
      //时间轴图表中，甘特图的高度
      // gantt.config.row_height = 36
      //时间轴图表中，如果不设置，只有行边框，区分上下的任务，设置之后带有列的边框，整个时间轴变成格子状。
      gantt.config.show_task_cells = true
      //当task的长度改变时，自动调整图表坐标轴区间用于适配task的长度
      gantt.config.fit_tasks = true
      gantt.config.min_column_width = 50;
      gantt.config.auto_types = true;
      gantt.config.xml_date = "%Y-%m-%d %H%i";//定义用于从数据集中解析数据并将数据发送到服务器的日期格式
      gantt.config.scale_unit = "year";
      gantt.config.step = 1;
      gantt.config.date_scale = "%Y";
      gantt.config.start_on_monday = true;
      gantt.config.scale_height = 90;
      gantt.config.autoscroll = true;
      gantt.config.calendar_property = "start_date";
      gantt.config.calendar_property = "end_date";
      gantt.config.readonly = true;
      //设置甘特图的时间格式
      gantt.config.date_format = "%Y-%m-%d %H:%i";
      //columns时间格式设置
      gantt.config.date_grid = "%Y-%m-%d %H:%i";
      gantt.config.duration_unit = "day";
      gantt.config.duration_step = 1;
      gantt.config.show_tasks_outside_timescale = true;
      gantt.i18n.setLocale('cn');
      gantt.config.work_time = true;
      // 初始化
      gantt.init(this.$refs.gantt)
      // 数据解析
      // console.log(this.tasks);
      gantt.clearAll()
      this.todayMaker()
      gantt.parse(this.tasks)
    },
    selectChange() {
      var monthScaleTemplate = function(date) {
        var dateToStr = gantt.date.date_to_str("第 %m 月");
        return dateToStr(date);
      };
      const unit = this.unit;
      if(unit === "month"){
        gantt.config.subscales = [
          {
            unit: "month",
            step: 1,
            template: monthScaleTemplate
          }
        ];
        gantt.config.scale_unit = "year";
        // 初始化
        gantt.init(this.$refs.gantt)
        gantt.clearAll()
        this.todayMaker()
        gantt.parse(this.tasks)
      }else if(unit === "year"){
        gantt.config.subscales = [
        ];
        gantt.config.scale_unit = "year";
        // 初始化
        gantt.init(this.$refs.gantt)
        gantt.clearAll()
        this.todayMaker()
        gantt.parse(this.tasks)
      }else{
        gantt.config.subscales = [
          {
            unit: "month",
            step: 1,
            template: monthScaleTemplate
          },
          {
            unit: "day",
            step: 1,
            format: "%d"
          },
        ];
        gantt.config.scale_unit = "year";
        // 初始化
        gantt.init(this.$refs.gantt)
        // 数据解析
        gantt.clearAll()
        this.todayMaker()
        gantt.parse(this.tasks)
      }
      console.log(unit);
    }
  }
};
</script>
<style scoped>
.firstLevelTask {
  border: none;

}
.firstLevelTask .gantt_task_content {
  font-size: 13px;
}
.secondLevelTask {
  border: none;
}
.thirdLevelTask {
  border: 2px solid #da645d;
  color: #da645d;
  background: #da645d;
  height: 20px !important;
}
</style>
