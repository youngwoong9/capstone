package com.example.capstoneMap.user;

import java.util.ArrayList;
import java.util.List;

import com.example.capstoneMap.route.Route;
import com.example.capstoneMap.route.RouteDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private Long id;
	private String username;
	private String password;
	private List<RouteDto> routeDtos;
	
	public UserDto(String username, String password) {
		this.username=username;
		this.password=password;
	}
	
	public User toEntity() {
		if(routeDtos==null) {
			return new User(id, username, password);	
		}else {
			List<Route> routes=new ArrayList<>();
			
			for(RouteDto i : routeDtos) {
				routes.add(i.toEntity());
			}
			
			return new User(id, username, password, routes);
		}
	}
}
