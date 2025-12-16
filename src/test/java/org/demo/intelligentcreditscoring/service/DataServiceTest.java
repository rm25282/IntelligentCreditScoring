package org.demo.intelligentcreditscoring.service;

import org.demo.intelligentcreditscoring.model.BankAccount;
import org.demo.intelligentcreditscoring.model.LoanAccount;
import org.demo.intelligentcreditscoring.model.LoanAccountType;
import org.demo.intelligentcreditscoring.repository.IBankAccountRepository;
import org.demo.intelligentcreditscoring.repository.ILoanRepository;
import org.demo.intelligentcreditscoring.repository.model.DbBankAccount;
import org.demo.intelligentcreditscoring.repository.model.Loan;
import org.demo.intelligentcreditscoring.repository.model.LoanType;
import org.demo.intelligentcreditscoring.service.conviction.IConvictionService;
import org.demo.intelligentcreditscoring.service.electorial.IElectorialRegisterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DataServiceTest {

    public static final long LOAN_AMOUNT = 2000000L;
    public static final int NUMBER_OF_MISSED_PAYMENTS = 2;
    private final static Double CURRENT_BALANCE = 3543.00;
    private final static Double OVERDRAFT_LIMIT = -1000.00;
    private final static int NUMBER_OF_TIMES_OVERDRAWN = 0;
    @Captor
    ArgumentCaptor<DbBankAccount> dbBankAccountArgumentCaptor;
    @Captor
    ArgumentCaptor<Loan> loanArgumentCaptor;
    @Captor
    ArgumentCaptor<Boolean> canVoteCaptor;
    @Captor
    ArgumentCaptor<Boolean> convictionsCaptor;
    @InjectMocks
    DataService dataService;
    @Mock
    private ILoanRepository loanRepository;
    @Mock
    private IBankAccountRepository bankAccountRepository;
    @Mock
    private IElectorialRegisterService electorialRegister;
    @Mock
    private IConvictionService convictions;

    @Test
    void shouldSaveBankAccount() {
        final DbBankAccount savedBankAccount = new DbBankAccount(
                OVERDRAFT_LIMIT,
                CURRENT_BALANCE,
                NUMBER_OF_TIMES_OVERDRAWN);

        Mockito.when(bankAccountRepository.save(any(DbBankAccount.class))).thenReturn(savedBankAccount);

        dataService.addBankAccount(new BankAccount(OVERDRAFT_LIMIT, CURRENT_BALANCE, NUMBER_OF_TIMES_OVERDRAWN));
        verify(bankAccountRepository).save(dbBankAccountArgumentCaptor.capture());

        final DbBankAccount savedArgument = dbBankAccountArgumentCaptor.getValue();

        assertThat(savedArgument.getOverdraftLimit()).isEqualTo(OVERDRAFT_LIMIT);
        assertThat(savedArgument.getCurrentBalance()).isEqualTo(CURRENT_BALANCE);
        assertThat(savedArgument.getNumberOfTimesOverdrawn()).isEqualTo(NUMBER_OF_TIMES_OVERDRAWN);
    }

    @Test
    void shouldSaveLoanAccount() {

        final LoanAccount loanAccount = new LoanAccount(LoanAccountType.MORTGAGE, LOAN_AMOUNT, NUMBER_OF_MISSED_PAYMENTS);

        dataService.addLoan(loanAccount);

        verify(loanRepository).save(loanArgumentCaptor.capture());

        final Loan savedLoan = loanArgumentCaptor.getValue();

        assertThat(savedLoan.getLoanType()).isEqualTo(LoanType.MORTGAGE);
        assertThat(savedLoan.getLoanAmount()).isEqualTo(LOAN_AMOUNT);
        assertThat(savedLoan.getMissedPayments()).isEqualTo(NUMBER_OF_MISSED_PAYMENTS);
    }

    @Test
    void shouldSetAbilityToVote() {
        dataService.setRegisterToVote(true);
        verify(electorialRegister).setRegisteredToVote(canVoteCaptor.capture());
        assertThat(canVoteCaptor.getValue()).isTrue();
    }

    @Test
    void shouldSetAbilityToSetConvictions() {
        dataService.setConvictions(true);
        verify(convictions).setHasConvictions(convictionsCaptor.capture());
        assertThat(convictionsCaptor.getValue()).isTrue();
    }

    @Test
    void shouldReset() {
        dataService.reset();
        verify(convictions).setHasConvictions(convictionsCaptor.capture());
        verify(electorialRegister).setRegisteredToVote(canVoteCaptor.capture());
        verify(bankAccountRepository).deleteAll();
        verify(loanRepository).deleteAll();

        assertThat(convictionsCaptor.getValue()).isFalse();
        assertThat(canVoteCaptor.getValue()).isFalse();

    }
}