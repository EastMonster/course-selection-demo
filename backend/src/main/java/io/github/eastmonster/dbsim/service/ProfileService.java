package io.github.eastmonster.dbsim.service;

import io.github.eastmonster.dbsim.mapper.InstructorMapper;
import io.github.eastmonster.dbsim.mapper.StudentMapper;
import io.github.eastmonster.dbsim.util.Result;
import io.github.eastmonster.dbsim.util.dto.ChangeableProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private InstructorMapper instructorMapper;

    public Result changePassword(Integer id, ChangeableProfileDTO newProfile) {
        if (newProfile.getIsStudent()) {
            int isOriginRight = studentMapper.verifyStudent(id, newProfile.getOldPassword());
            if (isOriginRight != 1) {
                return Result.error().setMessage("原密码输入错误");
            } else {
                studentMapper.updatePassword(id, newProfile);
                return Result.ok().setMessage("密码修改成功，请重新登录");
            }
        } else {
            int isOriginRight = instructorMapper.verifyInstructor(id, newProfile.getOldPassword());
            if (isOriginRight != 1) {
                return Result.error().setMessage("原密码输入错误");
            } else {
                instructorMapper.updatePassword(id, newProfile);
                return Result.ok().setMessage("密码修改成功，请重新登录");
            }
        }
    }
}
