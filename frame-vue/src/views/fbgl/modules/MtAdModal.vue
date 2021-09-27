<template>
  <a-modal
    :title="title"
    :width="1000"
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
          label="广告标题">
          <a-input placeholder="请输入标题" v-decorator="['description', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="广告banner">
          <a-upload
            listType="picture-card"
            class="avatar-uploader"
            :showUploadList="false"
            :action="uploadAction"
            :data="{'isup':1}"
            :headers="headers"
            :beforeUpload="beforeUpload"
            @change="handleChange"
          >
            <img v-if="images" :src="getAvatarView()" alt="广告banner" style="height:150px;max-width:500px"/>
            <div v-else>
              <a-icon :type="uploadLoading ? 'loading' : 'plus'" />
              <div class="ant-upload-text">上传</div>
            </div>
          </a-upload>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="广告内容">
          <j-editor  style="width: 100%" v-decorator="['detail', {}]"  triggerChange />
        </a-form-item>

<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="联系人">-->
<!--          <a-input placeholder="请输入联系人" v-decorator="['contactperson', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="联系电话">-->
<!--          <a-input placeholder="请输入联系电话" v-decorator="['phone', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="电子邮箱">-->
<!--          <a-input placeholder="请输入电子邮箱" v-decorator="['email', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="联系地址">-->
<!--          <a-input placeholder="请输入联系地址" v-decorator="['address', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="公司">-->
<!--          <a-input placeholder="请输入公司" v-decorator="['company', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="发布时间">-->
<!--          <a-date-picker v-decorator="[ 'createtime', {}]" />-->
<!--          &lt;!&ndash;-->
<!--            <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'createtime', {}]" />-->
<!--          &ndash;&gt;-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="发布人">-->
<!--          <a-input placeholder="请输入发布人" v-decorator="['publisher', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="最后修改时间">-->
<!--          <a-date-picker v-decorator="[ 'updatetime', {}]" />-->
<!--          &lt;!&ndash;-->
<!--            <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'updatetime', {}]" />-->
<!--          &ndash;&gt;-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="最后修改人">-->
<!--          <a-input placeholder="请输入最后修改人" v-decorator="['modifiedby', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="删除标志">-->
<!--          <a-input placeholder="请输入删除标志" v-decorator="['deleteflag', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="详情图片">-->
<!--          <a-input placeholder="请输入详情图片" v-decorator="['imgurl', {}]" />-->
<!--        </a-form-item>-->
		
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import Vue from 'vue'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"
  import JEditor from "../../../components/jeecg/JEditor";

  export default {
    name: "MtAdModal",
      components: {
          JEditor
      },
    data () {
      return {
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
        headers:{},
        uploadLoading:false,
        images: "",
        url: {
          add: "/ggzs/save",
          edit: "/ggzs/update",
          fileUpload: window._CONFIG['domianURL']+"/sys/common/upload",
          imgerver: window._CONFIG['domianURL']+"/sys/common/view",
        },
      }
    },
    created () {
      const token = Vue.ls.get(ACCESS_TOKEN);
      this.headers = {"X-Access-Token":token};

    },
    computed:{
      uploadAction:function () {
        return this.url.fileUpload;
      }
    },
    methods: {
      add () {
        this.images = "";
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        if(record.hasOwnProperty("id")){
          if(this.model.images){
            this.images = "Has no pic url yet";
          }
        }
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'description','detail'))
		  //时间格式化
      //     this.form.setFieldsValue({createtime:this.model.createtime?moment(this.model.createtime):null})
      //     this.form.setFieldsValue({updatetime:this.model.updatetime?moment(this.model.updatetime):null})
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
            let images = that.model.images;
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
            formData.images = images;
            // //时间格式化
            // formData.createtime = formData.createtime?formData.createtime.format():null;
            // //formData.createtime = formData.createtime?formData.createtime.format('YYYY-MM-DD HH:mm:ss'):null;
            // formData.updatetime = formData.updatetime?formData.updatetime.format():null;
            // //formData.updatetime = formData.updatetime?formData.updatetime.format('YYYY-MM-DD HH:mm:ss'):null;
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
        this.close()
      },
      beforeUpload: function(file){
        var fileType = file.type;
        if(fileType.indexOf('image')<0){
          this.$message.warning('请上传图片');
          return false;
        }
        //TODO 验证文件大小
      },
        handleChange (info) {
        this.images = "";
        if (info.file.status === 'uploading') {
          this.uploadLoading = true;
          return
        }
        if (info.file.status === 'done') {
          var response = info.file.response;
          this.uploadLoading = false;
          console.log(response);
          if(response.success){
            this.model.images = response.data;
            this.images = "Has no pic url yet";
          }else{
            this.$message.warning(response.message);
          }
        }
      },
      getAvatarView(){
        return this.model.images;
      },
    }
  }
</script>

<style scoped>

</style>