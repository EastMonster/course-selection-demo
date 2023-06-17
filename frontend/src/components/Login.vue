<script lang="ts">
import { defineComponent } from 'vue';
import { userStore } from '@/store/user';
import { mapStores } from 'pinia'
import { ElMessage } from 'element-plus';
import { miscStore } from '@/store/misc';
import axios from 'axios';

export default defineComponent({
  data() {
    return {
      loginForm: {
        id: "",
        password: "",
        isStudent: true,
      },

      radioButtonStatus: "学生端",
    }
  },
  methods: {
    async login() {
      if (this.loginForm.id.trim() === "" || this.loginForm.password.trim() === "") {
        ElMessage({
          message: "请输入用户名和密码",
          type: 'error'
        })
        this.loginForm.id = this.loginForm.password = ""
      }

      this.loginForm.isStudent = this.radioButtonStatus === "学生端" ? true : false;
      try {
        const loginResult = await axios.post('/api/login', this.loginForm)

        if (loginResult.data.success === true) {
          let path = ""
          ElMessage({ message: "登录成功", type: 'success' })
          window.localStorage.setItem('dbsim-access', loginResult.data.data.token)
          this.userStore.id = parseInt(this.loginForm.id) // ID 在这里已经设置好了

          if (this.loginForm.isStudent === true) {
            path = `/api/student/${this.userStore.id}`
            this.userStore.role = "学生端"
            this.userStore.name = (await this.getName(path)).data.stuName
            this.$router.replace({ path: '/student/index' })
          } else {
            path = `/api/instructor/${this.userStore.id}`
            this.userStore.name = (await this.getName(path)).data.instName
            this.userStore.role = "教师端"
            this.$router.replace({ path: '/instructor/index' })
          }
        } else {
          ElMessage({ message: loginResult.data.message, type: 'error' })
        }
      } catch (error: any) {
        this.$message.error("请求失败: " + error.toString())
      }
    },

    async getName(path: string) {
      return axios.get(path, {
        headers: { Authorization: window.localStorage.getItem('dbsim-access') }
      })
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
    this.loadMisc()
  },
  computed: {
    ...mapStores(userStore),
    ...mapStores(miscStore)
  }
})
</script>

<template>
  <div class="centered-container">
    <div class="login-frame">
      <h2 style="padding-top: 20px;">欢迎使用选课系统</h2>
      <br>
      <el-radio-group v-model="radioButtonStatus">
        <el-radio-button label="学生端" />
        <el-radio-button label="教师端" />
      </el-radio-group>
      <br>
      <el-form class="form" label-position="right" label-width="100px">
        <el-input placeholder="ID" v-model="loginForm.id" style="height: 40px; font-size: 18px;"></el-input>
        <el-input placeholder="密码" v-model="loginForm.password" type="password"
          style="margin-top: 10px; height: 40px; font-size: 18px;"></el-input>
        <el-button type="primary" @click="login" style="margin: 20px;">登录</el-button>
      </el-form>
    </div>
  </div>
</template>

<style scoped>
.centered-container {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: aliceblue;
}

.login-frame {
  background-color: rgba(255, 228, 196, 0.5);
  max-width: 800px;
  height: auto;
  text-align: center;
  border-radius: 32px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}

.form {
  max-width: 400px;
  display: inline-block;
  margin: 20px;
}
</style>