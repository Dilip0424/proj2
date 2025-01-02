package com.proj.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.proj.customer.dto.CustomerRequest;
import com.proj.customer.dto.CustomerResponse;
import com.proj.customer.model.Customer;
import com.proj.customer.util.HelperForConsversion;

@SpringBootTest
public class HelperForConversionTestCases {

	@Test
	public void convertToCustomer() {

		CustomerRequest request = new CustomerRequest();
		request.setEmail("test@gmail.com");
		request.setMobileNumber("1234567890");
		request.setName("test");
		Customer customer = HelperForConsversion.conevrtToCustomer(request);
		assertNotNull(customer);
		assertEquals(customer.getEmail(), request.getEmail());
		assertEquals(customer.getName(), request.getName());
		assertEquals(customer.getMobileNumber(), Long.valueOf(request.getMobileNumber()));
	}

	@Test
	public void convertToResponse() {
		Customer customer = new Customer();
		customer.setEmail("test@gmail.com");
		customer.setMobileNumber(1234567890);
		customer.setName("test");
		CustomerResponse response = HelperForConsversion.convertToCustomerResponse(customer);
		assertNotNull(response);
		assertEquals(response.getEmail(), customer.getEmail());
		assertEquals(response.getName(), customer.getName());
		assertEquals(response.getMobileNumber(),String.valueOf(customer.getMobileNumber()));
	}

	@Test
	public void converToResponses() {

		Customer customer = new Customer();
		customer.setEmail("test@gmail.com");
		customer.setMobileNumber(1234567890);
		customer.setName("test");

		Customer customer2 = new Customer();
		customer2.setEmail("test2@gmail.com");
		customer2.setMobileNumber(987654321);
		customer2.setName("test2");
		
		List<Customer> list = List.of(customer,customer2);
		  
		List<CustomerResponse> responses =HelperForConsversion.convertToResponses(list);
		    assertNotNull(responses);
		    assertEquals(list.size(),responses.size());
	}
}
