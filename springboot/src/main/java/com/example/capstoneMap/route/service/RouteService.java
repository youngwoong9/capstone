package com.example.capstoneMap.route.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.capstoneMap.route.Entity.Route;
import com.example.capstoneMap.route.dto.RouteDto;
import com.example.capstoneMap.route.repository.RouteRepository;

@Service
public class RouteService {
	
	@Autowired
	private RouteRepository routeRepository;
	
	//폴리라인 그린걸 저장하는 함수, 현재는 User와는 무관함
    @Transactional
    public Route saveRoute(RouteDto routeDto) {
        System.out.println("Dto: " + routeDto.toString());
        Route route = routeDto.toEntity();
        System.out.println("entity: " + route.toString());
        return routeRepository.save(route);
    }

}
