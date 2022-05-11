package com.api.user.repository;

import com.api.user.entity.UserImageTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserImageRepository extends JpaRepository<UserImageTb, Long> {
}