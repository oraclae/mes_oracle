<template>
  <div class="upcontainer">
    <div class="outer">
      <div class="left-side">
        <div style="position: relative;width: 100%;margin-bottom: 15px">
          <el-button
            type="primary"
            icon="el-icon-arrow-left"
            :disabled="showWtlb"
            @click="showWtlb = true"
          >返回
          </el-button>
          <transition name="fade" mode="out-in">
            <b style="float: right;margin-top: 3px;color: #00afff" v-if="!showWtlb">{{ form.wtlb }}</b>
          </transition>
        </div>
        <transition name="fade" mode="out-in">
          <div v-if="showWtlb" key="wtlb" style="width: 370px;">
            <!--问题类别-->
            <div style="display: inline-block;vertical-align: middle;"
                 v-for="(value,index) in this.wtlbXlList" :key="value.wtlb+'-'+index">
              <div class="addWtlbButtonDiv" :style="{marginLeft:(index+2)%5===0? '60px':0}">
                <button class="addWtlbButton" @click="addNew(value.wtlb)">{{ value.wtlb }}</button>
              </div>
            </div>
          </div>
          <!--问题细类-->
          <div v-else key="wtxl" style="width: 370px;">
            <div style="display: inline-block;vertical-align: middle;"
                 v-for="(value,index) in this.wtlbXlList[wtlbIndex].wtxl" :key="value+'-'+index">
              <div class="addWtlbButtonDiv" :style="{marginLeft:(index+2)%5===0? '60px':0}">
                <button class="addWtlbButton" @click="wtxlClick(value)">{{ value }}</button>
              </div>
            </div>
          </div>
        </transition>
      </div>
      <div class="right-side">
        <div class="r-onerow" style="height: 45px;margin-bottom: 10px">
          <div style="width: 500px;display: inline-block">
            <el-button
              type="primary"
              icon="el-icon-loading"
              @click="getList"
            >刷新
            </el-button>
<!--            <el-button
              type="primary"
              :disabled="canquxiao"
              @click="cancelWtzt"
            >取消
            </el-button>
            <el-button
              type="danger"
              :disabled="canguanbi"
              @click="closeit"
            >关闭
            </el-button>-->
            <el-button
              type="danger"
              :disabled="multiple"
              @click="handleDelete"
            >删除
            </el-button>
            <el-button type="primary" @click="angl">按钮管理</el-button>
          </div>
          <b style="color: #4f4f4f;font-size: 28px;">现场配合问题</b>
        </div>
        <el-table border v-loading="loading" height="calc(100vh - 200px)" :data="cjlsList"
                  @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="操作" width="120px" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                v-if="scope.row.wtzt === '提交'"
                size="mini"
                type="text"
                icon="el-icon-remove-outline"
                @click="cancelWtzt(scope.row)"
              >取消
              </el-button>
              <el-button
                v-if="scope.row.wtzt === '接收' || scope.row.wtzt === '申请已关闭'"
                size="mini"
                type="text"
                icon="el-icon-circle-close"
                @click="closeit(scope.row)"
              >关闭
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
          <el-table-column type="index" label="序号" align="center" width="50px"/>
          <el-table-column show-overflow-tooltip label="创建部门科室" header-align="center" width="150px" prop="cjbmks"/>
          <el-table-column show-overflow-tooltip label="问题名称" header-align="center" width="100px" prop="wtmc"/>
          <el-table-column show-overflow-tooltip label="问题状态" header-align="center" width="100px" prop="wtzt"/>
          <el-table-column show-overflow-tooltip label="问题类别" header-align="center" width="100px" prop="wtlb"/>
          <el-table-column show-overflow-tooltip label="问题细类" header-align="center" width="100px" prop="wtxl"/>
          <el-table-column show-overflow-tooltip label="生产订单号" header-align="center" width="120px" prop="scddh"/>
          <el-table-column show-overflow-tooltip label="产品型号" header-align="center" width="100px" prop="cpxh"/>
          <el-table-column show-overflow-tooltip label="件号" header-align="center" width="100px" prop="jh"/>
          <el-table-column show-overflow-tooltip label="班产日期" header-align="center" width="120px" prop="bcrq"/>
          <el-table-column show-overflow-tooltip label="批次" header-align="center" width="100px" prop="pc"/>
          <el-table-column show-overflow-tooltip label="工序号" header-align="center" width="100px" prop="gxh"/>
          <el-table-column show-overflow-tooltip label="问题描述" header-align="center" width="100px" prop="wtms"/>
          <el-table-column show-overflow-tooltip label="设备型号" header-align="center" width="100px" prop="sbxh"/>
          <el-table-column show-overflow-tooltip label="是否满意" header-align="center" width="100px" prop="sfmy"/>
          <el-table-column show-overflow-tooltip label="提报人" header-align="center" width="100px" prop="tbr"/>
          <el-table-column show-overflow-tooltip label="被叫责任科室" header-align="center" width="120px" prop="bjzrks"/>
          <el-table-column show-overflow-tooltip label="被叫人" header-align="center" width="100px" prop="bjzrr"/>
          <el-table-column show-overflow-tooltip label="接收时间" header-align="center" width="100px" prop="tbjssj"/>
          <el-table-column show-overflow-tooltip label="当前时间" header-align="center" width="100px" prop="tbdqst"/>
          <el-table-column show-overflow-tooltip label="接收人" header-align="center" width="100px" prop="jsr"/>
          <el-table-column show-overflow-tooltip label="解决方案" header-align="center" width="100px" prop="jjfa"/>
          <el-table-column show-overflow-tooltip label="创建时间" header-align="center" width="100px" prop="cjsj"/>
          <el-table-column show-overflow-tooltip label="提报人ID" header-align="center" width="100px" prop="tbrid"/>
          <el-table-column show-overflow-tooltip label="被叫人ID" header-align="center" width="100px" prop="bjrid"/>
          <el-table-column show-overflow-tooltip label="接收人ID" header-align="center" width="100px" prop="jsrid"/>
        </el-table>
        <div style="height: 50px">
          <pagination
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
            style="background-color: transparent;margin-right: 50px"
          />
        </div>
      </div>
    </div>
    <!--选择问题细类弹框-->
    <el-dialog title="请选择问题细类" :close-on-click-modal="false" :visible.sync="selectWtxlDialog" width="600px"
               class="selectWtxlDialog"
               append-to-body>
      <el-table border v-loading="wtxlLoading" v-if="wtxlLoading" height="618px" :data="this.wtlbXlList[wtlbIndex].wtxl"
                @row-click="wtxlClick">
        <el-table-column label-class-name="wtxlLable" label="序号" align="center" type="index" width="80"/>
        <el-table-column label-class-name="wtxlLable" show-overflow-tooltip label="问题细类">
          <template slot-scope="scope">
            <span>{{ scope.row }}</span>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <!-- 添加提出问题对话框 -->
    <el-dialog @close="addNewClose" :close-on-click-modal="false" :visible.sync="open" width="600px"
               class="addDialog" append-to-body>
      <div class="dia">
        <b
          style="font-size: 23px;margin: 0;text-align: center;color: black;display: inline-block;width: 100%">创建问题</b>
        <el-form ref="form" :model="form" :rules="rules" label-width="120px" class="underline-input">
          <el-form-item style="font-size: 20px!important;" label="问题名称：" prop="wtmc">
            <el-input style="font-size: 20px" v-model="form.wtmc" placeholder="请输入问题名称"/>
          </el-form-item>
          <el-form-item label="生产订单号：" prop="scddh">
            <el-input v-model="form.scddh" placeholder="请输入生产订单号"/>
          </el-form-item>
          <el-form-item label="产品型号：" prop="cpxh">
            <el-input v-model="form.cpxh" placeholder="请输入产品型号"/>
          </el-form-item>
          <el-form-item label="件号：" prop="jh">
            <el-input v-model="form.jh" placeholder="请输入件号"/>
          </el-form-item>
          <el-form-item label="班产日期：" prop="bcrq">
            <el-input v-model="form.bcrq" placeholder="请输入班产日期"/>
          </el-form-item>
          <el-form-item label="批次：" prop="pc">
            <el-input v-model="form.pc" placeholder="请输入批次"/>
          </el-form-item>
          <el-form-item label="工序号：" prop="gxh">
            <el-input v-model="form.gxh" placeholder="请输入工序号"/>
          </el-form-item>
          <el-form-item label="设备型号：" prop="sbxh">
            <el-input v-model="form.sbxh" placeholder="请输入设备型号"/>
          </el-form-item>
          <el-form-item label="问题类别：" prop="wtlb">
            <el-input style="font-size: 18px;color: black;pointer-events: none" v-model="form.wtlb"/>
          </el-form-item>
          <el-form-item label="问题细类：" prop="wtxl">
            <div @click="selectWtxl">
              <el-input style="font-size: 18px;color: black;pointer-events: none" v-model="form.wtxl"/>
            </div>
          </el-form-item>
          <el-form-item label="问题描述：" prop="wtms">
            <el-input
              style="width: 90%; font-size: 18px"
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 4}"
              placeholder="请输入问题描述"
              v-model="form.wtms">
            </el-input>

          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer" style="height: 50px;margin-top: 20px">
          <el-button type="success" style="float: right;margin-right: 20px" @click="selectHistory">确 定</el-button>
          <el-button type="primary" style="float: right;margin-right: 20px" @click="cancel">取 消</el-button>
        </div>
      </div>
    </el-dialog>
    <!--取消按钮弹窗-->
    <el-dialog
      top="25vh"
      class="butDialog"
      title
      center
      :visible.sync="cancelWtztDialog" width="500px">
      <span slot="title" style="font-size: 30px;">系统提示</span>
      <div style="font-size: 20px;text-align: center;width: 100%">是否确认取消该数据项?</div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="cancelWtztOK">确 定</el-button>
        <el-button @click="cancelWtztDialog = false" style="margin-left: 50px">取 消</el-button>
      </span>
    </el-dialog>
    <!--关闭按钮弹窗-->
    <el-dialog
      class="butDialog"
      title
      center
      :visible.sync="closeitDialog" width="500px">
      <span slot="title" style="font-size: 30px;">系统提示</span>
      <div style="font-size: 20px;text-align: center;width: 100%">问题解决方案是否满意?</div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="closeitOK('确认已完成')">满 意</el-button>
        <el-button type="primary" @click="closeitOK('已完成')" style="margin-left: 20px">不满意</el-button>
        <el-button @click="closeitDialog = false" style="margin-left: 20px">取 消</el-button>
      </span>
    </el-dialog>
    <!--关闭按钮弹窗-->
    <el-dialog
      class="butDialog"
      title
      center
      v-if="closeJSDialog"
      :visible.sync="closeJSDialog" width="500px">
      <span slot="title" style="font-size: 30px;">系统提示</span>
      <div style="font-size: 20px;text-align: center;width: 100%">问题解决方案未填写，是否确认关闭问题?</div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="closeitOK('已完成')" style="margin-left: 20px">确 定</el-button>
        <el-button @click="closeJSDialog = false" style="margin-left: 60px">取 消</el-button>
      </span>
    </el-dialog>
    <!--删除按钮弹窗-->
    <el-dialog
      @close="deleteRowId = null"
      class="butDialog"
      title
      center
      :visible.sync="handleDeleteDialog" width="500px">
      <span slot="title" style="font-size: 30px;">系统提示</span>
      <div style="font-size: 20px;text-align: center;width: 100%">是否确认删除该数据项?</div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleDeleteOK()">确 定</el-button>
        <el-button @click="handleDeleteDialog = false" style="margin-left: 30px">取 消</el-button>
      </span>
    </el-dialog>
    <!--查询历史答案-->
    <el-dialog class="addDialog" :close-on-click-modal="false" title="启用问题处理库" width="1200px"
               :visible.sync="lishidaanDialog">
      <zdhf ref="lishidaanDialog" @xiangxixinxi="xiangxixinxi" @dialogCreatedQuestion="submitForm"
            v-if="lishidaanDialog" :form="form"></zdhf>
    </el-dialog>
    <!--详细信息弹窗-->
    <el-dialog class="zdhfDialog" :close-on-click-modal="false" @close="shifoumanyi" title="详细信息"
               :visible.sync="xiangxiDialog">
      <xiangxixinxi v-if="xiangxiDialog" :jhsj-list="jhsjList" :form="form"></xiangxixinxi>
    </el-dialog>
    <!--是否满意-->
    <el-dialog
      :close-on-click-modal="false"
      class="zdhfButDialog"
      title
      center
      :visible.sync="shifoumanyiDialog" width="500px">
      <span slot="title" style="font-size: 30px;">系统提示</span>
      <div style="font-size: 20px;text-align: center;width: 100%">问题解决方案是否满意?</div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="closeitOK1">满 意</el-button>
        <el-button type="primary" @click="shifoumanyiDialog=false" style="margin-left: 20px">不满意</el-button>
        <el-button @click="shifoumanyiDialog=false" style="margin-left: 20px">取消</el-button>
      </span>
    </el-dialog>
    <!--按钮管理弹窗-->
    <el-dialog class="anglDialog" width="800px" :close-on-click-modal="false" @close="getUpButtons" title="按钮管理"
               :visible.sync="anglDialog">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="anglAdd"
          >新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="anglmultiple"
            @click="anglDelete"
          >删除
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-edit"
            size="mini"
            :disabled="anglsingle"
            @click="anglUpdate"
          >修改
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-refresh-right"
            size="mini"
            @click="getUpButtons"
          >刷新
          </el-button>
        </el-col>
      </el-row>
      <el-table height="500px" border v-loading="anglLoading" :data="buttons" @selection-change="anglSelectionChange">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="序号" align="center" type="index" width="50"/>
        <el-table-column show-overflow-tooltip label="按钮名" align="center" prop="butname"/>
        <el-table-column show-overflow-tooltip width="50px" label="排序" align="center" prop="px"/>
        <el-table-column show-overflow-tooltip label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="anglUpdate(scope.row)"
            >修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="anglDelete(scope.row)"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <!--按钮管理添加/修改弹窗-->
    <el-dialog class="butDialog" :title="anglTitle" :visible.sync="anglOpen" width="500px" append-to-body>
      <el-form ref="anglForm" :model="anglForm" :rules="anglRules" label-width="100px">
        <el-form-item label="按钮名" prop="butname">
          <el-input v-model="anglForm.butname" placeholder="请输入按钮名"/>
        </el-form-item>
        <el-form-item label="排序" prop="px">
          <el-input v-model.number="anglForm.px" placeholder="请输入排序"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="anglSubmitForm">确 定</el-button>
        <el-button @click="anglCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getUpButtons,
  getUpButton,
  delangl,
  updateUpButton,
  addUpButton,
  listCjls,
  addCjls,
  delCjls,
  changewtzt,
  updateDaccToRd,
  getWtlbXlList
} from "@/api/question/upQuestion.js";
import {getJhjlByWtid, getwtxlMethod} from "@/api/question/question";
import zdhf from "@/views/question/myquestion/znwdDialog"
import treeTransfer from 'el-tree-transfer';
import xiangxixinxi from "@/views/question/myquestion/xiangxixinxi";
import Button from "@/views/question/soundModule/Button";

export default {
  name: "Cjls",
  components: {
    Button,
    treeTransfer, zdhf, xiangxixinxi
  },
  data() {
    return {
      wtlbXlList: [],//问题类别细类列表
      wtlbIndex: 0,//选中问题类别下标
      showWtlb: true,//是否显示问题类别按钮
      // 问题类别列表
      wtxlList: [],
      //问题细类列表遮罩层
      wtxlLoading: false,
      //选择问题细类弹窗
      selectWtxlDialog: false,
      //防止多次点击
      isone: true,
      //按钮
      buttons: [],
      //按钮管理弹窗
      anglDialog: false,
      //按钮管理选中数据的序号
      anglSelectXhs: [],
      anglLoading: false,
      // 按钮管理非多个禁用
      anglmultiple: true,
      // 按钮管理非单个禁用
      anglsingle: true,
      // 按钮管理新增修改弹窗
      anglOpen: false,
      // 按钮管理新增修改弹窗表头
      anglTitle: '',
      // 按钮管理form
      anglForm: {},
      //按钮管理表单验证
      anglRules: {
        px: [
          {required: true, message: '排序不能为空'},
          {type: 'number', message: '排序必须为数字值'}
        ],
        imgname: [
          {required: true, message: '图片名不能为空'}
        ],
      },


      shifoumanyiDialog: false,//是否满意的弹出框
      xiangxiDialog: false,//详细信息是否弹出框
      jhsjList: [],//交互数据的数据集合
      lishidaanDialog: false,
      backgroundImageUrl: require('@/assets/questionIcons/背景.png'),
      loading: false,//遮罩层
      // 选中数组
      ids: [],
      //选中数据
      selectedRows: [],
      selectedRow: {},
      deleteRowId: "",//删除的对象id
      // 是否全选
      selectAll: false,
      // 非多个禁用
      multiple: true,
      canquxiao: true,//能否取消
      canguanbi: true,//能否关闭
      // 非单个禁用
      single: true,
      // 总条数
      total: 0,
      // 提出问题表格数据
      cjlsList: [],
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},

      // 表单校验
      rules: {
        /* wtms: [
           {required: true, message: '请填问题描述', trigger: 'blur'}
         ],*/
        wtlb: [
          {required: true, message: '请填问题类别', trigger: 'blur'}
        ],
        wtxl: [
          {required: true, message: '请填问题细类', trigger: 'blur'}
        ],
        /*wtmc: [
          {required: true, message: '请填问题名称', trigger: 'blur'}
        ],*/
      },
      //鼠标是否在按钮内
      isHoveredId: "0",
      // 取消按钮弹窗
      cancelWtztDialog: false,
      // 关闭按钮弹窗
      closeitDialog: false,
      // 关闭按钮弹窗
      closeJSDialog: false,
      // 删除按钮弹窗
      handleDeleteDialog: false,
    };
  },
  created() {
    this.getList();
    this.getWtlbXlList()
  },

  methods: {
    //新增弹窗关闭方法
    addNewClose() {
      this.isone = true
      this.reset()
    },
    selectWtxl() {
      this.selectWtxlDialog = true
    },
    //获取问题类别细类列表
    getWtlbXlList() {
      getWtlbXlList().then(response => {
        this.wtlbXlList = response.rows
      })
    },

    //获取按钮列表
    getUpButtons() {
      this.anglLoading = true
      getUpButtons().then(response => {
        this.buttons = response.rows
        this.anglLoading = false
      })
    },
    //按钮管理按钮
    angl() {
      this.anglLoading = true
      getUpButtons().then(response => {
        this.buttons = response.rows
        this.anglLoading = false
      })
      this.anglDialog = true
    },
    //按钮管理新增按钮
    anglAdd() {
      this.anglreset();
      this.anglOpen = true;
      this.anglTitle = "添加字典";
    },
    //按钮管理删除按钮
    anglDelete(row) {
      const xh = row.xh || this.anglSelectXhs;
      this.$confirm('是否确认删除数据项?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delangl(xh).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.getUpButtons()
        })
      }).catch(() => {
      });
    },
    //按钮管理修改按钮
    anglUpdate(row) {
      this.anglreset();
      const xh = row.xh || this.anglSelectXhs
      getUpButton(xh).then(response => {
        this.anglForm = response.data;
        this.anglOpen = true;
        this.anglTitle = "修改按钮";
      });
    },
    // 按钮管理多选框选中数据
    anglSelectionChange(selection) {
      this.anglSelectXhs = selection.map(item => item.xh)
      this.anglmultiple = selection.length === 0
      this.anglsingle = selection.length !== 1
    },
    //按钮管理重置表单
    anglreset() {
      this.anglForm = {
        XH: null,
        PX: 1,
        butname: null,
        imgname: null,
        cjsj: null
      }
    },
    //按钮管理提交按钮
    anglSubmitForm() {
      this.$refs["anglForm"].validate(valid => {
        if (valid) {
          if (this.anglForm.xh != null) {
            updateUpButton(this.anglForm).then(() => {
              this.$message.success("修改成功");
              this.anglOpen = false;
              this.getUpButtons();
            });
          } else {
            addUpButton(this.anglForm).then(() => {
              this.$message.success("新增成功");
              this.anglOpen = false;
              this.getUpButtons();
            });
          }
        }
      });
    },
    //按钮管理取消按钮
    anglCancel() {
      this.anglOpen = false
      this.anglreset()
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
    //满意的按钮执行方法
    closeitOK1() {
      updateDaccToRd({daxxid: this.daccid}).then(res => {
        if (res.code === 200) {
          this.$message.success("已满意！");
          if (this.lishidaanDialog) {
            this.$refs.lishidaanDialog.getList()
          }
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
    // 查询提出问题列表
    getList() {
      this.loading = true
      listCjls(this.queryParams).then(response => {
        this.cjlsList = response.rows;
        this.total = response.total;
        // 设置提报-当前时间
        var currentDate = new Date();
        this.cjlsList.forEach((v, i) => {
          var selectedDate = new Date(v.cjsj);
          // 计算时间差（以毫秒为单位）
          var timeDifference = currentDate.getTime() - selectedDate.getTime();
          // 将时间差转换为分钟
          var minutesDifference = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
          v.tbdqst = minutesDifference.toString() + "天"
        })
        //补充空值
        /*if (this.cjlsList.length < this.queryParams.pageSize) {
          for (let i = this.cjlsList.length; i < this.queryParams.pageSize; i++) {
            const item = {}
            this.cjlsList.push(item)
          }
        }*/
        this.loading = false
      });
    },
    // 弹框内取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮
    cancelWtzt(row) {
      this.selectedRow = row
      this.cancelWtztDialog = true
    },
    // 取消弹窗内确定按钮
    cancelWtztOK() {
      let list = []
      list.push(this.selectedRow)
      changewtzt(list, '取消').then(() => {
        this.getList();
        this.$message.success("取消成功");
        this.cancelWtztDialog = false
      })
    },
    // 关闭按钮
    closeit() {
      this.selectedRow = row
      this.closeitDialog = true;
    },
    // 关闭确认按钮
    closeitOK(str) {
      let list = []
      list.push(this.selectedRow)
      changewtzt(list, str).then(response => {
        this.$message.success(str + "!");
        this.getList();
        this.closeitDialog = false
        this.closeJSDialog = false
      })
    },
    // 表单重置
    reset() {
      this.form = {
        lsid: null,
        zs: null,
        cjwtid: null,
        wtjs: null,
        sbxh: null,
        jh: null,
        cpxh: null,
        gxh: null,
        gxmc: null,
        bcrq: null,
        tbr: null,
        bjzrks: null,
        bjzrr: null,
        wtms: null,
        tbjssj: null,
        tbdqst: null,
        jsr: null,
        jjfa: null,
        wtzt: null,
        scddh: null,
        pc: null,
        sfmy: null,
        tbrid: null,
        bjrid: null,
        jsrid: null,
        cjbmks: null,
        wtmc: null,
        wtxl: null,
        wtlb: this.form.wtlb
      };
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.lsid)
      this.selectedRows = selection.map(item => item)
      this.single = selection.length !== 1
      this.multiple = !selection.length

      this.canquxiao = true
      this.canguanbi = true
      if (selection.length === 1) {
        if (selection[0].wtzt === "提交") {
          this.canquxiao = false
        }
        if (selection[0].wtzt === "接收" || selection[0].wtzt === "申请已关闭") {
          this.canguanbi = false
        }
      }
    },
    //新建按钮打开问题细类选择
    addNew(value) {
      if (value === '待确认' || value === '未确认') {
        return;
      }
      this.form.wtlb = value
      this.wtlbXlList.forEach((v, i) => {
        if (v.wtlb === value) {
          this.wtlbIndex = i
        }
      })
      this.showWtlb = false
    },
    //问题细类的获得方法
    wtxlMethod() {
      this.wtxlList = [];
      getwtxlMethod({wtlb: this.form.wtlb}).then(res => {
        if (res.code === 200) {
          let list = []
          list = res.rows;
          for (let wtxl of list) {
            this.wtxlList.push({name: wtxl})
          }
          this.wtxlLoading = false

        }
      })
    },
    //问题细类单击触发
    wtxlClick(wtxl) {
      this.form.wtxl = wtxl
      this.open = true;
      this.selectWtxlDialog = false
    },
    // 提交按钮
    submitForm() {
      if (!this.isone) {
        return;
      }
      this.lishidaanDialog = false
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.isone = false
          addCjls(this.form).then(response => {
            this.$message.success("新增成功");
            this.open = false;
            this.getList();
            this.isone = true
          }).catch(response => {
            this.isone = true
          })
        }
      });
    },
    // 删除按钮操作
    handleDelete(row) {
      this.deleteRowId = row.lsid
      this.handleDeleteDialog = true
    },
    // 删除确定按钮
    handleDeleteOK() {
      const LSIDs = this.deleteRowId || this.ids
      delCjls(LSIDs).then(() => {
        this.getList();
        this.$message.success("删除成功");
        this.handleDeleteDialog = false
      })
    },
    // 查询历史答案按钮
    selectHistory() {
      this.lishidaanDialog = true;
    },
  }
};
</script>
<style>


/*主*/
.upcontainer {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%; /* 使用视口宽度单位 */
  height: 100%; /* 使用视口高度单位 */
}

.upcontainer .outer {
  position: absolute;
  top: 0;
  left: 0;
  display: flex;
  width: 100%;
  height: 100%;
}

/*左div*/
.upcontainer .left-side {
  float: left; /* 左侧浮动 */
  height: 98%;
  overflow: auto;
  width: 400px;
  display: inline-block;
  padding: 15px 15px 15px 15px;
}

/*右div*/
.upcontainer .right-side {
  width: calc(100% - 400px);
  flex: 1;
  padding: 5px 20px 20px 1px;
}

/*问题类别问题细类按钮样式*/
.addWtlbButtonDiv {
  width: 123px;
  height: 100px;
  display: flex;
  justify-content: center;
  position: relative;
}

.addWtlbButton {
  font-size: 16px;
  width: 110px;
  height: 120px;
  background: #3498db;
  position: relative;
  border: none;
  clip-path: polygon(50% 0%, 100% 25%, 100% 75%, 50% 100%, 0% 75%, 0% 25%);
  color: white;
  transition-duration: 0.1s;
}

/*按钮鼠标悬浮效果*/
.addWtlbButton:hover {
  transform: scale(1.1);
}

/*问题类别问题细类过度动画*/
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s;
}

.fade-enter,
.fade-leave-to /* .fade-leave-active 在Vue 2.1.8以及之前版本中可用 */
{
  opacity: 0;
}

/*表格*/
.upcontainer tr {
  text-align: center;
}

/*页数签*/
.upcontainer .pagination-container {
  margin: 0 !important;
  margin-right: 50px !important;
}

.outer td {
  max-width: 150px;
  height: 45px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.dia td {
  height: 40px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.anglDialog .el-dialog:not(.is-fullscreen) {
  margin-top: 10% !important;
}

.anglDialog .el-dialog {
  border-radius: 10px;
}

.anglDialog .el-dialog__close {
  font-size: 30px;
}

.anglDialog .el-dialog__headerbtn .el-dialog__close {
  color: red;
}

.zdhfButDialog .el-dialog {
  border-radius: 10px;
}

.zdhfButDialog .el-dialog__close {
  font-size: 30px;
}

.zdhfButDialog .el-dialog__headerbtn .el-dialog__close {
  color: red;
}

.zdhfButDialog .el-dialog:not(.is-fullscreen) {
  margin-top: 15% !important;
}

.zdhfDialog .el-dialog:not(.is-fullscreen) {
  margin-top: 10% !important;
}

.zdhfDialog .el-dialog__body {
  padding: 0 10px;
}

.zdhfDialog .el-dialog {
  border-radius: 10px;
}

.zdhfDialog .el-dialog__close {
  font-size: 30px;
}

.zdhfDialog .el-dialog__headerbtn {
  color: red;
}

/*按钮中的字*/
.art-text {
  position: absolute;
  top: 120px; /* 调整艺术文字的位置 */
  left: 40px;
  width: 80px;
  text-align: center;
  color: rgb(131, 131, 131); /* 调整艺术文字的颜色 */
  text-shadow: 1px 1px 2px rgba(50, 39, 0, 0.5); /* 调整艺术文字的阴影效果 */
  cursor: default
}

.overlay-image {
  position: absolute;
  top: 28px;
  left: 38px;
  z-index: 1;
  width: 85px
}

.underline-input .el-input__inner {
  border: none; /* 移除默认边框 */
  border-bottom: 1px solid #000; /* 添加下划线 */
  border-radius: 0; /* 可选：去除下划线两端的圆角 */
  padding: 0; /* 可选：去除内部填充 */
  width: 90%;
}

.addDialog .el-form-item {
  margin: 20px;
}

.addDialog .el-dialog__body {
  padding: 0 20px;
}

.addDialog .el-dialog__header {
  padding-bottom: 10px;
}

.addDialog .el-col {
  padding: 0 !important;
}

.addDialog .el-dialog {
  border-radius: 30px;
}

.addDialog .el-dialog__close {
  font-size: 30px; /* 调整按钮大小 */
}

.addDialog .el-dialog__headerbtn .el-dialog__close {
  color: red;
}

.addDialog input {
  font-size: 18px;
}

.addDialog .el-input.is-disabled .el-input__inner {
  color: black;
}

.addDialog .el-form-item__label {
  font-size: 18px;
  color: black;
}

.addDialog .el-input__inner {
  color: black;
}

.selectDialog .el-dialog {
  border-radius: 30px;
}

.butDialog .el-dialog {
  border-radius: 30px;
}

.butDialog .el-dialog__close {

  font-size: 30px; /* 调整按钮大小 */
}

.butDialog .el-dialog__headerbtn .el-dialog__close {
  color: red;
}

.butDialog .el-dialog:not(.is-fullscreen) {
  margin-top: 15% !important;
}

.selectDialog .el-dialog__close {
  font-size: 30px; /* 调整按钮大小 */
}


.selectDialog .el-dialog__headerbtn .el-dialog__close {
  color: red;
}

.selectDialog .el-dialog__body {
  padding: 0 20px;
}

.selectWtxlDialog .wtxlLable {
  height: 25px;
  font-size: 18px
}
</style>
