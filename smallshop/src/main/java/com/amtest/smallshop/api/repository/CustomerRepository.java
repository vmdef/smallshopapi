package com.amtest.smallshop.api.repository;

import com.amtest.smallshop.entity.CustomerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends CrudRepository<CustomerEntity, UUID> {
    // TODO join user by creatorId
    @Query("select c from CustomerEntity c")
    public Optional<CustomerEntity> findByCustomerId(@Param("customerId") UUID customerId);
}
