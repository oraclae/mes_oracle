<template>
  <div class="app-container home">
    <el-row :gutter="24">
      <!--用户数据-->
      <el-col :span="24" :xs="24">
        <el-radio-group v-model="queryParams.radios" @change="changeRadio">
          <el-radio label="待办" class="el_radios_position">待办</el-radio>
          <el-radio label="处理中" class="el_radios_position">处理中</el-radio>
          <el-radio label="待关闭" class="el_radios_position">待关闭</el-radio>
          <el-radio label="已关闭" class="el_radios_position">已关闭</el-radio>
        </el-radio-group>
        <el-button type="success" size="small" @click="createQuestion">创建问题</el-button>
        <el-button type="success" size="small" :disabled="single" @click="handleAdd">回复/预览</el-button>
        <el-button type="primary" style="background-color: red" :disabled="single"
                   v-if="queryParams.radios!=='已关闭'&&queryParams.radios!=='待关闭'" size="small" @click="closeQuestion">关闭
        </el-button>
        <el-button type="primary" size="small" :disabled="multiple" @click="deleteData">删除</el-button>
        <el-button
          type="primary"
          plain
          :disabled="single"
          icon="el-icon-download"
          size="mini"
          @click="handleUpload"
        >上传附件
        </el-button>
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
      </el-col>
    </el-row>
    <el-row :gutter="24">
      <el-col :span="24" :xs="24">
        <div class="myQuestionTable">
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
            :highlight-current-row="highLight"
            row-key="id"
            ref="multipleTable"
            @row-dblclick="openDialog"
            border
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
            <el-table-column prop="wtdb" label="我的问题待办数量" show-overflow-tooltip>
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'wtdb'"-->
                <!--                        v-model="scope.row.wtdb" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.wtdb }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="wtbh" label="问题编号" show-overflow-tooltip>
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'wtbh'"-->
                <!--                        v-model="scope.row.wtbh" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.wtbh }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="cjr" label="人员" show-overflow-tooltip>
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'cjr'"-->
                <!--                        v-model="scope.row.cjr" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.cjr }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="cjbm"
              label="部门"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'cjbm'"-->
                <!--                        v-model="scope.row.cjbm" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.cjbm }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="wtmc"
              label="问题名称"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'wtmc'"-->
                <!--                        v-model="scope.row.wtmc" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.wtmc }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="gxh"
              label="工序号"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'gxh'"-->
                <!--                        v-model="scope.row.gxh" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.gxh }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="cjsj"
              label="问题创建时间"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'cjsj'"-->
                <!--                        v-model="scope.row.cjsj" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.cjsj }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="scddh"
              label="生产订单号"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'scddh'"-->
                <!--                        v-model="scope.row.scddh" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.scddh }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="cpxh"
              label="产品型号"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'cpxh'"-->
                <!--                        v-model="scope.row.cpxh" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.cpxh }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="jh"
              label="件号"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'cpxh'"-->
                <!--                        v-model="scope.row.cpxh" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.cpxh }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="wtly"
              label="问题来源"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'wtly'"-->
                <!--                        v-model="scope.row.wtly" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.wtly }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="wtlb"
              label="问题类别"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'wtlb'"-->
                <!--                        v-model="scope.row.wtlb" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.wtlb }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="wtxl"
              label="问题细类"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'wtxl'"-->
                <!--                        v-model="scope.row.wtxl" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.wtxl }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="wtms"
              label="问题描述"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'wtms'"-->
                <!--                        v-model="scope.row.wtms" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.wtms }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="yzrbm"
              label="阅知人部门"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'yzrbm'"-->
                <!--                        v-model="scope.row.yzrbm" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.yzrbm }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="yzr"
              label="阅知人"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'yzr'"-->
                <!--                        v-model="scope.row.yzr" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.yzr }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="zycd"
              label="重要程度"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'zycd'"-->
                <!--                        v-model="scope.row.zycd" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.zycd }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="xwjjsj"
              label="希望解决时间"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'xwjjsj'"-->
                <!--                        v-model="scope.row.xwjjsj" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.xwjjsj }}</span>
              </template>
            </el-table-column>

            <el-table-column
              prop="dqzrr"
              label="主责任人"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'sjzrr'"-->
                <!--                        v-model="scope.row.sjzrr" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.dqzrr }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="wtzt"
              label="问题状态"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'wtzt'"-->
                <!--                        v-model="scope.row.wtzt" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.wtzt }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="jssj"
              label="接收时间"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'jssj'"-->
                <!--                        v-model="scope.row.jssj" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.jssj }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="gzxt"
              label="是否跟踪协同"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'gzxt'"-->
                <!--                        v-model="scope.row.gzxt" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.gzxt === 'true' ? '是' : '否' }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="gbsj"
              label="关闭时间"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'gbsj'"-->
                <!--                        v-model="scope.row.gbsj" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.gbsj }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="lxfk"
              label="是否例行反馈"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'lxfk'"-->
                <!--                        v-model="scope.row.lxfk" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.lxfk }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="pc"
              label="批次"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'pc'"-->
                <!--                        v-model="scope.row.pc" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.pc }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="sb"
              label="设备"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'sb'"-->
                <!--                        v-model="scope.row.sb" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.sb }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="xcphwt"
              label="是否现场配合问题"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'xcphwt'"-->
                <!--                        v-model="scope.row.xcphwt" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.xcphwt === 'true' ? '是' : '否' }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="ldps"
              label="是否领导批示"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'ldps'"-->
                <!--                        v-model="scope.row.ldps" @blur="inputBlur(scope)"></el-input>-->
                <span>{{ scope.row.ldps === 'true' ? '是' : '否' }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="scfj"
              label="是否上传附件"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!--              <el-input v-if="editing && scope.$index === editingIndex && editingProperty === 'scfj'"-->
                <!--                        v-model="scope.row.scfj" @blur="inputBlur(scope)"></el-input>-->
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
    <el-dialog class="dialogRad" title="创建问题" width="700px" :close-on-click-modal="false"
               @close="createQuestionCloseButten"
               v-if="dialogTableVisible"
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
    <!-- 1090   1390-->
    <el-dialog class="dialog dialogRad" @close="shuaxin" :visible.sync="open" :width="withd"
               style="padding-top: 0;padding-bottom: 0;margin-top:1px;" append-to-body>
      <div class="app-container indexHeight">
        <el-card shadow="always" class="box-card"
                 style="background: linear-gradient(to bottom, rgba(7, 129, 230,0.52), rgba(7, 129, 230,1));width: 100%;border-radius: 30px">
          <div style="line-height: 15px;">
            <label style="font-size: 18px">回复/预览</label>
            <el-button style="margin-left: 10px" type="warning" size="small" @click="open=false">退出</el-button>
            <el-button style="margin-left: 10px" size="small" @click="dialogReload">刷新</el-button>
            <el-button style="background-color: red" v-if="queryParams.radios==='待关闭'" type="warning" size="small"
                       @click="applyForClosure(closureID)">关闭
            </el-button>
            <div style="display: inline-block;text-align: center;margin-left: 240px;font-size: 15px">
              <div><label>未完成天数</label></div>
              <div><label>{{ wwcts === 'NAN天' ? '0天' : wwcts }}</label></div>
            </div>
            <div style="display: inline-block;text-align: center;margin-left: 120px;font-size: 15px">
              <div><label>超期天数</label></div>
              <div><label>{{ cqts === 'NAN天' ? '0天' : cqts }}</label></div>
            </div>
            <div style="display: inline-block;text-align: center;margin-left: 140px;font-size: 15px">
              <div><label>完成天数</label></div>
              <div><label>{{ wcts === 'NAN天' ? '0天' : wcts }}</label></div>
            </div>
          </div>
        </el-card>
        <el-container>
          <el-aside width="300px">
            <div>
              <el-card shadow="always" class="box-card"
                       style="background: linear-gradient(to bottom, #0781E6, #7ED4D9);width: 300px;border-radius: 20px">
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
                       style="background: linear-gradient(to bottom, #0781E6, #7ED4D9);width: 300px;border-radius: 20px">
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
                       style="background: linear-gradient(to bottom, rgba(7, 129, 230,1), #93bec1);width: 300px;border-radius: 20px;border-radius: 20px">
                <el-table
                  :data="zrrList"
                  height="300px"
                  max-height="300px"
                  border
                  size="mini"
                  style="background: linear-gradient(to bottom, #0781E6, #7ED4D9);width: 100%">
                  <el-table-column width="150px" label="操作" v-if="queryParams.radios!=='已关闭'">
                    <template slot-scope="scope">
                      <el-button @click="bihuanButton(scope.row)" style="font-size: 13px;background-color: #ffba00"
                                 size="small">闭环
                      </el-button>
                      <el-button @click="jihuoButton(scope.row)" size="small"
                                 style="font-size: 13px;background-color: #ffba00">激活
                      </el-button>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="zrrzt"
                    label="责任人状态"
                    width="100px"
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
                       style="background: linear-gradient(to bottom, #0781E6, #7ED4D9);width: 750px;border-radius: 20px">
                <div style="margin-bottom: 8px">
                  <label style="font-size: 18px">问题名称:</label>
                  <label style="font-size: 18px">{{ closureID.wtmc }}</label>
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
                       style="background: linear-gradient(to bottom, #0781E6, #7ED4D9);width: 750px;border-radius: 20px">
                <label style="font-size: 16px;background-image: linear-gradient(90deg, #ff1515, #021fd6);
                  -webkit-background-clip: text; -webkit-text-fill-color: transparent"
                       v-if="this.closureID.lxfk==='例行反馈'">例行反馈</label>
                <label style="font-size: 16px;background-image: linear-gradient(90deg, #ff1515, #021fd6);
                  -webkit-background-clip: text; -webkit-text-fill-color: transparent" v-else>业务交互</label>
                <el-checkbox class="fj fjyl" v-model="fujian" @change="fujianyulan"
                             style="margin-left: 25px;color: white">
                  附件预览
                </el-checkbox>
                <el-button style="margin-left: 25px;background-color: #15f901" size="mini" @click="handleUpload('弹窗')">
                  上传附件
                </el-button>
                <el-button v-if="this.closureID.lxfk==='例行反馈'" style="margin-left: 25px;background-color: #ffba00"
                           size="mini"
                           @click="lixingfankuiDialogMethod">例行反馈
                </el-button>
                <el-button v-else style="margin-left: 25px;background-color: #ffba00" size="mini"
                           @click="huifuDialogMethod">回复
                </el-button>
                <!--                <el-button style="margin-left: 55px" @click="lingdaopishiDialogMethod" size="mini" type="danger">领导批示</el-button>-->
              </el-card>
              <el-card shadow="always" class="box-card"
                       style="width: 750px;height:400px;overflow-y: auto;white-space:normal;overflow-x: scroll; border-radius: 20px">
                <div style="width: 1000px" v-for="item in jhsjList" :key="item.xh">
                  <div style="display: inline-block" v-if="item.hffj">
                    <i class="el-icon-upload"></i>
                  </div>
                  <div class="chatName" v-text="item.hfr"></div>
                  <div style="vertical-align: top;display: inline-block">
                    <div @contextmenu="showContextMenu($event,item)" @click="huifuyangshione(item.xh)"
                         :class="{ 'clicked': currentDivIndex === item.xh }"
                         @dblclick="huifuyangshi(item.ejhfppyj,item.xh,islxfk)" class="chatBox chatBox-left"
                         style="word-wrap: break-word;max-width: 375px;white-space: normal;"
                         v-text="item.hfxx"></div>
                    <div class="chatTime"><span
                      style="font-size: 14px;vertical-align: bottom;margin-left: 3px">{{ item.wtcjsj }}</span></div>
                  </div>
                  <div v-if="item.sjjhs.length>0">
                    <div style="margin-left: 60px" v-for="item in item.sjjhs" :key="item.xh">
                      <div style="display: inline-block" v-if="item.hffj">
                        <i class="el-icon-upload"></i>
                      </div>
                      <div class="chatName" v-text="item.hfr"></div>
                      <div style="vertical-align: top;display: inline-block">
                        <div @contextmenu="showContextMenu($event,item)" @click="huifuyangshione(item.xh)"
                             :class="{ 'clicked': currentDivIndex === item.xh }"
                             @dblclick="huifuyangshi(item.ejhfppyj,item.xh,islxfk)" class="chatBox chatBox-left"
                             style="word-wrap: break-word;max-width: 375px;white-space: normal;"
                             v-text="item.hfxx"></div>
                        <div class="chatTime"><span
                          style="font-size: 14px;vertical-align: bottom;margin-left: 3px">{{ item.wtcjsj }}</span></div>
                      </div>
                      <div v-if="item.sjjhs.length>0">
                        <div style="margin-left: 60px" v-for="item in item.sjjhs" :key="item.xh">
                          <div style="display: inline-block" v-if="item.hffj">
                            <i class="el-icon-upload"></i>
                          </div>
                          <div class="chatName" v-text="item.hfr"></div>
                          <div style="vertical-align: top;display: inline-block">
                            <div @contextmenu="showContextMenu($event,item)" @click="huifuyangshione(item.xh)"
                                 :class="{ 'clicked': currentDivIndex === item.xh }"
                                 @dblclick="huifuyangshi(item.ejhfppyj,item.xh,islxfk)" class="chatBox chatBox-left"
                                 style="word-wrap: break-word;max-width: 375px;white-space: normal;"
                                 v-text="item.hfxx"></div>
                            <div class="chatTime"><span style="font-size: 14px;vertical-align: bottom;margin-left: 3px">{{
                                item.wtcjsj
                              }}</span></div>
                          </div>
                          <div v-if="item.sjjhs.length>0">
                            <div style="margin-left: 60px" v-for="item in item.sjjhs" :key="item.xh">
                              <div style="display: inline-block" v-if="item.hffj">
                                <i class="el-icon-upload"></i>
                              </div>
                              <div class="chatName" v-text="item.hfr"></div>
                              <div style="vertical-align: top;display: inline-block">
                                <div @contextmenu="showContextMenu($event,item)" @click="huifuyangshione(item.xh)"
                                     :class="{ 'clicked': currentDivIndex === item.xh }"
                                     @dblclick="huifuyangshi(item.ejhfppyj,item.xh,islxfk)" class="chatBox chatBox-left"
                                     style="word-wrap: break-word;max-width: 375px;white-space: normal;"
                                     v-text="item.hfxx"></div>
                                <div class="chatTime"><span
                                  style="font-size: 14px;vertical-align: bottom;margin-left: 3px">{{
                                    item.wtcjsj
                                  }}</span></div>
                              </div>
                              <div v-if="item.sjjhs.length>0">
                                <div style="margin-left: 60px" v-for="item in item.sjjhs" :key="item.xh">
                                  <div style="display: inline-block" v-if="item.hffj">
                                    <i class="el-icon-upload"></i>
                                  </div>
                                  <div class="chatName" v-text="item.hfr"></div>
                                  <div style="vertical-align: top;display: inline-block">
                                    <div @contextmenu="showContextMenu($event,item)" @click="huifuyangshione(item.xh)"
                                         :class="{ 'clicked': currentDivIndex === item.xh }"
                                         class="chatBox chatBox-left"
                                         style="word-wrap: break-word;max-width: 375px;white-space: normal;"
                                         v-text="item.hfxx"></div>
                                    <div class="chatTime"><span
                                      style="font-size: 14px;vertical-align: bottom;margin-left: 3px">{{
                                        item.wtcjsj
                                      }}</span></div>
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
              <div v-show="isContextMenuVisible" class="context-menu"
                   :style="{ top: contextMenuPosition.y + 'px', left: contextMenuPosition.x + 'px' }">
                <!-- 菜单内容 -->
                <div class="menu-item" @click="handleUpload('弹窗',itemJhjl)">上传附件</div>
                <div class="menu-item" @click="deleteJhjl">删除</div>
              </div>
            </div>
            <div v-show="isShow" style="display: inline-block;float: right;">
              <el-card v-show="isShow" shadow="always" class="box-card cards"
                       style="width: 300px;height: 695px;border-radius: 20px">
                <el-card v-show="isShow" shadow="always" class="box-card cards tablec"
                         style="background: linear-gradient(to bottom, #0781E6, #7ED4D9);width: 300px;height: 345px;border-radius: 20px">
                  <div style="margin-top: 16px">
                    <label style="margin-left: 100px;font-size: 18px;">问题附件预览</label>
                  </div>
                  <el-table
                    :data="fileList"
                    @row-dblclick="downLoadFile"
                    border
                    size="mini"
                    max-height="250px"
                    style="width: 100%;margin-top: 26px;">
                    <el-table-column
                      prop="cjr"
                      label="人员"
                      show-overflow-tooltip
                    >
                    </el-table-column>
                    <el-table-column
                      prop="wjmc"
                      label="文件名称"
                      show-overflow-tooltip
                    >
                    </el-table-column>
                    <el-table-column
                      prop="scsj"
                      label="上传时间"
                      show-overflow-tooltip
                    >
                    </el-table-column>
                  </el-table>
                </el-card>

                <el-card v-show="isShow" shadow="always" class="box-card cards tablec"
                         style="background: linear-gradient(to bottom, #0781E6, #7ED4D9);width: 300px;height: 345px;border-radius: 20px">
                  <div style="margin-top: 10px">
                    <label style="margin-left: 100px;font-size: 18px;">回复附件预览</label>
                  </div>
                  <el-table
                    :data="jhjlFileList"
                    border
                    size="mini"
                    max-height="250px"
                    @row-dblclick="downLoadFile"
                    style="width: 100%;margin-top: 26px">
                    <el-table-column
                      prop="cjr"
                      label="人员"
                      show-overflow-tooltip
                    >
                    </el-table-column>
                    <el-table-column
                      prop="wjmc"
                      label="文件名称"
                      show-overflow-tooltip
                    >
                    </el-table-column>
                    <el-table-column
                      prop="scsj"
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
                       style="white-space:nowrap;overflow-x: scroll;height: 695px;overflow-y: auto;background: linear-gradient(to bottom, #0781E6, #7ED4D9);width: 300px;border-radius: 20px">
                <div style="margin-top: 20px;margin-bottom: 20px">
                  <label style="margin-left: 115px;font-size: 18px;">领导批示</label>
                </div>
                <div v-for="item in ldpiList">
                  <div class="chatName" v-text="item.hfr"></div>
                  <div style="vertical-align: top;display: inline-block">
                    <div
                         @click="huifuyangshione(item.xh)"
                         :class="{ 'clicked': currentDivIndex === item.xh }"
                         class="chatBox chatBox-left"
                         v-text="item.hfxx"
                         style="word-wrap: break-word;max-width: 200px;white-space: normal"></div>
                    <div class="chatTime"><span
                      style="font-size: 14px;vertical-align: bottom;margin-left: 3px">{{ item.wtcjsj }}</span></div>
                  </div>
                </div>
              </el-card>
            </div>
          </el-main>
        </el-container>
      </div>
    </el-dialog>
    <!--  回复按钮的弹出框  -->
    <el-dialog
      class="buttonDialog dialogRad"
      title="回复"
      @open="dialogOpened"
      v-if="huifuDialog"
      :visible.sync="huifuDialog"
      @close="huifuId = ''"
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
        <el-button @click="huifuDialog=false,huifuId = ''">取 消</el-button>
        <el-button type="primary" @click.once="huifuSubmit(ejhfppyj,huifuId,'回复','1')">确 定</el-button>
      </span>
    </el-dialog>
    <!--  例行反馈的按钮的弹出框  -->
    <el-dialog
      class="buttonDialog dialogRad"
      @open="dialogLXFKOpened"
      style="margin-top: 150px"
      title="例行反馈"
      v-if="lixingfankuiDialog"
      :visible.sync="lixingfankuiDialog"
      @close="huifuId = ''"
      width="30%">
      <el-input
        ref="lixingfankuiInput"
        type="textarea"
        :rows="5"
        placeholder="请输入内容"
        v-model="huifuTest">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="lixingfankuiDialog = false,huifuId = ''">取 消</el-button>
        <el-button type="primary" @click.once="huifuSubmit(ejhfppyj,huifuId,'例行反馈','1')">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      @open="dialogLDPSOpened"
      class="buttonDialog dialogRad"
      title="领导批示"
      v-if="lingdaopishiDialog"
      :visible.sync="lingdaopishiDialog"
      @close="huifuId = ''"
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
        <el-button @click="lingdaopishiDialog = false,huifuId = ''">取 消</el-button>
        <el-button type="primary" @click.once="huifuSubmit(ejhfppyj,huifuId,'领导批示','1')">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog class="dialogRad" title="选择答案" v-if="xuanzedaan" :visible.sync="xuanzedaan" @open="clearSelect">
      <el-table
        ref="daanTable"
        @selection-change="handleChangeSelection"
        :data="huifuxinxiList"
        row-key="xh"
        border
      >
        <el-table-column show-overflow-tooltip type="selection" width="50" :reserve-selection="true"></el-table-column>
        <el-table-column show-overflow-tooltip prop="hfr" width="100px" label="人员"></el-table-column>
        <el-table-column show-overflow-tooltip prop="hfxx" label="回复信息"></el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="xuanzedaan = false">取 消</el-button>
        <el-button type="primary" @click="confirmAnswer(closureID)">确 定</el-button>
      </span>
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
    <!--  上传问题的弹出框  -->
    <el-dialog v-if="openScfj" @close="reload" class="xcssjyk" append-to-body title="上传附件" :visible.sync="openScfj"
               width="40%" append-to-body>
      <fj v-if="openScfj" :fileList="fileList" :row="selectFj"></fj>
    </el-dialog>
  </div>
</template>

<script>
import {
  getwtbhMethod, getjjcdMethod, getwtxlMethod,
  getwtlyMethod, getwtlbMethod, selectWtxxData,
  createQuestion, deleteQuestions, updateQuestions,
  updateQuestionsStatus, getJhjl, getLDPS,
  selectJhjlList, answerSelectOff, updateQuestionStatus,
  getzerData, saveJhjlList, closedLoop, activate,
  deleteJhjlByXh, updateQuestionWTDBToZero, updateQuestionWddb, getJhjlByWtid,
} from "@/api/question/question";
import {addCjls, updateDaccToRd} from "@/api/question/upQuestion";
import deptTreeSelect from "@/views/question/myquestion/deptTreeSelect"
import zdhf from "@/views/question/WTGL_ZDHF/index";
import xiangxixinxi from "@/views/question/myquestion/xiangxixinxi";
import {listById, getFjByIds} from "@/api/fj/fj";
import fj from "@/views/fj/fj";

export default {
  name: 'Myquestion',
  components: {
    deptTreeSelect, zdhf, xiangxixinxi, fj
  },
  computed: {
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
    }
  },
  data() {
    return {
      fileList: [],//文件的集合
      jhjlFileList: [],//交互记录文件
      openScfj: false,//是否打开上传附件弹出框
      whoFjBut: '',//谁的附件上传按钮
      selectFj: '',//查询附件
      shifoumanyiDialog: false,//是否满意的弹出框
      xiangxiDialog: false,//详细信息是否弹出框
      jhsjListDia: [],//交互数据的数据集合
      wwcts: '',//未完成天数
      cqts: '',//超期天数
      wcts: '',//完成天数
      lishidaanDialog: false,//启用问题处理库的是否弹出框
      selectYzrList: [],//选中的阅知人集合
      selectZrrList: [],//选中的责任人集合
      zrrstr: '',//创建任务弹窗的责任人
      yzrstr: '',//创建任务弹窗的阅知人
      zrbmstr: '',//创建任务弹窗的责任部门
      selectTitle: '',//选择责任人或阅知人弹出框的标题
      treeVisual: false,//弹出选择责任人和阅知人的弹窗

      isone: true,
      updateIds: [],//存储需要修改的id值
      openUpdate: false,//修改的弹出框
      single: true,//多选就不好使
      multiple: true,//多选也好使
      chuansuokuang: false,//穿梭框的弹出框是否显示
      xuanzedaan: false,//选择答案的弹出框是否显示
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
      withd: '1090px',//回复预览弹出框的大小1390
      fujian: false,//附件多选框是否确定
      zrrList: [],//回复预览页面的责任人数据集合
      title: "",
      open: false,//是否打开回复预览页面
      jhsjList: [],//交互数据的数组
      ldpiList: [],//领导批示数据的集合
      huifuxinxiList: [],//显示需要选择的正确答案列表

      options: [
        {
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
        }],//选择可以查询的字段

      isQwfksj: true,//期望解决时间的是否可以写数据
      canceTime: false,//希望解决时间是否可以写数据
      isXcphwt: false,//判断现场配合是否可以写数据
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
      /*点击时间下标*/
      editingIndex: '',
      /*点击事件字段*/
      editingProperty: '',
      /*是否高度变化*/
      highLight: false,
      /*用于失去焦距*/
      editing: false,
      formLabelWidth: '120px',
      dialogTableVisible: false,
      tableData: [],
      total: 200,
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
      handleSelect: [],//录入选中的数据
      handleSelectGB: [],//录入关闭时候选择的数据
      loading: true,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tName: null,
        tCode: null,
        tPid: null,
        tLevel: null,
        radios: '待办',
        userName: null,
        WTID: null,
        JHZT: null,
        type: '',
      },

    }
  },
  methods: {
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
    //给表格的表头设置颜色
    styleFunc({row, column, rowIndex, columnIndex}) {
      if (column.property === 'wtms' || column.property === 'wtmc') {
        return "background:orange";
      }
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
    //历史记录的查寻的方法
    lishidaanchaxun() {
      this.lishidaanDialog = true;
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

    //清除答案选择表格的多选框
    clearSelect() {
      if (this.$refs.daanTable === undefined) {
        return;
      }
      this.handleSelectGB = [];
      this.$refs.daanTable.clearSelection();
    },
    //关闭选择信息的确定按钮
    confirmAnswer(row) {
      if (this.handleSelectGB.length === 0) {
        this.$message.error("请至少选择一条数据")
        return
      }
      this.$confirm('是否确认答案选择项?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        answerSelectOff({
          sjjhDTOS: this.handleSelectGB,
          wtxl: row.wtxl,
          wtlb: row.wtlb,
          gxh: row.gxh,
          wtms: row.wtms,
          jh: row.jh,
          cpxh: row.cpxh,
          zrr: row.dqzrr,
        }).then(res => {
          if (res.code === 200) {
            this.$message.success("答案保存并且关闭成功");
            this.load();
          }
        })
      }).catch(() => {
      });
      this.xuanzedaan = false;
      this.open = false;
    },
    //实现双击弹出回复预览弹出框
    openDialog(row) {
      if (this.editingProperty === 'wtmc' || this.editingProperty === 'wtms') {
        this.computTime(row);
        this.isLdps = false;
        this.isShowLdps = false;
        this.isShow = false;
        this.withd = '1090px';
        this.currentDivIndex = '';
        this.jhsjList = [];
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
        updateQuestionWTDBToZero({id: this.closureID.id}).then(res => {
          if (res.code === 200) {
          }
        });
      }

    },
    //实现右键菜单
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
    //处理菜单项目的删除事件
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
    //回复数据的单击事件
    huifuyangshione(index) {
      this.currentDivIndex = index;
    },
    //回复页面的责任人的闭环和激活按钮
    //闭环按钮
    bihuanButton(row) {
      let data = {xh: row.xh, wtid: row.wtid};
      closedLoop(data).then(res => {
        if (res.code === 200) {
          this.$message.success("责任人闭环成功");
          this.loadzerData();
        } else {
          this.$message.error("责任人闭环失败");
        }
      });
    },
    //激活
    jihuoButton(row) {
      let data = {xh: row.xh, wtid: row.wtid};
      activate(data).then(res => {
        if (res.code === 200) {
          this.$message.success("责任人激活成功");
          updateQuestionStatus({id: row.wtid, value: '反馈'}).then(res => {
            if (res.code === 200) {
              this.load();
            }
          });
          this.loadzerData();
        } else {
          this.$message.error("责任人激活失败");
        }
      });
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
    //获得交互记录的数据
    loadJhjlList(jhzt, str) {
      if (jhzt === '领导批示') {
        getLDPS({WTID: this.closureID.id, JHZT: jhzt}).then(res => {
          if (res.code === 200) {
            this.ldpiList = res.rows;
            if (str !== '刷新') {
              if (this.ldpiList.length > 0) {
                this.withd = '1390px';
                this.isShowLdps = true;
                this.isShow = false;
              } else {
                this.withd = '1090px';
                this.isShowLdps = false;
                this.isShow = false;
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
              console.log(this.jhsjList)
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
    //回复按钮的取消方法
    huifuCancel(id, type) {
      this.huifuDialog = false;
    },
    //回复按钮的确定方法
    huifuSubmit(ejhfppyj, id, type, js) {
      this.huifuDialog = false;
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
      updateQuestionWddb(sjjh).then(res => {
        if (res.code === 200) {
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
      //查询交互记录的数据
      selectJhjlList({ID: this.closureID.id}).then(res => {
        if (res.code === 200) {
          this.huifuxinxiList = res.rows;
        } else {
        }
      });
      //先弹出一个弹出框，并且选择答案。
      this.xuanzedaan = true;
    },
    //判断问题升级，如果问题升级为true，那么希望解决时间变灰
    cancellationTime() {
      if (this.form.wtsj === true) {
        this.canceTime = true;
      } else {
        this.canceTime = false;
      }
    },
    //问题类别改变后执行的方法
    wtlbChange() {
      this.form.wtxl = '';
      this.wtxlMethod();
      let nameList = ['工艺', '调度', '生产', '设备'];
      this.isXcphwt = true;
      for (let string of nameList) {
        if (this.form.wtlb.indexOf(string) !== -1) {
          this.isXcphwt = false;
        }
      }
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
    //主责任人的获得方法
    zcrrMethod() {

    },
    //阅知人的获得方法
    yzrMethod() {

    },
    //问题编号获得方法
    wtbhMethod() {
      getwtbhMethod().then(res => {
        if (res.code === 200) {
          this.form.wtbh = res.msg;
        }
      })
    },
    //创建问题按钮的方法
    createQuestion() {
      this.createQuestionShuaxin();
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
    },
    //按照条件查询数据
    handleQuery() {
      this.load();
    },
    //关闭按钮
    closeQuestion() {
      if (this.handleSelect.length < 1) {
        this.$message.info("请至少选择一条数据");
      } else {
        let ids = [];
        this.handleSelect.forEach(item => {
          const id = {id: '', value: ''};
          id.id = item.id;
          id.value = '已关闭';
          ids.push(id);
        });
        this.$confirm('是否确认关闭数据项?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          updateQuestionsStatus(ids).then(res => {
            if (res.code === 200) {
              this.$message.success("关闭成功");
              this.load();
            }
          })
        }).catch(() => {
        });
      }
    },
    //保存按钮
    saveData() {
      let UpdateList = [];
      this.tableData.forEach(item => {
        if (item.isUpdate === "true") {
          UpdateList.push(item);
        }
      });
      if (UpdateList.length < 1) {
        this.$message.warning("没有修改的数据");
        return;
      }
      updateQuestions(UpdateList).then(res => {
        if (res.code === 200) {
          this.$message.success("修改成功");
          this.load();
        } else {
          this.$message.error("修改失败");
        }
      });
    },
    handleAdd() {
      this.isLdps = false;
      this.isShowLdps = false;
      this.isShow = false;
      this.withd = '1090px';
      this.currentDivIndex = '';
      this.jhsjList = [];
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
      this.computTime(this.closureID);
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
      updateQuestionWTDBToZero({id: this.closureID.id}).then(res => {
        if (res.code === 200) {
        }
      });
    },
    //弹窗内刷新
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
      //判断当前登录用户是否使管理员用户
      if (this.queryParams.radios !== '待办') {
        this.$message.error("当前问题不是待办问题不能被删除。");
        return;
      }

      let num = this.handleSelect.length;
      if (num <= 0) {
        this.$message.error("请至少选择一条数据");
        return
      }
      let ids = [];
      this.handleSelect.forEach(item => {
        ids.push(item.id);
      })
      this.$confirm('是否确认删除数据项?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteQuestions(ids).then(res => {
          if (res.code === 200) {
            this.load();
            this.$message.success("批量删除成功");
          }
        })
      }).catch(() => {
      });
    },
    //刷新按钮
    shuaxin() {
      this.tableData = [];
      this.load();
    },
    //弹出框的确定并提交方法的执行方法
    saveDataDialog() {
      if (!this.isone) {
        return;
      }
      if (this.form.wtms === '') {
        this.$message.error("请输入问题描述的数据");
        return;
      }
      if (this.form.wtmc === '') {
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
          this.createQuestionShuaxin();
          this.isone = true
        });
        return;
      }
      if (this.form.lxfk === true) {
        this.form.lxfk = '例行反馈';
      } else {
        this.form.lxfk = '业务交互';
      }
      if (!this.form.wtsj) {
        if (this.form.xwjjsj === null || this.form.xwjjsj === '') {
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
          this.$message.success("创建问题成功");
          this.dialogTableVisible = false;
          this.createQuestionShuaxin();
          this.load();
          this.isone = true
        } else {
          this.$message.success("创建失败");
          this.dialogTableVisible = false;
          this.createQuestionShuaxin();
          this.isone = true
        }
      });
      this.selectZrrList = []
      this.selectYzrList = []
      this.zrrstr = ''
      this.zrbmstr = ''
      this.yzrstr = ''
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
    //改变单选框执行的方法
    changeRadio() {
      this.load();
    },
    //页面加载完后执行加载的数据
    load() {
      this.loading = true;
      selectWtxxData(this.queryParams).then(res => {
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
      this.editing = true
      this.editingIndex = row.index
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
      this.handleSelect = val;
      this.single = val.length !== 1
      this.multiple = !val.length
    },
    //将关闭的数据存到数组中
    handleChangeSelection(val) {
      this.handleSelectGB = val;
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
    //跟踪协同的多选框数据改变触发的方法
    gzxtwtMethod() {
      if (this.form.gzxt === true) {
        this.isLdps = false;
        this.isLxfk = false;
        this.isWtsj = false;
        this.isXcphwt = true
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
        this.isXcphwt = false
      }
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
    //打开弹出框的焦点事件
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

.tablefirst .el-table__cell {
  height: 40px;
}

.tablefirst .el-table--medium .el-table__cell {
  padding: 0;
}

.fjyl ::v-deep .el-checkbox__input.is-checked + .el-checkbox__label {
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
  margin-top: 5% !important;
}

.el-textarea.is-disabled .el-textarea__inner {
  color: black;
  font-size: 17px;
}

.myQuestionTable .el-table__cell {
  height: 40px;
}

.myQuestionTable .el-table--medium .el-table__cell {
  padding: 0;
}

.dialog .el-table .el-table__header-wrapper th, .el-table .el-table__fixed-header-wrapper th {
  color: black;
}

.dialog .el-aside {
  background-color: transparent;
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

.tablec ::v-deep .el-table th.el-table__cell {
  background-color: rgba(0, 0, 0, 0);
}
</style>
