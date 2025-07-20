package pl.rengreen.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.rengreen.taskmanager.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles")
    List<User> findAllWithRoles();
}
