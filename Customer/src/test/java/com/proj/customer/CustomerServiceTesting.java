package com.proj.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.proj.customer.dto.CustomerRequest;
import com.proj.customer.dto.CustomerResponse;
import com.proj.customer.model.Customer;
import com.proj.customer.repository.CustomerRepository;
import com.proj.customer.service.CustomerService;
import com.proj.customer.util.HelperForConsversion;

@SpringBootTest
public class CustomerServiceTesting {

	@InjectMocks
	CustomerService customerService;

	@Mock
	CustomerRepository customerRepository;

	@Test

	public void test_saveCustomer() {
		CustomerRequest request = new CustomerRequest();
		request.setEmail("test@gmail.com");
		request.setName("test");
		request.setMobileNumber("1234567890");

		Customer customer = HelperForConsversion.conevrtToCustomer(request);
		when(customerRepository.save(customer)).thenReturn(customer);
		CustomerResponse response = customerService.saveCustomer(request);
		assertNotNull(response);
		assertEquals(request.getEmail(), response.getEmail());
		assertEquals(request.getName(), request.getName());
		assertEquals(request.getMobileNumber(), response.getMobileNumber());
		verify(customerRepository, times(1)).save(customer);

	}

	@Test
	public void getByIdCustomerPresent() {

		Customer customer = new Customer();
		customer.setEmail("test@gmail.com");
		customer.setMobileNumber(1234567890L);
		customer.setName("Dilip");
		when(customerRepository.findById(1234567890L)).thenReturn(Optional.of(customer));
		CustomerResponse response = customerService.getCustomerById("1234567890");
		assertNotNull(response);
		assertEquals(response.getName(), customer.getName());
		assertEquals(response.getEmail(), customer.getEmail());
		assertEquals(response.getMobileNumber(), String.valueOf(customer.getMobileNumber()));
		verify(customerRepository, times(1)).findById(1234567890L);

	}

	@Test
	public void customerNotFound() {
		when(customerRepository.findById(1234567890L)).thenReturn(Optional.empty());
		CustomerResponse response = customerService.getCustomerById("1234567890");
		assertNull(response);
		verify(customerRepository, timeout(1)).findById(1234567890L);
	}

	@Test
	public void deleteCustomer() {
		doNothing().when(customerRepository).deleteById(1234567890L);
		customerService.deleteCustomerById("1234567890");
		verify(customerRepository,times(1)).deleteById(1234567890L);
	}
}
