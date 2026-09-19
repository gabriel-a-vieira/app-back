package com.softix.app_back.user;

import lombok.RequiredArgsConstructor;
import com.softix.app_back.client.Client;
import com.softix.app_back.client.ClientRepository;
import com.softix.app_back.client.ClientStatus;
import com.softix.app_back.config.JWTUserData;
import com.softix.app_back.person.Person;
import com.softix.app_back.person.PersonService;
import com.softix.app_back.professional.Professional;
import com.softix.app_back.professional.ProfessionalRepository;
import com.softix.app_back.professional.ProfessionalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.softix.app_back.shared.exception.BusinessException;

import utils.security.SecurityUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final ClientRepository clientRepository;

    private final ProfessionalRepository professionalRepository;

    private final PersonService personService;

    private final PasswordEncoder passwordEncoder;

    @Transactional
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

        applyLink(newUser, request.clientId(), request.professionalId(), request.autoCreateLinkedRecord());

        return toResponse(newUser);

    }

    public Page<UserResponse> findAll(String search, UserRole role, Pageable pageable) {
        return userRepository.findAdvanced(search, role, pageable).map(this::toResponse);
    }

    public UserResponse findById(String id) {
        return toResponse(findEntity(id));
    }

    @Transactional
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

        applyLink(user, request.clientId(), request.professionalId(), request.autoCreateLinkedRecord());

        return toResponse(user);

    }

    public void deleteMany(List<String> ids) {
        userRepository.deleteAllById(ids);
    }

    private User findEntity(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Usuario nao encontrado"));
    }

    private void applyLink(User user, String clientId, String professionalId, Boolean autoCreateLinkedRecord) {

        boolean autoCreate = Boolean.TRUE.equals(autoCreateLinkedRecord);

        if (user.getRole() == UserRole.CLIENT) {

            if (clientId != null && !clientId.isBlank()) {

                Client client = clientRepository.findByIdAndCompanyId(clientId, user.getCompanyId())
                        .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Cliente nao encontrado"));

                if (client.getUserId() != null && !client.getUserId().equals(user.getId())) {
                    throw new BusinessException(HttpStatus.BAD_REQUEST, "Cliente ja vinculado a outro usuario");
                }

                unlinkPreviousClient(user, client.getId());

                client.setUserId(user.getId());
                clientRepository.save(client);

            } else if (autoCreate) {

                unlinkPreviousClient(user, null);

                Client client = new Client();
                client.setCompanyId(user.getCompanyId());
                client.setUserId(user.getId());
                client.setStatus(ClientStatus.ACTIVE);
                client.setPerson(minimalPerson(user));

                clientRepository.save(client);

            } else {
                unlinkPreviousClient(user, null);
            }

        } else if (user.getRole() == UserRole.PROFESSIONAL) {

            if (professionalId != null && !professionalId.isBlank()) {

                Professional professional = professionalRepository.findByIdAndCompanyId(professionalId, user.getCompanyId())
                        .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Profissional nao encontrado"));

                if (professional.getUserId() != null && !professional.getUserId().equals(user.getId())) {
                    throw new BusinessException(HttpStatus.BAD_REQUEST, "Profissional ja vinculado a outro usuario");
                }

                unlinkPreviousProfessional(user, professional.getId());

                professional.setUserId(user.getId());
                professionalRepository.save(professional);

            } else if (autoCreate) {

                unlinkPreviousProfessional(user, null);

                Professional professional = new Professional();
                professional.setCompanyId(user.getCompanyId());
                professional.setUserId(user.getId());
                professional.setStatus(ProfessionalStatus.ACTIVE);
                professional.setPerson(minimalPerson(user));

                professionalRepository.save(professional);

            } else {
                unlinkPreviousProfessional(user, null);
            }

        }

    }

    private void unlinkPreviousClient(User user, String keepClientId) {
        clientRepository.findByCompanyIdAndUserId(user.getCompanyId(), user.getId())
                .filter(client -> !client.getId().equals(keepClientId))
                .ifPresent(client -> {
                    client.setUserId(null);
                    clientRepository.save(client);
                });
    }

    private void unlinkPreviousProfessional(User user, String keepProfessionalId) {
        professionalRepository.findByCompanyIdAndUserId(user.getCompanyId(), user.getId())
                .filter(professional -> !professional.getId().equals(keepProfessionalId))
                .ifPresent(professional -> {
                    professional.setUserId(null);
                    professionalRepository.save(professional);
                });
    }

    private Person minimalPerson(User user) {
        Person person = new Person();
        person.setCompanyId(user.getCompanyId());
        person.setName(user.getName());

        return personService.save(person);
    }

    private UserResponse toResponse(User user) {

        if (user.getRole() == UserRole.CLIENT) {

            return clientRepository.findByCompanyIdAndUserId(user.getCompanyId(), user.getId())
                    .map(client -> UserResponse.fromEntity(user, client.getId(),
                            client.getPerson() != null ? client.getPerson().getName() : null, null, null))
                    .orElseGet(() -> UserResponse.fromEntity(user));

        }

        if (user.getRole() == UserRole.PROFESSIONAL) {

            return professionalRepository.findByCompanyIdAndUserId(user.getCompanyId(), user.getId())
                    .map(professional -> UserResponse.fromEntity(user, null, null, professional.getId(),
                            professional.getPerson() != null ? professional.getPerson().getName() : null))
                    .orElseGet(() -> UserResponse.fromEntity(user));

        }

        return UserResponse.fromEntity(user);

    }

}
