 package org.example.customer_service.mapstruct;

 import org.example.customer_service.model.Customer;
 import org.example.customer_service.model.dto.CustomerRequest;
 import org.mapstruct.Mapper;
 import org.mapstruct.factory.Mappers;

 @Mapper
 public interface CustomerMapper {
     CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

     Customer customerRequestToCustomer(CustomerRequest customerRequest);
 }
