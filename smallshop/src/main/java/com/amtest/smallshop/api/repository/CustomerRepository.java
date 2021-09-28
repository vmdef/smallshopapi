package com.amtest.smallshop.api.repository;

import com.amtest.smallshop.api.entity.CustomerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends CrudRepository<CustomerEntity, UUID> {

    @Query("select c from CustomerEntity c where c.name = :name and c.surname = :surname")
    Optional<CustomerEntity> findCustomerByName(String name, String surname);

}
