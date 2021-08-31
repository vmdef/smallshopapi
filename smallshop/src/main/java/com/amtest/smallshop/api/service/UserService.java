package com.amtest.smallshop.api.service;

import com.amtest.smallshop.api.entity.CustomerEntity;
import com.amtest.smallshop.api.entity.UserEntity;
import com.amtest.smallshop.api.model.Customer;
import com.amtest.smallshop.api.model.RefreshToken;
import com.amtest.smallshop.api.model.SignedInUser;
import com.amtest.smallshop.api.model.User;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    public Iterable<UserEntity> getUsers();

    public Optional<UserEntity> getUserById(UUID userId);

    void deleteUserById(UUID customerId);

    Optional<SignedInUser> createUser(User user);

    UserEntity findUserByUsername(String username);

    SignedInUser getSignedInUser(UserEntity userEntity);

    Optional<SignedInUser> getAccessToken(RefreshToken refreshToken);

    void removeRefreshToken(RefreshToken refreshToken);

    public UserEntity saveUser(User user);
}
