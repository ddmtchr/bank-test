package com.ddmtchr.banktest.services;

import com.ddmtchr.banktest.database.entities.Email;
import com.ddmtchr.banktest.database.entities.Phone;
import com.ddmtchr.banktest.database.entities.User;
import com.ddmtchr.banktest.database.repository.UserRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final UserRepository userRepository;

    public Page<User> searchUsers(String fullName, String phoneNumber, String email, LocalDate birthDate, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Specification<User> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (fullName != null && !fullName.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("fullName"), fullName + "%"));
            }

            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                Join<User, Phone> phoneJoin = root.join("phones");
                predicates.add(criteriaBuilder.equal(phoneJoin.get("number"), phoneNumber));
            }

            if (email != null && !email.isEmpty()) {
                Join<User, Email> emailJoin = root.join("emails");
                predicates.add(criteriaBuilder.equal(emailJoin.get("email"), email));
            }

            if (birthDate != null) {
                predicates.add(criteriaBuilder.greaterThan(root.get("birthDate"), birthDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return userRepository.findAll(spec, pageable);
    }
}
