<template>
  <div class="downcontainer">
    <div class="left-side">
      <el-row :gutter="10"
              style="text-align: center;margin-top: 20px;cursor: default;margin-left: 40px">
        <el-col :span="12">
          <div style="width: 80%;color: rgb(0 255 48);">
            <b
              style="width: 100%;top: 0;left: 0;font-size: 46px;text-shadow: rgb(0 86 255) 2px 2px 4px">{{
                this.wcsl
              }}</b>
            <br>
            <div style="margin-top: 10px">
              <b style="font-size: 25px;color: black">完成数量</b>
            </div>
          </div>
        </el-col>
        <el-col :span="12">
          <div style="width: 80%;color: rgb(255 40 40)">
            <b
              style="width: 100%;top: 0;left: 0;font-size: 46px;text-shadow: rgb(140 0 255) 2px 2px 4px">{{
                this.wwcsl
              }}</b>
            <br>
            <div style="margin-top: 10px">
              <b style="font-size: 25px;color: black">未完成数量</b>
            </div>
          </div>
        </el-col>
      </el-row>
      <div style="height: 710px;overflow: hidden">
        <div id="piechart" style="width: 100%;height: 300px"/>
        <div id="histogram" style="width:100%;height: 470px;bottom: 50px"/>
      </div>
    </div>
    <div class="right-side">
      <div class="r-onerow" style="height: 36px;margin-bottom: 10px">
        <el-row>
          <el-col :span="11">
            <el-button
              type="primary"
              icon="el-icon-loading"
              @click="refresh"
            >刷新
            </el-button>
          </el-col>
          <el-col :span="13">
            <b style="font-size: 20px">现场配合问题</b>
          </el-col>
        </el-row>
      </div>
      <div class="r-tworow" style="height: calc((100% - 36px - 36px - 30px)*0.5)">
        <el-table border v-loading="downQuestionLoading" height="calc(50vh - 150px)" :data="downQuestionList"
                  highlight-current-row @row-dblclick="jswtOrJjfa">
          <!--          <el-table-column type="selection" width="55" align="center"/>-->
          <el-table-column label="操作" width="100px" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                v-if="scope.row.wtzt === '提交'"
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="receive(scope.row)"
              >接收问题
              </el-button>
              <el-button
                v-if="scope.row.wtzt === '接收'"
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="solve(scope.row)"
                style="color: red"
              >解决方案
              </el-button>
            </template>
          </el-table-column>
          <el-table-column type="index" label="序号" align="center" width="50px"/>
          <el-table-column show-overflow-tooltip label="展示" header-align="center" width="50px">
            <template slot-scope="scope">
              <div
                v-if="scope.row.wtzt === '已完成' || scope.row.wtzt === '确认已完成' || scope.row.wtzt === '提交' || scope.row.wtzt === '接收' && (scope.row.jjfa == null || scope.row.jjfa === '')">
                <img
                  style="width: 30px;margin-top: 4px"
                  v-if="scope.row.wtzt === '提交'"
                  :src="require('@/assets/questionIcons/红灯.png')"
                  alt="null"/>
                <img
                  style="width: 30px;margin-top: 4px"
                  v-if="scope.row.wtzt === '接收' && (scope.row.jjfa == null || scope.row.jjfa === '')"
                  :src="require('@/assets/questionIcons/黄灯.png')"
                  alt="null"/>
              </div>
              <img
                alt="null"
                style="width: 30px;margin-top: 4px"
                v-else
                :src="require('@/assets/questionIcons/绿灯.png')"
              />
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="创建部门科室" header-align="center" width="150px" prop="cjbmks"/>
          <el-table-column show-overflow-tooltip label="问题名称" header-align="center" width="150px" prop="wtmc"/>
          <el-table-column show-overflow-tooltip label="问题类别" header-align="center" width="150px" prop="wtlb"/>
          <el-table-column show-overflow-tooltip label="问题细类" header-align="center" width="150px" prop="wtxl"/>
          <el-table-column show-overflow-tooltip label="问题状态" header-align="center" width="150px" prop="wtzt"/>
          <el-table-column show-overflow-tooltip width="120" prop="wtbh" label="问题编号" header-align="center"/>
          <el-table-column show-overflow-tooltip label="生产订单号" header-align="center" width="150px" prop="scddh"/>
          <el-table-column show-overflow-tooltip label="产品型号" header-align="center" width="150px" prop="cpxh"/>
          <el-table-column show-overflow-tooltip label="件号" header-align="center" width="150px" prop="jh"/>
          <el-table-column show-overflow-tooltip label="班产日期" header-align="center" width="150px" prop="bcrq"/>
          <el-table-column show-overflow-tooltip label="批次" header-align="center" width="150px" prop="pc"/>
          <el-table-column show-overflow-tooltip label="工序号" header-align="center" width="150px" prop="gxh"/>
          <el-table-column show-overflow-tooltip label="问题描述" header-align="center" width="150px" prop="wtms"/>
          <el-table-column show-overflow-tooltip label="设备型号" header-align="center" width="150px" prop="sbxh"/>
          <el-table-column show-overflow-tooltip label="是否满意" header-align="center" width="150px" prop="sfmy"/>
          <el-table-column show-overflow-tooltip label="提报人" header-align="center" width="150px" prop="tbr"/>
          <el-table-column show-overflow-tooltip label="被叫责任科室" header-align="center" width="150px" prop="bjzrks"/>
          <el-table-column show-overflow-tooltip label="被叫人" header-align="center" width="150px" prop="bjzrr"/>
          <el-table-column show-overflow-tooltip label="接收时间" header-align="center" width="150px" prop="tbjssj"/>
          <el-table-column show-overflow-tooltip label="当前时间" header-align="center" width="150px" prop="tbdqst"/>
          <el-table-column show-overflow-tooltip label="接收人" header-align="center" width="150px" prop="jsr"/>
          <el-table-column show-overflow-tooltip label="解决方案" header-align="center" width="150px" prop="jjfa"/>
          <el-table-column show-overflow-tooltip label="创建时间" header-align="center" width="150px" prop="cjsj"/>
        </el-table>
        <div style="height: 50px">
          <pagination
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getxcphList"
            style="background-color: transparent;margin-right: 50px"
          />
        </div>
      </div>
      <div class="r-threerow" style="height: 36px;margin: 10px 0">
        <el-row>
          <el-col :span="11">
            <el-button
              type="primary"
              icon="el-icon-loading"
              @click="refreshYWCJ"
            >刷新
            </el-button>
          </el-col>
          <el-col :span="13">
            <b style="font-size: 20px">跟踪协同问题</b>
          </el-col>
        </el-row>
      </div>
      <div class="r-fourrow" style="height: calc((100% - 36px - 36px - 30px)*0.5)">
        <el-table border v-loading="ywcjQuestionLoading" height="calc(50vh - 150px)" :data="ywcjList"
                  highlight-current-row @row-dblclick="questionDetails">
          <el-table-column label="操作" width="100px" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="questionDetails(scope.row)"
              >任务详细
              </el-button>
            </template>
          </el-table-column>
          <el-table-column type="index" label="序号" align="center" width="50px"/>
          <el-table-column show-overflow-tooltip label="关注" header-align="center" width="50px">
            <template slot-scope="scope">
              <img
                v-if="scope.row.sfgz"
                style="width: 30px;margin-top: 4px"
                :src="require('@/assets/questionIcons/红旗子.png')"
                alt="null"/>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="我的问题待办数量" header-align="center" width="150px" prop="wtdb"/>
          <el-table-column show-overflow-tooltip label="问题编号" header-align="center" width="150px" prop="wtbh"/>
          <el-table-column show-overflow-tooltip label="人员" header-align="center" width="150px" prop="cjr"/>
          <el-table-column show-overflow-tooltip label="部门" header-align="center" width="150px" prop="cjbm"/>
          <el-table-column show-overflow-tooltip label="问题名称" header-align="center" width="150px" prop="wtmc"/>
          <el-table-column show-overflow-tooltip label="工序号" header-align="center" width="150px" prop="gxh"/>
          <el-table-column show-overflow-tooltip label="问题创建时间" header-align="center" width="150px" prop="cjsj"/>
          <el-table-column show-overflow-tooltip label="生产订单号" header-align="center" width="150px" prop="scddh"/>
          <el-table-column show-overflow-tooltip label="产品型号" header-align="center" width="150px" prop="cpxh"/>
          <el-table-column show-overflow-tooltip label="问题来源" header-align="center" width="150px" prop="wtly"/>
          <el-table-column show-overflow-tooltip label="问题类别" header-align="center" width="150px" prop="wtlb"/>
          <el-table-column show-overflow-tooltip label="问题细类" header-align="center" width="150px" prop="wtxl"/>
          <el-table-column show-overflow-tooltip label="问题描述" header-align="center" width="150px" prop="wtms"/>
          <el-table-column show-overflow-tooltip label="责任部门" header-align="center" width="150px" prop="zrbm"/>
          <el-table-column show-overflow-tooltip label="责任人" header-align="center" width="150px" prop="zrr"/>
          <el-table-column show-overflow-tooltip label="重要程度" header-align="center" width="150px" prop="zycd"/>
          <el-table-column show-overflow-tooltip label="希望解决时间" header-align="center" width="150px" prop="xwjjsj"/>
          <el-table-column show-overflow-tooltip label="升级责任人" header-align="center" width="150px" prop="sjzrr"/>
          <el-table-column show-overflow-tooltip label="问题状态" header-align="center" width="150px" prop="wtzt"/>
          <el-table-column show-overflow-tooltip label="接收时间" header-align="center" width="150px" prop="jssj"/>
          <el-table-column show-overflow-tooltip label="解决时间/时" header-align="center" width="150px" prop="jjsj"/>
          <el-table-column show-overflow-tooltip label="是否例行反馈" header-align="center" width="150px" prop="lxfk"/>
        </el-table>
        <div style="height: 50px">
          <pagination
            :total="ywcjtotal"
            :page.sync="ywcjqueryParams.pageNum"
            :limit.sync="ywcjqueryParams.pageSize"
            @pagination="getYWCJList"
            style="background-color: transparent;margin-right: 50px"
          />
        </div>
      </div>
    </div>
    <!--接收按钮弹窗-->
    <el-dialog
      class="buttonDialog"
      title
      center
      v-if="wtjsDialog"
      :visible.sync="wtjsDialog" width="500px">
      <span slot="title" style="font-size: 30px;">接收问题</span>
      <div style="font-size: 20px;text-align: center;width: 100%; color: black">是否为被叫责任人?</div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="wtjsOK('是')">是</el-button>
        <el-button type="primary" @click="notBjzrr" style="margin-left: 20px">否</el-button>
        <el-button @click="wtjsDialog = false" style="margin-left: 20px">取 消</el-button>
      </span>
    </el-dialog>
    <!--是否为被叫责任人 否 按钮弹窗-->
    <el-dialog
      class="buttonDialog"
      title
      center
      v-if="notBjzrrDialog"
      :visible.sync="notBjzrrDialog" width="500px">
      <span slot="title" style="font-size: 30px;">请选择接收人</span>
      <div style="font-size: 20px;text-align: center;width: 100%">
        <el-select style="width: 80%; font-size: 30px" v-model="jsrId" clearable placeholder="请选择接收人">
          <el-option
            v-for="item in jsrList"
            :key="item.userId"
            :label="item.nickName"
            :value="item.userId">
          </el-option>
        </el-select>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="wtjsOK('否')">确定</el-button>
        <el-button @click="notBjzrrDialog = false" style="margin-left: 30px">取 消</el-button>
      </span>
    </el-dialog>
    <!--解决方案弹窗-->
    <el-dialog
      :close-on-click-modal="false"
      class="buttonDialog"
      title
      center
      v-if="solveDialog"
      :visible.sync="solveDialog" width="500px">
      <span slot="title" style="font-size: 30px;">解决方案</span>
      <div style="font-size: 20px;text-align: center;width: 100%">
        <el-form ref="jjfaform" :model="jjfaform" :rules="jjfarules" style="padding: 0 50px">
          <el-form-item prop="jjfa">
            <el-input style="font-size: 20px" type="textarea" v-model="jjfaform.jjfa" placeholder="请输入解决方案"/>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="zndyDialog()">启用问题处理库</el-button>
        <el-button type="primary" @click="solveOK()">确 定</el-button>
        <el-button @click="solveDialog = false" style="margin-left: 20px">取 消</el-button>
      </span>
    </el-dialog>
    <!--申请关闭弹窗-->
    <el-dialog
      class="buttonDialog"
      title
      center
      :visible.sync="toCloseDialog" width="500px">
      <span slot="title" style="font-size: 30px;">系统提示</span>
      <div style="font-size: 20px;text-align: center;width: 100%">是否确定关闭该数据项?</div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="toCloseOK()">确 定</el-button>
        <el-button @click="toCloseDialog = false" style="margin-left: 20px">取 消</el-button>
      </span>
    </el-dialog>
    <!-- 1090   1390-->
    <el-dialog class="dialog dialogRad" :visible.sync="open" :width="width"
               style="padding-top: 0;padding-bottom: 0;margin-top:1px;" append-to-body>
      <huifuyulan ref="huifuyulan" v-if="open" style="height: 800px"
                  :closureID="closureID" :radios="queryParams.radios" :authority="authority"
                  @closeXzxi="closeXzxi" @closeDia="open = false" @changeWidth="changeDialogWidth"/>
    </el-dialog>
    <!--  上传附件的弹出框  -->
    <el-dialog v-if="openScfj" @close="reload" class="xcssjyk" append-to-body title="上传附件" :visible.sync="openScfj"
               width="40%" append-to-body>
      <fj v-if="openScfj" :fileList="fileList" :row="selectFj"></fj>
    </el-dialog>

    <el-dialog class="dialogRad" :close-on-click-modal="false" title="启用问题处理库" width="1200px"
               :visible.sync="lishidaanDialog">
      <zdhf ref="cxda" @xiangxixinxi="xiangxixinxi" @dialogdafk="dialogdafk"
            v-if="lishidaanDialog" :form="selectedRow"></zdhf>
    </el-dialog>
    <!--详细信息弹窗-->
    <el-dialog class="buttonDialog" :close-on-click-modal="false" @close="shifoumanyi" title="详细信息"
               :visible.sync="xiangxiDialog">
      <xiangxixinxi v-if="xiangxiDialog" :jhsj-list="jhsjList"></xiangxixinxi>
    </el-dialog>
    <!--是否满意-->
    <el-dialog
      :close-on-click-modal="false"
      class="buttonDialog"
      title
      center
      v-if="shifoumanyiDialog"
      :visible.sync="shifoumanyiDialog" width="500px">
      <span slot="title" style="font-size: 30px;">系统提示</span>
      <div style="font-size: 20px;text-align: center;width: 100%">问题解决方案是否满意?</div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click.once="closeitOK">满 意</el-button>
        <el-button type="primary" @click.once="shifoumanyiDialog=false" style="margin-left: 20px">不满意</el-button>
        <el-button @click="shifoumanyiDialog=false" style="margin-left: 20px">取消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  downlistCjls,
  ywcjList,
  updatejjfa,
  getwcsl,
  getcjksbmNumber,
  speechListCjls,
  wtjsIt,
  getjsrBywtlb, updateDaccToRd
} from "@/api/question/upQuestion";
import {
  activate,
  deleteJhjlByXh,
  getJhjl, getJhjlByWtid,
  getzerData,
  saveJhjlList, updateMyDoListStatus, updateWtztEnd,
} from "@/api/question/question";
import * as echarts from 'echarts';
import {listById, getFjByIds} from "@/api/fj/fj";
import huifuyulan from "@/views/question/huifuyulan";
import fj from "@/views/fj/fj";
import {getUserInfo} from "@/api/question/wtlbry";
import zdhf from "@/views/question/MyToDoList/znwdDialog";
import xiangxixinxi from "@/views/question/myquestion/xiangxixinxi";

export default {
  name: "DownQuestion",
  components: {
    fj, zdhf, xiangxixinxi, huifuyulan
  },
  data() {
    return {
      //权限
      authority: {
        shangchuangfujian: true,//上传附件
        ldps: false,//领导批示
        controls: false,//操作
        recover: true,//回复
        shenqingguanbi: false,//申请关闭
        close: false,//关闭
        isdown:true,//问题接收页面
      },
      userInfo: {},
      lishidaanDialog: false,//历史答案弹窗
      xiangxiDialog: false,//详细信息是否弹出框
      shifoumanyiDialog: false,//是否满意的弹出框
      fileList: [],//文件的集合
      openScfj: false,//是否打开上传附件弹出框
      whoFjBut: '',//谁的附件上传按钮
      selectFj: '',//查询附件
      wwcts: '',//未完成天数
      cqts: '',//超期天数
      wcts: '',//完成天数
      //问题状态与数量
      wtztNumber: [],
      //饼图数据
      piechartData: [],
      // 条形图数据
      histogramData: [],
      // 条形图项目
      histogramCategory: [],
      // 条形图的值
      histogramValue: [],
      //完成数量
      wcsl: 0,
      //未完成数量
      wwcsl: 0,
      // 选中数组
      ids: [],
      // 跟踪协同问题选中数组
      ywcjids: [],
      //选中数据
      selectedRow: {},
      //跟踪协同问题选中数据
      closureID: {},//点击回复预览的数据，存在这里: {},
      // 是否全选
      selectAll: false,
      // 跟踪协同问题是否全选
      ywcjselectAll: false,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 非单个禁用
      ywcjsingle: true,
      // 非多个禁用
      ywcjmultiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 跟踪协同问题总条数
      ywcjtotal: 0,
      // 问题接收表格数据
      downQuestionList: [],
      downQuestionLoading: false,//遮罩层
      // 跟踪协同问题表格数据
      ywcjList: [],
      ywcjQuestionLoading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 查询参数
      ywcjqueryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 问题接收弹窗
      wtjsDialog: false,
      // 是否为被叫责任人弹窗 否 按钮
      notBjzrrDialog: false,
      // 接收责任人列表
      jsrList: [],
      // 选中的接收人
      jsrId: null,
      // 解决方案弹窗
      solveDialog: false,
      // 解决方案内容
      solveText: '',
      // 解决方案弹窗的form
      jjfaform: {},
      // 解决方案表单校验
      jjfarules: {
        jjfa: [
          {required: true, message: '请填解决方案', trigger: 'blur'}
        ],
      },
      // 申请关闭弹窗
      toCloseDialog: false,
      /*任务详情相关*/
      itemJhjl: {},
      isContextMenuVisible: false,
      contextMenuPosition: {x: 0, y: 0},//实现右键菜单的
      currentDivIndex: null,//实现单击颜色变化的
      wtms: '',//问题描述不能修改
      huifuTest: '',//回复弹出框的输入框
      huifuId: '',//存上级的id值
      ejhfppyj: '',//存下级的所有id
      //回复预览框需要的的属性
      lingdaopishiDialog: false,//领导批示按钮的弹出框的是否显示
      lixingfankuiDialog: false,//例行反馈按钮的弹出框的是否显示
      huifuDialog: false,//回复按钮的弹出框的是否显示
      islxfk: false,//是否是例行反馈
      isShowLdps: false,//判断是否有领导批示
      isShow: false,//判断附件预览是否展示
      width: '1090px',//回复预览弹出框的大小1390
      fujian: false,//附件多选框是否确定
      jhjlFileList: [],//交互记录文件
      zrrList: [],//回复预览页面的责任人数据集合
      title: "",
      open: false,//是否打开回复预览页面
      jhsjList: [],//交互数据的数组
      ldpiList: [],
      /** 喊话相关 */
      //朗读定时器
      speechTimer: null,
      //获取现场配合问题列表定时器
      getxcphListTimer: null,
      //获取的旧数据
      oldDownQuestionList: [],
      //定时器新获取的数据
      newDownQuestionList: [],
    };
  },
  mounted() {
    document.addEventListener('click', this.handlePageClick);
    this.getUserInfo()
    //查询问题接收列表
    this.getxcphList()
    //查询所有问题接收列表并喊话
    this.getxcphListAllAndSpeech();
    //查询跟踪协同问题列表
    this.getYWCJList();
    //定时获取现场配合问题列表
    this.intervalGetxcphList();
  },
  beforeDestroy() {
    document.removeEventListener('click', this.handlePageClick);
  },
  methods: {
    getUserInfo() {
      getUserInfo().then(response => {
        this.userInfo = response.data
      })
    },
    //关闭选择信息确认
    closeXzxi() {
      this.open = false
      this.getYWCJList()
    },
    changeDialogWidth(data) {
      this.width = data
    },
    //满意的按钮执行方法
    closeitOK() {
      updateDaccToRd({daxxid: this.daccid}).then(res => {
        if (res.code === 200) {
          this.$message.success("已满意！");
          this.$refs.cxda.getList();
        } else {
          this.$message.error("操作失败");
        }
      });
      this.shifoumanyiDialog = false;
    },
    //关闭详细信息执行的方法
    shifoumanyi() {
      this.shifoumanyiDialog = true;
    },
    dialogdafk(row) {
      const id = this.selectedRow.lsid
      updatejjfa(id, row.daxx).then(() => {
        this.$message.success("修改成功！");
        /*修改xml状态申请已完成*/
        this.getxcphList();
        this.solveDialog = false
        this.lishidaanDialog = false
      })
    },
    //详细信息的方法
    xiangxixinxi(row) {
      this.daccid = row.daxxid;
      this.jhsjList = [];
      getJhjlByWtid({WTID: row.wtid}).then(res => {
        if (res.code === 200) {
          this.jhsjList = res.rows;
        }
      });
      this.xiangxiDialog = true
    },
    zndyDialog() {
      this.lishidaanDialog = true
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
    // 饼形图
    piechart() {
      var chartDom = document.getElementById('piechart');
      if (chartDom == null) {
        return
      }
      var myChart = echarts.init(chartDom);
      var option;
      option = {
        grid: {
          top: '0',  // 设置图表区域相对于容器顶部的距离
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          itemWidth: 35, // 设置图例项的宽度
          itemHeight: 35, // 设置图例项的高度
          orient: 'vertical',
          left: '280px',
          top: 'center',
          textStyle: {
            color: 'black', // 设置图例字体的颜色
            fontSize: 20  // 设置图例字体的大小
          },
          formatter: function (name) {
            var dataItem = option.series[0].data.find(function (item) {
              return item.name === name;
            });
            return name + ':' + (dataItem ? dataItem.value : '');
          }
        },
        series: [
          {
            type: 'pie',
            radius: '75%',
            center: ['30%', '50%'],  // 设置系列相对于图表区域的位置，这里将系列位置设置为居中靠上
            data: this.piechartData,
            label: {
              show: false,
              color: 'black',
              fontSize: 20,
              formatter: '{b}：{c}', // 自定义标签内容格式，{c} 表示数据项的值
            },
            labelLine: {
              show: false,
              length: 20,
              lineStyle: {
                width: 2,
              }
            },
          }
        ]
      };
      option && myChart.setOption(option);
    },
    // 柱状图
    histogram() {
      var chartDom = document.getElementById('histogram');
      if (chartDom == null) {
        return
      }
      var myChart = echarts.init(chartDom);
      var option;
      option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '130'
        },
        yAxis: {
          type: 'category',
          data: this.histogramCategory,
          axisLabel: {
            color: 'black',
            fontSize: 20
          },
          axisLine: {
            lineStyle: {
              color: 'black',  // 将 y 轴线颜色设置为白色
            }
          }
        },
        xAxis: {
          type: 'value',
          position: 'top',
          axisLabel: {
            color: 'black'  // 将 x 轴标签字体颜色设置为白色
          },
        },
        series: [
          {
            data: this.histogramValue,
            type: 'bar',
            itemStyle: {
              color: '#1890ff',  // 将柱状图的颜色设置为 "#3CD0D0"
              barBorderRadius: 4
            }
          }
        ]
      };
      option && myChart.setOption(option);
    },
    /** 查询问题接收列表并喊话 */
    getxcphListAllAndSpeech() {
      speechListCjls().then(response => {
        this.oldDownQuestionList = response.rows;
        this.oldDownQuestionList.forEach((v, i) => {
          if (v.wtzt === '提交') {
            const speechText = "请" + v.bjzrr + "接收问题"
            const speechUtterance = new SpeechSynthesisUtterance(speechText);
            for (let i = 0; i < 3; i++) {
              window.speechSynthesis.speak(speechUtterance);
            }
          }
        })
        if (this.oldDownQuestionList.length > 0) {
          //十分钟一次读数据
          this.tenMinutesSpeech()
        }
      });
    },
    /** 5分钟一次循环读，如果没读完就顺延5分钟（原10分钟） */
    tenMinutesSpeech() {
      this.speechTimer = setInterval(() => {
        // 如果语音合成引擎正在朗读
        if (window.speechSynthesis.speaking) {
          clearInterval(this.speechTimer);
          this.tenMinutesSpeech();
          // window.speechSynthesis.cancel(); // 停止朗读
        } else {
          this.oldDownQuestionList.forEach((v, i) => {
            if (v.wtzt === '提交') {
              const speechText = "请" + v.bjzrr + "接收问题"
              const speechUtterance = new SpeechSynthesisUtterance(speechText);
              for (let i = 0; i < 3; i++) {
                window.speechSynthesis.speak(speechUtterance);
              }
            }
          })
        }
      }, 5 * 60 * 1000); // 5分钟
    },
    /** 2分钟定时获取现场配合问题列表并朗读新数据 */
    intervalGetxcphList() {
      this.threeTimer = setInterval(() => {
        this.getNewxcphListAllAndSpeech()
        this.getxcphList()
      }, 2 * 60 * 1000); // 2分钟
    },
    //获取新数据并朗读，如果有新数据则重置10分钟定时器
    getNewxcphListAllAndSpeech() {
      //获取列表并将老数据删掉
      speechListCjls().then(response => {
        let data;
        data = [];
        this.newDownQuestionList = response.rows;
        const val = this.oldDownQuestionList
        const list = data.concat(this.newDownQuestionList)

        val.forEach((value, index) => {
          this.newDownQuestionList.forEach((v, i) => {
            if (value.lsid === v.lsid && value.wtzt === v.wtzt) {
              this.newDownQuestionList.splice(i, 1)
            }
          })
        })
        //朗读三遍新数据
        if (this.newDownQuestionList.length > 0) {
          this.newDownQuestionList.forEach((v, i) => {
            if (v.wtzt === '提交') {
              const speechText = "请" + v.bjzrr + "接收问题"
              const speechUtterance = new SpeechSynthesisUtterance(speechText);
              for (let i = 0; i < 3; i++) {
                window.speechSynthesis.speak(speechUtterance);
              }
            }
          })
          //重置读数据的定时器
          clearInterval(this.speechTimer);
          this.tenMinutesSpeech();
        }
        //将新数据变成老数据
        this.oldDownQuestionList = list
      });
    },
    /** 查询问题接收列表 */
    getxcphList() {
      this.downQuestionLoading = true
      downlistCjls(this.queryParams).then(response => {
        this.downQuestionList = response.rows;
        // 设置提报-当前时间
        var currentDate = new Date();
        this.downQuestionList.forEach((v, i) => {
          var selectedDate = new Date(v.cjsj);
          // 计算时间差（以毫秒为单位）
          var timeDifference = currentDate.getTime() - selectedDate.getTime();
          // 将时间差转换为分钟
          var minutesDifference = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
          v.tbdqst = minutesDifference.toString() + "天"
        })
        this.total = response.total;
        this.downQuestionLoading = false
      });
      //获取问题数量和饼形图
      this.getwcsl()
      //获取条形图数据
      this.gethistogramData()
    },
    /** 查询跟踪协同问题列表 */
    getYWCJList() {
      this.ywcjQuestionLoading = true
      ywcjList(this.ywcjqueryParams).then(res => {
        this.ywcjList = res.rows;
        this.ywcjtotal = res.total;
        this.ywcjQuestionLoading = false
      });
    },
    //获取完成数量
    getwcsl() {
      getwcsl().then(response => {
        this.wtztNumber = response.rows
        this.wcsl = 0
        this.wwcsl = 0
        this.piechartData = []
        this.wtztNumber.forEach((v, i) => {
          if (v.str === '确认已完成' || v.str === '已完成') {
            this.wcsl += v.num
          } else {
            this.wwcsl += v.num
          }
          if (v.str === '提交') {
            const data = {value: v.num, name: v.str, itemStyle: {color: '#FF0000'}}
            this.piechartData.push(data)
          } else if (v.str === '接收') {
            const data = {value: v.num, name: v.str, itemStyle: {color: '#ff8c00'}}
            this.piechartData.push(data)
          } else if (v.str === '申请已完成') {
            const data = {value: v.num, name: v.str, itemStyle: {color: '#00ffd9'}}
            this.piechartData.push(data)
          } else if (v.str === '确认已完成') {
            const data = {value: v.num, name: v.str, itemStyle: {color: '#00c4ff'}}
            this.piechartData.push(data)
          } else if (v.str === '已完成') {
            const data = {value: v.num, name: v.str, itemStyle: {color: '#1dce14'}}
            this.piechartData.push(data)
          } else {
            const data = {value: v.num, name: v.str}
            this.piechartData.push(data)
          }
        })
        this.piechart()
      });
    },
    //获取条形图数据
    gethistogramData() {
      getcjksbmNumber().then(response => {
        this.histogramData = response.rows
        this.histogramCategory = []
        this.histogramValue = []
        this.histogramData.forEach((v, i) => {
          this.histogramCategory.push(v.str)
          this.histogramValue.push(v.num)
        })
        this.histogram();
      })
    },
    //刷新
    refresh() {
      this.getxcphList()
      this.getNewxcphListAllAndSpeech()
    },
    //跟踪协同问题刷新
    refreshYWCJ() {
      this.getYWCJList()
    },
    //现场配合问题双击
    jswtOrJjfa(row) {
      if (row.wtzt === "提交") {
        this.receive(row)
      } else if (row.wtzt === "接收") {
        this.solve(row)
      }
    },
    // 接收按钮
    receive(row) {
      if (row.wtzt !== '提交') {
        this.$modal.msgError("问题状态为提交状态才可以接收！");
        return;
      }
      this.selectedRow = row
      this.wtjsDialog = true
    },
    // 问题接收弹窗 是/否 按钮
    wtjsOK(whether) {
      if (whether === "是") {
        if (this.selectedRow.bjrid !== this.userInfo.userId.toString()) {
          this.$message.error("您不是被叫人，不能点“是”")
          return
        }
      }
      let selectedRow = this.selectedRow;
      if (whether === '否') {
        let jsrData = {}
        this.jsrList.forEach(item => {
          if (item.userId === this.jsrId) {
            jsrData = item
          }
        })
        selectedRow.jsr = jsrData.nickName
        selectedRow.jsrid = jsrData.userId.toString()
      }
      let list = []
      list.push(selectedRow)
      wtjsIt(list, whether).then(() => {
        this.$message.success("接收成功！");
        this.getxcphList();
        this.wtjsDialog = false
        this.notBjzrrDialog = false
      })
    },
    // 问题接收弹窗 否 按钮
    notBjzrr() {
      this.jsrList = []
      this.wtjsDialog = false
      this.notBjzrrDialog = true
      getjsrBywtlb({wtlb: this.selectedRow.wtlb, wtxl: this.selectedRow.wtxl,}).then(response => {
        this.jsrList = response.rows;
      })
    },
    // 解决方案表单重置
    jjfareset(row) {
      this.jjfaform = {
        jjfa: row.jjfa
      }
      this.resetForm("jjfaform");
    },
    // 解决方案按钮
    solve(row) {
      if (row.jsrid !== this.userInfo.userId.toString()) {
        this.$message.error("您不是接收人，不能填写解决方案")
        return
      }
      this.selectedRow = row
      this.jjfareset(row)
      this.solveDialog = true
    },
    // 解决方案确定按钮
    solveOK() {
      this.$refs["jjfaform"].validate(valid => {
        if (valid) {
          const id = this.selectedRow.lsid
          updatejjfa(id, this.jjfaform.jjfa).then(response => {
            this.$message.success("修改成功！");
            /*修改xml状态申请已完成*/
            this.getxcphList();
            this.solveDialog = false
          })
        }
      });
    },
    //任务详情按钮
    questionDetails(row) {
      this.width = '1090px'
      this.closureID = row;
      this.title = "回复/预览";
      this.open = true
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
    //责任人的申请关闭
    closeQuestion() {
      let ids = [];
      const id = {id: '', value: ''};
      id.id = this.closureID.id;
      id.value = '待关闭';
      ids.push(id);
      updateMyDoListStatus(ids).then(res => {
        if (res.code === 200) {
          this.$message.success("修改成功");
          this.getYWCJList();
        }
      })
    },
    //回复预览页面的责任人的数据
    loadzerData() {
      this.zrrList = [];
      getzerData({wtid: this.closureID.id}).then(res => {
        if (res.code === 200) {
          this.zrrList = res.rows;
        }
      })
    },
    //回复预览页面的附件预览多选框
    fujianyulan() {
      if (this.fujian) {
        this.width = '1390px';
        this.isShow = true;
        this.isShowLdps = false;
      } else {
        if (this.ldpiList.length > 0) {
          //判断是否领导批示有值，如果有值那么就展示领导批示
          this.width = '1390px';
          this.isShow = false;
          this.isShowLdps = true;
        } else {
          this.width = '1090px';
          this.isShow = false;
        }
      }
    },
    //回复按钮的执行方法
    huifuDialogMethod() {
      this.huifuTest = '';
      if (this.queryParams.radios === '已关闭') {
        this.$message.error("该问题已经关闭，不能回复数据");
      } else {
        this.huifuDialog = true;
      }
    },
    //回复按钮的确定方法
    huifuSubmit(ejhfppyj, id, type, js) {
      this.lixingfankuiDialog = false;
      let sjjh = {
        value: this.closureID.wtzt,
        hfxx: this.huifuTest,
        wtbt: this.closureID.wtbt,
        wtid: this.closureID.id,
        wtcjid: this.closureID.cjrid,
        wtcjr: this.closureID.cjr,
        wtcjbm: this.closureID.cjbm,
        wtcjks: this.closureID.cjks,
        jhzt: type,
        js: js,
        xh: '',
      }
      if (type === '领导批示') {
        saveJhjlList(sjjh).then(res => {
          if (res.code === 200) {
            this.$message.success("回复成功");
            this.huifuDialog = false;
            this.lingdaopishiDialog = false;
            this.lixingfankuiDialog = false;
            this.loadJhjlList('领导批示');
          } else {
            this.$message.error("回复失败，请查看是否服务器错误，或者重复提交");
          }
        });
        return;
      }
      if (id === null || id === '') {
        saveJhjlList(sjjh).then(res => {
          if (res.code === 200) {
            this.$message.success("回复成功");
            this.huifuDialog = false;
            this.lingdaopishiDialog = false;
            this.lixingfankuiDialog = false;
            //再此查询交互记录
            if (this.islxfk) {
              this.loadJhjlList('例行反馈');
            } else {
              this.loadJhjlList('回复');
            }
          } else {
            this.$message.error("回复失败，请查看是否服务器错误，或者重复提交");
          }
        });
      } else {
        sjjh.xh = id;
        saveJhjlList(sjjh).then(res => {
          if (res.code === 200) {
            this.$message.success("回复成功");
            this.huifuDialog = false;
            this.lingdaopishiDialog = false;
            this.lixingfankuiDialog = false;
            if (this.islxfk) {
              this.loadJhjlList('例行反馈');
            } else {
              this.loadJhjlList('回复');
            }
            //再此查询交互记录
          } else {
            this.$message.error("回复失败，请查看是否服务器错误，或者重复提交");
          }
        });
      }
    },
    deleteJhjl() {
      // 处理菜单项点击事件
      let ejhfppyj = this.itemJhjl.ejhfppyj;
      if (this.itemJhjl.hffj) {
        this.$message.error("该回复下面有附件不能删除");
        return
      }
      //先判断当前登录的用户是否管理员，如果是那么可以删除，如果不是下级有数据不能删除
      if (ejhfppyj !== null && ejhfppyj.length > 0) {
        this.$message.error("该回复下面有回复不能删除");
      } else {
        deleteJhjlByXh(this.itemJhjl.xh).then(res => {
          if (res.code === 200) {
            this.$message.success("删除成功");
            if (this.closureID.lxfk === '例行反馈') {
              this.loadJhjlList('例行反馈');
            } else {
              this.loadJhjlList('回复');
            }
          } else {
            this.$message.error("删除失败");
          }
        });
      }
    },
    //回复预览页面的申请关闭按钮
    applyForClosure(closureID) {
      let ids = [];
      const id = {id: closureID.id, value: '待关闭'};
      ids.push(id);
      this.open = false;
      this.$modal.confirm('是否确认关闭数据项？').then(function () {
        updateMyDoListStatus(ids)
      }).then(() => {
        this.$message.success("关闭成功");
        this.getYWCJList();
      }).catch(() => {
      });
    },
    //获得交互记录的数据
    loadJhjlList(jhzt, str) {
      this.queryParams.WTID = this.closureID.id;
      this.queryParams.JHZT = jhzt;
      if (jhzt === '领导批示') {
        getJhjl(this.queryParams).then(res => {
          if (res.code === 200) {
            this.ldpiList = res.rows;
            if (str !== '刷新') {
              if (this.ldpiList.length > 0) {
                this.width = '1390px'
                this.isShowLdps = true;
                this.isShow = false
              } else {
                this.width = '1090px'
                this.isShowLdps = false;
                this.isShow = false
              }
            }
          }
        });
      } else {
        getJhjl({WTID: this.closureID.id, JHZT: jhzt}).then(res => {
          if (res.code === 200) {
            this.jhsjList = res.rows;
            let xhs = [{id: this.closureID.id}]
            for (let j1 of this.jhsjList) {
              xhs.push({id: j1.xh})
              for (let j2 of j1.sjjhs) {
                xhs.push({id: j2.xh})
                for (let j3 of j2.sjjhs) {
                  xhs.push({id: j3.xh})
                  for (let j4 of j3.sjjhs) {
                    xhs.push({id: j4.xh})
                    for (let j5 of j4.sjjhs) {
                      xhs.push({id: j5.xh})
                    }
                  }
                }
              }
            }
            getFjByIds(xhs).then(res => {
              let fjs = []
              fjs = res.rows
              this.fileList = []
              this.jhjlFileList = []
              for (let fjsdata of fjs) {
                if (fjsdata.id === this.closureID.id) {
                  this.fileList.push(fjsdata)
                } else {
                  this.jhjlFileList.push(fjsdata)
                  for (let j1 of this.jhsjList) {
                    if (fjsdata.id === j1.xh) {
                      j1.hffj = true
                    }
                    for (let j2 of j1.sjjhs) {
                      if (fjsdata.id === j2.xh) {
                        j2.hffj = true
                      }
                      for (let j3 of j2.sjjhs) {
                        if (fjsdata.id === j3.xh) {
                          j3.hffj = true
                        }
                        for (let j4 of j3.sjjhs) {
                          if (fjsdata.id === j4.xh) {
                            j4.hffj = true
                          }
                          for (let j5 of j4.sjjhs) {
                            if (fjsdata.id === j5.xh) {
                              j5.hffj = true
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            })
          }
        });
      }
    },
    //回复数据的单击事件
    huifuyangshione(index) {
      this.currentDivIndex = index;
    },
    //回复页面的责任人的闭环和激活按钮
    //激活
    jihuoButton(row) {
      let data = {xh: row.xh}
      activate(data).then(res => {
        if (res.code === 200) {
          this.$message.success("责任人激活成功");
          this.loadzerData();
        } else {
          this.$message.error("责任人激活失败");
        }
      });
    },
    showContextMenu(event, item) {
      event.preventDefault(); // 阻止默认的右键菜单显示
      this.isContextMenuVisible = true;
      this.contextMenuPosition.x = event.clientX;
      this.contextMenuPosition.y = event.clientY;

      this.itemJhjl = item;
      // 点击其他地方时隐藏右键菜单
      document.addEventListener('click', this.hideContextMenu);
    },
    hideContextMenu() {
      this.isContextMenuVisible = false;
      document.removeEventListener('click', this.hideContextMenu);
    },
    uploadAttachment() {
      // 处理菜单项点击事件
    },
//判断是否使例行反馈还是回复的数据
    huifuyangshi(ejhfppyj, xh, islxfk) {
      this.huifuTest = '';
      this.huifuId = '';
      if (islxfk) {
        this.lixingfankuiDialog = true;
      } else {
        this.huifuDialog = true;
      }
      this.huifuId = xh;
      this.ejhfppyj = ejhfppyj;
    },
    //例行反馈按钮的执行方法
    lixingfankuiDialogMethod() {
      this.huifuTest = '';
      if (this.queryParams.radios === '已关闭') {
        this.$message.error("该问题已经关闭，不能回复数据");
      } else {
        this.lixingfankuiDialog = true;
      }
    },
    handlePageClick(event) {
      // 检查点击事件是否发生在小气泡上
      const isBubbleClicked = event.target.closest('.chatBox');

      if (!isBubbleClicked) {
        this.currentDivIndex = null; // 点击的是其他地方，将当前小气泡的索引设为 null，恢复原来的颜色
      }
    },
    dialogOpened() {
      this.$nextTick(() => {
        this.$refs.huifuInput.focus();
      });
    },
    dialogLDPSOpened() {
      this.$nextTick(() => {
        this.$refs.lingdaoInput.focus();
      });
    },
    dialogLXFKOpened() {
      this.$nextTick(() => {
        this.$refs.lixingfankuiInput.focus();
      });
    }
  }
};
</script>
<style>
.el-checkbox__input.is-checked + .el-checkbox__label {
  color: white;
}

/*主*/
.downcontainer {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%; /* 使用视口宽度单位 */
  height: 100%; /* 使用视口高度单位 */
  display: flex;
}

/*表格*/
.downcontainer tr {
  text-align: center;
}

.downcontainer td {
  max-width: 150px;
  height: 45px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

/*左div*/
.downcontainer .left-side {
  overflow: auto;
  float: left; /* 左侧浮动 */
  height: 100%;
  width: 500px;
  display: inline-block;
  padding: 15px;
  padding-bottom: 0;
}

/*右div*/
.downcontainer .right-side {
  width: calc(100% - 500px);
  flex: 1;
  padding: 10px;
}

/*页数签*/
.downcontainer .pagination-container {
  margin: 0 !important;
  margin-right: 50px !important;
}

/* 隐藏垂直滚动条 */
.downcontainer ::-webkit-scrollbar {
  width: 8px; /* 设置滚动条的宽度 */
  height: 8px;
}

/*提示弹窗*/
.buttonDialog .el-dialog__close {
  font-size: 30px; /* 调整按钮大小 */
}

.buttonDialog .el-dialog__headerbtn .el-dialog__close {
  color: red;
}

.buttonDialog .el-dialog {
  border-radius: 10px;
}

.buttonDialog .el-dialog:not(.is-fullscreen) {
  margin-top: 15% !important;
}

.buttonDialog .el-input__inner {
  font-size: 18px;
}

.buttonDialog .el-dialog__body {
  padding-top: 10px;
  padding-bottom: 10px;
}


.dialog .fj .el-checkbox__label {
  font-size: 16px;
}

.dialog .el-dialog__close {
  font-size: 30px; /* 调整按钮大小 */
}

.dialog .el-dialog__headerbtn .el-dialog__close {
  color: red;
}

.dialog .el-tooltip__popper {
  font-size: 18px; /* 调整字体大小为你需要的大小 */
}

.dialog .cards .el-card__body {
  padding: 0;
}

.dialog .el-dialog__body {
  padding-top: 0;
  padding-bottom: 20px;
}

.dialog .el-dialog {
  border-radius: 10px;
}

.dialog .el-aside {
  padding: 0;
}

.dialog .el-main {
  padding: 0;
}

.dialog .card .el-card__body {
  padding: 0;
}

.dialog .el-dialog__header {
  padding: 0;
}

.dialog .el-dialog__body {
  padding: 0;
}

.dialog .el-aside {
  margin-bottom: 0;
}

.dialog .el-textarea.is-disabled .el-textarea__inner {
  color: black;
  font-size: 17px;
}

.dialog .el-table .el-table__header-wrapper th, .el-table .el-table__fixed-header-wrapper th {
  color: black;
}

.dialog .el-table {
  color: black;
}

.dialog .el-aside {
  background-color: transparent;
}

.dialogRad .el-dialog__body {
  padding-top: 0;
  padding-bottom: 20px;
}

.dialog .el-checkbox__input.is-checked + .el-checkbox__label {
  color: black;
}

.dialogRad .el-checkbox__label {
  color: black;
}

.dialogRad .el-checkbox__input.is-checked + .el-checkbox__label {
  color: black;
}

.downcontainer .el-table--medium .el-table__cell {
  padding: 0 !important;
  height: 40px !important;
}
.dialogRad .el-dialog__headerbtn {
  z-index: 100;
}
</style>
<style scoped lang="scss">
.tablec ::v-deep .el-table {
  background: rgba(0, 0, 0, 0);
}

.tablec ::v-deep .el-table tr {
  background: rgba(0, 0, 0, 0);
}

.tablec ::v-deep .el-table .el-table__header-wrapper th, .el-table .el-table__fixed-header-wrapper th {
  background-color: rgba(0, 0, 0, 0);
}

.dialog .context-menu {
  position: fixed;
  background-color: white;
  padding: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.dialog .menu-item {
  padding: 5px;
  cursor: pointer;
}

.dialog .menu-item:hover {
  background-color: lightblue;
}

.dialog .green-color {
  color: green;
}

.dialog .yellow-color {
  color: yellow;
}

.dialog .red-color {
  color: red;
}

.dialog .clicked {
  background-color: #ffba00 !important;
}

.dialog .clicked::before {
  border-right-color: #ffba00 !important;
}

.dialog .app-container {
  padding-top: 10px;
}

.dialog .el-row {
  margin-bottom: 10px;

  &:last-child {
    margin-bottom: 0;
  }
}

.dialog .el-col {
  line-height: 20px;
}

.dialog .el_radios_position {
  margin-right: 10px;
}

.dialog .app-container {
  padding-top: 10px;
}

/*高度为100% */
.dialog .indexHeight {
  height: 100%;
}

.dialog .chatBox {
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

.dialog .chatBox-left::before {
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

.dialog .chatName {
  display: inline-block;
  background-color: #c0d6f1;
  border-radius: 30px;
  padding: 5px;
  font-size: 16px;
}

.dialog .chatTime {
  display: inline-block;
}

.dialog {
  position: absolute;;
  z-index: 1;
}
</style>
