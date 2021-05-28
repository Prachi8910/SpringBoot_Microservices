package com.prachi.microservices.temperatureconversionservice;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemperatureConversionController {
	
	@Autowired
	private TemperatureExchangeProxy proxy;
	
	@Autowired
	private Environment environment;
	
	
	@GetMapping("/temperature-conversion/from/{from}/to/{to}/value/{value}")
	public TemperatureConversion calculateTemperatureConversion(
			@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal value)
	{
		
		TemperatureConversion temperatureConversion=proxy.retriveExchangeValue(from, to);
		MathContext mc = new MathContext(8);
		BigDecimal q1 = (value.divide(BigDecimal.valueOf(1.8),8, RoundingMode.HALF_UP)).add(temperatureConversion.getConversionMultiple()).subtract(BigDecimal.valueOf((32/1.8)-17.2222));
		BigDecimal q2 = (value.multiply(BigDecimal.valueOf(1.8))).add(temperatureConversion.getConversionMultiple()).subtract(BigDecimal.valueOf(1.8));
		//BigDecimal q2 = (value.add(BigDecimal.valueOf(32))).multiply(temperatureConversion.getConversionMultiple());
		
		
		String port = environment.getProperty("local.server.port");
		temperatureConversion.setEnvironment(port);
		if (from.equals("F") && to.equals("C")) {
			return new TemperatureConversion(temperatureConversion.getId(),
					from, to, value,
					temperatureConversion.getConversionMultiple(),
					q1.round(mc),
					temperatureConversion.getEnvironment());
		} else {
			return new TemperatureConversion(temperatureConversion.getId(),
					from, to, value,
					temperatureConversion.getConversionMultiple(),
					q2.round(mc),
					temperatureConversion.getEnvironment());
		}
		
	}
	

}