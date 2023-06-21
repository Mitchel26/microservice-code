package com.jvaras.userservice.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.jvaras.userservice.model.Car;

@FeignClient(name = "car-service", path = "/cars" /*, url = "http://localhost:8002/cars"*/)
public interface CarFeignClient {

	// Metodo de microservicio car-service

	@PostMapping
	Car save(@RequestBody Car car);

	@GetMapping("/byuser/{userId}")
	List<Car> getCars(@PathVariable("userId") int userId);
}
