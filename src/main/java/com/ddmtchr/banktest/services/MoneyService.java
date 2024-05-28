package com.ddmtchr.banktest.services;

import com.ddmtchr.banktest.database.entities.User;
import com.ddmtchr.banktest.database.repository.UserRepository;
import com.ddmtchr.banktest.exception.InsufficientFundsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MoneyService {
    private final UserRepository userRepository;

    @Transactional
    public void transferMoney(Long fromId, Long toId, double amount) {
        User fromUser = userRepository.findById(fromId)
                .orElseThrow(() -> new UsernameNotFoundException("Sender not found"));
        User toUser = userRepository.findById(toId)
                .orElseThrow(() -> new UsernameNotFoundException("Receiver not found"));

        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("Can't transfer money to the same account");
        }

        synchronized (this) {
            if (fromUser.getMoney() < amount) {
                throw new InsufficientFundsException("Insufficient funds");
            }
            fromUser.setMoney(fromUser.getMoney() - amount);
            toUser.setMoney(toUser.getMoney() + amount);

            userRepository.save(fromUser);
            userRepository.save(toUser);
        }
    }
}
