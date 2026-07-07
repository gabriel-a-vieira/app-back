package com.softix.app_back.user;

import com.softix.app_back.company.Company;
import com.softix.app_back.company.CompanyRepository;
import com.softix.app_back.config.JWTUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.security.core.context.SecurityContextHolder;
import utils.security.SecurityUtils;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(CreateUserRequest request) {

        JWTUserData currentUser = SecurityUtils.currentUser();

        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email ja cadastrado");
        }

        String companyId = SecurityUtils.resolveCompanyId(request.companyId());

        User newUser = new User();

        newUser.setName(request.name());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(request.role());
        newUser.setCompanyId(companyId);

        userRepository.save(newUser);

        return UserResponse.fromEntity(newUser);

    }

}