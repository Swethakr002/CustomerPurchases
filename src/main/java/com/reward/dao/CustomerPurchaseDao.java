package com.reward.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reward.model.CustomerPurchase;

@Repository
public interface CustomerPurchaseDao extends JpaRepository<CustomerPurchase, Long> {

	List<CustomerPurchase> findByCustomerId(String customerId);

}
