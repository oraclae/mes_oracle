<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
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
          size="mini"
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

    <el-table height="calc(100vh - 220px)" border v-loading="loading" :data="ywzdList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="50"/>
      <el-table-column label="主节点(父)" align="center" prop="zjd" />
      <el-table-column label="次节点" align="center" prop="cjd" />
      <el-table-column label="备注" align="center" prop="bz" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改字典对话框 -->
    <el-dialog class="wtglZdAddDialog" :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="主节点(父)" prop="zjd">
          <el-input v-model="form.zjd" placeholder="请输入主节点(父)" />
        </el-form-item>
        <el-form-item label="次节点" prop="cjd">
          <el-input v-model="form.cjd" placeholder="请输入次节点" />
        </el-form-item>
        <el-form-item label="备注" prop="bz">
          <el-input v-model="form.bz" placeholder="请输入备注" />
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
import {listYwzd, getYwzd, delYwzd, addYwzd, updateYwzd} from "@/api/question/upQuestion";

export default {
  name: "WTGL_ZD",
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
      // 字典表格数据
      ywzdList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        selectData:''
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        zjd: [
          {required: true, message: '请填主节点', trigger: 'blur'}
        ],
        cjd: [
          {required: true, message: '请填次节点', trigger: 'blur'}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询字典列表 */
    getList() {
      this.loading = true;
      listYwzd(this.queryParams).then(response => {
        this.ywzdList = response.rows;
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
        zjd: null,
        cjd: null,
        bz: null,
        xh: null
      };
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.selectData = null
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.xh)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加字典";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const XH = row.xh || this.ids
      getYwzd(XH).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改字典";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.xh != null) {
            updateYwzd(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addYwzd(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const XHs = row.xh || this.ids;
      this.$confirm('是否确认删除数据项?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delYwzd(XHs).then(response=>{
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.getList()
        })
      }).catch(() => {
      });
    },
  }
};
</script>
<style>
.wtglZdAddDialog .el-dialog {
  border-radius: 10px;
}

.wtglZdAddDialog .el-dialog__close {
  font-size: 30px; /* 调整按钮大小 */
}

.wtglZdAddDialog .el-dialog__headerbtn .el-dialog__close {
  color: red;
}

.wtglZdAddDialog .el-dialog:not(.is-fullscreen) {
  margin-top: 15% !important;
}
.wtglZdAddDialog .el-input{
  width: 85%;
}
</style>
