package com.example.capstonemap.locationUpdate;

import com.example.capstonemap.routes.RouteDto;
import com.example.capstonemap.user.UserDto;

public class SaveMyRecord {
    static UserRecordRepository userRecordRepository = new UserRecordRepository();

    public static void saveMyRecord(UserRecordDto userRecordDto, Long userId, Long routeId){

        //리파지토리, 스프링과 연동해서 경로를 만들면 저장함
        userRecordRepository.saveMyRecord(userRecordDto, userId, routeId,
                () -> System.out.println("Record saved safely"),
                () -> System.out.println("Record saving error")
        );
    }
}
