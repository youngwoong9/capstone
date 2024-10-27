package com.example.capstoneMap.dto;

import java.util.ArrayList;

import com.example.capstoneMap.login.entity.User;
import com.example.capstoneMap.route.dto.RouteDto;

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
	private ArrayList<RouteDto> routes;
	
	public UserDto(String username, String password) {
		this.username=username;
		this.password=password;
	}
	
	public User toEntity() {
		return new User(id, username, password);
	}
}
