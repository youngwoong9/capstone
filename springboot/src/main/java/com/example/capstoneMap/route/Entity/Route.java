package com.example.capstoneMap.route.Entity;

import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Route {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Column(columnDefinition = "TEXT")
	private String encodedPath;
	private String locationListJson;
	
	private Long userId;
	
	
	// 유저 아이디 안받는 버전(테스트용)
	public Route(Long id, String name, String encodedPath, String locationListJson) {
		this.id=id;
		this.name=name;
		this.encodedPath=encodedPath;
		this.locationListJson=locationListJson;
	}
	
	public Route(Long id, String name, String encodedPath, String locationListJson, Long userId) {
		this.id=id;
		this.name=name;
		this.encodedPath=encodedPath;
		this.locationListJson=locationListJson;
		this.userId=userId;
	}
}
