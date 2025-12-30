// language: java
package br.com.ferreiradev.fmu.core.application.service.impl;

import br.com.ferreiradev.fmu.core.application.mapper.UserMapper;
import br.com.ferreiradev.fmu.core.domain.model.auth.User;
import br.com.ferreiradev.fmu.core.domain.repository.UserRepository;
import br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.ResourceNotFoundException;
import br.com.ferreiradev.fmu.core.presentation.dto.UserRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void createWhenPasswordProvidedEncodesAndSaves() {
        UserRecord input = mock(UserRecord.class);
        when(input.password()).thenReturn("plain-pass");

        User entity = mock(User.class);
        User savedEntity = mock(User.class);
        UserRecord expectedRecord = mock(UserRecord.class);

        when(mapper.toEntity(input)).thenReturn(entity);
        when(encoder.encode("plain-pass")).thenReturn("encoded-pass");
        when(repository.save(entity)).thenReturn(savedEntity);
        when(mapper.toRecord(savedEntity)).thenReturn(expectedRecord);

        UserRecord result = service.create(input);

        assertSame(expectedRecord, result);
        verify(mapper, times(1)).toEntity(input);
        verify(encoder, times(1)).encode("plain-pass");
        verify(entity, times(1)).setPassword("encoded-pass");
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toRecord(savedEntity);
        verifyNoMoreInteractions(mapper, repository, encoder, entity);
    }

    @Test
    void createWhenPasswordNullDoesNotEncode() {
        UserRecord input = mock(UserRecord.class);
        when(input.password()).thenReturn(null);

        User entity = mock(User.class);
        User savedEntity = mock(User.class);
        UserRecord expectedRecord = mock(UserRecord.class);

        when(mapper.toEntity(input)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(savedEntity);
        when(mapper.toRecord(savedEntity)).thenReturn(expectedRecord);

        UserRecord result = service.create(input);

        assertSame(expectedRecord, result);
        verify(mapper, times(1)).toEntity(input);
        verify(encoder, never()).encode(anyString());
        verify(entity, never()).setPassword(anyString());
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toRecord(savedEntity);
        verifyNoMoreInteractions(mapper, repository, encoder, entity);
    }

    @Test
    void findByUsernameWhenFoundReturnsRecord() {
        User entity = mock(User.class);
        UserRecord expectedRecord = mock(UserRecord.class);

        when(repository.findByUsername("john")).thenReturn(Optional.of(entity));
        when(mapper.toRecord(entity)).thenReturn(expectedRecord);

        UserRecord result = service.findByUsername("john");

        assertSame(expectedRecord, result);
        verify(repository, times(1)).findByUsername("john");
        verify(mapper, times(1)).toRecord(entity);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void findByUsernameWhenNotFoundThrowsResourceNotFoundException() {
        when(repository.findByUsername("missing")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findByUsername("missing"));

        verify(repository, times(1)).findByUsername("missing");
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void findByEmailWhenFoundReturnsRecord() {
        User entity = mock(User.class);
        UserRecord expectedRecord = mock(UserRecord.class);

        when(repository.findByEmail("a@b.com")).thenReturn(Optional.of(entity));
        when(mapper.toRecord(entity)).thenReturn(expectedRecord);

        UserRecord result = service.findByEmail("a@b.com");

        assertSame(expectedRecord, result);
        verify(repository, times(1)).findByEmail("a@b.com");
        verify(mapper, times(1)).toRecord(entity);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void findByEmailWhenNotFoundThrowsResourceNotFoundException() {
        when(repository.findByEmail("missing@b.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findByEmail("missing@b.com"));

        verify(repository, times(1)).findByEmail("missing@b.com");
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void findOrThrowByWhenUnknownKeyThrowsResourceNotFoundException() throws Exception {
        var method = UserServiceImpl.class.getDeclaredMethod("findOrThrowBy", String.class, String.class);
        method.setAccessible(true);

        assertThrows(ResourceNotFoundException.class, () -> {
            try {
                method.invoke(service, "unknown", "value");
            } catch (java.lang.reflect.InvocationTargetException e) {
                throw e.getTargetException();
            }
        });
    }
}
