<template>
  <div class="upcontainer"
       :style="{backgroundImage: 'url(' + backgroundImageUrl + ')', backgroundSize: 'cover', backgroundPosition: 'center' }">
    <div class="outer">
      <div class="left-side">
        <div style="width: 170px;position: relative;margin-left: 15px; display: inline-block" v-for="(value,index) in this.buttons">
          <img
            :src="isHoveredId === value+'-'+index ? require('@/assets/questionIcons/yesbutton.png') : require('@/assets/questionIcons/nobutton.png')"
            style="width: 160px;height: 193px"
            alt="按钮图像"
            @mouseover="handleMouseOver(value+'-'+index)"
            @mouseout="handleMouseOut"
            @click="addNew(value)"
          />
          <img
            class="overlay-image"
            :src="isHoveredId === value+'-'+index ? require('@/assets/questionIcons/'+value+'-2.png') : require('@/assets/questionIcons/'+value+'.png')"
            :style="{ transform: isHoveredId ===value+'-'+index ? 'scale(1)' : 'scale(0.9)' }"
            @mouseover="handleMouseOver(value+'-'+index)"
            @click="addNew(value)"
          />
          <span class="art-text" @click="addNew(value)"
                :style="{ color: isHoveredId ===value+'-'+index ? 'rgb(255,195,99)': 'rgb(255,255,255)',fontSize: isHoveredId ===value+'-'+index ? '19px' : '17px'}"
                @mouseover="handleMouseOver(value+'-'+index)">{{ value }} </span>
        </div>
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
            <el-button
              type="primary"
              :disabled="multiple"
              @click="cancelWtzt"
            >取消
            </el-button>
            <el-button
              type="danger"
              :disabled="multiple"
              @click="closeit"
            >关闭
            </el-button>
            <el-button
              type="danger"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['myjob:cjls:remove']"
            >删除
            </el-button>
          </div>
          <b style="color: white;font-size: 28px;">现场配合问题</b>
        </div>
        <div class="r-tworow" style="height: calc(100% - 55px)">
          <div style="max-height: calc(100% - 50px); overflow-x: auto; overflow-y: auto">
            <div style="width: auto; height: auto;">
              <table border="1"
                     style="table-layout: fixed; border-collapse: collapse; color: white;border-color: #00A9FF;min-width: 100%">
                <tr>
                  <th style="min-width: 45px">
                    <input style="width: 20px; height: 20px;" type="checkbox" v-model="selectAll"
                           @change="selectAllRows"/>
                  </th>
                  <th style="min-width: 110px">创建部门科室</th>
                  <th style="min-width: 100px;height: 45px">问题名称</th>
                  <th style="min-width: 100px;height: 45px">问题状态</th>
                  <th style="min-width: 100px;height: 45px">问题类别</th>
                  <th style="min-width: 100px;height: 45px">问题细类</th>
                  <th style="min-width: 120px">生产订单号</th>
                  <!--                  <th style="min-width: 100px">产品型号</th>-->
                  <th style="min-width: 100px">产品型号</th>
                  <th style="min-width: 100px">班产日期</th>
                  <th style="min-width: 100px">批次</th>
                  <th style="min-width: 100px">工序号</th>
                  <th style="min-width: 100px">工序名称</th>
                  <th style="min-width: 100px">问题描述</th>
                  <th style="min-width: 100px">设备型号</th>
                  <th style="min-width: 100px">是否满意</th>
                  <th style="min-width: 100px">提报人</th>
                  <th style="min-width: 110px">被叫责任科室</th>
                  <th style="min-width: 100px">被叫人</th>
                  <th style="min-width: 120px">提报-接收时间</th>
                  <th style="min-width: 120px">提报-当前时间</th>
                  <th style="min-width: 100px">接收人</th>
                  <th style="min-width: 100px">解决方案</th>
                  <th style="min-width: 100px">创建时间</th>
                  <th style="min-width: 100px">提报人ID</th>
                  <th style="min-width: 100px">被叫人ID</th>
                  <th style="min-width: 100px">接收人ID</th>
                </tr>
                <tr v-for="(v , i) in cjlsList" :key="i">
                  <td>
                    <input v-if="v.lsid != null" style="color: #00DD77; width: 20px; height: 20px;" type="checkbox"
                           v-model="selectedRows"
                           @change="handleSelectionChange(selectedRows)"
                           :value="v"/>
                  </td>
                  <el-tooltip class="item" effect="dark" :content="v.cjbmks" placement="top" :open-delay="500"
                              :disabled="v.cjbmks == null">
                    <td>{{ v.cjbmks }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.wtmc" placement="top" :open-delay="500"
                              :disabled="v.wtmc == null">
                    <td>{{ v.wtmc }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.wtzt" placement="top" :open-delay="500"
                              :disabled="v.wtzt == null">
                    <td>{{ v.wtzt }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.wtlb" placement="top" :open-delay="500"
                              :disabled="v.wtlb == null">
                    <td>{{ v.wtlb }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.wtxl" placement="top" :open-delay="500"
                              :disabled="v.wtxl == null">
                    <td>{{ v.wtxl }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.scddh" placement="top" :open-delay="500"
                              :disabled="v.scddh == null">
                    <td>{{ v.scddh }}</td>
                  </el-tooltip>
                  <!--                  <el-tooltip class="item" effect="dark" :content="v.cpxh" placement="top" :open-delay="500"
                                                :disabled="v.cpxh == null">
                                      <td>{{ v.cpxh }}</td>
                                    </el-tooltip>-->
                  <el-tooltip class="item" effect="dark" :content="v.jh" placement="top" :open-delay="500"
                              :disabled="v.jh == null">
                    <td>{{ v.jh }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.bcrq" placement="top" :open-delay="500"
                              :disabled="v.bcrq == null">
                    <td>{{ v.bcrq }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.pc" placement="top" :open-delay="500"
                              :disabled="v.pc == null">
                    <td>{{ v.pc }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.gxh" placement="top" :open-delay="500"
                              :disabled="v.gxh == null">
                    <td>{{ v.gxh }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.gxmc" placement="top" :open-delay="500"
                              :disabled="v.gxmc == null">
                    <td>{{ v.gxmc }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.wtms" placement="top" :open-delay="500"
                              :disabled="v.wtms == null">
                    <td>{{ v.wtms }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.sbxh" placement="top" :open-delay="500"
                              :disabled="v.sbxh == null">
                    <td>{{ v.sbxh }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.sfmy" placement="top" :open-delay="500"
                              :disabled="v.sfmy == null">
                    <td>{{ v.sfmy }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.tbr" placement="top" :open-delay="500"
                              :disabled="v.tbr == null">
                    <td>{{ v.tbr }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.bjzrks" placement="top" :open-delay="500"
                              :disabled="v.bjzrks == null">
                    <td>{{ v.bjzrks }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.bjzrr" placement="top" :open-delay="500"
                              :disabled="v.bjzrr == null">
                    <td>{{ v.bjzrr }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.tbjssj" placement="top" :open-delay="500"
                              :disabled="v.tbjssj == null">
                    <td>{{ v.tbjssj }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.tbdqst" placement="top" :open-delay="500"
                              :disabled="v.tbdqst == null">
                    <td>{{ v.tbdqst }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.jsr" placement="top" :open-delay="500"
                              :disabled="v.jsr == null">
                    <td>{{ v.jsr }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.jjfa" placement="top" :open-delay="500"
                              :disabled="v.jjfa == null">
                    <td>{{ v.jjfa }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.cjsj" placement="top" :open-delay="500"
                              :disabled="v.cjsj == null">
                    <td>{{ v.cjsj }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.tbrid" placement="top" :open-delay="500"
                              :disabled="v.tbrid == null">
                    <td>{{ v.tbrid }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.bjrid" placement="top" :open-delay="500"
                              :disabled="v.bjrid == null">
                    <td>{{ v.bjrid }}</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" :content="v.jsrid" placement="top" :open-delay="500"
                              :disabled="v.jsrid == null">
                    <td>{{ v.jsrid }}</td>
                  </el-tooltip>
                </tr>
              </table>
            </div>
          </div>
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
    </div>
    <!-- 添加提出问题对话框 -->
    <el-dialog :visible.sync="open" width=60% class="addDialog" append-to-body>
      <div class="dia">
        <el-row>
          <el-col :span="10" style="padding: 20px">
            <b
              style="font-size: 23px;margin: 0;text-align: center;color: black;display: inline-block;width: 100%">创建问题</b>
            <el-form ref="form" :model="form" :rules="rules" label-width="120px" class="underline-input">
              <el-form-item style="font-size: 20px!important;" label="问题名称：" prop="wtmc">
                <el-input style="font-size: 20px" v-model="form.wtmc" placeholder="请输入问题名称"/>
              </el-form-item>
              <!--              <el-form-item label="产品型号：" prop="cpxh">
                              <el-input v-model="form.cpxh" placeholder="请输入产品型号"/>
                            </el-form-item>-->
              <el-form-item label="生产订单号：" prop="scddh">
                <el-input v-model="form.scddh" placeholder="请输入生产订单号"/>
              </el-form-item>
              <el-form-item label="产品型号：" prop="jh">
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
                <el-input v-model="form.sbxh" placeholder="请输入工序名称"/>
              </el-form-item>
              <el-form-item label="问题类别：" prop="wtlb">
                <el-input style="font-size: 18px;color: black" v-model="form.wtlb" :disabled="true"/>
              </el-form-item>
              <el-form-item label="问题细类：" prop="wtxl">
                <el-select style="width: 100%" v-model="form.wtxl" clearable placeholder="请选择问题细类">
                  <el-option
                    v-for="item in wtxlList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
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
          </el-col>
          <el-col :span="14" style="padding: 20px">
            <b style="font-size: 23px;margin: 0;text-align: center;color: black;display: inline-block;width: 100%">历史答案查询数据</b>
            <el-card class="box-card" style="margin: 10px 10px 0 0;height: 570px">
              <table border="1"
                     style="table-layout: fixed; border-collapse: collapse;width: 100%;font-size: 18px">
                <tr>
                  <th style="width: 60px; height: 45px">序号</th>
                  <th style="width: 30%">问题描述</th>
                  <th>问题详情</th>
                  <th style="width: 150px">操作</th>
                </tr>
                <tr style="text-align: center">
                  <el-tooltip class="item" effect="dark" content="序号" placement="top" :open-delay="500"
                              :disabled="'序号' != null">
                    <td>序号</td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" content="" placement="top"
                              :open-delay="500" :disabled="'' != null">
                    <td></td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" content="" placement="top"
                              :open-delay="500" :disabled="'问题详情' != null">
                    <td></td>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" content="" placement="top"
                              :open-delay="500" :disabled="'回复预览页面' != null">
                    <td></td>
                  </el-tooltip>
                </tr>
              </table>
            </el-card>
          </el-col>
        </el-row>
        <div slot="footer" class="dialog-footer" style="height: 50px;margin-top: 20px">
          <el-button type="success" style="float: right;margin-right: 20px" @click="submitForm">确 定</el-button>
          <el-button type="primary" style="float: right;margin-right: 20px" @click="cancel">取 消</el-button>
          <el-button type="warning" style="float: right;margin-right: 20px" @click="selectHistory">历史答案查询</el-button>
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
    <!--删除按钮弹窗-->
    <el-dialog
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
  </div>
</template>

<script>
import {listCjls, addCjls, delCjls, changewtzt} from "@/api/question/upQuestion";
import {getwtxlMethod} from "@/api/question/question";

export default {
  name: "Cjls",
  data() {
    return {
      backgroundImageUrl: require('@/assets/questionIcons/背景.png'),
      // 选中数组
      ids: [],
      //选中数据
      selectedRows: [],
      // 是否全选
      selectAll: false,
      // 非多个禁用
      multiple: true,
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
      // 问题类别列表
      wtxlList: [],
      // 表单校验
      rules: {
        wtms: [
          {required: true, message: '请填问题描述', trigger: 'blur'}
        ],
        wtlb: [
          {required: true, message: '请填问题类别', trigger: 'blur'}
        ],
        wtxl: [
          {required: true, message: '请填问题细类', trigger: 'blur'}
        ],
        wtmc: [
          {required: true, message: '请填问题名称', trigger: 'blur'}
        ],
      },
      //鼠标是否在按钮内
      isHoveredId: "0",
      //按钮数据
      buttons: ['工艺问题', '调度问题', '设备问题','生产问题', '待确认', '待确认'],
      // 取消按钮弹窗
      cancelWtztDialog: false,
      // 关闭按钮弹窗
      closeitDialog: false,
      // 删除按钮弹窗
      handleDeleteDialog: false,
    };
  },
  created() {
    this.getList();
  },

  methods: {

    handleChange(checkedNodes) {
      console.log(checkedNodes);
      if (checkedNodes.children && checkedNodes.children.length > 0) {
        // 提示用户重新选择
        this.$message.error(`请重新选择用户`);
      }


      this.traverseData(this.toData);
    },
    traverseData(toData) {
      const selectedIds = [];
      const stack = [...toData];

      while (stack.length > 0) {
        const node = stack.pop();

        if (!node.children || node.children.length === 0) {
          selectedIds.push(node.id);
        } else {
          for (const child of node.children) {
            stack.push(child);
          }
        }
      }
      console.log(selectedIds);
    },
    /** 方法 start */
    // 获取树形数据
    getPermissionTree() {
      let param = {
        roleId: '',
        sysId: ''
      }
      permissionTree(param).then(res => {
        if (res.code === 0) {
          console.log(res.data)
          this.fromData = res.data.children
        } else {
          this.$message({
            message: res.msg,
            type: 'warning'
          })
        }
      })
    },
    // 切换模式 现有树形穿梭框模式transfer 和通讯录模式addressList
    changeMode() {
      if (this.mode === 'transfer') {
        this.mode = 'addressList'
      } else {
        this.mode = 'transfer'
      }
    },
    // 监听穿梭框组件添加
    add(fromData, toData, obj) {
      // 树形穿梭框模式transfer时，返回参数为左侧树移动后数据、右侧树移动后数据、移动的{keys,nodes,halfKeys,halfNodes}对象
      // 通讯录模式addressList时，返回参数为右侧收件人列表、右侧抄送人列表、右侧密送人列表
      console.log('fromData:', fromData)
      console.log('toData:', toData)
      console.log('obj:', obj)

      // 递归遍历树形结构
      function getRoleId(obj) {
        for (var i in obj) {
          // 输出每个节点id
          console.log(obj[i].id)
          if (obj[i].children) {
            // 如果存在子树,递归调用函数
            getRoleId(obj[i].children)
          }
        }
      }

      getRoleId(toData)
    },
    // 监听穿梭框组件移除
    remove(fromData, toData, obj) {
      // 树形穿梭框模式transfer时，返回参数为左侧树移动后数据、右侧树移动后数据、移动的{keys,nodes,halfKeys,halfNodes}对象
      // 通讯录模式addressList时，返回参数为右侧收件人列表、右侧抄送人列表、右侧密送人列表
      console.log('fromData:', fromData)
      console.log('toData:', toData)
      console.log('obj:', obj)
    },


    // 查询提出问题列表
    getList() {
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
        if (this.cjlsList.length < this.queryParams.pageSize) {
          for (let i = this.cjlsList.length; i < this.queryParams.pageSize; i++) {
            const item = {}
            this.cjlsList.push(item)
          }
        }
      });
    },
    // 弹框内取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮
    cancelWtzt() {
      this.cancelWtztDialog = true
    },
    // 取消弹窗内确定按钮
    cancelWtztOK() {
      const LSIDs = this.ids
      changewtzt(LSIDs, '取消').then(() => {
        this.getList();
        this.$modal.msgSuccess("取消成功");
        this.selectedRows = []
        this.ids = this.selectedRows.map(item => item.lsid)
        this.multiple = this.selectedRows.length === 0
        this.cancelWtztDialog = false
      })
    },
    // 关闭按钮
    closeit() {
      this.closeitDialog = true
    },
    // 关闭确认按钮
    closeitOK(str) {
      const LSIDs = this.ids
      changewtzt(LSIDs, str).then(response => {
        this.$modal.msgSuccess(str + "!");
        this.getList();
        this.selectedRows = []
        this.ids = this.selectedRows.map(item => item.lsid)
        this.multiple = this.selectedRows.length === 0
        this.closeitDialog = false
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
        wtlb: null,
        wtxl: null,
      };
      this.resetForm("form");
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.lsid)
      this.selectedRows = selection.map(item => item)
      this.multiple = selection.length === 0
    },
    //全选
    selectAllRows() {
      if (this.selectAll) {
        // 全选
        this.selectedRows = this.cjlsList.map(v => v);
        this.ids = this.cjlsList.map(item => item.lsid)
        this.multiple = this.selectedRows.length === 0
      } else {
        // 取消全选
        this.selectedRows = [];
        this.ids = [];
        this.multiple = this.selectedRows.length === 0
      }
    },
    // 新增按钮操作
    addNew(value) {
      if (value === '待确认') {
        return;
      }
      this.reset();
      this.form.wtlb = value
      this.wtxlMethod()
      this.open = true;
    },
    //问题细类的获得方法
    wtxlMethod() {
      this.wtxlList = [];
      getwtxlMethod({wtlb: this.form.wtlb}).then(res => {
        if (res.code === 200) {
          let data = res.rows;
          for (let i = 0; i < data.length; i++) {
            this.wtxlList.push({label: data[i], value: data[i]})
          }
        }
      })
    },
    // 提交按钮
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addCjls(this.form).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    // 删除按钮操作
    handleDelete() {
      this.handleDeleteDialog = true
    },
    // 删除确定按钮
    handleDeleteOK() {
      const LSIDs = this.ids
      delCjls(LSIDs).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
        this.selectedRows = []
        this.ids = this.selectedRows.map(item => item.lsid)
        this.multiple = this.selectedRows.length === 0
        this.handleDeleteDialog = false
      })
    },
    // 查询历史答案按钮
    selectHistory() {

    },
    //鼠标移入
    handleMouseOver(str) {
      this.isHoveredId = str;
    },
    //鼠标移出
    handleMouseOut() {
      this.isHoveredId = '0';
    }
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
  padding: 15px;
}

/*右div*/
.upcontainer .right-side {
  width: calc(100% - 400px);
  flex: 1;
  padding: 20px;
  padding-top: 5px;
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

/* 隐藏垂直滚动条 */
.outer ::-webkit-scrollbar {
  width: 8px; /* 设置滚动条的宽度 */
  height: 8px;
}

.upcontainer ::-webkit-scrollbar-track {
  background-color: transparent; /* 设置滚动条轨道的背景色为透明 */
}

.upcontainer ::-webkit-scrollbar-thumb {
  background-color: rgba(255, 255, 255, 0.3); /* 设置滚动条的颜色和透明度 */
  border-radius: 4px; /* 设置滚动条的边框半径 */
}

.upcontainer ::-webkit-scrollbar-thumb:hover {
  background-color: rgba(255, 255, 255, 0.5); /* 设置鼠标悬停时滚动条的颜色和透明度 */
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
  padding: 0;
}

.addDialog .el-dialog__header {
  padding-bottom: 20px;
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
  margin-top: 20% !important;
}

.upcontainer .el-pagination__total, .el-pagination__jump {
  color: white;
}

</style>
