package com.amtest.smallshop.api.repository;

import com.amtest.smallshop.api.entity.CustomerEntity;
import com.amtest.smallshop.api.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);

    @Query(value = "select count(u.*) from smallshop.user u where u.username = :username", nativeQuery = true)
    Integer countByUsername(String username);
}
