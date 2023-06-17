<script lang="ts">
import { miscStore } from '@/store/misc';
import { userStore } from '@/store/user';
import { mapStores } from 'pinia';
import { defineComponent } from 'vue';
import axios from 'axios';


export type Schedule = {
  courseId: number,
  secId: number,
  title: string,
  building: string,
  roomNumber: number,
  timeSlotId: number,
  credits: number,
  // --------------
  location: string
}

export default defineComponent({
  data() {
    return {
      scheduleInfo: [] as Schedule[],
      dayTitle: ['', '周一', '周二', '周三', '周四', '周五', '周六', '周日'],
      schedule: [
        ['8:15~9:00', '-', '-', '-', '-', '-', '-', '-'],
        ['9:10~9:55', '-', '-', '-', '-', '-', '-', '-'],
        ['10:15~11:00', '-', '-', '-', '-', '-', '-', '-'],
        ['11:10~11:55', '-', '-', '-', '-', '-', '-', '-'],
        ['13:50~14:35', '-', '-', '-', '-', '-', '-', '-'],
        ['14:45~15:30', '-', '-', '-', '-', '-', '-', '-'],
        ['15:40~16:25', '-', '-', '-', '-', '-', '-', '-'],
        ['16:45~17:30', '-', '-', '-', '-', '-', '-', '-'],
        ['17:40~18:25', '-', '-', '-', '-', '-', '-', '-'],
        ['19:20~20:05', '-', '-', '-', '-', '-', '-', '-'],
        ['20:15~21:00', '-', '-', '-', '-', '-', '-', '-'],
        ['21:10~21:55', '-', '-', '-', '-', '-', '-', '-'],
      ]
    }
  },
  methods: {
    async getSchedule() {
      try {
        const data = await axios.get(`/api/student/${this.userStore.id}/schedule`, {
          params: {
            year: this.miscStore.year,
            semester: this.miscStore.semester
          },
          headers: { Authorization: window.localStorage.getItem('dbsim-access') }
        })
        this.scheduleInfo = data.data
        this.parseSchedule()
      } catch (error: any) {
        this.$message.error("请求失败: " + error.toString())
      }
    },

    parseSchedule() {
      this.scheduleInfo.forEach(x => {
        x.location = `${x.building}${x.roomNumber}`
        const tsId = x.timeSlotId
        const credit = x.credits
        for (let i = 0; i < credit; i++) {
          this.schedule[(tsId - 1 + i) % 12][Math.floor((tsId - 1) / 12) + 1] = x.title
        }
      })
    }
  },
  computed: {
    ...mapStores(userStore),
    ...mapStores(miscStore)
  },
  created() {
    this.getSchedule()
  }
})
</script>

<template>
  <h3 style="text-align: center;">{{ miscStore.year }} 年{{ miscStore.semester }}课表</h3>
  <el-table :data="schedule" style="width: auto;">
    <el-table-column v-for="(_, index) in schedule[0]" :key="index" :label="dayTitle[index]">
      <template v-slot="{ row }">
        {{ row[index] }}
      </template>
    </el-table-column>
  </el-table>

  <h4 style="text-align: center;">课程详细信息</h4>
  <h5 style="text-align: center;">已选学分数: {{ scheduleInfo.reduce((sum, course) => sum + course.credits, 0) }}</h5>
  <el-table :data="scheduleInfo" style="width: auto;">
    <el-table-column prop="courseId" label="课程号" />
    <el-table-column prop="secId" label="课序号" />
    <el-table-column prop="title" label="课程名" />
    <el-table-column prop="location" label="上课地点" />
    <el-table-column prop="credits" label="学分" />
  </el-table>
</template>