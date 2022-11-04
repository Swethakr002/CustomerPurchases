package com.reward.controller;

import com.reward.exception.CustomerCallException;
import com.reward.request.CustomerPurchaseRequest;
import com.reward.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.reward.constants.Constants.*;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /*
     * This controller saves purchase requests of customer to database
     *
     * @return ResponseEntity<CustomerPurchase> for success response
     */
    @PostMapping(path = POST_CUSTOMER_PURCHASE_REQUEST_PATH,
            consumes = {org.springframework.http.MediaType.APPLICATION_JSON_VALUE},
            produces = {org.springframework.http.MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> createCustomerPurchase(
            @Valid @RequestBody CustomerPurchaseRequest customerPurchaseRequest) throws CustomerCallException {
        logger.info(CUSTOMER_CONTROLLER_LOG + PURCHASE_REQUEST_RECEIVED_LOG);
        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(customerService.createCustomerPurchase(customerPurchaseRequest));
    }

    /*
     * This controller retrieves reward points of customer from database
     *
     * @return ResponseEntity<CustomerRewardResponse> for success response
     */
    @GetMapping(path = GET_CUSTOMER_REWARDS_REQUEST_PATH,
            produces = {org.springframework.http.MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getCustomerRewardPoints(@PathVariable String customerId)
            throws CustomerCallException {
        logger.info(CUSTOMER_CONTROLLER_LOG + REWARDS_REQUEST_RECEIVED_LOG);
        return ResponseEntity.status(HttpStatus.OK.value()).body(customerService.getCustomerRewardPoints(customerId));

    }
}
