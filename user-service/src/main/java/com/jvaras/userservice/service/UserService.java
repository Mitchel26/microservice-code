package com.jvaras.userservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jvaras.userservice.entity.User;
import com.jvaras.userservice.feignclients.BikeFeignClient;
import com.jvaras.userservice.feignclients.CarFeignClient;
import com.jvaras.userservice.model.Bike;
import com.jvaras.userservice.model.Car;
import com.jvaras.userservice.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CarFeignClient carFeignClient;

	@Autowired
	private BikeFeignClient bikeFeignClient;

	public List<User> getAll() {
		return this.userRepository.findAll();
	}

	public User getUserById(int id) {
		return this.userRepository.findById(id).orElse(null);
	}

	public User save(User user) {
		User userNew = this.userRepository.save(user);
		return userNew;
	}

	// Metodos del los microservicios uso RestTemplate
	public List<Car> getCars(int id) {
		@SuppressWarnings("unchecked")
		List<Car> cars = this.restTemplate.getForObject("http://car-service/cars/byuser/" + id, List.class);
		return cars;
	}

	public List<Bike> getBikes(int id) {
		@SuppressWarnings("unchecked")
		List<Bike> bikes = this.restTemplate.getForObject("http://bike-service/bikes/byuser/" + id, List.class);
		return bikes;
	}

	// Metodos del los microservicios uso Feign
	public Car saveCar(int userId, Car car) {
		car.setUserId(userId);
		Car newCar = this.carFeignClient.save(car);
		return newCar;
	}

	public Bike saveBike(int userId, Bike bike) {
		bike.setUserId(userId);
		Bike newBike = this.bikeFeignClient.save(bike);
		return newBike;
	}

	public Map<String, Object> getUserAndVehicles(int userId) {
		Map<String, Object> result = new HashMap<>();
		User user = this.userRepository.findById(userId).orElse(null);
		
		if (user == null) {
			result.put("Mensaje", "No existe el usuario");
			return result;
		}
		
		result.put("User", user);

		List<Car> cars = this.carFeignClient.getCars(userId);

		if (cars.isEmpty()) {
			result.put("Cars", "Este user no tiene  cars");
		} else {
			result.put("Cars", cars);
		}

		List<Bike> bikes = this.bikeFeignClient.getBikes(userId);
		
		if (bikes.isEmpty()) {
			result.put("Bikes", "Este user no tiene  bikes");
		} else {
			result.put("Bikes", bikes);
		}

		return result;
	}

}
