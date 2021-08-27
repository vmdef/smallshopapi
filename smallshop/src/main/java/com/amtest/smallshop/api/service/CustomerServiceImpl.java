package com.amtest.smallshop.api.service;

import com.amtest.smallshop.api.exception.GenericAlreadyExistsException;
import com.amtest.smallshop.api.model.Customer;
import com.amtest.smallshop.api.repository.CustomerRepository;
import com.amtest.smallshop.api.entity.CustomerEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<CustomerEntity> getCustomerById(UUID customerId) {
        return repository.getCustomerById(customerId);
    }

    @Override
    public Iterable<CustomerEntity> getCustomers() {
        return repository.findAll();
    }

    @Override
    public void deleteCustomerById(UUID customerId) {
        repository.deleteById(customerId);
    }

    @Override
    @Transactional
    public Optional<CustomerEntity> createCustomer(Customer customer) {
        //TODO we need a check to avoid duplicate customers (email)
/*        if (Objects.nonNull(repository.findCustomerByName(customer.getName()))) {
            throw new GenericAlreadyExistsException("Use different username and email.");
        }*/
        return Optional.of(repository.save(toEntity(customer)));
    }

    private CustomerEntity toEntity(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity();
        BeanUtils.copyProperties(customer, customerEntity);
        return customerEntity;
    }
}
