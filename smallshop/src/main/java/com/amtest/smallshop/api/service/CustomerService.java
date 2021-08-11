package com.amtest.smallshop.api.service;

import com.amtest.smallshop.entity.CustomerEntity;

import java.util.Optional;

public interface CustomerService {
    public Optional<CustomerEntity> getCustomerById(String customerId);
}
