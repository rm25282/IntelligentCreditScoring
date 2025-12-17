package org.demo.intelligentcreditscoring.service.loan;

import org.demo.intelligentcreditscoring.model.Address;
import org.demo.intelligentcreditscoring.model.User;
import org.demo.intelligentcreditscoring.repository.ILoanRepository;
import org.demo.intelligentcreditscoring.repository.model.Loan;
import org.demo.intelligentcreditscoring.repository.model.LoanType;
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
class OutstandingLoanServiceTest {

    private final Address address = new Address("123 Main Street", "", "Belfast", "BT7 2DE");
    private final User user = new User("Richard", "Martin", LocalDate.of(1985, 12, 12), address);
    @Mock
    private ILoanRepository loanRepository;
    @InjectMocks
    private OutstandingLoanService outstandingLoan;

    @Test
    void shouldScoreOfZeroWhenNumberOfMissedPaymentAreGreaterThanTen() {
        Loan loan = new Loan(LoanType.LOAN, 10000L, 12);

        Mockito.when(loanRepository.findAll()).thenReturn(List.of(loan));

        OutstandingLoan expected = new OutstandingLoan(12, 0);
        assertThat(outstandingLoan.calculateOutStandingLoanScore(user)).isEqualTo(expected);
    }

    @Test
    void shouldScoreOfFiftyWhenNumberOfMissedPaymentsIsFive() {
        Loan loan = new Loan(LoanType.LOAN, 10000L, 5);

        Mockito.when(loanRepository.findAll()).thenReturn(List.of(loan));

        OutstandingLoan expected = new OutstandingLoan(5, 50);
        assertThat(outstandingLoan.calculateOutStandingLoanScore(user)).isEqualTo(expected);
    }

    @Test
    void shouldScoreFortyWhenNumberOfMissedPaymentsAcrossThreeAccountsTotalSix() {
        Loan loan = new Loan(LoanType.LOAN, 10000L, 2);
        Loan mortageLoan = new Loan(LoanType.MORTGAGE, 200000L, 1);
        Loan creditCardLoan = new Loan(LoanType.CREDIT_CARD, 2000L, 3);

        Mockito.when(loanRepository.findAll()).thenReturn(List.of(loan, mortageLoan, creditCardLoan));

        OutstandingLoan expected = new OutstandingLoan(6, 40);
        assertThat(outstandingLoan.calculateOutStandingLoanScore(user)).isEqualTo(expected);
    }
}