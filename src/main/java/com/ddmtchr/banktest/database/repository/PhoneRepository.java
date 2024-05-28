package com.ddmtchr.banktest.database.repository;

import com.ddmtchr.banktest.database.entities.Phone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends CrudRepository<Phone, Long> {
    Boolean existsByNumber(String number);

}
