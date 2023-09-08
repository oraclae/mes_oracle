<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="问题类别" prop="deptid">
        <el-select style="width: 200px;margin-left: 24px" v-model="queryParams.wtlb" clearable
                   placeholder="请选择问题类别">
          <el-option
            v-for="item in wtlbList"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="人员" prop="ryxm">
        <el-input
          v-model="queryParams.ryxm"
          placeholder="请输入人员"
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
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table border v-loading="loading" :data="wtlbryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column type="index" label="序号" align="center" width="50px"/>
      <el-table-column show-overflow-tooltip label="问题类别" align="center" width="150px" prop="wtlb"/>
      <el-table-column show-overflow-tooltip label="问题细类" align="center" width="150px" prop="wtxl"/>
      <el-table-column show-overflow-tooltip label="部门id" align="center" width="100px" prop="deptid"/>
      <el-table-column show-overflow-tooltip label="部门" align="center" width="100px" prop="bjrbm"/>
      <el-table-column show-overflow-tooltip label="被叫人id" align="center" width="100px" prop="bjrid"/>
      <el-table-column show-overflow-tooltip label="被叫人姓名" align="center" width="100px" prop="bjrxm"/>
      <el-table-column show-overflow-tooltip label="接收人id" align="center" prop="jsrid"/>
      <el-table-column show-overflow-tooltip label="接收人姓名" align="center" prop="jsrxm"/>
      <el-table-column label="操作" align="center" width="150px" class-name="small-padding fixed-width">
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

    <!-- 添加或修改问题类别人员对照对话框 -->
    <el-dialog class="wtlbRyAddDialog" :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="问题类别" prop="wtlb">
          <el-select v-model="form.wtlb" @change="getWtxlList" clearable
                     placeholder="请选择问题类别">
            <el-option
              v-for="item in wtlbList"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="问题细类" prop="wtxl">
          <el-select v-model="form.wtxl" clearable placeholder="请选择问题细类">
            <el-option
              v-for="item in wtxlList"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-hasPermi="['question:dept']" label="部门" prop="deptid">
          <div @click="selectBjrbmDialog = true">
            <el-tooltip effect="dark" :content="form.bjrbm" placement="top" :open-delay="500"
                        :disabled="form.bjrbm == null || form.bjrbm === ''">
              <div style="width: 217px;">
                <el-input placeholder="请选择部门" style="pointer-events: none; width: 217px"
                          :value="form.bjrbm"/>
              </div>
            </el-tooltip>
          </div>
        </el-form-item>
        <el-form-item label="被叫人" prop="bjrxm">
          <el-select v-model="form.bjrxm" @change="setbjrxm" clearable
                     placeholder="请选择被叫人">
            <el-option
              v-for="item in userList"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="接收人" prop="jsrid">
          <div @click="selectJsrDialog = true">
            <el-tooltip effect="dark" :content="form.jsrxm" placement="top" :open-delay="500"
                        :disabled="form.jsrxm == null || form.jsrxm === ''">
              <div style="width: 217px;">
                <el-input placeholder="请选择接收人" style="pointer-events: none; width: 217px"
                          :value="form.jsrxm"/>
              </div>
            </el-tooltip>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog class="jsrDialog" title="选择接收人" :visible.sync="selectJsrDialog" width="1000px" append-to-body>
      <div class="one-row"></div>
      <div class="two-row">
        <el-table
          height="300px"
          :data="userListOtherBy"
          @selection-change="jsrDiaSelectionChange"
          highlight-current-row
          row-key="id"
          @row-dblclick="jsrDiaSelectData"
          ref="refUserTable"
          border
        >
          <el-table-column type="selection" width="50px" :reserve-selection="true"/>
          <el-table-column align="center" label="序号" type="index" width="50px"/>
          <el-table-column prop="userName" label="账号" show-overflow-tooltip/>
          <el-table-column prop="nickName" label="姓名" show-overflow-tooltip/>
          <el-table-column prop="dept.deptName" label="所属部门" show-overflow-tooltip/>
        </el-table>
      </div>
      <div class="three-row" style="height: 48px;">
        <el-button :disabled="jsrDiaMultiple" type="success" size="small" style="margin-top: 10px"
                   @click="jsrDiaAddUserData">添 加
        </el-button>
        <el-button :disabled="jsrDiaMultipleTwo" type="danger" size="small" style="margin-top: 10px"
                   @click="jsrDiaDeleteUserData">移 除
        </el-button>
        <el-button type="primary" size="small" style="margin-top: 10px" @click="jsrDiaSubmitButten">确 定</el-button>
        <el-button type="warning" size="small" style="margin-top: 10px" @click="jsrDiaCancelButten">退 出</el-button>
        <pagination
          style="display: inline-block; float: right"
          v-show="userTotal>0"
          :total="userTotal"
          :page.sync="userQueryParams.current"
          :limit.sync="userQueryParams.size"
          @pagination="getUserListByDeptIdOtherBy"/>
      </div>
      <div class="four-row">
        <el-table
          ref="refUserTable1"
          height="400px"
          :data="jsrDiaUserSelectTableData"
          @selection-change="jsrDiaHandleChange"
          highlight-current-row
          row-key="id"
          border
        >
          <el-table-column type="selection" width="50px" :reserve-selection="true"/>
          <el-table-column align="center" label="序号" type="index" width="50px"/>
          <el-table-column prop="userName" label="账号" show-overflow-tooltip/>
          <el-table-column prop="nickName" label="姓名" show-overflow-tooltip/>
          <el-table-column prop="dept.deptName" label="所属部门" show-overflow-tooltip/>
        </el-table>
      </div>
    </el-dialog>
    <el-dialog class="jsrDialog" title="选择部门" :visible.sync="selectBjrbmDialog" width="1000px" append-to-body>
      <div>
        <el-tree
          :data="deptOptions"
          :props="defaultProps"
          :expand-on-click-node="false"
          :filter-node-method="filterNode"
          ref="tree"
          node-key="id"
          default-expand-all
          highlight-current
          @node-click="handleNodeClick"
        />
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listWtlbry, getWtlbry, delWtlbry, addWtlbry, updateWtlbry, getUserInfo} from "@/api/question/wtlbry";
import {getUpButtons} from "@/api/question/upQuestion";
import {getwtxlMethod} from "@/api/question/question";
import {deptTreeSelect, listUser} from "@/api/system/user";

export default {
  name: "Wtlbry",
  data() {
    return {
      // 部门树选项
      deptOptions: undefined,
      defaultProps: {
        children: "children",
        label: "label"
      },
      userInfo: {},//登录用户信息
      wtlbList: [],//问题类别列表
      wtxlList: [],//问题细类列表
      userList: [],//当前部门下人员列表
      userListOtherBy: [],//当前部门下人员列表
      selectJsrDialog: false,//选择接收人弹窗
      selectBjrbmDialog: false,//选择部门弹窗

      jsrDiaHandleSelect: [],//接收人选中数据
      jsrDiaSingle: true,
      jsrDiaSingleTwo: true,
      jsrDiaMultiple: true,
      jsrDiaMultipleTwo: true,
      jsrDiaUserSelectTableData: [],


      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      selectRows: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      userTotal: 0,
      // 问题类别人员对照表格数据
      wtlbryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        wtlb: null,
        wtxl: null,
        deptid: null,
        ryxm: null,
        ryid: null
      },
      userQueryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        wtlb: [
          {required: true, message: '请选择问题类别', trigger: 'blur'}
        ],
        wtxl: [
          {required: true, message: '请选择问题细类', trigger: 'blur'}
        ],
        ryid: [
          {required: true, message: '请选择人员', trigger: 'blur'}
        ],
      }
    };
  },
  created() {
    this.getUserInfo()
    this.getList();
    this.getWtlbList()
    this.getDeptTree()
  },
  methods: {
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.form = {
        wtlb: this.form.wtlb,
        wtxl: this.form.wtxl,
        deptid: data.id,
        bjrxm: null,
        bjrid: null,
        bjrzh: null,
        bjrbm: data.label,
        jsrxm: null,
        jsrid: null,
        jsrzh: null,
        jsrbm: null
      };
      this.selectBjrbmDialog = false
      this.getUserListByDeptId(data.id)
      this.getUserListByDeptIdOtherBy(data.id)
      this.jsrDiaUserSelectTableData = []
    },
    //获取用户信息
    getUserInfo() {
      getUserInfo().then(response => {
        this.userInfo = response.data
        this.getUserListByDeptId(this.userInfo.deptId)
      })
    },
    //获取用户信息
    getUserListByDeptId(deptId) {
      listUser({pageNum: 1,pageSize: 10,deptId: deptId}).then(response => {
          this.userList = response.rows;
        }
      );
    },
    //获取用户信息
    getUserListByDeptIdOtherBy(deptid) {
      if (deptid != null && deptid.page == null) {
        this.userQueryParams.deptId = deptid;
      }
      listUser(this.userQueryParams).then(response => {
          this.userListOtherBy = response.rows;
          this.userTotal = response.total;
        }
      );
    },
    //获取问题类别列表
    getWtlbList() {
      getUpButtons().then(response => {
        const list = response.rows
        for (let item of list) {
          this.wtlbList.push(item.butname)
        }
      })
    },
    /** 查询问题类别人员对照列表 */
    getList() {
      this.loading = true;
      listWtlbry(this.queryParams).then(response => {
        this.wtlbryList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //获取问题细类
    getWtxlList() {
      getwtxlMethod({wtlb: this.form.wtlb}).then(res => {
        if (res.code === 200) {
          this.wtxlList = res.rows;
        }
      })
    },
    //设置被叫人姓名
    setbjrxm() {
      for (let user of this.userList) {
        if (user.userId === this.form.bjrxm) {
          this.form.bjrid = this.form.bjrxm
          this.form.bjrxm = user.nickName
          this.form.bjrzh = user.userName
        }
      }
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.jsrDiaUserSelectTableData = []
      this.form = {
        xh: null,
        wtlb: null,
        wtxl: null,
        deptid: null,
        bjrxm: null,
        bjrid: null,
        bjrzh: null,
        bjrbm: null,
        jsrxm: null,
        jsrid: null,
        jsrzh: null,
        jsrbm: null
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
      this.selectRows = selection.map(item => item)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.getUserListByDeptIdOtherBy(this.userInfo.deptId)
      this.getUserListByDeptId(this.userInfo.deptId)
      this.reset();
      this.wtxlList = []
      this.open = true;
      this.handleNodeClick({id:this.userInfo.deptId, label: this.userInfo.user.dept.deptName})
      this.title = "添加问题类别人员对照";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.getUserListByDeptIdOtherBy(row.deptid)
      this.getUserListByDeptId(row.deptid)
      this.reset();
      const xh = row.xh || this.ids
      const data = row || this.selectRows
      this.open = true;
      getWtlbry(xh).then(response => {
        this.form = response.data;
        this.title = "修改问题类别人员对照";
        if (this.form.wtlb != null && this.form.wtlb !== '') {
          this.getWtxlList()
        }
        this.jsrDiaUserSelectTableData = []
        const jsrzhs = data.jsrzh.split(",")
        const jsrbms = data.jsrbm.split(",")
        const jsrids = data.jsrid.split(",")
        const jsrxms = data.jsrxm.split(",")
        for (let i = 0;i<jsrids.length; i++) {
          const data = {userId: jsrids[i],userName: jsrzhs[i],nickName: jsrxms[i],dept:{deptName:jsrbms[i]}}
          this.jsrDiaUserSelectTableData.push(data)
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.xh != null) {
            updateWtlbry(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWtlbry(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除问题类别人员对照编号为"' + xhs + '"的数据项？').then(function () {
        return delWtlbry(xhs);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('question/wtlbry/export', {
        ...this.queryParams
      }, `wtlbry_${new Date().getTime()}.xlsx`)
    },
    //上面的表格选中的责任人数据
    jsrDiaSelectionChange(val) {
      this.jsrDiaHandleSelect = val;
      this.jsrDiaSingle = val.length !== 1;
      this.jsrDiaMultiple = !val.length;
    },
    //下面的表格选中的责任人数据
    jsrDiaHandleChange(val) {
      this.jsrDiaHandleChangeData = val;
      this.jsrDiaSingleTwo = val.length !== 1;
      this.jsrDiaMultipleTwo = !val.length;
    },
    //查询数据
    jsrDiaSelectData(row) {
      var isSelect = false;
      if (this.jsrDiaUserSelectTableData.length > 0) {
        this.jsrDiaUserSelectTableData.forEach(item => {
          if (item.userId.toString() === row.userId.toString()) {
            isSelect = true;
          }
        });
      }
      if (!isSelect) {
        this.jsrDiaUserSelectTableData.unshift(row);
      }
    },
    //上面选中的数据放到下面的表格中
    jsrDiaAddUserData() {
      const data = this.jsrDiaHandleSelect;
      data.forEach(item => {
        this.jsrDiaUserSelectTableData.forEach((user, i) => {
          if (item.userId.toString() === user.userId.toString()) {
            this.jsrDiaUserSelectTableData.splice(i, 1);
          }
        });
        this.jsrDiaUserSelectTableData.unshift(item);
      });
      this.$refs.refUserTable.clearSelection();
      this.$refs.refUserTable1.clearSelection();
    },
    //移除下面选中的数据
    jsrDiaDeleteUserData() {
      this.jsrDiaHandleChangeData.forEach(item => {
        this.jsrDiaUserSelectTableData.forEach((user, index) => {
          if (item.userId.toString() === user.userId.toString()) {
            this.jsrDiaUserSelectTableData.splice(index, 1);
          }
        });
      });
      this.$refs.refUserTable.clearSelection();
      this.$refs.refUserTable1.clearSelection();
    },
    //选中责任人确定按钮
    jsrDiaSubmitButten() {
      if (this.jsrDiaUserSelectTableData.length <= 0) {
        this.$message.error("请选择接收人")
        return
      }
      let jsrNameList = []
      let jsrIdList = []
      let jsrzhList = []
      let jsrbmList = []
      this.jsrDiaUserSelectTableData.forEach(item => {
        jsrNameList.push(item.nickName)
        jsrIdList.push(item.userId)
        jsrzhList.push(item.userName)
        jsrbmList.push(item.dept.deptName)
      })
      this.form.jsrxm = jsrNameList.join(",")
      this.form.jsrid = jsrIdList.join(",")
      this.form.jsrzh = jsrzhList.join(",")
      this.form.jsrbm = jsrbmList.join(",")
      this.selectJsrDialog = false
    },
    //取消弹框的按钮
    jsrDiaCancelButten() {
      this.selectJsrDialog = false
    },


  }
};
</script>
<style>
.jsrDialog .pagination-container {
  margin-top: 0;
}
.wtlbRyAddDialog .el-dialog {
  border-radius: 30px;
}

.wtlbRyAddDialog .el-dialog__close {

  font-size: 30px; /* 调整按钮大小 */
}

.wtlbRyAddDialog .el-dialog__headerbtn .el-dialog__close {
  color: red;
}

.wtlbRyAddDialog .el-dialog:not(.is-fullscreen) {
  margin-top: 10% !important;
}

.jsrDialog .el-dialog {
  border-radius: 30px;
}

.jsrDialog .el-dialog__close {

  font-size: 30px; /* 调整按钮大小 */
}

.jsrDialog .el-dialog__headerbtn .el-dialog__close {
  color: red;
}

.jsrDialog .el-dialog:not(.is-fullscreen) {
}
</style>
