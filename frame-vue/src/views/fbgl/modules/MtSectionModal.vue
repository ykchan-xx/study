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
          label="标题">
          <a-input placeholder="请输入标题" v-decorator="['title', validatorRules.title]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="副标题">
          <a-input placeholder="请输入副标题" v-decorator="['subtitle',validatorRules.subtitle]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="类型">
          <j-search-select-tag placeholder="请选择发布类型" v-decorator="[ 'type', validatorRules.type]" dict="announceType"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="内容">
          <j-editor style="width: 100%" v-decorator="[ 'content',validatorRules.content ]" triggerChange></j-editor>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
    import { httpAction } from '@/api/manage'
    import pick from 'lodash.pick'
    import moment from "moment"
    import JEditor from "../../../components/jeecg/JEditor";
    import JSearchSelectTag from "../../../components/dict/JSearchSelectTag";

    export default {
        name: "MtSectionModal",
        components: {
            JEditor,
            JSearchSelectTag
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
                    id:{rules: [{ required: true, message: '请输入id!' }]},
                    title:{rules: [{ required: true, message: '请输入标题' }]},
                    subtitle:{rules: [{ required: true, message: '请输入副标题!' }]},
                    type:{rules: [{ required: true, message: '请选择类型!' }]},
                    content:{rules: [{ required: true, message: '请输入内容!' }]},
                },
                url: {
                    add: "/zlzq/save",
                    edit: "/zlzq/gengxin",
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
                    this.form.setFieldsValue(pick(this.model/*,'id'*/,'title','subtitle','content','type'/*,'keyword','imgurl','source','publisher','modifiedby','deleteflag'*/))
                    //时间格式化
                    // this.form.setFieldsValue({releasetime:this.model.releasetime?moment(this.model.releasetime):null})
                    // this.form.setFieldsValue({updatetime:this.model.updatetime?moment(this.model.updatetime):null})
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
                        // formData.releasetime = formData.releasetime?formData.releasetime.format('YYYY-MM-DD HH:mm:ss'):null;
                        //formData.releasetime = formData.releasetime?formData.releasetime.format('YYYY-MM-DD HH:mm:ss'):null;
                        // formData.updatetime = formData.updatetime?formData.updatetime.format('YYYY-MM-DD HH:mm:ss'):moment;
                        //formData.updatetime = formData.updatetime?formData.updatetime.format('YYYY-MM-DD HH:mm:ss'):null;
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