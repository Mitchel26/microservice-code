package com.jvaras.bikeservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jvaras.bikeservice.entity.Bike;
import com.jvaras.bikeservice.repository.BikeRepository;

@Service
public class BikeService {

	@Autowired
	private BikeRepository bikeRepository;

	public List<Bike> getAll() {
		return this.bikeRepository.findAll();
	}

	public Bike getBikeById(int id) {
		return this.bikeRepository.findById(id).orElse(null);
	}

	public Bike save(Bike bike) {
		Bike bikeNew = this.bikeRepository.save(bike);
		return bikeNew;
	}

	public List<Bike> byUserId(int id) {
		return this.bikeRepository.findByUserId(id);
	}

}
