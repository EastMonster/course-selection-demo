### 数据库模式
**粗体**为实体集合, *斜体*为弱实体集;

<ins>下划线</ins>为主键, *斜体*为外键.


#### 实体集
- **student**(<ins>stu_id</ins>, stu_name, stu_passwd, total_cred, *major_name*)
- **instructor**(<ins>inst_id</ins>,  inst_name, inst_passwd, *dept_name*)
- **department**(<ins>dept_name</ins>)
- **major**(<ins>major_name</ins>, *dept_name*)
- **course**(<ins>course_id</ins>, title, credits, *dept_name*)
- **classroom**(<ins>building</ins>, <ins>room_number</ins>, capacity)
- **time_slot**(<ins>time_slot_id</ins>, start_hr, start_min, end_hr, end_min)

- *section*(*<ins>course_id</ins>*, <ins>sec_id</ins>, <ins>semester</ins>, <ins>year</ins>, selected_stu, max_stu, *building*, *room_number*, *time_slot_id*)

#### 关系集
- **major_dept** (并入 major)
- **stu_dept** (并入 student)
- **inst_dept** (并入 instructor)
- **teaches**(*<ins>inst_id</ins>*, *<ins>course_id</ins>*, *<ins>sec_id</ins>*, *<ins>semester</ins>*, *<ins>year</ins>*)
- **takes**(*<ins>stu_id</ins>*, *<ins>course_id</ins>*, *<ins>sec_id</ins>*, *<ins>semester</ins>*, *<ins>year</ins>*, grade)
- **course_dept** (并入 course)
- *sec_course* (并入 section)
- **sec_time_slot** (并入 section)
- **sec_class** (并入 section)