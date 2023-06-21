package com.jvaras.carservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jvaras.carservice.entity.Car;
import com.jvaras.carservice.repository.CarRepository;

@Service
public class CarService {

	@Autowired
	private CarRepository carRepository;

	public List<Car> getAll() {
		return this.carRepository.findAll();
	}

	public Car getCarById(int id) {
		return this.carRepository.findById(id).orElse(null);
	}

	public Car save(Car car) {
		Car carNew = this.carRepository.save(car);
		return carNew;
	}

	public List<Car> byUserId(int id) {
		return this.carRepository.findByUserId(id);
	}

}
