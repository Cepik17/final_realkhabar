package kz.bitlab.realKhabar.realKhabar.services;

import kz.bitlab.realKhabar.realKhabar.dtos.ArticleView;
import kz.bitlab.realKhabar.realKhabar.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getCurrentUser();

    String addNewUser(String fullName, String email, String password, String rePassword);

    String updatePassword(String currentPassword, String newPassword, String reNewPassword);

    String editRole(String email, Long roleId);

    String deleteUser(String email);

    User getUserById(Long userId);

    String getFullNameByAuthorId(Long authorId);
}
