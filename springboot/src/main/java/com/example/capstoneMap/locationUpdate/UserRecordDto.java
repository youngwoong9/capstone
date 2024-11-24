package com.example.capstoneMap.locationUpdate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRecordDto {
	private Long id;
	private Long userId;
	private Long routeId;
	private double elapsedTime;
	private List<Double[]> locationList;
	
	public UserRecordDto(Long userId, Long routeId, double elapsedTime, List<Double[]> locationList) {
		this.userId=userId;
		this.routeId=routeId;
		this.elapsedTime=elapsedTime;
		this.locationList=locationList;
	}
	
	public UserRecordDto( double elapsedTime, List<Double[]> locationList) {
		this.elapsedTime=elapsedTime;
		this.locationList=locationList;
	}
	
	public UserRecord toEntity() {
		return new UserRecord(id, elapsedTime, locationList);
	}
}
