<template>
  <div>
    <div class="app-container indexHeight dialog">
      <el-card shadow="always"
               style="background: linear-gradient(to bottom, rgba(7, 129, 230,0.52), rgba(7, 129, 230,1));width: 100%;border-radius: 20px">
        <div style="line-height: 15px;position: relative">
          <label style="font-size: 18px">回复/预览</label>
          <el-button style="margin-left: 10px" type="warning" size="small" @click="closeDia">退出</el-button>
          <el-button style="margin-left: 10px" size="small" @click="openDialog">刷新</el-button>
          <el-button style="background-color: red;border-color: red;color: white"
                     v-if="authority.close && (radios==='处理中' || radios==='待关闭')"
                     size="small"
                     @click="applyForClosure">关闭
          </el-button>
          <el-button v-if="authority.shenqingguanbi && (radios==='处理中' || radios==='待关闭')"
                     style="background-color: red" type="primary" size="small"
                     @click="closeQuestion">申请关闭
          </el-button>
          <el-button v-if="authority.wtsj && (radios==='处理中' || radios==='待关闭') && closureID.yjjb != null && closureID.yjjb !== ''"
                     style="background-color: red" type="primary" size="small"
                     @click.once="wtsjOne"><span v-if="closureID.yjjb != null && closureID.yjjb !== ''">问题升级-{{ closureID.yjjb }}</span>
          </el-button>
          <div style="position: absolute;left: 550px;display: inline-block">
            <div style="display: inline-block;text-align: center;margin-right: 80px;font-size: 15px;float: right">
              <div><label>完成天数</label></div>
              <div><label>{{ wcts === 'NAN天' ? '0天' : wcts }}</label></div>
            </div>
            <div style="display: inline-block;text-align: center;margin-right: 80px;font-size: 15px;float: right">
              <div><label>超期天数</label></div>
              <div><label>{{ cqts === 'NAN天' ? '0天' : cqts }}</label></div>
            </div>
            <div style="display: inline-block;text-align: center;margin-right: 80px;font-size: 15px;float: right">
              <div><label>未完成天数</label></div>
              <div><label>{{ wwcts === 'NAN天' ? '0天' : wwcts }}</label></div>
            </div>
          </div>
        </div>
      </el-card>
      <el-container>
        <el-aside width="301px">
          <div>
            <el-card shadow="always"
                     style="background: linear-gradient(to bottom, #0781E6, #7ED4D9);width: 300px;border-radius: 20px;height: 260px;">
              <div style="overflow: auto;height: 225px">
                <div>
                  <label>创建部门:</label>
                  <span style="margin-left: 15px" v-text="closureID.cjbm"></span>
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
                  <span style="margin-left: 5px" v-text="closureID.wtxl"></span>
                </div>
              </div>
            </el-card>
          </div>
          <div>
            <el-card shadow="always"
                     style="background: linear-gradient(to bottom, #0781E6, #7ED4D9);width: 300px;border-radius: 20px;height: 140px;">
              <div style="height: 100px;overflow: auto;">
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
            <el-card shadow="always" class="card tablec"
                     style="background: linear-gradient(to bottom, #2982C3, #93bec1);width: 300px;border-radius: 20px">
              <el-table
                :data="zrrList"
                height="300px"
                max-height="300px"
                border
                size="mini"
                style="background: linear-gradient(to bottom, #0781E6, #7ED4D9);width: 100%">
                <el-table-column v-if="authority.controls && (radios !== '已关闭')" width="150px" label="操作">
                  <template slot-scope="scope">
                    <el-button @click="bihuanButton(scope.row)"
                               style="font-size: 13px;background-color: #ffba00"
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
            <el-card shadow="always"
                     style="background: linear-gradient(to bottom, #0781E6, #7ED4D9);width: 765px;border-radius: 20px">
              <div style="height: 170px;overflow: auto">
                <div style="margin-bottom: 8px">
                  <label style="font-size: 18px">问题名称:</label>
                  <span style="font-size: 18px">{{ closureID.wtmc }}</span>
                </div>
                <div>
                  <label style="font-size: 18px">问题描述:</label>
                  <span style="font-size: 18px">{{ closureID.wtms }}</span>
                  <!--                  <el-input
                                      type="textarea"
                                      :autosize="{ minRows: 4, maxRows: 4}"
                                      style="margin-top: 1px;"
                                      placeholder="请输入内容"
                                      v-model="wtms"
                                      :disabled="true">
                                    </el-input>-->
                </div>
              </div>
            </el-card>
            <el-card shadow="always"
                     style="background: linear-gradient(to bottom, #3c93b9, #00dcff);width: 765px;border-radius: 20px;height: 72px;">
              <div style="display: inline-block;height: 32px;padding-top: 4px">
                <label style="font-size: 16px;background-image: linear-gradient(90deg, #ff1515, #021fd6);
                  -webkit-background-clip: text; -webkit-text-fill-color: transparent"
                       v-if="this.closureID.lxfk==='例行反馈'">例行反馈</label>
                <label style="font-size: 16px;background-image: linear-gradient(90deg, #ff1515, #021fd6);
                  -webkit-background-clip: text; -webkit-text-fill-color: transparent" v-else>业务交互</label>
                <el-checkbox class="fj fjyl" v-model="fujian" @change="fujianyulan"
                             style="margin-left: 25px;color: black">
                  附件预览
                </el-checkbox>
              </div>
              <div style="display: inline-block;height: 32px;" v-if="closureID.wtzt !== '已关闭'">
                <el-button v-if="authority.shangchuangfujian"
                           style="margin-left: 25px;background-color: #15f901"
                           @click="fjylBut('but')"
                           size="mini">上传附件
                </el-button>
                <el-button v-if="this.closureID.lxfk==='例行反馈' && authority.recover"
                           style="margin-left: 25px;background-color: #ffba00"
                           size="mini"
                           @click="lixingfankuiDialogMethod">例行反馈
                </el-button>
                <el-button v-if="this.closureID.lxfk!=='例行反馈' && authority.recover"
                           style="margin-left: 25px;background-color: #ffba00" size="mini"
                           @click="huifuDialogMethod">回复
                </el-button>
                <el-button v-if="authority.ldps"
                           style="margin-left: 55px;background-color: #f62e2e;border-color: white;color: white"
                           @click="lingdaopishiDialogMethod"
                           size="mini">领导批示
                </el-button>
              </div>

            </el-card>
            <el-card shadow="always"
                     style="width: 765px;height:417px;overflow: auto;white-space:normal; border-radius: 20px">
              <div v-for="item in jhsjList" :key="item.xh">
                <div style="display: inline-block" v-if="item.hffj==='是'">
                  <i class="el-icon-upload"></i></div>
                <div class="chatName" v-text="item.hfr"></div>
                <div style="vertical-align: top;display: inline-block">
                  <div @contextmenu="showContextMenu($event,item)"
                       @click="huifuyangshione(item.xh)"
                       :class="{ 'clicked': currentDivIndex === item.xh }"
                       @dblclick="huifuyangshi(item.ejhfppyj,item.xh,islxfk)"
                       class="chatBox chatBox-left"
                       v-text="item.hfxx"
                       style="word-wrap: break-word;max-width: 375px;white-space: normal;"></div>
                  <div class="chatTime"><span
                    style="font-size: 14px;vertical-align: bottom;margin-left: 3px">{{ item.wtcjsj }}</span>
                  </div>
                </div>
                <div v-if="item.sjjhs.length>0">
                  <div style="margin-left: 40px" v-for="item in item.sjjhs" :key="item.xh">
                    <div style="display: inline-block" v-if="item.hffj==='是'">
                      <i class="el-icon-upload"></i></div>
                    <div class="chatName" v-text="item.hfr"></div>
                    <div style="vertical-align: top;display: inline-block">
                      <div @contextmenu="showContextMenu($event,item)"
                           @click="huifuyangshione(item.xh)"
                           :class="{ 'clicked': currentDivIndex === item.xh }"
                           @dblclick="huifuyangshi(item.ejhfppyj,item.xh,islxfk)"
                           class="chatBox chatBox-left"
                           v-text="item.hfxx"
                           style="word-wrap: break-word;max-width: 375px;white-space: normal;"></div>
                      <div class="chatTime"><span
                        style="font-size: 14px;vertical-align: bottom;margin-left: 3px">{{ item.wtcjsj }}</span>
                      </div>
                    </div>
                    <div v-if="item.sjjhs.length>0">
                      <div style="margin-left: 40px" v-for="item in item.sjjhs" :key="item.xh">
                        <div style="display: inline-block" v-if="item.hffj==='是'">
                          <i class="el-icon-upload"></i></div>
                        <div class="chatName" v-text="item.hfr"></div>
                        <div style="vertical-align: top;display: inline-block">
                          <div @contextmenu="showContextMenu($event,item)"
                               @click="huifuyangshione(item.xh)"
                               :class="{ 'clicked': currentDivIndex === item.xh }"
                               @dblclick="huifuyangshi(item.ejhfppyj,item.xh,islxfk)"
                               class="chatBox chatBox-left"
                               v-text="item.hfxx"
                               style="word-wrap: break-word;max-width: 375px;white-space: normal;"></div>
                          <div class="chatTime"><span
                            style="font-size: 14px;vertical-align: bottom;margin-left: 3px">{{ item.wtcjsj }}</span>
                          </div>
                        </div>
                        <div v-if="item.sjjhs.length>0">
                          <div style="margin-left: 40px" v-for="item in item.sjjhs" :key="item.xh">
                            <div style="display: inline-block" v-if="item.hffj==='是'">
                              <i class="el-icon-upload"></i></div>
                            <div class="chatName" v-text="item.hfr"></div>
                            <div style="vertical-align: top;display: inline-block">
                              <div @contextmenu="showContextMenu($event,item)"
                                   @click="huifuyangshione(item.xh)"
                                   :class="{ 'clicked': currentDivIndex === item.xh }"
                                   @dblclick="huifuyangshi(item.ejhfppyj,item.xh,islxfk)"
                                   class="chatBox chatBox-left"
                                   v-text="item.hfxx"
                                   style="word-wrap: break-word;max-width: 375px;white-space: normal;"></div>
                              <div class="chatTime"><span
                                style="font-size: 14px;vertical-align: bottom;margin-left: 3px">{{ item.wtcjsj }}</span>
                              </div>
                            </div>
                            <div v-if="item.sjjhs.length>0">
                              <div style="margin-left: 40px" v-for="item in item.sjjhs" :key="item.xh">
                                <div style="display: inline-block" v-if="item.hffj==='是'">
                                  <i class="el-icon-upload"></i></div>
                                <div class="chatName" v-text="item.hfr"></div>
                                <div style="vertical-align: top;display: inline-block">
                                  <div @contextmenu="showContextMenu($event,item)"
                                       @click="huifuyangshione(item.xh)"
                                       :class="{ 'clicked': currentDivIndex === item.xh }"
                                       class="chatBox chatBox-left"
                                       v-text="item.hfxx"
                                       style="word-wrap: break-word;max-width: 375px;white-space: normal;"></div>
                                  <div class="chatTime"><span
                                    style="font-size: 14px;vertical-align: bottom;margin-left: 3px">{{
                                      item.wtcjsj
                                    }}</span>
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
              </div>
            </el-card>
            <div v-show="isContextMenuVisible" class="context-menu"
                 v-if="(closureID.wtzt !== '已关闭' && this.authority.recover)
                        &&(itemJhjl.jhzt !== '领导批示' || itemJhjl.hfrid === userInfo.userId+'' || guanliyuan)"
                 :style="{ top: contextMenuPosition.y + 'px', left: contextMenuPosition.x + 'px',zIndex: 10}">
              <!-- 菜单内容 -->
              <div style="border-color: transparent">
                <el-button v-if="itemJhjl.jhzt !== '领导批示'" type="text" icon="el-icon-upload"
                           @click="fjylBut('dia')">附件预览
                </el-button>
                <br v-if="itemJhjl.jhzt !== '领导批示'">
                <el-button type="text" icon="el-icon-delete"
                           v-if="itemJhjl.hfrid === userInfo.userId+'' || guanliyuan"
                           @click="deleteJhjl">删除
                </el-button>
              </div>
            </div>
          </div>
          <div v-show="isShow" style="display: inline-block;float: right;">
            <el-card v-show="isShow" shadow="always" class="cards"
                     style="width: 300px;height: 695px;border-radius: 20px">
              <el-card v-show="isShow" shadow="always" class="cards tablec"
                       style="background: linear-gradient(to bottom, #0781E6, #7ED4D9);width: 300px;height: 345px;border-radius: 20px">
                <div style="margin-top: 16px">
                  <label style="margin-left: 100px;font-size: 18px;">问题附件预览</label>
                </div>
                <el-table
                  @row-dblclick="downLoadFj"
                  :data="wtFjylList"
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
                  <el-table-column label="操作" width="55px" align="center"
                                   class-name="small-padding fixed-width">
                    <template slot-scope="scope">
                      <el-button v-if="scope.row.cjrid === userInfo.userId+'' || guanliyuan" size="mini"
                                 type="text" @click="fjDelete(scope.row)"><span
                        style="color:black;">删除</span>
                      </el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </el-card>

              <el-card v-show="isShow" shadow="always" class="cards tablec"
                       style="background: linear-gradient(to bottom, #0781E6, #7ED4D9);width: 300px;height: 345px;border-radius: 20px">
                <div style="margin-top: 16px">
                  <label style="margin-left: 100px;font-size: 18px;">回复附件预览</label>
                </div>
                <el-table
                  @row-dblclick="downLoadFj"
                  :data="fileData"
                  border
                  size="mini"
                  max-height="250px"
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
                  <el-table-column label="操作" width="55px" align="center"
                                   class-name="small-padding fixed-width">
                    <template slot-scope="scope">
                      <el-button v-if="scope.row.cjrid === userInfo.userId.toString() || guanliyuan" size="mini"
                                 type="text" @click="fjDelete(scope.row)"><span
                        style="color:black;">删除</span>
                      </el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </el-card>
            </el-card>
          </div>
          <div v-show="isShowLdps" style="width: 300px;display: inline-block;float: right;height: 700px;">
            <el-card v-show="isShowLdps" shadow="always" class="cards tablec"
                     style="white-space:nowrap;height: 695px;border-radius: 20px;background: linear-gradient(to bottom, #0781E6, #7ED4D9)">
              <div style="margin-top: 20px;margin-bottom: 20px">
                <label style="margin-left: 115px;font-size: 18px;">领导批示</label>
              </div>
              <div style="height: 635px;width: 298px;overflow: auto">
                <div v-for="item in ldpiList" :key="item.xh">
                  <div class="chatName" v-text="item.hfr"></div>
                  <div style="vertical-align: top;display: inline-block">
                    <div @click="huifuyangshione(item.xh)"
                         @contextmenu="showContextMenu($event,item)"
                         :class="{ 'clicked': currentDivIndex === item.xh }"
                         class="chatBox chatBox-left"
                         v-text="item.hfxx"
                         style="word-wrap: break-word;max-width: 200px;white-space: normal"></div>
                    <div class="chatTime"><span
                      style="font-size: 14px;vertical-align: bottom;margin-left: 3px">{{ item.wtcjsj }}</span></div>
                  </div>
                </div>
              </div>
            </el-card>
          </div>
        </el-main>
      </el-container>
    </div>

    <!--  选择答案弹框  -->
    <el-dialog append-to-body v-if="xuanzedaan" class="dialogRad" :close-on-click-modal="false" title="选择答案"
               :visible.sync="xuanzedaan">
      <el-table height="600px" ref="daanTable" @selection-change="handleChangeSelection"
                :data="huifuxinxiList" row-key="xh" border>
        <el-table-column show-overflow-tooltip type="selection" width="50" :reserve-selection="true"></el-table-column>
        <el-table-column show-overflow-tooltip prop="hfr" width="100px" label="人员"></el-table-column>
        <el-table-column show-overflow-tooltip prop="hfxx" label="回复信息"></el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="xuanzedaan = false,huifuId = '',huifuTest = ''">取 消</el-button>
        <el-button type="primary" @click="confirmAnswer(closureID)">确 定</el-button>
      </span>
    </el-dialog>

    <!--  回复按钮的弹出框  -->
    <el-dialog append-to-body :close-on-click-modal="false" class="huifuDialog" title="回复"
               @open="dialogOpened" :visible.sync="huifuDialog" @close="huifuClose" width="500px">
      <el-input ref="huifuInput" type="textarea" :rows="5" maxlength="300" show-word-limit
                style="margin-top: 5px" placeholder="请输入内容" v-model="huifuTest">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="huifuDialog=false,huifuId = '',huifuTest = ''">取 消</el-button>
        <el-button type="primary" @click="huifuSubmit(ejhfppyj,huifuId,'回复','1')">确 定</el-button>
      </span>
    </el-dialog>

    <!--  例行反馈的按钮的弹出框  -->
    <el-dialog append-to-body :close-on-click-modal="false" @open="dialogLXFKOpened" title="例行反馈"
               :visible.sync="lixingfankuiDialog" @close="huifuClose" v-if="lixingfankuiDialog"
               class="huifuDialog" width="500px">
      <el-input ref="lixingfankuiInput" type="textarea" :rows="5" maxlength="300" show-word-limit
                placeholder="请输入内容" v-model="huifuTest">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="lixingfankuiDialog = false,huifuId = '',huifuTest = ''">取 消</el-button>
        <el-button type="primary" @click="huifuSubmit(ejhfppyj,huifuId,'例行反馈','1')">确 定</el-button>
      </span>
    </el-dialog>

    <!--  领导批示的按钮的弹出框  -->
    <el-dialog append-to-body :close-on-click-modal="false" @open="dialogLDPSOpened" class="huifuDialog"
               title="领导批示" :visible.sync="lingdaopishiDialog" @close="huifuClose"
               v-if="lingdaopishiDialog" width="500px">
      <el-input ref="lingdaoInput" type="textarea" :rows="5" maxlength="300" show-word-limit
                style="margin-top: 5px;" placeholder="请输入内容" v-model="huifuTest">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="lingdaopishiDialog = false,huifuId = '',huifuTest = ''">取 消</el-button>
        <el-button type="primary" @click="huifuSubmit(ejhfppyj,huifuId,'领导批示','1')">确 定</el-button>
      </span>
    </el-dialog>

    <!--  附件预览弹出框  -->
    <el-dialog append-to-body v-if="fjylDialog" @close="fjylClose" class="fjylDialog" append-to-body title="附件预览" center
               :visible.sync="fjylDialog" width="800px">
      <fj :row="fujianDiaData"></fj>
    </el-dialog>
  </div>
</template>

<script>

import {
  updateQuestionsStatus, getJhjl, getLDPS,
  selectJhjlList, answerSelectOff, updateQuestionStatus,
  getzerData, saveJhjlList, closedLoop, activate,
  deleteJhjlByXh, updateQuestionWddb, updateMyDoListStatus,
} from "@/api/question/question";
import {delFj, selectFjListByIds} from "@/api/fj/fj";
import fj from "@/views/fj/fj";
import {getUserInfo} from "@/api/question/wtlbry";

export default {
  name: "huifuyulan",
  components: {fj},
  props: ['closureID', 'radios', 'authority'],
  data() {
    return {
      guanliyuan: false,//是否管理员
      userInfo: {},
      isLdps: true,//判断领导批示能否写如数据
      isShow: false,//判断附件预览是否展示
      jhsjList: [],//交互数据的数组
      fujian: false,//附件多选框是否确定
      islxfk: false,//是否是例行反馈
      wtms: this.closureID.wtms,//问题描述不能修改
      isShowLdps: false,//判断是否有领导批示
      wwcts: '',//未完成天数
      cqts: '',//超期天数
      wcts: '',//完成天数
      ldpiList: [],//领导批示数据的集合
      zrrList: [],//回复预览页面的责任人数据集合
      wtFjylList: [],//文件的集合
      fileData: [],//交互记录文件
      itemJhjl: {},//单击回复数据的当前值

      isContextMenuVisible: false,//是否显示菜单
      contextMenuPosition: {x: 0, y: 0},//实现右键菜单的
      currentDivIndex: null,//实现单击颜色变化的

      handleSelectGB: [],//录入关闭时候选择的数据
      huifuxinxiList: [],//显示需要选择的正确答案列表
      xuanzedaan: false,//选择答案的弹出框是否显示
      lingdaopishiDialog: false,//领导批示按钮的弹出框的是否显示
      lixingfankuiDialog: false,//例行反馈按钮的弹出框的是否显示
      huifuDialog: false,//回复按钮的弹出框的是否显示
      huifuTest: '',//回复弹出框的输入框
      huifuId: '',//存上级的id值
      ejhfppyj: '',//存下级的所有id
      //回复预览框需要的的属性
      fjylDialog: false,//是否打开上传附件弹出框
      fujianDiaList: [],//文件的集合
      fujianDiaData: '',//查询附件
      submitOne: false,//单击
    }
  },
  mounted() {
    this.getUserInfo()
    this.openDialog()
  },
  beforeDestroy() {
    document.removeEventListener('click', this.handlePageClick);
  },
  methods: {
    //获取用户信息
    getUserInfo() {
      getUserInfo().then(response => {
        this.userInfo = response.data
        this.userInfo.user.roles.forEach(value => {
          if (value.roleName.includes('管理员')) {
            this.guanliyuan = true
          }
        })
      })
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
    },
    //回复按钮的确定方法
    huifuSubmit(ejhfppyj, id, type, js) {
      if (this.submitOne) {
        return;
      }
      if (this.huifuTest.trim() == null || this.huifuTest.trim() === '') {
        this.$message.error('请输入内容')
        return;
      }
      this.submitOne = true

      let sjjh = {
        value: this.closureID.wtzt,
        hfxx: this.huifuTest.trim(),
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
            setTimeout(() => {
              this.submitOne = false
            }, 250)
            this.loadJhjlList('领导批示');
          } else {
            this.$message.error("回复失败，请查看是否服务器错误，或者重复提交");
          }
        });
        this.huifuId = '';
        this.huifuTest = ''
        return;
      }
      if (this.authority.dbgz && this.closureID.cjrid !== this.userInfo.userId+'') {
        sjjh.userName = '1'
      }
      if (id === null || id === '') {
        saveJhjlList(sjjh).then(res => {
          if (res.code === 200) {
            this.$message.success("回复成功");
            this.huifuDialog = false;
            this.lingdaopishiDialog = false;
            this.lixingfankuiDialog = false;
            setTimeout(() => {
              this.submitOne = false
            }, 250)
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
            setTimeout(() => {
              this.submitOne = false
            }, 250)
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
      updateQuestionWddb(sjjh)
      this.huifuId = '';
      this.huifuTest = ''
    },
    //回复弹窗关闭
    huifuClose() {
      this.huifuId = '';
      this.huifuTest = ''
    },
    //附件预览关闭方法
    fjylClose() {
      this.openDialog()
    },
    //附件删除
    fjDelete(row) {
      if (!row.xh) {
        return
      }
      if (row.cjrid !== this.userInfo.userId.toString()) {
        this.$message.error("此附件不是您上传的，不可删除!")
        return;
      }
      this.$confirm('是否确认删除数据项?', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delFj(row.xh || row.id).then(res => {
          if (res.code === 200) {
            this.$message.success(res.msg);
          } else if (res.code === 404) {
            this.$message.error(res.msg);
          } else {
            this.$message.error(res.msg);
          }
          this.openDialog();
        });
      })
    },
    openDialog() {
      if (this.closureID.lxfk === '例行反馈') {
        this.loadJhjlList('例行反馈')
        this.islxfk = true
      } else {
        this.loadJhjlList('回复')
        this.islxfk = false
      }
      this.loadJhjlList('领导批示')
      this.loadzerData();
      this.getFileHFFJ();
      this.computTime(this.closureID)
    },
    //获得交互记录的数据
    loadJhjlList(jhzt, str) {
      if (jhzt === '领导批示') {
        getLDPS({WTID: this.closureID.id, JHZT: jhzt}).then(res => {
          if (res.code === 200) {
            this.ldpiList = res.rows;
            if (this.ldpiList.length > 0) {
              if (!this.fujian) {
                this.$emit('changeWidth', '1390px');
                this.isShowLdps = true;
              }
            } else {
              this.isShowLdps = false;
              if (!this.fujian) {
                this.$emit('changeWidth', '1090px')
              }
            }
          }
        });
      } else {
        getJhjl({WTID: this.closureID.id, JHZT: jhzt}).then(res => {
          if (res.code === 200) {
            this.jhsjList = res.rows;
          }
        });
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

    //h获取附件列表
    getFileHFFJ() {
      selectJhjlList({ID: this.closureID.id}).then(res=>{
        if (res.code === 200) {
          let data = res.rows;
          let ids = data.map(item=>item.xh)
          ids.push(this.closureID.id)
          selectFjListByIds(ids).then(res =>{
            this.wtFjylList = []
            this.fileData = []
            const rows = res.rows
            rows.forEach(v =>{
              if (v.id === this.closureID.id) {
                this.wtFjylList.push(v)
              }else {
                this.fileData.push(v)
              }
            })
          })
        }
      })
    },

    //计算完成天数，未完成天数等数据
    computTime(data) {
      //计算超期天数
      var currentDate = new Date();

      //计算完成天数
      if (data.wtzt === '已关闭') {
        var gbsjDate = new Date(data.gbsj);
        // 计算时间差（以毫秒为单位）
        var gbsjms = currentDate.getTime() - gbsjDate.getTime();
        // 将时间差转换为分钟
        var gbsjmin = Math.floor(gbsjms / (1000 * 60 * 60 * 24));
        this.wcts = gbsjmin.toString() + "天";
        //未完成天数
        this.wwcts = '已完成'
        //超期天数
        this.cqts = '已完成'
      }
      //计算未完成天数
      if (data.wtzt !== '已关闭') {
        var cjsjDate = new Date(data.cjsj);
        // 计算时间差（以毫秒为单位）
        var wwctsms = currentDate.getTime() - cjsjDate.getTime();
        // 将时间差转换为分钟
        var wwctsmim = Math.floor(wwctsms / (1000 * 60 * 60 * 24));
        this.wwcts = wwctsmim.toString() + "天";
        //超期天数
        var xwjjsjDate = new Date(data.xwjjsj);
        // 计算时间差（以毫秒为单位）
        var xwjjsjms = currentDate.getTime() - xwjjsjDate.getTime();
        // 将时间差转换为分钟
        var xwjjsj = Math.floor(xwjjsjms / (1000 * 60 * 60 * 24));
        this.cqts = xwjjsj.toString() + "天";
      }
    },

    //退出按钮
    closeDia() {
      this.$emit('closeDia')
    },

    //关闭按钮
    applyForClosure() {
      this.handleSelectGB = []
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

    //关闭选择信息的确定按钮
    confirmAnswer(row) {
      this.$confirm('是否确认答案选择项?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let data = {
          sjjhDTOS: this.handleSelectGB,
          wtxl: row.wtxl,
          wtlb: row.wtlb,
          gxh: row.gxh,
          wtms: row.wtms,
          jh: row.jh,
          cpxh: row.cpxh,
          wtid: this.closureID.id
        }
        answerSelectOff(data).then(res => {
          if (res.code === 200) {
            this.$message.success("关闭成功");
            this.$emit('closeXzxi')
          }
        })
      })
      this.xuanzedaan = false;
    },

    //将关闭的数据存到数组中
    handleChangeSelection(val) {
      this.handleSelectGB = val
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
          updateQuestionStatus({id: row.wtid, value: '反馈'})
          this.loadzerData();
        } else {
          this.$message.error("责任人激活失败");
        }
      });
    },

    //回复预览页面的附件预览多选框
    fujianyulan() {
      if (this.fujian) {
        this.$emit('changeWidth', '1390px')
        this.isShow = true;
        this.isShowLdps = false;
      } else {
        if (this.ldpiList.length > 0) {
          //判断是否领导批示有值，如果有值那么就展示领导批示
          this.$emit('changeWidth', '1390px')
          this.isShow = false;
          this.isShowLdps = true;
        } else {
          this.$emit('changeWidth', '1090px')
          this.isShow = false;
        }
      }
    },

    //上传附件按钮对用的方法
    fjylBut(str) {
      this.fjylDialog = true;
      if (str === 'dia') {
        this.fujianDiaData = this.itemJhjl
        this.fujianDiaData.type = '回复数据'
      } else {
        this.fujianDiaData = this.closureID
      }
    },

    //例行反馈按钮的执行方法
    lixingfankuiDialogMethod() {
      this.huifuTest = '';
      if (this.radios === '已关闭') {
        this.$message.error("该问题已经关闭，不能回复数据");
      } else {
        this.lixingfankuiDialog = true;
      }
    },

    //领导批示按钮的执行方法
    lingdaopishiDialogMethod() {
      this.huifuTest = '';
      if (this.radios === '已关闭') {
        this.$message.error("该问题已经关闭，不能回复数据");
      } else {
        this.lingdaopishiDialog = true;
      }
    },

    //回复按钮的执行方法
    huifuDialogMethod() {
      this.huifuTest = '';
      if (this.radios === '已关闭') {
        this.$message.error("该问题已经关闭，不能回复数据");
      } else {
        this.huifuDialog = true;
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

    //回复数据的单击事件
    huifuyangshione(index) {
      this.currentDivIndex = index;
    },

    //判断是否使例行反馈还是回复的数据
    huifuyangshi(ejhfppyj, xh, islxfk) {
      if (this.closureID.wtzt === '已关闭') {
        return
      }
      if (!this.authority.recover) {
        return
      }
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

    //小气泡的触发事件
    handlePageClick(event) {
      // 检查点击事件是否发生在小气泡上
      const isBubbleClicked = event.target.closest('.chatBox');

      if (!isBubbleClicked) {
        this.currentDivIndex = null; // 点击的是其他地方，将当前小气泡的索引设为 null，恢复原来的颜色
      }
    },

    //处理菜单项目的删除事件
    deleteJhjl() {
      // 处理菜单项点击事件
      let ejhfppyj = this.itemJhjl.ejhfppyj;
      if (this.itemJhjl.hffj === "是") {
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
            this.loadJhjlList('领导批示');
          } else {
            this.$message.error("删除失败");
          }
        });
      }
    },

    //下载文件
    downLoadFj(row) {
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

    //责任人的申请关闭
    closeQuestion() {
      let ids = [];
      const id = {id: '', value: ''};
      id.id = this.closureID.id;
      id.value = '待关闭';
      ids.push(id);
      updateQuestionsStatus(ids).then(res => {
        updateMyDoListStatus(ids).then(res=>{
          if (res.code === 200) {
            this.$message.success("申请关闭成功");
            this.openDialog();
          }
        })
      })

    },
    //问题升级
    wtsjOne() {
      wtsjOneZrr(this.closureID).then(res=>{
        if (res.code === 200) {
          this.$message.success("升级成功")
          this.openDialog()
        }else {
          this.$message.error(res.msg)
        }
      })
    },


  },
}
</script>

<style>
.el-card__body{
  padding: 20px;
}
.card .el-card__body {
  padding: 0;
}
.dialog .el-card{
  margin: 1px;
}
.dialog .el-table{
  color: black;
}
.dialog .el-table .el-table__header-wrapper th, .el-table .el-table__fixed-header-wrapper th {
  color: black;
}

.dialog .el-aside {
  background-color: transparent;
}
.fj .el-checkbox__label {
  font-size: 16px;
}
.cards .el-card__body {
  padding: 0;
}
.huifuDialog .el-dialog__header {
  padding-top: 20px;
}
.huifuDialog .el-dialog__body {
  padding-top: 0;
  padding-bottom: 20px;
}
.huifuDialog .el-dialog__close {
  font-size: 30px; /* 调整按钮大小 */
}
.huifuDialog .el-dialog__headerbtn .el-dialog__close {
  color: red;
}
.huifuDialog .el-dialog__headerbtn{
  z-index: 2;
}
.huifuDialog .el-dialog {
  border-radius: 10px;
}
.huifuDialog .el-dialog:not(.is-fullscreen) {
  margin-top: 10% !important;
}
</style>
<style scoped>
.app-container {
  padding: 10px;
  margin: 0;
  width: 100%;
}
/*高度为100% */
.indexHeight {
  height: 100%;
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
.clicked {
  background-color: #ffba00 !important;
}

.clicked::before {
  border-right-color: #ffba00 !important;
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
</style>
