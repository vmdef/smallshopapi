package com.amtest.smallshop.api.controller;

import com.amtest.smallshop.api.CustomerApi;
import com.amtest.smallshop.api.hateoas.CustomerRepresentationModelAssembler;
import com.amtest.smallshop.api.model.Customer;
import com.amtest.smallshop.api.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
public class CustomerController implements CustomerApi {

    private CustomerService service;
    private final CustomerRepresentationModelAssembler assembler;

    public CustomerController(CustomerService service, CustomerRepresentationModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Override
    public ResponseEntity<Customer> getCustomerById(UUID customerId) {
        return service.getCustomerById(customerId).map(assembler::toModel).map(ResponseEntity::ok).orElse(notFound().build());
    }

    //TODO implement other methods
}
