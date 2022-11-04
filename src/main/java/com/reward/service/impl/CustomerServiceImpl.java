package com.reward.service.impl;

import com.reward.dao.CustomerPurchaseDao;
import com.reward.exception.CustomerCallException;
import com.reward.model.CustomerPurchase;
import com.reward.request.CustomerPurchaseRequest;
import com.reward.response.CustomerRewardResponse;
import com.reward.response.RewardPerMonth;
import com.reward.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.reward.constants.Constants.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerPurchaseDao customerPurchaseDao;

    @Autowired
    public CustomerServiceImpl(CustomerPurchaseDao customerPurchaseDao) {
        this.customerPurchaseDao = customerPurchaseDao;
    }

    /*
     * This function captures the request object and purchase requests of customer to database
     *
     * @return CustomerPurchase
     */
    @Override
    public CustomerPurchase createCustomerPurchase(@Valid CustomerPurchaseRequest customerPurchaseRequest) throws CustomerCallException {
        try {
            CustomerPurchase customerPurchase = CustomerPurchase.builder().amount(customerPurchaseRequest.getAmount())
                    .customerId(customerPurchaseRequest.getCustomerId())
                    .purchaseDate(customerPurchaseRequest.getPurchaseDate())
                    .reward(calculateRewards(customerPurchaseRequest.getAmount().intValue())).build();
            return customerPurchaseDao.save(customerPurchase);
        } catch (Exception exception) {
            logger.error(CUSTOMER_CONTROLLER_LOG + PURCHASE_REQUEST_FAILED_LOG);
            logger.error(FAILED_WITH_EXCEPTION_LOG, exception.getMessage());
            throw new CustomerCallException(HttpStatus.INTERNAL_SERVER_ERROR, PURCHASE_REQUEST_FAILED_LOG);
        }
    }

    /*
     * This function calculates the rewards points based on purchase
     *
     * @return int
     */
    private int calculateRewards(int amount) {
        int reward = 0;
        if (amount <= 50) {
            return reward;
        } else if (amount <= 100) {
            reward += (amount - 50);
        } else {
            reward += 50;
            reward += (amount - 100) * 2;
        }
        return reward;
    }

    /*
     * This function retrieves reward points for specific customer
     *
     * @return CustomerRewardResponse
     */
    @Override
    public CustomerRewardResponse getCustomerRewardPoints(String customerId) throws CustomerCallException {
        try {
            List<RewardPerMonth> rewardPerMonthList = new ArrayList<>();
            List<CustomerPurchase> customerPurchase = customerPurchaseDao.findByCustomerId(customerId);
            if (!customerPurchase.isEmpty()) {
                LocalDate date = LocalDate.now().minusMonths(3);
                customerPurchase = customerPurchase.stream()
                        .filter(customer -> customer.getPurchaseDate().getMonth() >= date.getMonthValue())
                        .collect(Collectors.toList());
                Map<Integer, List<CustomerPurchase>> purchaseOrderGroupBy = customerPurchase.stream()
                        .collect(Collectors.groupingBy(rewards -> rewards.getPurchaseDate().getMonth()));

                for (Map.Entry<Integer, List<CustomerPurchase>> customerPurchaseGroup : purchaseOrderGroupBy.entrySet()) {
                    RewardPerMonth rewardPerMonth = RewardPerMonth.builder()
                            .monthName(new DateFormatSymbols().getMonths()[customerPurchaseGroup.getKey()])
                            .totalRewards(
                                    customerPurchaseGroup.getValue().stream().mapToInt(CustomerPurchase::getReward).sum())
                            .build();
                    rewardPerMonthList.add(rewardPerMonth);
                }
            }
            return CustomerRewardResponse.builder().rewardPerMonth(rewardPerMonthList)
                    .rewardTotal(customerPurchase.stream().mapToInt(CustomerPurchase::getReward).sum()).build();

        } catch (Exception exception) {
            logger.error(CUSTOMER_CONTROLLER_LOG + REWARDS_REQUEST_FAILED_LOG);
            logger.error(FAILED_WITH_EXCEPTION_LOG, exception.getMessage());
            throw new CustomerCallException(HttpStatus.INTERNAL_SERVER_ERROR, PURCHASE_REQUEST_FAILED_LOG);
        }
    }

}
