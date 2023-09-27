5<template>
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
        >删除
        </el-button>
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
        >刷新
        </el-button>
      </el-col>
      <el-col :span="1.5" style="height: 30px">
        <el-select size="mini" style="margin-left: 10px;width: 150px" v-model="queryParams.selectType" clearable placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
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
    <el-table border v-loading="loading" :data="upCjlsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="50"/>
      <el-table-column show-overflow-tooltip width="110" prop="cjbmks" label="创建部门科室" header-align="center" />
      <el-table-column show-overflow-tooltip width="110" prop="cjsj" label="创建时间" header-align="center" />
      <el-table-column show-overflow-tooltip width="120" prop="jjfa" label="解决方案" header-align="center" />
      <el-table-column show-overflow-tooltip width="120" prop="wtmc" label="问题名称" header-align="center" />
      <el-table-column show-overflow-tooltip width="120" prop="wtzt" label="问题状态" header-align="center" />
      <el-table-column show-overflow-tooltip width="120" prop="wtlb" label="问题类别" header-align="center" />
      <el-table-column show-overflow-tooltip width="120" prop="wtxl" label="问题细类" header-align="center" />
      <el-table-column show-overflow-tooltip width="120" prop="wtms" label="问题描述" header-align="center" />
      <el-table-column show-overflow-tooltip width="110" prop="scddh" label="生产订单号" header-align="center" />
      <el-table-column show-overflow-tooltip width="120" prop="cpxh" label="产品型号" header-align="center" />
      <el-table-column show-overflow-tooltip width="120" prop="jh" label="件号" header-align="center" />
      <el-table-column show-overflow-tooltip width="120" prop="bcrq" label="班产日期" header-align="center" />
      <el-table-column show-overflow-tooltip width="120" prop="pc" label="批次" header-align="center" />
      <el-table-column show-overflow-tooltip width="120" prop="gxh" label="工序号" header-align="center" />
      <el-table-column show-overflow-tooltip width="120" prop="gxmc" label="工序名称" header-align="center" />
      <el-table-column show-overflow-tooltip width="120" prop="sbxh" label="设备型号" header-align="center" />
      <el-table-column show-overflow-tooltip width="120" prop="tbr" label="提报人" header-align="center" />
      <el-table-column show-overflow-tooltip width="110" prop="bjzrks" label="被叫责任科室" header-align="center" />
      <el-table-column show-overflow-tooltip width="120" prop="bjzrr" label="被叫人" header-align="center" />
      <el-table-column show-overflow-tooltip width="120" prop="tbjssj" label="提报-接收时间" header-align="center" />
      <el-table-column show-overflow-tooltip width="120" prop="tbdqst" label="提报-当前时间" header-align="center" />
      <el-table-column show-overflow-tooltip width="120" prop="jsr" label="接收人" header-align="center" />
      <el-table-column show-overflow-tooltip width="120" prop="sfmy" label="是否满意" header-align="center" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改创建流水对话框 -->
    <el-dialog class="upCjlsAddDialog" title="修改" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="问题名称" prop="wtmc">
          <el-input style="width: 90%" v-model="form.wtmc" placeholder="请输入问题名称" />
        </el-form-item>
        <el-form-item label="问题状态" prop="wtzt">
          <el-input style="width: 90%" v-model="form.wtzt" placeholder="请输入问题状态" />
        </el-form-item>
        <el-form-item label="问题细类" prop="wtxl">
          <el-input style="width: 90%" v-model="form.wtxl" placeholder="请输入问题细类" />
        </el-form-item>
        <el-form-item label="班产日期" prop="bcrq">
          <el-input style="width: 90%" v-model="form.bcrq" placeholder="请输入班产日期" />
        </el-form-item>
        <el-form-item label="问题描述" prop="wtms">
          <el-input style="width: 90%" v-model="form.wtms" placeholder="请输入问题描述" />
        </el-form-item>
        <el-form-item label="解决方案" prop="jjfa">
          <el-input style="width: 90%" v-model="form.jjfa" placeholder="请输入解决方案" />
        </el-form-item>
        <el-form-item label="是否满意" prop="sfmy">
          <el-input style="width: 90%" v-model="form.sfmy" placeholder="请输入是否满意" />
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
import {listCjls, updateupCjlsList, delCjls, getCjls, updateCjls,} from "@/api/question/upQuestion";

export default {
  name: "UpCjls",
  data() {
    return {
      //查询选项
      options: [
        {
          value: 'WTMC',
          label: '问题名称'
        }, {
          value: 'JH',
          label: '产品型号'
        }, {
          value: 'WTLY',
          label: '问题来源'
        }, {
          value: 'SCDDH',
          label: '生产订单号'
        }, {
          value: 'WTMS',
          label: '问题描述'
        }, {
          value: 'SFMY',
          label: '是否满意'
        }, {
          value: 'JJFA',
          label: '解决方案'
        }],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非多个禁用
      multiple: true,
      // 非单个禁用
      single: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 创建流水表格数据
      upCjlsList: [],
      /*点到表行的下标*/
      entityIndex: -1,
      /*点到表列的名字*/
      entityName: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        selectType:'',
        selectData:'',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    tableRowClassName({row, column, rowIndex, columnIndex}) {
      //把每一行的索引放进row,和index
      row.index = rowIndex;
      column.index = columnIndex;
    },
    /** 查询创建流水列表 */
    getList() {
      this.loading = true;
      listCjls(this.queryParams).then(response => {
        this.upCjlsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.lsid)
      this.multiple = !selection.length
      this.single = selection.length!==1
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        WTMC: null,
        WTZT: null,
        WTXL: null,
        BCRQ: null,
        WTMS: null,
        JJFA: null,
        SFMY: null,
      };
    },
    /** 修改按钮操作 */
    handleUpdate() {
      this.reset();
      const LSID = this.ids[0]
      getCjls(LSID).then(response => {
        this.form = response.data;
        this.open = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateCjls(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const LSIDs = row.LSID || this.ids;
      this.$confirm('是否确认删除数据项?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delCjls(LSIDs).then(response=>{
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.getList()
        })
      }).catch(() => {
      });
    },
    /*双击事件*/
    celldblClick(row, column, cell, event) {
      this.entityIndex = row.index;
      this.entityName = column.property
    },
    /*输入框失去焦点事件*/
    /*out() {
      this.entityIndex = -1
    },*/
  }
};
</script>
<style>
.upCjlsAddDialog .el-dialog {
  border-radius: 10px;
}

.upCjlsAddDialog .el-dialog__close {
  font-size: 30px; /* 调整按钮大小 */
}

.upCjlsAddDialog .el-dialog__headerbtn .el-dialog__close {
  color: red;
}

.upCjlsAddDialog .el-dialog:not(.is-fullscreen) {
  margin-top: 10% !important;
}

.upCjlsAddDialog .el-dialog__body {
  padding-top: 20px;
  padding-bottom: 10px;
}

.upCjlsAddDialog .el-input {
  width: 85%;
}
</style>
