package com.api.user.repository;

import com.api.user.entity.UserOptionTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOptionRepository extends JpaRepository<UserOptionTb, Long> {
}