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

<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="城市">-->
<!--          <a-input placeholder="请输入城市" v-decorator="['sj', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="区县">-->
<!--          <a-input placeholder="请输入区县" v-decorator="['qx', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="企业类别">-->
<!--          <a-input placeholder="请输入企业类别" v-decorator="['qylb', {}]" />-->
<!--        </a-form-item>-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="企业名称">
          <a-input placeholder="请输入企业名称" v-decorator="['qymc', {}]" />
        </a-form-item>
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="企业编码">-->
<!--          <a-input placeholder="请输入企业编码" v-decorator="['qybm', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="注册资金">-->
<!--          <a-input placeholder="请输入注册资金" v-decorator="['zczj', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="注册地址">-->
<!--          <a-input placeholder="请输入注册地址" v-decorator="['zcdz', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="住宿登记地址">-->
<!--          <a-input placeholder="请输入住宿登记地址" v-decorator="['zcdjdz', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="法人">-->
<!--          <a-input placeholder="请输入法人" v-decorator="['fr', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="联系人">-->
<!--          <a-input placeholder="请输入联系人" v-decorator="['lxr', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="联系电话">-->
<!--          <a-input placeholder="请输入联系电话" v-decorator="['lxdh', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="现有人数">-->
<!--          <a-input placeholder="请输入现有人数" v-decorator="['xyrs', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="目前境内人数">-->
<!--          <a-input placeholder="请输入目前境内人数" v-decorator="['mqjnrs', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="目前境外人数">-->
<!--          <a-input placeholder="请输入目前境外人数" v-decorator="['mqjwrs', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="属地派出所">-->
<!--          <a-input placeholder="请输入属地派出所" v-decorator="['ddpcs', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="出入境责任民警">-->
<!--          <a-input placeholder="请输入出入境责任民警" v-decorator="['crjzrmj', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="走访时间">-->
<!--          <a-input placeholder="请输入走访时间" v-decorator="['zfsj', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="企业地址">-->
<!--          <a-input placeholder="请输入企业地址" v-decorator="['qydz', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="签约方">-->
<!--          <a-input placeholder="请输入签约方" v-decorator="['qyf', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="合同有效期">-->
<!--          <a-input placeholder="请输入合同有效期" v-decorator="['htyxq', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="企业总人数">-->
<!--          <a-input placeholder="请输入企业总人数" v-decorator="['qyzrs', {}]" />-->
<!--        </a-form-item>-->

<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="录入时间 yyyy-MM-dd hh:mm:ss ">-->
<!--          <a-input placeholder="请输入录入时间 yyyy-MM-dd hh:mm:ss " v-decorator="['lrsj', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="最后修改人">-->
<!--          <a-input placeholder="请输入最后修改人" v-decorator="['zhxgr', {}]" />-->
<!--        </a-form-item>-->
<!--        <a-form-item-->
<!--          :labelCol="labelCol"-->
<!--          :wrapperCol="wrapperCol"-->
<!--          label="最后修改时间 yyyy-MM-dd hh:mm:ss ">-->
<!--          <a-input placeholder="请输入最后修改时间 yyyy-MM-dd hh:mm:ss " v-decorator="['zhxgsj', {}]" />-->
<!--        </a-form-item>-->
		
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "CykqyArchiveinfoModal",
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
        url: {
          add: "/qygl/cykqyxx/save",
          edit: "/qygl/cykqyxx/update",
        },
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'sj','qx','qylb','qymc','qybm','zczj','zcdz','zcdjdz','fr','lxr','lxdh','xyrs','mqjnrs','mqjwrs','ddpcs','crjzrmj','zfsj','qydz','qyf','htyxq','qyzrs','lrr','lrsj','zhxgr','zhxgsj'))
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
        this.close()
      },
    }
  }
</script>

<style scoped>

</style>