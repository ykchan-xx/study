<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="企业类别">
              <a-input placeholder="请输入企业类别" v-model="queryParam.qylb"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="企业名称">
              <a-input placeholder="请输入企业名称" v-model="queryParam.qymc"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="企业编码">
                <a-input placeholder="请输入企业编码" v-model="queryParam.qybm"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="法人">
                <a-input placeholder="请输入法人" v-model="queryParam.fr"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="联系电话">
                <a-input placeholder="请输入联系电话" v-model="queryParam.lxdh"></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :md="6" :sm="8">
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
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete"/>
            删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作
          <a-icon type="down"/>
        </a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a
          style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
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

          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down"/></a>
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
    <Archiveinfo-modal ref="modalForm" @ok="modalFormOk"></Archiveinfo-modal>
  </a-card>
</template>

<script>
import ArchiveinfoModal from './modules/ArchiveinfoModal'
import {JeecgListMixin} from '@/mixins/JeecgListMixin'

export default {
  name: "ArchiveinfoList",
  mixins: [JeecgListMixin],
  components: {
    ArchiveinfoModal
  },
  data() {
    return {
      description: '企业档案管理界面',
      // 表头
      columns: [
       /* {
          title: '序号',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: "center",
          customRender: function (t, r, index) {
            return parseInt(index) + 1;
          }
        },*/
        /*{
          title: '企业类别',
          align: 'center',
          dataIndex: 'qylb'
        },*/
        {
          title: '企业名称',
          align: 'center',
          dataIndex: 'qymc'
        },
        {
          title: '企业编码',
          align: 'center',
          dataIndex: 'qybm'
        },
        {
          title: '注册资金',
          align: 'center',
          dataIndex: 'zczj'
        },
       /* {
          title: '注册地址',
          align: 'center',
          dataIndex: 'zcdz'
        },*/
       /* {
          title: '住宿登记地址',
          align: 'center',
          dataIndex: 'zcdjdz'
        },*/
        {
          title: '法人',
          align: 'center',
          dataIndex: 'fr'
        },
        {
          title: '联系人',
          align: 'center',
          dataIndex: 'lxr'
        },
        {
          title: '联系电话',
          align: 'center',
          dataIndex: 'lxdh'
        },
        {
          title: '企业总人数',
          align: 'center',
          dataIndex: 'qyzrs'
        },
        {
          title: '现有人数',
          align: 'center',
          dataIndex: 'xyrs'
        },
        {
          title: '目前境内人数',
          align: 'center',
          dataIndex: 'mqjnrs'
        },
       /* {
          title: '目前境外人数',
          align: 'center',
          dataIndex: 'mqjwrs'
        },*/
        {
          title: '市级',
          align: 'center',
          dataIndex: 'sj'
        },
        {
          title: '区县',
          align: 'center',
          dataIndex: 'qx'
        },
        {
          title: '企业地址',
          align: 'center',
          dataIndex: 'qydz'
        },
        {
          title: '属地派出所',
          align: 'center',
          dataIndex: 'ddpcs'
        },
        {
          title: '合同有效期',
          align: 'center',
          dataIndex: 'htyxq'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: "center",
          scopedSlots: {customRender: 'action'},
        }
      ],
      url: {
        list: "/qygl/qyxx/listPage",
        delete: "/qygl/qyxx/deleteById",
        deleteBatch: "/qygl/qyxx/deleteBatchByIds",
      },
    }
  },
  computed: {},
  methods: {}
}
</script>
<style lang="less" scoped>
/** Button按钮间距 */
.ant-btn {
  margin-left: 3px
}

.ant-card-body .table-operator {
  margin-bottom: 18px;
}

.ant-table-tbody .ant-table-row td {
  padding-top: 15px;
  padding-bottom: 15px;
}

.anty-row-operator button {
  margin: 0 5px
}

.ant-btn-danger {
  background-color: #ffffff
}

.ant-modal-cust-warp {
  height: 100%
}

.ant-modal-cust-warp .ant-modal-body {
  height: calc(100% - 110px) !important;
  overflow-y: auto
}

.ant-modal-cust-warp .ant-modal-content {
  height: 90% !important;
  overflow-y: hidden
}
</style>