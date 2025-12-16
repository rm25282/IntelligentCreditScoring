package org.demo.intelligentcreditscoring.repository;

import org.demo.intelligentcreditscoring.repository.model.DbBankAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class BankRepositoryTest {

    @Autowired
    private IBankAccountRepository bankAccountRepository;

    @Test
    void shouldFindBankAccountAfterSave() {
        final DbBankAccount newBankAccount = new DbBankAccount(-500.00, 453.98, 0);
        final DbBankAccount savedBankAccount = bankAccountRepository.save(newBankAccount);
        final List<DbBankAccount> found = bankAccountRepository.findAll();
        assertThat(found).containsExactly(savedBankAccount);
    }

}
