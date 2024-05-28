package com.ddmtchr.banktest.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    @JsonIgnore
    private String password;
    private double money;
    private double initialDeposit;
    private String fullName;
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "user",
            orphanRemoval = true
    )
    private List<Phone> phones = new ArrayList<>();
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "user",
            orphanRemoval = true
    )
    private List<Email> emails = new ArrayList<>();
    private LocalDate birthDate;

    public User(String login, String password, double money, String fullName, Phone phone, Email email, LocalDate birthDate) {
        this.login = login;
        this.password = password;
        this.money = money;
        this.initialDeposit = money;
        this.fullName = fullName;
        this.phones = new ArrayList<>();
        this.emails = new ArrayList<>();
        phones.add(phone);
        emails.add(email);
        this.birthDate = birthDate;
    }
}
