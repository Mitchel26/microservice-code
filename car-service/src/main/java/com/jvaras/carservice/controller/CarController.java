package com.jvaras.carservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jvaras.carservice.entity.Car;
import com.jvaras.carservice.service.CarService;

@RestController
@RequestMapping("/cars")
public class CarController {

	@Autowired
	private CarService carService;

	@GetMapping
	public ResponseEntity<List<Car>> getAll() {
		List<Car> cars = this.carService.getAll();
		if (cars.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(cars);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Car> getById(@PathVariable("id") int id) {
		Car car = this.carService.getCarById(id);
		if (car == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(car);
	}

	@PostMapping
	public ResponseEntity<Car> save(@RequestBody Car car) {
		Car carNew = this.carService.save(car);
		return ResponseEntity.ok(carNew);
	}

	@GetMapping("/byuser/{userId}")
	public ResponseEntity<List<Car>> getUserById(@PathVariable("userId") int userId) {
		List<Car> cars = this.carService.byUserId(userId);
//		if (cars.isEmpty()) {
//			return ResponseEntity.noContent().build();
//		}
		return ResponseEntity.ok(cars);
	}

}
