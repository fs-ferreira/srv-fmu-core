package br.com.ferreiradev.fmu.core.application.service.impl;

import br.com.ferreiradev.fmu.core.application.mapper.UserMapper;
import br.com.ferreiradev.fmu.core.application.service.UserService;
import br.com.ferreiradev.fmu.core.domain.model.User;
import br.com.ferreiradev.fmu.core.domain.repository.UserRepository;
import br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.ResourceNotFoundException;
import br.com.ferreiradev.fmu.core.presentation.dto.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.constants.ErrorConstants.NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;

    @Override
    public UserRecord create(UserRecord user) {
        var pass = user.password();

        User entity = mapper.toEntity(user);
        if (pass != null) {
            entity.setPassword(encoder.encode(pass));
        }
        return mapper.toRecord(repository.save(entity));
    }

    @Override
    public UserRecord findByUsername(String username) {
        return mapper.toRecord(findOrThrowBy("username", username));
    }

    @Override
    public UserRecord findByEmail(String email) {
        return mapper.toRecord(findOrThrowBy("email", email));
    }


    private User findOrThrowBy(String key, String value) {
        return switch (key) {
            case "username" -> repository.findByUsername(value).orElseThrow(this::resourceNotFoundException);
            case "email" -> repository.findByEmail(value).orElseThrow(this::resourceNotFoundException);
            default -> throw resourceNotFoundException();
        };
    }

    private ResourceNotFoundException resourceNotFoundException() {
        return new ResourceNotFoundException(NOT_FOUND_MESSAGE);
    }
}
