package ru.itis.springsemwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itis.springsemwork.models.Item;
import ru.itis.springsemwork.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
//    Optional<User> findByAccessToken(String token);
//    Optional<User> findByRefreshToken(String token);
    boolean existsByEmail(String email);

}
