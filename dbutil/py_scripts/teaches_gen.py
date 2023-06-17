from datas import sections, dict_courseId2dept, instructors
from itertools import groupby
import random

# 众所周知，大学老师会影分身

inst_sorted = sorted(instructors, key=lambda x: x[2])

for sec in [s for s in sections if s[2] == "春期"]:  # 秋期 or 春期
    # 对于开的每门课，随机请一位老师认领
    dept = dict_courseId2dept.get(str(sec[0])[:2])
    inst_list = []

    inst_by_dept = groupby(inst_sorted, lambda x: x[2])
    for k, x in inst_by_dept:
        if k == dept:
            for item in x:
                inst_list.append(item)

    inst_id = random.choice(inst_list)[0]
    print(f"({inst_id}, {sec[0]}, {sec[1]}, \"{sec[2]}\", {sec[3]}),")