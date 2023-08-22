<template>
  <div class="zhinengwendamain" @click="toInput" style="background-color: #e6ebf5">
    <div class="xiaoxiqu" ref="xiaoxiqu">

      <div style="overflow: hidden;font-size: 20px;margin-bottom: 20px" v-for="(value,index) in texts" :key="index">
        <div class="robot-bubble" style="float: left" v-if="value.user === '机器人'">
          <img :src="require('@/assets/questionIcons/机器人.gif')" width="40px"
               style="border-radius: 80px;margin-right: 20px">
          <div class="bubble-content" style="vertical-align: top"><span v-html="value.text"></span></div>
        </div>
        <div class="user-bubble" style="float: right" v-if="value.user === '用户'">
          <div>
            <div class="bubble-content-user"><span>{{ value.text }}</span></div>
            <img :src="require('@/assets/questionIcons/用户.jpg')" width="40px"
                 style="border-radius: 80px;margin-left: 20px;vertical-align: top">
          </div>
        </div>
      </div>
    </div>
    <el-input ref="input_ref" class="input-box" v-model="inputValue" @input="handleInput"></el-input>
    <el-dialog class="dialogRad" title="创建问题" width="700px" :close-on-click-modal="false"
               @close="createQuestionCloseButten"
               :visible.sync="dialogTableVisible">
      <div style="overflow: auto">
        <div style="margin-bottom: 45px">
          <div style="float: left">
            <label style="color: red;margin-right: 5px;">*</label>
            <label>问题名称:</label>
            <el-input placeholder="请输入问题名称" style="width: 200px;margin-left: 24px;height: 15px" v-model="form.wtmc"/>
          </div>

          <div style="float: right;margin-right: 25px">
            <label style="color: red;margin-right: 5px;">*</label>
            <label>主责任人:</label>
            <!--            <el-button @click="chuansuokuang=true">请选择</el-button>-->
            <div style="display: inline-block" @click="treeVisual = true, selectTitle = '选择责任人'">
              <el-tooltip effect="dark" :content="zrrstr" placement="top" :open-delay="500"
                          :disabled="zrrstr == null || zrrstr === ''">
                <el-input placeholder="请选择责任人" style="width: 200px; margin-left: 27px;pointer-events: none"
                          :value="zrrstr"/>
              </el-tooltip>
            </div>
          </div>
        </div>
        <div style="margin-bottom: 90px">
          <div style="float: left">
            <label style="margin-left: 10px">零组件号:</label>
            <el-input placeholder="请输入产品型号" style="width: 200px;margin-left: 24px" v-model="form.cpxh"/>
          </div>
          <div style="float: right;margin-right: 25px">
            <label style="color: red;margin-right: 5px;">*</label>
            <label>责任部门:</label>
            <el-tooltip effect="dark" :content="zrbmstr" placement="top" :open-delay="500"
                        :disabled="zrbmstr == null || zrbmstr === ''">
              <el-input placeholder="请输入责任部门" style="width: 200px; margin-left: 27px;pointer-events: none"
                        :value="zrbmstr"/>
            </el-tooltip>
          </div>
        </div>
        <div style="margin-bottom: 135px">
          <div style="float: left">
            <label style="margin-left: 10px">生产订单号:</label>
            <el-input placeholder="请输入生产订单号" style="width: 200px;margin-left: 10px" v-model="form.scddh"/>
          </div>
          <div style="float: right;margin-right: 25px">
            <label>阅知人:</label>
            <div style="display: inline-block" @click="treeVisual = true, selectTitle = '选择阅知人'">
              <el-tooltip effect="dark" :content="yzrstr" placement="top" :open-delay="500"
                          :disabled="yzrstr == null || yzrstr === ''">
                <el-input placeholder="请选择阅知人" style="width: 200px; margin-left: 27px;pointer-events: none"
                          :value="yzrstr"/>
              </el-tooltip>
            </div>
          </div>
        </div>
        <div style="margin-bottom: 180px">
          <div style="float: left">
            <label style="margin-left: 10px">问题来源:</label>
            <el-select style="width: 200px;margin-left: 24px" v-model="form.wtly" clearable placeholder="请选择问题来源">
              <el-option
                v-for="item in wtlyList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </div>
          <div style="float: right;margin-right: 67px;margin-top: 8px">
            <el-checkbox v-model="form.xcphwt" :disabled="isXcphwt" @change="xcphwtMethod()">现场配合问题</el-checkbox>
            <el-checkbox v-model="form.gzxt" @change="gzxtwtMethod" :disabled="isGzxtwt">跟踪协同问题</el-checkbox>
          </div>
        </div>
        <div style="margin-bottom: 225px">
          <div style="float: left">
            <label style="margin-left: 10px">问题类别:</label>
            <el-select style="width: 200px;margin-left: 24px" v-model="form.wtlb" @change="wtlbChange" clearable
                       placeholder="请选择问题类别">
              <el-option
                v-for="item in wtlbList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </div>
          <div style="float: right;margin-top: 8px">
            <div style="display: inline-block;margin-right: 20px">
              <el-checkbox :disabled="isLdps" v-model="form.ldps">领导批示</el-checkbox>
            </div>
            <div style="display: inline-block;margin-right: 30px">
              <el-checkbox :disabled="isLxfk" v-model="form.lxfk" @change="lxfkMethod">例行反馈</el-checkbox>
            </div>
            <div style="display: inline-block;margin-right: 23px">
              <el-checkbox :disabled="isWtsj" v-model="form.wtsj" @change="cancellationTime">问题升级</el-checkbox>
            </div>
          </div>
        </div>
        <div style="margin-bottom: 270px">
          <div style="float: left">
            <label style="margin-left: 10px">问题细类:</label>
            <el-select style="width: 200px;margin-left: 24px" v-model="form.wtxl" clearable placeholder="请选择问题细类">
              <el-option
                v-for="item in wtxlList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </div>
          <div style="float: right">
            <label style="color: red;margin-right: 5px;">*</label>
            <label>期望解决时间:</label>
            <el-date-picker
              v-model="form.xwjjsj"
              type="datetime"
              placeholder="选择期望解决时间"
              style="width: 200px;margin-left: 24px"
              :disabled="canceTime"
              format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </div>
        </div>
        <div style="margin-bottom: 315px">
          <div style="float: left">
            <label style="margin-left: 10px">批 次:</label>
            <el-input placeholder="请输入批次" style="width: 200px;margin-left: 48px;height: 15px" v-model="form.pc"/>
          </div>
          <div style="float: right;margin-right: 25px">
            <label>紧急程度:</label>
            <el-select style="width: 200px;margin-left: 27px" v-model="form.zycd" clearable placeholder="请选择紧急程度">
              <el-option
                v-for="item in jjcdList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </div>
        </div>
        <div style="margin-bottom: 360px">
          <div style="float: left">
            <label style="margin-left: 10px">设 备:</label>
            <el-input placeholder="请输入设备" style="width: 200px;margin-left: 48px;height: 15px" v-model="form.sb"/>
          </div>
          <div style="float: right;margin-right: 27px">
            <label>影响程度:</label>
            <el-input placeholder="请输入影响程度" style="width: 200px;margin-left: 24px;height: 15px" v-model="form.yxcd"/>
          </div>
        </div>
        <div style="margin-bottom: 405px">
          <div style="float: left">
            <label style="margin-left: 10px">工序号:</label>
            <el-input placeholder="请输入工序号" style="width: 200px;margin-left: 38px;height: 15px" v-model="form.gxh"/>
          </div>

        </div>
        <div style="margin-bottom: 450px">
          <div style="float: left">
            <label style="margin-left: 10px">件 号:</label>
            <el-input placeholder="请输入件号" style="width: 200px;margin-left: 48px;height: 15px" v-model="form.jh"/>
          </div>

        </div>
        <div>
          <div style="float: left">
            <label style="margin-right: 5px;color: red">*</label>
            <label>问题描述:</label>
            <el-input
              size="small"
              :autosize="{ minRows: 4, maxRows: 40}"
              type="textarea"
              placeholder="详细描述信息"
              v-model="form.wtms"
              style="width: 660px"
            >
            </el-input>
          </div>
        </div>
      </div>
      <div style="height: 10px;margin-top: 15px;margin-bottom: 5px">
        <div style="float: right">
          <el-button type="danger" size="mini" @click="lishidaanchaxun">启用问题处理库</el-button>
          <el-button type="primary" size="mini" @click="createQuestionCloseButten">取 消</el-button>
          <el-button size="mini" style="background-color: #a8ff00" @click="saveDataDialog">确定并提交</el-button>
        </div>
      </div>
    </el-dialog>
    <el-dialog class="dialogRad" :close-on-click-modal="false" :title="selectTitle" width="1200px"
               :visible.sync="treeVisual">
      <deptTreeSelect :selectZrrList="selectTitle==='选择责任人'?selectZrrList:selectYzrList"
                      :text="selectTitle"
                      @selectDialogCancel="treeVisual=false"
                      v-if="treeVisual"
                      @selectDialogSubmit="zrrSubmit"
      >
      </deptTreeSelect>
    </el-dialog>
    <el-dialog class="dialogRad" :close-on-click-modal="false" title="启用问题处理库" width="1200px"
               :visible.sync="lishidaanDialog">
      <zdhf ref="cxda" @xiangxixinxi="xiangxixinxi" v-if="lishidaanDialog" :form="form"></zdhf>
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
  createQuestion, getJhjlByWtid,
  getjjcdMethod,
  getwtbhMethod,
  getwtlbMethod,
  getwtlyMethod,
  getwtxlMethod
} from "@/api/question/question";
import {selectDataByNotId} from "@/api/question/zhiNengWenDa";
import {addCjls, getUpButtons, updateDaccToRd} from "@/api/question/upQuestion";
import deptTreeSelect from "@/views/question/myquestion/deptTreeSelect"
import zdhf from "@/views/question/WTGL_ZDHF/index";
import xiangxixinxi from "@/views/question/myquestion/xiangxixinxi";

export default {
  components: {
    deptTreeSelect, zdhf, xiangxixinxi
  },
  data() {
    return {
      dialogTableVisible: false,//创建问题弹窗
      form: {
        wtmc: '',   //问题名称
        cpxh: '',   //产品型号
        scddh: '',  //生产订单号
        wtly: '内部',   //问题来源
        wtlb: '',   //问题类别
        wtxl: '',   //问题细类
        zycd: '',   //紧急程度
        yxcd: '',   //影响程度
        wtms: '',   //问题描述
        zre: '',    //主责任人
        zrbm: '',   //责任部门
        wtsj: false,   //问题升级
        yzr: '',    //阅知人
        xcphwt: false, //现场配合问题
        gzxt: false,   //跟踪协同
        ldps: '',   //领导批示
        xwjjsj: '', //期望解决时间
        yjsj: '',   //问题预警时间
        wtbh: '',   //问题编号
        lxfk: '',    //是否例行反馈
        pc: '',      //批次
        gxh: '',     //工序号
        sb: '',      //设备
        qwfksj: '',  //期望反馈时间
      },
      lishidaanDialog: false,//启用问题处理库的是否弹出框
      selectYzrList: [],//选中的阅知人集合
      selectZrrList: [],//选中的责任人集合
      zrrstr: '',//创建任务弹窗的责任人
      yzrstr: '',//创建任务弹窗的阅知人
      zrbmstr: '',//创建任务弹窗的责任部门
      selectTitle: '',//选择责任人或阅知人弹出框的标题
      treeVisual: false,//弹出选择责任人和阅知人的弹窗

      isQwfksj: true,//期望解决时间的是否可以写数据
      canceTime: false,//希望解决时间是否可以写数据
      isXcphwt: true,//判断现场配合是否可以写数据
      isLdps: true,//判断领导批示能否写如数据
      isWtsj: true,//判断问题升级能否写数据
      isLxfk: true,//判断是否问题反馈
      isGzxtwt: false,//判断跟踪协同能否写
      wtlyList: [],//问题来源的数组
      wtlbList: [],//问题类别的数据
      wtxlList: [],//问题细类的数据
      jjcdList: [],//紧急程度的数据
      gongkai: '公开',//单选框公开数据
      neibu: '',//内部数据
      ids: [],
      xcphwtList: [],//现场配合问题
      isone: true,

      shifoumanyiDialog: false,//是否满意的弹出框
      xiangxiDialog: false,//详细信息是否弹出框


      wtlbList2: [],
      wtxlList2: [],
      inputValue: '',
      queryParams: {
        wtms: null,
        wtmc: null,
        cpxh: null,
        jh: null,
        gxh: null,
        wtlb: null,
        wtxl: null,
        ids: []
      },
      jb: '提出问题',
      timeoutId: null,
      manyiTime: null,
      texts: [
        {user: '机器人', text: '请说出 “提出问题” 开始提问'},
      ]
    };
  },
  mounted() {
    this.toInput()
    this.getwtlb()
    this.scrollToBottom()
    this.getxcphwtList()
  },
  //监听消息区变量，更新了则滚动条拉到最下面
  watch: {
    texts: {
      handler(newTexts, oldTexts) {
        // 这个函数将在texts发生变化时触发
        // newTexts 是texts的新值，oldTexts 是texts的旧值
        setTimeout(() => {
          this.scrollToBottom(); // 每当texts发生变化时，调用滚动到底部的方法
        }, 1);

      },
      deep: true, // 深度监测，可监测对象或数组的变化
    },
  },
  methods: {
    //聚焦到输入框
    toInput() {
      if (!this.dialogTableVisible) {
        this.$refs.input_ref.focus();
      }
    },
    //问题类别的获得方法
    getwtlb() {
      this.wtlbList2 = [];
      getwtlbMethod().then(res => {
        if (res.code === 200) {
          let wtlbs = res.rows
          for (let wtlb of wtlbs) {
            const i = wtlb.lastIndexOf("问题")
            if (i >= 0) {
              wtlb = wtlb.substring(0, i);
            }
          }
          this.wtlbList2 = wtlbs
        }
      })
    },
    //停止输入后触发方法
    handleInput() {
      // 清除之前设置的timeout
      if (this.timeoutId) {
        clearTimeout(this.timeoutId);
      }
      if (this.manyiTime) {
        clearTimeout(this.manyiTime);
      }
      // 停止输入**时间后执行
      this.timeoutId = setTimeout(() => {
        this.doSomethingAfterTwoSeconds();
        // 停止输入**时间后执行
        if (this.jb !== '提出问题') {
          this.manyiTime = setTimeout(() => {
            if (this.jb !== '提出问题') {
              this.out('长时间未操作，已自动退出当前问题，可输入 “提出问题” 开始提问。')
            }
          }, 2 * 60 * 1000);
        }

      }, 1.5 * 1000);
    },
    //退出当前提问
    out(str) {
      this.robotInputXiaoXi(str || '已退出当前提问，请输入 “提出问题” 重新开始提问。')
      this.jb = '提出问题'
      this.inputValue = ''
      this.queryParams = {
        wtms: null,
        wtmc: null,
        cpxh: null,
        jh: null,
        gxh: null,
        wtlb: null,
        wtxl: null,
        ids: []
      }
      this.createQuestionShuaxin()
    },
    //机器人输入
    robotInputXiaoXi(text) {
      this.$refs.input_ref.blur()
      setTimeout(() => {
        this.texts.push({user: '机器人', text: text})
        setTimeout(() => {
          this.$refs.input_ref.focus()
        }, 500);
      }, 500);
    },
    //停止输入后触发的方法
    doSomethingAfterTwoSeconds() {
      if (this.inputValue.includes('退出')) {
        this.texts.push({user: '用户', text: this.inputValue});
        this.out()
        return
      }
      if (this.inputValue.includes('创建问题')) {
        this.texts.push({user: '用户', text: this.inputValue});
        this.inputValue = ''
        setTimeout(() => {
          clearTimeout(this.manyiTime);
          this.createQuestion()
        }, 500);
        return
      }
      //开始
      if (this.jb === '提出问题') {
        if (this.inputValue.includes('提出问题') || this.inputValue.includes("问问题")) {
          this.texts.push({user: '用户', text: this.inputValue});
          this.jb = "类别";
          let wtlbBrAll = '';
          for (let wtlb of this.wtlbList2) {
            const wtlbBr = wtlb + '<br/>'
            wtlbBrAll = wtlbBrAll + wtlbBr
          }
          this.robotInputXiaoXi('查询到的问题类型如下，请选择');
          setTimeout(() => {
            this.robotInputXiaoXi(wtlbBrAll)
          }, 500);
        }
      }
      //输入完类别
      else if (this.jb === '类别') {
        this.texts.push({user: '用户', text: this.inputValue});
        let i = 0;
        for (let wtlb of this.wtlbList2) {
          if (this.inputValue.includes(wtlb)) {
            i = 1
            this.queryParams.wtlb = wtlb
            this.form.wtlb = wtlb
            break
          }
        }
        if (i === 1) {
          this.jb = '细类'
          this.$refs.input_ref.blur()
          getwtxlMethod({wtlb: this.queryParams.wtlb}).then(res => {
            if (res.code === 200) {
              this.wtxlList2 = res.rows;
              let wtxlBrAll = ''
              for (let i = 0; i < this.wtxlList2.length; i++) {
                const wtxlBr = this.wtxlList2[i] + '<br/>'
                wtxlBrAll = wtxlBrAll + wtxlBr
              }
              this.robotInputXiaoXi('查询到的问题细类如下，请选择')
              setTimeout(() => {
                this.robotInputXiaoXi(wtxlBrAll)
              }, 500);
            }
          })
        } else {
          let wtlbBrAll = ''
          for (let wtlb of this.wtlbList2) {
            const wtlbBr = wtlb + '<br/>&nbsp;&nbsp;'
            wtlbBrAll = wtlbBrAll + wtlbBr
          }
          this.robotInputXiaoXi('没有您要找的类别，请从下列类别中选择：<br/>&nbsp;&nbsp;' + wtlbBrAll)
        }
      }
      //输入完细类
      else if (this.jb === '细类') {
        this.texts.push({user: '用户', text: this.inputValue});
        let i = 0;
        for (let wtxl of this.wtxlList2) {
          if (this.inputValue.includes(wtxl) || this.inputValue.includes('新建质量问题')) {
            i = 1
            this.queryParams.wtxl = wtxl
            this.form.wtxl = wtxl
            break
          }
        }
        if (i === 1) {
          this.jb = '满意'
          this.$refs.input_ref.blur()
          setTimeout(() => {
            this.texts.push({user: '机器人', text: '正在搜索答案。。。'})
            this.getAnswer()
          }, 500);
        } else {
          let wtxlBrAll = ''
          for (let i = 0; i < this.wtxlList2.length; i++) {
            const wtxlBr = this.wtxlList2[i] + '<br/>&nbsp;&nbsp;'
            wtxlBrAll = wtxlBrAll + wtxlBr
          }
          this.robotInputXiaoXi('没有您要找的细类，请从下列细类中选择：<br/>&nbsp;&nbsp;' + wtxlBrAll)
        }
      }
      //查询完答案
      else if (this.jb === '满意') {
        let not = [
          '不满意',
          '不要',
        ]
        let i = 0;
        for (let str of not) {
          if (this.inputValue.includes(str)) {
            i = 1
            break
          }
        }
        if (i === 1 || (this.inputValue.includes('不是') && this.inputValue.includes('要'))) {
          this.$refs.input_ref.blur()
          setTimeout(() => {
            this.texts.push({user: '机器人', text: '不满意当前答案，正在搜索其他答案。。。'})
            this.getAnswer()
          }, 500);
        } else if (this.inputValue.includes('满意')) {
          this.jb = '提出问题'
          this.out('满意当前答案，已退出当前问题，您可以输入 “提出问题” 重新提问。')
        } else {
          this.robotInputXiaoXi('无法识别您输入的文字，请问您对上条答案是否满意？')
        }
      }
      //未识别到指令不执行操作清空输入框
      this.inputValue = ''
    },
    //根据用户提供信息查询答案
    getAnswer() {
      selectDataByNotId(this.queryParams).then(response => {
        this.$refs.input_ref.blur()
        setTimeout(() => {
          if (response.data != null) {
            this.texts.push({user: '机器人', text: response.data.daxx});
            this.texts.push({user: '机器人', text: '请问答案是否满意'})
            this.queryParams.ids.push(response.data.daxxid)
          } else {
            let wtxlBrAll = ''
            this.jb = '细类'
            for (let i = 0; i < this.wtxlList2.length; i++) {
              const wtxlBr = this.wtxlList2[i] + '<br/>'
              wtxlBrAll = wtxlBrAll + wtxlBr
            }
            this.robotInputXiaoXi('很抱歉没有查询到您想要的答案，您可以输入“退出”来退出当前提问或在以下细类中重新选择提问：')
            setTimeout(() => {
              this.robotInputXiaoXi(wtxlBrAll)
            }, 500);
            wtxlBrAll
          }
          setTimeout(() => {
            this.$refs.input_ref.focus()
          }, 500);
        }, 500);
      })
    },
    //滚动条到最下面
    scrollToBottom() {
      const container = this.$refs.xiaoxiqu;
      container.scrollTop = container.scrollHeight;
    },
    //创建问题按钮的方法
    createQuestion() {

      this.dialogTableVisible = true;
      //获得问题编号
      this.wtbhMethod();
      //获得问题来源
      this.wtlyMethod();
      //获得问题类别
      this.wtlbMethod();
      //获得紧急程度
      this.jjcdMethod();
      this.isXcphwt = true;
      if (this.form.wtlb != null && this.form.wtlb !== '') {
        this.wtlbChange()
      }
    },
    //刷新创建问题的数据情况
    createQuestionShuaxin() {
      this.isQwfksj = true;
      this.isWtsj = true;
      this.isLxfk = true;
      this.isLdps = true;
      this.isXcphwt = false;
      this.isGzxtwt = false;
      this.form = {
        wtmc: '',     //问题名称
        cpxh: '',   //产品型号
        scddh: '',  //生产订单号
        wtly: '内部',   //问题来源
        wtlb: '',   //问题类别
        wtxl: '',   //问题细类
        zycd: '',   //紧急程度
        yxcd: '',   //影响程度
        wtms: '',   //问题描述
        zre: '', //主责任人
        zrbm: '',//责任部门
        yzr: '',    //阅知人
        xcphwt: false, //现场配合问题
        gzxt: false,   //跟踪协同
        ldps: '',   //领导批示
        xwjjsj: '',   //期望解决时间
        yjsj: '',   //问题预警时间
        wtbh: '',   //问题编号
        lxfk: '',   //例行反馈
        qwfksj: '',   //期望反馈时间
      }
    },
    //问题编号获得方法
    wtbhMethod() {
      getwtbhMethod().then(res => {
        if (res.code === 200) {
          this.form.wtbh = res.msg;
        }
      })
    },
    //问题来源的获得方法
    wtlyMethod() {
      this.wtlyList = [];
      getwtlyMethod().then(res => {
        if (res.code === 200) {
          let data = res.rows;
          for (let i = 0; i < data.length; i++) {
            this.wtlyList.push({label: data[i], value: data[i]})
          }
        }
      })
    },
    //问题类别的获得方法
    wtlbMethod() {
      this.wtlbList = [];
      getwtlbMethod().then(res => {
        if (res.code === 200) {
          let data = res.rows;
          for (let i = 0; i < data.length; i++) {
            this.wtlbList.push({label: data[i], value: data[i]})
          }
        }
      })
    },
    //紧急程度的获得方法
    jjcdMethod() {
      this.jjcdList = [];
      getjjcdMethod().then(res => {
        if (res.code === 200) {
          let data = res.rows;
          for (let i = 0; i < data.length; i++) {
            this.jjcdList.push({label: data[i], value: data[i]})
          }
        }
      })
    },

    //创建问题弹窗的取消按钮
    createQuestionCloseButten() {
      this.selectZrrList = []
      this.selectYzrList = []
      this.zrrstr = ''
      this.yzrstr = ''
      this.zrbmstr = ''
      this.dialogTableVisible = false
    },
    //现场配合问题的多选框的改变触发的方法
    xcphwtMethod() {
      if (this.form.xcphwt === true) {
        this.form.gzxt = false
        this.isLdps = true;
        this.isWtsj = true;
        this.isLxfk = true;
        this.isGzxtwt = true;
      } else {
        this.isGzxtwt = false;
      }
    },
//跟踪协同的多选框数据改变触发的方法
    gzxtwtMethod() {
      if (this.form.gzxt === true) {
        this.isLdps = false;
        this.isLxfk = false;
        this.isWtsj = false;
        this.isXcphwt = true;
        this.form.xcphwt = false
      } else {
        this.form.ldps = false;
        this.form.xcphwt = false;
        this.form.lxfk = false;
        this.form.wtsj = false;
        this.isQwfksj = true;
        this.isLdps = true;
        this.isLxfk = true;
        this.isWtsj = true;
        this.isXcphwt = true;
        if (this.form.wtlb != null && this.form.wtlb !== '') {
          for (let string of this.xcphwtList) {
            if (this.form.wtlb.indexOf(string) !== -1) {
              this.isXcphwt = false;
            }
          }
        }
      }
    },
    //问题类别改变后执行的方法
    wtlbChange() {
      this.wtxlMethod();
      this.isXcphwt = true;
      if (this.form.wtlb != null && this.form.wtlb !== '') {
        for (let string of this.xcphwtList) {
          if (this.form.wtlb.indexOf(string) !== -1) {
            this.isXcphwt = false;
          }
        }
      }
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
    //例行反馈的数据改变触发的方法
    lxfkMethod() {
      if (this.form.lxfk === true) {
        this.isQwfksj = false;
        this.canceTime = false;
        this.isWtsj = true;
        this.form.wtsj = false;
      } else {
        this.isQwfksj = true;
        this.isWtsj = false;
      }
    },
    //判断问题升级，如果问题升级为true，那么希望解决时间变灰
    cancellationTime() {
      if (this.form.wtsj === true) {
        this.canceTime = true;
      } else {
        this.canceTime = false;
      }
    },
    //弹出框的确定并提交方法的执行方法
    saveDataDialog() {
      if (!this.isone) {
        return;
      }
      if (this.form.wtms == null || this.form.wtms === '') {
        this.$message.error("请输入问题描述的数据");
        return;
      }
      if (this.form.wtmc == null || this.form.wtmc === '') {
        this.$message.error("请输入问题名称数据");
        return;
      }
      if (this.form.xcphwt === '' && this.form.gzxt === '' || this.form.xcphwt === false && this.form.gzxt === false) {
        this.$message.error("请选择跟踪协同问题或者现场配合问题");
        return;
      }
      if (this.form.xcphwt) {
        let data = {
          wtmc: this.form.wtmc,
          cpxh: this.form.cpxh,
          jh: this.form.jh,
          scddh: this.form.scddh,
          wtms: this.form.wtms,
          pc: this.form.pc,
          sbxh: this.form.sb,
          gxh: this.form.gxh,
          wtlb: this.form.wtlb,
          wtxl: this.form.wtxl
        };
        this.isone = false
        addCjls(data).then(res => {
          this.$message.success(res.msg);
          this.dialogTableVisible = false;
          this.isone = true
          this.out('创建问题成功，退出当前提问，输入提出问题可重新提问')
        });
        return;
      }
      if (this.form.lxfk === true) {
        this.form.lxfk = '例行反馈';
      } else {
        this.form.lxfk = '业务交互';
      }
      if (!this.form.wtsj) {
        if (this.form.xwjjsj == null || this.form.xwjjsj === '') {
          this.$message.error("没有选择期望解决时间");
          return;
        } else {
          let date = this.form.xwjjsj;
          const year = date.getFullYear();
          const month = String(date.getMonth() + 1).padStart(2, '0');
          const day = String(date.getDate()).padStart(2, '0');
          const hours = String(date.getHours()).padStart(2, '0');
          const minutes = String(date.getMinutes()).padStart(2, '0');
          const seconds = String(date.getSeconds()).padStart(2, '0');
          this.form.xwjjsj = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        }
      }
      let selectZrrArray = []
      let yzridList = []
      let yzrbmList = []
      let yzrList = []
      let yzrbmidList = []
      this.selectZrrList.forEach(item => {
        const zrrVo = {
          zrrid: item.userId,
          zrrzt: '未处理',
          zrbm: item.dept.deptName,
          zrr: item.nickName,
          zrbmid: item.deptId,
          sfzzrr: item.sfzzrr
        }
        selectZrrArray.push(zrrVo)
      })
      this.selectYzrList.forEach(item => {
        yzridList.push(item.userId);
        yzrbmList.push(item.dept.deptName)
        yzrList.push(item.nickName)
        yzrbmidList.push(item.deptId)
      })
      this.form.zrrvolist = selectZrrArray;
      this.form.yzrid = yzridList.join(',')
      this.form.yzrbm = yzrbmList.join(',')
      this.form.yzr = yzrList.join(',')
      this.form.yzrbmid = yzrbmidList.join(',')
      ////////////////////////////////////////////////////////
      this.form.cjbm = '未得到的创建部门'//this.userInfo.deptName
      ////////////////////////////////////////////////////////
      this.isone = false
      createQuestion(this.form).then(res => {
        if (res.code === 200) {
          this.out('创建问题成功，退出当前提问，输入提出问题可重新提问')
          this.dialogTableVisible = false;
          this.isone = true
        } else {
          this.out('创建问题失败，退出当前提问，输入提出问题可重新提问')
          this.dialogTableVisible = false;
          this.isone = true
        }
      });
      this.selectZrrList = []
      this.selectYzrList = []
      this.zrrstr = ''
      this.zrbmstr = ''
      this.yzrstr = ''
    },
    getxcphwtList() {
      this.xcphwtList = [];
      getUpButtons().then(response => {
        const list = response.rows
        for (let item of list) {
          this.xcphwtList.push(item.butname)
        }
      })
    },
    //子组件确定按钮执行方法
    zrrSubmit(list, title) {
      if (title === '责任人') {
        let zrrStr = []
        let zrbmStr = []
        this.selectZrrList = list
        this.selectZrrList.forEach(item => {
          zrrStr.push(item.nickName)
          zrbmStr.push(item.dept.deptName)
        })
        this.zrrstr = zrrStr.join(",")
        this.zrbmstr = zrbmStr.join(",")
      } else {
        let yzrStr = []
        this.selectYzrList = list
        this.selectYzrList.forEach(item => {
          yzrStr.push(item.nickName)
        })
        this.yzrstr = yzrStr.join(",")
      }
      this.treeVisual = false
    },
    //历史记录的查寻的方法
    lishidaanchaxun() {
      this.lishidaanDialog = true;
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
    //关闭详细信息执行的方法
    shifoumanyi() {
      this.shifoumanyiDialog = true;
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


  },
};
</script>

<style>
.zhinengwendamain {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: calc(100vh - 84px);
}

.xiaoxiqu {
  height: calc(100% - 180px);
  width: 80%;
  max-width: 1000px;
  margin: 10px 0 50px 0;
  /* 添加样式以使内容滚动 */
  overflow-y: auto;
}

.robot-bubble, .user-bubble {
  max-width: calc(100% - 120px - 78px);
  position: relative; /* 设置气泡框为相对定位，以便定位箭头 */
}

.bubble-content {
  display: inline-block;
  max-width: calc(100% - 60px);
  padding: 10px;
  border-radius: 12px;
  background-color: #ffffff; /* 设置气泡框背景色 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加气泡框阴影效果 */
}

.bubble-content-user {
  display: inline-block;
  max-width: calc(100% - 60px);
  padding: 10px;
  border-radius: 12px;
  background-color: #dcf8c6; /* 设置气泡框背景色 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加气泡框阴影效果 */
}

.bubble-content::before {
  left: -10px; /* 设置箭头的左侧偏移值，可以调整箭头与气泡框的距离 */
  border-width: 8px 12px 8px 0; /* 设置箭头的形状 */
  border-color: transparent #dcf8c6 transparent transparent; /* 设置箭头颜色 */
}

.robot-bubble::before, .user-bubble::before {
  content: '';
  position: absolute;
  top: 24px; /* 设置箭头的垂直位置为气泡框高度的一半 */
  transform: translateY(-50%); /* 调整箭头垂直位置居中 */
  width: 0;
  height: 0;
  border-style: solid;
}

.user-bubble::before {
  right: 50px; /* 设置箭头的右侧偏移值，可以调整箭头与气泡框的距离 */
  border-width: 8px 0 8px 12px; /* 设置箭头的形状 */
  border-color: transparent transparent transparent #dcf8c6; /* 设置箭头颜色 */
}

.robot-bubble::before {
  left: 50px; /* 设置箭头的左侧偏移值，可以调整箭头与气泡框的距离 */
  border-width: 8px 12px 8px 0; /* 设置箭头的形状 */
  border-color: transparent #ffffff transparent transparent; /* 设置箭头颜色 */
}


.input-box {
  width: 60%;
  max-width: 780px;

}

.input-box .el-input__inner {
  height: 60px;
  border-radius: 10px;
  font-size: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

</style>
