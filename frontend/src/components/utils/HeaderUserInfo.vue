<script lang="ts">
import { userStore } from '@/store/user';
import { ElMessage } from 'element-plus';
import { mapStores } from 'pinia';
import { defineComponent } from 'vue';

export default defineComponent({
  data() { return {} },
  methods: {
    logout() {
      this.$router.push('/login')
      ElMessage({ message: "您已退出系统", type: "success" })
    },

    info() {
      if (this.userStore.role === "学生端") {
        this.$router.push('/student/userinfo')
      } else {
        this.$router.push('/instructor/userinfo')
      }
    }
  },
  computed: {
    ...mapStores(userStore),
  }
})
</script>

<template>
  <div class="avatar">
    <el-row>
      <el-col>
        <span>欢迎您, {{ userStore.name }}！</span>
      </el-col>

      <el-col>
        <span>ID: {{ userStore.id }}</span>
        <el-dropdown>
          <span class="el-dropdown-link" >
            选项
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="info">个人信息</el-dropdown-item>
              <el-dropdown-item @click="logout">退出</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.avatar {
  width: auto;
}

.el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  font-size: 16px;
}
</style>