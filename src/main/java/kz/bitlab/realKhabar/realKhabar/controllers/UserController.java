package kz.bitlab.realKhabar.realKhabar.controllers;

import kz.bitlab.realKhabar.realKhabar.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/signup")
    public String signUpPage() {
        return "signup";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/signup")
    public String registerPage(@RequestParam String fullName,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String rePassword) {
        String value = userService.addNewUser(fullName, email, password, rePassword);
        return "redirect:/" + value;
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/signin")
    public String signInPage() {
        return "signin";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String profilePage() {
        return "profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/updatepassword")
    public String updatePwd(@RequestParam(name = "current_password") String currentPassword,
                            @RequestParam(name = "new_password") String newPassword,
                            @RequestParam(name = "re_new_password") String reNewPassword) {
        String value = userService.updatePassword(currentPassword, newPassword, reNewPassword);
        return "redirect:/" + value;
    }

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping("/editrole")
    public String editRole(@RequestParam String email,
                           @RequestParam Long roleId) {
        String value = userService.editRole(email, roleId);
        return "redirect:/" + value;

    }

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping("/deleteuser")
    public String deleteUser(@RequestParam String email) {
        String value = userService.deleteUser(email);
        return "redirect:/" + value;
    }
}
