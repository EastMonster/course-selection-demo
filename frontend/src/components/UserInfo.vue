<script lang="ts">
import { userStore } from '@/store/user';
import { mapStores } from 'pinia';
import { defineComponent } from 'vue';
import { ElMessage } from 'element-plus';
import axios from 'axios';
import { miscStore } from '@/store/misc';

type UserMajorInfo = {
  deptName: string;
  majorName: string;
}

export default defineComponent({
  data() {
    return {
      majorInfo: {} as UserMajorInfo,
      dialogFormVisible: false,
      newProfileForm: {
        isStudent: true,
        oldPassword: "",
        newPassword: "",
      }
    }
  },
  methods: {
    getUserInfo() {
      let path = this.userStore.role === '学生端' ?
        `/api/student/${this.userStore.id}` :
        `/api/instructor/${this.userStore.id}`
      try {
        axios.get(path, {
          headers: {
            Authorization: window.localStorage.getItem('dbsim-access')
          }
        }).then(res => {
          this.majorInfo = res.data
        })
      } catch (error: any) {
        this.$message.error("用户信息请求失败: " + error.toString())
      }
    },

    submitChange() {
      if (this.newProfileForm.oldPassword.trim() === ""
        || this.newProfileForm.newPassword.trim() === "") {
        ElMessage({ message: "请输入密码", type: "error" })
        return
      }

      if (this.newProfileForm.newPassword.trim().length < 6) {
        ElMessage({ message: "密码长度应大于 6 位", type: "error" })
        this.newProfileForm.newPassword = ""
        return
      }

      this.newProfileForm.isStudent = this.userStore.role === '学生端'

      let path = `${this.newProfileForm.isStudent ? '/api/student' : '/api/instructor'}/${this.userStore.id}`
      try {
        axios.patch(path, this.newProfileForm, {
          headers: {
            Authorization: window.localStorage.getItem('dbsim-access')
          }
        }).then(res => {
          ElMessage({ message: res.data.message, type: res.data.success ? "success" : "error" })
          if (res.data.success) {
            this.$router.replace('/login')
          }
        })
      } catch (error: any) {
        this.$message.error("请求失败: " + error.toString())
      }

      this.newProfileForm.oldPassword = this.newProfileForm.newPassword = ""
    },

    async toggleSwitch(): Promise<boolean> {
      const res = await axios.put('/api/misc', "", {
        headers: { Authorization: window.localStorage.getItem('dbsim-access') }
      })
      if (!res.data.success) {
        this.$message.error(res.data.message)
        return false;
      } else {
        this.$message.success(res.data.message)
        this.miscStore.isSelectionEnabled = res.data.data.isSelectionEnabled
        return true;
      }
    },

    loadMisc() {
      try {
        axios.get('/api/misc', {
          headers: { Authorization: window.localStorage.getItem('dbsim-access') }
        }).then(res => {
          this.miscStore.$state = res.data
        })
      } catch (error: any) {
        this.$message.error("请求失败: " + error.toString())
      }
    }
  },
  created() {
    this.getUserInfo()
    this.loadMisc()
  },
  computed: {
    ...mapStores(userStore),
    ...mapStores(miscStore),
    getUserTitle() {
      return this.userStore.role.slice(0, 2)
    }
  }
})
</script>

<template>
  <h3 style="text-align: center;">个人信息</h3>
  <div class="user-info-list">
    <el-row justify="center">
      <el-col :span="6" :offset="6">
        <el-avatar :size="100" src="src/assets/avatar.jpeg" style="box-shadow: 0px 0px 2px gray;" />
      </el-col>
      <el-col :span="12" style="padding-left: 10px; width: 300px;">
        <h3> {{ userStore.name }} {{ getUserTitle }} </h3>
        <p> {{ userStore.role === "学生端" ? "学号" : "教职工号" }} {{ userStore.id }} </p>
      </el-col>
      <el-divider />
      <el-col>
        <p style="text-align: center;"><strong>学院</strong> {{ majorInfo.deptName }} </p>
      </el-col>
      <el-col>
        <p v-if="majorInfo.majorName != null" style="text-align: center;"><strong>专业</strong> {{ majorInfo.majorName }}
        </p>
      </el-col>
      <el-divider />
      <el-col :span="12" :offset="6">
        <el-switch v-if="userStore.role === '教师端'" :value="miscStore.isSelectionEnabled" active-text="选课已开启"
          inactive-text="选课已关闭" :before-change="toggleSwitch" />

      </el-col>
      <el-col style="text-align: center; margin-top: 30px;">
        <el-button @click="dialogFormVisible = true">修改密码</el-button>
      </el-col>
    </el-row>
  </div>

  <el-dialog v-model="dialogFormVisible" title="修改密码">
    <el-form :model="newProfileForm">
      <el-form-item label="旧密码">
        <el-input v-model="newProfileForm.oldPassword" type="password"></el-input>
      </el-form-item>
      <el-form-item label="新密码">
        <el-input v-model="newProfileForm.newPassword" type="password"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false,
          newProfileForm.newPassword = newProfileForm.oldPassword = ''">取消</el-button>
        <el-button type="primary" @click="submitChange">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.user-info-list {
  display: flex;
  justify-content: center;
  width: auto;
}
</style>