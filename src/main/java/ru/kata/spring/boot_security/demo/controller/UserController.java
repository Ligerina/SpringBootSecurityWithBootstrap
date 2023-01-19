package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    private final RoleRepository roleRepository;

    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping(value = "/getUserInfo")
    public String getUserInfo(Principal principal, Model model){
        model.addAttribute("userInfo",userService.getUser(principal.getName()));
        return "UserView";
    }

    @GetMapping(value = "/getEmptyUser")
    public String getEmptyPage(){
        return "EmptyUser";
    }
}
