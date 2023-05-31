package ru.itis.springsemwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.springsemwork.models.Token;

@Repository
public interface TokensRepository extends JpaRepository<Token, Long> {

}
