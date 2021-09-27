<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="标题">
              <a-input placeholder="请输入标题" v-model="queryParam.title"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="发布类型">
              <j-search-select-tag placeholder="请输入类型" v-model="queryParam.type" dict="announceType"/>
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
    <MtSection-modal ref="modalForm" @ok="modalFormOk"></MtSection-modal>
  </a-card>
</template>

<script>
    import MtSectionModal from './modules/MtSectionModal'
    import { JeecgListMixin } from '@/mixins/JeecgListMixin'
    import JSearchSelectTag from "../../components/dict/JSearchSelectTag";

    export default {
        name: "MtSectionList",
        mixins:[JeecgListMixin],
        components: {
            JSearchSelectTag,
            MtSectionModal
        },
        data () {
            return {
                description: '专栏专区管理页面',
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
                    {
                        title: '标题',
                        align: 'center',
                        width:"15%",
                        dataIndex: 'title'
                    },
                    {
                        title: '副标题',
                        align: 'center',
                        dataIndex: 'subtitle'
                    },
                    // {
                    //   title: '内容',
                    //   align: 'center',
                    //   dataIndex: 'content'
                    // },
                    {
                        title: '发布类型',
                        align: 'center',
                        width:"8%",
                        dataIndex: 'type'
                    },
                    // {
                    //   title: '关键字',
                    //   align: 'center',
                    //   dataIndex: 'keyword'
                    // },
                    // {
                    //   title: '图片路径',
                    //   align: 'center',
                    //   dataIndex: 'imgurl'
                    // },
                    // {
                    //   title: '来源',
                    //   align: 'center',
                    //   dataIndex: 'source'
                    // },
                    {
                        title: '发布时间',
                        align: 'center',
                        width:"10%",
                        dataIndex: 'releasetime'
                    },
                    {
                        title: '发布人',
                        align: 'center',
                        width:"5%",
                        dataIndex: 'publisher'
                    },
                    {
                        title: '最后修改时间',
                        align: 'center',
                        width:"10%",
                        dataIndex: 'updatetime'
                    },
                    {
                        title: '最后修改人',
                        align: 'center',
                        width:"5%",
                        dataIndex: 'modifiedby'
                    },
                      // {
                      //     title: '删除标志',
                      //     align: 'center',
                      //     dataIndex: 'deleteflag'
                      // },
                    {
                        title: '操作',
                        dataIndex: 'action',
                        align:"center",
                        width:"10%",
                        scopedSlots: { customRender: 'action' },
                    }
                ],
                url: {
                    list: "/zlzq/queryPage",
                    delete: "/zlzq/deleteById",
                    deleteBatch: "/zlzq/deleteBatchByIds",
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