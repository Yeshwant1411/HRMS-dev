package com.example.auth.repository;

import com.example.auth.model.LeaveRequest;
import com.example.auth.model.LeaveStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
	List<LeaveRequest> findByStatus(LeaveStatus status);

}