<template>
  <div :style="{ padding: '0' }">
    <h4 :style="{ marginBottom: '20px' }">{{ title }}</h4>

    <v-chart ref="chart" :forceFit="true" :height="height" :data="dataSource" :scale="scale">
      <v-tooltip :shared="false"/>
      <v-axis/>
      <v-line position="时间*增长数" :size="lineSize" :color="lineColor"/>
      <v-area position="时间*增长数" :color="color"/>
      <v-series
        somth = "true"
        position="value*1"
        shape="pointer"
        color="#1890FF"
        :active="false"
      ></v-series>
    </v-chart>
  </div>
</template>

<script>
  import { triggerWindowResizeEvent } from '@/utils/util'

  export default {
    name: 'TimeAndIncrease',
    props: {
      // 图表数据
      dataSource: {
        type: Array,
        required: true
      },
      // 图表标题
      title: {
        type: String,
        default: ''
      },
      // x 轴别名
      x: {
        type: String,
        default: '时间'
      },
      // y 轴别名
      y: {
        type: String,
        default: '增长数'
      },
      // Y轴最小值
      min: {
        type: Number,
        default: 0
      },
      // Y轴最大值
      max: {
        type: Number,
        default: null
      },
      // 图表高度
      height: {
        type: Number,
        default: 254
      },
      // 线的粗细
      lineSize: {
        type: Number,
        default: 2
      },
      // 面积的颜色
      color: {
        type: String,
        default: '#FB9FAB'
      },
      // 线的颜色
      lineColor: {
        type: String,
        default: 'red'
      }
    },
    computed: {
      scale() {
        return [
          { dataKey: '时间', title: this.x, alias: this.x },
          { dataKey: '增长数', title: this.y, alias: this.y, min: this.min, max: this.max }
        ]
      }
    },
    mounted() {
      triggerWindowResizeEvent()
    }
  }
</script>

<style lang="scss" scoped>
  @import "chart";
</style>