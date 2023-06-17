<script lang="ts">
import { defineComponent } from 'vue';
export default defineComponent({ data() { return {} } })
</script>

<template>
  <div class="error-page">
    <p style="font-size: 100px;">🤔</p>
    <p style="font-size: 32px;">你是不是走错地方了...？</p>
    <p>404 Not Found</p>
    <el-button @click="$router.back" type="warning">返回上一页</el-button>
  </div>
</template>

<style scoped>
.error-page {
  text-align: center;
}
</style>
