<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="是否上班" prop="sfsb">
        <el-input
          v-model="queryParams.sfsb"
          placeholder="请输入是否上班"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="12" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['pc:rl:add']"
        >新增
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
          v-hasPermi="['pc:rl:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['pc:rl:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pc:rl:export']"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-delete"
          size="mini"
          @click="handleGenerationDate"
        >生成日期
        </el-button>
      </el-col>

<!--      v-hasPermi="['pc:rl:generationDate']"-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table border v-loading="loading" :data="rlList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" type="index"/>
      <el-table-column label="日期" align="center" prop="rq"/>
      <el-table-column label="星期" align="center" prop="xq"/>
      <el-table-column label="是否上班" align="center" prop="sfsb"/>
      <el-table-column label="备注" align="center" prop="bz"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pc:rl:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pc:rl:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改日期对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="日期" prop="rq">
          <el-input v-model="form.rq" placeholder="请输入日期"/>
        </el-form-item>
        <el-form-item label="星期" prop="xq">
          <el-input v-model="form.xq" placeholder="请输入星期"/>
        </el-form-item>
        <el-form-item label="是否上班" prop="sfsb">
          <el-input v-model="form.sfsb" placeholder="请输入是否上班"/>
        </el-form-item>
        <el-form-item label="备注" prop="bz">
          <el-input v-model="form.bz" placeholder="请输入备注"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="title" :visible.sync="openTime" width="500px" append-to-body>
      <div class="block">
        <span class="demonstration">时间：</span>
        <el-date-picker
          v-model="value1"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitGenerationDate">确 定</el-button>
        <el-button @click="cancelGenerationDate">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listRl, getRl, delRl, addRl, updateRl, addGenerationDate} from "@/api/pc/rl";
import log from "@/views/monitor/job/log";

export default {
  name: "Rl",
  data() {
    return {
      openTime: false,
      value1: '',
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      // 日期表格数据
      rlList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        rq: null,
        xq: null,
        sfsb: null,
        bz: null
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
    //取消方法
    cancelGenerationDate() {
      this.openTime = false;
    },
    submitGenerationDate() {
      const year = this.value1[0].getFullYear();
      const month = String(this.value1[0].getMonth() + 1).padStart(2, '0'); // 月份从 0 开始，需要加 1
      const day = String(this.value1[0].getDate()).padStart(2, '0');
      const year1 = this.value1[1].getFullYear();
      const month1 = String(this.value1[1].getMonth() + 1).padStart(2, '0'); // 月份从 0 开始，需要加 1
      const day1 = String(this.value1[1].getDate()).padStart(2, '0');
      let time = `${year}-${month}-${day}`;
      let time1 = `${year1}-${month1}-${day1}`;
      addGenerationDate({ksrq: time, jsrq: time1}).then(res=>{
        this.$message.success("生成成功");
        this.openTime = false;
        this.getList();
      });
    },
    //生产订单
    handleGenerationDate() {
      this.title='时间选择'
      this.openTime = true;

    },
    /** 查询日期列表 */
    getList() {
      this.loading = true;
      listRl(this.queryParams).then(response => {
        this.rlList = response.rows;
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
        id: null,
        rq: null,
        xq: null,
        sfsb: null,
        bz: null
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加日期";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRl(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改日期";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRl(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRl(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除日期编号为"' + ids + '"的数据项？').then(function () {
        return delRl(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pc/rl/export', {
        ...this.queryParams
      }, `rl_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
