package com.example.capstoneMap.user;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import com.example.capstoneMap.route.Route;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

// 현재 더미 유저데이터를 추가한 상태


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "users") 
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    
    private String password;
    
    
    //mappedBy = 연관주인객체, CascadeType.ALL=이게 수정되면 다 수정됨, FetchType.LAZY= 필요할때만 호출됨
    //cascade때문에 userRepository에 route를 저장한다면 routeRepository에 자동으로 route가 저장됨
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private List<Route> routes=new ArrayList<>();
    
    public User(Long id, String username, String password) {
    	this.id=id;
    	this.username=username;
    	this.password=password;
    }
    
    // 루트를 추가하면 User 엔티티의 routes에도 추가가됨
    public void addRoute(Route route) {
    	if(routes==null) {
    		routes=new ArrayList<Route>();
    	}
    	
        routes.add(route);
    }
    
    public boolean isOwned(Long routeId, Long userId) {
        return routes.stream()
                .anyMatch(route -> route.getId().equals(routeId) && route.getUserId().equals(userId));
    }
    
    public void removeRouteById(Long routeId) {
        routes.removeIf(route -> route.getId().equals(routeId));
    }
}
