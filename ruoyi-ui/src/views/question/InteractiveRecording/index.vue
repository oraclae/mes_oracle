<template>
  <div class="app-container home">
    <el-row :gutter="24">
      <el-col :span="18" :xs="24">
        <!--        <el-button type="primary" size="small" @click="saveData">保存</el-button>-->
        <el-button type="primary" size="small" :disabled="single" @click="handleUpdate">修改</el-button>
        <el-button type="primary" size="small" @click="deleteData">删除</el-button>
        <el-button type="warning" size="small" @click="shuaxin">刷新</el-button>
        <el-select style="margin-left: 10px" v-model="queryParams.type" clearable placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入数据"
          clearable
          style="width: 240px;margin-left: 20px;margin-right: 10px"
          @keyup.enter.native="handleQuery"
        />
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
      </el-col>
    </el-row>
    <el-row :gutter="24">
      <el-col :span="24" :xs="24">
        <div class="InteractiveRecordingTable">
          <el-table
            height="calc(100vh - 220px)"
            :data="tableData"
            v-loading="loading"
            :show-overflow-tooltip="true"
            :cell-class-name=tableRowClassName
            size="medium"
            @selection-change="handleSelectionChange"
            highlight-current-row
            row-key="xh"
            ref="multipleTable"
            border
            style="width: 100%">
            <el-table-column type="selection" width="50" :reserve-selection="true"/>
            <el-table-column label="序号" type="index" width="50"/>
            <el-table-column show-overflow-tooltip width="150" prop="wtbt" label="问题名称"/>
            <el-table-column show-overflow-tooltip width="120" prop="hfr" label="回复人"/>
            <el-table-column show-overflow-tooltip width="120" prop="wtcjr" label="问题创建人"/>
            <el-table-column show-overflow-tooltip width="120" prop="wtcjbm" label="问题创建部门"/>
            <el-table-column show-overflow-tooltip width="155" prop="wtcjsj" label="问题创建时间"/>
            <el-table-column show-overflow-tooltip width="120" prop="wtzrr" label="问题责任人"/>
            <el-table-column show-overflow-tooltip width="120" prop="jhzt" label="交互记录状态"/>
            <el-table-column show-overflow-tooltip prop="hfxx" label="回复信息"/>
          </el-table>
        </div>
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="load"
        />
      </el-col>
    </el-row>

    <!-- 添加或修改【请填写功能名称】对话框 -->
    <el-dialog class="interactiveRecordingAddDialog" title="修改交互数据" :visible.sync="openUpdate" width="500px" append-to-body>
      <el-form ref="form" :model="formUpdate" label-width="120px">
        <el-form-item label="回复信息" prop="hfxx">
          <el-input v-model="formUpdate.hfxx" placeholder="请输入回复信息" />
        </el-form-item>
        <el-form-item label="问题标题" prop="wtbt">
          <el-input v-model="formUpdate.wtbt" placeholder="请输入问题标题" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import {
  getProblemInteractionList, updateProblemInteraction,
  deleteProblemInteraction, getJHJL,updateJHJL,
} from "@/api/question/question";
export default {
  data() {
    return {
      formUpdate: {},
      updateIds:[],//存储需要修改的id值
      openUpdate:false,//修改的弹出框
      single:true,//多选就不好使
      multiple:true,//多选也好使
      ids: [],
      /*点击时间下标*/
      editingIndex: '',
      /*点击事件字段*/
      editingProperty: '',
      /*是否高亮*/
      highLight:false,
      /*用于失去焦距*/
      editing:false,
      handleSelect:[],//选择的数据
      // 总条数
      total: 0,
      open: false,//是否打开回复预览页面
      //加载图片
      loading: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tName: null,
        tCode: null,
        tPid: null,
        tLevel: null,
        type: '',
      },
      tableData: [],
      options: [
        {
          value: 'hfxx',
          label: '回复信息'
        }, {
          value: 'wtbt',
          label: '问题标题'
        }],
    };
  },
  methods: {
    /** 修改按钮操作 */
    handleUpdate() {
      this.reset();
      const xh = this.updateIds[0];
      getJHJL({xh: xh}).then(res => {
        this.formUpdate = res.data;
        this.openUpdate = true;
      });
    },
    //重置
    reset() {
      this.formUpdate = {
        xh: null,
        hfxx: null,
        wtbt: null,
        wtcjid: null,
        wtcjr: null,
        wtcjbm: null,
        wtcjks: null,
        wtcjsj: null,
        wtzrrid: null,
        wtzrrbm: null,
        wtzrr: null,
        ejhfppyj: null,
        wtid: null,
        jhzt: null,
        hfr: null,
        js: null,
        yyd: null
      };
    },
    //修改表单的确定按钮
    submitForm() {
      updateJHJL(this.formUpdate).then(res => {
        if (res.code === 200) {
          this.$message.success("修改成功");
          this.openUpdate = false;
          this.load();
        }
      });
      this.reset();
    },
    //修改表单的取消按钮
    cancel() {
      this.reset();
      this.openUpdate = false;
    },
    //把每一行的索引放进row,和index
    tableRowClassName ({row, column, rowIndex, columnIndex}) {
      row.index = rowIndex;
      column.index=columnIndex;
    },
    // 控制input显示 row 当前行 column 当前列
    cellClick(row, column) {
      this.editing = true
      this.editingIndex = row.index
      this.editingProperty = column.property
      this.highLight = true
    },
    //按照条件查询数据
    handleQuery() {
      this.load();
    },
    // 失去焦点初始化
    inputBlur(object) {
      this.editing = false;
      object.row.isUpdate = "true";
    },
    //保存按钮
    saveData() {
      let UpdateList = [];
      this.tableData.forEach(item => {
        if (item.isUpdate === "true") {
          UpdateList.push(item);
        }
      });
      updateProblemInteraction(UpdateList).then(res=>{
        if (res.code === 200) {
          this.$message.success("修改成功");
          this.load();
        } else {
          this.$message.error("修改失败");
        }
      });
    },
    //删除问题的方法
    deleteData() {
      let num = this.handleSelect.length;
      if (num <= 0) {
        this.$message.error("请至少选择一条数据");
        return
      }
      let ids = [];
      this.handleSelect.forEach(item=>{
        ids.push(item.xh);
      });
      this.$confirm('是否确认删除数据项?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteProblemInteraction(ids).then(res=>{
          if (res.code === 200) {
            this.$message.success("批量删除成功");
            this.load();
          }
        })
      }).catch(() => {
      });
    },
    //刷新按钮
    shuaxin() {
      this.load();
    },
    load() {
      this.loading = true;
      getProblemInteractionList(this.queryParams).then(res => {
        if (res.code === 200) {
          this.tableData = res.rows;
          this.total = res.total;
          this.loading = false;
          this.$refs.multipleTable.clearSelection();
        }
      });
    },
    //多选框的方法
    handleSelectionChange(val) {
      this.handleSelect = val;
      this.updateIds = val.map(item => item.xh)
      this.single = val.length!==1
      this.multiple = !val.length
    },
  },
  mounted() {
    this.load();
  },
};
</script>
<style>
.fj .el-checkbox__label {
  font-size: 16px;
}

.cards .el-card__body {
  padding: 0;
}

.card .el-card__body {
  padding: 0;
}

.el-textarea.is-disabled .el-textarea__inner {
  color: black;
  font-size: 17px;
}
.InteractiveRecordingTable .el-table__cell{
  height: 40px;
}
.InteractiveRecordingTable .el-table--medium .el-table__cell{
  padding: 0;
}

.interactiveRecordingAddDialog .el-dialog {
  border-radius: 10px;
}

.interactiveRecordingAddDialog .el-dialog__close {
  font-size: 30px; /* 调整按钮大小 */
}

.interactiveRecordingAddDialog .el-dialog__headerbtn .el-dialog__close {
  color: red;
}

.interactiveRecordingAddDialog .el-dialog:not(.is-fullscreen) {
  margin-top: 10% !important;
}

.interactiveRecordingAddDialog .el-dialog__body {
  padding-top: 10px;
  padding-bottom: 10px;
}

.interactiveRecordingAddDialog .el-input {
  width: 85%;
}
</style>
<style scoped lang="scss">
.el-tooltip__popper {
  font-size: 18px; /* 调整字体大小为你需要的大小 */
}
.el-table{
  color: black;
}
.context-menu {
  position: fixed;
  background-color: white;
  padding: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.menu-item {
  padding: 5px;
  cursor: pointer;
}

.menu-item:hover {
  background-color: lightblue;
}
.green-color {
  background-color: green;
}

.yellow-color {
  background-color: yellow;
}

.red-color {
  background-color: red;
}

.clicked {
  background-color: #ffba00 !important;
}

.clicked::before {
  border-right-color: #ffba00 !important;
}

.app-container {
  padding-top: 10px;
}

.el-row {
  margin-bottom: 10px;

  &:last-child {
    margin-bottom: 0;
  }
}

.el-col {
  line-height: 20px;
}

.el_radios_position {
  margin-right: 10px;
}

.app-container {
  padding-top: 10px;
}

/*高度为100% */
.indexHeight {
  height: 100%;
}

.chatBox {
  font-size: 18px;
  display: inline-block;
  background-color: greenyellow;
  padding: 2px 5px;
  border-radius: 8px;
  margin-left: 10px;
  position: relative;
  word-break: break-all;
  border: 1px solid #989898;
  cursor: default;
  margin-bottom: 10px;
}

.chatBox-left::before {
  content: '';
  margin-top: 8px;
  margin-left: -13px;
  position: absolute;
  border: 5px solid;
  border-right-color: greenyellow;
  border-left-color: #97a8be;
  border-top-color: #97a8be;
  border-bottom-color: #97a8be;
}

.chatName {
  display: inline-block;
  background-color: #8492a6;
  border-radius: 30px;
  padding: 5px;
  font-size: 16px;
  margin-bottom: 10px;
}

.chatTime {
  display: inline-block;
}

.dialog {
  position: absolute;;
  z-index: 1;
}
</style>

