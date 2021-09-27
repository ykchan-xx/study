<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="签约方负责人">
              <a-input placeholder="请输入签约方负责人" v-model="queryParam.qyffzr"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="协议领队人员">
              <a-input placeholder="请输入协议领队人员" v-model="queryParam.xyldry"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
          <a-col :md="6" :sm="8">
            <a-form-item label="协议开始时间">
              <a-input placeholder="请输入协议开始时间" v-model="queryParam.xykssj"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="协议结束时间">
              <a-input placeholder="请输入协议结束时间" v-model="queryParam.xyjssj"></a-input>
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
    <Xyxx-modal ref="modalForm" @ok="modalFormOk"></Xyxx-modal>
  </a-card>
</template>

<script>
  import XyxxModal from './modules/XyxxModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "XyxxList",
    mixins:[JeecgListMixin],
    components: {
      XyxxModal
    },
    data () {
      return {
        description: '企业人员管理管理页面',
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
            title: '协议编号',
            align: 'center',
            dataIndex: 'xybh'
          },
          {
            title: '企业编码',
            align: 'center',
            dataIndex: 'qybm'
          },
          {
            title: '企业规定人数',
            align: 'center',
            dataIndex: 'qygdrs'
          },
          {
            title: '签约方负责人',
            align: 'center',
            dataIndex: 'qyffzr'
          },
          {
            title: '协议领队人员',
            align: 'center',
            dataIndex: 'xyldry'
          },
          {
            title: '领队联系方式',
            align: 'center',
            dataIndex: 'ldlxfs'
          },
          {
            title: '协议开始时间',
            align: 'center',
            dataIndex: 'xykssj'
          },
          {
            title: '协议结束时间',
            align: 'center',
            dataIndex: 'xyjssj'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/qygl/xyxx/listPage",
          delete: "/qygl/xyxx/deleteById",
          deleteBatch: "/qygl/xyxx/deleteBatchByIds",
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