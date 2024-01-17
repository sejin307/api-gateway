package com.devops.api2.gateway.repository;

import com.devops.api2.gateway.model.GatewayLog;
import org.springframework.data.repository.CrudRepository;

public interface GatewayLogRepository extends CrudRepository<GatewayLog, Long> {
}
