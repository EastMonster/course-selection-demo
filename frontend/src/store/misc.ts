import { defineStore } from 'pinia'

export const miscStore = defineStore('misc', {
  state: () => {
    return {
      year: {} as number,
      semester: {} as string,
      isSelectionEnabled: {} as boolean,
    }
  },
  persist: true
})