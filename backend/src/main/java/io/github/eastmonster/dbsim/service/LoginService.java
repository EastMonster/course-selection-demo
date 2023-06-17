package io.github.eastmonster.dbsim.service;

import io.github.eastmonster.dbsim.mapper.InstructorMapper;
import io.github.eastmonster.dbsim.mapper.StudentMapper;
import io.github.eastmonster.dbsim.util.JwtUtil;
import io.github.eastmonster.dbsim.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private InstructorMapper instructorMapper;

    public Result login(Integer id, String password, Boolean isStudent) {
        String token = JwtUtil.generateToken(id, isStudent);
        if (isStudent) {
            if (studentMapper.verifyStudent(id, password) == 1) {
                return Result.ok().addData("token", token);
            } else {
                return Result.error().setMessage("ID 或密码错误");
            }
        } else {
            if (instructorMapper.verifyInstructor(id, password) == 1) {
                return Result.ok().addData("token", token);
            } else {
                return Result.error().setMessage("ID 或密码错误");
            }
        }
    }
}
