package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;

    private final RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping()
    public String printWelcome(ModelMap model) {
        List<User> userList = userService.getListUser();
        model.addAttribute("UserList", userList);
        return "StartPage";
    }

    @RequestMapping(value = "/addNewUser1")
    public String addNewUser(Model model) {
        User user = new User();
        model.addAttribute("User", user);
        List<Role> roleList = roleRepository.findAll();
        model.addAttribute("roleList", roleList);
        return "UserInfo";
    }

    @GetMapping(value = "/addOrUpdate")
    public String AddingPerson(@ModelAttribute("User") User user) {
        System.out.println("Saving the user");
        if(userService.loadUserByUsername(user.getUsername()) != null){ // TODO
            userService.updateUser(user);
        } else {
            userService.saveNewUser(user);
        }
        return "redirect:/admin";
    }

    @GetMapping(value = "/updUser/{id}")
    public String updUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("User",user);
        List<Role> roleList = roleRepository.findAll();
        model.addAttribute("roleList", roleList);
        return "UserInfo";
    }

    @GetMapping(value = "/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
