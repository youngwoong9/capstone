package com.example.capstonemap.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    private Long id;
    private String username;
    private String password;

    public UserDto(String username, String password){
        this.username=username;
        this.password=password;
    }

    public UserDto(Long id, String username, String password){
        this.id=id;
        this.username=username;
        this.password=password;
    }
}
