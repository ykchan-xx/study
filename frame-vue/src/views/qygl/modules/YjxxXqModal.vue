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
                  label="企业编号">
                  <a-input placeholder="请输入企业编号" disabled="true" v-decorator="['qybh', {}]" />
                </a-form-item>
                <a-form-item
                  :labelCol="labelCol"
                  :wrapperCol="wrapperCol"
                  label="企业名称">
                  <a-input placeholder="请输入企业名称" disabled="true" v-decorator="['qymc', {}]" />
                </a-form-item>
                <a-form-item
                  :labelCol="labelCol"
                  :wrapperCol="wrapperCol"
                  label="英文姓名">
                  <a-input placeholder="请输入英文姓名" disabled="true" v-decorator="['ywxm', {}]" />
                </a-form-item>
                <a-form-item
                  :labelCol="labelCol"
                  :wrapperCol="wrapperCol"
                  label="性别">
                  <a-input placeholder="请输入性别" disabled="true" v-decorator="['sex', {}]" />
                </a-form-item>
                <a-form-item
                  :labelCol="labelCol"
                  :wrapperCol="wrapperCol"
                  label="出生日期">
                  <a-input placeholder="请输入出生日期" disabled="true" v-decorator="['csrq', {}]" />
                </a-form-item>
                <a-form-item
                  :labelCol="labelCol"
                  :wrapperCol="wrapperCol"
                  label="国家地区">
                  <a-input placeholder="请输入国家地区" disabled="true" v-decorator="['gjdq', {}]" />
                </a-form-item>
                <a-form-item
                  :labelCol="labelCol"
                  :wrapperCol="wrapperCol"
                  label="证件种类">
                  <a-input placeholder="请输入证件种类" disabled="true" v-decorator="['zjzl', {}]" />
                </a-form-item>
                <a-form-item
                  :labelCol="labelCol"
                  :wrapperCol="wrapperCol"
                  label="证件号码">
                  <a-input placeholder="请输入证件号码" disabled="true" v-decorator="['zjhm', {}]" />
                </a-form-item>
                <a-form-item
                  :labelCol="labelCol"
                  :wrapperCol="wrapperCol"
                  label="预警类型">
                  <a-input placeholder="请输入预警类型" disabled="true" v-decorator="['yjlx', {}]" />
                </a-form-item>
                <a-form-item
                  :labelCol="labelCol"
                  :wrapperCol="wrapperCol"
                  label="预警原因">
                  <a-input placeholder="请输入预警原因" disabled="true" v-decorator="['yjyy', {}]" />
                </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="预警状态">
          <j-dict-select-tag :triggerChange="true" disabled="true"  v-decorator="['yjzt', {}]" placeholder="请选择预警状态" dictCode="yjzt"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="处理意见">
          <a-textarea placeholder="请输入处理意见" disabled="true" v-decorator="['clyj', {}]" :rows="4" />
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { httpAction } from '@/api/manage'
import pick from 'lodash.pick'
import moment from "moment"

export default {
  name: "YjxxXqModal",
  data () {
    return {
      title:"详情",
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
        add: "/qygl/yjxx/save",
        edit: "/qygl/yjxx/update",
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
        this.form.setFieldsValue(pick(this.model,'qybh','qymc','ywxm','sex','csrq','gjdq','zjzl','zjhm','yjlx','yjyy','yjzt','clyj','cjsj','cjr'))
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