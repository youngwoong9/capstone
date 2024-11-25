package com.example.capstonemap.locationUpdate;

public class GetOldRecord {
    static UserRecordRepository userRecordRepository = new UserRecordRepository();
    static UserRecordDto userRecordDto = new UserRecordDto();

    public static UserRecordDto getMyOldRecord(Long userId, Long routeId) {
        userRecordRepository.getMyOldRecord(userId, routeId,
                // 성공 시 routeDtoList에 데이터 저장
                myOldRecordord -> {
                    userRecordDto=myOldRecordord;
                    System.out.println("기록이 있습니다.");
                },
                // 실패 시 처리
                () -> {
                    userRecordDto=null;
                    System.out.println("기록이 없습니다.");
                }
        );

        return userRecordDto;
    }

    public static UserRecordDto getOldRecord(Long userId, Long routeId) {
        userRecordRepository.getOldRecord(userId, routeId,
                // 성공 시 routeDtoList에 데이터 저장
                OldRecordord -> {
                    userRecordDto=OldRecordord;
                    System.out.println("기록이 있습니다.");
                },
                // 실패 시 처리
                () -> {
                    userRecordDto=null;
                    System.out.println("기록이 없습니다.");
                }
        );

        return userRecordDto;
    }
}
