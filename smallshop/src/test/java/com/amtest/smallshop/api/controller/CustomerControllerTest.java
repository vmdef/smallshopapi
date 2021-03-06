package com.amtest.smallshop.api.controller;

import com.amtest.smallshop.api.entity.CustomerEntity;
import com.amtest.smallshop.api.entity.RoleEnum;
import com.amtest.smallshop.api.entity.UserEntity;
import com.amtest.smallshop.api.exception.RestApiErrorHandler;
import com.amtest.smallshop.api.hateoas.CustomerRepresentationModelAssembler;
import com.amtest.smallshop.api.hateoas.UserRepresentationModelAssembler;
import com.amtest.smallshop.api.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.amtest.smallshop.api.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    private static final String URI = "/api/v1/customers";
    private static final String id = "a1b9b31d-e73c-4112-af7c-b68530f38222";
    private static final String userId = "30f38222-e73c-4112-af7c-b685a1b9b31d";
    private static CustomerService service = mock(CustomerService.class);
    private static CustomerController controller;
    private static MockMvc mockMvc;
    private static MessageSource msgSource = mock(MessageSource.class);
    private static CustomerEntity entity;
    private static Customer customer;
    private static UserRepresentationModelAssembler uAssembler = mock(UserRepresentationModelAssembler.class);


    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
/*        userEntity = new UserEntity()
                .setId(UUID.fromString(userId))
                .setUsername("user")
                .setPassword("password")
                .setRole(RoleEnum.USER);*/

        controller = new CustomerController(service, new CustomerRepresentationModelAssembler(uAssembler));
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestApiErrorHandler(msgSource))
                .alwaysDo(print())
                .build();

        entity = new CustomerEntity()
                .setId(UUID.fromString(id))
                .setName("V??ctor").setSurname("D??niz").setPhoto("https://url.to.photo");
    }

    @Test
    @DisplayName("returns customer by given id")
    public void getCustomer() throws Exception {
        given(service.getCustomer(UUID.fromString(id)))
                .willReturn(Optional.of(entity));

        // when
        mockMvc.getDispatcherServlet().getHandlerMappings();
        ResultActions result = mockMvc.perform(
                get(URI + "/{id}", id)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        // then
        result.andExpect(status().isOk());
        //verifyJson(result);
    }

    @Test
    @DisplayName("returns customers")
    public void getCustomers() throws Exception {
        given(service.getCustomers()).willReturn(List.of(entity));

        // when
        mockMvc.getDispatcherServlet().getHandlerMappings();
        ResultActions result = mockMvc.perform(
                get(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        // then
        result.andExpect(status().isOk());
    }
}
