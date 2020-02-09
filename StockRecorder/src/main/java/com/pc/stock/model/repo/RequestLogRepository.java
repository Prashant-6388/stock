package com.pc.stock.model.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pc.stock.model.RequestLog;

@Repository
public interface RequestLogRepository extends CrudRepository<RequestLog, String>{

}
