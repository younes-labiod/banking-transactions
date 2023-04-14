package com.banking.transactions.web.api;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.banking.transactions.Transaction;
import com.banking.transactions.data.TransactionRepository;

@RestController
@RequestMapping(path = "/transactions", produces = "application/json")
public class BankTransactionController {

//	@Autowired
	private TransactionRepository transactionRepo;

	public BankTransactionController(TransactionRepository transactionRepository) {
		this.transactionRepo = transactionRepository;
	}

	@GetMapping
	public List<Transaction> getAllTransactions() {
		return transactionRepo.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
		Optional<Transaction> transaction = transactionRepo.findById(id);
		return transaction.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody Transaction transaction) {
		Transaction savedTransaction = transactionRepo.save(transaction);
		return ResponseEntity.created(URI.create("/transactions/" + savedTransaction.getId())).body(savedTransaction);
	}

	@DeleteMapping("/{transactionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTransaction(@PathVariable("transactionId") Long transactionId) {
		try {
			transactionRepo.deleteById(transactionId);
		} catch (EmptyResultDataAccessException e) {
		}
	}

}
