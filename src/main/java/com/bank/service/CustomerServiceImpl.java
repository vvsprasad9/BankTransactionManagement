package com.bank.service;

import com.bank.dao.CustomerDAO;
import com.bank.dto.CustomerDTO;
import com.bank.entity.Customer;
import com.bank.exception.InvalidTransactionException;
import com.bank.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        if (customerDAO.findByEmail(customerDTO.getEmail()).isPresent()) {
            throw new InvalidTransactionException("Customer with email " + customerDTO.getEmail() + " already exists");
        }

        Customer customer = new Customer(
                customerDTO.getName(),
                customerDTO.getEmail(),
                customerDTO.getPhone()
        );

        Customer savedCustomer = customerDAO.save(customer);
        return mapToDTO(savedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO getCustomerById(int customerId) {
        Customer customer = customerDAO.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        return mapToDTO(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO getCustomerByEmail(String email) {
        Customer customer = customerDAO.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email: " + email));
        return mapToDTO(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        return customerDAO.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO updateCustomer(int customerId, CustomerDTO customerDTO) {
        Customer existing = customerDAO.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

        existing.setName(customerDTO.getName());
        existing.setEmail(customerDTO.getEmail());
        existing.setPhone(customerDTO.getPhone());

        Customer updated = customerDAO.update(existing);
        return mapToDTO(updated);
    }

    @Override
    public void deleteCustomer(int customerId) {
        if (!customerDAO.deleteById(customerId)) {
            throw new ResourceNotFoundException("Customer not found with ID: " + customerId);
        }
    }

    private CustomerDTO mapToDTO(Customer customer) {
        return new CustomerDTO(
                customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }
}
