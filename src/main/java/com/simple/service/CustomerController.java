package com.simple.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simple.model.Customer;
import com.simple.model.CustomerRepository;

@RestController
public class CustomerController {

  private static final String template = "Hello, %s!";

  @Autowired
  private CustomerRepository customerRepository;

  @RequestMapping(value = "/greeting", produces = MediaType.APPLICATION_JSON_VALUE)
  public Customer greeting(@RequestParam(value="name", defaultValue="World") String name) {

    Customer customer = new Customer(String.format(template, name), "testowo");

    customerRepository.save(customer);

    List<Customer> customerList = customerRepository.findByLastName("testowo");

    return customerList.get(0);
  }
}
