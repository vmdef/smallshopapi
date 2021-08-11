package com.amtest.smallshop.api.service;

import com.amtest.smallshop.api.repository.CustomerRepository;
import com.amtest.smallshop.entity.CustomerEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<CustomerEntity> getCustomerById(Long customerId) {
        return repository.findByCustomerId(customerId);
    }
}
