package com.ddmtchr.bankrestapiinternship;

import com.ddmtchr.banktest.BankTestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BankTestApplication.class)
@AutoConfigureTestDatabase
class BankTestApplicationTests {

    @Test
    void contextLoads() {
    }

}
