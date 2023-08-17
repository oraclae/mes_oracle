<template>
  <el-card shadow="always" class="box-card"
           style="width: 100%;height:400px;overflow-y: auto;white-space:normal;overflow-x: scroll; border-radius: 20px">
    <div v-for="item in jhsjList" :key="item.xh">
      <div class="chatName" v-text="item.hfr"></div>
      <div style="vertical-align: top;display: inline-block">
        <div class="chatBox chatBox-left"
             style="word-wrap: break-word;max-width: 375px;white-space: normal;"
             v-text="item.hfxx"></div>
        <div class="chatTime"><span style="font-size: 14px;vertical-align: bottom;margin-left: 3px">{{ item.wtcjsj }}</span></div>
      </div>
      <div v-if="item.sjjhs.length>0">
        <div style="margin-left: 60px" v-for="item in item.sjjhs" :key="item.xh">
          <div class="chatName" v-text="item.hfr"></div>
          <div style="vertical-align: top;display: inline-block">
            <div class="chatBox chatBox-left"
                 style="word-wrap: break-word;max-width: 375px;white-space: normal;"
                 v-text="item.hfxx"></div>
            <div class="chatTime"><span style="font-size: 14px;vertical-align: bottom;margin-left: 3px">{{ item.wtcjsj }}</span></div>
          </div>
          <div v-if="item.sjjhs.length>0">
            <div style="margin-left: 60px" v-for="item in item.sjjhs" :key="item.xh">
              <div class="chatName" v-text="item.hfr"></div>
              <div style="vertical-align: top;display: inline-block">
                <div class="chatBox chatBox-left"
                     style="word-wrap: break-word;max-width: 375px;white-space: normal;"
                     v-text="item.hfxx"></div>
                <div class="chatTime"><span style="font-size: 14px;vertical-align: bottom;margin-left: 3px">{{ item.wtcjsj }}</span></div>
              </div>
              <div v-if="item.sjjhs.length>0">
                <div style="margin-left: 60px" v-for="item in item.sjjhs" :key="item.xh">
                  <div class="chatName" v-text="item.hfr"></div>
                  <div style="vertical-align: top;display: inline-block">
                    <div class="chatBox chatBox-left"
                         style="word-wrap: break-word;max-width: 375px;white-space: normal;"
                         v-text="item.hfxx"></div>
                    <div class="chatTime"><span style="font-size: 14px;vertical-align: bottom;margin-left: 3px">{{ item.wtcjsj }}</span></div>
                  </div>
                  <div v-if="item.sjjhs.length>0">
                    <div style="margin-left: 60px" v-for="item in item.sjjhs" :key="item.xh">
                      <div class="chatName" v-text="item.hfr"></div>
                      <div style="vertical-align: top;display: inline-block">
                        <div class="chatBox chatBox-left"
                             style="word-wrap: break-word;max-width: 375px;white-space: normal;"
                             v-text="item.hfxx"></div>
                        <div class="chatTime"><span style="font-size: 14px;vertical-align: bottom;margin-left: 3px">{{ item.wtcjsj }}</span></div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </el-card>
</template>

<script>
export default {
  name: "xianxixinxi",
  props:['jhsjList']
}
</script>

<style>

.chatName {
  display: inline-block;
  background-color: #c0d6f1;
  border-radius: 30px;
  padding: 5px;
  font-size: 16px;
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

.chatTime {
  margin-left: 5px;
  display: inline-block;
}

</style>
