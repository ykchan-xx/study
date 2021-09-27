<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="中文姓名">
              <a-input placeholder="请输入中文姓名" v-model="queryParam.zwxm"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="英文姓名">
              <a-input placeholder="请输入英文姓名" v-model="queryParam.ywxm"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
          <a-col :md="6" :sm="8">
            <a-form-item label="证件号码">
              <a-input placeholder="请输入证件号码" v-model="queryParam.zjhm"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="单位名称">
              <a-input placeholder="请输入单位名称" v-model="queryParam.dwmc"></a-input>
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
    <Ryxx-modal ref="modalForm" @ok="modalFormOk"></Ryxx-modal>
  </a-card>
</template>

<script>
  import RyxxModal from './modules/RyxxModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "RyxxList",
    mixins:[JeecgListMixin],
    components: {
      RyxxModal
    },
    data () {
      return {
        description: '企业人员管理管理页面',
        // 表头
        columns: [
          // {
          //   title: '#',
          //   dataIndex: '',
          //   key:'rowIndex',
          //   width:60,
          //   align:"center",
          //   customRender:function (t,r,index) {
          //     return parseInt(index)+1;
          //   }
          // },
          // {
          //   title: '头像',
          //   align: 'center',
          //   dataIndex: 'tx'
          // },
          {
            title: '城市',
            align: 'center',
            dataIndex: 'sj'
          },
          // {
          //   title: '区县',
          //   align: 'center',
          //   dataIndex: 'qx'
          // },
          {
            title: '中文姓名',
            align: 'center',
            dataIndex: 'zwxm'
          },
          {
            title: '英文姓名',
            align: 'center',
            dataIndex: 'ywxm'
          },
          {
            title: '性别',
            align: 'center',
            dataIndex: 'sex'
          },
          {
            title: '国家地区',
            align: 'center',
            dataIndex: 'gjdq'
          },
          {
            title: '出生日期 ',
            align: 'center',
            dataIndex: 'csrq'
          },
          {
            title: '证件号码',
            align: 'center',
            dataIndex: 'zjhm'
          },
          {
            title: '证件种类',
            align: 'center',
            dataIndex: 'zjzl'
          },
          // {
          //   title: '所属企业编码',
          //   align: 'center',
          //   dataIndex: 'ssqybm'
          // },
          // {
          //   title: '职位',
          //   align: 'center',
          //   dataIndex: 'zw'
          // },
          // {
          //   title: '联系电话',
          //   align: 'center',
          //   dataIndex: 'lxdh'
          // },
          // {
          //   title: '实际入境时间 ',
          //   align: 'center',
          //   dataIndex: 'sjrjsj'
          // },
          // {
          //   title: '实际入境口岸',
          //   align: 'center',
          //   dataIndex: 'sjrjka'
          // },
          // {
          //   title: '住宿登记时间',
          //   align: 'center',
          //   dataIndex: 'zsdjsj'
          // },
          // {
          //   title: '住宿地址',
          //   align: 'center',
          //   dataIndex: 'zsdz'
          // },
          // {
          //   title: '单位名称',
          //   align: 'center',
          //   dataIndex: 'dwmc'
          // },
          // {
          //   title: '起始日期',
          //   align: 'center',
          //   dataIndex: 'qsrq'
          // },
          // {
          //   title: '结束日期',
          //   align: 'center',
          //   dataIndex: 'jsrq'
          // },
          // {
          //   title: '计划出境时间',
          //   align: 'center',
          //   dataIndex: 'jhcjsj'
          // },
          // {
          //   title: '计划出境口岸',
          //   align: 'center',
          //   dataIndex: 'jhcjka'
          // },
          // {
          //   title: '计划入境时间',
          //   align: 'center',
          //   dataIndex: 'jhrjsj'
          // },
          // {
          //   title: '计划入境口岸',
          //   align: 'center',
          //   dataIndex: 'jhrjka'
          // },
          // {
          //   title: '关注情况',
          //   align: 'center',
          //   dataIndex: 'gzqk'
          // },
          // {
          //   title: '备注',
          //   align: 'center',
          //   dataIndex: 'bz'
          // },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/qygl/ryxx/listPage",
          delete: "/qygl/ryxx/deleteById",
          deleteBatch: "/qygl/ryxx/deleteBatchByIds",
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