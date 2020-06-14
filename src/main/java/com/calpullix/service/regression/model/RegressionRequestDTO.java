package com.calpullix.service.regression.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegressionRequestDTO {
	
	private Integer branchId;
	
	private Integer idProduct;
	
	private Integer year;
	
	private Integer month;
	
	private String product;

}
