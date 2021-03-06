package com.amtest.smallshop.api.service;

import com.amtest.smallshop.api.entity.CustomerEntity;
import com.amtest.smallshop.api.model.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    public Optional<CustomerEntity> getCustomer(UUID customerId);
    public Iterable<CustomerEntity> getCustomers();
    void deleteCustomer(UUID customerId);
    Optional<CustomerEntity> createCustomer(Customer customer);
    Optional<CustomerEntity> saveCustomer(Customer customer);
}
