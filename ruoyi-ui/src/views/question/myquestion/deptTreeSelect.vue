<template>
  <div class="deptSelect">
    <el-row>
      <el-col :span="4">
        <div style="height: 750px; overflow-y: auto">
          <el-tree class="avue-tree" ref="deptTree" :data="deptTreeData" :props="deptTreeOption.props"
                   :highlight-current="deptTreeOption.highlightCurrent"
                   :default-expanded-keys="deptTreeOption.defaultExpandedKeys"
                   :accordion="deptTreeOption.defaultExpandAdd" :size="deptTreeOption.size"
                   @node-click="nodeClick">
          </el-tree>
        </div>
      </el-col>
      <el-col :span="20">
        <div class="one-row"></div>
        <div class="two-row">
          <el-table
            height="300px"
            :data="userTableData"
            v-loading="loading"
            @selection-change="handleSelectionChange"
            highlight-current-row
            row-key="id"
            @row-dblclick="selectData"
            ref="refUserTable"
            border
          >
            <el-table-column type="selection" width="50px" :reserve-selection="true"/>
            <el-table-column align="center" label="序号" type="index" width="50px"/>
            <el-table-column prop="account" label="账号" show-overflow-tooltip/>
            <el-table-column prop="name" label="姓名" show-overflow-tooltip/>
            <el-table-column prop="deptName" label="所属部门" show-overflow-tooltip/>
          </el-table>
        </div>
        <div class="three-row" style="height: 48px;">
          <el-button :disabled="multiple" type="success" size="small" style="margin-top: 10px" @click="addUserData">添
            加
          </el-button>
          <el-button :disabled="multipleTwo" type="danger" size="small" style="margin-top: 10px"
                     @click="deleteUserData">移 除
          </el-button>
          <el-button type="primary" size="small" style="margin-top: 10px" @click="submitButten">确 定</el-button>
          <el-button type="warning" size="small" style="margin-top: 10px" @click="cancelButten">退 出</el-button>
          <pagination
            style="display: inline-block; float: right"
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.current"
            :limit.sync="queryParams.size"
            @pagination="load"/>
        </div>
        <div class="four-row">
          <el-table
            ref="refUserTable1"
            max-height="400px"
            :data="userSelectTableData"
            @selection-change="handleChange"
            highlight-current-row
            row-key="id"
            border
          >
            <el-table-column type="selection" width="50px" :reserve-selection="true"/>
            <el-table-column align="center" label="序号" type="index" width="50px"/>
            <el-table-column prop="account" label="账号" show-overflow-tooltip/>
            <el-table-column prop="name" label="姓名" show-overflow-tooltip/>
            <el-table-column prop="deptName" label="所属部门" show-overflow-tooltip/>
          </el-table>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "deptTreeSelect",
  props: ['selectZrrList', 'text'],
  data() {
    return {
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
      queryParams: {
        current: 1,
        size: 10,
        account: null,
        name: null,
        deptId: null
      },
      defaultExpandedKeys: [],
      deptTreeData: [],
      deptTreeOption: {
        menu: false,
        addBtn: false,
        delBtn: false,
        editBtn: false,
        size: 'small',
        highlightCurrent: true,
        defaultExpandedKeys: [],
        defaultExpandAdd: false,//手风琴模式
        props: {
          lable: 'title',
          children: 'children'
        }
      }
    }
  },
  computed: {},
  mounted() {
    if (this.selectZrrList.lenght > 0) {
      this.userSelectTableData = this.selectZrrList
      this.userSelectTableData.forEach(item => {
        if (item.sfzzrr === 'true') {
          this.$refs.refUserTable1.toggleRowSelection(item)
        }
      })
    }
    this.initData();
    this.load()
  },
  methods: {
    //选中责任人确定按钮
    submitButten() {
      if (this.text.indexOf('责任人') !== -1) {
        if (this.handleChangeData.length === 1) {
          this.userSelectTableData.forEach(item => {
            if (item.id === this.handleChangeData[0].id) {
              item.sfzzrr = 'true'
            } else {
              item.sfzzrr = ''
            }
          });
          this.$emit('selectDialogSubmit', this.userSelectTableData, '责任人')
        } else {
          this.$message.error("请选择一条为主责任人")
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
    //查询数据
    selectData(row) {
      var isSelect = false;
      if (this.userSelectTableData.length > 0) {
        this.userSelectTableData.forEach(item => {
          if (item.id === row.id) {
            isSelect = true;
          }
        });
      }
      if (!isSelect) {
        this.userSelectTableData.unshift(row);
      }
    },
    //上面选中的数据放到下面的表格中
    addUserData() {
      const data = this.handleSelect;
      data.forEach(item => {
        this.userSelectTableData.forEach((user, i) => {
          if (item.id === user.id) {
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
          if (item.id === user.id) {
            this.userSelectTableData.splice(index, 1);
          }
        });
      });
      this.$refs.refUserTable.clearSelection();
      this.$refs.refUserTable1.clearSelection();
    },
    //初始化请求用户数据
    load() {
      /*this.queryParams.deptId = this.userInfo.deptId;
      getUserPage(this.queryParams).then(res => {
        if (res.data.code === 200) {
          this.total = res.data.data.total;
          this.userTableData = res.data.data.records;
        }
      });*/
      this.total = 4
      this.userTableData = [{id: '1', account: '1', name: '2', deptName: '3'}, {
        id: '4',
        account: '1',
        name: '2',
        deptName: '3'
      }, {id: '3', account: '1', name: '2', deptName: '3'}, {id: '2', account: '1', name: '2', deptName: '3'}]


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
    initData() {
      /*this.getOrgTreeAll().then(res => {
        this.initExpandFirstLevel(res.data.data);
        this.deptTreeData = res.data.data;
      });*/
      const data = [{
        id: "100",
        key: "100",
        parentId: "0",
        title: "中国航发东安",
        hasChildren: true,
        value: "100",
        $treeNodeId: 1,
        children: [{
          children: [],
          hasChildren: true,
          id: "201",
          key: "201",
          parentId: "100",
          title: "信息档案中心",
          value: "201",
          $treeNodeId: 2
        }
        ]
      }]
      this.initExpandFirstLevel(data);
      this.deptTreeData = data;


    },
    //初始化默认展开第一级
    initExpandFirstLevel(data) {
      for (let dept of data) {
        this.defaultExpandedKeys.push(dept.id);
      }
      this.$set(this.deptTreeOption, 'defaultExpandedKeys', this.defaultExpandedKeys);
    },
    //获得组织树全部数据
    getOrgTreeAll() {
      return Request({
        uri: baseUrl + '/digital-system/dept/tree',
        method: 'get',
      })
    },
    //点击事件
    nodeClick(node) {
      this.queryParams.deptId = node.id;
      /*getUserPage(this.queryParams).then(res => {
        if (res.data.code === 200) {
          this.total = res.data.data.total;
          this.userTableData = res.data.data.records;
        }
      });*/
      this.$message.success("点击了")
      this.total = 4
      this.userTableData = [{id: '1', account: '1', name: '2', deptName: '3'}, {
        id: '4',
        account: '1',
        name: '2',
        deptName: '3'
      }, {id: '3', account: '1', name: '2', deptName: '3'}, {id: '2', account: '1', name: '2', deptName: '3'}]


    }
  }
}
</script>
<style>
.deptSelect .pagination-container {
  margin-top: 0;
}
</style>

<!--<style scoped lang="less">
.avue-tree-class {
  .el-table-node__content {
    display: inline !important;
  }

  .el-tree-node {
    position: relative;
  }

  .el-tree-node__children {
    overflow: visible !important;
  }
}

.avue-tree {
  height: calc(100% - 20px);
}
</style>-->
