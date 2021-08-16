package com.amtest.smallshop.api.hateoas;

import com.amtest.smallshop.api.CustomerController;
import com.amtest.smallshop.api.model.Customer;
import com.amtest.smallshop.entity.CustomerEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerRepresentationModelAssembler extends RepresentationModelAssemblerSupport<CustomerEntity, Customer> {
    /**
     * Creates a new {@link RepresentationModelAssemblerSupport} using the given controller class and resource type.
     *
     */
    public CustomerRepresentationModelAssembler() {
        super(CustomerController.class, Customer.class);
    }

    @Override
    public Customer toModel(CustomerEntity entity) {
        Customer resource = createModelWithId(entity.getId(), entity);
        BeanUtils.copyProperties(entity, resource);
        resource.setId(entity.getId());
        // Self link generated by createModelWithId has missing api path. Therefore generating additionally.
        // can be removed once fixed.
        resource.add(
                linkTo(methodOn(CustomerController.class).getCustomerById(entity.getId()))
                        .withSelfRel());
        // TODO add after implementing the method
/*        resource.add(
                linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("customers"));*/
        return resource;
    }

    /**
     * Coverts the collection of Product entities to list of resources.
     *
     * @param entities
     */
    public List<Customer> toListModel(Iterable<CustomerEntity> entities) {
        if (Objects.isNull(entities)) {
            return Collections.emptyList();
        }
        return StreamSupport.stream(entities.spliterator(), false).map(e -> toModel(e))
                .collect(toList());
    }
}