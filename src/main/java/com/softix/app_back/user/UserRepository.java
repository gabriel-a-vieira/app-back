package com.softix.app_back.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import utils.model.tenant.IgnoreTenantFilter;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<UserDetails> findUserByEmail(String email);

    @IgnoreTenantFilter
    @Query("SELECT u FROM User u")
    List<User> findAllUnfiltered();

}
