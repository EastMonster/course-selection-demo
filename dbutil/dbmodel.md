### 数据库模式
**粗体**为实体集合, *斜体*为弱实体集;

<u>下划线</u>为主键, *斜体*为外键.


#### 实体集
- **student**(<u>stu_id</u>, stu_name, stu_passwd, total_cred, *major_name*)
- **instructor**(<u>inst_id</u>,  inst_name, inst_passwd, *dept_name*)
- **department**(<u>dept_name</u>)
- **major**(<u>major_name</u>, *dept_name*)
- **course**(<u>course_id</u>, title, credits, *dept_name*)
- **classroom**(<u>building</u>, <u>room_number</u>, capacity)
- **time_slot**(<u>time_slot_id</u>, start_hr, start_min, end_hr, end_min)

- *section*(*<u>course_id</u>*, <u>sec_id</u>, <u>semester</u>, <u>year</u>, selected_stu, max_stu, *building*, *room_number*, *time_slot_id*)

#### 关系集
- **major_dept** (并入 major)
- **stu_dept** (并入 student)
- **inst_dept** (并入 instructor)
- **teaches**(*<u>inst_id</u>*, *<u>course_id</u>*, *<u>sec_id</u>*, *<u>semester</u>*, *<u>year</u>*)
- **takes**(*<u>stu_id</u>*, *<u>course_id</u>*, *<u>sec_id</u>*, *<u>semester</u>*, *<u>year</u>*, grade)
- **course_dept** (并入 course)
- *sec_course* (并入 section)
- **sec_time_slot** (并入 section)
- **sec_class** (并入 section)