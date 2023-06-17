<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({ data() { return {} } })
</script>

<template>
  <PageHeader />
  <el-divider />

  <div class="main-frame">
    <el-menu class="vertical-menu">
      <el-menu-item index="1" @click="$router.push('/student/index')">
        <template #title><span>总览</span></template>
      </el-menu-item>
      <el-sub-menu index="2">
        <template #title><span>课程管理</span></template>
        <el-menu-item index="2-1" @click="$router.push('/student/selection')">选课</el-menu-item>
        <el-menu-item index="2-2" @click="$router.push('/student/dropping')">退课</el-menu-item>
        <el-menu-item index="2-3" @click="$router.push('/student/schedule')">本学期课表</el-menu-item>
        <el-menu-item index="2-4" @click="$router.push('/student/grades')">成绩查询</el-menu-item>
      </el-sub-menu>
    </el-menu>

    <router-view />
  </div>
</template>

<style scoped>
.vertical-menu {
  width: 200px;
  float: left;
  min-height: 900px;
  /* ... 不得已而为之*/
}

.router-view {
  float: right;
}
</style>