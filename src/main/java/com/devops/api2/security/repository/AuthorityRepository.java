package com.devops.api2.security.repository;

import com.devops.api2.security.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 현재는 필요없음.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
