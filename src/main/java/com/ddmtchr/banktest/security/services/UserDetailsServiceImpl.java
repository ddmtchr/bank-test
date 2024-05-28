package com.ddmtchr.banktest.security.services;

import com.ddmtchr.banktest.database.entities.Email;
import com.ddmtchr.banktest.database.entities.Phone;
import com.ddmtchr.banktest.database.entities.User;
import com.ddmtchr.banktest.database.repository.EmailRepository;
import com.ddmtchr.banktest.database.repository.PhoneRepository;
import com.ddmtchr.banktest.database.repository.UserRepository;
import com.ddmtchr.banktest.mapper.UserMapper;
import com.ddmtchr.banktest.payload.RegisterRequest;
import com.ddmtchr.banktest.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final EmailRepository emailRepository;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = findUserByLogin(login);
        return UserDetailsImpl.userToUserDetails(user);
    }

    public User findUserByLogin(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + login + " not found"));
    }

    public boolean canRegister(RegisterRequest request) {
        if (userRepository.existsByLogin(request.getLogin())) return false;
        if (phoneRepository.existsByNumber(request.getPhone())) return false;
        return !emailRepository.existsByEmail(request.getEmail());
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void addPhone(Long userId, String phoneNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Long currentUserId = findUserByLogin(jwtUtils.getCurrentUser().getUsername()).getId();
        if (!Objects.equals(user.getId(), currentUserId)) throw new IllegalArgumentException("Can't change someone else's data");

        Phone phone = UserMapper.instance.toPhone(phoneNumber);
        phone.setUser(user);
        user.getPhones().add(phone);
        userRepository.save(user);
    }

    public void updatePhone(Long userId, String oldPhone, String newPhone) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Long currentUserId = findUserByLogin(jwtUtils.getCurrentUser().getUsername()).getId();
        if (!Objects.equals(user.getId(), currentUserId)) throw new IllegalArgumentException("Can't change someone else's data");

        Phone phone = user.getPhones().stream()
                .filter(p -> p.getNumber().equals(oldPhone))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Phone number not found"));

        phone.setNumber(newPhone);
        phoneRepository.save(phone);
    }

    public void deletePhone(Long userId, String phoneNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Long currentUserId = findUserByLogin(jwtUtils.getCurrentUser().getUsername()).getId();
        if (!Objects.equals(user.getId(), currentUserId)) throw new IllegalArgumentException("Can't change someone else's data");

        List<Phone> phones = user.getPhones();
        if (phones.size() == 1) {
            throw new RuntimeException("Cannot delete the only phone number");
        }

        Phone phone = phones.stream()
                .filter(p -> p.getNumber().equals(phoneNumber))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Phone number not found"));

        phones.remove(phone);
        phoneRepository.delete(phone);
    }

    public void addEmail(Long userId, String emailString) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Long currentUserId = findUserByLogin(jwtUtils.getCurrentUser().getUsername()).getId();
        if (!Objects.equals(user.getId(), currentUserId)) throw new IllegalArgumentException("Can't change someone else's data");

        Email email = UserMapper.instance.toEmail(emailString);
        email.setUser(user);
        user.getEmails().add(email);
        userRepository.save(user);
    }

    public void updateEmail(Long userId, String oldEmail, String newEmail) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Long currentUserId = findUserByLogin(jwtUtils.getCurrentUser().getUsername()).getId();
        if (!Objects.equals(user.getId(), currentUserId)) throw new IllegalArgumentException("Can't change someone else's data");

        Email email = user.getEmails().stream()
                .filter(p -> p.getEmail().equals(oldEmail))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Email not found"));

        email.setEmail(newEmail);
        emailRepository.save(email);
    }

    public void deleteEmail(Long userId, String emailToDelete) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Long currentUserId = findUserByLogin(jwtUtils.getCurrentUser().getUsername()).getId();
        if (!Objects.equals(user.getId(), currentUserId)) throw new IllegalArgumentException("Can't change someone else's data");

        List<Email> emails = user.getEmails();
        if (emails.size() == 1) {
            throw new RuntimeException("Cannot delete the only email");
        }

        Email email = emails.stream()
                .filter(e -> e.getEmail().equals(emailToDelete))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Email not found"));

        emails.remove(email);
        emailRepository.delete(email);
    }
}
