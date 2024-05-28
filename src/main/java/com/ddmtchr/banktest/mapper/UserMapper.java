package com.ddmtchr.banktest.mapper;

import com.ddmtchr.banktest.database.entities.Email;
import com.ddmtchr.banktest.database.entities.Phone;
import com.ddmtchr.banktest.database.entities.User;
import com.ddmtchr.banktest.payload.RegisterRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
public interface UserMapper {
    UserMapper instance = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "phones", ignore = true),
            @Mapping(target = "emails", ignore = true),
            @Mapping(target = "initialDeposit", ignore = true),
            @Mapping(target = "password", expression = "java(encoder.encode(request.getPassword()))")
    })
    User toUser(RegisterRequest request, PasswordEncoder encoder);

    Phone toPhone(String number);

    Email toEmail(String email);

    @AfterMapping
    default void afterMapping(@MappingTarget User user, RegisterRequest request) {
        Phone phone = toPhone(request.getPhone());
        Email email = toEmail(request.getEmail());
        phone.setUser(user);
        email.setUser(user);
        user.getPhones().add(phone);
        user.getEmails().add(email);
        user.setInitialDeposit(user.getMoney());
    }
}
