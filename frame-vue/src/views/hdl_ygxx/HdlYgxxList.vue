<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="员工名字">
              <a-input placeholder="请输入员工名字" v-model="queryParam.ygname"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="员工性别">
              <a-input placeholder="请输入员工性别" v-model="queryParam.ygsex"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
          <a-col :md="6" :sm="8">
            <a-form-item label="员工企业">
              <a-input placeholder="请输入员工企业" v-model="queryParam.ygqy"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="员工出生日期">
              <a-input placeholder="请输入员工出生日期" v-model="queryParam.ygbirth"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="员工身份证号">
              <a-input placeholder="请输入员工身份证号" v-model="queryParam.ygsfzid"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="员工邮箱">
              <a-input placeholder="请输入员工邮箱" v-model="queryParam.ygyx"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="员工号">
              <a-input placeholder="请输入员工号" v-model="queryParam.ygqyid"></a-input>
            </a-form-item>
          </a-col>
          </template>
          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <HdlYgxx-modal ref="modalForm" @ok="modalFormOk"></HdlYgxx-modal>
  </a-card>
</template>

<script>
  import HdlYgxxModal from './modules/HdlYgxxModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "HdlYgxxList",
    mixins:[JeecgListMixin],
    components: {
      HdlYgxxModal
    },
    data () {
      return {
        description: '员工信息管理页面',
        // 表头
        columns: [
          {
            title: '序号',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title: '员工名字',
            align: 'center',
            dataIndex: 'ygname'
          },
          {
            title: '员工头像',
            align: 'center',
            dataIndex: 'tx'
          },
          {
            title: '员工性别',
            align: 'center',
            dataIndex: 'ygsex'
          },
          {
            title: '员工企业',
            align: 'center',
            dataIndex: 'ygqy'
          },
          {
            title: '员工出生日期',
            align: 'center',
            dataIndex: 'ygbirth'
          },
          {
            title: '员工身份证号',
            align: 'center',
            dataIndex: 'ygsfzid'
          },
          {
            title: '员工邮箱',
            align: 'center',
            dataIndex: 'ygyx'
          },
          {
            title: '员工号',
            align: 'center',
            dataIndex: 'ygqyid'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/qygl/hdl_ygxx/listPage",
          delete: "/qygl/hdl_ygxx/deleteById",
          deleteBatch: "/qygl/hdl_ygxx/deleteBatchByIds",
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