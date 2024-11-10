package com.example.capstoneMap.route.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.capstoneMap.route.Entity.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
	
}