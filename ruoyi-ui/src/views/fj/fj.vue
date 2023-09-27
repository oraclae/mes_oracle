<template>
  <div>
    <!-- 按钮的地方   -->
    <div>
      <el-upload
        v-if="row.wtzt !== '已关闭'
        && (row.hfrid == null || row.hfrid === userInfo.userId+'')"
        ref="upload"
        :limit="1"
        accept=".xlsx, .pdf, .jpg, .xls, .png, .doc, .docx, .zip, .rar"
        :action="upload.url"
        :headers="upload.headers"
        :file-list="upload.fileList"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        >
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        <div slot="tip" class="el-upload__tip">只能上传文件，且不超过50M</div>
      </el-upload>

    </div>
    <!--  表格的位置  -->
    <el-table height="400px" :data="fileListTable" border style="width: 100%">
      <el-table-column prop="cjr" width="100" label="创建人">
      </el-table-column>
      <el-table-column prop="wjmc" label="文件名称">
      </el-table-column>
      <el-table-column prop="scsj" label="上传时间">
      </el-table-column>
      <el-table-column label="操作" width="120"><template slot-scope="scope">
          <el-button v-if="scope.row.cjrid == null
                           || scope.row.cjrid === userInfo.userId+'' || guanliyuan"
                     @click="handleDeleteFj(scope.row)" type="text" size="small">删除</el-button>
          <el-button @click="handleDownLoad(scope.row)" type="text" size="small">下载</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div>

    </div>
  </div>
</template>

<script>
import {getToken} from "@/utils/auth";
import {addFj, delFj, listById} from "@/api/fj/fj";
import {getUserInfo} from "@/api/question/wtlbry";

export default {
  name: "fj",
  props:['row','fileList'],
  data() {
    return {
      guanliyuan: false,//是否管理员
      userInfo:{},//当前登录用户
      // 上传参数
      upload: {
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/common/upload",
        // 上传的文件列表
        fileList: [],
        maxSize: 500 * 1024, // 最大上传文件大小，单位为字节
      },
      fileListTable: this.fileList,
    }
  },
  mounted() {
    if (this.fileList == null || this.fileList.length === 0) {
      this.load()
    }
    this.getUserInfo()
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
    load() {
      listById({id: this.row.xh || this.row.id}).then(res => {
        this.fileListTable = res.rows;
      });
    },
    //下载文件
    handleDownLoad(row) {
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
    //刷新附件
    fujianshuaxin() {
      listById({id:this.row.xh || this.row.id}).then(res=>{
        this.fileListTable = res.rows;
        this.upload.fileList = [];
      });
    },
    handleDeleteFj(row) {
      delFj(row.xh || row.id).then(res=>{
        if (res.code === 200) {
          this.$message.success(res.msg);
        } else if (res.code === 404) {
          this.$message.error(res.msg);
        } else {
          this.$message.error(res.msg);
        }
        this.fujianshuaxin();
      })
    },
    // 文件提交处理
    submitUpload() {
      this.$refs.upload.submit();
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, wtFjylList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, wtFjylList) {
      this.upload.isUploading = false;
      // this.form.filePath = response.url;
      this.$message.success(response.msg);
      let FileNam = file.name;
      let fj = {id:this.row.xh || this.row.id,wjmc:file.name, lj: response.url, type: this.row.type};
      addFj(fj).then(res => {
        this.fujianshuaxin();
      });
    }
  }
}
</script>

<style scoped>

</style>
