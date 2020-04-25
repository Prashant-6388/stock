package com.pc.stock.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pc.model.stock.RequestLog;
import com.pc.model.stock.RequestLogId;

@Repository
public interface RequestLogRepository extends JpaRepository<RequestLog, RequestLogId> {

}
