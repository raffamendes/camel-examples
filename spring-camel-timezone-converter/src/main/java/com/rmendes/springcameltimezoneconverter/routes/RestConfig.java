package com.rmendes.springcameltimezoneconverter.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class RestConfig extends RouteBuilder{
	
	@Autowired
	private Environment env;

	@Override
	public void configure() throws Exception {
		restConfiguration()
		.component("servlet").bindingMode(RestBindingMode.auto).port(env.getProperty("server.port","8080"))
			.dataFormatProperty("prettyPrint", "true")
			.contextPath("/")
				.apiContextPath("/api-doc")
					.apiProperty("api.title", "TZ Converters")
					.apiProperty("api.version", "1.0")
					.apiProperty("host","")
		.enableCORS(true);
		
		rest("/convert")
		.get("/{time}/{tzFrom}/{tzTo}")
			.consumes("text/plain").produces("text/plain")
			.description("Convert a time from One Timezone to another")
			.param().name("time").type(RestParamType.path).description("Time in format YYYY-MM-DD HH:mm").dataType("String").endParam()
			.param().name("tzFrom").type(RestParamType.path).description("Original TimeZone from Java").dataType("String").endParam()
			.param().name("tzTo").type(RestParamType.path).description("Target TimeZone from Java").dataType("String").endParam()
		.to("direct:tz-converter");
	}
}
