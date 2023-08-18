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
  </div>
</template>

<script>

import {getwtlbMethod, getwtxlMethod} from "@/api/question/question";
import {selectDataByNotId} from "@/api/question/zhiNengWenDa";

export default {
  data() {
    return {
      wtlbList: [],
      wtxlList: [],
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
      this.$refs.input_ref.focus()
    },
    //问题类别的获得方法
    getwtlb() {
      this.wtlbList = [];
      getwtlbMethod().then(res => {
        if (res.code === 200) {
          let wtlbs = res.rows
          for (let wtlb of wtlbs) {
            const i = wtlb.lastIndexOf("问题")
            if (i >= 0) {
              wtlb = wtlb.substring(0, i);
            }
          }
          this.wtlbList = wtlbs
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
            this.out('长时间未操作，已自动退出当前问题，可输入 “提出问题” 开始提问。')
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
      //开始
      if (this.jb === '提出问题' && (this.inputValue.includes('提出问题') || this.inputValue.includes("问问题"))) {
        this.texts.push({user: '用户', text: this.inputValue})
        this.jb = "类别"
        let wtlbBrAll = ''
        for (let wtlb of this.wtlbList) {
          const wtlbBr = wtlb + '<br/>'
          wtlbBrAll = wtlbBrAll + wtlbBr
        }
        this.robotInputXiaoXi('查询到的问题类型如下，请选择')
        setTimeout(() => {
          this.robotInputXiaoXi(wtlbBrAll)
        }, 500);
      }
      //输入完类别
      else if (this.jb === '类别') {
        this.texts.push({user: '用户', text: this.inputValue});
        if (this.inputValue.includes('退出')) {
          this.out()
          return
        }
        let i = 0;
        for (let wtlb of this.wtlbList) {
          if (this.inputValue.includes(wtlb)) {
            i = 1
            this.queryParams.wtlb = wtlb
            break
          }
        }
        if (i === 1) {
          this.jb = '细类'
          this.$refs.input_ref.blur()
          getwtxlMethod({wtlb: this.queryParams.wtlb}).then(res => {
            if (res.code === 200) {
              this.wtxlList = res.rows;
              let wtxlBrAll = ''
              for (let i = 0; i < this.wtxlList.length; i++) {
                const wtxlBr = this.wtxlList[i] + '<br/>'
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
          for (let wtlb of this.wtlbList) {
            const wtlbBr = wtlb + '<br/>&nbsp;&nbsp;'
            wtlbBrAll = wtlbBrAll + wtlbBr
          }
          this.robotInputXiaoXi('没有您要找的类别，请从下列类别中选择：<br/>&nbsp;&nbsp;' + wtlbBrAll)
        }
      }
      //输入完细类
      else if (this.jb === '细类') {
        this.texts.push({user: '用户', text: this.inputValue});
        if (this.inputValue.includes('退出')) {
          this.out()
          return
        }
        let i = 0;
        for (let wtxl of this.wtxlList) {
          if (this.inputValue.includes(wtxl) || this.inputValue.includes('新建质量问题')) {
            i = 1
            this.queryParams.wtxl = wtxl
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
          for (let i = 0; i < this.wtxlList.length; i++) {
            const wtxlBr = this.wtxlList[i] + '<br/>&nbsp;&nbsp;'
            wtxlBrAll = wtxlBrAll + wtxlBr
          }
          this.robotInputXiaoXi('没有您要找的细类，请从下列细类中选择：<br/>&nbsp;&nbsp;' + wtxlBrAll)
        }
      }
      //查询完答案
      else if (this.jb === '满意') {
        this.texts.push({user: '用户', text: this.inputValue});
        if (this.inputValue.includes('退出')) {
          this.out()
          return
        }
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
            for (let i = 0; i < this.wtxlList.length; i++) {
              const wtxlBr = this.wtxlList[i] + '<br/>'
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
