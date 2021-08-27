package com.amtest.smallshop.api.repository;

import com.amtest.smallshop.api.entity.CustomerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends CrudRepository<CustomerEntity, UUID> {
    Optional<CustomerEntity> getCustomerById(UUID customerId);

    @Query("select c from CustomerEntity c where c.name = :name")
    Optional<CustomerEntity> findCustomerByName(String name);

}
