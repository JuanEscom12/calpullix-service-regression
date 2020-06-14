package com.calpullix.service.regression.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calpullix.db.process.branch.model.Branch;
import com.calpullix.db.process.product.model.Product;
import com.calpullix.db.process.regression.model.ForecastSales;
import com.calpullix.db.process.regression.model.ForecastVariables;
import com.calpullix.db.process.regression.repository.ForecastSalesRepository;
import com.calpullix.service.regression.model.RegressionRequestDTO;
import com.calpullix.service.regression.model.RegressionResponseDTO;
import com.calpullix.service.regression.service.RegressionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RegressionServiceImpl implements RegressionService {
		
	private static final String PRODUCT_TOKEN = " ";

	@Autowired
	private ForecastSalesRepository regressionRepository;
	
	
	@Override
	public RegressionResponseDTO getRegression(RegressionRequestDTO request) {
		log.info(":: Service getRegression {} ", request);
		final RegressionResponseDTO result = new RegressionResponseDTO();
		
		final Branch idbranch = new Branch();
		idbranch.setId(request.getBranchId());
		final Product idproduct = new Product();
		idproduct.setId(getIdProduct(request.getProduct()));
		final Optional<ForecastSales> regression = regressionRepository.
				findOneByIdbranchAndIdproductAndYearAndIsactive(idbranch, idproduct, request.getYear(), Boolean.TRUE);
		
		if (regression.isPresent()) {
			final List<byte[]> graphics = new ArrayList<>();
			final List<String> labelGraphics = new ArrayList<>();
			final List<List<String>> rowsPredictions = new ArrayList<>();
			List<String> row;
			
			result.setRmsePrediction(regression.get().getRmseprediction());
			result.setRmseTraining(regression.get().getRmsetraining());
			result.setBestArima(regression.get().getBestarima());
			 
			final List<String> months = new ArrayList<>();
			months.add("Enero");
			months.add("Febrero");
			months.add("Marzo");
			months.add("Abril");
			months.add("Mayo");
			months.add("Junio");
			months.add("Julio");
			months.add("Agosto");
			months.add("Septiembre");
			months.add("Octubre");
			months.add("Noviembre");
			months.add("Diciembre");
			int index = 0;
			for (final ForecastVariables variables : regression.get().getForecastVariables()) {
				row = new ArrayList<>();
				row.add(months.get(index));
				index++;
				row.add(variables.getPrediction().toString());
				rowsPredictions.add(row);
			}
			result.setRowsPredictions(rowsPredictions);
			
			graphics.add(regression.get().getImageautocorrelation());
			graphics.add(regression.get().getImagehistogram());
			graphics.add(regression.get().getImageplot());
			graphics.add(regression.get().getImagestationary());
			graphics.add(regression.get().getImageresults());
			result.setGraphics(graphics);
			labelGraphics.add("Gráfica de auto correlación");
			labelGraphics.add("Histograma de ventas del producto");
			labelGraphics.add("Estacionalidad del producto");
			labelGraphics.add("Ventas diferenciadas");
			labelGraphics.add("Predicciones de ventas vs resultados esperados");
			result.setLabelGraphics(labelGraphics);
			
		}
		return result;
	}

	private Integer getIdProduct(final String product) {
		final StringTokenizer token = new StringTokenizer(product, PRODUCT_TOKEN);
		return Integer.valueOf(token.nextToken());
	}
}
