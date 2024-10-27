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
	
	public Route(Long id, String name, String encodedPath, String locationListJson) {
		this.id=id;
		this.name=name;
		this.encodedPath=encodedPath;
		this.locationListJson=locationListJson;
	}
}
