import { Take } from "./components/instructor/Teaches.vue";

export type GradeStat = {
    highest: number;
    lowest: number;
    average: number;
}

export function getGradeStat(takes: Take[]): GradeStat {
    let max = -Infinity
    let min = Infinity
    let sum = 0
    let validCount = 0;

    for (let i = 0; i < takes.length; i++) {
        if (takes[i].grade === 0) continue

        validCount++
        if (takes[i].grade > max) {
            max = takes[i].grade
        }
        if (takes[i].grade < min) {
            min = takes[i].grade
        }
        sum += takes[i].grade
    }

    const average = parseInt((sum / validCount).toFixed(2))

    return {
        highest: max === -Infinity ? 0 : max,
        lowest: min === Infinity ? 0: min,
        average: isNaN(average) ? 0: average,
    }
}

export function getClassTime(id: number): string {
    const day = ["周一", "周二", "周三", "周四", "周五", "周六", "周日"]
    const startTime = ["8:15", "9:10", "10:15", "11:10", "13:50", "14:45", "15:40", "16:45", "17:40", "19:20", "20:15", "21:10"]

    return day[Math.floor((id - 1) / 12)] + " " + startTime[(id - 1) % 12]
}