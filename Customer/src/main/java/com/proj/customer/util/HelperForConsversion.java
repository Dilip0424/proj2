package com.proj.customer.util;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.proj.customer.dto.CustomerRequest;
import com.proj.customer.dto.CustomerResponse;
import com.proj.customer.model.Customer;

public class HelperForConsversion {

	public static Customer conevrtToCustomer(CustomerRequest request) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(request, customer);
		customer.setMobileNumber(Long.valueOf(request.getMobileNumber()));
		return customer;
	}

	public static CustomerResponse convertToCustomerResponse(Customer customer) {
		CustomerResponse customerResponse = new CustomerResponse();
		BeanUtils.copyProperties(customer, customerResponse);
		customerResponse.setMobileNumber(String.valueOf(customer.getMobileNumber()));
		return customerResponse;
	}
	
	public static List<CustomerResponse> convertToResponses(List<Customer> customersList){
	     return	  customersList.stream().map(HelperForConsversion :: convertToCustomerResponse).toList();
	}
}
