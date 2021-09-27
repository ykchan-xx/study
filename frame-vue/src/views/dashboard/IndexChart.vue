<template>
  <div class="page-header-index-wide">
    <a-row :gutter="24" style="padding: 5px 0 0px;" id="topRow">
      <a-col :md="6" :sm="24">
        <div class="chart-card">
          <div class="chart-card-header">
            <img src="../../assets/index/1-1.png"/><span class="chart-card-title" style="color: white">企业数量</span>
            <span class="total" style="color: white">{{countQy}}</span>
          </div>
        </div>
      </a-col>

      <a-col :md="6" :sm="24">
        <div class="chart-card ">
          <a-row :gutter="24">
            <div class="chart-card-header">
              <img src="../../assets/index/2-1.png"/><span class="chart-card-title" style="color: white">企业人员数量</span>
              <span class="total" style="color: white">{{countQyrs}}</span>
            </div>
          </a-row>
        </div>
      </a-col>

      <a-col :md="6" :sm="24">
        <div class="chart-card ">
          <a-row :gutter="24">
            <div class="chart-card-header">
              <img src="../../assets/index/2-1.png"/><span class="chart-card-title" style="color: white">协议档案数量</span>
              <span class="total" style="color: white">{{countXyda}}</span>
            </div>
          </a-row>
        </div>
      </a-col>

      <a-col :md="6" :sm="24">
        <div class="chart-card ">
          <a-row :gutter="24">
            <div class="chart-card-header">
              <img src="../../assets/index/2-1.png"/><span class="chart-card-title" style="color: white">黑名单数量</span>
              <span class="total" style="color: white">{{countHmd}}</span>
            </div>
          </a-row>
        </div>
      </a-col>
    </a-row>

    <a-row :gutter="24" style="padding: 5px 0px 0;" id="botRow">
      <a-col :md="16" :sm="24">
        <div class="content-box">
          <div class="content-title" style="color: gray">预警提示</div>
          <div id="yj" :style='"height:270 px"'>
            <!--轮播图-->
            <swiper :options="swiperOption" class="swiper-box">
              <swiper-slide v-for="(item, index) in dataItem" :key="index">
                <div
                  class="content-c"
                  :data-type="val.zjf"
                  v-for="(val,y) in item"
                  :key="y"
                  style="margin:3px;"
                >
                  <div class="cont-item">
                    <a-row>
                      <div
                        class="contents"
                        style="white-space:nowrap;text-overflow:ellipsis;overflow:hidden"
                        :title="val.jfsy"
                      >{{val.zjf+" - "+val.jfsy}} <span class="times">{{val.cssj}}</span></div>
                    </a-row>
                  </div>
                </div>
              </swiper-slide>
              <div class="swiper-pagination" slot="pagination"></div>
            </swiper>
          </div>
        </div>
      </a-col>
      <a-col :md="8" :sm="24">
        <div class="content-box">
          <div class="content-title" style="color: gray">人员总量增长分析(/月)</div>
          <div style>
            <time-and-increase :dataSource="dataSourceRyZzfx" :height="220" />
          </div>
        </div>
      </a-col>
    </a-row>
  </div>
</template>

<script>
  import {axios} from '../../utils/request'
  import tabBar from './IndexChartBar'
  import tabPie from './IndexChartPie'
  import Bar from '@/components/chart/Bar'
  import AreaChartTy from '@/components/chart/AreaChartTy' //面积图
  import TimeAndCountListChart from '@/components/chart/TimeAndCountListChart.vue'
  import TimeAndIncrease from '@/components/chart/TimeAndIncrease.vue'
  import ACol from 'ant-design-vue/es/grid/Col'
  import ATooltip from 'ant-design-vue/es/tooltip/Tooltip'
  import MiniProgress from '@/components/chart/MiniProgress'
  import RankList from '@/components/chart/RankList'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import HeadInfo from '@/components/tools/HeadInfo.vue'
  import {swiper, swiperSlide} from 'vue-awesome-swiper'
  import 'swiper/dist/css/swiper.css'
  // import { set } from 'vue/types/umd'

  export default {
    name: 'IndexChart',
    components: {
      tabBar,
      tabPie,
      Bar,
      AreaChartTy,
      TimeAndCountListChart,
      TimeAndIncrease,
      ATooltip,
      ACol,
      MiniProgress,
      RankList,
      LineChartMultid,
      HeadInfo,
      axios,
      swiper,
      swiperSlide,
    },
    data() {
      return {
        addImg: require('@/assets/testImg/add.png'),
        jhImg: require('@/assets/testImg/sub.png'),
        selectedRowKeys: [],
        countQy: '',
        countQyrs: '',
        countXyda: '',
        countHmd: '',
        dataMaue: [],
        dataSource: [],
        dataSourceQy: [],
        dataSourceQyrs: [],
        dataSourceXyda: [],
        dataSourceHmd: [],
        dataSourceRyZzfx: [],
        dataSourceZdy: [],
        dataSourceYjcd: [],
        dataSourceyjzcd: [],
        imgSrc: require('@/assets/daiban.png'),
        dataSource1: [
          // 所有的 percent 相加等于 100
          {item: '一月', percent: 40},
          {item: '二月', percent: 21},
          {item: '三月', percent: 17},
          {item: '四月', percent: 13},
          {item: '五月', percent: 9},
        ],
        dataItem: [],
        swiperOption: {
          autoplay: 5000,
          pagination: '.swiper-pagination',
          loop: true,
        },
        //表格数据
        datasList: [],
        // 表头
        columns: [
          {
            title: '序号',
            dataIndex: '',
            key: 'rowIndex',
            width: 80,
            align: 'center',
            customRender: function (t, r, index) {
              return parseInt(index) + 1
            },
          },
          {
            title: '姓名',
            align: 'center',
            dataIndex: 'YWXM',
          },
          {
            title: '性别',
            align: 'center',
            dataIndex: 'XB',
          },
          {
            title: '国家地区',
            align: 'center',
            dataIndex: 'GJDQ',
          },
          {
            title: '出生日期',
            align: 'center',
            dataIndex: 'CSRQ',
          },
          {
            title: '操作',
            dataIndex: 'action',
            align: 'center',
            scopedSlots: {customRender: 'action'},
          },
        ],
        navList: [

        ],
        imgs: [
          require('@/assets/indexIcons/ruku.png'),
          require('@/assets/indexIcons/shouli.png'),
          require('@/assets/indexIcons/daiban.png'),
          require('@/assets/indexIcons/zhenjian.png'),
          require('@/assets/indexIcons/dangan.png'),
          require('@/assets/indexIcons/laifang.png'),
        ],
        showShadowBox: false,
        kjjlistindex: 0,
        echartHeight: 500,
        yjHeight: 500,
      }
    },
    created() {
      this.showQyCount()
      this.showQyrsCount()
      this.showXydaCount()
      this.showHmdCount()
      this.showRyfx(this.dataSourceRyZzfx)
      this.showHighRisk(this.datasList)
      this.showYjShow(this.dataItem)
    },
    mounted() {
      this.getType()
      this.getEchartHeight()
    },
    methods: {
      /*企业数量*/
      showQyCount() {
        axios({
          method: 'Get',
          url: '/statistics/index/getQyCount',
        }).then((res) => {
          if (JSON.stringify(res) != '[]') {
            this.countQy = res.data
          } else {
            this.countQy = 0
          }
        })
      },
      /*企业人数*/
      showQyrsCount() {
        axios({
          method: 'Get',
          url: '/statistics/index/getQyrsCount',
        }).then((res) => {
          if (JSON.stringify(res) != '[]') {
            this.countQyrs = res.data
          } else {
            this.countQyrs = 0
          }
        })
      },
      /*协议档案数量*/
      showXydaCount() {
        axios({
          method: 'Get',
          url: '/statistics/index/getXyzsCount',
        }).then((res) => {
          if (JSON.stringify(res) != '[]') {
            this.countXyda = res.data
          } else {
            this.countXyda = 0
          }
        })
      },
      /*黑名单数量*/
      showHmdCount() {
        axios({
          method: 'Get',
          url: '/statistics/index/getHmdCount',
        }).then((res) => {
          if (JSON.stringify(res) != '[]') {
            this.countHmd = res.data
          } else {
            this.countHmd = 0
          }
        })
      },

      /*境外人员建档总量增长分析*/
      showRyfx(dataSourceRyZzfx) {
        axios({
          method: 'Get',
          url: '/qygl/ryxx/getRyxxMonthCount',
        }).then((res) => {
          if (JSON.stringify(res) != '[]') {
            //console.log(res.data)
            for (var i = 0; i < res.data.length; i++) {
              var map = {时间: '', 增长数: 0}
              map['时间'] = res.data[i]['rq'] + "月";
              map['增长数'] = res.data[i]['rs'];
              dataSourceRyZzfx.push(map);
            }
          } else {
          }
        })
        return this.dataSourceRyZzfx
      },
      /*境外人员高风险人员列表*/
      showHighRisk(datasList) {
        axios({
          method: 'Get',
          url: '/qygl/yjxx/getGfxryList',
        }).then((res) => {
          if (JSON.stringify(res) != '[]') {
            for (let i = 0; i < res.data.length; i++) {
              var map = {}
              for (let y in res.data[i]) {
                map[y] = res.data[i][y]
              }
              datasList.push(map)
            }
          } else {
          }
        })
        return datasList
      },
      /*境外人员高风险人员列表*/
      showYjShow(dataItem) {
        axios({
          method: 'Get',
          url: 'qygl/yjxx/listPage',
        }).then((res) => {
          if (JSON.stringify(res) != '[]') {
            var zc = parseInt(res.data.records.length / 5)
            var ys = res.data.length % 5
            for (let i = 1; i <= zc; i++) {
              var list = []
              var num = 0
              for (let y = i * 5 - 5; y < i * 5; y++) {
                var map = {}
                map['jfsy'] = res.data.records[y]['yjlx']
                map['zjf'] = res.data.records[y]['ywxm']
                map['cssj'] = res.data.records[y]['cjsj']
                list.push(map)
              }
              if (dataItem.length < 20) {
                dataItem.push(list)
              }

            }

            var list1 = []
            for (let i = zc * 5; i < zc * 5 + ys; i++) {
              var map = {}
              map['jfsy'] = res.data.records[y]['yjyp']
              map['zjf'] = res.data.records[y]['ywxm']
              map['cssj'] = res.data.records[y]['cjsj']
              list1.push(map)
            }
            if (dataItem.length < 20) {
              dataItem.push(list1)
            }
          } else {
          }
        })

        return this.dataItem
      },

      callback(key) {
        //console.log(key);
      },
      getType() {
        /*颜色节点获取还需要调整*/
        var t = document.getElementById('yj')
        var dom = document.getElementById('yj').children[0].getElementsByClassName('swiper-box')
        for (var i = 0; i < dom.length; i++) {
          for (var y = 0; y < dom[i].length; y++) {
            if (2 <= parseInt(dom[i].children[y].getAttribute('data-type')) <= 3.5) {
              dom[i].children[0].children[y].style.borderColor = '#FB8868'
            } else if (1 <= dom[i].children[y].getAttribute('data-type') < 2) {
              dom[i].children[0].children[y].style.borderColor = '#7BA4F0'
            } else if (0 < dom[i].children[y].getAttribute('data-type') < 1) {
              dom[i].children[0].children[y].style.borderColor = '#39B87B'
            }
          }
        }
      },
      handleJump(record) {
        var rybh2 = record['RYBH']
        this.$router.push({name: 'wgrDetail', params: {rybh: rybh2}})
      },
      handleEdit(record) {
        this.$router.push({path: '/wgrDetail', query: record})
      },
      onSelectChange(selectedRowKeys) {
        console.log('selectedRowKeys changed: ', selectedRowKeys)
        this.selectedRowKeys = selectedRowKeys
      },
      getEchartHeight() {
        let winHeight = window.innerHeight;
        let h1 = document.getElementById("topRow").clientHeight;
        this.echartHeight = winHeight - h1 - 375;
        this.yjHeight = this.echartHeight;
      }
    },
  }
</script>

<style lang="scss" scoped>
  $color: '#FB8868';
  $color1: '#7BA4F0';
  $color: '#39B87B';

  .kjjliname {
    list-style: none;
    display: inline-block;
    margin: 3px 4px;
    user-select: none;
    cursor: pointer;
  }

  .kjjliname:hover {
    color: rgb(51, 11, 161);
  }

  .usernav_box {
    // height: 260px;
    min-height: 130px;
    max-height: 260px;
    overflow-y: auto;
  }

  .othernav,
  .userusednav {
    width: 16%;
    height: 130px;
    float: left;
    text-align: center;
    padding: 20px;
    position: relative;

    .addicon {
      position: absolute;
      top: 10px;
      right: 20px;
      cursor: pointer;
    }

    .othernavname {
      width: 90%;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
      margin: 5px auto 0;
    }
  }

  .page-header-index-wide {
    color: #fff;

    > div {
      margin: 0 !important;
      padding: 0 10px;

      .homepage {
        font-size: 20px;
        font-weight: bold;
        padding: 0 15px;
        color: #fff;

        img {
          width: 35px;
          height: 35px;
          border-radius: 50%;
          margin-right: 10px;
        }
      }

      .search-input {
        width: 80%;
        background-color: rgba(255, 255, 255, 0.85);
        border-radius: 10px;
        border: 1px solid #ddd;
        margin-left: 18%;

        input {
          border: none;
          border-radius: 10px;
          /*width: 90%;*/
          outline: none !important;
          list-style: none;
          background: none;
          color: #5a84fb;
          font-size: 20px;

          &:focus {
            outline: none;
          }
        }
      }

      .chart-card {
        //background-image: linear-gradient(to right, #7ecce0, #2e98d9);
        background: url('../../assets/index/bg1.png') no-repeat;
        background-size: 100% 100%;
        height: 140px;
        padding: 10px 5%;
        border-radius: 10px;

        &.chart-card-cz {
          //background-image: linear-gradient(to right, #a453fc, #7a62ec);
          background: url('../../assets/index/bg2.png') no-repeat;
          background-size: 100% 100%;
        }

        &.chart-card-ap {
          //background: url('../../assets/img-4.png') no-repeat;
          background: url('../../assets/index/bg3.png') no-repeat;
          background-size: 100% 100%;

          .primary-top {
            font-size: 1.5rem;
            text-align: center;
            height: 60px;

            .imgspan {
              padding: 20px 20px;
              border-bottom: 1px rgba(255, 255, 255, 0.5) solid;
            }

            img {
              height: 30px;
            }
          }

          .primary-top-btn {
            text-align: center;

            .primary-btn {
              font-size: 1rem;
              background: rgba(255, 255, 255, 0.2);
              color: #fff;
              font-weight: 800;
              border: none;
              margin-top: 15px;
              border-radius: 20px;
              letter-spacing: 5px;
              border: 1px #fff solid;
              font-family: "Microsoft YaHei,SimHei";

              img {
                width: 43px;
                height: 43px;
                margin-left: 20px;
              }
            }
          }

        }

        .chart-card-top {
          margin-bottom: 25px;

          img {
            width: 24px;
            height: 18px;
          }

          span {
            float: right;
          }
        }

        .chart-card-header {
          font-size: 1.25rem;
          position: relative;
          height: 50px;
          line-height: 50px;
          border-bottom: 1px rgba(255, 255, 255, 0.5) solid;

          img {
            width: 25px;
            height: 25px;
            margin-top: -5px;
          }

          .chart-card-title {
            margin-left: 20px;
            letter-spacing: 2px;
            font-weight: 600;
          }

          .total {
            font-size: 2rem;
            letter-spacing: 5px;
            position: absolute;
            right: 10px;
          }
        }


      }

      .content_nav {
        background-color: #fff;
        border-radius: 10px;
        color: #333;
        display: flex;

        > div {
          flex: 1;
          text-align: center;
          padding: 20px 0 15px;

          img {
            width: 80px;
            height: 80px;
            margin-bottom: 10px;
            cursor: pointer;
          }

          p {
            width: 150px;
            margin: 0 auto;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
            font-size: 16px;
            user-select: none;
          }
        }
      }

      .content-box {
        background: #fff;
        border-radius: 11px;
        border: 1px solid #e8e8e8;

        .content-title {
          color: #527efb;
          font-weight: bold;
          letter-spacing: 2px;
          border-bottom: 1px solid #e8e8e8;
          padding: 10px 15px;
          font-size: 1.15rem;
        }

        .content-c {
          padding: 10px 5px;

          &:nth-child(even) {
            background-color: #f8f8f8;
          }

          &:hover {
            background: #eeeeee;
          }

          > div {
            color: #7e7e7e;
            border-left: 5px solid #ddd;
            padding: 0 10px;

            .titles {
              color: #000;
              font-size: 1rem;
            }

            .times {
              float: right;
              color: #bcbcbc;
            }
          }

          .contents {
            margin-top: 5px;
            white-space: nowrap;
            text-overflow: ellipsis;
            overflow: hidden;
            word-break: break-all;
          }
        }

        .ant-tabs-nav-wrap {
          padding: 0 15px;
        }
      }
    }

    a :hover {
      text-decoration: underline;
      padding-left: 2px;
    }
  }
</style>
