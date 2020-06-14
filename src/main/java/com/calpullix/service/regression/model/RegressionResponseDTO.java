package com.calpullix.service.regression.model;

import java.math.BigDecimal;
import java.util.List;

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
public class RegressionResponseDTO {
	
	private BigDecimal rmsePrediction;
	
	private BigDecimal rmseTraining;
	
	private String bestArima;
	
	private List<String> labelGraphics;
	
	private List<byte[]> graphics;
	
	private List<List<String>> rowsPredictions;
	
}
