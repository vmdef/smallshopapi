package com.amtest.smallshop.api;

import com.amtest.smallshop.api.hateoas.CustomerRepresentationModelAssembler;
import com.amtest.smallshop.api.model.Customer;
import com.amtest.smallshop.api.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
public class CustomerController implements CustomerApi {
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private CustomerService service;
    private final CustomerRepresentationModelAssembler assembler;

    public CustomerController(CustomerService service, CustomerRepresentationModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Override
    public ResponseEntity<Customer> getCustomerById(Long customerId) {
        return service.getCustomerById(customerId).map(assembler::toModel).map(ResponseEntity::ok).orElse(notFound().build());
    }

}
