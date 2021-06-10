package com.rmendes.springcameltimezoneconverter.routes;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.zone.ZoneRulesException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class TimeZoneConverter extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		from("direct:tz-converter")
		.doTry()
			.process( new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {
					LocalDateTime time = LocalDateTime.parse(exchange.getIn().getHeader("time").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
					String fromZone = exchange.getIn().getHeader("tzFrom").toString();
					String toZone = exchange.getIn().getHeader("tzTo").toString();
					exchange.getIn().setBody(toZone(time, ZoneId.of(fromZone), ZoneId.of(toZone)));
					
				}
			})
			.to("mock:output")
			.doCatch(ZoneRulesException.class)
				.setBody(simple("{{error.message}}")).to("mock:output");
		
	}
	
	private static LocalDateTime toZone(final LocalDateTime time, final ZoneId fromZone, final ZoneId toZone) {
        final ZonedDateTime zonedtime = time.atZone(fromZone);
        final ZonedDateTime converted = zonedtime.withZoneSameInstant(toZone);
        return converted.toLocalDateTime();
    }

}
