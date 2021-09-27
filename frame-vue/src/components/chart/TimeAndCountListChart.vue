<template>
  <div class="antv-chart-mini">
    <div class="chart-wrapper" :style="{ height: 46 }">
      <v-chart :force-fit="true" :height="height" :data="dataSource" :scale="scale" :padding="[36, 0, 18, 0]">
        <v-tooltip/>
        <v-smooth-area position="时间*人数" :color="color"/>
      </v-chart>
    </div>
  </div>
</template>

<script>
  import moment from 'dayjs'

  const sourceData = []
  const beginDay = new Date().getTime()

  for (let i = 0; i < 10; i++) {
    sourceData.push({
      x: moment(new Date(beginDay + 1000 * 60 * 60 * 24 * i)).format('YYYY-MM-DD'),
      y: Math.round(Math.random() * 10)
    })
  }

  export default {
    name: 'TimeAndCountListChart',
    props: {
      dataSource: {
        type: Array,
        default: () => []
      },
      // x 轴别名
      x: {
        type: String,
        default: '时间'
      },
      // y 轴别名
      y: {
        type: String,
        default: '人数'
      },
      color: {
        type: String,
        default: 'white'
      },
    },
    data() {
      return {
        data: [],
        height: 100
      }
    },
    computed: {
      scale() {
        return [
          { dataKey: '时间', title: this.x, alias: this.x },
          { dataKey: '人数', title: this.y, alias: this.y }
        ]
      }
    },
    /* created() {
       if (this.dataSource.length === 0) {
         this.data = sourceData
       } else {
         this.data = this.dataSource
       }
     }*/
  }
</script>

<style lang="scss" scoped>
  @import "chart";
</style>