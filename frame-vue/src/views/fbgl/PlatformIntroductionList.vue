<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

        <!--  <a-col :md="6" :sm="8">
            <a-form-item label="类型">
              <a-input placeholder="请输入类型" v-model="queryParam.type"></a-input>
            </a-form-item>
          </a-col>-->
          <a-col :md="6" :sm="8">
            <a-form-item label="平台名称">
              <a-input placeholder="请输入平台名称" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

  <!--  &lt;!&ndash; 操作按钮区域 &ndash;&gt;
    <div class="table-operator">
     &lt;!&ndash; <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>&ndash;&gt;
      &lt;!&ndash;<a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>&ndash;&gt;
    </div>-->

    <!-- table区域-begin -->
    <!--<div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>
-->
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
        </span>

      </a-table>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <PlatformIntroduction-modal ref="modalForm" @ok="modalFormOk"></PlatformIntroduction-modal>
  </a-card>
</template>

<script>
  import PlatformIntroductionModal from './modules/PlatformIntroductionModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "PlatformIntroductionList",
    mixins:[JeecgListMixin],
    components: {
      PlatformIntroductionModal
    },
    data () {
      return {
        description: '平台介绍管理页面',
        // 表头
        columns: [
          /*{
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },*/
         /* {
            title: '类型',
            align: 'center',
            dataIndex: 'type'
          },*/
          {
            title: '平台名称',
            align: 'center',
            dataIndex: 'name'
          },
         /* {
            title: '介绍',
            align: 'center',
            dataIndex: 'introduction'
          },*/
          {
            title: '创建时间',
            align: 'center',
            dataIndex: 'cjsj'
          },
          {
            title: '创建人',
            align: 'center',
            dataIndex: 'cjr'
          },
          {
            title: '最后修改时间',
            align: 'center',
            dataIndex: 'zhxgsj'
          },
          {
            title: '最后修改人',
            align: 'center',
            dataIndex: 'zhxgr'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/ptjs/listPage",
          delete: "/ptjs/deleteById",
          deleteBatch: "/ptjs/deleteBatchByIds",
        },
      }
    },
    computed: {

    },
    methods: {
     
    }
  }
</script>
<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 3px
  }
  .ant-card-body .table-operator{
    margin-bottom: 18px;
  }
  .ant-table-tbody .ant-table-row td{
    padding-top:15px;
    padding-bottom:15px;
  }
  .anty-row-operator button{margin: 0 5px}
  .ant-btn-danger{background-color: #ffffff}

  .ant-modal-cust-warp{height: 100%}
  .ant-modal-cust-warp .ant-modal-body{height:calc(100% - 110px) !important;overflow-y: auto}
  .ant-modal-cust-warp .ant-modal-content{height:90% !important;overflow-y: hidden}
</style>
