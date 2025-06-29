package com.example.demo.web.client;

import com.example.demo.dao.UserMapper;
import com.example.demo.model.domain.User;
import com.example.demo.model.domain.user_authority;
import com.example.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userServices;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/regist")
    public String showRegistForm(Model model) {
        model.addAttribute("user", new User());
        return "comm/regist"; // 假设你的注册页面是 src/main/resources/templates/regist.html
    }

    //    @PostMapping("/regist")
//    public String registerUser(@ModelAttribute User user, BindingResult result) {
//        if (result.hasErrors()) {
//            return "comm/regist"; // 如果有错误，返回注册表单
//        }
//        userServices.registerUser(user); // 保存用户信息到数据库
//        return "redirect:/login"; // 注册成功后重定向到登录页面
//    }
    @PostMapping("/regist")
    public String registerUser(@ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            return "comm/regist"; // 如果有错误，返回注册表单
        }
        userServices.registerUser(user); // 保存用户信息到数据库
        Long userId = userMapper.findByName(user.getUsername()).getId();
        user_authority userAuthority = new user_authority();
        userAuthority.setUserid(userId); // 设置从数据库中查询到的userId
        userAuthority.setAuthorityid(2); // 设置authorityId为2
        userMapper.insert(userAuthority);
        return "redirect:/login"; // 注册成功后重定向到登录页面
    }



    @GetMapping("/setting")
    public String showSettingsForm(Model model) {
        model.addAttribute("user", new User()); // 可以添加一个新用户对象或者根据ID获取现有用户
        return "setting"; // 返回包含表单的视图
    }
}
