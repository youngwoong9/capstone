package com.example.capstoneMap.locationUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.capstoneMap.route.Route;
import com.example.capstoneMap.route.RouteDto;
import com.example.capstoneMap.route.RouteRepository;
import com.example.capstoneMap.user.User;
import com.example.capstoneMap.user.UserRepository;

@Service
public class UserRecordService {
	
	@Autowired
	private UserRecordRepository userRecordRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RouteRepository routeRepository;
	
    @Transactional
    public ResponseEntity<Route> saveRecord(UserRecordDto userRecordDto, Long userId, Long routeId) {
    	User user=userRepository.findById(userId)
    			.orElseThrow(() -> new IllegalArgumentException("User not found"));
    	
    	Route route=routeRepository.findById(routeId)
    			.orElseThrow(() -> new IllegalArgumentException("Route not found"));
    	
        if (userRecordDto.getId() != null && routeRepository.existsById(userRecordDto.getId())) {
            throw new IllegalArgumentException("Route with ID " + userRecordDto.getId() + " already exists.");
        }
    	
        System.out.println("Dto: " + userRecordDto.toString());
        UserRecord userRecord = userRecordDto.toEntity();
        System.out.println("entity: " + userRecord.toString());
        route.addUserRecord(userRecord);
        routeRepository.save(route);
        
        return ResponseEntity.ok(route);
    }
	
}
