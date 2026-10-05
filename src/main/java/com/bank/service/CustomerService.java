package com.bank.service;

import com.bank.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO createCustomer(CustomerDTO customerDTO);
    CustomerDTO getCustomerById(int customerId);
    CustomerDTO getCustomerByEmail(String email);
    List<CustomerDTO> getAllCustomers();
    CustomerDTO updateCustomer(int customerId, CustomerDTO customerDTO);
    void deleteCustomer(int customerId);
}
