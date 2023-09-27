<template>
  <div class="app-container home">
    <el-row :gutter="24">
      <!--用户数据-->
      <el-col :span="20" :xs="24">
        <el-radio-group v-model="queryParams.radios" @change="changeRadio">
          <el-radio label="待办" class="el_radios_position">待办</el-radio>
          <el-radio label="已关闭" class="el_radios_position">已关闭</el-radio>
        </el-radio-group>
        <el-button
          :style="{background: 'linear-gradient(to bottom,'+(single?'#f902e680, #08ff0480':'#f902e6, #08ff04')+')',color: 'white'}"
          size="small" :disabled="single" @click="openDialog">回复/预览
        </el-button>
        <el-button type="success" size="small" @click="cancelConcern">取消关注</el-button>
        <el-button type="warning" size="small" @click="shuaxin">刷新</el-button>
        <el-button type="primary" size="small" @click="saveData">保存</el-button>
        <el-button type="primary" size="small" @click="deleteData">删除</el-button>
        <el-select style="margin-left: 10px" v-model="queryParams.type" clearable placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名称"
          clearable
          style="width: 240px;margin-left: 20px;margin-right: 10px"
          @keyup.enter.native="handleQuery"
        />
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
      </el-col>
    </el-row>
    <el-row :gutter="24">
      <el-col :span="24" :xs="24">
        <div class="MyProblemTable">
          <el-table
            :header-cell-style="styleFunc"
            height="calc(100vh - 220px)"
            :data="tableData"
            v-loading="loading"
            :show-overflow-tooltip="true"
            :cell-class-name=tableRowClassName
            size="medium"
            @cell-dblclick="cellClick"
            @selection-change="handleSelectionChange"
            highlight-current-row
            row-key="id"
            ref="multipleTable"
            border
            @row-dblclick="openDialog"
          >
            <el-table-column
              type="selection"
              width="50"
              :reserve-selection="true">
            </el-table-column>
            <el-table-column
              label="序号"
              type="index"
              width="50">
            </el-table-column>
            <el-table-column
              align="center"
              label="预警字段"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <div style="padding-top: 3px;height: 37px;" v-if="scope.row.xwjjsj !== '' && scope.row.xwjjsj != null">
                  <img
                    style="width: 30px"
                    v-if="getTimeText(scope.row.xwjjsj)==='超出'&&scope.row.wtzt!=='已关闭'"
                    :src="require('@/assets/questionIcons/红灯.png')"
                  />
                  <img
                    style="width: 30px"
                    v-if="getTimeText(scope.row.xwjjsj)==='警告'&&scope.row.wtzt!=='已关闭'"
                    :src="require('@/assets/questionIcons/黄灯.png')"
                  />
                  <img
                    style="width: 30px"
                    v-if="getTimeText(scope.row.xwjjsj)==='正常'&&scope.row.wtzt!=='已关闭'"
                    :src="require('@/assets/questionIcons/绿灯.png')"
                  />
                </div>
                <div style="padding-top: 3px;height: 37px;" v-else>
                  <img
                    style="width: 30px"
                    :src="require('@/assets/questionIcons/问题升级.png')"
                  />
                </div>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="wtdb" label="反馈预警" show-overflow-tooltip>
              <template slot-scope="scope">
                <img
                  style="width: 30px"
                  v-if="getFkyjText(scope.row)==='红'"
                  :src="require('@/assets/questionIcons/红警告.png')"
                />
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip width="135" prop="wdwt" label="我的待办待办数量" align="center"/>
            <el-table-column show-overflow-tooltip width="120" prop="wtbh" label="问题编号" header-align="center"/>
            <el-table-column show-overflow-tooltip width="120" prop="cjr" label="人员" header-align="center"/>
            <el-table-column show-overflow-tooltip width="120" prop="cjbm" label="部门" header-align="center"/>
            <el-table-column show-overflow-tooltip width="80" prop="zycd" label="重要程度" align="center"/>
            <el-table-column show-overflow-tooltip width="210" prop="wtmc" label="问题名称" header-align="center"/>
            <el-table-column show-overflow-tooltip width="120" prop="wtly" label="问题来源" align="center"/>
            <el-table-column show-overflow-tooltip width="120" prop="wtlb" label="问题类别" header-align="center"/>
            <el-table-column show-overflow-tooltip width="120" prop="wtxl" label="问题细类" header-align="center"/>
            <el-table-column show-overflow-tooltip width="310" prop="wtms" label="问题描述" header-align="center"/>
            <el-table-column show-overflow-tooltip width="120" prop="wtzt" label="问题状态" header-align="center"/>
            <el-table-column show-overflow-tooltip width="120" prop="yxcd" label="影响程度" header-align="center"/>
            <el-table-column show-overflow-tooltip width="120" prop="gxh" label="工序号" header-align="center"/>
            <el-table-column show-overflow-tooltip width="120" prop="scddh" label="生产订单号" header-align="center"/>
            <el-table-column show-overflow-tooltip width="120" prop="cpxh" label="产品型号" header-align="center"/>
            <el-table-column show-overflow-tooltip width="120" prop="jh" label="件号" header-align="center"/>
            <el-table-column show-overflow-tooltip width="120" prop="pc" label="批次" header-align="center"/>
            <el-table-column show-overflow-tooltip width="120" prop="sb" label="设备" header-align="center"/>
            <el-table-column show-overflow-tooltip width="120" prop="yzrbm" label="阅知人部门" header-align="center"/>
            <el-table-column show-overflow-tooltip width="120" prop="yzr" label="阅知人" header-align="center"/>
            <el-table-column show-overflow-tooltip width="155" prop="cjsj" label="问题创建时间" header-align="center"/>
            <el-table-column show-overflow-tooltip width="155" prop="xwjjsj" label="希望解决时间" header-align="center"/>
            <el-table-column show-overflow-tooltip width="155" prop="jssj" label="接收时间" header-align="center"/>
            <el-table-column show-overflow-tooltip width="155" prop="gbsj" label="关闭时间" header-align="center"/>
            <el-table-column show-overflow-tooltip width="120" prop="lxfk" label="是否例行反馈" header-align="center"/>
            <el-table-column show-overflow-tooltip width="135" prop="xcphwt" label="是否现场配合问题" header-align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.xcphwt === 'true' ? '是' : '否' }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip width="120" prop="ldps" label="是否领导批示" header-align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.ldps === 'true' ? '是' : '否' }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip width="120" prop="gzxt" label="是否跟踪协同" header-align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.gzxt === 'true' ? '是' : '否' }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip width="120" prop="scfj" label="是否上传附件" header-align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.scfj === 'true' ? '是' : '否' }}</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="load"
        />
      </el-col>
    </el-row>

    <!-- 1090   1390-->
    <el-dialog class="dialog dialogRad" :visible.sync="open" :width="width"
               style="padding-top: 0;padding-bottom: 0;margin-top:1px;" append-to-body>
      <huifuyulan ref="huifuyulan" v-if="open" style="height: 800px"
                  :closureID="closureID" :radios="queryParams.radios" :authority="authority"
                  @closeXzxi="closeXzxi"
                  @closeDia="open = false" @changeWidth="changeDialogWidth"/>
    </el-dialog>
    <!--  上传问题的弹出框  -->
    <el-dialog v-if="openScfj" @close="reload" class="xcssjyk" append-to-body title="上传附件" :visible.sync="openScfj"
               width="40%" append-to-body>
      <fj v-if="openScfj" :fileList="fileList" :row="selectFj"></fj>
    </el-dialog>
  </div>
</template>

<script>
import {deleteQuestions, updateQuestions, getMyProblemList, deletegzwtList} from "@/api/question/question";
import huifuyulan from "@/views/question/huifuyulan";
import {listById} from "@/api/fj/fj";
import fj from "@/views/fj/fj";

export default {
  components: {
    fj, huifuyulan
  },
  computed: {
    //判断是否反馈预警
    getFkyjText() {
      return function (row) {
        if (row.qwfksj === undefined || row.qwfksj === null || row.qwfksj === '') {
          return;
        }
        if (row.sfdzfk === 0 || row.sfdzfk === '0') {
          const date = new Date();
          const week = date.getDay();
          return '红';
        }

      };
    },
    getTimeText() {
      return function (time) {
        const currentTime = new Date();
        const targetTime = new Date(time);
        const diffTime = targetTime - currentTime;
        const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24)) + 1;
        if (diffDays >= 2) {
          return '正常'; // 在三天前之前，添加绿色类名
        } else if (diffDays < 2 && diffDays >= 1) {
          return '警告'; // 在三天之内，添加黄色类名
        } else {                 //小于0的时候为红色
          return '超出';
        }
      };
    },
  },
  data() {
    return {
      //权限
      authority: {
        shangchuangfujian: true,//上传附件
        ldps: false,//领导批示
        controls: false,//操作
        recover: true,//回复
        shenqingguanbi: true,//申请关闭
        dbgz: false,//待办/关注
        close: false//关闭
      },
      single: true,//多选就不好使
      multiple: true,//多选也好使
      fileList: [],//文件的集合
      jhjlFileList: [],//交互记录文件
      openScfj: false,//是否打开上传附件弹出框
      whoFjBut: '',//谁的附件上传按钮
      selectFj: '',//查询附件
      wwcts: '',//未完成天数
      cqts: '',//超期天数
      wcts: '',//完成天数
      itemJhjl: {},//单击回复数据的当前值
      isContextMenuVisible: false,//是否显示菜单
      contextMenuPosition: {x: 0, y: 0},//实现右键菜单的
      currentDivIndex: null,//实现单击颜色变化的
      wtms: '',//问题描述不能修改
      huifuTest: '',//回复弹出框的输入框
      huifuId: '',//存上级的id值
      ejhfppyj: '',//存下级的所有id
      //回复预览框需要的的属性
      closureID: {},//点击回复预览的数据，存在这里
      lingdaopishiDialog: false,//领导批示按钮的弹出框的是否显示
      lixingfankuiDialog: false,//例行反馈按钮的弹出框的是否显示
      huifuDialog: false,//回复按钮的弹出框的是否显示
      islxfk: false,//是否是例行反馈
      isShowLdps: false,//判断是否有领导批示
      isShow: false,//判断附件预览是否展示
      width: '1090px',//回复预览弹出框的大小1390
      fujian: false,//附件多选框是否确定
      zrrList: [],//回复预览页面的责任人数据集合
      title: "",
      open: false,//是否打开回复预览页面
      jhsjList: [],//交互数据的数组
      ldpiList: [],

      options: [{
        value: 'wtmc',
        label: '问题名称'
      }, {
        value: 'cpxh',
        label: '产品型号'
      }, {
        value: 'wtly',
        label: '问题来源'
      }, {
        value: 'scddh',
        label: '生产订单号'
      }, {
        value: 'wtms',
        label: '问题描述'
      }],

      ids: [],
      /*点击时间下标*/
      editingIndex: '',
      /*点击事件字段*/
      editingProperty: '',
      /*是否高亮*/
      highLight: false,
      /*用于失去焦距*/
      editing: false,
      formLabelWidth: '120px',
      dialogTableVisible: false,
      tableData: [],
      total: 200,
      handleSelect: [],//录入选中的数据
      loading: true,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tName: null,
        tCode: null,
        tPid: null,
        tLevel: null,
        radios: '待办',
        userName: null
      },
    }
  },
  methods: {
    //关闭选择信息确认
    closeXzxi() {
      this.open = false
      this.load()
    },
    changeDialogWidth(data) {
      this.width = data
    },
    //下载文件
    downLoadFile(row) {
      var name = row.wjmc;
      var url = row.lj;
      var first = name.substring(0, name.lastIndexOf("."));
      var suffix = url.substring(url.lastIndexOf("."), url.length);
      const a = document.createElement('a')
      a.setAttribute('download', first + suffix)
      a.setAttribute('target', '_blank')
      a.setAttribute('href', url)
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
    },
    //上传附件的弹出框关闭的执行方法
    reload() {
      this.fileList = [];
      if (this.whoFjBut === '弹窗') {
        this.dialogReload();
      }
    },
    //上传附件按钮对用的方法
    handleUpload(str, data) {
      this.whoFjBut = str;
      this.selectFj = data || this.closureID
      listById({id: this.selectFj.xh || this.selectFj.id}).then(res => {
        this.fileList = res.rows;
        this.openScfj = true;
      });
    },
    //获取附件列表
    getFJList() {
      listById({id: this.closureID.xh}).then(res => {
        this.fileList = res.rows;
        this.fileData = res.rows;
      });
    },
    //给表格的表头设置颜色
    styleFunc({row, column, rowIndex, columnIndex}) {
      if (column.property === 'wtms' || column.property === 'wtmc') {
        return "background:orange";
      }
    },
    //计算完成天数，未完成天数等数据
    computTime(data) {
      //计算超期天数
      var currentDate = new Date();
      var xwjjsjDate = new Date(data.xwjjsj);
      // 计算时间差（以毫秒为单位）
      var xwjjsjms = xwjjsjDate.getTime() - currentDate.getTime();
      // 将时间差转换为分钟
      var xwjjsjmin = Math.floor(xwjjsjms / (1000 * 60 * 60 * 24));
      this.cqts = xwjjsjmin.toString() + "天";

      //计算完成天数
      if (data.wtzt === '已关闭') {
        var gbsjDate = new Date(data.gbsj);
        // 计算时间差（以毫秒为单位）
        var gbsjms = currentDate.getTime() - gbsjDate.getTime();
        // 将时间差转换为分钟
        var gbsjmin = Math.floor(gbsjms / (1000 * 60 * 60 * 24));
        this.wcts = gbsjmin.toString() + "天";
      }
      //计算未完成天数
      if (data.wtzt !== '已关闭') {
        var cjsjDate = new Date(data.cjsj);
        // 计算时间差（以毫秒为单位）
        var cjsjms = currentDate.getTime() - cjsjDate.getTime();
        // 将时间差转换为分钟
        var cjsjmin = Math.floor(cjsjms / (1000 * 60 * 60 * 24));
        this.wwcts = cjsjmin.toString() + "天";
      } else {
        this.wwcts = '已完成';
      }
    },
    //实现双击弹出回复预览弹出框
    openDialog(row, column) {
      if (column != null) {
        if (this.editingProperty !== 'wtmc' && this.editingProperty !== 'wtms') {
          return
        }
        this.closureID = row
      } else {
        this.closureID = this.handleSelect[0]
      }
      this.width = '1090px';
      this.title = "回复/预览";
      this.open = true;
    },
    //取消关注的方法
    cancelConcern() {
      let number = this.handleSelect.length;
      if (number < 1) {
        this.$message.error("没有选则数据");
        return;
      }
      let ids = [];
      this.handleSelect.forEach(Item => {
        ids.push(Item.id);
      });
      deletegzwtList(ids).then(res => {
        if (res.code === 200) {
          this.$message.success("取消关注成功");
          this.load();
        } else {
          this.$message.error("取消关注失败");
        }
      })
    },
    //按照条件查询数据
    handleQuery() {
      this.load();
    },
    //保存按钮
    saveData() {
      let UpdateList = [];
      this.tableData.forEach(item => {
        if (item.isUpdate === "true") {
          UpdateList.push(item);
        }
      });
      updateQuestions(UpdateList).then(res => {
        if (res.code === 200) {
          this.$message.success("修改成功");
          this.load();
        } else {
          this.$message.error("修改失败");
        }
      });
    },
    //弹窗内刷新按钮
    dialogReload() {
      this.currentDivIndex = '';
      this.jhsjList = [];
      this.wtms = '';
      this.fileList = []
      this.jhjlFileList = []
      if (this.closureID.lxfk === '例行反馈') {
        this.loadJhjlList('例行反馈', '刷新');
        this.islxfk = true;
      } else {
        this.loadJhjlList('回复', '刷新');
        this.islxfk = false;
      }
      this.loadJhjlList('领导批示', '刷新');
      this.open = true;
      this.title = "回复/预览";
      this.loadzerData();
    },
    //删除问题的方法
    deleteData() {
      let num = this.handleSelect.length;
      if (num <= 0) {
        this.$message.error("请至少选择一条数据");
        return
      }
      let ids = [];
      this.handleSelect.forEach(item => {
        ids.push(item.id);
      })
      this.$modal.confirm('是否确认删除数据项？').then(function () {
        return deleteQuestions(ids);
      }).then((res) => {
        if (res.code === 200) {
          this.load();
          this.$modal.msgSuccess("批量删除成功");
        }
      }).catch(() => {
      });
    },
    //刷新按钮
    shuaxin() {
      this.tableData = [];
      this.load();
    },
    //改变单选框执行的方法
    changeRadio() {
      this.load();
    },
    //页面加载完后执行加载的数据
    load() {
      this.loading = true;
      getMyProblemList(this.queryParams).then(res => {
        if (res.code === 200) {
          this.tableData = res.rows;
          this.total = res.total;
          this.loading = false;
          this.$refs.multipleTable.clearSelection();
        }
      });
    },
    // 控制input显示 row 当前行 column 当前列
    cellClick(row, column) {
      //this.editing = true
      //this.editingIndex = row.index
      this.editingProperty = column.property
      this.highLight = true
    },
    tableRowClassName({row, column, rowIndex, columnIndex}) {
      //把每一行的索引放进row,和index
      row.index = rowIndex;
      column.index = columnIndex;
    },
    // 失去焦点初始化
    inputBlur(object) {
      this.editing = false;
      object.row.isUpdate = "true";
    },
    //多选框的方法
    handleSelectionChange(val) {
      this.handleSelect = val
      this.single = val.length !== 1
      this.multiple = !val.length
    },
    //小气泡的触发事件
    handlePageClick(event) {
      // 检查点击事件是否发生在小气泡上
      const isBubbleClicked = event.target.closest('.chatBox');

      if (!isBubbleClicked) {
        this.currentDivIndex = null; // 点击的是其他地方，将当前小气泡的索引设为 null，恢复原来的颜色
      }
    },
    //打开弹出框的焦点事件
    dialogOpened() {
      this.$nextTick(() => {
        this.$refs.huifuInput.focus();
      });
    },
    //打开弹出框的焦点事件
    dialogLDPSOpened() {
      this.$nextTick(() => {
        this.$refs.lingdaoInput.focus();
      });
    },
    // 打开弹出框的焦点事件
    dialogLXFKOpened() {
      this.$nextTick(() => {
        this.$refs.lixingfankuiInput.focus();
      });
    }
  },
  mounted() {
    this.load();
    // 打开弹窗清空右侧树形框
    document.addEventListener('click', this.handlePageClick);
  },
  beforeDestroy() {
    //注销弹窗清空右侧树形框
    document.removeEventListener('click', this.handlePageClick);
  }
};
</script>
<style>
.el-checkbox__input.is-checked + .el-checkbox__label {
  color: white;
}

.fj .el-checkbox__label {
  font-size: 16px;
}

.dialogRad .el-dialog__close {
  font-size: 30px; /* 调整按钮大小 */
}

.dialogRad .el-dialog__headerbtn .el-dialog__close {
  color: red;
}

.dialogRad .el-tooltip__popper {
  font-size: 18px; /* 调整字体大小为你需要的大小 */
}

.cards .el-card__body {
  padding: 0;
}

.dialogRad .el-dialog__body {
  padding-top: 0;
  padding-bottom: 20px;
}

.dialogRad .el-dialog {
  border-radius: 10px;
}

.dialogRad .el-aside {
  padding: 0;
}

.dialogRad .el-main {
  padding: 0;
}

.card .el-card__body {
  padding: 0;
}

.dialog .el-dialog__header {
  padding: 0;
}

.dialog .el-dialog__body {
  padding: 0;
}

.dialogRad .el-aside {
  margin-bottom: 0;
}

.buttonDialog .el-dialog:not(.is-fullscreen) {
  margin-top: 15% !important;
}

.el-textarea.is-disabled .el-textarea__inner {
  color: black;
  font-size: 17px;
}

.MyProblemTable .el-table__cell {
  height: 40px;
}

.MyProblemTable .el-table--medium .el-table__cell {
  padding: 0;
}

.dialogRad .el-dialog__headerbtn {
  z-index: 100;
}
</style>
<style scoped lang="scss">
.el-table {
  color: black;
}

.context-menu {
  position: fixed;
  background-color: white;
  padding: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.menu-item {
  padding: 5px;
  cursor: pointer;
}

.menu-item:hover {
  background-color: lightblue;
}

.green-color {
  background-color: green;
}

.yellow-color {
  background-color: yellow;
}

.red-color {
  background-color: red;
}

.clicked {
  background-color: #ffba00 !important;
}

.clicked::before {
  border-right-color: #ffba00 !important;
}

.app-container {
  padding-top: 10px;
}

.el-row {
  margin-bottom: 10px;

  &:last-child {
    margin-bottom: 0;
  }
}

.el-col {
  line-height: 20px;
}

.el_radios_position {
  margin-right: 10px;
}

.app-container {
  padding-top: 10px;
}

/*高度为100% */
.indexHeight {
  height: 100%;
}

.chatBox {
  font-size: 18px;
  display: inline-block;
  background-color: greenyellow;
  padding: 2px 5px;
  border-radius: 8px;
  margin-left: 10px;
  position: relative;
  word-break: break-all;
  border: 1px solid #989898;
  cursor: default;
  margin-bottom: 10px;
}

.chatBox-left::before {
  content: '';
  margin-top: 8px;
  margin-left: -16px;
  position: absolute;
  border: 5px solid;
  border-right-color: greenyellow;
  border-left-color: transparent;
  border-top-color: transparent;
  border-bottom-color: transparent;
}

.chatName {
  display: inline-block;
  background-color: #c0d6f1;
  border-radius: 30px;
  padding: 5px;
  font-size: 16px;
}

.chatTime {
  display: inline-block;
}

.dialog {
  position: absolute;;
  z-index: 1;
}

.tablec ::v-deep .el-table {
  background: rgba(0, 0, 0, 0);
}

.tablec ::v-deep .el-table tr {
  background: rgba(0, 0, 0, 0);
}

.tablec ::v-deep .el-table .el-table__header-wrapper th, .el-table .el-table__fixed-header-wrapper th {
  background-color: rgba(0, 0, 0, 0);
}
</style>
