<template>
  <div class="wtgl_zdhf">
    <el-row style="margin-bottom: 5px;height: 105px">
      <el-col :span="10">
        <el-row>
          <el-col class="labelAndInput" :span="12">
            <div>
              <label>关键字: </label>
              <el-input
                v-model="queryParams.KEYWORDS"
                placeholder="请输入关键字"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </div>
            <div>
              <label>产品型号: </label>
              <el-input
                v-model="queryParams.CPXH"
                placeholder="请输入产品型号"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </div>
            <div>
              <label>件号: </label>
              <el-input
                v-model="queryParams.JH"
                placeholder="请输入件号"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </div>
          </el-col>
          <el-col class="labelAndInput" :span="12">
            <div>
              <label>工序号: </label>
              <el-input
                v-model="queryParams.GXH"
                placeholder="请输入工序号"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </div>
            <div>
              <label>问题类别: </label>
              <el-select v-model="queryParams.WTLB" @change="wtlbChange" clearable
                         placeholder="请选择问题类别">
                <el-option
                  v-for="item in wtlbList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </div>
            <div>
              <label>问题细类: </label>
              <el-select v-model="queryParams.WTXL" @change="handleQuery" clearable placeholder="请选择问题细类">
                <el-option
                  v-for="item in wtxlList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </div>
          </el-col>
        </el-row>
      </el-col>
      <el-col style="margin-top: 5px" :span="6">
        <div class="labelAndInput" style="margin-bottom: 5px">
          <div>
            <label style="width: 110px;">问题名称: </label>
            <el-input
              v-model="queryParams.WTMC"
              placeholder="请输入问题名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </div>
        </div>
        <div>
          <label style="display: inline-block;font-weight: normal;width: 110px">问题描述信息:</label>
          <el-input
            style="width: 60%;vertical-align: top"
            type="textarea"
            placeholder="请输入内容"
            v-model="queryParams.WTMS">
          </el-input>
        </div>
      </el-col>
      <el-col :span="4" style="height: 100%;display: flex;justify-content: center;align-items: center;">
        <el-button @click="handleQuery" type="primary"
                   style="width: 80%;height: 60px;font-size: 18px;">
          智能查询
        </el-button>
      </el-col>
      <el-col :span="4" style="height: 100%;display: flex;justify-content: center;align-items: center;">
        <el-button @click="dialogCreatedQuestion" type="success"
                   style="width: 80%;height: 60px;font-size: 18px;">
          创建问题
        </el-button>
      </el-col>

    </el-row>
    <el-table border v-loading="loading" height="calc(100vh - 320px)" :data="daccList"
              @selection-change="handleSelectionChange">
      <el-table-column label="序号" type="index" width="50"/>
      <el-table-column show-overflow-tooltip label="产品型号" align="center" prop="cpxh"/>
      <el-table-column show-overflow-tooltip label="件号" align="center" prop="jh"/>
      <el-table-column show-overflow-tooltip label="工序号" align="center" prop="gxh"/>
      <el-table-column show-overflow-tooltip label="问题名称" align="center" prop="wtmc"/>
      <el-table-column show-overflow-tooltip label="问题类别" align="center" prop="wtlb"/>
      <el-table-column show-overflow-tooltip label="问题细类" align="center" prop="wtxl"/>
      <el-table-column show-overflow-tooltip label="问题描述" align="center" prop="wtms"/>
      <el-table-column show-overflow-tooltip label="答案信息" align="center" prop="daxx"/>
      <el-table-column show-overflow-tooltip label="热度" align="center" prop="rd"/>
      <el-table-column label="操作" align="center" width="100">
        <template slot-scope="scope">
          <el-button @click="xiangxixinxi(scope.row)" type="text" size="small">详细信息</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="height: 50px;">
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
        :page-sizes="[20, 50, 100]"
      />
    </div>
    <!--详细信息弹窗-->
    <el-dialog class="zdhfDialog" :close-on-click-modal="false" @close="shifoumanyi" title="详细信息"
               :visible.sync="xiangxiDialog">
      <xiangxixinxi v-if="xiangxiDialog" :jhsj-list="jhsjList"></xiangxixinxi>
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
        <el-button type="primary" @click="closeitOK">满 意</el-button>
        <el-button type="primary" @click="shifoumanyiDialog=false" style="margin-left: 40px">不满意</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {updateDaccToRd, listDaccToZN, getDacc, delDacc, updateDacc} from "@/api/question/upQuestion";
import {getJhjlByWtid, getwtlbMethod, getwtxlMethod} from "@/api/question/question";
import xiangxixinxi from '@/views/question/myquestion/xiangxixinxi'

export default {
  name: "WTGL_DACC",
  props: ['form'],
  components: {xiangxixinxi},
  data() {
    return {
      shifoumanyiDialog: false,//是否满意的弹出框
      xiangxiDialog: false,//详细信息是否弹出框
      jhsjList: [],//交互数据的数据集合
      daccid: null,//打开详细信息的答案id
      wtxlList: [],//问题细类的数组
      wtlbList: [],//问题类别的数组
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 答案信息表格数据
      daccList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        WTMS: null,
        WTMC: null,
        CPXH: null,
        JH: null,
        GXH: null,
        WTLB: null,
        WTXL: null,
        KEYWORDS: null
      }
    };
  },
  mounted() {
    if (this.form != null) {
      this.queryParams.WTMS = this.form.wtms
      this.queryParams.WTMC = this.form.wtmc
      this.queryParams.CPXH = this.form.cpxh
      this.queryParams.JH = this.form.jh
      this.queryParams.GXH = this.form.gxh
      this.queryParams.WTLB = this.form.wtlb
      this.queryParams.WTXL = this.form.wtxl
      if (this.form.wtlb != null && this.form.wtlb !== '') {
        this.wtxlMethod()
      }
      this.handleQuery()
    }
    this.wtlbMethod();
  },
  methods: {
    dialogCreatedQuestion() {
      this.$emit('dialogCreatedQuestion')
    },
    //详细信息的方法
    xiangxixinxi(row) {
      if (this.form != null) {
        this.$emit('xiangxixinxi', row)
        return
      }
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
          this.getList();
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
    //问题类别的下拉框改变后执行的方法
    wtlbChange() {
      this.queryParams.WTXL = null;
      this.wtxlMethod();
      this.handleQuery()
    },
    //问题细类的获得方法
    wtxlMethod() {
      this.wtxlList = [];
      getwtxlMethod({wtlb: this.queryParams.WTLB}).then(res => {
        if (res.code === 200) {
          let data = res.rows;
          for (let i = 0; i < data.length; i++) {
            this.wtxlList.push({label: data[i], value: data[i]})
          }
        }
      })
    },
    /** 查询答案信息列表 */
    getList() {
      if (
        (this.queryParams.WTMS == null || this.queryParams.WTMS === '') &&
        (this.queryParams.WTMC == null || this.queryParams.WTMC === '') &&
        (this.queryParams.CPXH == null || this.queryParams.CPXH === '') &&
        (this.queryParams.JH == null || this.queryParams.JH === '') &&
        (this.queryParams.GXH == null || this.queryParams.GXH === '') &&
        (this.queryParams.WTLB == null || this.queryParams.WTLB === '') &&
        (this.queryParams.WTXL == null || this.queryParams.WTXL === '') &&
        (this.queryParams.KEYWORDS == null || this.queryParams.KEYWORDS === '')
      ) {
        setTimeout(() => {
          this.$message.error("请输入查询条件!")
        }, 10);
        return
      }
      this.loading = true;
      listDaccToZN(this.queryParams).then(response => {
        this.daccList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        daxxid: null,
        wtmc: null,
        zrr: null,
        gbsj: null,
        daxx: null,
        cjr: null,
        wtlb: null,
        wtxl: null,
        rd: null,
        wtms: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.selectData = null
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.daxxid)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加答案信息";
    },
    /** 修改按钮操作 */
    handleUpdate() {
      this.reset();
      const daccid = this.ids
      getDacc(daccid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改答案";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.daxxid != null) {
            updateDacc(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } /*else {
            addDacc(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }*/
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete() {
      const daccids = this.ids;
      this.$confirm('是否确认删除数据项?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delDacc(daccids).then(response => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        })
      }).catch(() => {
      });
    },
  }
};
</script>
<style>
.labelAndInput .el-input__inner {
  border: none; /* 移除默认边框 */
  border-bottom: 1px solid #000; /* 添加下划线 */
  border-radius: 0; /* 可选：去除下划线两端的圆角 */
  padding: 0; /* 可选：去除内部填充 */
  height: 30px;
  line-height: 30px;
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


</style>
<style scoped>
.labelAndInput label {
  display: inline-block;
  width: 80px;
  font-weight: normal;
}

.labelAndInput .el-input {
  width: 60%;
  vertical-align: bottom;
}

.labelAndInput .el-select {
  width: 60%;
  vertical-align: bottom;
}

.wtgl_zdhf {
  padding: 5px 15px;
}
.labelAndInput>div{
  margin-bottom: 5px;
}
</style>
