package com.amtest.smallshop.api.service;

import com.amtest.smallshop.api.entity.CustomerEntity;
import com.amtest.smallshop.api.entity.RoleEnum;
import com.amtest.smallshop.api.entity.UserEntity;
import com.amtest.smallshop.api.entity.UserTokenEntity;
import com.amtest.smallshop.api.exception.CustomerNotFoundException;
import com.amtest.smallshop.api.exception.GenericAlreadyExistsException;
import com.amtest.smallshop.api.exception.InvalidRefreshTokenException;
import com.amtest.smallshop.api.exception.UserNotFoundException;
import com.amtest.smallshop.api.model.Customer;
import com.amtest.smallshop.api.model.RefreshToken;
import com.amtest.smallshop.api.model.SignedInUser;
import com.amtest.smallshop.api.model.User;
import com.amtest.smallshop.api.repository.UserRepository;
import com.amtest.smallshop.api.repository.UserTokenRepository;
import com.amtest.smallshop.api.security.JwtManager;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserTokenRepository userTokenRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtManager tokenManager;

    public UserServiceImpl(UserRepository repository, UserTokenRepository userTokenRepository,
                           PasswordEncoder bCryptPasswordEncoder, JwtManager tokenManager) {
        this.repository = repository;
        this.userTokenRepository = userTokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenManager = tokenManager;
    }

    @Override
    @Transactional
    public Optional<SignedInUser> createUser(User user) {
        Integer count = repository.countByUsername(user.getUsername());
        if (count > 0) {
            throw new GenericAlreadyExistsException("Use different username.");
        }
        UserEntity userEntity = repository.save(toEntity(user));
        return Optional.of(createSignedUserWithRefreshToken(userEntity));
    }

    @Override
    @Transactional
    public SignedInUser getSignedInUser(UserEntity userEntity) {
        userTokenRepository.deleteByUserId(userEntity.getId());
        return createSignedUserWithRefreshToken(userEntity);
    }

    private SignedInUser createSignedUserWithRefreshToken(UserEntity userEntity) {
        return createSignedInUser(userEntity).refreshToken(createRefreshToken(userEntity));
    }

    private SignedInUser createSignedInUser(UserEntity userEntity) {
        String token = tokenManager.create(org.springframework.security.core.userdetails.User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(Objects.nonNull(userEntity.getRole()) ? userEntity.getRole().name() : "")
                .build());
        return new SignedInUser().username(userEntity.getUsername()).accessToken(token)
                .userId(userEntity.getId().toString());
    }

    @Override
    public Optional<SignedInUser> getAccessToken(RefreshToken refreshToken) {
        // You may add an additional validation for time that would remove/invalidate the refresh token
        return userTokenRepository.findByRefreshToken(refreshToken.getRefreshToken())
                .map(ut -> Optional.of(createSignedInUser(ut.getUser()).refreshToken(refreshToken.getRefreshToken())))
                .orElseThrow(() -> new InvalidRefreshTokenException("Invalid token."));
    }

    @Override
    public void removeRefreshToken(RefreshToken refreshToken) {
        userTokenRepository.findByRefreshToken(refreshToken.getRefreshToken())
                .ifPresentOrElse(userTokenRepository::delete, () -> {
                    throw new InvalidRefreshTokenException("Invalid token.");
                });
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        if (Strings.isBlank(username)) {
            throw new UsernameNotFoundException("Invalid user.");
        }
        final String uname = username.trim();
        Optional<UserEntity> oUserEntity = repository.findByUsername(uname);
        UserEntity userEntity = oUserEntity.orElseThrow(
                () -> new UsernameNotFoundException(String.format("Given user(%s) not found.", uname)));
        return userEntity;
    }

    private UserEntity toEntity(User user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        // TODO check if the password is already encrypted
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (user.getAdminStatus() == true) {
            userEntity.setRole(RoleEnum.ADMIN);
        } else {
            userEntity.setRole(RoleEnum.USER);
        }
        return userEntity;
    }

    private String createRefreshToken(UserEntity user) {
        String token = RandomHolder.randomKey(128);
        userTokenRepository.save(new UserTokenEntity().setRefreshToken(token).setUser(user));
        return token;
    }

    // https://stackoverflow.com/a/31214709/109354
    // or can use org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(n)
    private static class RandomHolder {
        static final Random random = new SecureRandom();
        public static String randomKey(int length) {
            return String.format("%"+length+"s", new BigInteger(length*5/*base 32,2^5*/, random)
                    .toString(32)).replace('\u0020', '0');
        }
    }

    @Override
    public Iterable<UserEntity> getUsers() {
        return repository.findAll();
    }

    @Override
    public Optional<UserEntity> getUser(UUID userId) {
        return repository.findById(userId);
    }

    @Override
    public void deleteUser(UUID userId) {
        repository.deleteById(userId);
    }

    @Override
    @Transactional
    public UserEntity saveUser(User user) {
        // TODO Update only modified fields (PATCH)
        return repository.save(toEntity(user));
    }
}
