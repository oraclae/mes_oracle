<template>
  <div class="app-container home">
    <el-row :gutter="24">
      <!--用户数据-->
      <el-col :span="20" :xs="24">
        <el-radio-group v-model="queryParams.radios" @change="changeRadio">
          <el-radio label="待办" class="el_radios_position">待办</el-radio>
          <el-radio label="已关闭" class="el_radios_position">已关闭</el-radio>
        </el-radio-group>
        <el-button type="success" size="small" :disabled="single" @click="openDialog">回复/预览</el-button>
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
              prop="wtdb"
              label="我的问题待办数量"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'wtdb'"
                          v-model="scope.row.wtdb" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.wtdb }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="wtbh"
              label="问题编号"
              show-overflow-tooltip
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'wtbh'"
                          v-model="scope.row.wtbh" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.wtbh }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="cjr"
              label="人员"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'cjr'"
                          v-model="scope.row.cjr" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.cjr }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="cjbm"
              label="部门"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'cjbm'"
                          v-model="scope.row.cjbm" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.cjbm }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="wtmc"
              label="问题名称"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'wtmc'"
                          v-model="scope.row.wtmc" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.wtmc }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="gxh"
              label="工序号"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'gxh'"
                          v-model="scope.row.gxh" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.gxh }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="cjsj"
              label="问题创建时间"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'cjsj'"
                          v-model="scope.row.cjsj" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.cjsj }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="scddh"
              label="生产订单号"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'scddh'"
                          v-model="scope.row.scddh" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.scddh }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="cpxh"
              label="产品型号"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'cpxh'"
                          v-model="scope.row.cpxh" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.cpxh }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="wtly"
              label="问题来源"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'wtly'"
                          v-model="scope.row.wtly" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.wtly }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="wtlb"
              label="问题类别"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'wtlb'"
                          v-model="scope.row.wtlb" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.wtlb }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="wtxl"
              label="问题细类"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'wtxl'"
                          v-model="scope.row.wtxl" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.wtxl }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="wtms"
              label="问题描述"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'wtms'"
                          v-model="scope.row.wtms" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.wtms }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="yzrbm"
              label="阅知人部门"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'yzrbm'"
                          v-model="scope.row.yzrbm" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.yzrbm }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="yzr"
              label="阅知人"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'yzr'"
                          v-model="scope.row.yzr" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.yzr }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="zycd"
              label="重要程度"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'zycd'"
                          v-model="scope.row.zycd" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.zycd }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="xwjjsj"
              label="希望解决时间"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'xwjjsj'"
                          v-model="scope.row.xwjjsj" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.xwjjsj }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="dqzrr"
              label="主责任人"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'sjzrr'"
                          v-model="scope.row.sjzrr" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.dqzrr }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="wtzt"
              label="问题状态"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'wtzt'"
                          v-model="scope.row.wtzt" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.wtzt }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="jssj"
              label="接收时间"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'jssj'"
                          v-model="scope.row.jssj" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.jssj }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="gzxt"
              label="是否跟踪协同"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'gzxt'"
                          v-model="scope.row.gzxt" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.gzxt === 'true' ? '是' : '否' }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="gbsj"
              label="关闭时间"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'gbsj'"
                          v-model="scope.row.gbsj" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.gbsj }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="lxfk"
              label="是否例行反馈"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'lxfk'"
                          v-model="scope.row.lxfk" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.lxfk }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="pc"
              label="批次"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'pc'"
                          v-model="scope.row.pc" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.pc }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="sb"
              label="设备"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'sb'"
                          v-model="scope.row.sb" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.sb }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="xcphwt"
              label="是否现场配合问题"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'xcphwt'"
                          v-model="scope.row.xcphwt" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.xcphwt === 'true' ? '是' : '否' }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="ldps"
              label="是否领导批示"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'ldps'"
                          v-model="scope.row.ldps" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.ldps === 'true' ? '是' : '否' }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="scfj"
              label="是否上传附件"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'scfj'"
                          v-model="scope.row.scfj" @blur="inputBlur(scope)"></el-input>
                <span v-else>{{ scope.row.scfj === 'true' ? '是' : '否' }}</span>
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
import {
  deleteQuestions, updateQuestions, getMyProblemList,
  deletegzwtList, getJhjl, getLDPS, saveJhjlList,
  getzerData, updateQuestionWdwt, updateQuestionWTDBToZero,
  deleteJhjlByXh, updateQuestionWDWTToZero, updateMyDoListStatus, updateSfdzfk
} from "@/api/question/question";
import huifuyulan from "@/views/question/huifuyulan";
import {getFjByIds, listById} from "@/api/fj/fj";
import fj from "@/views/fj/fj";

export default {
  components: {
    fj,huifuyulan
  },
  data() {
    return {
      //权限
      authority: {
        shangchuangfujian: true,//上传附件
        ldps:false,//领导批示
        controls:false,//操作
        recover: true,//回复
        shenqingguanbi: true,//申请关闭
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
      }else {
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
