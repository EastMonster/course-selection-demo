<script lang="ts">
import { miscStore } from '@/store/misc';
import { userStore } from '@/store/user';
import { mapStores } from 'pinia';
import { defineComponent } from 'vue';
import axios from 'axios';
import { ElMessage } from 'element-plus';

type Take = {
  courseId: number,
  secId: number,
  title: string,
  grade: number
}

export default defineComponent({
  data() {
    return {
      originTakesInfo: [] as Take[],
      droppableTakes: [] as Take[],
      dialogVisible: false,
      confirmCourseName: {} as string,
      confirmIndex: {} as number,
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

    async loadTakes() {
      if (!this.miscStore.isSelectionEnabled) return

      try {
        const data = await axios.get(`/api/student/${this.userStore.id}/takes`, {
          headers: { Authorization: window.localStorage.getItem('dbsim-access') }
        })
        this.originTakesInfo = data.data
        this.droppableTakes = this.originTakesInfo.filter(x => { // 过滤掉还没得分的课
          return x.grade == 0
        })
      } catch (error: any) {
        this.$message.error("请求失败: " + error.toString())
      }
    },
    
    showDialog(index: number) {
      this.dialogVisible = true;
      this.confirmCourseName = this.droppableTakes[index].title
      this.confirmIndex = index;
    },

    async drop() {
      const targetTake = this.droppableTakes[this.confirmIndex]
      try {
        const data = await axios.delete(`/api/student/${this.userStore.id}/takes`, {
          params: {
            courseId: targetTake.courseId,
            secId: targetTake.secId,
          },
          headers: { Authorization: window.localStorage.getItem('dbsim-access') }
        })
        ElMessage({ message: data.data.message, type: data.data.success ? "success" : "error" })
      } catch (error: any) {
        this.$message.error("请求失败: " + error.toString())
      }
      await this.loadTakes()
      this.dialogVisible = false;
    },
  },
  async created() {
    await this.loadMisc()
    await this.loadTakes()
  }
})
</script>

<template>
  <p v-if="!miscStore.isSelectionEnabled" class="switch-is-off">⚠️当前选课开关未开启</p>
  <div v-else>
    <h3 style="text-align: center;"> 退课 </h3>
    <p style="text-align: center;"> 😔你只能退掉还没有获得成绩的课! </p>
    <el-table :data="droppableTakes" style="width: auto;">
      <el-table-column prop="courseId" label="课程号" />
      <el-table-column prop="secId" label="课序号" />
      <el-table-column prop="title" label="课程名" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button type="danger" @click="showDialog(scope.$index)">退课</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <el-dialog v-model="dialogVisible" title="请确认操作">
    你确定要退掉 <strong>{{ confirmCourseName }}</strong> 吗?
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="danger" @click="drop()">确认</el-button>
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