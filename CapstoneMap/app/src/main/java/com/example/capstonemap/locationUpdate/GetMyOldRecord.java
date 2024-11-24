package com.example.capstonemap.locationUpdate;

import com.example.capstonemap.routes.RouteDto;
import com.example.capstonemap.routes.RouteRepository;

import java.util.ArrayList;
import java.util.List;

public class GetMyOldRecord {
    static UserRecordRepository userRecordRepository = new UserRecordRepository();
    static UserRecordDto userRecordDto = new UserRecordDto();

    private static UserRecordDto getMyOldRecord(Long userId, Long routeId) {
        userRecordRepository.getMyOldRecord(userId, routeId,
                // 성공 시 routeDtoList에 데이터 저장
                myOldRecordord -> {
                    userRecordDto=myOldRecordord;
                },
                // 실패 시 처리
                () -> {
                    System.out.println("루트 조회에 실패했습니다.");
                }
        );

        return userRecordDto;
    }
}
