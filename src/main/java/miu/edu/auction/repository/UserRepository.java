package miu.edu.auction.repository;

import miu.edu.auction.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    //    @Query("SELECT u.id, u.email, FROM User u where u.email=:email")
    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);
}
