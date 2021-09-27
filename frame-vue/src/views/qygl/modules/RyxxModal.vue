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

        <a-form-item label="头像" :labelCol="labelCol" :wrapperCol="wrapperCol" >
<!--          <a-input placeholder="请输入头像" v-decorator="['tx', {}]" />-->
          <a-upload
            list-type="picture-card"
            class="avatar-uploader"
            :showUploadList="false"
            :action="uploadAction"
            :data="{'isup':1}"
            :headers="headers"
            :beforeUpload="beforeUpload"
            @change="handleChange"
          >
            <img v-if="picUrl" :src="getAvatarView()" alt="头像" style="height:104px;max-width:300px"/>
         <div v-else>
           <a-icon :type="uploadLoading ? 'loading' : 'plus'" />
           <div class="ant-upload-text">上传</div>
         </div>
          </a-upload>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="所属企业">
<!--          <a-input placeholder="请选择所属企业" v-decorator="['ssqybm', {}]" />-->
          <a-select  style="width: 100%" placeholder="请选择所属企业" v-decorator="['ssqybm', {}]" @change="handleChangeQyInfo">
            <a-select-option  v-for="item in archiveinfo" :key="item['qybm']" :value="item['qybm']">
              {{item['qymc']}}
            </a-select-option>
          </a-select>

        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="城市">
          <a-input placeholder="请输入城市" v-decorator="['sj', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="区县">
          <a-input placeholder="请输入区县" v-decorator="['qx', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="中文姓名">
          <a-input placeholder="请输入中文姓名" v-decorator="['zwxm', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="英文姓名">
          <a-input placeholder="请输入英文姓名" v-decorator="['ywxm', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="性别">
<!--          <a-input placeholder="请输入性别" v-decorator="['sex', {}]" />-->
          <j-dict-select-tag :triggerChange="true"  v-decorator="['sex', {}]" placeholder="请选择性别" dictCode="sex"/>
        </a-form-item>
        <!--国家地区头-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="国家地区">
          <j-dict-select-tag :triggerChange="true"  v-decorator="['gjdq', {}]" placeholder="请选择国家地区" dictCode="gjdq"/>
        </a-form-item>
        <!--国家地区尾-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="出生日期 ">
<!--          <a-input placeholder="请输入出生日期 " v-decorator="['csrq', {}]" />-->
          <a-date-picker  v-decorator="['csrq', {}]"></a-date-picker>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="证件号码">
          <a-input placeholder="请输入证件号码" v-decorator="['zjhm', {}]" />
        </a-form-item>
        <!--证件种类↓-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="证件种类">
          <j-dict-select-tag :triggerChange="true"  v-decorator="['zjzl', {}]" placeholder="请选择证件种类" dictCode="zjzl"/>
        </a-form-item>
        <!--证件种类↑-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="职位">
          <a-input placeholder="请输入职位" v-decorator="['zw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="联系电话">
          <a-input placeholder="请输入联系电话" v-decorator="['lxdh', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="实际入境时间 ">
<!--          <a-input placeholder="请输入实际入境时间 " v-decorator="['sjrjsj', {}]" />-->
          <a-date-picker showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="['sjrjsj', {}]"></a-date-picker>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="实际入境口岸">
          <a-input placeholder="请输入实际入境口岸" v-decorator="['sjrjka', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="住宿登记时间">
<!--          <a-input placeholder="请输入住宿登记时间" v-decorator="['zsdjsj', {}]" />-->
          <a-date-picker showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="['zsdjsj', {}]"></a-date-picker>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="住宿地址">
          <a-input placeholder="请输入住宿地址" v-decorator="['zsdz', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="单位名称">
          <a-input placeholder="请输入单位名称" v-decorator="['dwmc', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="起始日期">
<!--          <a-input placeholder="请输入起始日期" v-decorator="['qsrq', {}]" />-->
          <a-date-picker  v-decorator="['qsrq', {}]"></a-date-picker>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="结束日期">
<!--          <a-input placeholder="请输入结束日期" v-decorator="['jsrq', {}]" />-->
          <a-date-picker  v-decorator="['jsrq', {}]"></a-date-picker>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="计划出境时间">
<!--          <a-input placeholder="请输入计划出境时间" v-decorator="['jhcjsj', {}]" />-->
          <a-date-picker showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="['jhcjsj', {}]"></a-date-picker>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="计划出境口岸">
          <a-input placeholder="请输入计划出境口岸" v-decorator="['jhcjka', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="计划入境时间">
<!--          <a-input placeholder="请输入计划入境时间" v-decorator="['jhrjsj', {}]" />-->
          <a-date-picker showTime format="YYYY-MM-DD HH:mm:ss" v-decorator="['jhrjsj', {}]"></a-date-picker>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="计划入境口岸">
          <a-input placeholder="请输入计划入境口岸" v-decorator="['jhrjka', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="关注情况">
          <a-input placeholder="请输入关注情况" v-decorator="['gzqk', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="备注">
          <a-input placeholder="请输入备注" v-decorator="['bz', {}]" />
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import Vue from "vue";
  import {ACCESS_TOKEN} from "@/store/mutation-types";
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import {axios} from "@/utils/request";
  export default {
    name: "RyxxModal",
    components:{
      JDictSelectTag,
    },
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        archiveinfo:[

        ],
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        headers:{},
        picUrl: "",
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        uploadLoading:false,
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          add: "/qygl/ryxx/save",
          edit: "/qygl/ryxx/update",
          fileUpload: window._CONFIG['domianURL']+"/sys/common/upload",
          imgerver: window._CONFIG['domianURL']+"/sys/common/view",
        },
      }
    },
    created () {
      const token = Vue.ls.get(ACCESS_TOKEN);
      this.headers = {"X-Access-Token":token}
      this.handleChangeQyInfo();
    },
    computed:{
      uploadAction:function () {
        return this.url.fileUpload;
      }
    },
    methods: {
      handleChangeQyInfo:function () {
        let that =this;
        axios.get("/qygl/qyxx/queryDictInfo",{
          method:"GET",
        }).then(res=>{
          if (res.success){
            // console.log(res.data);
            that.archiveinfo = res.data;
            console.log(that.archiveinfo);
          }
        })
      },
      //图片上传
      beforeUpload: function(file){
        var fileType = file.type;
        if(fileType.indexOf('image')<0){
          this.$message.warning('请上传图片');
          return false;
        }
        //TODO 验证文件大小
      },
      handleChange (info) {
        this.picUrl = "";
        if (info.file.status === 'uploading') {
          this.uploadLoading = true;
          return
        }
        if (info.file.status === 'done') {
          var response = info.file.response;
          this.uploadLoading = false;
          console.log(response);
          if(response.success){
            this.model.tx = response.data;
            this.model.avatar = response.data;
            this.picUrl = "Has no pic url yet";
          }else{
            this.$message.warning(response.data);
          }
        }
      },
      getAvatarView(){
        return this.model.avatar;
      },
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'tx','sj','qx','zwxm','ywxm','sex','gjdq','csrq','zjhm','zjzl','ssqybm','zw','lxdh','sjrjsj','sjrjka','zsdjsj','zsdz','dwmc','qsrq','jsrq','jhcjsj','jhcjka','jhrjsj','jhrjka','gzqk','bz'))
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
            let avatar = that.model.avatar;
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            formData.avatar = avatar;
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
        this.close()
      },
    }
  }
</script>

<style scoped>
.avatar-uploader > .ant-upload {
  width:104px;
  height:104px;
}
.ant-upload-select-picture-card i {
  font-size: 49px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>