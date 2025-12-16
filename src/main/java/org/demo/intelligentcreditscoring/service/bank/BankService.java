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

    private static boolean isAccountOverdrawn(DbBankAccount bankAccount) {
        // For comparison making overdraft limit minus
        return bankAccount.getCurrentBalance() < -bankAccount.getOverdraftLimit();
    }

    @Override
    public Bank calculateBankAccountScore(User user) {

        final List<DbBankAccount> bankAccounts = bankAccountRepository.findAll();

        int finalScore = 0;
        for (DbBankAccount bankAccount : bankAccounts) {
            if (isAccountOverdrawn(bankAccount)) {
                finalScore -= 100;
            } else {
                finalScore += 100;
            }

            finalScore -= bankAccount.getNumberOfTimesOverdrawn() * 10;
        }

        long numberOfOverdrawnAccounts = bankAccounts
                .stream()
                .filter(BankService::isAccountOverdrawn)
                .count();

        return new Bank(numberOfOverdrawnAccounts > 0, max(finalScore, 0));
    }
}
