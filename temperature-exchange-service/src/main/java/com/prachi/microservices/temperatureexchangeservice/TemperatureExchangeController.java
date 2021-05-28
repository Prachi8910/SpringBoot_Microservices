package com.prachi.microservices.temperatureexchangeservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemperatureExchangeController {
	
	@Autowired
	private Environment environment;
	
	@GetMapping("/temperature-exchange/from/{from}/to/{to}")
	public TemperatureExchange retrieveExchangeValue(@PathVariable String from,@PathVariable String to) {
		if (from.equals("C") && to.equals("F")) {
		TemperatureExchange temperatureExchange=new TemperatureExchange(10001L,from,to,BigDecimal.valueOf(33.8));
		String port=environment.getProperty("local.server.port");
		temperatureExchange.setEnvironment(port);
		return temperatureExchange;
	}
	else {
		TemperatureExchange temperatureExchange1=new TemperatureExchange(10002L,from,to,BigDecimal.valueOf(-17.2222));
		String port=environment.getProperty("local.server.port");
		temperatureExchange1.setEnvironment(port);
		return temperatureExchange1;
		
	}}
		

}
