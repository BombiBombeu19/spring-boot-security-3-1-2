package ru.kata.spring_boot_security_3_1_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring_boot_security_3_1_2.model.User;
import ru.kata.spring_boot_security_3_1_2.repository.RoleRepository;
import ru.kata.spring_boot_security_3_1_2.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping()
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserService userServiceImpl
            , RoleRepository roleRepository) {
        this.userService = userServiceImpl;
        this.roleRepository = roleRepository;
    }

    @GetMapping({"/", "/index"})
    public String indexPage() {
        return "index";
    }

    @GetMapping({"/admin/admin"})
    public String adminPage(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "admin/admin";
    }

    @GetMapping("/user/user")
    public String userPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "user/user";
    }

    @GetMapping("/admin/adminAdd")
    public String adminAddPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/adminAdd";
    }

    @PostMapping("/admin/adminAdd")
    public String performAdminAdd(@ModelAttribute("user") @Valid User user
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/adminAdd";
        }
        userService.addUser(user);
        return "redirect:/admin/admin";
    }

    @GetMapping("/admin/adminUpdate")
    public String adminUpdatePage(@RequestParam int id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/adminUpdate";
    }

    @PostMapping("/admin/adminUpdate")
    public String performAdminUpdate(@ModelAttribute("user") @Valid User user
            , BindingResult bindingResult
            , @RequestParam int id) {
        if (bindingResult.hasErrors()) {
            return "admin/adminUpdate";
        }
        userService.updateUser(id, user);
        return "redirect:/admin/admin";
    }

    @GetMapping("/admin/adminDelete")
    public String adminDeletePage(@RequestParam int id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/adminDelete";
    }

    @PostMapping("/admin/adminDelete")
    public String performAdminDelete(@RequestParam int id) {
        userService.deleteUser(id);
        return "redirect:/admin/admin";
    }
}