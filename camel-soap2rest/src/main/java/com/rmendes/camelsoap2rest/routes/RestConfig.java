package com.rmendes.camelsoap2rest.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

@Component
public class RestConfig extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		restConfiguration()
		.component("undertow").host("0.0.0.0").port(9090).bindingMode(RestBindingMode.auto).scheme("http")
			.dataFormatProperty("prettyPrint", "true")
			.contextPath("/")
				.apiContextPath("/api-doc")
					.apiProperty("api.title", "Camel2Soap")
					.apiProperty("api.version", "1.0")
					.apiProperty("host","")
		.enableCORS(true);


		rest("/convert")
			.get("/celsius/to/fahrenheit/{num}")
				.consumes("text/plain").produces("text/plain")
				.description("Convert a temperature in Celsius to Fahrenheit")
				.param().name("num").type(RestParamType.path).description("Temperature in Celsius").dataType("int").endParam()
			.to("direct:celsius-to-fahrenheit")
			.get("/fahrenheit/to/celsius/{num}")
				.consumes("text/plain").produces("text/plain")
				.description("Convert a temperature in Fahrenheit to Celsius")
				.param().name("num").type(RestParamType.path).description("Temperature in Fahrenheit").dataType("int").endParam()
			.to("direct:fahrenheit-to-celsius");

	}

}
