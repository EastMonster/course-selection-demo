from faker import Faker
import random

from datas import departments

fake = Faker("zh_CN")

base = 100000

for i in range(100):
    pswd = fake.password(length=12, special_chars=True, digits=True, upper_case=True, lower_case=True)
    print(f"({base + i}, \"{fake.name()}\", \"{random.choice(departments)[0]}\", \"{pswd}\"),")