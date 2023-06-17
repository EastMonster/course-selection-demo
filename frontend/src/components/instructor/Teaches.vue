<script lang="ts">
import { defineComponent } from 'vue';
import { userStore } from '@/store/user';
import { mapStores } from 'pinia';
import { miscStore } from '@/store/misc';
import { getClassTime, getGradeStat, GradeStat } from '@/utils'
import { ElMessage } from 'element-plus';
import axios from 'axios';

export type Teach = {
  courseId: number;
  secId: number;
  title: string;
  selectedStu: number;
  building: string;
  roomNumber: string;
  credits: number;
  timeSlotId: number;
  semester: string;
  year: number;
  // ----------------
  classTime: string;
  location: string;
}

export type Take = {
  stuId: number;
  stuName: string;
  majorName: string;
  grade: number;
}

export default defineComponent({
  data() {
    return {
      originTeachesData: [] as Teach[],
      conditionedTeachesData: [] as Teach[],
      takesData: [] as Take[],
      stuTakeRecord: {} as Take,
      stuNewGrade: "" as number | string,
      stuIndex: 0 as number,

      gradeDialogVisible: false,
      editDialogVisible: false,
      condition: {
        semester: {} as string,
        year: {} as number | string // 被绑定后变字符串了...
      },
      selectedIndex: 0,
      gradeStat: {} as GradeStat,
    }
  },
  methods: {
    async loadTeaches() {
      try {
        const data = await axios.get(`/api/instructor/${this.userStore.id}/teaches/`, {
          headers: { Authorization: window.localStorage.getItem('dbsim-access') }
        });
        this.originTeachesData = data.data
        this.originTeachesData.forEach(x => {
          x.classTime = getClassTime(x.timeSlotId)
          x.location = `${x.building}${x.roomNumber}`
        });
      } catch (error: any) {
        this.$message.error("请求失败: " + error.toString())
      }
    },

    refreshCondition() {
      this.conditionedTeachesData = this.originTeachesData.filter(x => {
        return (x.semester === this.condition.semester
          && x.year === parseInt(this.condition.year as string))
      })
    },

    async showGradeDialog(index: number) {
      this.gradeDialogVisible = true;
      this.selectedIndex = index;
      const courseId = this.conditionedTeachesData[index].courseId

      try {
        const data = await axios.get(`/api/instructor/grade/${courseId}`, {
          params: {
            secId: this.conditionedTeachesData[index].secId,
            semester: this.condition.semester,
            year: this.condition.year,
          },
          headers: { Authorization: window.localStorage.getItem('dbsim-access') }
        })
        this.takesData = data.data
        this.gradeStat = getGradeStat(this.takesData)
      } catch (error: any) {
        this.$message.error("请求失败: " + error.toString())
      }
    },

    getTeachTitle(): string {
      if (this.conditionedTeachesData.length === 0) {
        return ""
      }
      const i = this.selectedIndex
      const courseId = this.conditionedTeachesData[i].courseId
      const secId = this.conditionedTeachesData[i].secId
      const title = this.conditionedTeachesData[i].title
      return `[${courseId}-${secId}] ${title}`
    },

    showEditDialog(index: number) {
      this.editDialogVisible = true
      this.stuIndex = index
      this.stuTakeRecord = this.takesData[index]
    },

    async updateGrade() {
      this.editDialogVisible = false
      if (this.stuNewGrade.toString().trim() === "") {
        ElMessage({ message: "请输入内容", type: "error" })
        return
      }
      const index = this.selectedIndex
      const target = this.conditionedTeachesData[index]
      const courseId = target.courseId
      const stuId = this.stuTakeRecord.stuId
      try {
        const res = await axios.patch(`/api/instructor/grade/${courseId}/student/${stuId}`, {
          secId: target.secId,
          semester: target.semester,
          year: target.year,
          grade: this.stuNewGrade
        }, { headers: { Authorization: window.localStorage.getItem('dbsim-access') } })

        if (res.data.success) {
          this.takesData[this.stuIndex].grade = parseInt(this.stuNewGrade as string) // 本地更新一下，不用再从后端取一遍了
          this.gradeStat = getGradeStat(this.takesData)
        }

        ElMessage({ message: res.data.message, type: res.data.success ? "success" : "error" })
      } catch (error: any) {
        this.$message.error("请求失败: " + error.toString())
      }
      this.stuNewGrade = ""
    }
  },
  async created() {
    this.condition.year = this.miscStore.year
    this.condition.semester = this.miscStore.semester
    await this.loadTeaches()
    this.refreshCondition()
  },
  computed: {
    ...mapStores(userStore),
    ...mapStores(miscStore),
  }
})
</script>

<template>
  <h3 style="text-align: center;"> 全部授课信息 </h3>
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

  <el-table :data="conditionedTeachesData" style="width: auto;">
    <el-table-column prop="courseId" label="课程号" />
    <el-table-column prop="secId" label="课程号" />
    <el-table-column prop="title" label="课程名" />
    <el-table-column prop="selectedStu" label="学生人数" />
    <el-table-column prop="location" label="上课地点" />
    <el-table-column prop="classTime" label="上课时间" />
    <el-table-column prop="credits" label="课程节数" />
    <el-table-column label="操作">
      <template #default="scope">
        <el-button @click="showGradeDialog(scope.$index)">查看学生信息</el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-dialog v-model="gradeDialogVisible" :title="getTeachTitle()">
    <el-row style="text-align: center;" class="stat-row">
      <el-col :span="8">
        <p style="background-color:lightcoral;" class="stat-row-item">最低分: {{ gradeStat.lowest }}</p>
      </el-col>
      <el-col :span="8">
        <p style="background-color:dodgerblue;" class="stat-row-item">平均分: {{ gradeStat.average }}</p>
      </el-col>
      <el-col :span="8">
        <p style="background-color:mediumseagreen;" class="stat-row-item">最高分: {{ gradeStat.highest }}</p>
      </el-col>
    </el-row>
    <el-table :data="takesData" max-height="600">
      <el-table-column type="index" />
      <el-table-column prop="stuId" label="学号" sortable />
      <el-table-column prop="stuName" label="姓名" />
      <el-table-column prop="majorName" label="专业" />
      <el-table-column prop="grade" label="成绩" sortable />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button type="primary" @click="showEditDialog(scope.$index);">更改</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>

  <!-- 不会设置表格可编辑, 只能再跳一个表单了 -->
  <el-dialog v-model="editDialogVisible" title="请确认更新操作">
    正在修改 <strong>{{ stuTakeRecord.stuName }}</strong> 的成绩, 原成绩为 <strong>{{ stuTakeRecord.grade }}</strong> 分.
    <el-form :model="{ stuNewGrade }">
      <el-form-item label="新成绩">
        <el-input v-model="stuNewGrade" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="editDialogVisible = false, stuNewGrade = ''">取消</el-button>
      <el-button type="primary" @click="updateGrade()">确认</el-button>
    </template>
  </el-dialog>
</template>

<style scoped>
.stat-row p.stat-row-item {
  border-radius: 8px;
  color: white;
  margin: 2px;
}
</style>