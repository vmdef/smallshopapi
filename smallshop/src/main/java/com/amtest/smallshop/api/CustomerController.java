package com.amtest.smallshop.api;

import com.amtest.smallshop.api.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController implements CustomerApi {
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Override
    public ResponseEntity<Customer> getCustomerById(Long customerId) {
        throw new RuntimeException("Manual Exception thrown");
    }

}
