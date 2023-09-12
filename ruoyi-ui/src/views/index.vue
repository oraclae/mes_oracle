<template>
  <div class="symain">
    <div class="left-side">
      <el-card style="height: 100%;border-radius: 20px" class="box-card">
        <zndy style="width: 100%;height: 100%;border-radius: 20px"/>
      </el-card>
    </div>
    <div class="right-side">
      <el-card style="height: 100%;border-radius: 20px" class="box-card">
        <div>
          <el-row style="text-align: center;cursor: default;margin-top: 100px">
            <el-col :span="24">
              <div style="margin-bottom: 50px">
                <b @click="toDownQuestion" class="wttitle">现场配合问题</b>
              </div>
              <div style="width: 100%;">
                <el-row>
                  <el-col :span="12">
                    <div style="width: 80%">
                      <b @click="toDownQuestion" class="wtsl">{{ this.sl.hjsl }}</b>
                      <br>
                      <div class="wtmc"><b @click="toDownQuestion">呼叫数量</b></div>
                    </div>
                  </el-col>
                  <el-col :span="12">
                    <div style="width: 80%">
                      <b @click="toDownQuestion" class="wtsl">{{ this.sl.jssl }}</b>
                      <br>
                      <div class="wtmc"><b @click="toDownQuestion">接收数量</b></div>
                    </div>
                  </el-col>
                </el-row>
              </div>
            </el-col>
            <el-col :span="24" style="margin-top: 200px;">
              <div style="margin-bottom: 50px" @click="toMyToDoList">
                <b @click="toDownQuestion" class="wttitle">跟踪协同问题</b>
              </div>
              <div style="width: 80%" @click="toMyToDoList">
                <b @click="toDownQuestion" class="wtsl">{{ this.sl.dbsl }}</b>
                <br>
                <div class="wtmc"><b @click="toDownQuestion">待办数量</b></div>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import zndy from "@/views/question/zhiNengWenDa/index"
import {getSl} from "@/api/question/sy";

export default {
  components: {
    zndy
  },
  name: "Index",
  data() {
    return {
      sl: {},
    };
  },
  mounted() {
    this.getSl()
  },
  methods: {
    getSl() {
      getSl().then(response => {
        this.sl = response.data
      })
    },
    toMyToDoList() {
      this.$router.push('/question/MyToDoList');
    },
    toDownQuestion() {
      this.$router.push('/question/downQuestion');
    },
  }
};
</script>
<style scoped>
/*主*/
.symain {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%; /* 使用视口宽度单位 */
  height: 100%; /* 使用视口高度单位 */
  display: flex;
}

/*左div*/
.left-side {
  height: 100%;
  width: calc(100% - 500px);
  display: inline-block;
  padding: 15px 15px 15px 15px;
}

/*右div*/
.right-side {
  width: 500px;
  height: 100%;
  padding: 15px;
  overflow: auto;
}

.wttitle {
  width: 100%;
  font-size: 32px;
}

.wtsl {
  width: 100%;
  font-size: 46px;
}

.wtmc b {
  font-size: 25px;
}

.wtmc b, .wttitle, .wtsl {
  color: #2414a1d4;
}

.wtmc {
  margin-top: 10px;
  width: 100%;
}

.el-col {
  justify-content: center;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.right-side b:hover {
  color: #00afff;
  cursor: pointer;
}

.right-side b:active {
  transform: scale(0.9);
}

</style>
<style>
.symain .el-card__body{
  padding: 0;
  height: 100%;
}
</style>

