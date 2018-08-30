package com.jo.controller;

import com.jo.bean.UserAdmin;
import com.jo.utils.JSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("login")
    @ResponseBody
    public JSONResult userLogin(String username, String password,
    HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return JSONResult.errorMsg("用戶名或者密碼不能為空");
        } else if ("admin".equals(username) && "admin".equals(password)) {
            String token = UUID.randomUUID().toString();
            UserAdmin user = new UserAdmin(username, password, token);
            request.getSession().setAttribute("sessionUser", user);
            return JSONResult.ok();
        } else {
            return JSONResult.errorMsg("登錄失敗");
        }
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("sessionUser");
        return "login";
    }
}
