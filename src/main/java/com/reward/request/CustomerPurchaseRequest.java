package com.reward.request;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPurchaseRequest {

	@NotEmpty(message = "customerId can not be null")
	private String customerId;

	@NotNull(message = "amount can not be null")
	private Float amount;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "MM/dd/yyy")
	@NotNull(message = "purchaseDate can not be null")
	private Date purchaseDate;

}
