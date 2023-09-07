package com.machado.HomeCashFlow.controllers;

import com.machado.HomeCashFlow.dtos.UserDTO;
import com.machado.HomeCashFlow.entities.User;

import com.machado.HomeCashFlow.repositories.UserRepository;
import com.machado.HomeCashFlow.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    UserController controller;

    @Mock
    private UserService service;

    @Mock
    private UserRepository repository;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void setup() {
        userDTO = new UserDTO(
                "Michael",
                "Machado",
                "machadoekim@gmail.com",
                "V3cn4P4ss");
        user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setId(UUID.randomUUID());

    }

    @Test
    void saveUserAndReturnHttpCreated() {
        ResponseEntity<Object> response = assertDoesNotThrow(() -> controller.save(userDTO));
        assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(service.save(user)), response);
    }

    @Test
    void getAllUserAndResponseOkAndListIntoBody() {
        ResponseEntity<List<User>> userList = assertDoesNotThrow(() -> controller.getAll());
        assertEquals(ResponseEntity.status(HttpStatus.OK).body(service.getAll()), userList);
    }

    @Test
    void getUserByIdIfUserIdFounded() {


        user.setId(UUID.randomUUID());

        ResponseEntity<Object> responseGetById = controller.getOne(user.getId());
        Optional<User> userByService = service.getOne(user.getId());
        if (userByService.isEmpty()) {
            assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found."), responseGetById);
        } else {
            assertEquals(ResponseEntity.status(HttpStatus.OK).body(userByService), responseGetById);
        }
    }

    @Test
    void updateUserById() {
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        newUser.setId(UUID.randomUUID());

        ResponseEntity<Object> responsePutById = controller.update(newUser.getId(), userDTO);
        Optional<User> selectedUser = service.getOne(newUser.getId());
        if (selectedUser.isEmpty())
            assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found."), responsePutById);
        else {
            BeanUtils.copyProperties(userDTO, selectedUser.get());
            repository.save(selectedUser.get());
        }

    }

    @Test
    void deleteUserById() {
        Optional<User> responseGet = service.getOne(user.getId());
        ResponseEntity<Object> responseDelete = controller.delete(user.getId());

        if (responseGet.isEmpty())
            assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found."), responseDelete);
        else
            assertEquals(ResponseEntity.status(HttpStatus.OK).body("User deleted with success"), responseDelete);
    }


}

