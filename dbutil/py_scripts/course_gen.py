from datas import courses

for course in courses:
    (dept_name, clist) = course
    for c in clist:
        (id, title, credit) = c
        print(f"({id}, \"{title}\", {credit}, \"{dept_name}\"),")