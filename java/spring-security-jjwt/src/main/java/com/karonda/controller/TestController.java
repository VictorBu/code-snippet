package com.karonda.controller;

import com.karonda.model.*;
import com.karonda.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseModel login(@RequestBody LoginModel loginModel) {
        ResponseModel responseModel = new ResponseModel();

        UserModel userModel = this.getUser(loginModel.getUsername());
        if(userModel == null ||
                !passwordEncoder.matches(loginModel.getPassword(), userModel.getPassword())) {
            responseModel.setCode(2000);
            responseModel.setMsg("用户名或密码错误。");
            return responseModel;
        }

        UserInfoModel userInfoModel = new UserInfoModel();
        BeanUtils.copyProperties(userModel, userInfoModel);

        List<RoleModel> roleList = this.listRole(userModel);
        userInfoModel.setRoleList(roleList);

        JwtModel jwtModel = JwtUtil.generateJwt(userInfoModel);

        responseModel.setCode(0);
        responseModel.setMsg("成功。");
        responseModel.setData(jwtModel);
        return responseModel;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/delete")
    public ResponseModel delete() {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setCode(0);
        responseModel.setMsg("成功。");
        return responseModel;
    }

    @RequestMapping("/test")
    public ResponseModel test() {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setCode(0);
        responseModel.setMsg("成功。");
        return responseModel;
    }

    private UserModel getUser(String username) {
        if("admin".equals(username)) {
            UserModel userModel = new UserModel();
            userModel.setId(1L);
            userModel.setUsername(username);
            userModel.setPassword("$2a$10$om0uDvh8lRPC/dAphzb/pudOOzZwrVVDbI8dMAOWyGBtXcMstxNnW");
            return userModel;
        }
        return null;
    }

    private List<RoleModel> listRole(UserModel userModel) {
        List<RoleModel> roleList = new ArrayList<>();
        RoleModel roleModel = new RoleModel();
        roleModel.setName("ADMIN");
        roleList.add(roleModel);
        return roleList;
    }
}
