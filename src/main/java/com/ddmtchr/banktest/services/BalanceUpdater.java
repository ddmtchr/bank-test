package com.ddmtchr.banktest.services;

import com.ddmtchr.banktest.database.entities.User;
import com.ddmtchr.banktest.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BalanceUpdater {
    private final UserRepository userRepository;

    @Scheduled(fixedRate = 60000, initialDelay = 60000)
    public void updateBalances() {
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            double maxBalance = user.getInitialDeposit() * 2.07;
            if (user.getMoney() < maxBalance) {
                double newBalance = user.getMoney() * 1.05;
                if (newBalance > maxBalance) {
                    newBalance = maxBalance;
                }
                user.setMoney(newBalance);
                userRepository.save(user);
            }
        }
    }
}
