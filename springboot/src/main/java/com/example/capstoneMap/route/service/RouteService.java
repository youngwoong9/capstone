package com.example.capstoneMap.route.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.capstoneMap.dto.UserDto;
import com.example.capstoneMap.login.entity.User;
import com.example.capstoneMap.login.repository.UserRepository;
import com.example.capstoneMap.route.Entity.Route;
import com.example.capstoneMap.route.dto.RouteDto;
import com.example.capstoneMap.route.repository.RouteRepository;

@Service
public class RouteService {
	
	@Autowired
	private RouteRepository routeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	//폴리라인 그린걸 저장하는 함수, 유저가 Id를 통해 저장할 수 있음.
    @Transactional
    public ResponseEntity<User> saveRoute(RouteDto routeDto, Long userId) {
    	User user=userRepository.findById(userId)
    			.orElseThrow(() -> new IllegalArgumentException("User not found"));
    	
        // Route ID를 통해 중복 확인
        if (routeDto.getId() != null && routeRepository.existsById(routeDto.getId())) {
            throw new IllegalArgumentException("Route with ID " + routeDto.getId() + " already exists.");
        }
    	
        System.out.println("Dto: " + routeDto.toString());
        Route route = routeDto.toEntity();
        System.out.println("entity: " + route.toString());
        user.addRoute(route);
        userRepository.save(user);
        
        return ResponseEntity.ok(user);
    }
    
    // 모든 루트를 조회하는 함수
    @Transactional
    public ResponseEntity<List<RouteDto>> getAllRoutes(){
    	List<Route> routes=routeRepository.findAll();   
    	
        List<RouteDto> routeDtos = routes.stream()
                .map(route -> new RouteDto(route.getId(), route.getName(), route.getEncodedPath(), route.getLocationList(), route.getUserId()))
                .collect(Collectors.toList());
    	
    	return ResponseEntity.ok(routeDtos);
    	}
}
