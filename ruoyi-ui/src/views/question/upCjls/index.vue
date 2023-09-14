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
      <el-table-column width="110px" label="创建部门科室" align="center" prop="cjbmks" />
      <el-table-column label="创建时间" align="center" prop="cjsj" width="110">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.CJSJ, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip width="100px" label="问题名称" align="center" prop="wtmc" />
      <el-table-column show-overflow-tooltip width="100px" label="问题状态" align="center" prop="wtzt" />
      <el-table-column show-overflow-tooltip width="100px" label="问题类别" align="center" prop="wtlb" />
      <el-table-column show-overflow-tooltip width="100px" label="问题细类" align="center" prop="wtxl" />
      <el-table-column show-overflow-tooltip width="110px" label="生产订单号" align="center" prop="scddh" />
      <el-table-column show-overflow-tooltip width="100px" label="产品型号" align="center" prop="jh" />
      <el-table-column show-overflow-tooltip width="100px" label="班产日期" align="center" prop="bcrq" />
      <el-table-column show-overflow-tooltip width="100px" label="批次" align="center" prop="pc" />
      <el-table-column show-overflow-tooltip width="100px" label="工序号" align="center" prop="gxh" />
      <el-table-column show-overflow-tooltip width="100px" label="工序名称" align="center" prop="gxmc" />
      <el-table-column show-overflow-tooltip width="100px" label="问题描述" align="center" prop="wtms" />
      <el-table-column show-overflow-tooltip width="100px" label="设备型号" align="center" prop="sbxh" />
      <el-table-column show-overflow-tooltip width="100px" label="提报人" align="center" prop="tbr" />
      <el-table-column show-overflow-tooltip width="110px" label="被叫责任科室" align="center" prop="bjzrks" />
      <el-table-column show-overflow-tooltip width="100px" label="被叫人" align="center" prop="bjzrr" />
      <el-table-column show-overflow-tooltip width="120px" label="提报-接收时间" align="center" prop="tbjssj" />
      <el-table-column show-overflow-tooltip width="120px" label="提报-当前时间" align="center" prop="tbdqst" />
      <el-table-column show-overflow-tooltip width="100px" label="接收人" align="center" prop="jsr" />
      <el-table-column show-overflow-tooltip width="100px" label="解决方案" align="center" prop="jjfa" />
      <el-table-column show-overflow-tooltip width="100px" label="是否满意" align="center" prop="sfmy" />
      <el-table-column show-overflow-tooltip width="100px" label="提报人ID" align="center" prop="tbrid" />
      <el-table-column show-overflow-tooltip width="100px" label="被叫人ID" align="center" prop="bjrid" />
      <el-table-column show-overflow-tooltip width="100px" label="接收人ID" align="center" prop="jsrid" />
    </el-table>

    <!--      <el-table border v-loading="loading" :data="upCjlsList" @selection-change="handleSelectionChange"
                    :cell-class-name=tableRowClassName
                    @cell-dblclick="celldblClick">
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column
              label="序号"
              type="index"
              width="50">
            </el-table-column>
            <el-table-column show-overflow-tooltip width="100px" label="创建部门科室" align="center" prop="cjbmks">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'cjbmks'"
                          v-model="scope.row.cjbmks"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.cjbmks }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="创建时间" align="center" prop="cjsj" width="120px">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.cjsj, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="问题名称" align="center" prop="wtmc">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'wtmc'"
                          v-model="scope.row.wtmc"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.wtmc }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="问题状态" align="center" prop="wtzt">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'wtzt'"
                          v-model="scope.row.wtzt"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.wtzt }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="问题类别" align="center" prop="wtlb">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'wtlb'"
                          v-model="scope.row.wtlb"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.wtlb }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="问题细类" align="center" prop="wtxl">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'wtxl'"
                          v-model="scope.row.wtxl"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.wtxl }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip width="100px" label="生产订单号" align="center" prop="scddh">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'scddh'"
                          v-model="scope.row.scddh"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.scddh }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="产品型号" align="center" prop="jh">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'jh'"
                          v-model="scope.row.jh"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.jh }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="班产日期" align="center" prop="bcrq">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'bcrq'"
                          v-model="scope.row.bcrq"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.bcrq }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="批次" align="center" prop="pc">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'pc'"
                          v-model="scope.row.pc"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.pc }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="工序号" align="center" prop="gxh">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'gxh'"
                          v-model="scope.row.gxh"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.gxh }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="工序名称" align="center" prop="gxmc">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'gxmc'"
                          v-model="scope.row.gxmc"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.gxmc }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="问题描述" align="center" prop="wtms">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'wtms'"
                          v-model="scope.row.wtms"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.wtms }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="设备型号" align="center" prop="sbxh">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'sbxh'"
                          v-model="scope.row.sbxh"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.sbxh }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="提报人" align="center" prop="tbr">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'tbr'"
                          v-model="scope.row.tbr"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.tbr }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip width="100px" label="被叫责任科室" align="center" prop="bjzrks">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'bjzrks'"
                          v-model="scope.row.bjzrks"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.bjzrks }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="被叫人" align="center" prop="bjzrr">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'bjzrr'"
                          v-model="scope.row.bjzrr"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.bjzrr }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip width="120px" label="提报-接收时间" align="center" prop="tbjssj">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'tbjssj'"
                          v-model="scope.row.tbjssj"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.tbjssj }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip width="120px" label="提报-当前时间" align="center" prop="tbdqst">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'tbdqst'"
                          v-model="scope.row.tbdqst"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.tbdqst }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="接收人" align="center" prop="jsr">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'jsr'"
                          v-model="scope.row.jsr"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.jsr }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="解决方案" align="center" prop="jjfa">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'jjfa'"
                          v-model="scope.row.jjfa"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.jjfa }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="是否满意" align="center" prop="sfmy">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'sfmy'"
                          v-model="scope.row.sfmy"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.sfmy }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="提报人ID" align="center" prop="tbrid">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'tbrid'"
                          v-model="scope.row.tbrid"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.tbrid }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="被叫人ID" align="center" prop="bjrid">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'bjrid'"
                          v-model="scope.row.bjrid"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.bjrid }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="接收人ID" align="center" prop="jsrid">
              <template slot-scope="scope">
                <el-input v-if="entityIndex === scope.$index && entityName === 'jsrid'"
                          v-model="scope.row.jsrid"
                          @blur="out(scope)"/>
                <span v-else>{{ scope.row.jsrid }}</span>
              </template>
            </el-table-column>
          </el-table>-->

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
