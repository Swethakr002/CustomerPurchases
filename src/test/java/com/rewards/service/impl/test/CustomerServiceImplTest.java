package com.rewards.service.impl.test;

import com.reward.dao.CustomerPurchaseDao;
import com.reward.exception.CustomerCallException;
import com.reward.model.CustomerPurchase;
import com.reward.request.CustomerPurchaseRequest;
import com.reward.response.CustomerRewardResponse;
import com.reward.service.impl.CustomerServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerServiceImplTest {

    private static final Float AMOUNT = 100f;
    private static final String CUSTOMER_ID = "1";
    private static final Long ID = 1L;
    private static final Date PURCHASE_DATE = new Date();
    private static final Integer REWARD = 50;
    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;
    @Mock
    private CustomerPurchaseDao customerPurchaseDao;

    @Test
    public void getRewards_Success() throws CustomerCallException {
        List<CustomerPurchase> customerPurchaseList = new ArrayList();
        customerPurchaseList.add(buildCustomerPurchase());
        Mockito.when(customerPurchaseDao.findByCustomerId("1")).thenReturn(customerPurchaseList);
        CustomerRewardResponse customerRewardsResponse = customerServiceImpl.getCustomerRewardPoints("1");
        Assert.assertNotNull(customerRewardsResponse);
        assertEquals(customerRewardsResponse.getRewardTotal(), REWARD);
        assertEquals(customerRewardsResponse.getRewardPerMonth().get(0).getTotalRewards(), REWARD);
    }

    @Test
    public void getRewards_SuccessEmptyList() throws CustomerCallException {
        List<CustomerPurchase> customerPurchaseList = new ArrayList();
        Mockito.when(customerPurchaseDao.findByCustomerId("1")).thenReturn(customerPurchaseList);
        CustomerRewardResponse customerRewardsResponse = customerServiceImpl.getCustomerRewardPoints("1");
        Assert.assertNotNull(customerRewardsResponse);
        assertEquals(customerRewardsResponse.getRewardTotal(), 0);
    }

    @Test
    public void saveCustomerPurcase() throws CustomerCallException {
        Mockito.when(customerPurchaseDao.save(Mockito.any())).thenReturn(buildCustomerPurchase());
        CustomerPurchase customerPurchase = customerServiceImpl.createCustomerPurchase(buildCustomerPurchaseRequest());
        Assert.assertNotNull(customerPurchase);
        assertEquals(customerPurchase.getAmount(), AMOUNT);
        assertEquals(customerPurchase.getReward(), REWARD);
        assertEquals(customerPurchase.getCustomerId(), CUSTOMER_ID);
        assertEquals(customerPurchase.getPurchaseDate(), PURCHASE_DATE);
    }

    private CustomerPurchase buildCustomerPurchase() {
        return CustomerPurchase.builder().amount(AMOUNT).customerId(CUSTOMER_ID).id(ID).purchaseDate(PURCHASE_DATE)
                .reward(REWARD).build();
    }

    private CustomerPurchaseRequest buildCustomerPurchaseRequest() {
        return CustomerPurchaseRequest.builder().amount(AMOUNT).customerId(CUSTOMER_ID).purchaseDate(PURCHASE_DATE)
                .build();
    }

}
