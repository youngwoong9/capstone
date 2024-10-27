package com.example.capstoneMap.route.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.capstoneMap.route.Entity.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {
	
}