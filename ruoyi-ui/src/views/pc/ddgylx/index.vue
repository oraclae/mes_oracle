<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100">
      <el-form-item label="产品型号" prop="cpxh">
        <el-input
          v-model="queryParams.cpxh"
          placeholder="请输入产品型号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品名称" prop="cpmc">
        <el-input
          v-model="queryParams.cpmc"
          placeholder="请输入产品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="零组件型号" prop="lzjxh">
        <el-input
          v-model="queryParams.lzjxh"
          placeholder="请输入零组件型号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="零组件名称" prop="lzjmc">
        <el-input
          v-model="queryParams.lzjmc"
          placeholder="请输入零组件名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备型号" prop="sbxh">
        <el-input
          v-model="queryParams.sbxh"
          placeholder="请输入设备型号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['pc:ddgylx:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['pc:ddgylx:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['pc:ddgylx:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pc:ddgylx:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table border v-loading="loading" :data="ddgylxList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column width="60" label="序号" align="center" type="index" />
      <el-table-column label="产品型号" align="center" prop="cpxh" />
      <el-table-column label="产品名称" align="center" prop="cpmc" />
      <el-table-column width="100" label="零组件型号" align="center" prop="lzjxh" />
      <el-table-column width="100" label="零组件名称" align="center" prop="lzjmc" />
      <el-table-column label="阶段" align="center" prop="jd" />
      <el-table-column label="版次" align="center" prop="bc" />
      <el-table-column label="工序号" align="center" prop="gxh" />
      <el-table-column label="工序名称" align="center" prop="gxmc" />
      <el-table-column label="承制单位" align="center" prop="czdw" />
      <el-table-column label="设备型号" align="center" prop="sbxh" />
      <el-table-column label="控制码" align="center" prop="kzm">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ddgylx_kzm" :value="scope.row.kzm"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="bz" />
      <el-table-column label="关键序" align="center" prop="gjx" />
      <el-table-column label="工种" align="center" prop="gc" />
      <el-table-column label="人员" align="center" prop="ry" />
      <el-table-column label="业务等级" align="center" prop="ywdj" />
      <el-table-column label="质量要求" align="center" prop="zlyq" />
      <el-table-column label="准备工时" align="center" prop="zbgs" />
      <el-table-column label="加工工时" align="center" prop="jggs" />
      <el-table-column width="100" label="生产加工(天)" align="center" prop="scjg" />
      <el-table-column width="110" label="生产加工时间" align="center" prop="scjgsj" />
      <el-table-column label="核算工时" align="center" prop="hsgs" />
      <el-table-column width="110" label="核算工时比率" align="center" prop="hsgsbl" />
      <el-table-column label="存货编码" align="center" prop="chbm" />
      <el-table-column label="批次" align="center" prop="pc" />
      <el-table-column label="厂家" align="center" prop="cj" />
      <el-table-column label="临艺说明" align="center" prop="lysm" />
      <el-table-column label="派工时间" align="center" prop="pgsj" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.pgsj, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="计划开始时间" align="center" prop="jhkssj" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.jhkssj, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="计划完成时间" align="center" prop="jhwcsj" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.jhwcsj, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际完成时间" align="center" prop="sjwcsj" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sjwcsj, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="订单数量" align="center" prop="ddsl" />
      <el-table-column label="在制数量" align="center" prop="zzsl" />
      <el-table-column label="确认数量" align="center" prop="qrsl" />
      <el-table-column label="报废数量" align="center" prop="bfsl" />
      <el-table-column label="领料数量" align="center" prop="llsl" />
      <el-table-column label="缴库数量" align="center" prop="jksl" />
      <el-table-column label="拆批数量" align="center" prop="cpsl" />
      <el-table-column width="110" label="返工返修数量" align="center" prop="fgfxsl" />
      <el-table-column label="拆批说明" align="center" prop="cpsm" />
      <el-table-column label="创建时间" align="center" prop="cjsj" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.cjsj, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检验数量" align="center" prop="jysl" />
      <el-table-column width="110" label="不合格品数量" align="center" prop="bhgpsl" />
      <el-table-column label="确认" align="center" prop="qr" />
      <el-table-column label="确认记录" align="center" prop="qrjl" />
      <el-table-column label="级数" align="center" prop="js" />
      <el-table-column label="系统控制" align="center" prop="xtkz" />
      <el-table-column label="订单状态" align="center" prop="ddzt" />
      <el-table-column label="是否业务" align="center" prop="sfyw" />
      <el-table-column width="110" label="目录系统编码" align="center" prop="mlxtbm" />
      <el-table-column label="人员编号" align="center" prop="rybh" />
      <el-table-column label="库位" align="center" prop="kw" />
      <el-table-column label="是否紧急" align="center" prop="sfjj">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.sfjj"/>
        </template>
      </el-table-column>
      <el-table-column label="拖欠数量" align="center" prop="tqsl" />
      <el-table-column label="是否转工" align="center" prop="sfzg" />
      <el-table-column label="是否外协" align="center" prop="sfwx" />
      <el-table-column label="分厂" align="center" prop="fc" />
      <el-table-column label="超期状态" align="center" prop="cqzt" />
      <el-table-column label="计划类型" align="center" prop="jhlx" />
      <el-table-column width="120" label="工序需使用时间" align="center" prop="gxxsysj" />
      <el-table-column label="加工单元" align="center" prop="jgdy" />
      <el-table-column width="110" label="是否需要工装" align="center" prop="sfxygz" />
      <el-table-column label="设备编码" align="center" prop="sbbm" />
      <el-table-column label="工段" align="center" prop="gd" />
      <el-table-column label="附件" align="center" prop="fj" />
      <el-table-column width="110" label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pc:ddgylx:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pc:ddgylx:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改订单工艺路线对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form :inline="true" ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="产品型号" prop="cpxh">
          <el-input v-model="form.cpxh" placeholder="请输入产品型号" />
        </el-form-item>
        <el-form-item label="产品名称" prop="cpmc">
          <el-input v-model="form.cpmc" placeholder="请输入产品名称" />
        </el-form-item>
        <el-form-item label="零组件型号" prop="lzjxh">
          <el-input v-model="form.lzjxh" placeholder="请输入零组件型号" />
        </el-form-item>
        <el-form-item label="零组件名称" prop="lzjmc">
          <el-input v-model="form.lzjmc" placeholder="请输入零组件名称" />
        </el-form-item>
        <el-form-item label="阶段" prop="jd">
          <el-input v-model="form.jd" placeholder="请输入阶段" />
        </el-form-item>
        <el-form-item label="版次" prop="bc">
          <el-input v-model="form.bc" placeholder="请输入版次" />
        </el-form-item>
        <el-form-item label="工序号" prop="gxh">
          <el-input v-model="form.gxh" placeholder="请输入工序号" />
        </el-form-item>
        <el-form-item label="工序名称" prop="gxmc">
          <el-input v-model="form.gxmc" placeholder="请输入工序名称" />
        </el-form-item>
        <el-form-item label="承制单位" prop="czdw">
          <el-input v-model="form.czdw" placeholder="请输入承制单位" />
        </el-form-item>
        <el-form-item label="设备型号" prop="sbxh">
          <el-input v-model="form.sbxh" placeholder="请输入设备型号" />
        </el-form-item>
        <el-form-item label="控制码" prop="kzm">
          <el-select v-model="form.kzm" placeholder="请选择控制码">
            <el-option
              v-for="dict in dict.type.ddgylx_kzm"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="bz">
          <el-input v-model="form.bz" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="关键序" prop="gjx">
          <el-input v-model="form.gjx" placeholder="请输入关键序" />
        </el-form-item>
        <el-form-item label="工种" prop="gc">
          <el-input v-model="form.gc" placeholder="请输入工种" />
        </el-form-item>
        <el-form-item label="人员" prop="ry">
          <el-input v-model="form.ry" placeholder="请输入人员" />
        </el-form-item>
        <el-form-item label="业务等级" prop="ywdj">
          <el-input v-model="form.ywdj" placeholder="请输入业务等级" />
        </el-form-item>
        <el-form-item label="质量要求" prop="zlyq">
          <el-input v-model="form.zlyq" placeholder="请输入质量要求" />
        </el-form-item>
        <el-form-item label="准备工时" prop="zbgs">
          <el-input v-model="form.zbgs" placeholder="请输入准备工时" />
        </el-form-item>
        <el-form-item label="加工工时" prop="jggs">
          <el-input v-model="form.jggs" placeholder="请输入加工工时" />
        </el-form-item>
        <el-form-item label="生产加工(天)" prop="scjg">
          <el-input v-model="form.scjg" placeholder="请输入生产加工(天)" />
        </el-form-item>
        <el-form-item label="生产加工时间" prop="scjgsj">
          <el-input v-model="form.scjgsj" placeholder="请输入生产加工时间" />
        </el-form-item>
        <el-form-item label="核算工时" prop="hsgs">
          <el-input v-model="form.hsgs" placeholder="请输入核算工时" />
        </el-form-item>
        <el-form-item label="核算工时比率" prop="hsgsbl">
          <el-input v-model="form.hsgsbl" placeholder="请输入核算工时比率" />
        </el-form-item>
        <el-form-item label="存货编码" prop="chbm">
          <el-input v-model="form.chbm" placeholder="请输入存货编码" />
        </el-form-item>
        <el-form-item label="批次" prop="pc">
          <el-input v-model="form.pc" placeholder="请输入批次" />
        </el-form-item>
        <el-form-item label="厂家" prop="cj">
          <el-input v-model="form.cj" placeholder="请输入厂家" />
        </el-form-item>
        <el-form-item label="临艺说明" prop="lysm">
          <el-input v-model="form.lysm" placeholder="请输入临艺说明" />
        </el-form-item>
        <el-form-item label="派工时间" prop="pgsj">
          <el-date-picker clearable
            v-model="form.pgsj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择派工时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="计划开始时间" prop="jhkssj">
          <el-date-picker clearable
            v-model="form.jhkssj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择计划开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="计划完成时间" prop="jhwcsj">
          <el-date-picker clearable
            v-model="form.jhwcsj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择计划完成时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="实际完成时间" prop="sjwcsj">
          <el-date-picker clearable
            v-model="form.sjwcsj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择实际完成时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="订单数量" prop="ddsl">
          <el-input v-model="form.ddsl" placeholder="请输入订单数量" />
        </el-form-item>
        <el-form-item label="在制数量" prop="zzsl">
          <el-input v-model="form.zzsl" placeholder="请输入在制数量" />
        </el-form-item>
        <el-form-item label="确认数量" prop="qrsl">
          <el-input v-model="form.qrsl" placeholder="请输入确认数量" />
        </el-form-item>
        <el-form-item label="报废数量" prop="bfsl">
          <el-input v-model="form.bfsl" placeholder="请输入报废数量" />
        </el-form-item>
        <el-form-item label="领料数量" prop="llsl">
          <el-input v-model="form.llsl" placeholder="请输入领料数量" />
        </el-form-item>
        <el-form-item label="缴库数量" prop="jksl">
          <el-input v-model="form.jksl" placeholder="请输入缴库数量" />
        </el-form-item>
        <el-form-item label="拆批数量" prop="cpsl">
          <el-input v-model="form.cpsl" placeholder="请输入拆批数量" />
        </el-form-item>
        <el-form-item label="返工返修数量" prop="fgfxsl">
          <el-input v-model="form.fgfxsl" placeholder="请输入返工返修数量" />
        </el-form-item>
        <el-form-item label="拆批说明" prop="cpsm">
          <el-input v-model="form.cpsm" placeholder="请输入拆批说明" />
        </el-form-item>
        <el-form-item label="检验数量" prop="jysl">
          <el-input v-model="form.jysl" placeholder="请输入检验数量" />
        </el-form-item>
        <el-form-item label="不合格品数量" prop="bhgpsl">
          <el-input v-model="form.bhgpsl" placeholder="请输入不合格品数量" />
        </el-form-item>
        <el-form-item label="确认" prop="qr">
          <el-input v-model="form.qr" placeholder="请输入确认" />
        </el-form-item>
        <el-form-item label="确认记录" prop="qrjl">
          <el-input v-model="form.qrjl" placeholder="请输入确认记录" />
        </el-form-item>
        <el-form-item label="级数" prop="js">
          <el-input v-model="form.js" placeholder="请输入级数" />
        </el-form-item>
        <el-form-item label="系统控制" prop="xtkz">
          <el-input v-model="form.xtkz" placeholder="请输入系统控制" />
        </el-form-item>
        <el-form-item label="订单状态" prop="ddzt">
          <el-input v-model="form.ddzt" placeholder="请输入订单状态" />
        </el-form-item>
        <el-form-item label="是否业务" prop="sfyw">
          <el-input v-model="form.sfyw" placeholder="请输入是否业务" />
        </el-form-item>
        <el-form-item label="目录系统编码" prop="mlxtbm">
          <el-input v-model="form.mlxtbm" placeholder="请输入目录系统编码" />
        </el-form-item>
        <el-form-item label="人员编号" prop="rybh">
          <el-input v-model="form.rybh" placeholder="请输入人员编号" />
        </el-form-item>
        <el-form-item label="库位" prop="kw">
          <el-input v-model="form.kw" placeholder="请输入库位" />
        </el-form-item>
        <el-form-item label="是否紧急" prop="sfjj">
          <el-select v-model="form.sfjj" placeholder="请选择是否紧急">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="拖欠数量" prop="tqsl">
          <el-input v-model="form.tqsl" placeholder="请输入拖欠数量" />
        </el-form-item>
        <el-form-item label="是否转工" prop="sfzg">
          <el-input v-model="form.sfzg" placeholder="请输入是否转工" />
        </el-form-item>
        <el-form-item label="是否外协" prop="sfwx">
          <el-input v-model="form.sfwx" placeholder="请输入是否外协" />
        </el-form-item>
        <el-form-item label="分厂" prop="fc">
          <el-input v-model="form.fc" placeholder="请输入分厂" />
        </el-form-item>
        <el-form-item label="超期状态" prop="cqzt">
          <el-input v-model="form.cqzt" placeholder="请输入超期状态" />
        </el-form-item>
        <el-form-item label="计划类型" prop="jhlx">
          <el-input v-model="form.jhlx" placeholder="请输入计划类型" />
        </el-form-item>
        <el-form-item label="工序需使用时间" prop="gxxsysj">
          <el-input v-model="form.gxxsysj" placeholder="请输入工序需使用时间" />
        </el-form-item>
        <el-form-item label="加工单元" prop="jgdy">
          <el-input v-model="form.jgdy" placeholder="请输入加工单元" />
        </el-form-item>
        <el-form-item label="是否需要工装" prop="sfxygz">
          <el-input v-model="form.sfxygz" placeholder="请输入是否需要工装" />
        </el-form-item>
        <el-form-item label="设备编码" prop="sbbm">
          <el-input v-model="form.sbbm" placeholder="请输入设备编码" />
        </el-form-item>
        <el-form-item label="工段" prop="gd">
          <el-input v-model="form.gd" placeholder="请输入工段" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDdgylx, getDdgylx, delDdgylx, addDdgylx, updateDdgylx } from "@/api/pc/ddgylx";

export default {
  name: "Ddgylx",
  dicts: ['ddgylx_kzm', 'sys_yes_no'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      // 订单工艺路线表格数据
      ddgylxList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        cpxh: null,
        cpmc: null,
        lzjxh: null,
        lzjmc: null,
        jd: null,
        bc: null,
        gxh: null,
        gxmc: null,
        czdw: null,
        sbxh: null,
        kzm: null,
        bz: null,
        gjx: null,
        gc: null,
        ry: null,
        ywdj: null,
        zlyq: null,
        zbgs: null,
        jggs: null,
        scjg: null,
        scjgsj: null,
        hsgs: null,
        hsgsbl: null,
        chbm: null,
        pc: null,
        cj: null,
        lysm: null,
        pgsj: null,
        jhkssj: null,
        jhwcsj: null,
        sjwcsj: null,
        ddsl: null,
        zzsl: null,
        qrsl: null,
        bfsl: null,
        llsl: null,
        jksl: null,
        cpsl: null,
        fgfxsl: null,
        cpsm: null,
        cjsj: null,
        jysl: null,
        bhgpsl: null,
        qr: null,
        qrjl: null,
        js: null,
        xtkz: null,
        ddzt: null,
        sfyw: null,
        mlxtbm: null,
        rybh: null,
        kw: null,
        sfjj: null,
        tqsl: null,
        sfzg: null,
        sfwx: null,
        fc: null,
        cqzt: null,
        jhlx: null,
        gxxsysj: null,
        jgdy: null,
        sfxygz: null,
        sbbm: null,
        gd: null,
        fj: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        cjsj: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询订单工艺路线列表 */
    getList() {
      this.loading = true;
      listDdgylx(this.queryParams).then(response => {
        this.ddgylxList = response.rows;
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
        id: null,
        cpxh: null,
        cpmc: null,
        lzjxh: null,
        lzjmc: null,
        jd: null,
        bc: null,
        gxh: null,
        gxmc: null,
        czdw: null,
        sbxh: null,
        kzm: null,
        bz: null,
        gjx: null,
        gc: null,
        ry: null,
        ywdj: null,
        zlyq: null,
        zbgs: null,
        jggs: null,
        scjg: null,
        scjgsj: null,
        hsgs: null,
        hsgsbl: null,
        chbm: null,
        pc: null,
        cj: null,
        lysm: null,
        pgsj: null,
        jhkssj: null,
        jhwcsj: null,
        sjwcsj: null,
        ddsl: null,
        zzsl: null,
        qrsl: null,
        bfsl: null,
        llsl: null,
        jksl: null,
        cpsl: null,
        fgfxsl: null,
        cpsm: null,
        cjsj: null,
        jysl: null,
        bhgpsl: null,
        qr: null,
        qrjl: null,
        js: null,
        xtkz: null,
        ddzt: null,
        sfyw: null,
        mlxtbm: null,
        rybh: null,
        kw: null,
        sfjj: null,
        tqsl: null,
        sfzg: null,
        sfwx: null,
        fc: null,
        cqzt: null,
        jhlx: null,
        gxxsysj: null,
        jgdy: null,
        sfxygz: null,
        sbbm: null,
        gd: null,
        fj: null
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单工艺路线";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDdgylx(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单工艺路线";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDdgylx(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDdgylx(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除订单工艺路线编号为"' + ids + '"的数据项？').then(function() {
        return delDdgylx(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pc/ddgylx/export', {
        ...this.queryParams
      }, `ddgylx_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
