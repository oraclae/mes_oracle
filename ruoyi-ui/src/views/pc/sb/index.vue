<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备编号" prop="sbbh">
        <el-input
          v-model="queryParams.sbbh"
          placeholder="请输入设备编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备名称" prop="sbmc">
        <el-input
          v-model="queryParams.sbmc"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备类别" prop="sblb">
        <el-select v-model="queryParams.sblb" placeholder="请选择设备类别" clearable>
          <el-option
            v-for="dict in dict.type.sys_pc_sblx"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所属部门" prop="ssbm">
        <el-input
          v-model="queryParams.ssbm"
          placeholder="请输入所属部门"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备班次" prop="sbbc">
        <el-input
          v-model="queryParams.sbbc"
          placeholder="请输入设备班次"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备颜色" prop="sbys">
        <el-input
          v-model="queryParams.sbys"
          placeholder="请输入设备颜色"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="甘特图显示次序" prop="gttxscx">
        <el-input
          v-model="queryParams.gttxscx"
          placeholder="请输入甘特图显示次序"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="zt">
        <el-input
          v-model="queryParams.zt"
          placeholder="请输入状态"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备注" prop="bz">
        <el-input
          v-model="queryParams.bz"
          placeholder="请输入备注"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备别名" prop="sbbm">
        <el-input
          v-model="queryParams.sbbm"
          placeholder="请输入设备别名"
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
          v-hasPermi="['pc:sb:add']"
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
          v-hasPermi="['pc:sb:edit']"
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
          v-hasPermi="['pc:sb:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pc:sb:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table border v-loading="loading" :data="sbList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column label="设备编号" align="center" prop="sbbh" />
      <el-table-column label="设备名称" align="center" prop="sbmc" />
      <el-table-column label="设备类别" align="center" prop="sblb">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_pc_sblx" :value="scope.row.sblb"/>
        </template>
      </el-table-column>
      <el-table-column label="所属部门" align="center" prop="ssbm" />
      <el-table-column label="设备班次" align="center" prop="sbbc" />
      <el-table-column label="设备颜色" align="center" prop="sbys" />
      <el-table-column label="甘特图显示次序" align="center" prop="gttxscx" />
      <el-table-column label="状态" align="center" prop="zt" />
      <el-table-column label="备注" align="center" prop="bz" />
      <el-table-column label="设备别名" align="center" prop="sbbm" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pc:sb:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pc:sb:remove']"
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

    <!-- 添加或修改设备对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备编号" prop="sbbh">
          <el-input v-model="form.sbbh" placeholder="请输入设备编号" />
        </el-form-item>
        <el-form-item label="设备名称" prop="sbmc">
          <el-input v-model="form.sbmc" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="设备类别" prop="sblb">
          <el-select v-model="form.sblb" placeholder="请选择设备类别">
            <el-option
              v-for="dict in dict.type.sys_pc_sblx"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所属部门" prop="ssbm">
          <el-input v-model="form.ssbm" placeholder="请输入所属部门" />
        </el-form-item>
        <el-form-item label="设备班次" prop="sbbc">
          <el-input v-model="form.sbbc" placeholder="请输入设备班次" />
        </el-form-item>
        <el-form-item label="设备颜色" prop="sbys">
          <el-input v-model="form.sbys" placeholder="请输入设备颜色" />
        </el-form-item>
        <el-form-item label="甘特图显示次序" prop="gttxscx">
          <el-input v-model="form.gttxscx" placeholder="请输入甘特图显示次序" />
        </el-form-item>
        <el-form-item label="状态" prop="zt">
          <el-input v-model="form.zt" placeholder="请输入状态" />
        </el-form-item>
        <el-form-item label="备注" prop="bz">
          <el-input v-model="form.bz" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="设备别名" prop="sbbm">
          <el-input v-model="form.sbbm" placeholder="请输入设备别名" />
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
import { listSb, getSb, delSb, addSb, updateSb } from "@/api/pc/sb";

export default {
  name: "Sb",
  dicts: ['sys_pc_sblx'],
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
      // 设备表格数据
      sbList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sbbh: null,
        sbmc: null,
        sblb: null,
        ssbm: null,
        sbbc: null,
        sbys: null,
        gttxscx: null,
        zt: null,
        bz: null,
        sbbm: null
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
    /** 查询设备列表 */
    getList() {
      this.loading = true;
      listSb(this.queryParams).then(response => {
        this.sbList = response.rows;
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
        sbbh: null,
        sbmc: null,
        sblb: null,
        ssbm: null,
        sbbc: null,
        sbys: null,
        gttxscx: null,
        zt: null,
        bz: null,
        sbbm: null
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
      this.title = "添加设备";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSb(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改设备";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSb(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSb(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除设备编号为"' + ids + '"的数据项？').then(function() {
        return delSb(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pc/sb/export', {
        ...this.queryParams
      }, `sb_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
