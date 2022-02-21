package com.api.user.domain.repository;

import com.api.user.domain.entity.UserOptionTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOptionRepository extends JpaRepository<UserOptionTb, Long> {
}