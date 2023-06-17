<script lang="ts">
import { miscStore } from '@/store/misc';
import { userStore } from '@/store/user';
import axios from 'axios';
import { mapStores } from 'pinia';
import { defineComponent } from 'vue';

type StudentStat = {
  gpa: string,
  weightedAvgGrade: string,
  totalCredit: number,
  studiedCourseCount: number,
  failedCourseCount: number
}

export default defineComponent({
  data() {
    return {
      stat: {} as StudentStat,
    }
  },
  methods: {
    async getStat() {
      try {
        const data = await axios.get(`/api/student/${this.userStore.id}/stat`, {
          headers: { Authorization: window.localStorage.getItem('dbsim-access') }
        })
        this.stat = data.data
      } catch (error: any) {
        this.$message.error("请求失败: " + error.toString())
      }
    }
  },
  computed: {
    ...mapStores(userStore),
    ...mapStores(miscStore),
  },
  async created() {
    await this.getStat()
  }
})
</script>

<template>
  <h3 style="text-align: center;">当前是 {{ miscStore.year }} 年{{ miscStore.semester }}。 好好学习，天天向上！🥰</h3>
  <div style="display: flex; justify-content: center; align-items: center;">
    <el-row class="stat-row" justify="space-evenly" align="middle">
      <el-col :span="4">
        <el-statistic title="绩点成绩" :value="parseFloat(stat.gpa)"
          :formatter="(x: { toString: () => any; }) => x.toString()" />
      </el-col>
      <el-col :span="4">
        <el-statistic title="加权平均分" :value="parseFloat(stat.weightedAvgGrade)"
          :formatter="(x: { toString: () => any; }) => x.toString()" />
      </el-col>
      <el-col :span="4">
        <el-statistic title="已修学分" :value="stat.totalCredit" />
      </el-col>
      <el-col :span="4">
        <el-statistic title="已修读课程门数" :value="stat.studiedCourseCount" />
      </el-col>
      <el-col :span="4">
        <el-statistic title="不及格课程门数" :value="stat.failedCourseCount" />
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.stat-row {
  min-width: 600px;
  text-align: center;
  margin-top: 30px;
  padding: 30px;
  border: 1px solid rgb(220, 223, 230);
  border-radius: 32px;
  box-shadow: 1px 1px 2px gray;
}
</style>