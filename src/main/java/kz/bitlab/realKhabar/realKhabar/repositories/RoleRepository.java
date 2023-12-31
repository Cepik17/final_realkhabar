package kz.bitlab.realKhabar.realKhabar.repositories;

import kz.bitlab.realKhabar.realKhabar.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    List<Role> findRolesById(Long id);
}
