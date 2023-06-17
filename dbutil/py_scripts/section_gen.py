from datas import courses, classrooms
import random


for course in courses:
    (dept_name, clist) = course
    for c in clist:
        (id, title, credit) = c
        max_sec_id = random.randint(2, 6)  # [1, 10]
        for sec_id in range(1, max_sec_id + 1):
            (building, room, limit) = random.choice(classrooms)
            starting_slot = random.randint(1, 13 - credit) + 12 * random.randint(0, 6)  # 我们大学生周末是不休息的

            print(f"({id}, {sec_id}, \"春期\", 2023, 0, {int(limit * (random.randint(75, 100) / 100))}, \"{building}\", {room}, {starting_slot}),")
