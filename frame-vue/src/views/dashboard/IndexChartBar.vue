<template>
  <div class="Bar">
    <bar title="柱状图" :dataSource="dataSource" :height="320"/>
  </div>
</template>

<script>
  import Bar from '@/components/chart/Bar';

  export default {
    name: "IndexChartBar",
    components: {
      Bar,
    },
    data() {
      return {
        dataSource: [
          {"x": "1月", "y": 320},
          {"x": "2月", "y": 457},
          {"x": "3月", "y": 182},
          {"x": "4月", "y": 182},
          {"x": "5月", "y": 182},
          {"x": "6月", "y": 182},
          {"x": "7月", "y": 182},
          {"x": "8月", "y": 182}
        ]

      }
    },
    methods: {
      callback(key) {
        console.log(key);
      }
    }
  }
</script>

<style lang="scss" scoped>

</style>
