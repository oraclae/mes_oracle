<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100">
      <el-form-item label="零组件号" prop="lzjh">
        <el-input
          v-model="queryParams.lzjh"
          placeholder="请输入零组件号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="零组件名称" prop="lzjmc">
        <el-input
          v-model="queryParams.lzjmc"
          placeholder="请输入零组件名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生产订单号" prop="scddh">
        <el-input
          v-model="queryParams.scddh"
          placeholder="请输入生产订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['pc:lzjscdd:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['pc:lzjscdd:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['pc:lzjscdd:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pc:lzjscdd:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table border v-loading="loading" :data="lzjscddList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column width="50" label="序号" align="center" type="index" />
      <el-table-column width="120" label="零组件号" align="center" prop="lzjh" />
      <el-table-column width="120" label="零组件名称" align="center" prop="lzjmc" />
      <el-table-column width="120" label="生产订单号" align="center" prop="scddh" />
      <el-table-column width="120" label="是否外协" align="center" prop="sfwx">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.sfwx"/>
        </template>
      </el-table-column>
      <el-table-column width="80" label="批次" align="center" prop="pc" />
      <el-table-column width="180" label="要求交付日期" align="center" prop="yqjfrq">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.yqjfrq, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column width="120" label="订单数量" align="center" prop="ddsl" />
      <el-table-column width="120" label="在制数量" align="center" prop="zzsl" />
      <el-table-column width="120" label="在制品状态" align="center" prop="zzpzt" />
      <el-table-column width="180" label="预计完成日期" align="center" prop="yjwcsj">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.yjwcsj, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" label="完工数量" align="center" prop="wgsl" />
      <el-table-column width="180" label="实际完成时间" align="center" prop="sjwcsj">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sjwcsj, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column width="180" label="创建日期" align="center" prop="cjrq">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.cjrq, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column width="120" label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pc:lzjscdd:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pc:lzjscdd:remove']"
          >删除</el-button>
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

    <!-- 添加或修改零组件生产订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form :inline="true" ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="零组件号" prop="lzjh">
          <el-input v-model="form.lzjh" placeholder="请输入零组件号" />
        </el-form-item>
        <el-form-item label="零组件名称" prop="lzjmc">
          <el-input v-model="form.lzjmc" placeholder="请输入零组件名称" />
        </el-form-item>
        <el-form-item label="生产订单号" prop="scddh">
          <el-input v-model="form.scddh" placeholder="请输入生产订单号" />
        </el-form-item>
        <el-form-item label="是否外协" prop="sfwx">
          <el-select v-model="form.sfwx" placeholder="请选择是否外协">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="批次" prop="pc">
          <el-input v-model="form.pc" placeholder="请输入批次" />
        </el-form-item>
        <el-form-item label="要求交付日期" prop="yqjfrq">
          <el-date-picker clearable
            v-model="form.yqjfrq"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择要求交付日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="订单数量" prop="ddsl">
          <el-input v-model="form.ddsl" placeholder="请输入订单数量" />
        </el-form-item>
        <el-form-item label="在制数量" prop="zzsl">
          <el-input v-model="form.zzsl" placeholder="请输入在制数量" />
        </el-form-item>
        <el-form-item label="在制品状态" prop="zzpzt">
          <el-input v-model="form.zzpzt" placeholder="请输入在制品状态" />
        </el-form-item>
        <el-form-item label="预计完成日期" prop="yjwcsj">
          <el-date-picker clearable
            v-model="form.yjwcsj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择预计完成日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="完工数量" prop="wgsl">
          <el-input v-model="form.wgsl" placeholder="请输入完工数量" />
        </el-form-item>
        <el-form-item label="实际完成时间" prop="sjwcsj">
          <el-date-picker clearable
            v-model="form.sjwcsj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择实际完成时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="创建日期" prop="cjrq">
          <el-date-picker clearable
            v-model="form.cjrq"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择创建日期">
          </el-date-picker>
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
import { listLzjscdd, getLzjscdd, delLzjscdd, addLzjscdd, updateLzjscdd } from "@/api/pc/lzjscdd";

export default {
  name: "Lzjscdd",
  dicts: ['sys_yes_no'],
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
      showSearch: false,
      // 总条数
      total: 0,
      // 零组件生产订单表格数据
      lzjscddList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        lzjh: null,
        lzjmc: null,
        scddh: null,
        sfwx: null,
        pc: null,
        yqjfrq: null,
        ddsl: null,
        zzsl: null,
        zzpzt: null,
        yjwcsj: null,
        wgsl: null,
        sjwcsj: null,
        cjrq: null
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
    /** 查询零组件生产订单列表 */
    getList() {
      this.loading = true;
      listLzjscdd(this.queryParams).then(response => {
        this.lzjscddList = response.rows;
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
        lzjh: null,
        lzjmc: null,
        scddh: null,
        sfwx: null,
        pc: null,
        yqjfrq: null,
        ddsl: null,
        zzsl: null,
        zzpzt: null,
        yjwcsj: null,
        wgsl: null,
        sjwcsj: null,
        cjrq: null
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加零组件生产订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getLzjscdd(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改零组件生产订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateLzjscdd(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLzjscdd(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除零组件生产订单编号为"' + ids + '"的数据项？').then(function() {
        return delLzjscdd(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pc/lzjscdd/export', {
        ...this.queryParams
      }, `lzjscdd_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
