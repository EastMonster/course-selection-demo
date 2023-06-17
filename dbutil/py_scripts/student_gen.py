from faker import Faker
import random

from datas import departments

fake = Faker("zh_CN")

base = 1000000

for i in range(300):
    pswd = fake.password(length=12, special_chars=True, digits=True, upper_case=True, lower_case=True)
    dept = random.choice(departments)
    major = random.choice(dept[1])
    print(f"({base + i}, \"{fake.name()}\", 0, \"{major}\", \"{pswd}\"),")