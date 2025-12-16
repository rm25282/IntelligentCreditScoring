package org.demo.intelligentcreditscoring.repository;

import org.demo.intelligentcreditscoring.repository.model.Loan;
import org.demo.intelligentcreditscoring.repository.model.LoanType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OutstandingLoanRepositoryTest {

    @Autowired
    private ILoanRepository loanRepository;

    @Test
    void shouldFindLoanAfterSave() {
        Loan carLoan = new Loan(LoanType.LOAN, 5000L, 2);
        Loan savedLoan = loanRepository.save(carLoan);
        Loan foundLoan = loanRepository.findById(carLoan.getId()).orElseThrow();
        assertThat(foundLoan).isEqualTo(savedLoan);
    }

    @Test
    void shouldFindMultipleLoans() {
        Loan carLoan = new Loan(LoanType.LOAN, 5000L, 2);
        Loan mortgage = new Loan(LoanType.MORTGAGE, 200000L, 0);
        Loan personalLoan = new Loan(LoanType.CREDIT_CARD, 10000L, 4);

        loanRepository.save(carLoan);
        loanRepository.save(mortgage);
        loanRepository.save(personalLoan);

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans).hasSize(3).contains(carLoan, mortgage, personalLoan);
    }
}
