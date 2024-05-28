package com.ddmtchr.banktest.database.repository;

import com.ddmtchr.banktest.database.entities.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends CrudRepository<Email, Long> {
    Boolean existsByEmail(String email);
}
