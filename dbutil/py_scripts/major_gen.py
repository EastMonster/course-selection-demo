from datas import departments

for dept in departments:
    dept_name = dept[0]
    for major in dept[1]:
        print(f"(\"{major}\", \"{dept_name}\"),")