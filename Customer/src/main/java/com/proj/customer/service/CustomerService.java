package com.proj.customer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proj.customer.dto.CustomerRequest;
import com.proj.customer.dto.CustomerResponse;
import com.proj.customer.model.Customer;
import com.proj.customer.repository.CustomerRepository;
import com.proj.customer.util.HelperForConsversion;

@Service
public class CustomerService {
	
	 @Autowired
    CustomerRepository customerRepository;

	public CustomerResponse saveCustomer(CustomerRequest request) {
		Customer customer =  HelperForConsversion.conevrtToCustomer(request);
		Customer saved = customerRepository.save(customer);
		return HelperForConsversion.convertToCustomerResponse(saved);
	}

	public CustomerResponse getCustomerById(String id) {
	      Optional<Customer> optionalCustomer = customerRepository.findById(Long.valueOf(id));
	      if(optionalCustomer.isEmpty()) {
	    	  return null;
	      }
	      return HelperForConsversion.convertToCustomerResponse(optionalCustomer.get());
	}

	public List<CustomerResponse> getAllCustomerDetails() {
		 List<Customer> customerList = customerRepository.findAll();
		   if(customerList.isEmpty()) {
			   return null;
		   }
		return  HelperForConsversion.convertToResponses(customerList);
	}

	public void deleteCustomerById(String id) {
       customerRepository.deleteById(Long.valueOf(id));
	}

}
