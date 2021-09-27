<template>
  <a-modal
    :title="title"
    :width="1100"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
<a-row>
  <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="市级">
          <a-input placeholder="请输入市级" v-decorator="['qysj', {rules:[{required: true,message:'请输入内容'}]}]" />
        </a-form-item>
  </a-col>
<!--  <a-col :span="12">-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="县区">-->
<!--          <a-input placeholder="请输入县区" v-decorator="['qyxq', {rules:[{required: true,message:'请输入内容'}]}]" />-->
<!--        </a-form-item>-->
<!--  </a-col>-->
<!--  <a-col :span="12">-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="企业地址">-->
<!--          <a-input placeholder="请输入企业地址" v-decorator="['qydz', {rules:[{required: true,message:'请输入内容'}]}]" />-->
<!--        </a-form-item>-->
<!--  </a-col>-->
<!--  <a-col :span="12">-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="企业类别">-->
<!--          <a-input placeholder="请输入企业类别" v-decorator="['qylb', {rules:[{required: true,message:'请输入内容'}]}]" />-->
<!--        </a-form-item>-->
<!--  </a-col>-->
<!--  <a-col :span="12">-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="企业名称">-->
<!--          <a-input placeholder="请输入企业名称" v-decorator="['qymc', {rules:[{required: true,message:'请输入内容'}]}]" />-->
<!--        </a-form-item>-->
<!--  </a-col>-->
<!--  <a-col :span="12">-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="企业编码">-->
<!--          <a-input placeholder="请输入企业编码" v-decorator="['qybm', {rules:[{required: true,pattern: /^[0-9]*$/,message:'请输入内容'}]}]" />-->
<!--        </a-form-item>-->
<!--  </a-col>-->
<!--  <a-col :span="12">-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="注册资金">-->
<!--          <a-input placeholder="请输入注册资金" v-decorator="['zczj', {rules:[{required: true,message:'请输入内容'}]}]" />-->
<!--        </a-form-item>-->
<!--  </a-col>-->
<!--  <a-col :span="12">-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="注册地址">-->
<!--          <a-input placeholder="请输入注册地址" v-decorator="['zcdz', {rules:[{required: true,message:'请输入内容'}]}]" />-->
<!--        </a-form-item>-->
<!--  </a-col>-->
</a-row>
        <a-row>
<!--          <a-col :span="12">-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="住宿登记地址">-->
<!--          <a-input placeholder="请输入住宿登记地址" v-decorator="['zsdz', {rules:[{required: true,message:'请输入内容'}]}]" />-->
<!--        </a-form-item>-->
<!--          </a-col>-->
<!--          <a-col :span="12">-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="法人">-->
<!--          <a-input placeholder="请输入法人" v-decorator="['fr', {rules:[{required: true,message:'请输入内容'}]}]" />-->
<!--        </a-form-item>-->
<!--          </a-col>-->
<!--          <a-col :span="12">-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="联系人">-->
<!--          <a-input placeholder="请输入联系人" v-decorator="['lxr', {rules:[{required: true,message:'请输入内容'}]}]" />-->
<!--        </a-form-item>-->
<!--          </a-col>-->
<!--          <a-col :span="12">-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="联系电话">-->
<!--          <a-input placeholder="请输入联系电话" v-decorator="['lxdh', {rules:[{required: true,pattern: /^[0-9]*$/,message:'请输入内容'}]}]" />-->
<!--        </a-form-item>-->
<!--          </a-col>-->
<!--          <a-col :span="12">-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="签约方">-->
<!--          <a-input placeholder="请输入签约方" v-decorator="['qyf', {rules:[{required: true,message:'请输入内容'}]}]" />-->
<!--        </a-form-item>-->
<!--          </a-col>-->
<!--          <a-col :span="12">-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="签约开始时间">-->
<!--          <a-date-picker showTime format="YYYY-MM-DD HH:mm:ss" placeholder=" 请输入签约开始时间 " v-decorator="['qy_starttime', {rules:[{required: true,message:'请输入内容'}]}]" />-->
<!--        </a-form-item>-->
<!--          </a-col>-->
<!--          <a-col :span="12">-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="签约结束时间">-->
<!--          <a-date-picker showTime format="YYYY-MM-DD HH:mm:ss" placeholder="请输入签约结束时间" v-decorator="['qy_endtime', {rules:[{required: true,message:'请输入内容'}]}]" />-->
<!--        </a-form-item>-->
<!--          </a-col>-->
<!--          <a-col :span="12">-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="企业总人数">-->
<!--          <a-input placeholder="请输入企业总人数" v-decorator="['qyrs', {rules:[{required: true,pattern: /^[0-9]*$/,message:'请输入内容'}]}]" />-->
<!--        </a-form-item>-->
<!--          </a-col>-->
        </a-row>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="附件"
        >
          <a-upload
            :action="uploadAction"
            :data="{'isup':1}"
            :headers="headers"
            list-type="picture-card"
            :file-list="fileList"
            @preview="handlePreview"
            @change="handleChange"
          >
            <div v-if="fileList.length<3">
              <a-icon type="plus"/>
              <div class="ant-upload-text">上传</div>
            </div>
          </a-upload>
          <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
            <img alt="example" stype="width:100%" :src="previewImage"/>
          </a-modal>

        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"
  import Vue from "vue";
  import {ACCESS_TOKEN} from "@/store/mutation-types";
  import {axios} from "@/utils/request";

  function getBase64(file) {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = error => reject(error);
    });
  }
  export default {
    name: "HdlQyxxModal",
    data() {
      return {
        headers: {},
        previewVisible: false,
        previewImage: '',
        fileList: [
          {
            uid: '',
            name: '',
            status: '',
            url: ''
          }],
        title: "操作",
        visible: false,
        model: {
        },
        labelCol: {
          xs: {span: 24},
          sm: {span: 5},
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 16},
        },

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules: {

        },
        url: {
          fileUpload: window._CONFIG['domianURL'] + "/sys/common/upload",
          imgerver: window._CONFIG['domianURL'] + "/sys/common/view",
          add: "/qygl/hdl_qyxx/save",
          edit: "/qygl/hdl_qyxx/update",
        },
      }
    },
    created() {
      let token = Vue.ls.get(ACCESS_TOKEN);
      this.headers = {"X-Access-Token": token}
    },
    computed: {
      uploadAction: function () {
        return this.url.fileUpload;
      }
    },
    methods: {
      add() {
        let that = this;
        that.fileList = [];
        this.edit({});

      },
      edit(record) {
        this.form.resetFields();
        let url = "/qygl/hdl_qyxx/getQyfj";
        let qybm = record.qybm;
        axios.get(url, {
          method: "GET",
          params: {qybm:qybm}
        }).then(res => {
          if (res.success) {
            let imgList = res.data;
            let that = this;
            that.fileList = res.data;
            console.log(res.data)
          } else {
            let that = this;
            that.fileList = [];
          }
        })
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'qysj', 'qyxq', 'qydz', 'qylb', 'qymc', 'qybm', 'zczj', 'zcdz', 'zsdz', 'fr', 'lxr', 'lxdh', 'qyf', 'qyStarttime', 'qyEndtime', 'qyrs'))
        });
      },
      close() {
        this.$emit('close');
        this.visible = false;
      },
      handleOk() {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if (!this.model.id) {
              httpurl += this.url.add;
              method = 'post';
            } else {
              httpurl += this.url.edit;
              method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
            httpAction(httpurl, formData, method).then((res) => {
              if (res.success) {
                that.$message.success(res.message);
                that.$emit('ok');
              } else {
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })

          }
        })
      },
      handleCancel() {
        this.previewVisible = false;
        this.close()
      },
      async handlePreview(file) {
        if (!file.url && !file.preview) {
          file.preview = await getBase64(file.originFileObj);
        }
        this.previewImage = file.url || file.preview;
        this.previewVisible = true;
      },
      handleChange({fileList}) {
        this.fileList = fileList;
        this.model.fileList = fileList;
        // console.log(this.model);
      },
    }
  }

</script>

<style scoped>
  .avatar-uploader > .ant-upload {
    width: 128px;
    height: 128px;
  }
  .ant-upload-select-picture-card i {
    font-size: 32px;
    color: #999;
  }

  .ant-upload-select-picture-card .ant-upload-text {
    margin-top: 8px;
    color: #666;
  }
</style>