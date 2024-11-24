package com.example.capstoneMap.locationUpdate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.capstoneMap.route.Route;

@Repository
public interface UserRecordRepository extends JpaRepository<UserRecord, Long> {

}
