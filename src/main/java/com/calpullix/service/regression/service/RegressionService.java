package com.calpullix.service.regression.service;

import com.calpullix.service.regression.model.RegressionRequestDTO;
import com.calpullix.service.regression.model.RegressionResponseDTO;

public interface RegressionService {

	RegressionResponseDTO getRegression(RegressionRequestDTO request);
	
}
