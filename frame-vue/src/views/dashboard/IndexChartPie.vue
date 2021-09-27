<template>
  <div class="Pie">
    <pie title="饼状图" :dataSource="dataSource" :height="320"/>
  </div>
</template>

<script>
  import Pie from '@/components/chart/Pie';

  export default {
    name: "IndexChartPie",
    components: {
      Pie,
    },
    data() {
      return {
        dataSource: [
          {"item": "一月", "percent": 40 },
          { "item": "二月", "percent": 21 },
          { "item": "三月", "percent": 17 },
          { "item": "四月", "percent": 13 },
          { "item": "五月", "percent": 9 }
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
