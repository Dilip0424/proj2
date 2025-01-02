package com.proj.customer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proj.customer.dto.CustomerRequest;
import com.proj.customer.dto.CustomerResponse;
import com.proj.customer.exception.CustomerNotFound;
import com.proj.customer.service.CustomerService;
import com.proj.customer.util.ResponseStructure;
import  static com.proj.customer.util.EndPoints.*;

@RestController
@RequestMapping(value =CUSTOMER)
public class CustomerController {

	@Autowired
	CustomerService service;
   // Customer controller Updating code Newly 
	@PostMapping(value = SAVE_CUSTOMER)
	public ResponseEntity<ResponseStructure<CustomerResponse>> addCustomer(@RequestBody CustomerRequest request) throws Exception {
		
        try {
            Long.valueOf(request.getMobileNumber());
        } catch (NumberFormatException e) {
            throw new Exception("Invalid MobileNumber Please Provide Valid Mobile Number");
        }
        CustomerResponse response = service.getCustomerById(request.getMobileNumber());
        if (Objects.nonNull(response)) {
            throw new Exception("Please Login Your MobileNumber is Already Registered");
        }
        CustomerResponse customerResponse = service.saveCustomer(request);
        return new ResponseEntity<>(ResponseStructure.buildResponse(customerResponse, "Data Saved Sucessfully",
                HttpStatus.CREATED, HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

	@GetMapping(value = GET_CUSTOMER)
	public ResponseEntity<ResponseStructure<CustomerResponse>> getCustomerByMobileNumber(@PathVariable String id)
			throws Exception {
		try {
			Long.valueOf(id);
		} catch (NumberFormatException e) {
			throw new Exception("Invalid MobileNumber Please Provide Valid Mobile Number ");
		}
		CustomerResponse response = service.getCustomerById(id);
		if (ObjectUtils.isEmpty(response)) {
			throw new CustomerNotFound("Customer Not Found Please Register");
		}
		return new ResponseEntity<ResponseStructure<CustomerResponse>>(ResponseStructure.buildResponse(response,
				"Data Feteched Sucessfully", HttpStatus.OK, HttpStatus.OK.value()), HttpStatus.OK);
	}

	@GetMapping(value = GET_ALL_CUSTOMERS)
	public ResponseEntity<ResponseStructure<List<CustomerResponse>>> getAllCustomer() {
		List<CustomerResponse> customerResponses = service.getAllCustomerDetails();
		if (customerResponses == null) {
			return new ResponseEntity<>(ResponseStructure.buildResponse(new ArrayList<>(), "No Customers ",
					HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value()), HttpStatus.OK);
		}
		return new ResponseEntity<>(ResponseStructure.buildResponse(customerResponses,
				"Customers Data fetched Sucessfully", HttpStatus.OK, HttpStatus.OK.value()), HttpStatus.OK);
	}
	
	@DeleteMapping(value = DELETE_CUSTOMER)
	public ResponseEntity<ResponseStructure<CustomerResponse>> deleteCustomer(@PathVariable String id) throws Exception{
		try {
			Long.valueOf(id);
		} catch (NumberFormatException e) {
			throw new Exception("Invalid MobileNumber Please Provide Valid Mobile Number ");
		}
		CustomerResponse response = service.getCustomerById(id);
		if (ObjectUtils.isEmpty(response)) {
			throw new CustomerNotFound("Unable to Delete Details Customer Data not present");
		}
		    service.deleteCustomerById(id);
		    return new ResponseEntity<>(ResponseStructure.buildResponse(response,
			"Customer Data Deleted Sucessfully", HttpStatus.OK, HttpStatus.OK.value()), HttpStatus.OK);    
	}
}
