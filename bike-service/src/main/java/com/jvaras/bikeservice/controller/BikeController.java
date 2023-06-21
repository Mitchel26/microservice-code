package com.jvaras.bikeservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jvaras.bikeservice.entity.Bike;
import com.jvaras.bikeservice.service.BikeService;

@RestController
@RequestMapping("/bikes")
public class BikeController {

	@Autowired
	private BikeService bikeService;

	@GetMapping
	public ResponseEntity<List<Bike>> getAll() {
		List<Bike> bikes = this.bikeService.getAll();
		if (bikes.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(bikes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Bike> getById(@PathVariable("id") int id) {
		Bike bike = this.bikeService.getBikeById(id);
		if (bike == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(bike);
	}

	@PostMapping
	public ResponseEntity<Bike> save(@RequestBody Bike bike) {
		Bike bikeNew = this.bikeService.save(bike);
		return ResponseEntity.ok(bikeNew);
	}

	@GetMapping("/byuser/{userId}")
	public ResponseEntity<List<Bike>> getUserById(@PathVariable("userId") int userId) {
		List<Bike> bikes = this.bikeService.byUserId(userId);
//		if (bikes.isEmpty()) {
//			return ResponseEntity.noContent().build();
//		}
		return ResponseEntity.ok(bikes);
	}

}
