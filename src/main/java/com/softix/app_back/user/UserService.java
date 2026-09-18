package com.softix.app_back.user;

import lombok.RequiredArgsConstructor;
import com.softix.app_back.config.JWTUserData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.softix.app_back.shared.exception.BusinessException;

import utils.security.SecurityUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(CreateUserRequest request) {

        JWTUserData currentUser = SecurityUtils.currentUser();

        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Email ja cadastrado");
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

    public Page<UserResponse> findAll(String search, UserRole role, Pageable pageable) {
        return userRepository.findAdvanced(search, role, pageable).map(UserResponse::fromEntity);
    }

    public UserResponse findById(String id) {
        return UserResponse.fromEntity(findEntity(id));
    }

    public UserResponse update(String id, UpdateUserRequest request) {

        User user = findEntity(id);

        if (!user.getEmail().equalsIgnoreCase(request.email()) && userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Email ja cadastrado");
        }

        user.setName(request.name());
        user.setEmail(request.email());
        user.setRole(request.role());

        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }

        userRepository.save(user);

        return UserResponse.fromEntity(user);

    }

    public void deleteMany(List<String> ids) {
        userRepository.deleteAllById(ids);
    }

    private User findEntity(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Usuario nao encontrado"));
    }

}
