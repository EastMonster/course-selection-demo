import random

building = [
    "综合楼B", "综合楼C", "一教A", "一教D"
]

capacity = [40, 65, 85, 125]

for x in building:
    for first in range(1, 6):
        for last in range(1, 7):
            print(f"(\"{x}\", {first}0{last}, {random.choice(capacity)}),")