package com.api.user.domain.repository;

import com.api.user.domain.entity.UserImageTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserImageRepository extends JpaRepository<UserImageTb, Long> {
}