package com.example.capstoneMap.locationUpdate;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@AllArgsConstructor
public class UserRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private Long userId;
	@Column
	private Long routeId;
	
	private double elapsedTime;
	
	@Column(columnDefinition = "TEXT")
	private List<Double[]> loactionList;
	
	public UserRecord(Long id, double elapsedTime, List<Double[]> locationList) {
		this.id=id;
		this.elapsedTime=elapsedTime;
		this.loactionList=locationList;
	}
	
}
