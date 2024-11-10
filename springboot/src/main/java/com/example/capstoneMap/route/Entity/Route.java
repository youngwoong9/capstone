package com.example.capstoneMap.route.Entity;

import java.util.ArrayList;
import java.util.List;

import com.example.capstoneMap.login.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
@AllArgsConstructor
public class Route {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Column(columnDefinition = "TEXT")
	private String encodedPath;
	private String locationListJson;
	
	@Column
	private Long userId;
	
	// 유저 아이디 안받는 버전(테스트용)
	public Route(Long id, String name, String encodedPath, String locationListJson) {
		this.id=id;
		this.name=name;
		this.encodedPath=encodedPath;
		this.locationListJson=locationListJson;
	}
	
    @Transient
    private List<Double[]> locationList;
	
    // 엔티티가 데이터베이스에서 로드될 때마다 loadLocationList()가 자동으로 호출되서 locationList에 저장됨
    @PostLoad
    private void loadLocationList() {
        if (locationListJson != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                locationList = mapper.readValue(locationListJson, new TypeReference<List<Double[]>>() {});
            } catch (Exception e) {
                e.printStackTrace();
                locationList = new ArrayList<>();
            }
        }
    }
	
}
