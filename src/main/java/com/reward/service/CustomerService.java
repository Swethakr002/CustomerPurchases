package com.reward.service;

import javax.validation.Valid;

import com.reward.exception.CustomerCallException;
import com.reward.model.CustomerPurchase;
import com.reward.request.CustomerPurchaseRequest;
import com.reward.response.CustomerRewardResponse;

public interface CustomerService {

	CustomerPurchase createCustomerPurchase(@Valid CustomerPurchaseRequest customerPurchaseRequest) throws CustomerCallException;

	CustomerRewardResponse getCustomerRewardPoints(String customerId) throws CustomerCallException;

}
