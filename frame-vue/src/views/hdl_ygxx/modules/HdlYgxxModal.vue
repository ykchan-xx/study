<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="员工名字">
          <a-input placeholder="请输入员工名字" v-decorator="['ygname', {rules:[{required: true,message:'请输入内容'}]}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="员工性别">
          <a-input placeholder="请输入员工性别" v-decorator="['ygsex', {rules:[{required: true,message:'请输入内容'}]}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="员工企业">
          <a-input placeholder="请输入员工企业" v-decorator="['ygqy', {rules:[{required: true,message:'请输入内容'}]}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="员工出生日期">
          <a-date-picker  format="YYYY-MM-DD" placeholder="请输入员工出生日期" v-decorator="['ygbirth', {rules:[{required: true,message:'请输入内容'}]}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="员工身份证号">
          <a-input placeholder="请输入员工身份证号" v-decorator="['ygsfzid', {rules:[{required: true,pattern: /^\d{16}$/,message:'请输入内容'}]}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="员工邮箱">
          <a-input placeholder="请输入员工邮箱"  v-decorator="['ygyx', {rules:[{required: true,type:'email',message:'请输入内容'}]}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="员工号">
          <a-input placeholder="请输入员工号" v-decorator="['ygqyid', {rules:[{required: true,pattern: /^[0-9]*$/,message:'请输入内容'}]}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="头像"
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
    name: "HdlYgxxModal",
    data () {
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
        title:"操作",
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          fileUpload: window._CONFIG['domianURL'] + "/sys/common/upload",
          imgerver: window._CONFIG['domianURL'] + "/sys/common/view",
          add: "/qygl/hdl_ygxx/save",
          edit: "/qygl/hdl_ygxx/update",
        },
      }
    },
    created () {
      let token = Vue.ls.get(ACCESS_TOKEN);
      this.headers = {"X-Access-Token": token}
    },
    computed: {
      uploadAction: function () {
        return this.url.fileUpload;
      }
    },
    methods: {
      add () {
        let that = this;
        that.fileList = [];
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        let url = "/qygl/hdl_ygxx/getYgfj";
        let ygqyid = record.ygqyid;

        axios.get(url, {
          method: "GET",
          params: {ygqyid:ygqyid}
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
          this.form.setFieldsValue(pick(this.model,'ygname','ygsex','ygqy','ygbirth','ygsfzid','ygyx','ygqyid','tx'))
		  //时间格式化
        });

      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })

          }
        })
      },
      handleCancel () {
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