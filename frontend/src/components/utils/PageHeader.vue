<script lang="ts">
import { userStore } from '@/store/user';
import { mapStores } from 'pinia';
import { defineComponent } from 'vue';

export default defineComponent({
  data() {
    return {
      
    }
  },
  computed: {
    ...mapStores(userStore)
  }
})
</script>

<template>
  <div class="header">
    <el-row justify="end">
      <el-col :span="12">
        <h2 style="padding-left: 10px;">选课系统 - {{ userStore.role }}</h2>
      </el-col>
      <el-col :span="12">
        <HeaderUserInfo style="padding: 20px;text-align: right;" />
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.header {
  width: 100%;
  height: auto;
  margin-bottom: 0;
  background: linear-gradient(to right, #4facfe, #0050fe);
  text-align: left;
  color: white;
  border-radius: 16px;
  box-shadow: 0 0 3px #0050fe;
}
</style>