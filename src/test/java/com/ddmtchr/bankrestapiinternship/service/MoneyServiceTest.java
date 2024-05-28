package com.ddmtchr.bankrestapiinternship.service;

import com.ddmtchr.banktest.database.entities.User;
import com.ddmtchr.banktest.database.repository.UserRepository;
import com.ddmtchr.banktest.exception.InsufficientFundsException;
import com.ddmtchr.banktest.services.MoneyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MoneyServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MoneyService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTransferMoneySuccess() {
        User sender = new User();
        sender.setId(1L);
        sender.setMoney(1000.0);

        User recipient = new User();
        recipient.setId(2L);
        recipient.setMoney(500.0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2L)).thenReturn(Optional.of(recipient));

        userService.transferMoney(1L, 2L, 100.0);

        verify(userRepository, times(1)).save(sender);
        verify(userRepository, times(1)).save(recipient);

        assert(sender.getMoney() == 900.0);
        assert(recipient.getMoney() == 600.0);
    }

    @Test
    public void testTransferMoneyInsufficientFunds() {
        User sender = new User();
        sender.setId(1L);
        sender.setMoney(50.0);

        User recipient = new User();
        recipient.setId(2L);
        recipient.setMoney(500.0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2L)).thenReturn(Optional.of(recipient));

        assertThrows(InsufficientFundsException.class, () -> userService.transferMoney(1L, 2L, 100.0));

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void testTransferMoneyRecipientNotFound() {
        User sender = new User();
        sender.setId(1L);
        sender.setMoney(1000.0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.transferMoney(1L, 2L, 100.0));

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void testTransferMoneySameAccount() {
        User sender = new User();
        sender.setId(1L);
        sender.setMoney(1000.0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));

        assertThrows(IllegalArgumentException.class, () -> userService.transferMoney(1L, 1L, 100.0));

        verify(userRepository, times(0)).save(any(User.class));
    }
}
