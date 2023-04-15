package com.banking.transactions;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "BK_Transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(nullable = false)
	private String currency;

	@NotNull
	@Column(nullable = false)
	private BigDecimal amount;

	@NotNull
	@Column(nullable = false)
	private String sourceaccountid;

	private Date createdat;

	private Date updatedat;

	@PrePersist
	void createdat() {
		this.createdat = new Date();
	}

	@PreUpdate
	void updatedat() {
		this.updatedat = new Date();
	}

}
