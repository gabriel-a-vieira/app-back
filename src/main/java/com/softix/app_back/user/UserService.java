package com.softix.app_back.user;

import com.softix.app_back.company.Company;
import com.softix.app_back.company.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(CreateUserRequest request) {

        User currentUser = getCurrentUser();

        validateCurrentUserCanCreate(currentUser);
        validateRoleAllowedByScreen(request.role());

        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email ja cadastrado");
        }

        String companyId = resolveCompanyId(currentUser, request.companyId());

        User newUser = new User();

        newUser.setName(request.name());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(request.role());
        newUser.setCompanyId(companyId);

        userRepository.save(newUser);

        return UserResponse.fromEntity(newUser);

    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario nao autenticado");
        }

        return user;

    }

    private void validateCurrentUserCanCreate(User currentUser) {

        if (currentUser.getRole() != UserRole.MASTER_ADMIN && currentUser.getRole() != UserRole.COMPANY_ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario sem permissao para cadastrar usuarios");
        }

    }

    private void validateRoleAllowedByScreen(UserRole role) {

        if (role == UserRole.MASTER_ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cadastro de MASTER_ADMIN nao permitido por esta rota");
        }

        if (role != UserRole.COMPANY_ADMIN && role != UserRole.CLIENT && role != UserRole.PROFESSIONAL) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Perfil de usuario invalido");
        }

    }

    private String resolveCompanyId(User currentUser, String requestedCompanyId) {

        if (currentUser.getRole() == UserRole.MASTER_ADMIN) {

            if (requestedCompanyId == null || requestedCompanyId.isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa obrigatoria para cadastro realizado por MASTER_ADMIN");
            }

            Company company = companyRepository.findById(requestedCompanyId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa nao encontrada"));

            return company.getId();

        }

        if (currentUser.getRole() == UserRole.COMPANY_ADMIN) {

            if (currentUser.getCompanyId() == null || currentUser.getCompanyId().isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario COMPANY_ADMIN sem empresa vinculada");
            }

            return currentUser.getCompanyId();

        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario sem permissao");

    }

}