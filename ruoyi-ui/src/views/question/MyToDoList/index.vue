<template>
  <div class="app-container home">
    <el-row :gutter="24">
      <el-col :span="24" :xs="24">
        <el-radio-group v-model="queryParams.radios" @change="changeRadio">
          <el-radio label="待办" class="el_radios_position">待办</el-radio>
          <el-radio label="处理中" class="el_radios_position">处理中</el-radio>
          <el-radio label="已关闭" class="el_radios_position">已关闭</el-radio>
        </el-radio-group>
        <el-button type="success" size="small" @click="problemConcern">问题关注</el-button>
        <el-button type="success" size="small" @click="handleAdd">回复/预览</el-button>
        <el-button style="background-color: red" type="primary" size="small" @click="closeQuestion">申请关闭</el-button>
        <el-button type="warning" size="small" @click="shuaxin">刷新</el-button>
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
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-col>
    </el-row>
    <el-row :gutter="24">
      <el-col :span="24" :xs="24">
        <div class="MyToDoListTable">
          <el-table
            :data="tableData"
            v-loading="loading"
            :show-overflow-tooltip="true"
            size="medium"
            @selection-change="handleSelectionChange"
            :highlight-current-row="highLight"
            row-key="id"
            ref="multipleTable"
            border
            @cell-dblclick="cellClick"
            @row-dblclick="openDialog"
          >
            <el-table-column
              type="selection"
              width="50"
              :reserve-selection="true">
            </el-table-column>
            <el-table-column
              align="center"
              label="序号"
              type="index"
              width="50">
            </el-table-column>
            <el-table-column
              align="center"
              prop=""
              label="预警字段"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
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
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              label="反馈预警"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <img
                  style="width: 30px"
                  v-if="getFkyjText(scope.row)==='红'&&scope.row.lxfk==='例行反馈'"
                  :src="require('@/assets/questionIcons/红警告.png')"
                />
              </template>
            </el-table-column>
            <el-table-column
              prop="wdwt"
              label="我的待办待办数量"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="zrrzt"
              label="责任人状态"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="wtbh"
              label="问题编号"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="cjr"
              label="人员"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="cjbm"
              label="部门"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="cjks"
              label="科室"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="wtmc"
              label="问题名称"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="gxh"
              label="工序号"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="cjsj"
              label="问题创建时间"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="scddh"
              label="生产订单号"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="cpxh"
              label="产品型号"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="wtly"
              label="问题来源"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="wtlb"
              label="问题类别"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="wtxl"
              label="问题细类"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="wtms"
              label="问题描述"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="zrbm"
              label="责任部门"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="zrks"
              label="责任科室"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="zrr"
              label="责任人"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="yrzbm"
              label="阅知人部门"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="yzr"
              label="阅知人"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="zycd"
              label="重要程度"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="xwjjsj"
              label="希望解决时间"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="sjzrr"
              label="升级责任人"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="wtzt"
              label="问题状态"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="jssj"
              label="接收时间"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="gzxt"
              label="是否跟踪协同"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <span >{{ scope.row.gzxt==='true'?'是':'否' }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="gbsj"
              label="关闭时间"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="lxfk"
              label="是否例行反馈"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="pc"
              label="批次"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="sb"
              label="设备"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="xcphwt"
              label="是否现场配合问题"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <span >{{ scope.row.xcphwt==='true'?'是':'否' }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="ldps"
              label="是否领导批示"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <span >{{ scope.row.ldps==='true'?'是':'否' }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="scfj"
              label="是否上传附件"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <span >{{ scope.row.scfj==='true'?'是':'否' }}</span>
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
    <el-dialog class="dialog dialogRad" :visible.sync="open" :width="withd"
               style="padding-top: 0;padding-bottom: 0;margin-top:1px;" append-to-body>
      <div class="app-container indexHeight">
        <el-card shadow="always" class="box-card" style="background: linear-gradient(to bottom, #93bec1, #2982C3);width: 100%;border-radius: 30px">
          <div style="line-height: 15px;">
            <label style="font-size: 18px">回复/预览</label>
            <el-button style="margin-left: 10px" type="warning" size="small" @click="open=false">退出</el-button>
            <el-button style="background-color: red" type="warning" size="small"
                       @click="applyForClosure(closureID)">申请关闭
            </el-button>
            <div style="display: inline-block;text-align: center;margin-left: 240px;font-size: 15px">
              <div><label>未完成天数</label></div>
              <div><label>{{ closureID.wwcts }}</label></div>
            </div>
            <div style="display: inline-block;text-align: center;margin-left: 120px;font-size: 15px">
              <div><label>超期天数</label></div>
              <div><label>{{ closureID.cqts }}</label></div>
            </div>
            <div style="display: inline-block;text-align: center;margin-left: 140px;font-size: 15px">
              <div><label>完成天数</label></div>
              <div><label>{{ closureID.wcts }}</label></div>
            </div>
          </div>
        </el-card>
        <el-container>
          <el-aside width="300px">
            <div>
              <el-card shadow="always" class="box-card"
                       style="background: linear-gradient(to right, #2982C3, #93bec1);width: 300px;border-radius: 20px">
                <div>
                  <div>
                    <label>创建部门:</label>
                    <span style="margin-left: 15px" v-text="closureID.cjbm"></span>
                  </div>
                  <div>
                    <label>创建科室:</label>
                    <span style="margin-left: 15px" v-text="closureID.cjks"></span>
                  </div>
                  <div>
                    <label>创建人:</label>
                    <span style="margin-left: 15px" v-text="closureID.cjr"></span>
                  </div>
                  <div>
                    <label>创建时间:</label>
                    <span style="margin-left: 15px" v-text="closureID.cjsj"></span>
                  </div>
                  <div>
                    <label>零组件号:</label>
                    <span style="margin-left: 15px" v-text="closureID.cpxh"></span>
                  </div>
                  <div>
                    <label>生产订单号:</label>
                    <span style="margin-left: 15px" v-text="closureID.scddh"></span>
                  </div>
                  <div>
                    <label>问题来源:</label>
                    <span style="margin-left: 15px" v-text="closureID.wtly"></span>
                  </div>
                  <div>
                    <label>问题细类:</label>
                    <span style="margin-left: 15px" v-text="closureID.wtxl"></span>
                  </div>
                </div>
              </el-card>
            </div>
            <div>
              <el-card shadow="always" class="box-card"
                       style="background: linear-gradient(to right, #2982C3, #93bec1);width: 300px;border-radius: 20px">
                <div>
                  <div>
                    <label>紧急程度:</label>
                    <span style="margin-left: 15px" v-text="closureID.zycd"></span>
                  </div>
                  <div>
                    <label>影响程度:</label>
                    <span style="margin-left: 15px" v-text="closureID.yxcd"></span>
                  </div>
                </div>

              </el-card>
            </div>
            <div>
              <el-card shadow="always" class="box-card card tablec"
                       style="background: linear-gradient(to right, #2982C3, #93bec1);width: 300px;border-radius: 20px;border-radius: 20px">
                <el-table
                  :data="zrrList"
                  height="300px"
                  max-height="300px"
                  border
                  size="mini"
                  style="background: linear-gradient(to right, #2982C3, #93bec1);width: 100%">
                  <el-table-column
                    prop="zrrzt"
                    label="责任人状态"
                    show-overflow-tooltip
                  >
                  </el-table-column>
                  <el-table-column
                    prop="zrr"
                    label="责任人"
                    show-overflow-tooltip
                  >
                  </el-table-column>
                  <el-table-column
                    prop="zrbm"
                    label="责任部门"
                    show-overflow-tooltip
                  >
                  </el-table-column>
                </el-table>
              </el-card>
            </div>
          </el-aside>
          <el-main>
            <div style="display: inline-block;float: left">
              <el-card shadow="always" class="box-card"
                       style="background: linear-gradient(to right, #2982C3, #93bec1);width: 750px;border-radius: 20px">
                <div style="margin-bottom: 8px">
                  <label style="font-size: 18px">问题名称:</label>
                  <label style="font-size: 18px">{{closureID.wtmc}}</label>
                </div>
                <div>
                  <label style="font-size: 18px">问题描述:</label>
                  <el-input
                    type="textarea"
                    :autosize="{ minRows: 4, maxRows: 4}"
                    style="margin-top: 1px;"
                    placeholder="请输入内容"
                    v-model="wtms"
                    :disabled="true">
                  </el-input>
                </div>
              </el-card>
              <el-card shadow="always" class="box-card"
                       style="background: linear-gradient(to right, #2982C3, #93bec1);width: 750px;border-radius: 20px">
                <!--                <el-switch
                                  v-model="islxfk"
                                  @change="switchMethod"
                                  active-text="例行反馈"
                                  inactive-text="业务交互">
                                </el-switch>-->
                <label style="font-size: 16px" v-if="this.closureID.lxfk==='例行反馈'">例行反馈</label>
                <label style="font-size: 16px" v-else>业务交互</label>
                <el-checkbox class="fj" v-model="fujian" @change="fujianyulan" style="margin-left: 25px;color: white">
                  附件预览
                </el-checkbox>
                <el-button style="margin-left: 25px;background-color: #db607a" size="mini">上传附件</el-button>
                <el-button v-if="this.closureID.lxfk==='例行反馈'" style="margin-left: 25px" size="mini" type="warning"
                           @click="lixingfankuiDialogMethod">例行反馈
                </el-button>
                <el-button v-else style="margin-left: 25px" size="mini" type="warning" @click="huifuDialogMethod">回复
                </el-button>
                <!--                <el-button style="margin-left: 55px" @click="lingdaopishiDialogMethod" size="mini" type="danger">领导批示</el-button>-->
              </el-card>
              <el-card shadow="always" class="box-card"
                       style="width: 750px;height:400px;overflow-y: auto;border-radius: 20px">
                <div v-for="item in jhsjList">
                  <div class="chatName" v-text="item.hfr"></div>
                  <div @contextmenu="showContextMenu($event,item)" @click="huifuyangshione(item.xh)"
                       :class="{ 'clicked': currentDivIndex === item.xh }"
                       @dblclick="huifuyangshi(item.ejhfppyj,item.xh,islxfk)" class="chatBox chatBox-left"
                       v-text="item.hfxx"></div>
                  <div class="chatTime"><span style="font-size: 14px">{{ item.wtcjsj }}</span></div>
                  <div v-if="item.sjjhs.length>0">
                    <div style="margin-left: 60px" v-for="item in item.sjjhs">
                      <div class="chatName" v-text="item.hfr"></div>
                      <div @contextmenu="showContextMenu($event,item)" @click="huifuyangshione(item.xh)" :class="{ 'clicked': currentDivIndex === item.xh }"
                           @dblclick="huifuyangshi(item.ejhfppyj,item.xh,islxfk)" class="chatBox chatBox-left"
                           v-text="item.hfxx"></div>
                      <div class="chatTime"><span style="font-size: 14px">{{ item.wtcjsj }}</span></div>
                      <div v-if="item.sjjhs.length>0">
                        <div style="margin-left: 60px" v-for="item in item.sjjhs">
                          <div class="chatName" v-text="item.hfr"></div>
                          <div @contextmenu="showContextMenu($event,item)" @click="huifuyangshione(item.xh)" :class="{ 'clicked': currentDivIndex === item.xh }"
                               @dblclick="huifuyangshi(item.ejhfppyj,item.xh,islxfk)" class="chatBox chatBox-left"
                               v-text="item.hfxx"></div>
                          <div class="chatTime"><span style="font-size: 14px">{{ item.wtcjsj }}</span></div>
                          <div v-if="item.sjjhs.length>0">
                            <div style="margin-left: 60px" v-for="item in item.sjjhs">
                              <div class="chatName" v-text="item.hfr"></div>
                              <div @contextmenu="showContextMenu($event,item)" @click="huifuyangshione(item.xh)" :class="{ 'clicked': currentDivIndex === item.xh }"
                                   @dblclick="huifuyangshi(item.ejhfppyj,item.xh,islxfk)" class="chatBox chatBox-left"
                                   v-text="item.hfxx"></div>
                              <div class="chatTime"><span style="font-size: 14px">{{ item.wtcjsj }}</span></div>
                              <div v-if="item.sjjhs.length>0">
                                <div style="margin-left: 60px" v-for="item in item.sjjhs">
                                  <div class="chatName" v-text="item.hfr"></div>
                                  <div @contextmenu="showContextMenu($event,item)" @click="huifuyangshione(item.xh)"
                                       :class="{ 'clicked': currentDivIndex === item.xh }" class="chatBox chatBox-left"
                                       v-text="item.hfxx"></div>
                                  <div class="chatTime"><span style="font-size: 14px">{{ item.wtcjsj }}</span></div>
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
              <div v-show="isContextMenuVisible" class="context-menu" :style="{ top: contextMenuPosition.y + 'px', left: contextMenuPosition.x + 'px' }">
                <!-- 菜单内容 -->
                <div class="menu-item" @click="uploadAttachment">上传附件</div>
                <div class="menu-item" @click="deleteJhjl">删除</div>
              </div>
            </div>
            <div v-show="isShow" style="display: inline-block;float: right;">
              <el-card v-show="isShow" shadow="always" class="box-card cards"
                       style="width: 300px;height: 695px;border-radius: 20px">
                <el-card v-show="isShow" shadow="always" class="box-card cards tablec"
                         style="background: linear-gradient(to right, #2982C3, #93bec1);width: 300px;height: 345px;border-radius: 20px">
                  <div style="margin-top: 16px">
                    <label style="margin-left: 100px;font-size: 18px;">问题附件预览</label>
                  </div>
                  <el-table
                    :data="fileData"
                    border
                    size="mini"
                    max-height="250px"
                    style="width: 100%;margin-top: 26px">
                    <el-table-column
                      prop="name"
                      label="人员"
                      show-overflow-tooltip
                    >
                    </el-table-column>
                    <el-table-column
                      prop="fileName"
                      label="文件名称"
                      show-overflow-tooltip
                    >
                    </el-table-column>
                    <el-table-column
                      prop="fileType"
                      label="上传时间"
                      show-overflow-tooltip
                    >
                    </el-table-column>
                  </el-table>
                </el-card>

                <el-card v-show="isShow" shadow="always" class="box-card cards tablec"
                         style="background: linear-gradient(to right, #2982C3, #93bec1);width: 300px;height: 345px;border-radius: 20px">
                  <div style="margin-top: 10px">
                    <label style="margin-left: 100px;font-size: 18px;">回复附件预览</label>
                  </div>
                  <el-table
                    :data="fileData"
                    border
                    size="mini"
                    max-height="250px"
                    style="width: 100%;margin-top: 26px">
                    <el-table-column
                      prop="name"
                      label="人员"
                      show-overflow-tooltip
                    >
                    </el-table-column>
                    <el-table-column
                      prop="fileName"
                      label="文件名称"
                      show-overflow-tooltip
                    >
                    </el-table-column>
                    <el-table-column
                      prop="fileType"
                      label="上传时间"
                      show-overflow-tooltip
                    >
                    </el-table-column>
                  </el-table>
                </el-card>
              </el-card>
            </div>
            <div v-show="isShowLdps" style="width: 300px;display: inline-block;float: right;">
              <el-card v-show="isShowLdps" shadow="always" class="box-card cards tablec"
                       style="white-space:nowrap;overflow-x: scroll;height: 695px;border-radius: 20px">
                <div style="margin-top: 16px;margin-bottom: 20px">
                  <label style="margin-left: 115px;font-size: 18px;">领导批示</label>
                </div>
                <div v-for="item in ldpiList">
                  <div class="chatName" v-text="item.hfr"></div>
                  <div @contextmenu="showContextMenu($event,item)" @click="huifuyangshione(item.xh)"
                       :class="{ 'clicked': currentDivIndex === item.xh }"
                       class="chatBox chatBox-left"
                       v-text="item.hfxx" style="word-wrap: break-word;max-width: 200px;white-space: normal"></div>
                  <div class="chatTime"><span style="font-size: 14px">{{ item.wtcjsj }}</span></div>
                </div>
                <!--                <el-table
                                  class="tablec"
                                  :data="ldpiList"
                                  border
                                  size="mini"
                                  style="width: 100%;margin-top: 26px;">
                                  <el-table-column
                                    prop="hfr"
                                    label="人员"
                                    show-overflow-tooltip
                                  >
                                  </el-table-column>
                                  <el-table-column
                                    prop="hfxx"
                                    label="回复信息"
                                    show-overflow-tooltip
                                  >
                                  </el-table-column>
                                  <el-table-column
                                    prop="wtcjsj"
                                    label="时间"
                                    show-overflow-tooltip
                                  >
                                  </el-table-column>
                                </el-table>-->
              </el-card>
            </div>
          </el-main>
        </el-container>
      </div>
    </el-dialog>
    <!--  回复按钮的弹出框  -->
    <el-dialog
      @open="dialogOpened"
      class="buttonDialog dialogRad"
      title="回复"
      :visible.sync="huifuDialog"
      width="30%"
    >
      <el-input
        ref="huifuInput"
        type="textarea"
        :rows="5"
        style="margin-top: 5px"
        placeholder="请输入内容"
        v-model="huifuTest">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="huifuDialog=false">取 消</el-button>
        <el-button type="primary" @click="huifuSubmit(ejhfppyj,huifuId,'回复',1)">确 定</el-button>
      </span>
    </el-dialog>
    <!--  例行反馈的按钮的弹出框  -->
    <el-dialog
      @open="dialogLXFKOpened"
      class="buttonDialog dialogRad"
      title="例行反馈"
      :visible.sync="lixingfankuiDialog"
      width="30%">
      <el-input
        ref="lixingfankuiInput"
        type="textarea"
        :rows="5"
        style="margin-top: 5px"
        placeholder="请输入内容"
        v-model="huifuTest">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="lixingfankuiDialog = false">取 消</el-button>
        <el-button type="primary" @click="huifuSubmit(ejhfppyj,huifuId,'例行反馈',1)">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      @open="dialogLDPSOpened"
      class="buttonDialog dialogRad"
      title="领导批示"
      :visible.sync="lingdaopishiDialog"
      width="30%">
      <el-input
        ref="lingdaoInput"
        type="textarea"
        :rows="5"
        style="margin-top: 5px;"
        placeholder="请输入内容"
        v-model="huifuTest">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="lingdaopishiDialog = false">取 消</el-button>
        <el-button type="primary" @click="huifuSubmit(ejhfppyj,huifuId,'领导批示',1)">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import {
  updateMyDoListStatus,
  getQuestionList,
  getJhjl,
  getzerData,
  saveJhjlList,
  updateWtztEnd,
  savegzwtList,
  deleteJhjlByXh,
  updateQuestionWDWTToZero, updateQuestionWdwt,getLDPS
} from "@/api/question/question";
export default {
  computed: {
    getTimeText() {
      return function (time) {
        const currentTime = new Date();
        const targetTime = new Date(time);
        const diffTime = targetTime - currentTime;
        const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24))+1;
        if (diffDays >= 2) {
          return '正常'; // 在三天前之前，添加绿色类名
        } else if (diffDays < 2 && diffDays >= 1) {
          return '警告'; // 在三天之内，添加黄色类名
        } else {                 //小于0的时候为红色
          return '超出';
        }
      };
    },
    //判断是否反馈预警
    getFkyjText() {
      return function (row) {
        if (row.qwfksj === undefined||row.qwfksj===null ||row.qwfksj==='') {
          return;
        }
        if (row.sfdzfk === 0 || row.sfdzfk === '0') {
          const date = new Date();
          const week=date.getDay();
          return '红';
          /*switch (row.qwfksj) {
            case '一':
              if (week === 6) {
                return '绿';
              }else if (week === 0) {
                return '黄';
              } else if (week===1){
                return '红';
              }
              break;
            case '二':
              if (week === 0) {
                return '绿';
              }else if (week === 1) {
                return '黄';
              } else if (week===2){
                return '红';
              }
              break;
            case '三':
              if (week === 1) {
                return '绿';
              }else if (week === 2) {
                return '黄';
              } else if (week===3){
                return '红';
              }
              break;
            case '四':
              if (week === 2) {
                return '绿';
              }else if (week === 3) {
                return '黄';
              } else if (week===4){
                return '红';
              }
              break;
            case '五':
              if (week === 3) {
                return '绿';
              }else if (week === 4) {
                return '黄';
              } else if (week===5){
                return '红';
              }
              break;
            case '六':
              if (week === 4) {
                return '绿';
              }else if (week === 5) {
                return '黄';
              } else if (week===6){
                return '红';
              }
              break;
            case '日':
              if (week === 5) {
                return '绿';
              }else if (week === 6) {
                return '黄';
              } else if (week===0){
                return '红';
              }
              break;
            default:
              console.log("数据存储错误");
          }*/
        }

      };
    }
  },
  data() {
    return {
      itemJhjl:{},//单击回复数据的当前值
      isContextMenuVisible:false,//是否显示菜单
      contextMenuPosition: { x: 0, y: 0 },//实现右键菜单的
      currentDivIndex: null,//实现单击颜色变化的
      wtms: '',//问题描述不能修改
      huifuTest:'',//回复弹出框的输入框
      huifuId:'',//存上级的id值
      ejhfppyj:'',//存下级的所有id
      //回复预览框需要的的属性
      closureID:{},//点击回复预览的数据，存在这里
      lingdaopishiDialog:false,//领导批示按钮的弹出框的是否显示
      lixingfankuiDialog:false,//例行反馈按钮的弹出框的是否显示
      huifuDialog:false,//回复按钮的弹出框的是否显示
      islxfk:false,//是否是例行反馈
      isShowLdps:false,//判断是否有领导批示
      isShow:false,//判断附件预览是否展示
      withd:'1090px',//回复预览弹出框的大小1390
      fujian: false,//附件多选框是否确定
      fileData: [
        {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }],
      zrrList:[],//回复预览页面的责任人数据集合
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

      handleSelect:[],//选择的数据
      // 总条数
      total: 0,
      //加载图片
      loading: true,
      /*是否高度变化*/
      highLight: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tName: null,
        tCode: null,
        tPid: null,
        tLevel: null,
        radios: '待办',
        username:'',
        WTID:null,
        JHZT:null
      },
      tableData: []
    };
  },
  methods: {
    // 控制input显示 row 当前行 column 当前列
    cellClick(row, column) {
      this.editing = true
      this.editingIndex = row.index
      this.editingProperty = column.property
      this.highLight = true
    },
    //实现双击弹出回复预览弹出框
    openDialog(row) {
      if (this.editingProperty === 'wtmc'||this.editingProperty==='wtms') {
        this.isShowLdps = false;
        this.isShow = false;
        this.withd = '1090px';
        this.currentDivIndex = '';
        this.jhsjList = [];
        this.closureID = {};
        this.wtms = '';
        this.fujian = false;
        this.islxfk = false;
        this.closureID = row;
        this.wtms = row.wtms;
        if (this.closureID.lxfk === '例行反馈') {
          this.loadJhjlList('例行反馈');
          this.islxfk = true;
        } else {
          this.loadJhjlList('回复');
          this.islxfk = false;
        }
        this.loadJhjlList('领导批示');
        this.open = true;
        this.title = "回复/预览";
        this.loadzerData();
        updateQuestionWDWTToZero({id: this.closureID.id}).then(res => {
          if (res.code === 200) {
            this.shuaxin();
          }
        });
        if (this.closureID.wtzt === '提交') {
          this.updateWtzt();
        }
      }

    },
    //实现右键菜单
    showContextMenu(event,item) {
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
    deleteJhjl() {
      // 处理菜单项点击事件
      let ejhfppyj = this.itemJhjl.ejhfppyj;
      //先判断当前登录的用户是否管理员，如果是那么可以删除，如果不是下级有数据不能删除
      if (ejhfppyj!==null&&ejhfppyj.length>0) {
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
    //回复数据的单击事件
    huifuyangshione(index) {
      this.currentDivIndex = index;
    },
    //上传附件的方法
    shangchuangfujian() {

    },
    //判断是否使例行反馈还是回复的数据
    huifuyangshi(ejhfppyj,xh,islxfk) {
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
    //获得交互记录的数据
    loadJhjlList(jhzt) {
      if (jhzt === '领导批示') {
        getLDPS({WTID:this.closureID.id,JHZT:jhzt}).then(res=>{
          if (res.code === 200) {
            this.ldpiList = res.rows;
            if (this.ldpiList.length > 0) {
              this.withd='1390px'
              this.isShowLdps = true;
            }
          }
        });
      } else {
        getJhjl({WTID:this.closureID.id,JHZT:jhzt}).then(res => {
          if (res.code === 200) {
            this.jhsjList = res.rows;
          }
        });
      }
    },
    //判断这条数据是否可以看例行反馈数据
    switchMethod() {
      if (this.closureID.gzxt==='true') {
        if (this.islxfk) {
          this.islxfk = true;
          this.jhsjList = [];
          this.loadJhjlList('例行反馈');
        } else {
          this.islxfk = false;
          this.jhsjList = [];
          this.loadJhjlList('回复');
        }
      } else {
        this.islxfk = false;
      }
    },
    //回复按钮的取消方法
    huifuCancel(id,type) {
      this.huifuDialog = false;
    },
    //回复按钮的确定方法
    huifuSubmit(ejhfppyj, id, type, js) {
      let sjjh = {
        value: this.closureID.wtzt,
        hfxx: this.huifuTest,
        wtbt: this.closureID.wtmc,
        wtid: this.closureID.id,
        wtcjid: this.closureID.cjrid,
        wtcjr: this.closureID.cjr,
        wtcjbm: this.closureID.cjbm,
        wtcjks: this.closureID.cjks,
        jhzt: type,
        js: js,
        xh: '',
        userName:'待办',
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
        sjjh.js = '';
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
      updateQuestionWdwt(sjjh).then(res => {
        if (res.code === 200) {
          this.load();
          console.log(res.msg);
        }
      });
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
    //例行反馈按钮的执行方法
    lixingfankuiDialogMethod() {
      this.huifuTest = '';
      if (this.queryParams.radios === '已关闭') {
        this.$message.error("该问题已经关闭，不能回复数据");
      } else {
        this.lixingfankuiDialog = true;
      }
    },
    //领导批示按钮的执行方法
    lingdaopishiDialogMethod() {
      this.huifuTest = '';
      if (this.queryParams.radios === '已关闭') {
        this.$message.error("该问题已经关闭，不能回复数据");
      } else {
        this.lingdaopishiDialog = true;
      }
    },
    //回复预览页面的责任人的数据
    loadzerData() {
      getzerData({wtid: this.closureID.id}).then(res=>{
        if (res.code === 200) {
          this.zrrList = res.rows;
        }
      })
    },
    //回复预览页面的附件预览多选框
    fujianyulan() {
      if (this.fujian) {
        this.withd = '1390px';
        this.isShow = true;
        this.isShowLdps = false;
      } else {
        if (this.ldpiList.length > 0) {
          //判断是否领导批示有值，如果有值那么就展示领导批示
          this.withd = '1390px';
          this.isShow = false;
          this.isShowLdps = true;
        } else {
          this.withd = '1090px';
          this.isShow = false;
        }
      }
    },
    //刷新附件
    refreshAttachment() {

    },
    //回复预览页面的申请关闭按钮
    applyForClosure(closureID) {
      let ids = [];
      const id = {id: closureID.id, value: '待关闭'};
      ids.push(id);
      this.open = false;
      this.$confirm('是否确认关闭数据项?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateMyDoListStatus(ids).then(res=>{
          if (res.code === 200) {
            this.$message.success("关闭成功");
            this.load();
          }
        })
      }).catch(() => {
      });
    },
    //按照条件查询数据
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.load();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.userName = null
      this.queryParams.type = '';
      this.resetForm("queryForm");
      this.handleQuery();
    },
    //刷新方法
    shuaxin() {
      this.load();
    },
    load() {
      this.loading = true;
      getQuestionList(this.queryParams).then(res => {
        if (res.code === 200) {
          this.tableData = res.rows;
          this.total = res.total;
          this.loading = false;
          this.$refs.multipleTable.clearSelection();
        }
      });
    },
    //改变单选框执行的方法
    changeRadio() {
      this.load();
    },

    //责任人的申请关闭
    closeQuestion() {
      if (this.handleSelect.length < 1) {
        this.$message.info("请至少选择一条数据");
      } else {
        let ids = [];
        this.handleSelect.forEach(item => {
          const id = {id: '', value: ''};
          id.id = item.id;
          id.value = '待关闭';
          ids.push(id);
        });
        updateMyDoListStatus(ids).then(res=>{
          if (res.code === 200) {
            this.$message.success("修改成功");
            this.load();
          }
        })
      }
    },
    //多选框的方法
    handleSelectionChange(val) {
      this.handleSelect = val
    },
    handleAdd() {
      this.isShowLdps = false
      this.isShow = false;
      this.withd = '1090px';
      this.currentDivIndex = '';
      this.jhsjList = [];
      this.closureID = {};
      this.wtms = '';
      this.fujian = false;
      let number = this.handleSelect.length;
      if (number <= 0) {
        this.$message.error("请选择一条数据");
        return
      }
      if (number > 1) {
        this.$message.error("只能选择一条数据");
        return;
      }
      this.islxfk = false;
      this.closureID = this.handleSelect[0];
      this.wtms = this.handleSelect[0].wtms;
      if (this.closureID.lxfk === '例行反馈') {
        this.loadJhjlList('例行反馈');
        this.islxfk = true;
      } else {
        this.loadJhjlList('回复');
        this.islxfk = false;
      }
      this.loadJhjlList('领导批示');
      this.open = true;
      this.title = "回复/预览";
      this.loadzerData();
      updateQuestionWDWTToZero({id:this.closureID.id}).then(res => {
        if (res.code === 200) {
          console.log(res.msg);
          this.shuaxin();
        }
      });
      if (this.closureID.wtzt === '提交') {
        this.updateWtzt();
      }
    },
    //修改问题状态为接收状态
    updateWtzt() {
      let wt={id:this.closureID.id, wtzt: this.closureID.wtzt};
      updateWtztEnd(wt).then(res=>{
      });
    },
    //问题关注的方法
    problemConcern() {
      let number = this.handleSelect.length;
      if (number < 1) {
        this.$message.error("请至少选择一条数据");
        return;
      }
      let ids = [];
      this.handleSelect.forEach(Item=>{
        ids.push(Item.id);
      });
      console.log(ids);
      savegzwtList(ids).then(res=>{
        if (res.code === 200) {
          this.$message.success("关注成功");
          this.load();
        } else {
          this.$message.error("关注失败");
        }
      })
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
  },
  mounted() {
    this.load();
    document.addEventListener('click', this.handlePageClick);
  },
  beforeDestroy() {
    document.removeEventListener('click', this.handlePageClick);
  }
};
</script>

<style>
.home ::-webkit-scrollbar {
  width: 8px; /* 设置滚动条的宽度 */
  height: 8px;
}
.el-checkbox__input.is-checked + .el-checkbox__label{
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
  border-radius: 30px;
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
.MyToDoListTable .el-table__cell{
  height: 40px;
}
.MyToDoListTable .el-table--medium .el-table__cell{
  padding: 0;
}
</style>
<style scoped lang="scss">
.el-tooltip__popper {
  font-size: 18px; /* 调整字体大小为你需要的大小 */
}
.el-table{
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
}

.chatBox-left::before {
  content: '';
  margin-top: 8px;
  margin-left: -16px;
  position: absolute;
  border: 5px solid;
  border-right-color: greenyellow;
  border-left-color: #ffffff;
  border-top-color: #ffffff;
  border-bottom-color: #ffffff;
}

.chatName {
  display: inline-block;
  background-color: #c0d6f1;
  border-radius: 30px;
  padding: 5px;
  font-size: 16px;
  margin-bottom: 10px;
}

.chatTime {
  display: inline-block;
}

.dialog {
  position: absolute;;
  z-index: 1;
}
.tablec ::v-deep .el-table{
  background: rgba(0,0,0,0);
}
.tablec ::v-deep .el-table tr{
  background: rgba(0,0,0,0);
}
.tablec ::v-deep .el-table .el-table__header-wrapper th, .el-table .el-table__fixed-header-wrapper th{
  background-color:rgba(0,0,0,0);
}
</style>

