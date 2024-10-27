package com.example.capstoneMap.route.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.capstoneMap.route.dto.RouteDto;
import com.example.capstoneMap.route.service.RouteService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://10.0.2.2:8080") 
public class RouteController {
	
	@Autowired
    private RouteService routeService;
	
	//현재 saveRoute가 Route 객체를 반환하기로 되어있는데 컨트롤러에서 사용할일 있으면 사용하기
    @PostMapping("/routes")
    public void saveRoute(@RequestBody RouteDto routeDto) {
        routeService.saveRoute(routeDto);
    }
}
