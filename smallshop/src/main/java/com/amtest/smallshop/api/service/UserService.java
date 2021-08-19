package com.amtest.smallshop.api.service;

import com.amtest.smallshop.api.entity.UserEntity;
import com.amtest.smallshop.api.model.RefreshToken;
import com.amtest.smallshop.api.model.SignedInUser;
import com.amtest.smallshop.api.model.User;
import java.util.Optional;

public interface UserService {

    Optional<SignedInUser> createUser(User user);

    UserEntity findUserByUsername(String username);

    SignedInUser getSignedInUser(UserEntity userEntity);

    Optional<SignedInUser> getAccessToken(RefreshToken refreshToken);

    void removeRefreshToken(RefreshToken refreshToken);
}
