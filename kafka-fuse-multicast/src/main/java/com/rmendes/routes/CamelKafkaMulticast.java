package com.rmendes.routes;

import org.apache.camel.builder.RouteBuilder;

public class CamelKafkaMulticast extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("kafka:{{kafka.topic.name}}?brokers={{kafka.external.bootstrap.url}}"
				+ "&sslTruststoreLocation={{kafka.security.truststore.path}}"
				+ "&sslTruststorePassword={{kafka.security.truststore.password}}"
				+ "&securityProtocol={{kafka.security.protocol}}")
		.log("Message from kafka: ${body}")
		.multicast()
		.parallelProcessing().to("netty-http://{{external.endpoint.system.a}}")
		.end();
	}

}
