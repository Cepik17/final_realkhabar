package kz.bitlab.realKhabar.realKhabar.repositories;

import kz.bitlab.realKhabar.realKhabar.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
