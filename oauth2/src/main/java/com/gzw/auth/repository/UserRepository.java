package com.gzw.auth.repository;

import com.gzw.auth.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by 瓦力.
 */
public interface UserRepository extends JpaRepository<SysUser, Long> {

    @Modifying
    @Query("update SysUser as user set user.lastLoginTime = :lastLoginTime where phone = :phone")

    void updateLastLoginTime(@Param(value = "phone") Long userId, @Param(value = "phone") String lastLoginTime);
}
