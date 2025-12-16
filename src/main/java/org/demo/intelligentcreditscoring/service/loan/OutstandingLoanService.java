package org.demo.intelligentcreditscoring.service.loan;

import org.demo.intelligentcreditscoring.model.User;
import org.demo.intelligentcreditscoring.repository.ILoanRepository;
import org.demo.intelligentcreditscoring.repository.model.Loan;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.max;

@Service
public class OutstandingLoanService implements IOutstandingLoanService {

    private final ILoanRepository loanRepository;

    public OutstandingLoanService(ILoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public OutstandingLoan calculateOutStandingLoanScore(User user) {
        int finalScore = 100;

        List<Loan> outstandingLoans = loanRepository.findAll();
        int numberOfMissedPayments = outstandingLoans.stream().mapToInt(Loan::getMissedPayments).sum();
        finalScore = finalScore - (numberOfMissedPayments * 10);

        return new OutstandingLoan(numberOfMissedPayments, max(finalScore, 0));
    }

}
