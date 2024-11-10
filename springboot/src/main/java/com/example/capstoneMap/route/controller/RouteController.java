package com.example.capstoneMap.route.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.capstoneMap.login.entity.User;
import com.example.capstoneMap.login.service.UserService;
import com.example.capstoneMap.route.Entity.Route;
import com.example.capstoneMap.route.dto.RouteDto;
import com.example.capstoneMap.route.service.RouteService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://10.0.2.2:8080") 
public class RouteController {
	
	@Autowired
    private RouteService routeService;
	
	
	//현재 saveRoute가 Route 객체를 반환하기로 되어있는데 컨트롤러에서 사용할일 있으면 사용하기
	//PathVariable {}안에 값을 받아옴, RequestParam 내가 {} 값을 전달
    @PostMapping("/users/{userId}/routes")
    public ResponseEntity<User> saveRoute(@RequestBody RouteDto routeDto, @PathVariable("userId") Long userId) {
        return routeService.saveRoute(routeDto, userId);
    }
    
    @GetMapping("/routes")
    public ResponseEntity<List<RouteDto>> getAllRoutes(){
    	return routeService.getAllRoutes();
    }
}
