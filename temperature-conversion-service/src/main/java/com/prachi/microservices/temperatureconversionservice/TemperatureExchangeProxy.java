package com.prachi.microservices.temperatureconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name="temperature-exchange")
public interface TemperatureExchangeProxy {
	
	@GetMapping("/temperature-exchange/from/{from}/to/{to}")
	public TemperatureConversion retriveExchangeValue(
			@PathVariable("from") String from,
			@PathVariable("to") String to);
	
	

}
