<script lang="ts">
import { miscStore } from '@/store/misc';
import { userStore } from '@/store/user';
import { mapStores } from 'pinia';
import { defineComponent } from 'vue';
import { ElMessage } from 'element-plus';
import axios from 'axios';

type Section = {
  courseId: number,
  secId: number,
  title: string,
  credits: number
  instName: string,
  selectedStu: number,
  maxStu: number,
  deptName: string,
  building: string,
  roomNumber: number,
  // --------------
  location: string
}

export default defineComponent({
  data() {
    return {
      sections: [] as Section[],
      conditionedSections: [] as Section[],
      dialogVisible: false,
      confirmCourseName: {} as string,
      confirmIndex: {} as number,
      condition: {
        courseId: "" as number | string,
        title: "" as string,
        deptName: "全部" as string
      },
      depts: [
        "经济学院",
        "外国语学院",
        "数学学院",
        "生命科学学院",
        "体育学院",
        "法学院",
        "物理学院",
        "电子信息学院",
        "文学与新闻学院",
        "历史文化学院",
        "化学学院",
        "计算机学院",
        "软件学院",
        "马克思主义学院"
      ]
    }
  },
  computed: {
    ...mapStores(miscStore),
    ...mapStores(userStore),
  },
  methods: {
    async loadMisc() {
      try {
        const res = await axios.get('/api/misc', {
          headers: { Authorization: window.localStorage.getItem('dbsim-access') }
        })
        
        this.miscStore.$state = res.data
      } catch (error: any) {
        this.$message.error("请求失败: " + error.toString())
      }
    },

    async loadSections() {
      if (!this.miscStore.isSelectionEnabled) return

      try {
        const data = await axios.get(`/api/student/${this.userStore.id}/takes/options`, {
          headers: { Authorization: window.localStorage.getItem('dbsim-access') }
        })
        this.sections = data.data
        this.sections.forEach(x => {
          x.location = `${x.building}${x.roomNumber}`
        })
        this.refreshCondition()
      } catch (error: any) {
        this.$message.error("请求失败: " + error.toString())
      }
    },

    refreshCondition() {
      this.conditionedSections = this.sections.filter(x => {
        return ((this.condition.deptName === "全部" ? true : x.deptName === this.condition.deptName)
          && x.title.includes(this.condition.title)
          && x.courseId.toString().includes(this.condition.courseId.toString()))
      })
    },

    cleanCondition() {
      this.condition = {
        courseId: "",
        title: "",
        deptName: "全部"
      }
      this.refreshCondition()
    },

    showDialog(index: number) {
      this.dialogVisible = true;
      this.confirmCourseName = this.conditionedSections[index].title
      this.confirmIndex = index;
    },

    async choose() {
      const targetSec = this.conditionedSections[this.confirmIndex]
      try {
        const data = await axios.post(`/api/student/${this.userStore.id}/takes`, {
          courseId: targetSec.courseId,
          secId: targetSec.secId,
        },
          { headers: { Authorization: window.localStorage.getItem('dbsim-access') } })

        ElMessage({ message: data.data.message, type: data.data.success ? "success" : "error" })

        await this.loadSections()
      } catch (error: any) {
        this.$message.error("请求失败: " + error.toString())
      }
      this.dialogVisible = false;
    }
  },
  async created() {
    await this.loadMisc()
    await this.loadSections()
  }
})
</script>

<template>
  <p v-if="!miscStore.isSelectionEnabled" class="switch-is-off">⚠️当前选课开关未开启</p>
  <div v-else>
    <h3 style="text-align: center;"> 选课 </h3>
    <el-form :inline="true" :model="condition" style="text-align: center;">
      <el-form-item label="课程号">
        <el-input v-model="condition.courseId" />
      </el-form-item>
      <el-form-item label="课程名">
        <el-input v-model="condition.title" />
      </el-form-item>
      <el-form-item label="开设学院">
        <el-select v-model="condition.deptName" placeholder="全部">
          <el-option label="全部" value="全部" />
          <el-option v-for="(item, _) in depts" :label="item" :value="item" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="cleanCondition()">清除</el-button>
        <el-button type="primary" @click="refreshCondition()">查询</el-button>
      </el-form-item>
    </el-form>


    <el-table :data="conditionedSections" style="width: auto;" :max-height="800">
      <el-table-column prop="courseId" label="课程号" />
      <el-table-column prop="secId" label="课序号" />
      <el-table-column prop="title" label="课程名" />
      <el-table-column prop="credits" label="学分" />
      <el-table-column prop="instName" label="教师名" />
      <el-table-column prop="selectedStu" label="已选人数" />
      <el-table-column prop="maxStu" label="课容量" />
      <el-table-column prop="deptName" label="开设学院" />
      <el-table-column prop="location" label="上课地点" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button type="primary" @click="showDialog(scope.$index)">选课</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <el-dialog v-model="dialogVisible" title="请确认操作">
    你确定要选 <strong>{{ confirmCourseName }}</strong> 吗?
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="choose()">确认</el-button>
    </template>
  </el-dialog>
</template>

<style scoped>
p.switch-is-off {
  margin-left: 220px;
  max-width: 200px;
  padding-left: 5px;
  background-color: rgb(232, 76, 61);
  color: white;
  border-radius: 8px;
}
</style>