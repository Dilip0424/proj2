package com.proj.customer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proj.customer.controller.CustomerController;
import com.proj.customer.dto.CustomerRequest;
import com.proj.customer.dto.CustomerResponse;
import com.proj.customer.service.CustomerService;

@ExtendWith(MockitoExtension.class)
public class ControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private CustomerService customerService; // Mocked service

	@InjectMocks
	private CustomerController customerController; // Controller with mocked dependencies injected

	private final ObjectMapper mapper = new ObjectMapper(); // For JSON conversion

	@BeforeEach
	public void setup() {
		// Initialize MockMvc here to avoid NullPointerException
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}

	@Test
	void testAddCustomer_Success() throws Exception {
		// Prepare the request
		CustomerRequest request = new CustomerRequest();
		request.setEmail("abcd@gamil.com");
		request.setMobileNumber("12356789");
		request.setName("test");

		// Prepare the mock response
		CustomerResponse response = new CustomerResponse();
		response.setEmail("abcd@gamil.com");
		response.setMobileNumber("12356789");
		response.setName("test");

		// Mock the service calls
		when(customerService.getCustomerById(request.getMobileNumber())).thenReturn(null);
		when(customerService.saveCustomer(request)).thenReturn(response);

		// when(customerService.getCustomerById(request.getMobileNumber())).thenReturn(null);
		when(customerService.saveCustomer(request)).thenReturn(response);

		mockMvc.perform(post("/api/customer/save").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.message").value("Data Saved Sucessfully"))
				.andExpect(jsonPath("$.code").value(201))
				.andExpect(jsonPath("$.status").value("CREATED"))
				.andExpect(jsonPath("$.data.name").value(response.getName()))
				.andExpect(jsonPath("$.data.mobileNumber").value(response.getMobileNumber()))
				.andExpect(jsonPath("$.data.email").value(response.getEmail()));

		verify(customerService, times(1)).getCustomerById(request.getMobileNumber());
		verify(customerService, times(1)).saveCustomer(request);
	}
}
