package com.proj.customer.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@SuppressWarnings("serial")
@AllArgsConstructor
@Data
public class CustomerNotFound extends Exception {
      String message;
}
