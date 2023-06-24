import { defineStore } from 'pinia'

export const userStore = defineStore('user', {
  state: () => {
    return {
      name: {} as string,
      id: {} as number,
      role: {} as string,
    }
  },
  persist: true
})