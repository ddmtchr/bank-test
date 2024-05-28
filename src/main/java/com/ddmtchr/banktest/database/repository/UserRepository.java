package com.ddmtchr.banktest.database.repository;

import com.ddmtchr.banktest.database.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByLogin(String login);
    Boolean existsByLogin(String login);
}
