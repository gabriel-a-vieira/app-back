package com.softix.app_back.permission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserPermissionRepository extends JpaRepository<UserPermission, String> {

    Optional<UserPermission> findByUserIdAndModule(String userId, SystemModule module);

    List<UserPermission> findByUserId(String userId);

    void deleteByUserIdIn(List<String> userIds);

    @Query("SELECT DISTINCT p.userId FROM UserPermission p")
    List<String> findDistinctUserIds();

}
