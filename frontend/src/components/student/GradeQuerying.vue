<script lang="ts">
import { miscStore } from '@/store/misc';
import { userStore } from '@/store/user';
import { mapStores } from 'pinia';
import { defineComponent } from 'vue';
import axios from 'axios';

export type StudentGrade = {
  courseId: number,
  title: string,
  grade: number,
  credits: number,
  semester: string,
  year: number
}

export default defineComponent({
  data() {
    return {
      condition: {
        semester: {} as string,
        year: {} as number | string
      },
      originGrades: [] as StudentGrade[],
      conditionedGrades: [] as StudentGrade[],
    }
  },
  methods: {
    async loadGrades() {
      try {
        const data = await axios.get(`/api/student/${this.userStore.id}/grades`, {
          headers: { Authorization: window.localStorage.getItem('dbsim-access') }
        })
        this.originGrades = data.data
      } catch (error: any) {
        this.$message.error("请求失败: " + error.toString())
      }
    },
    refreshCondition() {
      this.conditionedGrades = this.originGrades.filter(x => {
        return (x.semester === this.condition.semester
          && x.year === parseInt(this.condition.year as string))
      })
    }
  },
  computed: {
    ...mapStores(userStore),
    ...mapStores(miscStore)
  },
  async created() {
    this.condition.year = this.miscStore.year
    this.condition.semester = this.miscStore.semester
    await this.loadGrades()
    this.refreshCondition()
  }
})
</script>

<template>
  <h3 style="text-align: center;"> 成绩查询 </h3>
  <p style="text-align: center;">🐼分数为 0 代表未出分</p>
  <el-form :inline="true" :model="condition" style="text-align: center;">
    <el-form-item label="学年">
      <el-input v-model="condition.year" :placeholder="miscStore.year.toString()" />
    </el-form-item>
    <el-form-item label="学期">
      <el-select v-model="condition.semester" :placeholder="miscStore.semester">
        <el-option label="春期" value="春期" />
        <el-option label="秋期" value="秋期" />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="refreshCondition">查询</el-button>
    </el-form-item>
  </el-form>

  <el-table :data="conditionedGrades" style="width: auto;">
    <el-table-column prop="courseId" label="课程号" />
    <el-table-column prop="title" label="课程名" />
    <el-table-column prop="credits" label="学分" />
    <el-table-column prop="grade" label="成绩" />
  </el-table>
</template>