package org.demo.intelligentcreditscoring.service.bank;

import org.demo.intelligentcreditscoring.model.User;
import org.demo.intelligentcreditscoring.repository.IBankAccountRepository;
import org.demo.intelligentcreditscoring.repository.model.DbBankAccount;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.max;

@Service
public class BankService implements IBankService {

    private final IBankAccountRepository bankAccountRepository;

    public BankService(IBankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;

    }

    /**
     * Calculates whether the account is over its overdraft limit
     *
     * @param bankAccount account details
     * @return true if its over it overdraft limit
     */
    private static boolean isAccountPastOverdrawnLimit(DbBankAccount bankAccount) {
        // For comparison making overdraft limit minus
        return bankAccount.getCurrentBalance() < -bankAccount.getOverdraftLimit();
    }

    @Override
    public Bank calculateBankAccountScore(User user) {

        final List<DbBankAccount> bankAccounts = bankAccountRepository.findAll();

        int finalScore = 100;
        for (DbBankAccount bankAccount : bankAccounts) {
            if (isAccountPastOverdrawnLimit(bankAccount)) {
                finalScore -= 100;
            }

            finalScore -= bankAccount.getNumberOfTimesOverdrawn() * 10;
        }

        long numberOfAccountsPastOverdrawnLimit = bankAccounts
                .stream()
                .filter(BankService::isAccountPastOverdrawnLimit)
                .count();

        return new Bank(numberOfAccountsPastOverdrawnLimit > 0, max(finalScore, 0));
    }
}
