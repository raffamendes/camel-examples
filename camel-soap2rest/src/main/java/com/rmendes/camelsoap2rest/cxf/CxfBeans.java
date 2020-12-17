package com.rmendes.camelsoap2rest.cxf;

import org.apache.camel.component.cxf.CxfEndpoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learnwebservices.services.tempconverter.TempConverterEndpoint;

@Configuration
public class CxfBeans {
	
	@Value("${endpoint.wsdl}")
	private String SOAP_URL;
	
	
	@Bean(name = "cxfConvertTemp")
	public CxfEndpoint buildCxfEndpoint() {
		CxfEndpoint cxf = new CxfEndpoint();
		cxf.setAddress(SOAP_URL);
		cxf.setServiceClass(TempConverterEndpoint.class);
		return cxf;
	}
}
