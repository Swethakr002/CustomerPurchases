package com.reward.constants;

public class Constants {
    public static final String CUSTOMER_CONTROLLER_LOG = "CustomerController ";
    public static final String POST_CUSTOMER_PURCHASE_REQUEST_PATH = "/customer/purchaseRequest";
    public static final String GET_CUSTOMER_REWARDS_REQUEST_PATH = "/customer/{customerId}/rewards";
    public static final String PURCHASE_REQUEST_RECEIVED_LOG = "Received request for saving purchase request";
    public static final String PURCHASE_REQUEST_FAILED_LOG = "Error while saving purchase request";
    public static final String REWARDS_REQUEST_RECEIVED_LOG = "Received request for fetching reward points of customer";
    public static final String REWARDS_REQUEST_FAILED_LOG = "Error while retrieving reward points";
    public static final String FAILED_WITH_EXCEPTION_LOG = "Error message {} ";
}
