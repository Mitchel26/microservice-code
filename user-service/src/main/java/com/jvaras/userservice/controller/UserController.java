package com.jvaras.userservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jvaras.userservice.entity.User;
import com.jvaras.userservice.model.Bike;
import com.jvaras.userservice.model.Car;
import com.jvaras.userservice.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<User>> getAll() {
		List<User> users = this.userService.getAll();
		if (users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable("id") int id) {
		User user = this.userService.getUserById(id);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user);
	}

	@PostMapping
	public ResponseEntity<User> save(@RequestBody User user) {
		User userNew = this.userService.save(user);
		return ResponseEntity.ok(userNew);
	}

	@CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
	@GetMapping("cars/{userId}")
	public ResponseEntity<List<Car>> getCars(@PathVariable("userId") int userId) {
		User user = this.userService.getUserById(userId);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		List<Car> cars = this.userService.getCars(userId);
		return ResponseEntity.ok(cars);
	}

	@CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCar")
	@PostMapping("/savecar/{userId}")
	public ResponseEntity<Car> saveCar(@PathVariable("userId") int userId, @RequestBody Car car) {
		User user = this.userService.getUserById(userId);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		Car newCar = this.userService.saveCar(userId, car);
		return ResponseEntity.ok(newCar);
	}

	@CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackGetBikes")
	@GetMapping("bikes/{userId}")
	public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") int userId) {
		User user = this.userService.getUserById(userId);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		List<Bike> bikes = this.userService.getBikes(userId);
		return ResponseEntity.ok(bikes);
	}

	@CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackSaveBike")
	@PostMapping("/savebike/{userId}")
	public ResponseEntity<Bike> saveBike(@PathVariable("userId") int userId, @RequestBody Bike bike) {
		User user = this.userService.getUserById(userId);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		Bike newBike = this.userService.saveBike(userId, bike);
		return ResponseEntity.ok(newBike);
	}

	@CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetAll")
	@GetMapping("/getAll/{userId}")
	public ResponseEntity<Map<String, Object>> getAllVehicles(@PathVariable("userId") int userId) {
		Map<String, Object> result = this.userService.getUserAndVehicles(userId);
		return ResponseEntity.ok(result);
	}

	// Circut Breaker
	
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	private ResponseEntity<List<Car>> fallBackGetCars(@PathVariable("userId") int userId, RuntimeException e) {
		return new ResponseEntity("El usuario " + userId + " tiene los coches en el taller", HttpStatus.OK);
	}

	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	private ResponseEntity<Car> fallBackSaveCar(@PathVariable("userId") int userId, @RequestBody Car car,
			RuntimeException e) {
		return new ResponseEntity("El usuario " + userId + " no tiene dinero para coches", HttpStatus.OK);
	}

	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	private ResponseEntity<List<Bike>> fallBackGetBikes(@PathVariable("userId") int userId, RuntimeException e) {
		return new ResponseEntity("El usuario " + userId + " tiene las motos en el taller", HttpStatus.OK);
	}

	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	private ResponseEntity<Bike> fallBackSaveBike(@PathVariable("userId") int userId, @RequestBody Bike bike,
			RuntimeException e) {
		return new ResponseEntity("El usuario " + userId + " no tiene dinero para motos", HttpStatus.OK);
	}

	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	private ResponseEntity<Map<String, Object>> fallBackGetAll(@PathVariable("userId") int userId, RuntimeException e) {
		return new ResponseEntity("El usuario " + userId + " tiene los vehiculos en el taller", HttpStatus.OK);
	}

}
