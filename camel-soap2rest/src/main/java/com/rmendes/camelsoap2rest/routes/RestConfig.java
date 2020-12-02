package com.rmendes.camelsoap2rest.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class RestConfig extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		restConfiguration()
		.host("0.0.0.0").port(8080)
		.bindingMode(RestBindingMode.auto);

		rest("/convert")
			.get("/celsius/to/fahrenheit/{num}")
				.to("direct:celsius-to-fahrenheit")
			.get("/fahrenheit/to/celsius/{num}")
				.to("direct:fahrenheit-to-celsius");

	}

}
