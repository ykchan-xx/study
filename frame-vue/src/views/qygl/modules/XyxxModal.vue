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
          label="协议编号">
          <a-input placeholder="请输入协议编号" v-decorator="['xybh', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="企业编码">
          <a-input placeholder="请输入企业编码" v-decorator="['qybm', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="企业规定人数">
          <a-input placeholder="请输入企业规定人数" v-decorator="['qygdrs', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="签约方负责人">
          <a-input placeholder="请输入签约方负责人" v-decorator="['qyffzr', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="协议领队人员">
          <a-input placeholder="请输入协议领队人员" v-decorator="['xyldry', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="领队联系方式">
          <a-input placeholder="请输入领队联系方式" v-decorator="['ldlxfs', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="协议开始时间">
<!--          <a-input placeholder="请输入协议开始时间" v-decorator="['xykssj', {}]" />-->
          <a-date-picker showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="['xykssj', {}]"></a-date-picker>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="协议结束时间">
<!--          <a-input placeholder="请输入协议结束时间" v-decorator="['xyjssj', {}]" />-->
          <a-date-picker showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="['xyjssj', {}]"></a-date-picker>
        </a-form-item>
        <!--附件-->
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
    name: "XyxxModal",
    data () {
      return {
        headers:{},
        previewVisible:false,
        previewImage:'',
        fileList:[
          {uid:'',
            name:'',
            status:'',
            url:''
        }],
        title:"操作",
        visible: false,
        model: {
         },
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
          fileUpload: window._CONFIG['domianURL']+"/sys/common/upload",
          imgerver: window._CONFIG['domianURL']+"/sys/common/view",
          add: "/qygl/xyxx/save",
          edit: "/qygl/xyxx/update",
        },
      }
    },
    created () {
      let token = Vue.ls.get(ACCESS_TOKEN);
      this.headers = {"X-Access-Token":token}

    },
    computed:{
      uploadAction:function () {
        return this.url.fileUpload;
      }
    },
    methods: {
      add () {
        let that = this;
        that.fileList=[];
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        let url = "/qygl/xyxx/getxyfj";
        let xybh = record.xybh;

        axios.get(url,{
          method:"GET",
          params: {xybh:xybh}
        }).then(res=>{
          if (res.success){
            let imgList = res.data;
            let that =this;
            that.fileList = res.data;
            console.log(res.data)
          }else{
            let that =this;
            that.fileList = [];
          }
        })
        this.model = Object.assign({}, record);
        // console.log(this.model);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'xybh','qybm','qygdrs','qyffzr','xyldry','ldlxfs','xykssj','xyjssj'))
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
      handleChange({ fileList }) {
        this.fileList = fileList;
        this.model.fileList = fileList;
        // console.log(this.model);
      },
    }
  }
</script>

<style scoped>
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>