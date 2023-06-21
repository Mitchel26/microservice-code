package com.jvaras.bikeservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jvaras.bikeservice.entity.Bike;

public interface BikeRepository extends JpaRepository<Bike, Integer> {

	List<Bike> findByUserId(int userId);
}
