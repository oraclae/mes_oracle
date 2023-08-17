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
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
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

    <el-table border v-loading="loading" :data="sjzrzdList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="50"/>
      <el-table-column label="责任部门ID" align="center" prop="zrbmid" />
      <el-table-column label="责任部门" align="center" prop="zrbm" />
      <el-table-column label="责任科室ID" align="center" prop="zrksid" />
      <el-table-column label="责任科室" align="center" prop="zrks" />
      <el-table-column label="当前责任人ID" align="center" prop="dqzerid" />
      <el-table-column label="当前责任人" align="center" prop="dqzer" />
      <el-table-column label="一级责任人ID" align="center" prop="yjzrrid" />
      <el-table-column label="一级责任人" align="center" prop="yjzrr" />
      <el-table-column label="二级责任人ID" align="center" prop="ejzrrid" />
      <el-table-column label="二级责任人" align="center" prop="ejzrr" />
      <el-table-column label="三级责任人ID" align="center" prop="sjzrrid" />
      <el-table-column label="三级责任人" align="center" prop="sjzrr" />
      <el-table-column label="四级责任人ID" align="center" prop="sijzrrid" />
      <el-table-column label="四级责任人" align="center" prop="sijzrr" />
      <el-table-column label="操作" width="150px" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
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

    <!-- 添加或修改sjzrzd对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="500px" append-to-body>
      <div style="height: 600px;overflow-y: auto">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="责任部门ID" prop="zrbmid">
          <el-input v-model="form.zrbmid" placeholder="请输入责任部门ID" />
        </el-form-item>
        <el-form-item label="责任部门" prop="zrbm">
          <el-input v-model="form.zrbm" placeholder="请输入责任部门" />
        </el-form-item>
        <el-form-item label="责任科室ID" prop="zrksid">
          <el-input v-model="form.zrksid" placeholder="请输入责任科室ID" />
        </el-form-item>
        <el-form-item label="责任科室" prop="zrks">
          <el-input v-model="form.zrks" placeholder="请输入责任科室" />
        </el-form-item>
        <el-form-item label="当前责任人ID" prop="dqzerid">
          <el-input v-model="form.dqzerid" placeholder="请输入当前责任人ID" />
        </el-form-item>
        <el-form-item label="当前责任人" prop="dqzer">
          <el-input v-model="form.dqzer" placeholder="请输入当前责任人" />
        </el-form-item>
        <el-form-item label="一级责任人ID" prop="yjzrrid">
          <el-input v-model="form.yjzrrid" placeholder="请输入一级责任人ID" />
        </el-form-item>
        <el-form-item label="一级责任人" prop="yjzrr">
          <el-input v-model="form.yjzrr" placeholder="请输入一级责任人" />
        </el-form-item>
        <el-form-item label="二级责任人ID" prop="ejzrrid">
          <el-input v-model="form.ejzrrid" placeholder="请输入二级责任人ID" />
        </el-form-item>
        <el-form-item label="二级责任人" prop="ejzrr">
          <el-input v-model="form.ejzrr" placeholder="请输入二级责任人" />
        </el-form-item>
        <el-form-item label="三级责任人ID" prop="sjzrrid">
          <el-input v-model="form.sjzrrid" placeholder="请输入三级责任人ID" />
        </el-form-item>
        <el-form-item label="三级责任人" prop="sjzrr">
          <el-input v-model="form.sjzrr" placeholder="请输入三级责任人" />
        </el-form-item>
        <el-form-item label="四级责任人ID" prop="sijzrrid">
          <el-input v-model="form.sijzrrid" placeholder="请输入四级责任人ID" />
        </el-form-item>
        <el-form-item label="四级责任人" prop="sijzrr">
          <el-input v-model="form.sijzrr" placeholder="请输入四级责任人" />
        </el-form-item>
      </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSjzrzd, getSjzrzd, delSjzrzd, addSjzrzd, updateSjzrzd } from "@/api/question/sjzrzd";

export default {
  name: "Sjzrzd",
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
      // 升级责任字典表格数据
      sjzrzdList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //查询选项
      options: [
        {
          value: 'zrbm',
          label: '责任部门'
        }, {
          value: 'zrks',
          label: '责任科室'
        }, {
          value: 'yjzrr',
          label: '一级责任人'
        }, {
          value: 'ejzrr',
          label: '二级责任人'
        }, {
          value: 'sjzrr',
          label: '三级责任人'
        }, {
          value: 'sijzrr',
          label: '四级责任人'
        }, {
          value: 'dqzer',
          label: '当前责任人'
        }],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        selectType: null,
        selectDate: null
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
    /** 查询升级责任字典列表 */
    getList() {
      this.loading = true;
      listSjzrzd(this.queryParams).then(response => {
        this.sjzrzdList = response.rows;
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
        xh: null,
        zrbm: null,
        zrbmid: null,
        zrks: null,
        zrksid: null,
        yjzrr: null,
        ejzrr: null,
        sjzrr: null,
        yjzrrid: null,
        ejzrrid: null,
        sjzrrid: null,
        dqzer: null,
        dqzerid: null,
        sijzrr: null,
        sijzrrid: null
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
      this.ids = selection.map(item => item.xh)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加升级责任字典";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const xh = row.xh || this.ids
      getSjzrzd(xh).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改升级责任字典";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.xh != null) {
            updateSjzrzd(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSjzrzd(this.form).then(response => {
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
      const xhs = row.xh || this.ids;
      this.$modal.confirm('是否确认删除升级责任字典编号为"' + xhs + '"的数据项？').then(function() {
        return delSjzrzd(xhs);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
  }
};
</script>
