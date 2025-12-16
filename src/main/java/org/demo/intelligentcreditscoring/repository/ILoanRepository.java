package org.demo.intelligentcreditscoring.repository;

import org.demo.intelligentcreditscoring.repository.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILoanRepository extends JpaRepository<Loan, Long> {

}
