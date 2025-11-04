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

    public void create(UserRecord user){
        var pass = user.password();

        User entity = mapper.toEntity(user);
        entity.setPassword(encoder.encode(pass));
        repository.save(entity);
    }

    public UserRecord findByUsername(String username){
        return mapper.toRecord(findOrThrowBy(username));
    }

    private User findOrThrowBy(String username){
        return repository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE));
    }
}
