package com.calpullix.service.regression.conf;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.calpullix.service.regression.handler.RegressionHandler;

@Configuration
public class RoutesConf {

	@Value("${app.path-retrieve-regression}")
	private String pathRetrieveRegression;
	
	
	@Bean
	public RouterFunction<ServerResponse> routesLogin(RegressionHandler purchaseOrderHandler) {
		return route(POST(pathRetrieveRegression), purchaseOrderHandler::getRegression);
	}
	
}
