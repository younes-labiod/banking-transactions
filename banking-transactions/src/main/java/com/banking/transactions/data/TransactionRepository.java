package com.banking.transactions.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.transactions.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
