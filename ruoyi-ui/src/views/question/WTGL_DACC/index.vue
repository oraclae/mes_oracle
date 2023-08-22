<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-refresh-right"
          size="mini"
          @click="getList"
        >刷新</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-input
          v-model="queryParams.selectData"
          placeholder="请输入"
          clearable
          style="width: 200px;margin-left: 20px;margin-right: 10px"
          @keyup.enter.native="handleQuery"
        />
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-col>
    </el-row>

    <el-table
      :header-cell-style="styleFunc" border height="calc(100vh - 220px)" v-loading="loading" :data="daccList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="50"/>
      <el-table-column show-overflow-tooltip label="问题名称" align="center" prop="wtmc" />
      <el-table-column show-overflow-tooltip label="责任人" align="center" prop="zrr" />
      <el-table-column show-overflow-tooltip label="关闭时间" align="center" prop="gbsj" />
      <el-table-column show-overflow-tooltip label="答案信息" align="center" prop="daxx" />
      <el-table-column show-overflow-tooltip label="问题创建人" align="center" prop="cjr" />
      <el-table-column show-overflow-tooltip label="问题类别" align="center" prop="wtlb" />
      <el-table-column show-overflow-tooltip label="问题细类" align="center" prop="wtxl" />
      <el-table-column show-overflow-tooltip label="热度" align="center" prop="rd" />
      <el-table-column show-overflow-tooltip label="问题描述" align="center" prop="wtms" />
      <el-table-column show-overflow-tooltip label="件号" align="center" prop="jh" />
      <el-table-column show-overflow-tooltip label="产品型号" align="center" prop="cpxh" />
      <el-table-column show-overflow-tooltip label="工序号" align="center" prop="gxh" />
      <el-table-column show-overflow-tooltip label="关键字" align="center" prop="keywords" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改答案信息对话框 -->
    <el-dialog class="addDialog" :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="问题名称" prop="wtmc">
          <el-input v-model="form.wtmc" placeholder="请输入问题名称" />
        </el-form-item>
        <el-form-item label="责任人" prop="zrr">
          <el-input v-model="form.zrr" placeholder="请输入责任人" />
        </el-form-item>
        <el-form-item label="关闭时间" prop="gbsj">
          <el-input v-model="form.gbsj" placeholder="请输入关闭时间" />
        </el-form-item>
        <el-form-item label="答案信息" prop="daxx">
          <el-input v-model="form.daxx" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="问题创建人" prop="cjr">
          <el-input v-model="form.cjr" placeholder="请输入问题创建人" />
        </el-form-item>
        <el-form-item label="问题类别" prop="wtlb">
          <el-input v-model="form.wtlb" placeholder="请输入问题类别" />
        </el-form-item>
        <el-form-item label="问题细类" prop="wtxl">
          <el-input v-model="form.wtxl" placeholder="请输入问题细类" />
        </el-form-item>
        <el-form-item label="热度" prop="rd">
          <el-input v-model="form.rd" placeholder="请输入热度" />
        </el-form-item>
        <el-form-item label="问题描述" prop="wtms">
          <el-input v-model="form.wtms" placeholder="请输入问题描述" />
        </el-form-item>
        <el-form-item label="关键字" prop="wtms">
          <el-input v-model="form.keywords" placeholder="请输入关键字" />
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
import {listDacc, getDacc, delDacc, updateDacc} from "@/api/question/upQuestion";

export default {
  name: "WTGL_DACC",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 答案信息表格数据
      daccList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        selectData: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    //给表格的表头设置颜色
    styleFunc({row,column,rowIndex, columnIndex}) {
      if (column.property === 'wtms' || column.property === 'wtmc') {
        return "background:orange";
      }
      if (column.property === 'daxx') {
        return "background:#22f64a";
      }
    },
    /** 查询答案信息列表 */
    getList() {
      this.loading = true;
      listDacc(this.queryParams).then(response => {
        this.daccList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        daxxid: null,
        wtmc: null,
        zrr: null,
        gbsj: null,
        daxx: null,
        cjr: null,
        wtlb: null,
        wtxl: null,
        rd: null,
        wtms: null,
        keywords: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.selectData = null
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.daxxid)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加答案信息";
    },
    /** 修改按钮操作 */
    handleUpdate() {
      this.reset();
      const daccid = this.ids
      getDacc(daccid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改答案";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.daxxid != null) {
            updateDacc(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } /*else {
            addDacc(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }*/
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete() {
      const daccids = this.ids;
      this.$confirm('是否确认删除数据项?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delDacc(daccids).then(response=>{
          this.getList()
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        })
      }).catch(() => {
      });
    },
  }
};
</script>
<style>
.addDialog .el-dialog {
  border-radius: 30px;
}

.addDialog .el-dialog__close {
  font-size: 30px; /* 调整按钮大小 */
}

.addDialog .el-dialog__headerbtn .el-dialog__close {
  color: red;
}

.addDialog .el-dialog:not(.is-fullscreen) {
  margin-top: 3% !important;
}
</style>
