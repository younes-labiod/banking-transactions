package com.banking.transactions.data;

import org.springframework.data.repository.CrudRepository;

import com.banking.transactions.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
