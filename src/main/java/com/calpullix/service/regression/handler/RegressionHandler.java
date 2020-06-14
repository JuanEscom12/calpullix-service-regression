package com.calpullix.service.regression.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.calpullix.service.regression.model.RegressionRequestDTO;
import com.calpullix.service.regression.service.RegressionService;
import com.calpullix.service.regression.util.AbstractWrapper;
import com.calpullix.service.regression.util.ValidationHandler;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class RegressionHandler {

	@Autowired
	private RegressionService regressionService; 
	
	@Autowired
	private ValidationHandler validationHandler;

	@Value("${app.message-error-location-body}")
	private String messageErrorLocationBody;


	@Timed(value = "calpullix.service.regression.metrics", description = "Retrieve regression analysis ")
	public Mono<ServerResponse> getRegression(ServerRequest serverRequest) {
		log.info(":: Retrieve Regression Handler {} ", serverRequest);
		return validationHandler.handle(input -> input.flatMap(request -> AbstractWrapper.async(() -> 
			regressionService.getRegression(request)
		)).flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))), serverRequest,
				RegressionRequestDTO.class).switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}

}
