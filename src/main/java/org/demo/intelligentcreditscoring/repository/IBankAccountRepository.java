package org.demo.intelligentcreditscoring.repository;

import org.demo.intelligentcreditscoring.repository.model.DbBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBankAccountRepository extends JpaRepository<DbBankAccount, Long> {

}
