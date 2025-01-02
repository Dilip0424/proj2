package com.proj.customer;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proj.customer.dto.CustomerRequest;
import com.proj.customer.dto.CustomerResponse;
import com.proj.customer.service.CustomerService;

@SpringBootTest // Loads the full application context
@AutoConfigureMockMvc // Automatically configures MockMvc
public class ControllerTesting {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService; // Mocking the service

	@Autowired
	private ObjectMapper mapper;

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
		   request.setMobileNumber("12356789");
		   request.setName("test");
		   // updated code by the dilip
		// Mock the service calls (using Mockito's when)
		when(customerService.getCustomerById(request.getMobileNumber())).thenReturn(null);
		when(customerService.saveCustomer(request)).thenReturn(response);


		 mockMvc.perform(post("/api/customer/save").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request)))
		                .andExpect(status().isCreated())
						.andExpect(jsonPath("$.message").value("Data Saved Sucessfully"))
				        .andExpect(jsonPath("$.code").value(201))
				        .andExpect(jsonPath("$.status").value("CREATED"))
				        .andExpect(jsonPath("$.data.name").value(response.getName()))
				        .andExpect(jsonPath("$.data.mobileNumber").value(response.getMobileNumber()))
				        .andExpect(jsonPath("$.data.email").value(response.getEmail()));
				        	            
	}
}
