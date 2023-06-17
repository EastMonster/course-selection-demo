<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({ data() { return {} } })
</script>

<template>
  <PageHeader />
  <el-divider />
  
  <div class="main-frame">
    <el-menu class="vertical-menu">
      <el-menu-item index="1" @click="$router.push('/instructor/index')">
        <template #title><span>课程管理</span></template>
      </el-menu-item>
    </el-menu>

    <router-view />
  </div>
</template>

<style scoped>
.vertical-menu {
  width: 200px;
  float: left;
  min-height: 900px;
}

.router-view {
  float: right;
}
</style>