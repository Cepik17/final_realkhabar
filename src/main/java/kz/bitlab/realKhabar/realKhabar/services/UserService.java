package kz.bitlab.realKhabar.realKhabar.services;

import kz.bitlab.realKhabar.realKhabar.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User getCurrentUser();

    String addNewUser(String fullName, String email, String password, String rePassword);

    String updatePassword(String currentPassword, String newPassword, String reNewPassword);

    String editRole(String email, Long roleId);

    String setUserDisabled(String email);

    User getUserById(Long userId);

    String getFullNameByAuthorId(Long authorId);
}
