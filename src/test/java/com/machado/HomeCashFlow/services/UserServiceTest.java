package com.machado.HomeCashFlow.services;

import com.machado.HomeCashFlow.dtos.UserDTO;
import com.machado.HomeCashFlow.entities.User;
import com.machado.HomeCashFlow.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.machado.HomeCashFlow.services.user.UserServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImp service;

    @Mock
    UserRepository repository;

    User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        UserDTO userDTO = new UserDTO(
                "Michael",
                "Machado",
                "machadoekim@gmail.com",
                "V3cn4P4ss");
        BeanUtils.copyProperties(userDTO, user);
    }

    @Test
    void getAllUsers() {
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(user));
        List<User> userList = service.getAll();

        assertEquals(Collections.singletonList(user), userList);
        Mockito.verify(repository).findAll();
        Mockito.verifyNoMoreInteractions(repository);

    }

    @Test
    void saveNewUser() {
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        newUser.setId(UUID.randomUUID());

        Mockito.when(service.save(user)).thenReturn(newUser);
        assertEquals(newUser, service.save(user));
    }

    @Test
    void getOneUserById() {
        user.setId(UUID.randomUUID());
        Mockito.when(service.getOne(user.getId())).thenReturn(Optional.ofNullable(user));

        assertEquals(service.getOne(user.getId()), Optional.ofNullable(user));
    }
}
