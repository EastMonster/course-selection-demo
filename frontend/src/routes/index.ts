import { createRouter, createWebHashHistory } from 'vue-router'
import axios from 'axios'
import Login from '@c/Login.vue'
import Error from '@c/Error.vue'
import UserInfo from '@c/UserInfo.vue'
import StudentMain from '@c/student/StudentMain.vue'
import StudentOverall from '@c/student/StudentOverall.vue'
import CourseSelection from '@c/student/CourseSelection.vue'
import CourseSchedule from '@c/student/CourseSchedule.vue'
import CourseDropping from '@c/student/CourseDropping.vue'
import GradeQuerying from '@/components/student/GradeQuerying.vue'
import InstructorMain from '@c/instructor/InstructorMain.vue'
import Teaches from '@/components/instructor/Teaches.vue'

const routes = [
  { path: '/login', component: Login, alias: '/' },
  { 
    path: '/student',
    redirect: '/student/index', 
    component: StudentMain,
    children: [
      { path: 'index', component: StudentOverall },
      { path: 'userinfo', component: UserInfo },
      { path: 'selection', component: CourseSelection },
      { path: 'dropping', component: CourseDropping },
      { path: 'schedule', component: CourseSchedule },
      { path: 'grades', component: GradeQuerying },
    ] 
  },
  {
    path: '/instructor',
    redirect: '/instructor/index',
    component: InstructorMain,
    children: [
      { path: 'index', component: Teaches },
      { path: 'userinfo', component: UserInfo }
    ]
  },
  { path: '/:catchAll(.*)', component: Error }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes,
})

router.beforeEach((to, _, next) => {
  if (to.path.startsWith('/login')) {
    window.localStorage.removeItem('dbsim-access')
    next()
  } else {
    // 取出 token
    let accessItem = window.localStorage.getItem('dbsim-access')
    if (accessItem === null) {
      next({ path: '/login' })
    } else {
      // 校验 token            
      axios({
        url: '/api/login/check',
        method: 'get',
        headers: {
          Authorization: accessItem
        },
      }).then(res => {
        if (!res.data) {
          console.log('token 校验失败')
          next({ path: '/login' })
        } else {
          next()
        }
      })
    }
  }
})

export default router