package com.bank.dao;

import com.bank.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    Customer save(Customer customer);
    Optional<Customer> findById(int customerId);
    Optional<Customer> findByEmail(String email);
    List<Customer> findAll();
    Customer update(Customer customer);
    boolean deleteById(int customerId);
}
