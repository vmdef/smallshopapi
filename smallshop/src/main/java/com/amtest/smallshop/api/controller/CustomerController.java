package com.amtest.smallshop.api.controller;

import com.amtest.smallshop.api.CustomerApi;
import com.amtest.smallshop.api.FileUploadUtil;
import com.amtest.smallshop.api.entity.CustomerEntity;
import com.amtest.smallshop.api.hateoas.CustomerRepresentationModelAssembler;
import com.amtest.smallshop.api.model.Customer;
import com.amtest.smallshop.api.model.ModelApiResponse;
import com.amtest.smallshop.api.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.*;

@RestController
public class CustomerController implements CustomerApi {

    private CustomerService service;
    private final CustomerRepresentationModelAssembler assembler;

    public CustomerController(CustomerService service, CustomerRepresentationModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Override
    public ResponseEntity<Customer> getCustomer(UUID customerId) {
        return service.getCustomer(customerId).map(assembler::toModel).map(ResponseEntity::ok).orElse(notFound().build());
    }

    @Override
    public ResponseEntity<List<Customer>> getCustomers() {
        return ok(assembler.toListModel(service.getCustomers()));
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(UUID customerId) {
        service.deleteCustomer(customerId);
        return noContent().build();
    }

    @Override
    public ResponseEntity<Customer> createCustomer(@Valid Customer customer) {
        return status(HttpStatus.CREATED).body(service.createCustomer(customer).map(assembler::toModel).get());
    }

    @RequestMapping(value = "/api/v1/customers/{customerId}/uploadImage", method = RequestMethod.POST, consumes = "multipart/form-data")
    @Override
    public ResponseEntity<ModelApiResponse> uploadImage(UUID customerId, @RequestPart("image") MultipartFile multipartFile) {
        Optional<CustomerEntity> customerOptional = service.getCustomer(customerId);
        if (!customerOptional.isPresent())
            return ResponseEntity.notFound().build();
        Customer customer = customerOptional.map(assembler::toModel).get();
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if (fileName != "") {
            String filePath = "user-photos/" + customerId;

            try {
                FileUploadUtil.saveFile(filePath, fileName, multipartFile);
                customer.setPhoto(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            customer.setPhoto("");
        }
        service.saveCustomer(customer);
        return accepted().build();
    }

    @Override
    public ResponseEntity<Void> updateCustomer(@PathVariable UUID customerId, @RequestBody Customer customer) {

        Optional<CustomerEntity> customerOptional = service.getCustomer(customerId);
        if (!customerOptional.isPresent())
            return ResponseEntity.notFound().build();

        customer.setId(customerId);
        // Photo can be modified only uploading another photo
        if (customerOptional.get().getPhoto() != null) {
            customer.setPhoto(customerOptional.get().getPhoto());
        }

        service.saveCustomer(customer);

        return ResponseEntity.noContent().build();
    }
}
