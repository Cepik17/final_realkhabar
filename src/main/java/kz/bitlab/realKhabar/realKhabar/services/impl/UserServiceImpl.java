package kz.bitlab.realKhabar.realKhabar.services.impl;

import kz.bitlab.realKhabar.realKhabar.models.User;
import kz.bitlab.realKhabar.realKhabar.models.Role;
import kz.bitlab.realKhabar.realKhabar.repositories.RoleRepository;
import kz.bitlab.realKhabar.realKhabar.repositories.UserRepository;
import kz.bitlab.realKhabar.realKhabar.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return user;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }

    @Override
    public String addNewUser(String fullName, String email, String password, String rePassword) {
        String redirectValue = "signup?errorEmail";
        User user = userRepository.findByEmail(email);
        if (user == null) {
            redirectValue = "signup?errorPassword";
            if (password.equals(rePassword)) {
                user = new User();
                user.setFullName(fullName);
                user.setEmail(email);
                user.setPassword(passwordEncoder.encode(password));
                Role role = roleRepository.findByName("User");
                List<Role> roles = List.of(role);
                user.setRoles(roles);
                userRepository.save(user);
                redirectValue = "signup?success";
            }
        }
        return redirectValue;
    }

    @Override
    public String updatePassword(String currentPassword, String newPassword, String reNewPassword) {
        User currentUser = getCurrentUser();
        String redirectValue = "profile?errorpwd";
        if (passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
            redirectValue = "profile?errorpasswords";
            if (newPassword.equals(reNewPassword)) {
                currentUser.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(currentUser);
                redirectValue = "profile?success";
            }
        }
        return redirectValue;
    }

    @Override
    public String editRole(String email, Long roleId) {
        String redirectValue = "profile?editerror";
        User user = userRepository.findByEmail(email);
        if (user != null) {
            List<Role> roles= roleRepository.findRolesById(roleId);
            user.setRoles(roles);
            userRepository.save(user);
            redirectValue = "profile?editsuccess";
        }
        return redirectValue;
    }

    @Override
    public String deleteUser(String email) {
        String redirectValue = "profile?errordelete";
        User user = userRepository.findByEmail(email);
        if (user != null) {
//            Long id = user.getId();
//            userRepository.deleteById(id);
            userRepository.delete(user);
            redirectValue = "profile?successdelete";
        }
        return redirectValue;
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    @Override
    public String getFullNameByAuthorId(Long authorId) {
        User user = userRepository.findById(authorId).orElseThrow();
        return user.getFullName();
    }
}
