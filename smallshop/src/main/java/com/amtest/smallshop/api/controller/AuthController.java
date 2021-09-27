package com.amtest.smallshop.api.controller;

import com.amtest.smallshop.api.UserApi;
import com.amtest.smallshop.api.entity.CustomerEntity;
import com.amtest.smallshop.api.entity.UserEntity;
import com.amtest.smallshop.api.exception.InvalidRefreshTokenException;
import com.amtest.smallshop.api.hateoas.CustomerRepresentationModelAssembler;
import com.amtest.smallshop.api.hateoas.UserRepresentationModelAssembler;
import com.amtest.smallshop.api.model.*;
import com.amtest.smallshop.api.service.UserService;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.*;

@RestController
public class AuthController implements UserApi {

    private final UserService service;
    private final PasswordEncoder passwordEncoder;
    private final UserRepresentationModelAssembler assembler;

    public AuthController(UserService service, PasswordEncoder passwordEncoder, UserRepresentationModelAssembler assembler) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
        this.assembler = assembler;
    }

    @Override
    public ResponseEntity<SignedInUser> getAccessToken(@Valid RefreshToken refreshToken) {
        return ok(service.getAccessToken(refreshToken).orElseThrow(InvalidRefreshTokenException::new));
    }

    @Override
    public ResponseEntity<SignedInUser> signIn(@Valid SignInReq signInReq) {
        UserEntity userEntity = service.findUserByUsername(signInReq.getUsername());
        if (passwordEncoder.matches(signInReq.getPassword(), userEntity.getPassword())) {
            return ok(service.getSignedInUser(userEntity));
        }
        throw new InsufficientAuthenticationException("Unauthorized.");
    }

    @Override
    public ResponseEntity<Void> signOut(@Valid RefreshToken refreshToken) {
        // We are using removeToken API for signout.
        // Ideally you would like to get tgit she user ID from Logged in user's request
        // and remove the refresh token based on retrieved user id from request.
        service.removeRefreshToken(refreshToken);
        return accepted().build();
    }

    @Override
    public ResponseEntity<User> signUp(@Valid User user) {
        String password = user.getPassword();
        String passwordFormat = "at least eight chars, having at least a digit, a lower case letter, an upper case letter, a special character, no whitespace allowed";
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if ((password == null) || !(password.matches(pattern))) {
            throw new IllegalArgumentException("Password format not valid: " + passwordFormat);
        }
        return status(HttpStatus.CREATED).body(service.createUser(user).map(assembler::toModel).get());
    }

    @Override
    public ResponseEntity<List<User>> getUsers() {
        return ok(assembler.toListModel(service.getUsers()));
    }

    @Override
    public ResponseEntity<User> getUser(UUID userId) {
        return service.getUser(userId).map(assembler::toModel).map(ResponseEntity::ok).orElse(notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteUser(UUID userId) {
        service.deleteUser(userId);
        return accepted().build();
    }

    @Override
    public ResponseEntity<Void> updateUser(@PathVariable UUID userId, @RequestBody User user) {

        Optional<UserEntity> userOptional = service.getUser(userId);
        if (!userOptional.isPresent())
            return ResponseEntity.notFound().build();
        // Keep existing field values not modified (PATCH emulation)
        // It could be simple. Just copy all the values that are not present in the request.
        user.setId(userId);
        if (user.getPassword() == null) {
            user.setPassword(userOptional.get().getPassword());
        }
        if (user.getUsername() == null) {
            user.setUsername(userOptional.get().getUsername());
        }
        if (user.getAdminStatus() == null) {
            user.setAdminStatus(userOptional.get().getAdminStatus());
        }

        service.saveUser(user);

        return ResponseEntity.noContent().build();
    }

}
