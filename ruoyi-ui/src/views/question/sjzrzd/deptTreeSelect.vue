<template>
  <div class="deptSelect">
    <el-row>
      <el-col :span="4">
        <div style="height: 750px; overflow-y: auto">
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
      </el-col>
      <el-col :span="20">
        <div class="one-row"></div>
        <div class="two-row">
          <el-table
            max-height="700px"
            :data="userList"
            v-loading="loading"
            @selection-change="handleSelectionChange"
            highlight-current-row
            row-key="id"
            @row-click="selectData"
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
          <pagination
            style="display: inline-block; float: right"
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            :page-size="50"
            layout="total, prev, pager, next, jumper"
            @pagination="getList"/>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {deptTreeSelect, listUser} from "@/api/system/user";

export default {
  name: "deptTreeSelect",
  props: ['selectZrrList', 'text'],
  data() {
    return {
      // 部门树选项
      deptOptions: undefined,
      defaultProps: {
        children: "children",
        label: "label"
      },
      userList: [],
      single: true,
      singleTwo: true,
      multiple: true,
      multipleTwo: true,
      userSelectTableData: [],//选中的用户
      userTableData: [],//部门下的用户数据
      handleSelect: [],//录入选中数据
      loading: false,//正在加载样式
      handleChangeData: [],//下面表格选中数据
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 50,
        account: null,
        name: null,
        deptId: undefined
      },
      // 日期范围
      dateRange: [],
      defaultExpandedKeys: [],
      deptTreeData: [],
    }
  },
  computed: {},
  mounted() {
    if (this.selectZrrList.length>0) {
      this.userSelectTableData = this.selectZrrList
      this.userSelectTableData.forEach(item => {
        if (item.sfzzrr === 'true') {
          this.$refs.refUserTable1.toggleRowSelection(item)
        }
      })
    }
    this.getList()
    this.getDeptTree()
  },
  methods: {
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.handleQuery();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 查询用户列表 */
    getList() {
      this.loading = true;
      listUser(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.userList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    //选中责任人确定按钮
    submitButten() {
      if (this.text.indexOf('责任人') !== -1) {
        if (this.userSelectTableData.length > 0) {
          /*this.userSelectTableData.forEach(item => {
            if (item.userId === this.handleChangeData[0].userId) {
              item.sfzzrr = 'true'
            } else {
              item.sfzzrr = ''
            }
          });*/
          this.$emit('selectDialogSubmit', this.userSelectTableData, '责任人')
        } else {
          this.$message.error("请选择责任人")
        }
      } else {
        if (this.userSelectTableData.length <= 0) {
          this.$message.error("请选择阅知人")
          return
        }
        this.$emit('selectDialogSubmit', this.userSelectTableData, '阅知人')
      }
    },
    //取消弹框的按钮
    cancelButten() {
      this.$emit('selectDialogCancel')
    },
    //双击添加
    selectData(row) {
      this.$emit('selectDialogSubmit', row, this.text)
    },
    //双击删除
    deleteData(row) {
      this.$refs.refUserTable1.setCurrentRow(null);
      this.userSelectTableData.forEach((v, i) => {
        if (row.userId === v.userId) {
          this.userSelectTableData.splice(i, 1)
        }
      })
    },
    //上面选中的数据放到下面的表格中
    addUserData() {
      const data = this.handleSelect;
      data.forEach(item => {
        this.userSelectTableData.forEach((user, i) => {
          if (item.userId === user.userId) {
            this.userSelectTableData.splice(i, 1);
          }
        });
        this.userSelectTableData.unshift(item);
      });
      this.$refs.refUserTable.clearSelection();
      this.$refs.refUserTable1.clearSelection();
    },
    //移除下面选中的数据
    deleteUserData() {
      this.handleChangeData.forEach(item => {
        this.userSelectTableData.forEach((user, index) => {
          if (item.userId === user.userId) {
            this.userSelectTableData.splice(index, 1);
          }
        });
      });
      this.$refs.refUserTable.clearSelection();
      this.$refs.refUserTable1.clearSelection();
    },
    //上面的表格选中的责任人数据
    handleSelectionChange(val) {
      this.handleSelect = val;
      this.single = val.length !== 1;
      this.multiple = !val.length;
    },
    //下面的表格选中的责任人数据
    handleChange(val) {
      this.handleChangeData = val;
      this.singleTwo = val.length !== 1;
      this.multipleTwo = !val.length;
    },
  }
}
</script>
<style>
.deptSelect .pagination-container {
  margin-top: 0;
}
</style>
