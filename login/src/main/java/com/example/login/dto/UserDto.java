package com.example.login.dto;

import com.example.login.entity.User;
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
	
	public UserDto(String username, String password) {
		this.username=username;
		this.password=password;
	}
	
	public User toEntity() {
		return new User(id, username, password);
	}
}
