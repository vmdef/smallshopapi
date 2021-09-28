package com.amtest.smallshop.api.service;

import com.amtest.smallshop.api.entity.UserEntity;
import com.amtest.smallshop.api.exception.CustomerNotFoundException;
import com.amtest.smallshop.api.exception.GenericAlreadyExistsException;
import com.amtest.smallshop.api.model.Customer;
import com.amtest.smallshop.api.repository.CustomerRepository;
import com.amtest.smallshop.api.entity.CustomerEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<CustomerEntity> getCustomer(UUID customerId) {
        return repository.findById(customerId);
    }

    @Override
    public Iterable<CustomerEntity> getCustomers() {
        return repository.findAll();
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        Optional<CustomerEntity> customer = repository.findById(customerId);
        if (customer.isPresent()) {
            repository.deleteById(customerId);
        } else {
            throw new CustomerNotFoundException(String
                    .format("Customer with id %s doesn't exist", customerId.toString()));
        }
    }

    @Override
    @Transactional
    public Optional<CustomerEntity> createCustomer(Customer customer) {
        Optional<CustomerEntity> existentCustomer = repository.findCustomerByName(customer.getName(), customer.getSurname());
        if (existentCustomer.isPresent()) {
            throw new GenericAlreadyExistsException("Use different name or surname.");
        }
        return Optional.of(repository.save(toEntity(customer)));
    }

    @Override
    @Transactional
    public Optional<CustomerEntity> saveCustomer(Customer customer) {
        return Optional.of(repository.save(toEntity(customer)));
    }

    private CustomerEntity toEntity(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity();
        BeanUtils.copyProperties(customer, customerEntity);
        return customerEntity;
    }
}
