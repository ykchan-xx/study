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
          label="平台名称">
          <a-input placeholder="请输入平台名称" v-decorator="['name', {}]" />
        </a-form-item>

        <a-form-item
          lable="内容" >

        <j-editor style="width: 100%" v-decorator="['introduction', {} ]" triggerChange></j-editor>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JEditor from "../../../components/jeecg/JEditor"

  export default {
    name: "PlatformIntroductionModal",
    components:{
      JEditor,
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
        url: {
          add: "/ptjs/save",
          edit: "/ptjs/update",
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
            this.form.setFieldsValue(pick(this.model,'name','introduction'))
/*		  //时间格式化
            this.form.setFieldsValue({cjsj:this.model.cjsj?moment(this.model.cjsj):null})
            this.form.setFieldsValue({zhcjsj:this.model.zhcjsj?moment(this.model.zhcjsj):null})*/
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
            /*formData.cjsj = formData.cjsj?formData.cjsj.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.zhcjsj = formData.zhcjsj?formData.zhcjsj.format('YYYY-MM-DD HH:mm:ss'):null;*/
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