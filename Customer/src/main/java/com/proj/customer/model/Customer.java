package com.proj.customer.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer  implements Serializable{
	
	private String name;
	 
	@Column(unique = true)
	private String  email;
    @Id
    @Column(unique = true)
    private long mobileNumber;
    
}
