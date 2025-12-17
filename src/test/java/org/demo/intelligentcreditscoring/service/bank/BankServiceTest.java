package org.demo.intelligentcreditscoring.service.bank;

import org.demo.intelligentcreditscoring.model.Address;
import org.demo.intelligentcreditscoring.model.User;
import org.demo.intelligentcreditscoring.repository.IBankAccountRepository;
import org.demo.intelligentcreditscoring.repository.model.DbBankAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BankServiceTest {

    private final static Address ADDRESS = new Address("123 Main Street", "", "Belfast", "BT7 2DE");
    private final static User USER = new User("Richard", "Martin", LocalDate.of(1985, 12, 12), ADDRESS);
    @Mock
    private IBankAccountRepository bankAccountRepository;
    @InjectMocks
    private BankService bank;

    @Test
    void shouldReturnOneHundredWhenZeroMissedPaymentsAndNotOverdrawn() {
        final DbBankAccount dbBankAccount = new DbBankAccount(500.00, 500.00, 0);

        Mockito.when(bankAccountRepository.findAll()).thenReturn(List.of(dbBankAccount));

        final Bank score = bank.calculateBankAccountScore(USER);

        assertThat(score).isEqualTo(new Bank(false, 100));
    }

    @Test
    void shouldReturnZeroWhenAccountIsOverdrawn() {
        final DbBankAccount dbBankAccount = new DbBankAccount(500.00, -600.00, 0);

        Mockito.when(bankAccountRepository.findAll()).thenReturn(List.of(dbBankAccount));

        final Bank score = bank.calculateBankAccountScore(USER);

        assertThat(score).isEqualTo(new Bank(true, 0));
    }


    @Test
    void shouldReturn0WhenAccountIsOverdrawnAndTenTimesOverdrawn() {
        final DbBankAccount dbBankAccount = new DbBankAccount(500.00, -600.00, 10);

        Mockito.when(bankAccountRepository.findAll()).thenReturn(List.of(dbBankAccount));

        final Bank score = bank.calculateBankAccountScore(USER);

        assertThat(score).isEqualTo(new Bank(true, 0));
    }

    @Test
    void shouldReturnFiftyWhenAccountIsNotOverdrawnAndFiveTimesOverdrawn() {
        final DbBankAccount dbBankAccount = new DbBankAccount(500.00, -500.00, 5);

        Mockito.when(bankAccountRepository.findAll()).thenReturn(List.of(dbBankAccount));

        final Bank score = bank.calculateBankAccountScore(USER);

        assertThat(score).isEqualTo(new Bank(false, 50));
    }
}