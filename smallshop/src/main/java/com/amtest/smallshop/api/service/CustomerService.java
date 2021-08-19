package com.amtest.smallshop.api.service;

import com.amtest.smallshop.api.entity.CustomerEntity;

import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    public Optional<CustomerEntity> getCustomerById(UUID customerId);
}
