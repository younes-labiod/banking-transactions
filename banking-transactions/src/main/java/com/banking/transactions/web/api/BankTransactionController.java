package com.banking.transactions.web.api;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

	Logger logger = LoggerFactory.getLogger(BankTransactionController.class);

	private static final String LOG_PREFIX = "BankTransactionController";

	private TransactionRepository transactionRepo;

	public BankTransactionController(TransactionRepository transactionRepository) {
		this.transactionRepo = transactionRepository;
	}

	@GetMapping
	public List<Transaction> getAllTransactions() {
		List<Transaction> transactionsList = transactionRepo.findAll();
		logger.info(LOG_PREFIX + ".getAllTransactions | All transactions available : {}", transactionsList.toString());
		return transactionsList;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {

		logger.info(LOG_PREFIX + ".getTransactionById | Fetching transaction with ID : {}", id);
		Optional<Transaction> transaction = transactionRepo.findById(id);
		if (transaction.isPresent()) {
			logger.info(LOG_PREFIX + ".getTransactionById | Transaction with ID : {} found : {}", id,
					transaction.get().toString());
		} else {
			logger.info(LOG_PREFIX + ".getTransactionById | Transaction NOT found with ID : {}", id);
		}

		return transaction.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody Transaction transaction) {

		logger.info(LOG_PREFIX + ".createTransaction | Creating new transaction : {}", transaction.toString());
		Transaction savedTransaction = transactionRepo.save(transaction);
		return ResponseEntity.created(URI.create("/transactions/" + savedTransaction.getId())).body(savedTransaction);
	}

	@DeleteMapping("/{transactionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTransaction(@PathVariable("transactionId") Long transactionId) {
		try {
			logger.info(LOG_PREFIX + ".deleteTransaction | Deleting transaction with ID: {}", transactionId);
			transactionRepo.deleteById(transactionId);
		} catch (EmptyResultDataAccessException e) {
//			logger.error(LOG_PREFIX
//					+ ".deleteTransaction | Deleting transaction with ID: {}, EmptyResultDataAccessException : ",
//					transactionId);
		}
	}

	@PatchMapping(path = "/{transactionId}", consumes = "application/json")
	public Transaction patchTransaction(@PathVariable("transactionId") Long transactionId,
			@RequestBody Transaction patch) {

		Transaction transaction = transactionRepo.findById(transactionId).get();

		logger.info(LOG_PREFIX + ".patchTransaction | Updating transaction : {}", transaction.toString());

		if (patch.getCurrency() != null) {
			transaction.setCurrency(patch.getCurrency());
		}
		if (patch.getAmount() != null) {
			transaction.setAmount(patch.getAmount());
		}
		if (patch.getSourceaccountid() != null) {
			transaction.setSourceaccountid(patch.getSourceaccountid());
		}

		return transactionRepo.save(transaction);
	}

}
