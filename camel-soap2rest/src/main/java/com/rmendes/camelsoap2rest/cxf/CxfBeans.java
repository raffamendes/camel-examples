package com.rmendes.camelsoap2rest.cxf;

import org.apache.camel.component.cxf.CxfEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learnwebservices.services.tempconverter.TempConverterEndpoint;

@Configuration
public class CxfBeans {
	
	
	@Bean(name = "cxfConvertTemp")
	public CxfEndpoint buildCxfEndpoint() {
		CxfEndpoint cxf = new CxfEndpoint();
		cxf.setAddress("http://www.learnwebservices.com/services/tempconverter?wsdl");
		cxf.setServiceClass(TempConverterEndpoint.class);
		return cxf;
	}
	
	

}
