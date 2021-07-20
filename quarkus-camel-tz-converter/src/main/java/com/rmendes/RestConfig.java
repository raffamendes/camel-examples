package com.rmendes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;

public class RestConfig extends RouteBuilder{
	

	@Override
	public void configure() throws Exception {
		restConfiguration()
		.component("netty-http").host("0.0.0.0").port(8080)
		.bindingMode(RestBindingMode.auto)
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
