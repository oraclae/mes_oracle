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
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5" style="height: 30px">
        <el-select size="mini" style="margin-left: 10px;width: 150px" v-model="queryParams.selectType" clearable
                   placeholder="请选择">
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
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" type="index" width="50" align="center"/>
      <el-table-column show-overflow-tooltip width="120" label="责任部门" align="center" prop="zrbm"/>
      <el-table-column show-overflow-tooltip width="120" label="当前级别" align="center" prop="dqjb"/>
      <el-table-column show-overflow-tooltip width="120" label="当前责任人ID" align="center" prop="dqzerid"/>
      <el-table-column show-overflow-tooltip width="120" label="当前责任人" align="center" prop="dqzer"/>
      <el-table-column show-overflow-tooltip width="120" label="一级级别" align="center" prop="yjjb"/>
      <el-table-column show-overflow-tooltip width="120" label="一级责任人ID" align="center" prop="yjzrrid"/>
      <el-table-column show-overflow-tooltip width="120" label="一级责任人" align="center" prop="yjzrr"/>
      <el-table-column label="操作" width="150px" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
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

    <!-- 添加或修改sjzrzd对话框 -->
    <el-dialog class="sjzrzdAdd" :close-on-click-modal="false" :title="title" :visible.sync="open" width="500px"
               append-to-body>
      <div>
        <el-form ref="form" :model="form" :rules="rules" label-width="120px">
          <el-form-item label="责任部门" prop="zrbm">
            <el-input class="sjzrrDialogWidth" v-model="form.zrbm" placeholder="请输入责任部门"/>
          </el-form-item>
          <el-form-item label="当前责任人ID" prop="dqzerid">
            <el-input class="sjzrrDialogWidth" style="pointer-events: none" v-model="form.dqzerid"
                      placeholder="选择当前责任人时自动带入"/>
          </el-form-item>
          <el-form-item label="当前责任人" prop="dqzer">
            <div @click="openTreeVisual('当前责任人')">
              <el-input class="sjzrrDialogWidth" style="pointer-events: none" v-model="form.dqzer"
                        placeholder="请输入当前责任人"/>
            </div>
          </el-form-item>
          <el-form-item label="当前级别" prop="dqjb">
            <el-select class="sjzrrDialogWidth" v-model="form.dqjb" clearable placeholder="请选择当前级别">
              <el-option
                v-for="item in jbList"
                :key="item.value"
                :lable="item.lable"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="一级责任人ID" prop="yjzrrid">
            <el-input class="sjzrrDialogWidth" style="pointer-events: none" v-model="form.yjzrrid"
                      placeholder="选择一级责  时自动带入"/>
          </el-form-item>
          <el-form-item label="一级责任人" prop="yjzrr">
            <div @click="openTreeVisual('一级责任人')">
              <el-input class="sjzrrDialogWidth" style="pointer-events: none" v-model="form.yjzrr"
                        placeholder="请输入一级责任人"/>
            </div>
          </el-form-item>
          <el-form-item label="一级级别" prop="yjjb">
            <el-select class="sjzrrDialogWidth" v-model="form.yjjb" clearable placeholder="请选择一级级别">
              <el-option
                v-for="item in jbList"
                :key="item.value"
                :lable="item.lable"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog class="xzzrrDialog" :close-on-click-modal="false" :title="selectTitle" width="1200px"
               :visible.sync="treeVisual">
      <deptTreeSelect :selectZrrList="selectZrrList" :text="selectTitle" @selectDialogCancel="treeVisual=false"
                      @close="treeVisual=false"
                      v-if="treeVisual" @selectDialogSubmit="zrrSunmit"/>
    </el-dialog>
  </div>
</template>

<script>
import {listSjzrzd, getSjzrzd, delSjzrzd, addSjzrzd, updateSjzrzd} from "@/api/question/sjzrzd";
import deptTreeSelect from "@/views/question/sjzrzd/deptTreeSelect";
import {getwtxlMethod} from "@/api/question/question";

export default {
  name: "Sjzrzd",
  components: {
    deptTreeSelect
  },
  data() {
    return {
      selectTitle: null,
      treeVisual: false,
      selectZrrList: {},//选择责任人的对象
      selectYzrList: {},
      jbList: [],//级别的下拉数据
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
          value: 'yjzrr',
          label: '一级责任人'
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
        dqzerid: [
          {required: true, message: '请选择当前责任ID', trigger: 'blur'}
        ],
        dqzer: [
          {required: true, message: '请选择当前责任人', trigger: 'blur'}
        ],
        dqjb: [
          {required: true, message: '请选择当前类别', trigger: 'blur'}
        ],
        yjzrrid: [
          {required: true, message: '请选择一级责任人ID', trigger: 'blur'}
        ],
        yjzrr: [
          {required: true, message: '请选择一级责任人', trigger: 'blur'}
        ],
        yjjb: [
          {required: true, message: '请选择一级级别', trigger: 'blur'}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    //打开选择责任人
    openTreeVisual(title) {
      this.selectTitle = title
      this.treeVisual = true
    },
    //子组件确定按钮的执行方法
    zrrSunmit(row, title) {
      this.selectZrrList = row;
      if (title === '当前责任人') {
        this.form.zrbm = this.selectZrrList.dept.deptName;
        this.form.dqzerid = this.selectZrrList.userId.toString();
        this.form.dqzer = this.selectZrrList.nickName;
      } else {
        this.form.yjzrrid = this.selectZrrList.userId.toString();
        this.form.yjzrr = this.selectZrrList.nickName;
      }
      this.treeVisual = false
    },
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
        dqjb: null,
        yjjb: null,
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getWtsjjdZd()
      this.open = true;
      this.title = "添加升级责任字典";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getWtsjjdZd()
      const xh = row.xh || this.ids
      getSjzrzd(xh).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改升级责任字典";
      });
    },
    getWtsjjdZd() {
      this.jbList = []
      getwtxlMethod({wtlb: '问题升级阶段'}).then(res => {
        let data = res.rows
        for (let i = 0; i < data.length; i++) {
          this.jbList.push({lable: data[i], value: data[i]})
        }
      })
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
      this.$modal.confirm('是否确认删除升级责任字典编号为"' + xhs + '"的数据项？').then(function () {
        return delSjzrzd(xhs);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
  }
};
</script>
<style>
.sjzrzdAddDialog .el-dialog {
  border-radius: 10px;
}

.sjzrzdAddDialog .el-dialog__close {
  font-size: 30px; /* 调整按钮大小 */
}

.sjzrzdAddDialog .el-dialog__headerbtn .el-dialog__close {
  color: red;
}

.sjzrzdAddDialog .el-dialog:not(.is-fullscreen) {
  margin-top: 10% !important;
}

.sjzrzdAddDialog .el-dialog__body {
  padding-top: 20px;
  padding-bottom: 10px;
}

.sjzrzdAddDialog .el-input {
  width: 85%;
}

.xzzrrDialog .el-dialog__close {
  font-size: 30px; /* 调整按钮大小 */
}

.xzzrrDialog .el-dialog__headerbtn .el-dialog__close {
  color: red;
}

.xzzrrDialog .el-tooltip__popper {
  font-size: 18px; /* 调整字体大小为你需要的大小 */
}

.xzzrrDialog .el-dialog__body {
  padding-top: 0;
  padding-bottom: 20px;
}

.xzzrrDialog .el-dialog__headerbtn {
  z-index: 100;
}

.xzzrrDialog .el-dialog {
  border-radius: 10px;
}

.sjzrzdAdd .el-dialog__close {
  font-size: 30px; /* 调整按钮大小 */
}

.sjzrzdAdd .el-dialog__headerbtn .el-dialog__close {
  color: red;
}

.sjzrzdAdd .el-tooltip__popper {
  font-size: 18px; /* 调整字体大小为你需要的大小 */
}

.sjzrzdAdd .el-dialog__body {
  padding-top: 0;
  padding-bottom: 20px;
}

.sjzrzdAdd .el-dialog__headerbtn {
  z-index: 100;
}

.sjzrzdAdd .el-dialog {
  border-radius: 10px;
}

.sjzrzdAdd .el-dialog:not(.is-fullscreen) {
  margin-top: 10% !important;
}

.sjzrrDialogWidth {
  width: 80%;
}
</style>
