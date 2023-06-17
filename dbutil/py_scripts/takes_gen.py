# 就自动生成一下上学期和这学期的公共课，毕竟这个比较好演示
# 记得改 section 中的内容
# takes(stu_id, course_id, sec_id, semester, year, grade)

from datas import sections
import random

spring_section = [sec for sec in sections if sec[2] == "春期"]
fall_section = [sec for sec in sections if sec[2] == "秋期"]

# 春期微积分 300000001 - 1, max = 48
for i in range(48):
    print(f"({1000000 + i}, 300000001, 1, \"春期\", 2023, {random.randint(55, 99)}),")
# 春期微积分 300000001 - 2, max = 93
for i in range(93):
    print(f"({1000048 + i}, 300000001, 2, \"春期\", 2023, {random.randint(55, 99)}),")
# 春期微积分 300000001 - 3, max = 57
for i in range(57):
    print(f"({1000141 + i}, 300000001, 3, \"春期\", 2023, {random.randint(55, 99)}),")
# 春期微积分 300000001 - 4, max = 52
for i in range(52):
    print(f"({1000198 + i}, 300000001, 4, \"春期\", 2023, {random.randint(55, 99)}),")
# 300 个学生只有 250 个名额，剩下的下学期补
# 秋期微积分 300000001 - 1, max = 73. 这学期还没出分，正好用来演示
for i in range(50):
    print(f"({1000250 + i}, 300000001, 1, \"秋期\", 2023, 0),")

# 春期大英 200000001 - 2， max = 120
for i in range(120):
    print(f"({1000000 + i}, 200000001, 2, \"春期\", 2023, {random.randint(66, 98)}),")
# 春期大英 200000001 - 1， max = 105
for i in range(105):
    print(f"({1000120 + i}, 200000001, 1, \"春期\", 2023, {random.randint(66, 98)}),")
# 春期大英 200000001 - 3， max = 112
for i in range(75):
    print(f"({1000225 + i}, 200000001, 3, \"春期\", 2023, {random.randint(66, 98)}),")

# 就这样了，能演示就行...