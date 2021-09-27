<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="企业编号">
              <a-input placeholder="请输入企业编号" v-model="queryParam.qybh"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="企业名称">
              <a-input placeholder="请输入企业名称" v-model="queryParam.qymc"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
          <a-col :md="6" :sm="8">
            <a-form-item label="英文姓名">
              <a-input placeholder="请输入英文姓名" v-model="queryParam.ywxm"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="性别">
              <a-input placeholder="请输入性别" v-model="queryParam.sex"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="出生日期">
              <a-input placeholder="请输入出生日期" v-model="queryParam.csrq"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="国家地区">
              <a-input placeholder="请输入国家地区" v-model="queryParam.gjdq"></a-input>
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
<!--    <div class="table-operator">-->
<!--      <a-button @click="handleEdit(record)" type="primary" icon="plus">审批</a-button>-->
<!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
<!--        <a-menu slot="overlay">-->
<!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>-->
<!--        </a-menu>-->
<!--        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>-->
<!--      </a-dropdown>-->
<!--    </div>-->

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
          <a @click="handleEdit(record)">处理</a>
          <a-divider type="vertical" />
          <a @click="handleJump(record)" class="table_details">详情</a>
<!--          <a-dropdown>-->
<!--            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>-->
<!--            <a-menu slot="overlay">-->
<!--              <a-menu-item>-->
<!--                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">-->
<!--                  <a>删除</a>-->
<!--                </a-popconfirm>-->
<!--              </a-menu-item>-->
<!--            </a-menu>-->
<!--          </a-dropdown>-->
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <QyYjxxb-modal ref="modalForm" @ok="modalFormOk"></QyYjxxb-modal>
    <QyYjxxXq-modal ref="modalXqForm" @ok="modalFormOk"></QyYjxxXq-modal>
  </a-card>
</template>

<script>
  import QyYjxxbModal from './modules/YjxxModal'
  import QyYjxxXqModal from './modules/YjxxXqModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import {axios} from "@/utils/request";
  export default {
    name: "QyYjxxbList",
    mixins:[JeecgListMixin],
    components: {
      QyYjxxbModal,
      QyYjxxXqModal
    },
    data () {
      return {

        description: '预警信息管理页面',
        // 表头
        columns: [
         /* {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },*/
          {
            title: '企业编号',
            align: 'center',
            dataIndex: 'qybh'
          },
          {
            title: '企业名称',
            align: 'center',
            dataIndex: 'qymc'
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
            title: '出生日期',
            align: 'center',
            dataIndex: 'csrq'
          },
          {
            title: '国家地区',
            align: 'center',
            dataIndex: 'gjdq'
          },
          {
            title: '证件种类',
            align: 'center',
            dataIndex: 'zjzl'
          },
          {
            title: '证件号码',
            align: 'center',
            dataIndex: 'zjhm'
          },
          {
            title: '预警类型',
            align: 'center',
            dataIndex: 'yjlx'
          },
          {
            title: '预警原因',
            align: 'center',
            dataIndex: 'yjyy'
          },
          {
            title: '预警状态',
            align: 'center',
            dataIndex: 'yjzt'
          },
          {
            title: '处理意见',
            align: 'center',
            dataIndex: 'clyj'
          },
          // {
          //   title: '创建时间',
          //   align: 'center',
          //   dataIndex: 'cjsj'
          // },
          // {
          //   title: '创建人',
          //   align: 'center',
          //   dataIndex: 'cjr'
          // },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/qygl/yjxx/listPage",
          delete: "/qygl/yjxx/deleteById",
          deleteBatch: "/qygl/yjxx/deleteBatchByIds",
        },
      }
    },
    computed: {

    },
    methods: {
      handleJump(record) {

        axios.get("/qygl/yjxx/getYjXqById",{
          params:{
            id:record["id"]
          }
        }).then(res=>{
          console.log(res.data)

        })
        this.$refs.modalXqForm.disableSubmit = true;
        this.$refs.modalXqForm.edit(record);
      },
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