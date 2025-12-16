package org.demo.intelligentcreditscoring.service;

import org.demo.intelligentcreditscoring.model.BankAccount;
import org.demo.intelligentcreditscoring.model.LoanAccount;
import org.demo.intelligentcreditscoring.repository.IBankAccountRepository;
import org.demo.intelligentcreditscoring.repository.ILoanRepository;
import org.demo.intelligentcreditscoring.repository.model.DbBankAccount;
import org.demo.intelligentcreditscoring.repository.model.Loan;
import org.demo.intelligentcreditscoring.repository.model.LoanType;
import org.demo.intelligentcreditscoring.service.conviction.IConvictionService;
import org.demo.intelligentcreditscoring.service.electorial.IElectorialRegisterService;
import org.springframework.stereotype.Service;

@Service
public class DataService implements IDataService {

    private final IBankAccountRepository bankAccountRepository;
    private final ILoanRepository loanRepository;
    private final IElectorialRegisterService electorialRegister;
    private final IConvictionService convictions;

    public DataService(IBankAccountRepository bankAccountRepository, ILoanRepository loanRepository, IElectorialRegisterService electorialRegister, IConvictionService convictions) {
        this.bankAccountRepository = bankAccountRepository;
        this.loanRepository = loanRepository;
        this.electorialRegister = electorialRegister;
        this.convictions = convictions;
    }

    @Override
    public void addBankAccount(BankAccount bankAccount) {
        bankAccountRepository.save(toDbBankAccount(bankAccount));
    }

    @Override
    public void addLoan(LoanAccount loanAccount) {
        loanRepository.save(toDbLoan(loanAccount));
    }

    @Override
    public void setRegisterToVote(boolean canVote) {
        electorialRegister.setRegisteredToVote(canVote);
    }

    @Override
    public void setConvictions(boolean hasBeenConvicted) {
        convictions.setHasConvictions(hasBeenConvicted);
    }

    @Override
    public void reset() {
        bankAccountRepository.deleteAll();
        loanRepository.deleteAll();
        convictions.setHasConvictions(false);
        electorialRegister.setRegisteredToVote(false);
    }

    private Loan toDbLoan(LoanAccount loanAccount) {
        return new Loan(LoanType.valueOf(loanAccount.accountType().name()),
                loanAccount.amount(),
                loanAccount.numberOfMissedPayments());
    }

    private DbBankAccount toDbBankAccount(BankAccount bankAccount) {
        return new DbBankAccount(bankAccount.overdraftLimit(),
                bankAccount.currentBalance(),
                bankAccount.numberOfTimesOverdrawn());
    }
}
