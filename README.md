## 数据库系统和信息管理 课程项目 (2023/6)

### 原始需求
- 教师：
1.	教师登录 
2.	教师个人信息修改 
3.	查询自己教授的课程以及已选课的学生信息
4.	录入教师授课的某课程的所有学生成绩 
5.	其它(如统计课程的平均分，最高分，最低分，~~作试卷分析~~等…) 
- 学生：
1.	学生登录 
2.	学生个人信息修改 
3.	学生可选课程浏览 
4.	学生选课（增删~~改~~自己的选课记录）
5.	学生的课表浏览 
6.	学生所选课程的成绩查询 
7.	其它（如GPA计算，~~自动提醒成绩~~、~~选课时间~~等…） 


### 前端
- 主要语言: Vue, TypeScript


#### 主要涉及到的技术/框架
1. Vue 3, 前端框架
2. Element Plus, 界面组件库
3. Vue Router, 路由组件
4. Pinia, 状态组件

### 后端
请求接口在后端启动后进入 [Swagger UI](http://localhost:8080/swagger-ui.html) 查看

- 主要语言: Java

#### 主要涉及到的技术/框架
1. Spring Boot, 后端框架
2. MySQL, 数据库
3. MyBatis + MyBatis-Plus, 持久层框架
4. Alibaba Druid, 数据库连接池


### 注意事项
在本项目中，你可能会遇到：
1. 周末也不得不学习的学生
2. 影流教派出身的老师
3. 上课→吃饭→继续上同一门课

另外：
1. 没有排课功能，提供的数据里课都是手动排的
2. 选课是随便选，除了课容量没有其他限制
3. 请求接口可能不完全符合 RESTful API
4. 因为项目比较简单，所以大部分业务都写在 Controller 里了
5. 项目中的用户密码以明文存储，甚至还能直接在开发者工具里看到
6. 因为数据量小，所以没有做分页
7. 前后端对接写得很烂，swagger 上看到的返回格式基本上和实际接收的时候都不一样

### 运行
在这之前，先启动 MySQL 服务.
#### 数据库准备
以下步骤在命令行中执行，也可以使用 DBeaver、Navicat 等工具替代
1. 登录数据库 `mysql -u root -p`, 输入密码
2. 创建数据库
   ```sql
   CREATE DATABASE dbsim;
   ```
   > 可以使用其他的数据库名，但是要在后面的步骤中同步修改。
3. 退出数据库
   ```sql
   exit;
   ```
4. 导入数据，这一步需要输入密码
   ```
   mysql -u username -p dbsim < one-step-setup.sql
   ```
   > 导入用的脚本文件 `one-step-setup.sql` 文件已在 `./dbutil` 中提供。如果提示文件不存在，请使用绝对路径。

   >还需要注意的是，在 PowerShell 中这条命令无法执行，请使用 cmd.

5. 再次进入数据库，即重复第 1 步
6. 创建用户
   ```sql
   CREATE USER 'dbsim'@'localhost' IDENTIFIED BY 'ilovescu';
   ```
   可以使用其他的用户名和密码，但是要在后面的步骤中同步修改。
7. 给用户授权
   ```sql
   GRANT ALL PRIVILEGES ON dbsim TO 'dbsim'@'localhost';
   ```

#### 后端
1. 进入 `backend` 文件夹
2. 如果在前面配置数据库的步骤中修改了数据库或者用户的名称和密码，那么到 `/src/main/resource` 下的 `application.properties` 文件中进行相应更改。
3. 进入命令行，在 `backend` 文件夹下运行 `mvnw spring-boot:run`. 

   > 使用 IDEA 打开项目然后运行也是可以的
   
   > 如果提示 `JAVA_HOME` 相关的问题，请先配置好 Java 环境；

   > 本项目是基于 Java 17 开发的，能否使用低版本启动暂不明确，如果出现问题请尝试使用正确的版本；

   > 第一次运行会下载依赖，请耐心等待；

   > 这一步可能因网络原因进度缓慢或无法完成，请自行解决。

#### 前端
1. 安装 node.js
   
   到 [node.js 的官网](https://nodejs.org/zh-cn) 下载并安装 18.16.0 版本
2. 进入 `frontend` 文件夹
   
   进入命令行，执行 `npm i && npm run dev`
   
   这一步可能因网络原因进度缓慢或无法完成，请自行解决。
3. 浏览器打开 `http://localhost:8000` 即可进入系统

### 已知问题
1. 在以学生/教师身份登录时，可以手动输入链接进入教师/学生的界面
2. 一个用户可以在多处登录

##### 主要参考资料
[1天搞定SpringBoot+Vue全栈开发 (bilibili.com)](https://www.bilibili.com/video/BV1nV4y1s7ZN)